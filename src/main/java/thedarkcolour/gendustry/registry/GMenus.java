package thedarkcolour.gendustry.registry;

import forestry.modules.features.FeatureMenuType;
import forestry.modules.features.FeatureProvider;
import forestry.modules.features.IFeatureRegistry;
import forestry.modules.features.ModFeatureRegistry;

import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.blockentity.IHintKey;
import thedarkcolour.gendustry.menu.AdvancedMutatronMenu;
import thedarkcolour.gendustry.menu.MutatronMenu;
import thedarkcolour.gendustry.menu.ProcessorMenu;
import thedarkcolour.gendustry.menu.ThreeInputMenu;

@FeatureProvider
public class GMenus {
	private static final IFeatureRegistry REGISTRY = ModFeatureRegistry.get(Gendustry.MODULE_ID);

	public static final FeatureMenuType<ProcessorMenu> PROCESSOR = REGISTRY.menuType(ProcessorMenu::fromNetwork, "processor");
	public static final FeatureMenuType<ThreeInputMenu<? extends IHintKey>> SAMPLER = REGISTRY.menuType(ThreeInputMenu::samplerFromNetwork, "sampler");
	public static final FeatureMenuType<ThreeInputMenu<? extends IHintKey>> IMPRINTER = REGISTRY.menuType(ThreeInputMenu::imprinterFromNetwork, "imprinter");
	public static final FeatureMenuType<ThreeInputMenu<? extends IHintKey>> GENETIC_TRANSPOSER = REGISTRY.menuType(ThreeInputMenu::geneticTransposerFromNetwork, "genetic_transposer");
	public static final FeatureMenuType<MutatronMenu> MUTATRON = REGISTRY.menuType(MutatronMenu::fromNetwork, "mutatron");
	public static final FeatureMenuType<AdvancedMutatronMenu> ADVANCED_MUTATRON = REGISTRY.menuType(AdvancedMutatronMenu::fromNetwork, "advanced_mutatron");
}
