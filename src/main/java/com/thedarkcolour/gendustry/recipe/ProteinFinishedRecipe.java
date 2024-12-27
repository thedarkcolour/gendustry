package com.thedarkcolour.gendustry.recipe;

import com.google.gson.JsonObject;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import com.thedarkcolour.gendustry.registry.GRecipeTypes;
import org.jetbrains.annotations.Nullable;

// Using AI to write this class is the real data generation
public class ProteinFinishedRecipe implements FinishedRecipe {
	private final ResourceLocation id;
	private final Ingredient ingredient;
	private final int amount;

	public ProteinFinishedRecipe(ResourceLocation id, Ingredient ingredient, int amount) {
		this.id = id;
		this.ingredient = ingredient;
		this.amount = amount;
	}

	@Override
	public void serializeRecipeData(JsonObject json) {
		json.add("ingredient", this.ingredient.toJson());
		json.addProperty("amount", this.amount);
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public RecipeSerializer<?> getType() {
		return GRecipeTypes.PROTEIN.serializer();
	}

	@Nullable
	@Override
	public JsonObject serializeAdvancement() {
		return null;
	}

	@Nullable
	@Override
	public ResourceLocation getAdvancementId() {
		return null;
	}
}
