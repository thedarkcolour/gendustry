package thedarkcolour.gendustry.item;

import com.google.common.collect.ImmutableList;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import forestry.api.IForestryApi;
import forestry.api.genetics.ISpeciesType;
import forestry.api.genetics.alleles.IAllele;
import forestry.api.genetics.alleles.IAlleleManager;
import forestry.api.genetics.alleles.IChromosome;

import org.jetbrains.annotations.Nullable;
import thedarkcolour.gendustry.data.TranslationKeys;

public class GeneticTemplateItem extends SpeciesTypeItem {
	public static final String NBT_ALLELES = "alleles";

	public GeneticTemplateItem() {
		super(new Properties().stacksTo(1).rarity(Rarity.RARE));
	}

	public static void addAlleles(ItemStack template, Map<IChromosome<?>, IAllele> samples) {
		CompoundTag nbt = template.getOrCreateTagElement(NBT_ALLELES);

		samples.forEach((chromosome, allele) -> nbt.putString(chromosome.id().toString(), allele.alleleId().toString()));
	}

	public static Map<IChromosome<?>, IAllele> getAlleles(ItemStack template) {
		CompoundTag nbt = template.getTagElement(NBT_ALLELES);

		if (nbt == null) {
			return Map.of();
		}

		IdentityHashMap<IChromosome<?>, IAllele> alleles = new IdentityHashMap<>(nbt.size());
		IAlleleManager manager = IForestryApi.INSTANCE.getAlleleManager();

		for (String key : nbt.getAllKeys()) {
			ResourceLocation chromosomeId = ResourceLocation.tryParse(key);

			if (chromosomeId != null) {
				IChromosome<?> chromosome = manager.getChromosome(chromosomeId);

				if (chromosome != null) {
					ResourceLocation alleleId = ResourceLocation.tryParse(nbt.getString(key));

					if (alleleId != null) {
						IAllele allele = manager.getAllele(alleleId);

						if (allele != null) {
							alleles.put(chromosome, allele);
						}
					}
				}
			}
		}

		return alleles;
	}

	public static boolean isComplete(ItemStack stack) {
		ISpeciesType<?, ?> speciesType = getSpeciesType(stack);
		Map<IChromosome<?>, IAllele> alleles = getAlleles(stack);
		return speciesType != null && speciesType.getKaryotype().size() == alleles.size();
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		ISpeciesType<?, ?> speciesType = getSpeciesType(stack);

		if (speciesType == null) {
			return;
		}

		ImmutableList<IChromosome<?>> chromosomes = speciesType.getKaryotype().getChromosomes();
		Map<IChromosome<?>, IAllele> alleles = getAlleles(stack);
		int countIndex = tooltip.size();
		int totalAlleles = chromosomes.size();
		int foundAlleles = 0;

		for (IChromosome<?> chromosome : chromosomes) {
			IAllele allele = alleles.get(chromosome);
			Component chromosomeName = chromosome.getChromosomeDisplayName();

			if (allele == null) {
				tooltip.add(Component.translatable(TranslationKeys.TEMPLATE_ALLELE_ENTRY, chromosomeName, Component.translatable(TranslationKeys.TEMPLATE_MISSING_ALLELE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.GRAY));
			} else {
				Component alleleName = chromosome.getDisplayName(allele.cast()).withStyle(style -> style.withColor(GeneSampleItem.getColorCoding(allele.dominant())));
				tooltip.add(Component.translatable(TranslationKeys.TEMPLATE_ALLELE_ENTRY, chromosomeName, alleleName).withStyle(ChatFormatting.GRAY));
				++foundAlleles;
			}
		}

		tooltip.add(countIndex, Component.translatable(TranslationKeys.TEMPLATE_ALLELE_COUNT, foundAlleles, totalAlleles).withStyle(ChatFormatting.GRAY));
	}
}
