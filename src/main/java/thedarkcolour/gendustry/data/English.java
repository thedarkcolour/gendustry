package thedarkcolour.gendustry.data;

import forestry.api.core.IError;

import thedarkcolour.gendustry.block.GendustryMachineType;
import thedarkcolour.gendustry.compat.forestry.GendustryError;
import thedarkcolour.gendustry.registry.GBlocks;
import thedarkcolour.gendustry.registry.GCreativeTabs;
import thedarkcolour.gendustry.registry.GFluids;
import thedarkcolour.gendustry.registry.GItems;
import thedarkcolour.modkit.data.MKEnglishProvider;

class English {
	static void addTranslations(MKEnglishProvider lang) {
		addHint(lang, TranslationKeys.HINT_MUTAGEN_USAGE, "What's Mutagen for?", "Produce Mutagen to use in other Gendustry machines.");
		addHint(lang, TranslationKeys.HINT_MUTAGEN_INGREDIENTS, "What makes Mutagen?", "Mutagen can be made from redstone, glowstone, and even uranium!");
		addHint(lang, TranslationKeys.HINT_DNA_USAGE, "How to use Liquid DNA?", "Use Liquid DNA in the Replicator to construct new organisms!");
		addHint(lang, TranslationKeys.HINT_DNA_INGREDIENTS, "What makes Liquid DNA?", "Liquid DNA can be made from any organisms with a Forestry genome, like bees, saplings and pollen, and butterflies.");
		addHint(lang, TranslationKeys.HINT_PROTEIN_USAGE, "How to use Protein?", "Protein is an ingredient used by the Replicator to create new organisms.");
		addHint(lang, TranslationKeys.HINT_PROTEIN_INGREDIENTS, "How to get Protein?", "Protein can be made from any kind of raw meat.");
		addHint(lang, TranslationKeys.HINT_SAMPLE_USAGE, "What are samples for?", "Gene samples can be crafted with a Genetic Template to create a complete genome for use in the Imprinter.");
		addHint(lang, TranslationKeys.HINT_SAMPLE_REUSE, "Don't throw away samples!", "Unwanted gene samples can be wiped blank by heating them in a furnace.");
		addHint(lang, TranslationKeys.HINT_SAMPLE_SELECTION, "How to choose a gene?", "The Sampler picks a random allele from the specimen's genome and saves it to a gene sample.");

		lang.add(GBlocks.MACHINE.get(GendustryMachineType.DNA_EXTRACTOR).block(), "DNA Extractor");
		lang.add(GFluids.LIQUID_DNA.fluid().getFluidType(), "Liquid DNA");
		lang.add(GItems.GENE_SAMPLE.get(), "Gene Sample (%s)");

		addError(lang, GendustryError.NO_LABWARE, "No Labware", "This machine requires Labware to operate.");
		addError(lang, GendustryError.NO_SAMPLES, "No Samples", "This machine requires Blank Gene Samples to operate.");

		lang.add("itemGroup.gendustry", "Gendustry");
		lang.add("itemGroup.gene_samples", "Gene Samples");
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
}
