package com.thedarkcolour.gendustry.blockentity;

import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import forestry.api.core.ForestryError;
import forestry.core.fluids.FilteredTank;
import forestry.core.fluids.ITankManager;
import forestry.core.fluids.TankManager;
import forestry.core.inventory.IInventoryAdapter;
import forestry.core.tiles.ILiquidTankTile;
import forestry.core.tiles.TilePowered;

import com.thedarkcolour.gendustry.menu.MutagenProducerMenu;
import com.thedarkcolour.gendustry.recipe.MutagenRecipe;
import com.thedarkcolour.gendustry.recipe.cache.MutagenRecipeCache;
import com.thedarkcolour.gendustry.registry.GBlockEntities;
import com.thedarkcolour.gendustry.registry.GFluids;
import org.jetbrains.annotations.Nullable;

// Based somewhat on the squeezer
public class MutagenProducerBlockEntity extends TilePowered implements ILiquidTankTile {
	// All recipes take 100000 RF to process. Choose your mutagens wisely!
	private static final int ENERGY_PER_WORK_CYCLE = 100000;
	private static final int TICKS_PER_WORK_CYCLE = 200;

	private final TankManager tankManager;
	private final FilteredTank mutagenTank;
	private final MutagenProducerInventory inventory;

	@Nullable
	private MutagenRecipe currentRecipe;

	public MutagenProducerBlockEntity(BlockPos pos, BlockState state) {
		super(GBlockEntities.MUTAGEN_PRODUCER.tileType(), pos, state, 10000, 1000000);

		this.mutagenTank = new FilteredTank(10000).setFilters(Set.of(GFluids.MUTAGEN.fluid()));
		this.tankManager = new TankManager(this, mutagenTank);
		this.inventory = new MutagenProducerInventory(this);
		setInternalInventory(this.inventory);
	}

	@Override
	public void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		this.tankManager.write(nbt);
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		this.tankManager.read(nbt);
	}

	@Override
	public void writeData(FriendlyByteBuf data) {
		super.writeData(data);
		this.tankManager.writeData(data);
	}

	@Override
	public void readData(FriendlyByteBuf data) {
		super.readData(data);
		this.tankManager.readData(data);
	}

	@Override
	public void writeGuiData(FriendlyByteBuf data) {
		super.writeGuiData(data);
		this.tankManager.writeData(data);
	}

	@Override
	public void readGuiData(FriendlyByteBuf data) {
		super.readGuiData(data);
		this.tankManager.readData(data);
	}

	@Override
	public void serverTick(Level level, BlockPos pos, BlockState state) {
		super.serverTick(level, pos, state);

		if (updateOnInterval(20)) {
			FluidStack fluid = this.mutagenTank.getFluid();
			if (!fluid.isEmpty()) {
				this.inventory.fillContainers(fluid, tankManager);
			}
		}
	}

	@Override
	public ITankManager getTankManager() {
		return this.tankManager;
	}

	@Override
	public boolean hasWork() {
		// Check recipe
		MutagenRecipe matchingRecipe = MutagenRecipeCache.INSTANCE.getRecipe(this.inventory.getItem(MutagenProducerInventory.SLOT_INPUT));
		if (this.currentRecipe != matchingRecipe) {
			this.currentRecipe = matchingRecipe;

			if (this.currentRecipe != null) {
				// Configure machine
				setTicksPerWorkCycle(TICKS_PER_WORK_CYCLE);
				setEnergyPerWorkCycle(ENERGY_PER_WORK_CYCLE);
			}
		}

		// If we have a recipe, we have work
		return !getErrorLogic().setCondition(this.currentRecipe == null, ForestryError.NO_RECIPE);
	}

	@Override
	protected boolean workCycle() {
		// If inputs have been removed mid-cycle, abort
		if (this.currentRecipe == null) {
			return false;
		}

		// Check for room in result tank
		int resultAmount = this.currentRecipe.getAmount();
		if (this.mutagenTank.getRemainingSpace() < resultAmount) {
			return false;
		}

		// Consume inputs
		ItemStack input = this.inventory.getItem(MutagenProducerInventory.SLOT_INPUT);
		if (!this.currentRecipe.getIngredient().test(input)) {
			return false;
		}
		this.inventory.removeItem(MutagenProducerInventory.SLOT_INPUT, 1);

		// Create outputs
		FluidStack result = GFluids.MUTAGEN.fluidStack(resultAmount);
		this.mutagenTank.fillInternal(result, IFluidHandler.FluidAction.EXECUTE);

		// Return true upon completion of work
		return true;
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
		return new MutagenProducerMenu(pContainerId, pPlayerInventory, this);
	}

	public static boolean isMutagenIngredient(ItemStack stack) {
		return MutagenRecipeCache.INSTANCE.getRecipe(stack) != null;
	}

	@Override
	public IInventoryAdapter getInternalInventory() {
		return super.getInternalInventory();
	}
}
