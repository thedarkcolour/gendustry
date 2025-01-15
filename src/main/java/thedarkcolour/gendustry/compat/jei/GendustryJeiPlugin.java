package thedarkcolour.gendustry.compat.jei;

import net.minecraft.resources.ResourceLocation;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import thedarkcolour.gendustry.GendustryModule;
import thedarkcolour.gendustry.client.screen.MutatronScreen;
import thedarkcolour.gendustry.registry.GItems;

@JeiPlugin
public class GendustryJeiPlugin implements IModPlugin {
	@Override
	public ResourceLocation getPluginUid() {
		return GendustryModule.MODULE_ID;
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		registration.registerSubtypeInterpreter(GItems.GENE_SAMPLE.item(), new GeneSampleInterpreter());
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		// todo replace with MutationRecipe.class when Forestry makes it public
		RecipeType<?>[] mutationTypes = registration.getJeiHelpers().getAllRecipeTypes()
				.filter(type -> type.getRecipeClass().getName().equals("forestry.apiculture.compat.MutationRecipe"))
				.toArray(RecipeType[]::new);

		registration.addRecipeClickArea(MutatronScreen.class, 68, 38, 55, 18, mutationTypes);
	}
}
