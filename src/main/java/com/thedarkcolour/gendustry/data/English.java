package com.thedarkcolour.gendustry.data;

import thedarkcolour.modkit.data.MKEnglishProvider;

class English {
	static void addTranslations(MKEnglishProvider lang) {
		addHint(lang, TranslationKeys.HINT_MUTAGEN_USAGE, "What's mutagen for?", "Produce mutagen to use in other Gendustry machines.");
		addHint(lang, TranslationKeys.HINT_MUTAGEN_INGREDIENTS, "What makes mutagen?", "Mutagen can be made from redstone, glowstone, and even uranium!");
	}

	private static void addHint(MKEnglishProvider lang, String hint, String title, String description) {
		lang.add("for.hints." + hint + ".tag", title);
		lang.add("for.hints." + hint + ".desc", description);
	}
}
