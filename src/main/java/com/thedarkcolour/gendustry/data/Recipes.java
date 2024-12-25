package com.thedarkcolour.gendustry.data;

import java.util.function.Consumer;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import com.thedarkcolour.gendustry.Gendustry;
import com.thedarkcolour.gendustry.recipe.MutagenFinishedRecipe;
import thedarkcolour.modkit.data.MKRecipeProvider;

class Recipes {
	public static void addRecipes(Consumer<FinishedRecipe> writer, MKRecipeProvider recipes) {
		// Mutagen recipes
		mutagen(writer, Items.REDSTONE, 100);
		mutagen(writer, Items.GLOWSTONE_DUST, 200);
		mutagen(writer, Items.GLOWSTONE, 800);
		mutagen(writer, Items.REDSTONE_BLOCK, 900);
		// todo Yellorium and Uranium
	}

	private static void mutagen(Consumer<FinishedRecipe> writer, Item input, int mutagen) {
		writer.accept(new MutagenFinishedRecipe(Gendustry.loc("mutagen/" + MKRecipeProvider.path(input)), Ingredient.of(input), mutagen));
	}
}
