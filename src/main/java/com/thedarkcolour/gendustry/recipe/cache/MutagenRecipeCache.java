package com.thedarkcolour.gendustry.recipe.cache;

import com.google.common.collect.ImmutableList;

import java.util.IdentityHashMap;
import java.util.List;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;

import forestry.core.utils.RecipeUtils;

import com.thedarkcolour.gendustry.recipe.MutagenRecipe;
import com.thedarkcolour.gendustry.registry.GRecipeTypes;
import org.jetbrains.annotations.Nullable;

public enum MutagenRecipeCache implements IRecipeCache {
	INSTANCE;

	// Does not depend on NBT
	private IdentityHashMap<Item, MutagenRecipe> simple;
	// Depends on NBT
	private List<MutagenRecipe> complex;

	@Nullable
	public MutagenRecipe getRecipe(ItemStack stack) {
		MutagenRecipe recipe = this.simple.get(stack.getItem());

		if (recipe == null) {
			for (MutagenRecipe r : this.complex) {
				if (r.getIngredient().test(stack)) {
					return r;
				}
			}
		}

		return recipe;
	}

	@Override
	public void reload(RecipeManager recipes) {
		IdentityHashMap<Item, MutagenRecipe> simpleBuilder = new IdentityHashMap<>();
		ImmutableList.Builder<MutagenRecipe> complexBuilder = ImmutableList.builder();

		RecipeUtils.getRecipes(recipes, GRecipeTypes.MUTAGEN).forEach(recipe -> {
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
