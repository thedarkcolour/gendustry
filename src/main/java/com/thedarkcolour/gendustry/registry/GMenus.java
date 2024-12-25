package com.thedarkcolour.gendustry.registry;

import forestry.modules.features.FeatureMenuType;
import forestry.modules.features.FeatureProvider;
import forestry.modules.features.IFeatureRegistry;
import forestry.modules.features.ModFeatureRegistry;

import com.thedarkcolour.gendustry.Gendustry;
import com.thedarkcolour.gendustry.menu.MutagenProducerMenu;

@FeatureProvider
public class GMenus {
	private static final IFeatureRegistry REGISTRY = ModFeatureRegistry.get(Gendustry.MODULE_ID);

	public static final FeatureMenuType<MutagenProducerMenu> MUTAGEN_PRODUCER = REGISTRY.menuType(MutagenProducerMenu::fromNetwork, "mutagen_producer");
}
