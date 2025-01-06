package thedarkcolour.gendustry.compat.jei;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;

class GeneSampleInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
	@Override
	public String apply(ItemStack ingredient, UidContext context) {
		// not sure if this is the correct way to use the parameter but hey
		if (context == UidContext.Recipe) {
			return "written";
		}

		if (ingredient.hasTag()) {
			CompoundTag nbt = ingredient.getTag();

			if (nbt.contains("speciesType") && nbt.contains("chromosome") && nbt.contains("allele")) {
				return nbt.getString("speciesType") + nbt.getString("chromosome") + nbt.getString("allele");
			}
		}
		return IIngredientSubtypeInterpreter.NONE;
	}
}
