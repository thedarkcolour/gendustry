package com.thedarkcolour.gendustry.recipe.cache;

import net.minecraft.world.item.crafting.RecipeManager;

/**
 * A recipe cache is an object responsible for fast, map-based recipe lookup.
 * To use properly, use a {@link RecipeCacheRegistry}.
 */
public interface IRecipeCache {
	/**
	 * Called whenever recipes are loaded/reloaded so that caches have up-to-date information.
	 *
	 * @param recipes The recipe manager containing all currently loaded recipes.
	 */
	void reload(RecipeManager recipes);

	/**
	 * Used to clean up caches such as maps after a world is closed.
	 */
	void unload();
}
