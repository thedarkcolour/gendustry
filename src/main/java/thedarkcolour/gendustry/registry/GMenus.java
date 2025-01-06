package thedarkcolour.gendustry.registry;

import forestry.modules.features.FeatureMenuType;
import forestry.modules.features.FeatureProvider;
import forestry.modules.features.IFeatureRegistry;
import forestry.modules.features.ModFeatureRegistry;

import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.menu.ProcessorMenu;
import thedarkcolour.gendustry.menu.SamplerMenu;

@FeatureProvider
public class GMenus {
	private static final IFeatureRegistry REGISTRY = ModFeatureRegistry.get(Gendustry.MODULE_ID);

	public static final FeatureMenuType<ProcessorMenu> PROCESSOR = REGISTRY.menuType(ProcessorMenu::fromNetwork, "processor");
	public static final FeatureMenuType<SamplerMenu> SAMPLER = REGISTRY.menuType(SamplerMenu::fromNetwork, "sampler");
}
