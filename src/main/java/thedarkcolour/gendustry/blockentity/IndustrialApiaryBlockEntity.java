package thedarkcolour.gendustry.blockentity;

import java.util.Collections;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

import forestry.api.IForestryApi;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeHousingInventory;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IBeekeepingLogic;
import forestry.api.apiculture.genetics.IBeeSpecies;
import forestry.api.climate.IClimateProvider;
import forestry.api.core.ForestryError;
import forestry.api.core.HumidityType;
import forestry.api.core.IErrorLogic;
import forestry.api.core.TemperatureType;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IMutation;
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
	private final BeeModifier modifier;
	private int breedingProgressPercent;
	private int energyConsumption;

	public IndustrialApiaryBlockEntity(BlockPos pos, BlockState state) {
		super(GBlockEntities.INDUSTRIAL_APIARY.tileType(), pos, state);

		this.energyStorage = new ForestryEnergyStorage(100000, 500000);
		this.energyCap = LazyOptional.of(() -> this.energyStorage);
		this.inventory = new IndustrialApiaryInventory(this);
		setInternalInventory(this.inventory);
		this.ownerHandler = new OwnerHandler();
		this.beeLogic = IForestryApi.INSTANCE.getHiveManager().createBeekeepingLogic(this);
		this.climate = IForestryApi.INSTANCE.getClimateManager().createDummyClimateProvider();

		this.modifier = new BeeModifier();
		// Base consumption is 200, plus whatever the upgrades
		this.energyConsumption = BASE_ENERGY;
	}

	@Override
	public void setLevel(Level level) {
		super.setLevel(level);
		this.climate = IForestryApi.INSTANCE.getClimateManager().createClimateProvider(level, this.worldPosition);
	}

	@Override
	public void serverTick(Level level, BlockPos pos, BlockState state) {
		// Check redstone state
		IErrorLogic errors = getErrorLogic();
		boolean disabled = isRedstoneActivated();
		errors.setCondition(disabled, ForestryError.DISABLED_BY_REDSTONE);

		if (!disabled) {
			if (this.beeLogic.canWork()) {
				boolean hasEnergy = EnergyHelper.consumeEnergyToDoWork(this.energyStorage, 1, this.energyConsumption);
				if (hasEnergy) {
					this.beeLogic.doWork();
				}
			}
		}

		if ((level.getGameTime() & 63L) == 0L) {
			this.climate = IForestryApi.INSTANCE.getClimateManager().createClimateProvider(level, pos);
		}
	}

	@Override
	public void clientTick(Level level, BlockPos pos, BlockState state) {
		if (this.beeLogic.canDoBeeFX()) {
			this.beeLogic.doBeeFX();
		}
	}

	@Override
	public boolean onPollenRetrieved(IPollen<?> pollen) {
		return this.inventory.addProduct(pollen.createStack(), false);
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
		return List.of(this);
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
	public void writeGuiData(FriendlyByteBuf data) {
		data.writeVarInt(this.beeLogic.getBeeProgressPercent());
		NetworkUtil.writeClimateState(data, this.climate);
	}

	@Override
	public void readGuiData(FriendlyByteBuf data) {
		this.breedingProgressPercent = this.beeLogic.getBeeProgressPercent();
		this.climate = NetworkUtil.readClimateState(data);
	}

	@Override
	public ForestryEnergyStorage getEnergyManager() {
		return this.energyStorage;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		return !this.remove && capability == ForgeCapabilities.ENERGY ? this.energyCap.cast() : super.getCapability(capability, facing);
	}

	private static class BeeModifier implements IBeeModifier {
		private float territory = 1f;
		private float mutation = 1f;
		private float currentAging = 1f;
		private float productivity = 1f;
		private float pollination = 1f;
		private boolean stabilized;
		private boolean weatherproof;
		private boolean lighting;
		private boolean sky;
		private boolean nether;

		@Override
		public Vec3i modifyTerritory(IGenome genome, Vec3i currentModifier) {
			return new Vec3i((int) (currentModifier.getX() * this.territory), (int) (currentModifier.getY() * this.territory), (int) (currentModifier.getZ() * this.territory));
		}

		@Override
		public float modifyMutationChance(IGenome genome, IGenome mate, IMutation<IBeeSpecies> mutation, float currentChance) {
			return currentChance * this.mutation;
		}

		@Override
		public float modifyAging(IGenome genome, @Nullable IGenome mate, float currentAging) {
			return currentAging * this.currentAging;
		}

		@Override
		public float modifyProductionSpeed(IGenome genome, float currentSpeed) {
			return currentSpeed * this.productivity;
		}

		@Override
		public float modifyPollination(IGenome genome, float currentPollination) {
			return currentPollination * this.pollination;
		}

		@Override
		public float modifyGeneticDecay(IGenome genome, float currentDecay) {
			return this.stabilized ? 0.0f : currentDecay;
		}

		@Override
		public boolean isSealed() {
			return this.weatherproof;
		}

		@Override
		public boolean isAlwaysActive(IGenome genome) {
			return this.lighting;
		}

		@Override
		public boolean isSunlightSimulated() {
			return this.sky;
		}

		@Override
		public boolean isHellish() {
			return this.nether;
		}
	}
}
