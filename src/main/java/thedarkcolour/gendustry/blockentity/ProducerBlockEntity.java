package thedarkcolour.gendustry.blockentity;

import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import forestry.api.core.IError;
import forestry.core.fluids.FilteredTank;
import forestry.core.fluids.FluidHelper;
import forestry.modules.features.FeatureTileType;

import org.jetbrains.annotations.Nullable;
import thedarkcolour.gendustry.compat.forestry.GendustryError;
import thedarkcolour.gendustry.item.GendustryResourceType;
import thedarkcolour.gendustry.menu.ProducerMenu;
import thedarkcolour.gendustry.recipe.ProcessorRecipe;
import thedarkcolour.gendustry.registry.GFluids;
import thedarkcolour.gendustry.registry.GItems;

// Common logic shared between Mutagen Producer, Protein Liquefier, DNA Extractor
public abstract class ProducerBlockEntity<T extends ProducerBlockEntity<T, R>, R extends ProcessorRecipe> extends PoweredTankBlockEntity {
	private static final float CONSUME_LABWARE_CHANCE = 0.1f;

	protected final FilteredTank outputTank;
	protected final ProducerInventory<T> inventory;
	public final boolean usesLabware;
	private final GFluids resultFluid;

	@Nullable
	protected ProcessorRecipe currentRecipe;

	@SuppressWarnings("unchecked")
	public ProducerBlockEntity(FeatureTileType<?> type, GFluids result, boolean usesLabware, BlockPos pos, BlockState state) {
		super(type.tileType(), pos, state, 10000, 1000000);

		this.outputTank = new FilteredTank(10000, false, true).setFilters(Set.of(result.fluid()));
		this.tankManager.add(this.outputTank);
		this.inventory = new ProducerInventory<>((T) this, usesLabware);
		this.usesLabware = usesLabware;
		this.resultFluid = result;

		setInternalInventory(this.inventory);
	}

	@Override
	public void serverTick(Level level, BlockPos pos, BlockState state) {
		super.serverTick(level, pos, state);

		if (updateOnInterval(20)) {
			FluidStack fluid = this.outputTank.getFluid();
			if (!fluid.isEmpty()) {
				FluidHelper.fillContainers(this.tankManager, this.inventory, ProducerInventory.SLOT_CAN_INPUT, ProducerInventory.SLOT_CAN_OUTPUT, fluid.getFluid(), true);
			}
		}
	}

	@Override
	public boolean hasWork() {
		// Check input
		R matchingRecipe = getRecipe(this.inventory.getItem(ProducerInventory.SLOT_INPUT));
		if (this.currentRecipe != matchingRecipe) {
			this.currentRecipe = matchingRecipe;

			if (this.currentRecipe != null) {
				// Set up machine for work
				startWorking();
			}
		}

		boolean hasInput = !getErrorLogic().setCondition(this.currentRecipe == null, getNoInputError());

		// Check labware
		if (this.usesLabware) {
			ItemStack labware = this.inventory.getItem(ProducerInventory.SLOT_LABWARE);
			boolean hasLabware = !getErrorLogic().setCondition(labware.isEmpty(), GendustryError.NO_LABWARE);

			return hasInput && hasLabware;
		}

		return hasInput;
	}

	@Override
	protected boolean workCycle() {
		// Check for room in result tank
		int resultAmount = this.currentRecipe.getAmount();
		if (this.outputTank.getRemainingSpace() < resultAmount) {
			return false;
		}

		// Check input
		ItemStack input = this.inventory.getItem(ProducerInventory.SLOT_INPUT);
		if (!this.currentRecipe.isIngredient(input)) {
			return false;
		}
		if (this.usesLabware) {
			// Check labware
			ItemStack labware = this.inventory.getItem(ProducerInventory.SLOT_LABWARE);
			if (!labware.is(GItems.RESOURCE.item(GendustryResourceType.LABWARE))) {
				return false;
			}
			// Consume labware
			if (this.level.random.nextFloat() < CONSUME_LABWARE_CHANCE) {
				this.inventory.removeItem(ProducerInventory.SLOT_LABWARE, 1);
			}
		}
		// Consume input
		this.inventory.removeItem(ProducerInventory.SLOT_INPUT, 1);

		// Create outputs
		FluidStack result = this.resultFluid.fluidStack(resultAmount);
		this.outputTank.fillInternal(result, IFluidHandler.FluidAction.EXECUTE);

		// Return true upon completion of work
		return true;
	}

	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory playerInv, Player player) {
		return new ProducerMenu(windowId, playerInv, this);
	}

	public abstract boolean isValidInput(ItemStack input);

	@Nullable
	public abstract R getRecipe(ItemStack input);

	public abstract void startWorking();

	public abstract IError getNoInputError();

	public abstract String getHintsKey();
}
