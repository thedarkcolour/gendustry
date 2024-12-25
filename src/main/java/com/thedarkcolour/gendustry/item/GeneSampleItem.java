package com.thedarkcolour.gendustry.item;

import net.minecraft.world.item.Item;

import com.thedarkcolour.gendustry.registry.GItems;

public class GeneSampleItem extends Item {
	public GeneSampleItem() {
		super(new Item.Properties().stacksTo(1).craftRemainder(GItems.BLANK_GENE_SAMPLE.item()));
	}
}
