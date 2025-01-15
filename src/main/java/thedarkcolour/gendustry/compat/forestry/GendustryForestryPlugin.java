package thedarkcolour.gendustry.compat.forestry;

import net.minecraft.resources.ResourceLocation;

import forestry.api.plugin.IErrorRegistration;
import forestry.api.plugin.IForestryPlugin;

import thedarkcolour.gendustry.GendustryModule;

public class GendustryForestryPlugin implements IForestryPlugin {
	@Override
	public ResourceLocation id() {
		return GendustryModule.MODULE_ID;
	}

	@Override
	public void registerErrors(IErrorRegistration errors) {
		for (GendustryError error : GendustryError.values()) {
			errors.registerError(error);
		}
	}
}
