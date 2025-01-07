package thedarkcolour.gendustry.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import forestry.api.IForestryApi;
import forestry.api.genetics.ISpeciesType;

import org.jetbrains.annotations.Nullable;

public abstract class SpeciesTypeItem extends Item {
	public static final String NBT_SPECIES_TYPE = "speciesType";

	public SpeciesTypeItem(Properties properties) {
		super(properties);
	}

	@Override
	public Component getName(ItemStack stack) {
		ISpeciesType<?, ?> speciesType = getSpeciesType(stack);

		return Component.translatable(getOrCreateDescriptionId(), speciesType != null ? speciesType.getDisplayName() : "?");
	}

	/**
	 * Gets the species type stored on an item.
	 *
	 * @param stack An item, either the Gene Sample or Genetic Template.
	 * @return The species type of the item.
	 */
	@Nullable
	public static ISpeciesType<?, ?> getSpeciesType(ItemStack stack) {
		if (stack.hasTag()) {
			ResourceLocation location = ResourceLocation.tryParse(stack.getTag().getString(NBT_SPECIES_TYPE));

			if (location != null) {
				return IForestryApi.INSTANCE.getGeneticManager().getSpeciesType(location);
			}
		}

		return null;
	}

	public static void setSpeciesType(ItemStack stack, ISpeciesType<?, ?> type) {
		stack.getOrCreateTag().putString(NBT_SPECIES_TYPE, type.id().toString());
	}
}
