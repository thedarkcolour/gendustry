package thedarkcolour.gendustry.compat.jei.producers;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.ItemStack;
import thedarkcolour.gendustry.block.GendustryMachineType;
import thedarkcolour.gendustry.compat.jei.GendustryRecipeType;
import thedarkcolour.gendustry.recipe.ProteinRecipe;
import thedarkcolour.gendustry.registry.GBlocks;
import thedarkcolour.gendustry.registry.GFluids;

public class ProteinProducerRecipeCategory extends ProducerRecipeCategory<ProteinRecipe> {

    public static final ItemStack ICON_STACK = new ItemStack(GBlocks.MACHINE.get(GendustryMachineType.PROTEIN_LIQUEFIER).block());

    public ProteinProducerRecipeCategory(IGuiHelper helper) {
        super(helper, "block.gendustry.protein_liquefier", ICON_STACK);
    }

    @Override
    public RecipeType<ProteinRecipe> getRecipeType() {
        return GendustryRecipeType.PROTEIN_LIQUEFIER;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ProteinRecipe recipe, IFocusGroup iFocusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 23).addIngredients(recipe.getIngredient());
        addFluidTank(builder, GFluids.PROTEIN.fluid(), recipe.getAmount());
    }
}
