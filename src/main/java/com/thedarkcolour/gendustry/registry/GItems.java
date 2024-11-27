package com.thedarkcolour.gendustry.registry;

import com.thedarkcolour.gendustry.Gendustry;
import com.thedarkcolour.gendustry.item.GeneSampleItem;
import com.thedarkcolour.gendustry.item.PollenKitItem;
import forestry.core.items.ItemForestry;
import forestry.modules.features.FeatureItem;
import forestry.modules.features.IFeatureRegistry;
import forestry.modules.features.ModFeatureRegistry;
import net.minecraft.world.item.Item;

public class GItems {
    private static final IFeatureRegistry REGISTRY = ModFeatureRegistry.get(Gendustry.MODULE_ID);

    public static final FeatureItem<Item> POLLEN_KIT = REGISTRY.item(PollenKitItem::new, "pollen_kit");
    public static final FeatureItem<Item> BLANK_GENE_SAMPLE = REGISTRY.item(ItemForestry::new, "blank_gene_sample");
    public static final FeatureItem<Item> GENE_SAMPLE = REGISTRY.item(GeneSampleItem::new, "gene_sample");
    //public static final FeatureItem<Item> INDUSTRIAL_SCOOP = REGISTRY.item(() -> new IndustrialScoopItem(), "industrial_scoop");
    //public static final FeatureItem<Item> INDUSTRIAL_GRAFTER = REGISTRY.item(() -> new IndustrialGrafterItem(), "industrial_grafter");
}
