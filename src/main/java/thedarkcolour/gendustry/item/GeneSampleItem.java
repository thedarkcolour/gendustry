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
import forestry.api.genetics.capability.IIndividualHandlerItem;
import forestry.core.render.ColourProperties;

import org.jetbrains.annotations.Nullable;
import thedarkcolour.gendustry.registry.GItems;

public class GeneSampleItem extends Item {
	public GeneSampleItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).craftRemainder(GItems.RESOURCE.item(GendustryResourceType.BLANK_GENE_SAMPLE)));
	}

	@Override
	public Component getName(ItemStack stack) {
		if (stack.hasTag() && stack.getTag().contains("speciesType")) {
			String speciesTypeId = stack.getTag().getString("speciesType");
			ISpeciesType<?, ?> speciesType = IForestryApi.INSTANCE.getGeneticManager().getSpeciesTypeSafe(new ResourceLocation(speciesTypeId));

			if (speciesType != null) {
				return Component.translatable(getOrCreateDescriptionId(), speciesType.getDisplayName());
			}
		}
		return Component.translatable(getOrCreateDescriptionId(), "?");
	}

	public static ItemStack createStack(ISpeciesType<?, ?> speciesType, IChromosome<?> chromosome, IAllele allele) {
		ItemStack stack = new ItemStack(GItems.GENE_SAMPLE);
		CompoundTag nbt = stack.getOrCreateTag();
		nbt.putString("speciesType", speciesType.id().toString());
		nbt.putString("chromosome", chromosome.id().toString());
		nbt.putString("allele", allele.alleleId().toString());
		return stack;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag pIsAdvanced) {
		if (!stack.hasTag()) {
			return;
		}
		CompoundTag nbt = stack.getTag();
		if (!nbt.contains("chromosome") || !nbt.contains("allele")) {
			return;
		}
		ResourceLocation chromosomeId = new ResourceLocation(nbt.getString("chromosome"));
		IChromosome<?> chromosome = IForestryApi.INSTANCE.getAlleleManager().getChromosome(chromosomeId);
		if (chromosome == null) {
			return;
		}
		ResourceLocation alleleId = new ResourceLocation(nbt.getString("allele"));
		IAllele allele = IForestryApi.INSTANCE.getAlleleManager().getAllele(alleleId);
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
