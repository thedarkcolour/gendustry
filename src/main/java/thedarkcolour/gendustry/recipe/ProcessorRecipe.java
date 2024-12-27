package thedarkcolour.gendustry.recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import forestry.api.recipes.IForestryRecipe;

public abstract class ProcessorRecipe implements IForestryRecipe {
	private final ResourceLocation id;
	protected final int amount;

	public ProcessorRecipe(ResourceLocation id, int amount) {
		this.id = id;
		this.amount = amount;
	}

	public int getAmount() {
		return this.amount;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess) {
		return ItemStack.EMPTY;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	public abstract boolean isIngredient(ItemStack stack);
}
