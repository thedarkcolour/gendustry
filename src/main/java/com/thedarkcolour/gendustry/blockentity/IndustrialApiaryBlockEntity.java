package com.thedarkcolour.gendustry.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.items.ItemStackHandler;

import forestry.api.IForestryApi;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeHousingInventory;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IBeekeepingLogic;
import forestry.api.core.HumidityType;
import forestry.api.core.IErrorLogic;
import forestry.api.core.TemperatureType;
import forestry.api.util.TickHelper;
import forestry.core.tiles.TileForestry;

import com.thedarkcolour.gendustry.registry.GBlockEntities;
import org.jetbrains.annotations.Nullable;

public class IndustrialApiaryBlockEntity extends TileForestry implements IBeeModifier, IBeeListener, IBeeHousingInventory, IBeeHousing {
	public static final int QUEEN = 0;
	public static final int DRONE = 1;
	public static final int UPGRADE_SLOT_START = 2;
	public static final int UPGRADE_SLOT_COUNT = 4;
	public static final int OUTPUT_SLOT_START = 6;
	public static final int OUTPUT_SLOT_COUNT = 9;

	private final IErrorLogic errorHandler = IForestryApi.INSTANCE.getErrorManager().createErrorLogic();
	private final IBeekeepingLogic beeLogic = IForestryApi.INSTANCE.getHiveManager().createBeekeepingLogic(this);
	private final TickHelper tickHelper;
	private final ItemStackHandler storage = new ItemStackHandler(15);

	private final ApiaryModifiers modifiers = new ApiaryModifiers();

	public IndustrialApiaryBlockEntity(BlockPos pos, BlockState state) {
		super(GBlockEntities.INDUSTRIAL_APIARY.tileType(), pos, state);

		this.tickHelper = new TickHelper(pos.hashCode());
	}

	public static void clientTick(IndustrialApiaryBlockEntity apiary, Level level, BlockPos pos, BlockState state) {
		apiary.tickHelper.onTick();

		if (apiary.beeLogic.canDoBeeFX()) {
			apiary.beeLogic.doBeeFX();
		}
	}

	public static void serverTick(IndustrialApiaryBlockEntity apiary, Level level, BlockPos pos, BlockState state) {
		if (apiary.beeLogic.canWork()) {
			apiary.beeLogic.doWork();
		}
	}

	@Override
	public ItemStack getQueen() {
		return null;
	}

	@Override
	public ItemStack getDrone() {
		return null;
	}

	@Override
	public void setQueen(ItemStack stack) {

	}

	@Override
	public void setDrone(ItemStack stack) {

	}

	@Override
	public boolean addProduct(ItemStack product, boolean all) {
		return false;
	}

	@Override
	public Iterable<IBeeModifier> getBeeModifiers() {
		return null;
	}

	@Override
	public Iterable<IBeeListener> getBeeListeners() {
		return null;
	}

	@Override
	public IBeeHousingInventory getBeeInventory() {
		return null;
	}

	@Override
	public IBeekeepingLogic getBeekeepingLogic() {
		return null;
	}

	@Override
	public int getBlockLightValue() {
		return 0;
	}

	@Override
	public boolean canBlockSeeTheSky() {
		return false;
	}

	@Override
	public boolean isRaining() {
		return false;
	}

	@Nullable
	@Override
	public GameProfile getOwner() {
		return null;
	}

	@Override
	public Vec3 getBeeFXCoordinates() {
		return null;
	}

	@Override
	public Holder<Biome> getBiome() {
		return null;
	}

	@Override
	public TemperatureType temperature() {
		return null;
	}

	@Override
	public HumidityType humidity() {
		return null;
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
		return null;
	}
}
