package thedarkcolour.gendustry.compat.forestry;

import net.minecraft.resources.ResourceLocation;

import forestry.api.plugin.IErrorRegistration;
import forestry.api.plugin.IForestryPlugin;

import thedarkcolour.gendustry.Gendustry;

public class GendustryForestryPlugin implements IForestryPlugin {
	@Override
	public ResourceLocation id() {
		return Gendustry.MODULE_ID;
	}

	@Override
	public void registerErrors(IErrorRegistration errors) {
		for (GendustryError error : GendustryError.values()) {
			errors.registerError(error);
		}
	}
}
