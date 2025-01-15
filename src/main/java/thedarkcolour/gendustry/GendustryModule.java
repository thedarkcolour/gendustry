package thedarkcolour.gendustry;

import java.util.function.Consumer;

import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.eventbus.api.IEventBus;

import forestry.api.client.IClientModuleHandler;
import forestry.api.modules.ForestryModule;
import forestry.api.modules.IForestryModule;

import thedarkcolour.gendustry.client.ClientHandler;
import thedarkcolour.gendustry.recipe.cache.DnaRecipeCache;
import thedarkcolour.gendustry.recipe.cache.MutagenRecipeCache;
import thedarkcolour.gendustry.recipe.cache.ProteinRecipeCache;
import thedarkcolour.gendustry.recipe.cache.RecipeCacheRegistry;

@ForestryModule
public class GendustryModule implements IForestryModule {
	public static final ResourceLocation MODULE_ID = Gendustry.loc("core");

	@Override
	public void registerEvents(IEventBus modBus) {
		// Recipe caching
		new RecipeCacheRegistry(registrar -> {
			registrar.accept(MutagenRecipeCache.INSTANCE);
			registrar.accept(DnaRecipeCache.INSTANCE);
			registrar.accept(ProteinRecipeCache.INSTANCE);
		});
	}

	@Override
	public ResourceLocation getId() {
		return GendustryModule.MODULE_ID;
	}

	@Override
	public void registerClientHandler(Consumer<IClientModuleHandler> registrar) {
		registrar.accept(new ClientHandler());
	}
}
