package com.thedarkcolour.gendustry;

import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraftforge.fml.common.Mod;

import forestry.api.modules.ForestryModule;
import forestry.api.modules.IForestryModule;

import com.thedarkcolour.gendustry.registry.GBlockEntities;
import com.thedarkcolour.gendustry.registry.GBlocks;
import com.thedarkcolour.gendustry.registry.GItems;

@Mod(Gendustry.ID)
@ForestryModule
public class Gendustry implements IForestryModule {
	public static final String ID = "gendustry";
	public static final ResourceLocation MODULE_ID = new ResourceLocation(ID, "core");

	@Override
	public ResourceLocation getId() {
		return MODULE_ID;
	}
}