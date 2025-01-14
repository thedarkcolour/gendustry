package thedarkcolour.gendustry.blockentity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemHandlerHelper;

import forestry.api.IForestryApi;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeHousingInventory;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IBeekeepingLogic;
import forestry.api.apiculture.genetics.BeeLifeStage;
import forestry.api.apiculture.genetics.IBee;
import forestry.api.climate.ClimateState;
import forestry.api.climate.IClimateProvider;
import forestry.api.core.ForestryError;
import forestry.api.core.HumidityType;
import forestry.api.core.IErrorLogic;
import forestry.api.core.TemperatureType;
import forestry.api.genetics.capability.IIndividualHandlerItem;
import forestry.api.genetics.pollen.IPollen;
import forestry.apiculture.gui.IGuiBeeHousingDelegate;
import forestry.core.network.IStreamableGui;
import forestry.core.owner.IOwnedTile;
import forestry.core.owner.IOwnerHandler;
import forestry.core.owner.OwnerHandler;
import forestry.core.tiles.IPowerHandler;
import forestry.core.tiles.TileBase;
import forestry.core.utils.NetworkUtil;
import forestry.energy.EnergyHelper;
import forestry.energy.ForestryEnergyStorage;

import org.jetbrains.annotations.Nullable;
import thedarkcolour.gendustry.menu.IndustrialApiaryMenu;
import thedarkcolour.gendustry.registry.GBlockEntities;

public class IndustrialApiaryBlockEntity extends TileBase implements IBeeHousing, IOwnedTile, IClimateProvider, IGuiBeeHousingDelegate, IStreamableGui, IPowerHandler, IBeeListener {
	public static final String HINTS_KEY = "gendustry.industrial_apiary";
	public static final int BASE_ENERGY = 200;

	// Energy
	private final ForestryEnergyStorage energyStorage;
	private final LazyOptional<ForestryEnergyStorage> energyCap;
	// Inventory
	private final IndustrialApiaryInventory inventory;
	// Beekeeping logic
	private final OwnerHandler ownerHandler;
	private final IBeekeepingLogic beeLogic;
	protected IClimateProvider climate;

	// State
	private final IndustrialApiaryBeeModifier modifier;
	private int breedingProgressPercent;
	private int energyConsumption;
	private boolean recycleQueen;

	public IndustrialApiaryBlockEntity(BlockPos pos, BlockState state) {
		super(GBlockEntities.INDUSTRIAL_APIARY.tileType(), pos, state);

		this.energyStorage = new ForestryEnergyStorage(100000, 1000000);
		this.energyCap = LazyOptional.of(() -> this.energyStorage);
		this.inventory = new IndustrialApiaryInventory(this);
		setInternalInventory(this.inventory);
		this.ownerHandler = new OwnerHandler();
		this.beeLogic = IForestryApi.INSTANCE.getHiveManager().createBeekeepingLogic(this);
		this.climate = IForestryApi.INSTANCE.getClimateManager().createDummyClimateProvider();

		this.modifier = new IndustrialApiaryBeeModifier();
		// Base consumption is 200, plus whatever the upgrades
		this.energyConsumption = BASE_ENERGY;
	}

	@Override
	public void setLevel(Level level) {
		super.setLevel(level);
		this.climate = IForestryApi.INSTANCE.getClimateManager().createClimateProvider(level, this.worldPosition);

		refreshUpgrades();
	}

	@Override
	public void serverTick(Level level, BlockPos pos, BlockState state) {
		// Check redstone state
		IErrorLogic errors = getErrorLogic();
		boolean disabled = isRedstoneActivated();
		errors.setCondition(disabled, ForestryError.DISABLED_BY_REDSTONE);

		// Automation upgrade
		if (this.recycleQueen) {
			this.recycleQueen = false;

			recycleQueen();
		}

		// Bee logic that costs power
		if (!disabled) {
			if (this.beeLogic.canWork()) {
				// Check power state
				boolean hasEnergy = EnergyHelper.consumeEnergyToDoWork(this.energyStorage, 1, this.energyConsumption);
				errors.setCondition(!hasEnergy, ForestryError.NO_POWER);
				if (hasEnergy) {
					this.beeLogic.doWork();
				}
			}
		}

		// Update climate periodically
		if ((level.getGameTime() & 63L) == 0L) {
			refreshClimate();
		}
	}

	private void recycleQueen() {
		for (int i = 0; i < IndustrialApiaryInventory.OUTPUT_SLOT_COUNT; ++i) {
			int slotIndex = IndustrialApiaryInventory.OUTPUT_SLOT_START + i;
			ItemStack stack = this.inventory.getItem(slotIndex);

			IIndividualHandlerItem.ifPresent(stack, (bee, stage) -> {
				if (stage == BeeLifeStage.PRINCESS) {
					// Move the princess back into place
					this.inventory.setItem(slotIndex, ItemStack.EMPTY);
					this.inventory.setQueen(stack);
				} else if (stage == BeeLifeStage.DRONE) {
					ItemStack drone = this.inventory.getDrone();

					if (drone.isEmpty()) {
						// Move entire stack to drone slot
						this.inventory.setDrone(stack);
						this.inventory.setItem(slotIndex, ItemStack.EMPTY);
					} else {
						// Replenish drones
						int free = drone.getMaxStackSize() - drone.getCount();

						if (free > 0 && ItemHandlerHelper.canItemStacksStack(drone, stack)) {
							int taken = Math.min(stack.getCount(), free);
							stack.shrink(taken);
							ItemStack newDrone = drone.copyWithCount(drone.getCount() + taken);
							this.inventory.setDrone(newDrone);
						}
					}
				}
			});
		}
	}

