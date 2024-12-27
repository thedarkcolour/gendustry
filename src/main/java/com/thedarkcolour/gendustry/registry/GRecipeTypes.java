package com.thedarkcolour.gendustry.registry;

import forestry.modules.features.FeatureProvider;
import forestry.modules.features.FeatureRecipeType;
import forestry.modules.features.IFeatureRegistry;
import forestry.modules.features.ModFeatureRegistry;

import com.thedarkcolour.gendustry.Gendustry;
import com.thedarkcolour.gendustry.recipe.DnaRecipe;
import com.thedarkcolour.gendustry.recipe.MutagenRecipe;
import com.thedarkcolour.gendustry.recipe.ProteinRecipe;

@FeatureProvider
public class GRecipeTypes {
	private static final IFeatureRegistry REGISTRY = ModFeatureRegistry.get(Gendustry.MODULE_ID);

	public static final FeatureRecipeType<MutagenRecipe> MUTAGEN = REGISTRY.recipeType("mutagen", MutagenRecipe.Serializer::new);
	public static final FeatureRecipeType<ProteinRecipe> PROTEIN = REGISTRY.recipeType("protein", ProteinRecipe.Serializer::new);
	public static final FeatureRecipeType<DnaRecipe> DNA = REGISTRY.recipeType("dna", DnaRecipe.Serializer::new);
}
