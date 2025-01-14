package thedarkcolour.gendustry.registry;

import net.minecraft.world.item.CreativeModeTab;

import forestry.Forestry;
import forestry.api.IForestryApi;
import forestry.api.genetics.ISpeciesType;
import forestry.api.genetics.alleles.IAllele;
import forestry.api.genetics.alleles.IChromosome;
import forestry.api.genetics.alleles.IKaryotype;
import forestry.core.tab.ForestryCreativeTabs;
import forestry.modules.features.FeatureCreativeTab;
import forestry.modules.features.FeatureProvider;
import forestry.modules.features.IFeatureRegistry;
import forestry.modules.features.ModFeatureRegistry;

import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.block.GendustryMachineType;
import thedarkcolour.gendustry.item.GeneSampleItem;

@FeatureProvider
public class GCreativeTabs {
	private static final IFeatureRegistry REGISTRY = ModFeatureRegistry.get(Gendustry.MODULE_ID);

	public static final FeatureCreativeTab GENDUSTRY = REGISTRY.creativeTab("gendustry", tab -> {
		tab.icon(() -> GBlocks.MACHINE.stack(GendustryMachineType.INDUSTRIAL_APIARY));
		tab.displayItems(GCreativeTabs::addGendustryItems);
		tab.withTabsBefore(ForestryCreativeTabs.MAIL.getKey());
		tab.withTabsAfter(GCreativeTabs.GENE_SAMPLES.getKey());
	});

	public static final FeatureCreativeTab GENE_SAMPLES = REGISTRY.creativeTab("gene_samples", tab -> {
		tab.icon(GItems.GENE_SAMPLE::stack);
		tab.displayItems(GCreativeTabs::addGeneSamples);
		tab.withTabsBefore(GCreativeTabs.GENDUSTRY.getKey());
	});

	private static void addGendustryItems(CreativeModeTab.ItemDisplayParameters params, CreativeModeTab.Output items) {
		GBlocks.MACHINE.getBlocks().forEach(items::accept);
		items.accept(GItems.POLLEN_KIT);
		items.accept(GFluids.MUTAGEN.getBucket());
		items.accept(GFluids.LIQUID_DNA.getBucket());
		items.accept(GFluids.PROTEIN.getBucket());
		GItems.RESOURCE.getItems().forEach(items::accept);
		GItems.UPGRADE.getItems().forEach(items::accept);
		GItems.ELITE_UPGRADE.getItems().forEach(items::accept);

		if (Forestry.DEBUG) {
			items.accept(GItems.DEBUG_WAND);
		}
	}

	private static void addGeneSamples(CreativeModeTab.ItemDisplayParameters params, CreativeModeTab.Output items) {
		for (ISpeciesType<?, ?> speciesType : IForestryApi.INSTANCE.getGeneticManager().getSpeciesTypes()) {
			IKaryotype karyotype = speciesType.getKaryotype();

			for (IChromosome<?> chromosome : karyotype.getChromosomes()) {
				for (IAllele allele : karyotype.getAlleles(chromosome)) {
					items.accept(GeneSampleItem.createStack(speciesType, chromosome, allele));
				}
			}
		}
	}
}
