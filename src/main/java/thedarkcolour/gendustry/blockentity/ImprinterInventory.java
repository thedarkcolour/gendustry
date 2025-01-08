package thedarkcolour.gendustry.blockentity;

import net.minecraft.world.item.ItemStack;

import forestry.api.genetics.capability.IIndividualHandlerItem;
import forestry.core.inventory.InventoryAdapterTile;

import thedarkcolour.gendustry.item.GendustryResourceType;
import thedarkcolour.gendustry.item.GeneticTemplateItem;
import thedarkcolour.gendustry.registry.GItems;

public class ImprinterInventory extends InventoryAdapterTile<ImprinterBlockEntity> {
	// The SamplerImprinterMenu uses slot indices from the sampler inventory, so these must correspond to them
	public static final int SLOT_INPUT = SamplerInventory.SLOT_INPUT;
	public static final int SLOT_TEMPLATE = SamplerInventory.SLOT_BLANK_SAMPLE;
	public static final int SLOT_LABWARE = SamplerInventory.SLOT_LABWARE;
	public static final int SLOT_OUTPUT = SamplerInventory.SLOT_OUTPUT;

	public ImprinterInventory(ImprinterBlockEntity tile) {
		super(tile, 4, "items");
	}

	@Override
	public boolean canSlotAccept(int slotIndex, ItemStack stack) {
		return switch (slotIndex) {
			case SLOT_INPUT -> IIndividualHandlerItem.isIndividual(stack);
			case SLOT_TEMPLATE -> stack.is(GItems.GENETIC_TEMPLATE.item()) && GeneticTemplateItem.isComplete(stack);
			case SLOT_LABWARE -> stack.is(GItems.RESOURCE.item(GendustryResourceType.LABWARE));
			default -> false;
		};
	}
}
