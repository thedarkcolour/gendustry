package thedarkcolour.gendustry.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

import net.minecraftforge.registries.RegistryObject;

import forestry.modules.features.FeatureProvider;
import forestry.modules.features.FeatureRecipeType;
import forestry.modules.features.IFeatureRegistry;
import forestry.modules.features.ModFeatureRegistry;

import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.recipe.DnaRecipe;
import thedarkcolour.gendustry.recipe.GeneticTemplateRecipe;
import thedarkcolour.gendustry.recipe.MutagenRecipe;
import thedarkcolour.gendustry.recipe.ProteinRecipe;

@FeatureProvider
public class GRecipeTypes {
	private static final IFeatureRegistry REGISTRY = ModFeatureRegistry.get(Gendustry.MODULE_ID);

	public static final FeatureRecipeType<MutagenRecipe> MUTAGEN = REGISTRY.recipeType("mutagen", MutagenRecipe.Serializer::new);
	public static final FeatureRecipeType<ProteinRecipe> PROTEIN = REGISTRY.recipeType("protein", ProteinRecipe.Serializer::new);
	public static final FeatureRecipeType<DnaRecipe> DNA = REGISTRY.recipeType("dna", DnaRecipe.Serializer::new);

	public static final RegistryObject<SimpleCraftingRecipeSerializer<?>> GENETIC_TEMPLATE_SERIALIZER = REGISTRY.getRegistry(Registries.RECIPE_SERIALIZER).register("genetic_template", () -> new SimpleCraftingRecipeSerializer<>(GeneticTemplateRecipe::new));
}
