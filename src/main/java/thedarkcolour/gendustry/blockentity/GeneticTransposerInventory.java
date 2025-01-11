package thedarkcolour.gendustry.blockentity;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import forestry.core.inventory.InventoryAdapterTile;

import thedarkcolour.gendustry.item.GendustryResourceType;
import thedarkcolour.gendustry.registry.GItems;

public class GeneticTransposerInventory extends InventoryAdapterTile<GeneticTransposerBlockEntity> {
	public static final int SLOT_INPUT = SamplerInventory.SLOT_INPUT;
	public static final int SLOT_SOURCE = SamplerInventory.SLOT_BLANK_SAMPLE;
	public static final int SLOT_LABWARE = SamplerInventory.SLOT_LABWARE;
	public static final int SLOT_OUTPUT = SamplerInventory.SLOT_OUTPUT;

	public GeneticTransposerInventory(GeneticTransposerBlockEntity tile) {
		super(tile, 4, "items");
	}

	@Override
	public boolean canSlotAccept(int slotIndex, ItemStack stack) {
		return switch (slotIndex) {
			case SLOT_INPUT -> {
				Item blankTemplate = GItems.RESOURCE.item(GendustryResourceType.BLANK_GENETIC_TEMPLATE);
				Item blankSample = GItems.RESOURCE.item(GendustryResourceType.BLANK_GENE_SAMPLE);
				ItemStack source = getItem(SLOT_SOURCE);

				if (source.is(GItems.GENETIC_TEMPLATE.item())) {
					yield stack.is(blankTemplate);
				} else if (source.is(GItems.GENE_SAMPLE.item())) {
					yield stack.is(blankSample);
				} else {
					yield stack.is(blankTemplate) || stack.is(blankSample);
				}
			}
			case SLOT_SOURCE -> {
				ItemStack blank = getItem(SLOT_INPUT);

				if (blank.is(GItems.RESOURCE.item(GendustryResourceType.BLANK_GENETIC_TEMPLATE))) {
					yield stack.is(GItems.GENETIC_TEMPLATE.item());
				} else if (blank.is(GItems.RESOURCE.item(GendustryResourceType.BLANK_GENE_SAMPLE))) {
					yield stack.is(GItems.GENE_SAMPLE.item());
				} else {
					yield stack.is(GItems.GENETIC_TEMPLATE.item()) || stack.is(GItems.GENE_SAMPLE.item());
				}
			}
			case SLOT_LABWARE -> stack.is(GItems.RESOURCE.item(GendustryResourceType.LABWARE));
			default -> false;
		};
	}
}
