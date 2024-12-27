package thedarkcolour.gendustry.recipe.cache;

import java.util.IdentityHashMap;

import net.minecraft.world.item.crafting.RecipeManager;

import forestry.api.genetics.ILifeStage;
import forestry.core.utils.RecipeUtils;

import thedarkcolour.gendustry.recipe.DnaRecipe;
import thedarkcolour.gendustry.registry.GRecipeTypes;
import org.jetbrains.annotations.Nullable;

public enum DnaRecipeCache implements IRecipeCache {
	INSTANCE;

	private IdentityHashMap<ILifeStage, DnaRecipe> recipes = new IdentityHashMap<>();

	@Nullable
	public DnaRecipe getRecipe(ILifeStage stage) {
		return this.recipes.get(stage);
	}


	@Override
	public void reload(RecipeManager recipes) {
		IdentityHashMap<ILifeStage, DnaRecipe> builder = new IdentityHashMap<>();

		RecipeUtils.getRecipes(recipes, GRecipeTypes.DNA).forEach(recipe -> builder.put(recipe.getStage(), recipe));

		this.recipes = builder;
	}

	@Override
	public void unload() {
		this.recipes = new IdentityHashMap<>();
	}
}
