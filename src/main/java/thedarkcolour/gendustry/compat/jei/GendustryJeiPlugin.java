package thedarkcolour.gendustry.compat.jei;

import forestry.core.ClientsideCode;
import forestry.core.utils.RecipeUtils;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.registration.*;
import net.minecraft.resources.ResourceLocation;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BlockEntity;
import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.blockentity.DnaExtractorBlockEntity;
import thedarkcolour.gendustry.blockentity.MutagenProducerBlockEntity;
import thedarkcolour.gendustry.client.screen.*;
import thedarkcolour.gendustry.compat.jei.dna.DNAExtractorRecipeCategory;
import thedarkcolour.gendustry.compat.jei.mutagen.MutagenRecipeCategory;
import thedarkcolour.gendustry.compat.jei.protein.ProteinProducerRecipeCategory;
import thedarkcolour.gendustry.registry.GItems;
import thedarkcolour.gendustry.registry.GRecipeTypes;

import java.util.Collection;
import java.util.Collections;

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
		registration.addRecipeCategories(new MutagenRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new ProteinProducerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new DNAExtractorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
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
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		// todo replace with MutationRecipe.class when Forestry makes it public
		RecipeType<?>[] mutationTypes = registration.getJeiHelpers().getAllRecipeTypes()
				.filter(type -> type.getRecipeClass().getName().equals("forestry.apiculture.compat.MutationRecipe"))
				.toArray(RecipeType[]::new);

		registration.addRecipeClickArea(MutatronScreen.class, 68, 38, 55, 18, mutationTypes);

		registration.addGuiContainerHandler(ProducerScreen.class, new IGuiContainerHandler<>() {
            @Override
            public Collection<IGuiClickableArea> getGuiClickableAreas(ProducerScreen containerScreen, double guiMouseX, double guiMouseY) {
                BlockEntity blockEntity = containerScreen.getMenu().getTile();
                if (blockEntity instanceof MutagenProducerBlockEntity) {
                    return Collections.singleton(IGuiClickableArea.createBasic(48, 40, 55, 18, GendustryRecipeType.MUTAGEN_PRODUCER));
                } else if (blockEntity instanceof DnaExtractorBlockEntity) {
                    return Collections.singleton(IGuiClickableArea.createBasic(48, 40, 55, 18, GendustryRecipeType.DNA_EXTRACTOR));
                } else {
                    return Collections.singleton(IGuiClickableArea.createBasic(48, 40, 55, 18, GendustryRecipeType.PROTEIN_LIQUEFIER));
                }
            }
        });
	}
}
