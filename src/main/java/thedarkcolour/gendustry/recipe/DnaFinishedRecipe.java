package thedarkcolour.gendustry.recipe;

import com.google.gson.JsonObject;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

import forestry.api.genetics.ILifeStage;
import forestry.api.genetics.ISpeciesType;

import thedarkcolour.gendustry.registry.GRecipeTypes;
import org.jetbrains.annotations.Nullable;

public class DnaFinishedRecipe implements FinishedRecipe {
	private final ResourceLocation id;
	private final ISpeciesType<?, ?> speciesType;
	private final ILifeStage stage;
	private final int amount;

	public DnaFinishedRecipe(ResourceLocation id, ISpeciesType<?, ?> speciesType, ILifeStage stage, int amount) {
		this.id = id;
		this.speciesType = speciesType;
		this.stage = stage;
		this.amount = amount;
	}

	@Override
	public void serializeRecipeData(JsonObject json) {
		json.addProperty("species_type", this.speciesType.id().toString());
		json.addProperty("stage", this.stage.getSerializedName());
		json.addProperty("amount", this.amount);
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public RecipeSerializer<?> getType() {
		return GRecipeTypes.DNA.serializer();
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
