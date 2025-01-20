package thedarkcolour.gendustry.compat.jei;

import forestry.core.recipes.jei.ForestryRecipeCategory;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.recipe.ProcessorRecipe;

public abstract class ProducerRecipeCategory<T extends ProcessorRecipe> extends ForestryRecipeCategory<T> {

    private static final ResourceLocation GUI = new ResourceLocation(Gendustry.ID, "textures/gui/processor.png");
    private final IDrawableAnimated arrow;
    private final IDrawable tankOverlay;
    private final IDrawable icon;

    public ProducerRecipeCategory(IGuiHelper helper, String unlocalizedName, ItemStack stack) {
        super(helper.createDrawable(GUI, 13, 18, 151, 60), unlocalizedName);

        IDrawableStatic arrowDrawable = helper.createDrawable(GUI, 176, 60, 55, 18);
        this.arrow = helper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
        this.tankOverlay = helper.createDrawable(GUI, 176, 0, 16, 58);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, stack);
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(T recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        this.arrow.draw(graphics, 35, 23);
    }

    protected void addFluidTank(IRecipeLayoutBuilder builder, Fluid fluid, int amount) {
        builder.addSlot(RecipeIngredientRole.OUTPUT, 109, 1)
                .setFluidRenderer(10000, false, 16, 58)
                .setOverlay(tankOverlay, 0, 0)
                .addIngredient(ForgeTypes.FLUID_STACK, new FluidStack(fluid, amount));
    }
}
