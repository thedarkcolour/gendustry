package thedarkcolour.gendustry.data;

import net.minecraft.world.level.ItemLike;

import forestry.api.core.IError;

import thedarkcolour.gendustry.block.GendustryMachineType;
import thedarkcolour.gendustry.compat.forestry.GendustryError;
import thedarkcolour.gendustry.item.GendustryResourceType;
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
		addHint(lang, TranslationKeys.HINT_TEMPLATE_USAGE, "How to use the Imprinter?", "The Imprinter replaces the genome of an individual with the alleles stored in a Genetic Template. Ignoble stock may not survive.");
		addHint(lang, TranslationKeys.HINT_TRANSPOSER_USAGE, "How to use the Genetic Transposer?", "The Genetic Transposer creates copies of Gene Samples and Genetic Templates.");

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

		// Creative tabs
		lang.add("itemGroup.gendustry", "Gendustry");
		lang.add("itemGroup.gene_samples", "Gene Samples");

		// Item tooltips
		addTooltip(lang, GItems.RESOURCE.get(GendustryResourceType.BLANK_GENETIC_TEMPLATE), "Combine with Gene Samples in a Crafting Table");
		addTooltip(lang, GItems.RESOURCE.get(GendustryResourceType.BLANK_GENE_SAMPLE), "Use in the Sampler to obtain Gene Samples");
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
