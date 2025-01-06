package thedarkcolour.gendustry.recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import thedarkcolour.gendustry.registry.GItems;
import thedarkcolour.gendustry.registry.GRecipeTypes;

public class GeneticTemplateRecipe extends CustomRecipe {
	public GeneticTemplateRecipe(ResourceLocation id, CraftingBookCategory category) {
		super(id, category);
	}

	@Override
	public boolean matches(CraftingContainer container, Level level) {
		boolean hasTemplate = false;

		for (int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack stack = container.getItem(i);

			// Skip empty slot
			if (stack.isEmpty()) {
				continue;
			}
			// Only one genetic template
			if (stack.is(GItems.GENETIC_TEMPLATE.item())) {
				if (hasTemplate) {
					return false;
				} else {
					hasTemplate = true;
				}
				continue;
			}
			// Only other gene samples are allowed
			if (!stack.is(GItems.GENE_SAMPLE.item())) {
				return false;
			}
		}

		return hasTemplate;
	}

	@Override
	public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
		return null;
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
