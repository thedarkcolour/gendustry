package thedarkcolour.gendustry.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.items.ItemHandlerHelper;

import forestry.api.core.IErrorLogic;
import forestry.core.tiles.TilePowered;

import thedarkcolour.gendustry.compat.forestry.GendustryError;
import thedarkcolour.gendustry.menu.ThreeInputMenu;
import thedarkcolour.gendustry.registry.GBlockEntities;

public class GeneticTransposerBlockEntity extends TilePowered implements IHintTile {
	private static final float CONSUME_LABWARE_CHANCE = 0.2f;

	public static final String HINTS_KEY = "gendustry.genetic_transposer";

	private final GeneticTransposerInventory inventory;

	public GeneticTransposerBlockEntity(BlockPos pos, BlockState state) {
		super(GBlockEntities.GENETIC_TRANSPOSER.tileType(), pos, state, 10000, 1000000);

		this.inventory = new GeneticTransposerInventory(this);
		setInternalInventory(this.inventory);

		setTicksPerWorkCycle(20);
		setEnergyPerWorkCycle(50000);
	}

	@Override
	public boolean hasWork() {
		IErrorLogic errors = getErrorLogic();
		boolean noBlanks = errors.setCondition(this.inventory.getItem(GeneticTransposerInventory.SLOT_INPUT).isEmpty(), GendustryError.NO_BLANK);
		boolean noSource = errors.setCondition(this.inventory.getItem(GeneticTransposerInventory.SLOT_SOURCE).isEmpty(), GendustryError.NO_SOURCE);
		boolean noLabware = errors.setCondition(this.inventory.getItem(GeneticTransposerInventory.SLOT_LABWARE).isEmpty(), GendustryError.NO_LABWARE);
		return !noBlanks && !noSource && !noLabware;
	}

	@Override
	protected boolean workCycle() {
		// Check for room in result slot
		ItemStack copy = this.inventory.getItem(GeneticTransposerInventory.SLOT_SOURCE).copyWithCount(1);
		ItemStack output = this.inventory.getItem(GeneticTransposerInventory.SLOT_OUTPUT);
		if (!output.isEmpty() && !ItemHandlerHelper.canItemStacksStack(output, copy)) {
			return false;
		}
		// Consume inputs
		this.inventory.removeItem(GeneticTransposerInventory.SLOT_INPUT, 1);
		if (this.level.random.nextFloat() < CONSUME_LABWARE_CHANCE) {
			this.inventory.removeItem(GeneticTransposerInventory.SLOT_LABWARE, 1);
		}
		// Place output in result slot
		ItemStack result;
		if (output.isEmpty()) {
			result = copy;
		} else {
			result = output.copy();
			result.grow(1);
		}
		this.inventory.setItem(GeneticTransposerInventory.SLOT_OUTPUT, result);

		return true;
	}

	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory playerInv, Player player) {
		return ThreeInputMenu.geneticTransposer(windowId, playerInv, this);
	}

	@Override
	public String getHintsKey() {
		return HINTS_KEY;
	}
}
