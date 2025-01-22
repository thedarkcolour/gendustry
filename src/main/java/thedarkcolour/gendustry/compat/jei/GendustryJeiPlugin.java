package thedarkcolour.gendustry.compat.jei;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import forestry.apiculture.compat.MutationRecipe;
import forestry.core.ClientsideCode;
import forestry.core.utils.RecipeUtils;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.client.screen.MutatronScreen;
import thedarkcolour.gendustry.client.screen.ProducerScreen;
import thedarkcolour.gendustry.compat.jei.producers.DNAExtractorRecipeCategory;
import thedarkcolour.gendustry.compat.jei.producers.MutagenRecipeCategory;
import thedarkcolour.gendustry.compat.jei.producers.ProducerGuiContainerHandler;
import thedarkcolour.gendustry.compat.jei.producers.ProteinProducerRecipeCategory;
import thedarkcolour.gendustry.data.TranslationKeys;
import thedarkcolour.gendustry.registry.GFluids;
import thedarkcolour.gendustry.registry.GItems;
import thedarkcolour.gendustry.registry.GRecipeTypes;

@JeiPlugin
public class GendustryJeiPlugin implements IModPlugin {
	public static final ResourceLocation ID = Gendustry.loc("jei");

	@Override
	public ResourceLocation getPluginUid() {
		return ID;
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		registration.registerSubtypeInterpreter(GItems.GENE_SAMPLE.item(), new GeneSampleInterpreter());
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
		registration.addRecipeCategories(new MutagenRecipeCategory(guiHelper));
		registration.addRecipeCategories(new ProteinProducerRecipeCategory(guiHelper));
		registration.addRecipeCategories(new DNAExtractorRecipeCategory(guiHelper));
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(MutagenRecipeCategory.ICON_STACK, GendustryRecipeType.MUTAGEN_PRODUCER);
		registration.addRecipeCatalyst(DNAExtractorRecipeCategory.ICON_STACK, GendustryRecipeType.DNA_EXTRACTOR);
		registration.addRecipeCatalyst(ProteinProducerRecipeCategory.ICON_STACK, GendustryRecipeType.PROTEIN_LIQUEFIER);
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeManager manager = ClientsideCode.getRecipeManager();
		registration.addRecipes(GendustryRecipeType.MUTAGEN_PRODUCER, RecipeUtils.getRecipes(manager, GRecipeTypes.MUTAGEN).toList());
		registration.addRecipes(GendustryRecipeType.PROTEIN_LIQUEFIER, RecipeUtils.getRecipes(manager, GRecipeTypes.PROTEIN).toList());
		registration.addRecipes(GendustryRecipeType.DNA_EXTRACTOR, RecipeUtils.getRecipes(manager, GRecipeTypes.DNA).toList());

		registration.addIngredientInfo(GFluids.MUTAGEN.fluidStack(1000), ForgeTypes.FLUID_STACK, Component.translatable(TranslationKeys.JEI_INFO_MUTAGEN));
		registration.addIngredientInfo(GFluids.LIQUID_DNA.fluidStack(1000), ForgeTypes.FLUID_STACK, Component.translatable(TranslationKeys.JEI_INFO_DNA));
		registration.addIngredientInfo(GFluids.PROTEIN.fluidStack(1000), ForgeTypes.FLUID_STACK, Component.translatable(TranslationKeys.JEI_INFO_PROTEIN));
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		RecipeType<?>[] mutationTypes = registration.getJeiHelpers().getAllRecipeTypes()
				.filter(type -> type.getRecipeClass() == MutationRecipe.class)
				.toArray(RecipeType[]::new);

		registration.addRecipeClickArea(MutatronScreen.class, 68, 38, 55, 18, mutationTypes);
		registration.addGuiContainerHandler(ProducerScreen.class, new ProducerGuiContainerHandler());
	}
}
