package thedarkcolour.gendustry.recipe;

import java.util.IdentityHashMap;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import forestry.api.genetics.ISpeciesType;
import forestry.api.genetics.alleles.IAllele;
import forestry.api.genetics.alleles.IChromosome;

import thedarkcolour.gendustry.item.GendustryResourceType;
import thedarkcolour.gendustry.item.GeneSampleItem;
import thedarkcolour.gendustry.item.GeneticTemplateItem;
import thedarkcolour.gendustry.item.SpeciesTypeItem;
import thedarkcolour.gendustry.registry.GItems;
import thedarkcolour.gendustry.registry.GRecipeTypes;

public class GeneticTemplateRecipe extends CustomRecipe {
	public GeneticTemplateRecipe(ResourceLocation id, CraftingBookCategory category) {
		super(id, category);
	}

	@Override
	public boolean matches(CraftingContainer container, Level level) {
		boolean hasTemplate = false;
		ISpeciesType<?, ?> speciesType = null;
		int samples = 0;

		for (int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack stack = container.getItem(i);

			// Skip empty slot
			if (stack.isEmpty()) {
				continue;
			}
			if (stack.is(GItems.GENETIC_TEMPLATE.item()) || stack.is(GItems.RESOURCE.item(GendustryResourceType.BLANK_GENETIC_TEMPLATE))) {
				// Only one genetic template
				if (hasTemplate) {
					return false;
				} else {
					ISpeciesType<?, ?> templateType = SpeciesTypeItem.getSpeciesType(stack);

					// If we've already found samples, make sure the type matches
					if (speciesType != null) {
						if (speciesType != templateType && templateType != null) {
							return false;
						}
					} else {
						speciesType = templateType;
					}
					hasTemplate = true;
				}
				continue;
			}
			// Only gene samples are allowed
			if (!stack.is(GItems.GENE_SAMPLE.item())) {
				return false;
			} else {
				ISpeciesType<?, ?> sampleType = SpeciesTypeItem.getSpeciesType(stack);

				// No invalid samples
				if (sampleType == null) {
					return false;
				}

				// Ensure sample matches the template type, or set the type if the template is blank
				if (hasTemplate && speciesType != sampleType) {
					if (speciesType == null) {
						speciesType = sampleType;
					} else {
						return false;
					}
				} else {
					speciesType = sampleType;
				}

				++samples;
			}
		}

		return hasTemplate && samples != 0;
	}

	@Override
	public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
		IdentityHashMap<IChromosome<?>, IAllele> samples = new IdentityHashMap<>();
		ItemStack template = ItemStack.EMPTY;
		ISpeciesType<?, ?> type = null;

		for (int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack stack = container.getItem(i);

			if (stack.isEmpty()) {
				continue;
			}
			if (stack.is(GItems.GENE_SAMPLE.item())) {
				IChromosome<?> chromosome = GeneSampleItem.getChromosome(stack);
				IAllele allele = GeneSampleItem.getAllele(stack);

				samples.put(chromosome, allele);

				if (type == null) {
					type = SpeciesTypeItem.getSpeciesType(stack);
				}
			} else if (stack.is(GItems.GENETIC_TEMPLATE.item()) || stack.is(GItems.RESOURCE.item(GendustryResourceType.BLANK_GENETIC_TEMPLATE))) {
				if (!template.isEmpty()) {
					return ItemStack.EMPTY;
				} else {
					template = stack;
				}
			}
		}

		if (!template.isEmpty() && !samples.isEmpty() && type != null) {
			ItemStack result = template.is(GItems.GENETIC_TEMPLATE.item()) ? template.copyWithCount(1) : new ItemStack(GItems.GENETIC_TEMPLATE);

			SpeciesTypeItem.setSpeciesType(result, type);
			GeneticTemplateItem.addAlleles(result, samples);

			return result;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 2;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return GRecipeTypes.GENETIC_TEMPLATE_SERIALIZER.get();
	}
}
