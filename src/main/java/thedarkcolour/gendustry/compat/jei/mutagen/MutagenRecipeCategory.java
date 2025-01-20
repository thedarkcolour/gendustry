package thedarkcolour.gendustry.compat.jei.mutagen;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.ItemStack;
import thedarkcolour.gendustry.block.GendustryMachineType;
import thedarkcolour.gendustry.compat.jei.GendustryRecipeType;
import thedarkcolour.gendustry.compat.jei.ProducerRecipeCategory;
import thedarkcolour.gendustry.recipe.MutagenRecipe;
import thedarkcolour.gendustry.registry.GBlocks;
import thedarkcolour.gendustry.registry.GFluids;

public class MutagenRecipeCategory extends ProducerRecipeCategory<MutagenRecipe> {

    public static final ItemStack ICON_STACK = new ItemStack(GBlocks.MACHINE.get(GendustryMachineType.MUTAGEN_PRODUCER).block());

    public MutagenRecipeCategory(IGuiHelper helper) {
        super(helper, "block.gendustry.mutagen_producer", ICON_STACK);
    }

    @Override
    public RecipeType<MutagenRecipe> getRecipeType() {
        return GendustryRecipeType.MUTAGEN_PRODUCER;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MutagenRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 23).addIngredients(recipe.getIngredient());
        addFluidTank(builder, GFluids.MUTAGEN.fluid(), recipe.getAmount());
    }
}
