package thedarkcolour.gendustry.registry;

import net.minecraft.world.item.Item;

import forestry.modules.features.FeatureItem;
import forestry.modules.features.FeatureItemGroup;
import forestry.modules.features.FeatureProvider;
import forestry.modules.features.IFeatureRegistry;
import forestry.modules.features.ModFeatureRegistry;

import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.item.GendustryResourceType;
import thedarkcolour.gendustry.item.GeneSampleItem;
import thedarkcolour.gendustry.item.PollenKitItem;

@FeatureProvider
public class GItems {
	private static final IFeatureRegistry REGISTRY = ModFeatureRegistry.get(Gendustry.MODULE_ID);

	public static final FeatureItemGroup<Item, GendustryResourceType> RESOURCE = REGISTRY.itemGroup(subtype -> new Item(new Item.Properties()), GendustryResourceType.values()).create();
	public static final FeatureItem<Item> POLLEN_KIT = REGISTRY.item(PollenKitItem::new, "pollen_kit");
	public static final FeatureItem<Item> GENE_SAMPLE = REGISTRY.item(GeneSampleItem::new, "gene_sample");
	//public static final FeatureItem<Item> INDUSTRIAL_SCOOP = REGISTRY.item(() -> new IndustrialScoopItem(), "industrial_scoop");
	//public static final FeatureItem<Item> INDUSTRIAL_GRAFTER = REGISTRY.item(() -> new IndustrialGrafterItem(), "industrial_grafter");
}
