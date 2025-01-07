package thedarkcolour.gendustry.blockentity;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.fluids.FluidUtil;

import forestry.api.genetics.capability.IIndividualHandlerItem;
import forestry.core.inventory.InventoryAdapterTile;

import thedarkcolour.gendustry.item.GendustryResourceType;
import thedarkcolour.gendustry.registry.GFluids;
import thedarkcolour.gendustry.registry.GItems;

public class MutatronInventory extends InventoryAdapterTile<MutatronBlockEntity> {
	public static final int SLOT_PRIMARY = 0;
	public static final int SLOT_SECONDARY = 1;
	public static final int SLOT_LABWARE = 2;
	public static final int SLOT_CAN_INPUT = 3;
	public static final int SLOT_RESULT = 4;

	public MutatronInventory(MutatronBlockEntity tile) {
		super(tile, 5, "items");
	}

	@Override
	public boolean canSlotAccept(int slotIndex, ItemStack stack) {
		return switch (slotIndex) {
			case SLOT_PRIMARY ->
					IIndividualHandlerItem.filter(stack, (individual, stage) -> stage == individual.getType().getTypeForMutation(0));
			case SLOT_SECONDARY ->
					IIndividualHandlerItem.filter(stack, (individual, stage) -> stage == individual.getType().getTypeForMutation(1));
			case SLOT_LABWARE -> stack.is(GItems.RESOURCE.item(GendustryResourceType.LABWARE));
			case SLOT_CAN_INPUT ->
					FluidUtil.getFluidContained(stack).filter(fluid -> fluid.getFluid() == GFluids.MUTAGEN.fluid()).isPresent();
			default -> false;
		};
	}

	@Override
	public boolean canTakeItemThroughFace(int slotIndex, ItemStack stack, Direction side) {
		return slotIndex == SLOT_RESULT;
	}
}
