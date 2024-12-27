package thedarkcolour.gendustry.recipe;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import thedarkcolour.gendustry.registry.GRecipeTypes;
import org.jetbrains.annotations.Nullable;

public class ProteinRecipe extends ProcessorRecipe {
	private final Ingredient ingredient;

	public ProteinRecipe(ResourceLocation id, Ingredient ingredient, int amount) {
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
		return GRecipeTypes.PROTEIN.serializer();
	}

	@Override
	public RecipeType<?> getType() {
		return GRecipeTypes.PROTEIN.type();
	}

	public static class Serializer implements RecipeSerializer<ProteinRecipe> {
		@Override
		public ProteinRecipe fromJson(ResourceLocation id, JsonObject json) {
			Ingredient ingredient = Ingredient.fromJson(json.get("ingredient"));
			int amount = GsonHelper.getAsInt(json, "amount");

			return new ProteinRecipe(id, ingredient, amount);
		}

		@Override
		public @Nullable ProteinRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf data) {
			Ingredient ingredient = Ingredient.fromNetwork(data);
			int amount = data.readShort();
			return new ProteinRecipe(id, ingredient, amount);
		}

		@Override
		public void toNetwork(FriendlyByteBuf data, ProteinRecipe recipe) {
			recipe.ingredient.toNetwork(data);
			data.writeShort(recipe.amount);
		}
	}
}
