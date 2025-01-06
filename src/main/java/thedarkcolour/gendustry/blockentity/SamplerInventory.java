package thedarkcolour.gendustry.blockentity;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

import forestry.api.genetics.capability.IIndividualHandlerItem;
import forestry.core.inventory.InventoryAdapterTile;

import thedarkcolour.gendustry.item.GendustryResourceType;
import thedarkcolour.gendustry.registry.GItems;

public class SamplerInventory extends InventoryAdapterTile<SamplerBlockEntity> {
	public static final int SLOT_INPUT = 0;
	public static final int SLOT_BLANK_SAMPLE = 1;
	public static final int SLOT_LABWARE = 2;
	public static final int SLOT_OUTPUT = 3;

	public SamplerInventory(SamplerBlockEntity tile) {
		super(tile, 4, "items");
	}

	@Override
	public boolean canSlotAccept(int slotIndex, ItemStack stack) {
		return switch (slotIndex) {
			case SLOT_INPUT -> IIndividualHandlerItem.isIndividual(stack);
			case SLOT_BLANK_SAMPLE -> stack.is(GItems.RESOURCE.item(GendustryResourceType.BLANK_GENE_SAMPLE));
			case SLOT_LABWARE -> stack.is(GItems.RESOURCE.item(GendustryResourceType.LABWARE));
			default -> false;
		};
	}

	@Override
	public boolean canTakeItemThroughFace(int slotIndex, ItemStack stack, Direction side) {
		return slotIndex == SLOT_OUTPUT;
	}
}
