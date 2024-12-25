package com.thedarkcolour.gendustry;

import java.util.function.Consumer;

import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.fml.common.Mod;

import forestry.api.client.IClientModuleHandler;
import forestry.api.modules.ForestryModule;
import forestry.api.modules.IForestryModule;

import com.thedarkcolour.gendustry.client.ClientHandler;
import com.thedarkcolour.gendustry.recipe.cache.MutagenRecipeCache;
import com.thedarkcolour.gendustry.recipe.cache.RecipeCacheRegistry;

@Mod(Gendustry.ID)
@ForestryModule
public class Gendustry implements IForestryModule {
	public static final String ID = "gendustry";
	public static final ResourceLocation MODULE_ID = loc("core");

	public Gendustry() {
		// Recipe caching
		new RecipeCacheRegistry(registrar -> registrar.accept(MutagenRecipeCache.INSTANCE));
	}

	@Override
	public ResourceLocation getId() {
		return MODULE_ID;
	}

	@Override
	public void registerClientHandler(Consumer<IClientModuleHandler> registrar) {
		registrar.accept(new ClientHandler());
	}

	public static ResourceLocation loc(String path) {
		return new ResourceLocation(ID, path);
	}
}