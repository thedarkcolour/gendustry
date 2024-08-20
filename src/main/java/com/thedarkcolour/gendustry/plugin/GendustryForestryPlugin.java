package com.thedarkcolour.gendustry.plugin;

import net.minecraft.resources.ResourceLocation;

import forestry.api.plugin.IErrorRegistration;
import forestry.api.plugin.IForestryPlugin;

import com.thedarkcolour.gendustry.Gendustry;

public class GendustryForestryPlugin implements IForestryPlugin {
	@Override
	public ResourceLocation id() {
		return Gendustry.MODULE_ID;
	}

	@Override
	public void registerErrors(IErrorRegistration errors) {
		errors.registerError(GendustryError.DISABLED);
	}
}
