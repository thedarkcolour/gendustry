package thedarkcolour.gendustry.item;

import net.minecraft.world.item.Item;

import thedarkcolour.gendustry.registry.GItems;

public class GeneSampleItem extends Item {
	public GeneSampleItem() {
		super(new Item.Properties().stacksTo(1).craftRemainder(GItems.RESOURCE.item(GendustryResourceType.BLANK_GENE_SAMPLE)));
	}
}
