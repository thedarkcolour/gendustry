package thedarkcolour.gendustry.blockentity;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.fluids.FluidUtil;

import forestry.core.inventory.InventoryAdapterTile;

import thedarkcolour.gendustry.item.GendustryResourceType;
import thedarkcolour.gendustry.item.GeneticTemplateItem;
import thedarkcolour.gendustry.registry.GFluids;
import thedarkcolour.gendustry.registry.GItems;

public class ReplicatorInventory extends InventoryAdapterTile<ReplicatorBlockEntity> {
	public static final int SLOT_TEMPLATE = 0;
	public static final int SLOT_DNA_CAN_INPUT = 1;
	public static final int SLOT_PROTEIN_CAN_INPUT = 2;
	public static final int SLOT_OUTPUT = 3;

	public ReplicatorInventory(ReplicatorBlockEntity tile) {
		super(tile, 4, "items");
	}

	@Override
	public boolean canSlotAccept(int slotIndex, ItemStack stack) {
		return switch (slotIndex) {
			case SLOT_TEMPLATE -> stack.is(GItems.GENETIC_TEMPLATE.item()) && GeneticTemplateItem.isComplete(stack);
			case SLOT_DNA_CAN_INPUT -> FluidUtil.getFluidContained(stack).filter(fluid -> fluid.getFluid() == GFluids.LIQUID_DNA.fluid()).isPresent();
			case SLOT_PROTEIN_CAN_INPUT -> FluidUtil.getFluidContained(stack).filter(fluid -> fluid.getFluid() == GFluids.PROTEIN.fluid()).isPresent();
			default -> false;
		};
	}

	@Override
	public boolean canTakeItemThroughFace(int slotIndex, ItemStack stack, Direction side) {
		return slotIndex == SLOT_OUTPUT;
	}
}
