package com.thedarkcolour.gendustry.item;

import com.thedarkcolour.gendustry.registry.GItems;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GeneSampleItem extends Item {
    public GeneSampleItem() {
        super(new Item.Properties().stacksTo(1).craftRemainder(GItems.BLANK_GENE_SAMPLE.item()));
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
        super.fillItemCategory(tab, items);
    }
}
