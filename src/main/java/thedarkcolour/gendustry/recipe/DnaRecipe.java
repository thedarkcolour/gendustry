package thedarkcolour.gendustry.recipe;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import forestry.api.IForestryApi;
import forestry.api.genetics.ILifeStage;
import forestry.api.genetics.ISpeciesType;
import forestry.api.genetics.capability.IIndividualHandlerItem;

import thedarkcolour.gendustry.registry.GRecipeTypes;
import org.jetbrains.annotations.Nullable;

public class DnaRecipe extends ProcessorRecipe {
	private final ISpeciesType<?, ?> speciesType;
	private final ILifeStage stage;

	public DnaRecipe(ResourceLocation id, ISpeciesType<?, ?> speciesType, ILifeStage stage, int amount) {
		super(id, amount);

		this.speciesType = speciesType;
		this.stage = stage;
	}

	public ISpeciesType<?, ?> getSpeciesType() {
		return this.speciesType;
	}

	public ILifeStage getStage() {
		return this.stage;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return GRecipeTypes.DNA.serializer();
	}

	@Override
	public RecipeType<?> getType() {
		return GRecipeTypes.DNA.type();
	}

	@Override
	public boolean isIngredient(ItemStack stack) {
		return IIndividualHandlerItem.filter(stack, (i, stage) -> stage == this.stage);
	}

	public static class Serializer implements RecipeSerializer<DnaRecipe> {
		@Override
		public DnaRecipe fromJson(ResourceLocation id, JsonObject json) {
			ResourceLocation speciesTypeId = new ResourceLocation(GsonHelper.getAsString(json, "species_type"));
			String stageName = GsonHelper.getAsString(json, "stage");
			int amount = GsonHelper.getAsInt(json, "amount");

			ISpeciesType<?, ?> speciesType = IForestryApi.INSTANCE.getGeneticManager().getSpeciesType(speciesTypeId);
			ILifeStage stage = null;
			for (ILifeStage s : speciesType.getLifeStages()) {
				if (s.getSerializedName().equals(stageName)) {
					stage = s;
					break;
				}
			}
			if (stage == null) {
				throw new IllegalStateException("No such life stage " + stageName + " for species type " + speciesTypeId);
			}

			return new DnaRecipe(id, speciesType, stage, amount);
		}

		@Override
		public @Nullable DnaRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf data) {
			ISpeciesType<?, ?> speciesType = IForestryApi.INSTANCE.getGeneticManager().getSpeciesType(data.readResourceLocation());

			// todo LifeStage API needs to be redesigned for better serialization
			ILifeStage stage = null;
			String stageName = data.readUtf();
			for (ILifeStage s : speciesType.getLifeStages()) {
				if (s.getSerializedName().equals(stageName)) {
					stage = s;
					break;
				}
			}
			if (stage == null) {
				return null;
			}
			int amount = data.readShort();

			return new DnaRecipe(id, speciesType, stage, amount);
		}

		@Override
		public void toNetwork(FriendlyByteBuf data, DnaRecipe recipe) {
			data.writeResourceLocation(recipe.speciesType.id());
			data.writeUtf(recipe.stage.getSerializedName());
			data.writeShort(recipe.amount);
		}
	}
}
