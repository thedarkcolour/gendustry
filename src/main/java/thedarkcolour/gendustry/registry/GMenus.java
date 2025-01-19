package thedarkcolour.gendustry.registry;

import forestry.modules.features.FeatureMenuType;
import forestry.modules.features.FeatureProvider;
import forestry.modules.features.IFeatureRegistry;
import forestry.modules.features.ModFeatureRegistry;

import thedarkcolour.gendustry.GendustryModule;
import thedarkcolour.gendustry.blockentity.IHintTile;
import thedarkcolour.gendustry.menu.AdvancedMutatronMenu;
import thedarkcolour.gendustry.menu.IndustrialApiaryMenu;
import thedarkcolour.gendustry.menu.MutatronMenu;
import thedarkcolour.gendustry.menu.ProducerMenu;
import thedarkcolour.gendustry.menu.ReplicatorMenu;
import thedarkcolour.gendustry.menu.ThreeInputMenu;

@FeatureProvider
public class GMenus {
	private static final IFeatureRegistry REGISTRY = ModFeatureRegistry.get(GendustryModule.MODULE_ID);

	// todo rename to "producer" in 1.21
	public static final FeatureMenuType<ProducerMenu> PROCESSOR = REGISTRY.menuType(ProducerMenu::fromNetwork, "processor");
	public static final FeatureMenuType<ThreeInputMenu<? extends IHintTile>> SAMPLER = REGISTRY.menuType(ThreeInputMenu::samplerFromNetwork, "sampler");
	public static final FeatureMenuType<ThreeInputMenu<? extends IHintTile>> IMPRINTER = REGISTRY.menuType(ThreeInputMenu::imprinterFromNetwork, "imprinter");
	public static final FeatureMenuType<ThreeInputMenu<? extends IHintTile>> GENETIC_TRANSPOSER = REGISTRY.menuType(ThreeInputMenu::geneticTransposerFromNetwork, "genetic_transposer");
	public static final FeatureMenuType<MutatronMenu> MUTATRON = REGISTRY.menuType(MutatronMenu::fromNetwork, "mutatron");
	public static final FeatureMenuType<AdvancedMutatronMenu> ADVANCED_MUTATRON = REGISTRY.menuType(AdvancedMutatronMenu::fromNetwork, "advanced_mutatron");
	public static final FeatureMenuType<ReplicatorMenu> REPLICATOR = REGISTRY.menuType(ReplicatorMenu::fromNetwork, "replicator");
	public static final FeatureMenuType<IndustrialApiaryMenu> INDUSTRIAL_APIARY = REGISTRY.menuType(IndustrialApiaryMenu::fromNetwork, "industrial_apiary");
}
