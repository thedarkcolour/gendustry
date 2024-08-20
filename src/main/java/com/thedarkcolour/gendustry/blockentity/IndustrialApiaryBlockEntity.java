package com.thedarkcolour.gendustry.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.items.ItemStackHandler;

import forestry.api.IForestryApi;
import forestry.api.apiculture.IBeeHousingInventory;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IBeekeepingLogic;
import forestry.api.apiculture.genetics.IBeeSpeciesType;
import forestry.api.core.IErrorLogic;
import forestry.api.genetics.ForestrySpeciesTypes;
import forestry.api.util.TickHelper;

import com.thedarkcolour.gendustry.registry.GBlockEntities;

public class IndustrialApiaryBlockEntity extends BlockEntity implements IBeeModifier, IBeeListener, IBeeHousingInventory {
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

	private ApiaryModifiers modifiers = new ApiaryModifiers();

	public IndustrialApiaryBlockEntity(BlockPos pos, BlockState state) {
		super(GBlockEntities.INDUSTRIAL_APIARY.get(), pos, state);

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
}
