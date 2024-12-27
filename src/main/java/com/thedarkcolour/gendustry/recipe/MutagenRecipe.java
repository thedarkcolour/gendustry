package com.thedarkcolour.gendustry.recipe;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import com.thedarkcolour.gendustry.registry.GRecipeTypes;
import org.jetbrains.annotations.Nullable;

public class MutagenRecipe extends ProcessorRecipe {
	private final Ingredient ingredient;

	public MutagenRecipe(ResourceLocation id, Ingredient ingredient, int amount) {
		super(id, amount);
		this.ingredient = ingredient;
	}

	public Ingredient getIngredient() {
		return this.ingredient;
	}

	@Override
	public boolean isIngredient(ItemStack stack) {
		return this.ingredient.test(stack);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return GRecipeTypes.MUTAGEN.serializer();
	}

	@Override
	public RecipeType<?> getType() {
		return GRecipeTypes.MUTAGEN.type();
	}

	public static class Serializer implements RecipeSerializer<MutagenRecipe> {
		@Override
		public MutagenRecipe fromJson(ResourceLocation id, JsonObject json) {
			Ingredient ingredient = Ingredient.fromJson(json.get("ingredient"));
			int amount = GsonHelper.getAsInt(json, "amount");

			return new MutagenRecipe(id, ingredient, amount);
		}

		@Override
		public @Nullable MutagenRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf data) {
			Ingredient ingredient = Ingredient.fromNetwork(data);
			int amount = data.readShort();
			return new MutagenRecipe(id, ingredient, amount);
		}

		@Override
		public void toNetwork(FriendlyByteBuf data, MutagenRecipe recipe) {
			recipe.ingredient.toNetwork(data);
			data.writeShort(recipe.amount);
		}
	}
}
