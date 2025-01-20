package thedarkcolour.gendustry.compat.jei.dna;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.ItemStack;
import thedarkcolour.gendustry.compat.jei.GendustryRecipeType;
import thedarkcolour.gendustry.compat.jei.ProducerRecipeCategory;
import thedarkcolour.gendustry.recipe.DnaRecipe;

public class DNAExtractorRecipeCategory extends ProducerRecipeCategory<DnaRecipe> {

    public DNAExtractorRecipeCategory(IGuiHelper helper, String unlocalizedName, ItemStack stack) {
        super(helper, unlocalizedName, stack);
    }

    @Override
    public RecipeType<DnaRecipe> getRecipeType() {
        return GendustryRecipeType.DNA_EXTRACTOR;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, DnaRecipe dnaRecipe, IFocusGroup iFocusGroup) {

    }
}
