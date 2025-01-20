package thedarkcolour.gendustry.compat.jei.producers;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import thedarkcolour.gendustry.block.GendustryMachineType;
import thedarkcolour.gendustry.compat.jei.GendustryRecipeType;
import thedarkcolour.gendustry.compat.jei.ProducerRecipeCategory;
import thedarkcolour.gendustry.item.GendustryResourceType;
import thedarkcolour.gendustry.recipe.DnaRecipe;
import thedarkcolour.gendustry.registry.GBlocks;
import thedarkcolour.gendustry.registry.GFluids;
import thedarkcolour.gendustry.registry.GItems;

public class DNAExtractorRecipeCategory extends ProducerRecipeCategory<DnaRecipe> {

    public static final ItemStack ICON_STACK = new ItemStack(GBlocks.MACHINE.get(GendustryMachineType.DNA_EXTRACTOR).block());
    private final IDrawable labwareSlot;

    public DNAExtractorRecipeCategory(IGuiHelper helper) {
        super(helper, "block.gendustry.dna_extractor", ICON_STACK);
        this.labwareSlot = helper.createDrawable(GUI, 176, 78, 18, 18);
    }

    @Override
    public RecipeType<DnaRecipe> getRecipeType() {
        return GendustryRecipeType.DNA_EXTRACTOR;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, DnaRecipe recipe, IFocusGroup iFocusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 23).addItemStack(recipe.getSpeciesType().getDefaultSpecies().createStack(recipe.getStage()));
        addFluidTank(builder, GFluids.LIQUID_DNA.fluid(), recipe.getAmount());
        builder.addSlot(RecipeIngredientRole.INPUT, 51, 1).addItemStack(GItems.RESOURCE.item(GendustryResourceType.LABWARE).getDefaultInstance()).addRichTooltipCallback((recipeSlotView, tooltip) -> {
            tooltip.add(Component.translatable("gen.for.chance", 10).withStyle(ChatFormatting.AQUA));
        });
    }

    @Override
    public void draw(DnaRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        super.draw(recipe, recipeSlotsView, graphics, mouseX, mouseY);
        this.labwareSlot.draw(graphics, 50, 0);
    }
}
