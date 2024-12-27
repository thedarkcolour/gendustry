package thedarkcolour.gendustry.item;

import forestry.api.IForestryApi;
import forestry.api.genetics.ISpeciesType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class GeneTemplateItem extends Item {
    public GeneTemplateItem() {
        super(new Properties().stacksTo(1));
    }

    @Nullable
    public ISpeciesType<?, ?> getSpeciesType(ItemStack stack) {
        if (stack.hasTag()) {
            ResourceLocation location = ResourceLocation.tryParse(stack.getTag().getString("type"));

            if (location != null) {
                return IForestryApi.INSTANCE.getGeneticManager().getSpeciesType(location);
            }
        }

        return null;
    }
}