	@Override
	public void setChanged() {
		super.setChanged();

		refreshUpgrades();
	}

	private void refreshUpgrades() {
		this.energyConsumption = BASE_ENERGY + this.modifier.recalculate(this.inventory);
		this.beeLogic.setWorkThrottle(Math.max(5, 550 - this.modifier.throttle));

		refreshClimate();
	}

	@Override
	public void clientTick(Level level, BlockPos pos, BlockState state) {
		if (this.beeLogic.canDoBeeFX()) {
			this.beeLogic.doBeeFX();
		}
	}

	@Override
	public boolean onPollenRetrieved(IPollen<?> pollen) {
		return this.modifier.sieve && this.inventory.addProduct(pollen.createStack(), false);
	}

	// todo fix not recycling if output is full
	@Override
	public void onQueenDeath() {
		this.recycleQueen = this.modifier.automated;

		// Fertility
		if (this.modifier.fertility > 0) {
			spawnAdditionalOffspring();
		}
	}

	private void spawnAdditionalOffspring() {
		int fertility = this.modifier.fertility;
		ArrayList<IBee> drones = new ArrayList<>();

		IIndividualHandlerItem.ifPresent(this.inventory.getQueen(), individual -> {
			if (individual instanceof IBee queen) {
				while (drones.size() < fertility) {
					List<IBee> offspring = queen.spawnDrones(this);
					if (offspring.isEmpty()) {
						// This should never happen, but if fertility is 0, avoid infinite loop
						break;
					}
					drones.addAll(offspring);
				}
			}
		});

		for (int i = 0; i < fertility; ++i) {
			ItemStack stack = drones.get(i).createStack(BeeLifeStage.DRONE);
			this.inventory.addProduct(stack, true);
		}
	}

	// Updates the climate to reflect upgrades
	private void refreshClimate() {
		IClimateProvider oldClimate = this.modifier.nether ? new ClimateState(TemperatureType.HELLISH, HumidityType.ARID) : IForestryApi.INSTANCE.getClimateManager().createClimateProvider(this.level, this.worldPosition);
		this.climate = new ClimateState(oldClimate.temperature().up(this.modifier.temperature), oldClimate.humidity().up(this.modifier.humidity));
	}

	@Override
	public void writeGuiData(FriendlyByteBuf data) {
		this.energyStorage.writeData(data);
		data.writeVarInt(this.beeLogic.getBeeProgressPercent());
		NetworkUtil.writeClimateState(data, this.climate);
	}

	@Override
	public void readGuiData(FriendlyByteBuf data) {
		this.energyStorage.readData(data);
		this.breedingProgressPercent = data.readVarInt();
		this.climate = NetworkUtil.readClimateState(data);
	}

	@Override
	public void load(CompoundTag data) {
		super.load(data);
		this.energyStorage.read(data);
		this.beeLogic.read(data);
		this.ownerHandler.read(data);
	}

	@Override
	public void saveAdditional(CompoundTag data) {
		super.saveAdditional(data);
		this.energyStorage.write(data);
		this.beeLogic.write(data);
		this.ownerHandler.write(data);
	}

	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory playerInv, Player player) {
		return new IndustrialApiaryMenu(windowId, playerInv, this);
	}

	@Override
	public Iterable<IBeeModifier> getBeeModifiers() {
		return Collections.singleton(this.modifier);
	}

	@Override
	public Iterable<IBeeListener> getBeeListeners() {
		return Collections.singleton(this);
	}

	@Override
	public IBeeHousingInventory getBeeInventory() {
		return this.inventory;
	}

	@Override
	public IBeekeepingLogic getBeekeepingLogic() {
		return this.beeLogic;
	}

	@Override
	public String getHintKey() {
		return HINTS_KEY;
	}

	@Override
	public TemperatureType temperature() {
		return this.climate.temperature();
	}

	@Override
	public HumidityType humidity() {
		return this.climate.humidity();
	}

	@Override
	public IOwnerHandler getOwnerHandler() {
		return this.ownerHandler;
	}

	@Override
	public int getBlockLightValue() {
		return this.level.getMaxLocalRawBrightness(this.worldPosition.above());
	}

	@Override
	public boolean canBlockSeeTheSky() {
		return this.level.canSeeSky(this.worldPosition.above());
	}

	@Override
	public boolean isRaining() {
		return this.level.isRainingAt(this.worldPosition.above());
	}

	@Nullable
	@Override
	public GameProfile getOwner() {
		return this.ownerHandler.getOwner();
	}

	@Override
	public Vec3 getBeeFXCoordinates() {
		return this.worldPosition.getCenter();
	}

	@Override
	public Holder<Biome> getBiome() {
		return this.level.getBiome(this.worldPosition);
	}

	@Override
	public int getHealthScaled(int pixels) {
		return (this.breedingProgressPercent * pixels) / 100;
	}

	@Override
	public ForestryEnergyStorage getEnergyManager() {
		return this.energyStorage;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		return !this.remove && capability == ForgeCapabilities.ENERGY ? this.energyCap.cast() : super.getCapability(capability, facing);
	}
}
