package thedarkcolour.gendustry.data;

import net.minecraft.world.level.ItemLike;

import forestry.api.core.IError;

import thedarkcolour.gendustry.block.GendustryMachineType;
import thedarkcolour.gendustry.compat.forestry.GendustryError;
import thedarkcolour.gendustry.item.EliteGendustryUpgradeType;
import thedarkcolour.gendustry.item.GendustryResourceType;
import thedarkcolour.gendustry.item.GendustryUpgradeType;
import thedarkcolour.gendustry.registry.GBlocks;
import thedarkcolour.gendustry.registry.GFluids;
import thedarkcolour.gendustry.registry.GItems;
import thedarkcolour.modkit.data.MKEnglishProvider;

class English {
	static void addTranslations(MKEnglishProvider lang) {
		// Translation keys
		lang.add(TranslationKeys.TEMPLATE_MISSING_ALLELE, "MISSING");
		lang.add(TranslationKeys.TEMPLATE_ALLELE_ENTRY, "  %1$s - %2$s");
		lang.add(TranslationKeys.TEMPLATE_ALLELE_COUNT, "Alleles (%1$s/%2$s)");
		lang.add(TranslationKeys.UPGRADE_ENERGY_COST, "Energy Cost: %s RF");
		lang.add(TranslationKeys.UPGRADE_STACK_LIMIT, "Max Count: %s");

		// Machine hints
		addHint(lang, TranslationKeys.HINT_MUTAGEN_USAGE, "What's Mutagen for?", "Produce Mutagen to use in other Gendustry machines.");
		addHint(lang, TranslationKeys.HINT_MUTAGEN_INGREDIENTS, "What makes Mutagen?", "Mutagen can be made from redstone, glowstone, and even uranium!");
		addHint(lang, TranslationKeys.HINT_DNA_USAGE, "How to use Liquid DNA?", "Use Liquid DNA in the Replicator to construct new organisms!");
		addHint(lang, TranslationKeys.HINT_DNA_INGREDIENTS, "What makes Liquid DNA?", "Liquid DNA can be made from any organisms with a Forestry genome, like bees, saplings and pollen, and butterflies.");
		addHint(lang, TranslationKeys.HINT_PROTEIN_USAGE, "How to use Protein?", "Protein is an ingredient used by the Replicator to create new organisms.");
		addHint(lang, TranslationKeys.HINT_PROTEIN_INGREDIENTS, "How to get Protein?", "Protein can be made from any kind of raw meat.");
		addHint(lang, TranslationKeys.HINT_SAMPLE_USAGE, "What are samples for?", "Gene samples can be crafted with a Genetic Template to create a complete genome for use in the Imprinter.");
		addHint(lang, TranslationKeys.HINT_SAMPLE_REUSE, "Don't throw away samples!", "Unwanted gene samples can be wiped blank by heating them in a furnace.");
		addHint(lang, TranslationKeys.HINT_SAMPLE_SELECTION, "How to choose a gene?", "The Sampler picks a random allele from the specimen's genome and saves it to a gene sample.");
		addHint(lang, TranslationKeys.HINT_IMPRINTER_USAGE, "How to use the Imprinter?", "The Imprinter replaces the genome of an individual with the alleles stored in a Genetic Template. Ignoble stock may not survive.");
		addHint(lang, TranslationKeys.HINT_TRANSPOSER_USAGE, "How to use the Genetic Transposer?", "The Genetic Transposer creates copies of Gene Samples and Genetic Templates.");
		addHint(lang, TranslationKeys.HINT_REPLICATOR_USAGE, "How to use the Replicator?", "The Replicator produces a new organism from a Genetic Template using Liquid DNA and Protein.");
		addHint(lang, TranslationKeys.HINT_MUTATRON_USAGE, "What is the Mutatron?", "The Mutatron triggers a mutation between two parent organisms, yielding offspring of a new species.");
		addHint(lang, TranslationKeys.HINT_ADVANCED_MUTATRON_USAGE, "How to use the Replicator?", "To choose the desired mutation between the two parents, use the Advanced Mutatron.");
		addHint(lang, TranslationKeys.HINT_INDUSTRIAL_APIARY_USAGE, "How to use the Industrial Apiary?", "The left slots are for a Princess and Drone. The four middle slots are for upgrades. The nine right slots are outputs.");
		addHint(lang, TranslationKeys.HINT_INDUSTRIAL_APIARY_UPGRADES, "Why no frames?", "The Industrial Apiary does not need frames. Instead, it uses upgrades that affect climate, productivity, lifespan, and more!");

		// Item translation overrides
		lang.add(GBlocks.MACHINE.get(GendustryMachineType.DNA_EXTRACTOR).block(), "DNA Extractor");
		lang.add(GFluids.LIQUID_DNA.fluid().getFluidType(), "Liquid DNA");
		lang.add(GItems.GENE_SAMPLE.get(), "Gene Sample (%s)");
		lang.add(GItems.GENETIC_TEMPLATE.get(), "Genetic Template (%s)");

		// Machine errors
		addError(lang, GendustryError.NO_LABWARE, "No Labware", "This machine requires Labware to operate.");
		addError(lang, GendustryError.NO_SAMPLES, "No Samples", "This machine requires Blank Gene Samples to operate.");
		addError(lang, GendustryError.INCOMPATIBLE_SPECIES, "Incompatible species", "Individuals may only be mated with individuals of the same species type.");
		addError(lang, GendustryError.NO_MUTATIONS, "No Mutations", "There are no mutations between these two species. Please choose different species.");
		addError(lang, GendustryError.NO_MATES, "No Mates", "Two compatible mates are required for a mutation to occur.");
		addError(lang, GendustryError.NO_MUTAGEN, "No Mutagen", "Mutagen is required to trigger a mutation.");
		addError(lang, GendustryError.NO_TEMPLATE, "Missing template", "A complete Genetic Template is required to operate.");
		addError(lang, GendustryError.NO_SELECTION, "Select a mutation", "You must choose a mutation for the Advanced Mutatron.");
		addError(lang, GendustryError.NO_BLANK, "Missing blank template/sample", "The Genetic Transposer needs Blank Gene Samples or Blank Genetic Templates to copy to.");
		addError(lang, GendustryError.NO_SOURCE, "Missing source template/sample", "The Genetic Transposer is missing a filled Gene Sample or Genetic Template.");
		addError(lang, GendustryError.NO_DNA, "Missing Liquid DNA", "This machine requires Liquid DNA to operate.");
		addError(lang, GendustryError.NO_PROTEIN, "Missing Protein", "This machine requires Protein to operate.");

		// Creative tabs
		lang.add("itemGroup.gendustry", "Gendustry");
		lang.add("itemGroup.gene_samples", "Gene Samples");

		// Item tooltips
		addTooltip(lang, GItems.RESOURCE.get(GendustryResourceType.BLANK_GENETIC_TEMPLATE), "Combine with Gene Samples in a Crafting Table");
		addTooltip(lang, GItems.RESOURCE.get(GendustryResourceType.BLANK_GENE_SAMPLE), "Use in the Sampler to obtain Gene Samples");

		addTooltip(lang, GItems.UPGRADE.get(GendustryUpgradeType.AUTOMATION), "Automatically recycles princesses and drones from deceased queens.");
		addTooltip(lang, GItems.UPGRADE.get(GendustryUpgradeType.HEATER), "Raises the temperature of the apiary by 1 step.");
		addTooltip(lang, GItems.UPGRADE.get(GendustryUpgradeType.COOLER), "Lowers the temperature of the apiary by 1 step.");
		addTooltip(lang, GItems.UPGRADE.get(GendustryUpgradeType.HUMIDIFIER), "Raises the humidity of the apiary by 1 step.");
		addTooltip(lang, GItems.UPGRADE.get(GendustryUpgradeType.DRYER), "Lowers the humidity of the apiary by 1 step.");
		addTooltip(lang, GItems.UPGRADE.get(GendustryUpgradeType.POLLINATION), "Increases bee pollination by 25%.");
		addTooltip(lang, GItems.UPGRADE.get(GendustryUpgradeType.SCRUBBER), "Disables bee pollination.");
		addTooltip(lang, GItems.UPGRADE.get(GendustryUpgradeType.NETHER), "Sets the apiary's climate to Hellish temperature and Arid humidity.");
		addTooltip(lang, GItems.UPGRADE.get(GendustryUpgradeType.LIFESPAN), "Decreases lifespan by 20%.");
		addTooltip(lang, GItems.UPGRADE.get(GendustryUpgradeType.LIGHTING), "Allows bees to work without needing to sleep.");
		addTooltip(lang, GItems.UPGRADE.get(GendustryUpgradeType.PRODUCTIVITY), "Increases bee productivity by 25%.");
		addTooltip(lang, GItems.UPGRADE.get(GendustryUpgradeType.WEATHERPROOF), "Allows bees to work during the rain.");
		addTooltip(lang, GItems.UPGRADE.get(GendustryUpgradeType.SIEVE), "Automatically recycles princesses and drones from deceased queens.");
		addTooltip(lang, GItems.UPGRADE.get(GendustryUpgradeType.SKY), "Simulates a view of the sky for bees that aren't cave dwelling.");
		addTooltip(lang, GItems.UPGRADE.get(GendustryUpgradeType.STABILIZER), "Prevents Ignoble Stock bees from dying.");
		addTooltip(lang, GItems.UPGRADE.get(GendustryUpgradeType.TERRITORY), "Increases territory by 25%.");

		addTooltip(lang, GItems.ELITE_UPGRADE.get(EliteGendustryUpgradeType.MUTATION), "Increases bee mutation chances by 25%.");
		addTooltip(lang, GItems.ELITE_UPGRADE.get(EliteGendustryUpgradeType.ACTIVITY_SIMULATOR), "A combination of the Sky, Weatherproof, and Lighting upgrades.");
		addTooltip(lang, GItems.ELITE_UPGRADE.get(EliteGendustryUpgradeType.PRODUCTIVITY), "Increases bee productivity by 25% and speeds up work cycle by 15 ticks.");
		addTooltip(lang, GItems.ELITE_UPGRADE.get(EliteGendustryUpgradeType.TERRITORY), "Increases bee territory by 25%, but has a higher limit.");
		addTooltip(lang, GItems.ELITE_UPGRADE.get(EliteGendustryUpgradeType.YOUTH), "Increases lifespan by 20%.");
		addTooltip(lang, GItems.ELITE_UPGRADE.get(EliteGendustryUpgradeType.FERTILITY), "Increases fertility count by 1.");
	}

	private static void addHint(MKEnglishProvider lang, String hint, String title, String description) {
		lang.add("for.hints." + hint + ".tag", title);
		lang.add("for.hints." + hint + ".desc", description);
	}

	private static void addError(MKEnglishProvider lang, IError error, String title, String description) {
		String path = error.getId().getPath();
		String namespace = error.getId().getNamespace();
		String combined = namespace + '.' + path;

		lang.add("errors." + combined + ".desc", title);
		lang.add("errors." + combined + ".help", description);
	}

	// ItemForestry allows adding tooltips to items like this
	private static void addTooltip(MKEnglishProvider lang, ItemLike item, String tooltip) {
		lang.add(item.asItem().getDescriptionId() + ".tooltip", tooltip);
	}
}
