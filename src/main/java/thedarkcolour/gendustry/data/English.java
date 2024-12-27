package thedarkcolour.gendustry.data;

import thedarkcolour.gendustry.block.GendustryMachineType;
import thedarkcolour.gendustry.registry.GBlocks;
import thedarkcolour.gendustry.registry.GFluids;
import thedarkcolour.modkit.data.MKEnglishProvider;

class English {
	static void addTranslations(MKEnglishProvider lang) {
		addHint(lang, TranslationKeys.HINT_MUTAGEN_USAGE, "What's Mutagen for?", "Produce Mutagen to use in other Gendustry machines.");
		addHint(lang, TranslationKeys.HINT_MUTAGEN_INGREDIENTS, "What makes Mutagen?", "Mutagen can be made from redstone, glowstone, and even uranium!");
		addHint(lang, TranslationKeys.HINT_DNA_USAGE, "How to use Liquid DNA?", "Use Liquid DNA in the Replicator to construct new organisms!");
		addHint(lang, TranslationKeys.HINT_DNA_INGREDIENTS, "What makes Liquid DNA?", "Liquid DNA can be made from any organisms with a Forestry genome, like bees, saplings and pollen, and butterflies.");
		addHint(lang, TranslationKeys.HINT_PROTEIN_USAGE, "How to use Protein?", "Protein is an ingredient used by the Replicator to create new organisms.");
		addHint(lang, TranslationKeys.HINT_PROTEIN_INGREDIENTS, "How to get Protein?", "Protein can be made from any kind of raw meat.");

		lang.add(GBlocks.MACHINE.get(GendustryMachineType.DNA_EXTRACTOR).block(), "DNA Extractor");
		lang.add(GFluids.LIQUID_DNA.fluid().getFluidType(), "Liquid DNA");
		lang.add("errors.gendustry.no_labware.desc", "No Labware");
		lang.add("errors.gendustry.no_labware.help", "This machine requires Labware to operate.");
	}

	private static void addHint(MKEnglishProvider lang, String hint, String title, String description) {
		lang.add("for.hints." + hint + ".tag", title);
		lang.add("for.hints." + hint + ".desc", description);
	}
}
