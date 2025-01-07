package thedarkcolour.gendustry.item;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import forestry.api.IForestryApi;
import forestry.api.genetics.ISpeciesType;
import forestry.api.genetics.alleles.IAllele;
import forestry.api.genetics.alleles.IChromosome;
import forestry.core.render.ColourProperties;

import org.jetbrains.annotations.Nullable;
import thedarkcolour.gendustry.registry.GItems;

public class GeneSampleItem extends SpeciesTypeItem {
	public static final String NBT_CHROMOSOME = "chromosome";
	public static final String NBT_ALLELE = "allele";

	public GeneSampleItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
	}

	public static ItemStack createStack(ISpeciesType<?, ?> speciesType, IChromosome<?> chromosome, IAllele allele) {
		ItemStack stack = new ItemStack(GItems.GENE_SAMPLE);
		CompoundTag nbt = stack.getOrCreateTag();
		nbt.putString(NBT_SPECIES_TYPE, speciesType.id().toString());
		nbt.putString(NBT_CHROMOSOME, chromosome.id().toString());
		nbt.putString(NBT_ALLELE, allele.alleleId().toString());
		return stack;
	}

	@Nullable
	public static IChromosome<?> getChromosome(ItemStack stack) {
		if (stack.hasTag()) {
			ResourceLocation location = ResourceLocation.tryParse(stack.getTag().getString(NBT_CHROMOSOME));

			if (location != null) {
				return IForestryApi.INSTANCE.getAlleleManager().getChromosome(location);
			}
		}

		return null;
	}

	@Nullable
	public static IAllele getAllele(ItemStack stack) {
		if (stack.hasTag()) {
			ResourceLocation location = ResourceLocation.tryParse(stack.getTag().getString(NBT_ALLELE));

			if (location != null) {
				return IForestryApi.INSTANCE.getAlleleManager().getAllele(location);
			}
		}

		return null;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag pIsAdvanced) {
		IChromosome<?> chromosome = getChromosome(stack);
		if (chromosome == null) {
			return;
		}
		IAllele allele = getAllele(stack);
		if (allele == null) {
			return;
		}

		tooltip.add(chromosome.getChromosomeDisplayName().append(" - ").withStyle(ChatFormatting.GRAY).append(chromosome.getDisplayName(allele.cast()).withStyle(style -> style.withColor(getColorCoding(allele.dominant())))));
	}

	// Copied from GuiAlyzer
	public static int getColorCoding(boolean dominant) {
		if (dominant) {
			return ColourProperties.INSTANCE.get("gui.beealyzer.dominant");
		} else {
			return ColourProperties.INSTANCE.get("gui.beealyzer.recessive");
		}
	}
}
