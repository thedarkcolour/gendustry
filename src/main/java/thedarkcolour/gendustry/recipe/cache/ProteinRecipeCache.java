package thedarkcolour.gendustry.recipe.cache;

import com.google.common.collect.ImmutableList;

import java.util.IdentityHashMap;
import java.util.List;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;

import forestry.core.utils.RecipeUtils;

import thedarkcolour.gendustry.recipe.ProteinRecipe;
import thedarkcolour.gendustry.registry.GRecipeTypes;
import org.jetbrains.annotations.Nullable;

// Copy of MutagenRecipeCache, was too lazy to generify that one
public enum ProteinRecipeCache implements IRecipeCache {
	INSTANCE;

	// Does not depend on NBT
	private IdentityHashMap<Item, ProteinRecipe> simple = new IdentityHashMap<>();
	// Depends on NBT
	private List<ProteinRecipe> complex = List.of();

	@Nullable
	public ProteinRecipe getRecipe(ItemStack stack) {
		ProteinRecipe recipe = this.simple.get(stack.getItem());

		if (recipe == null) {
			for (ProteinRecipe r : this.complex) {
				if (r.getIngredient().test(stack)) {
					return r;
				}
			}
		}

		return recipe;
	}

	@Override
	public void reload(RecipeManager recipes) {
		IdentityHashMap<Item, ProteinRecipe> simpleBuilder = new IdentityHashMap<>();
		ImmutableList.Builder<ProteinRecipe> complexBuilder = ImmutableList.builder();

		RecipeUtils.getRecipes(recipes, GRecipeTypes.PROTEIN).forEach(recipe -> {
			Ingredient ingredient = recipe.getIngredient();

			if (ingredient.isSimple()) {
				for (ItemStack stack : ingredient.getItems()) {
					simpleBuilder.put(stack.getItem(), recipe);
				}
			} else {
				complexBuilder.add(recipe);
			}
		});

		this.simple = simpleBuilder;
		this.complex = complexBuilder.build();
	}

	@Override
	public void unload() {
		this.simple = new IdentityHashMap<>();
		this.complex = List.of();
	}
}
