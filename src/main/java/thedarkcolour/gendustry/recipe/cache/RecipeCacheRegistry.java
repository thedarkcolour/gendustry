package thedarkcolour.gendustry.recipe.cache;

import com.google.common.collect.ImmutableSet;

import java.util.function.Consumer;

import net.minecraft.util.Unit;
import net.minecraft.world.item.crafting.RecipeManager;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;

import net.minecraftforge.fml.loading.FMLEnvironment;

import forestry.core.ClientsideCode;

/**
 * Register your {@link IRecipeCache} recipe caches here so that they are properly reloaded.
 * To use, create a new instance in your mod constructor. No need to store the created instance.
 * <p>
 * Code is adapted from: <a href="https://github.com/thedarkcolour/ExDeorum/blob/1.20.1/src/main/java/thedarkcolour/exdeorum/recipe/RecipeUtil.java">Ex Deorum</a>
 */
public final class RecipeCacheRegistry {
	private final ImmutableSet<IRecipeCache> caches;
	private boolean needsReload;

	public RecipeCacheRegistry(Consumer<Consumer<IRecipeCache>> registerCaches) {
		ImmutableSet.Builder<IRecipeCache> builder = ImmutableSet.builder();
		registerCaches.accept(builder::add);
		this.caches = builder.build();

		MinecraftForge.EVENT_BUS.addListener((ServerStoppingEvent event) -> unload());
		MinecraftForge.EVENT_BUS.addListener((AddReloadListenerEvent event) -> {
			RecipeManager recipes = event.getServerResources().getRecipeManager();
			event.addListener((prepBarrier, resourceManager, prepProfiler, reloadProfiler, backgroundExecutor, gameExecutor) ->
					prepBarrier
							.wait(Unit.INSTANCE)
							.thenRunAsync(() -> reload(recipes), gameExecutor)
			);
		});

		if (FMLEnvironment.dist == Dist.CLIENT) {
			MinecraftForge.EVENT_BUS.addListener((TagsUpdatedEvent event) -> {
				RecipeManager recipes = ClientsideCode.getRecipeManager();

				if (this.needsReload && recipes != null) {
					reload(recipes);
					this.needsReload = false;
				}
			});
		}

	}

	private void reload(RecipeManager recipes) {
		for (IRecipeCache cache : this.caches) {
			cache.reload(recipes);
		}
	}

	private void unload() {
		for (IRecipeCache cache : this.caches) {
			cache.unload();
		}
	}
}
