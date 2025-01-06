package thedarkcolour.gendustry.compat.jei;

import net.minecraft.resources.ResourceLocation;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.ISubtypeRegistration;
import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.registry.GItems;

@JeiPlugin
public class GendustryJeiPlugin implements IModPlugin {
	@Override
	public ResourceLocation getPluginUid() {
		return Gendustry.MODULE_ID;
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		registration.registerSubtypeInterpreter(GItems.GENE_SAMPLE.item(), new GeneSampleInterpreter());
	}
}
