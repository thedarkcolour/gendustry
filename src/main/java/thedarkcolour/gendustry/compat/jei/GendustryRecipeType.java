package thedarkcolour.gendustry.compat.jei;

import mezz.jei.api.recipe.RecipeType;
import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.recipe.DnaRecipe;
import thedarkcolour.gendustry.recipe.MutagenRecipe;
import thedarkcolour.gendustry.recipe.ProteinRecipe;

public class GendustryRecipeType {
	public static final RecipeType<MutagenRecipe> MUTAGEN_PRODUCER = create("mutagen_producer", MutagenRecipe.class);
	public static final RecipeType<DnaRecipe> DNA_EXTRACTOR = create("dna_extractor", DnaRecipe.class);
	public static final RecipeType<ProteinRecipe> PROTEIN_LIQUEFIER = create("protein_liquefier", ProteinRecipe.class);

	private static <T> RecipeType<T> create(String uid, Class<? extends T> recipeClass) {
		return RecipeType.create(Gendustry.ID, uid, recipeClass);
	}
}
