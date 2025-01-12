package thedarkcolour.gendustry.compat.forestry;

import java.util.Locale;

import net.minecraft.resources.ResourceLocation;

import forestry.api.core.IError;

import thedarkcolour.gendustry.Gendustry;

public enum GendustryError implements IError {
	NO_LABWARE,
	NO_SAMPLES,
	INCOMPATIBLE_SPECIES,
	NO_MUTATIONS,
	NO_MATES,
	NO_MUTAGEN,
	NO_TEMPLATE,
	NO_SELECTION,
	NO_BLANK,
	NO_SOURCE,
	NO_DNA,
	NO_PROTEIN,
	;

	private final ResourceLocation id;
	private final ResourceLocation sprite;
	private final String descriptionKey;
	private final String helpKey;

	GendustryError() {
		String name = name().toLowerCase(Locale.ENGLISH);
		this.id = Gendustry.loc(name);
		this.sprite = Gendustry.loc("errors/" + name);
		String idDotted = Gendustry.ID + '.' + name;
		this.descriptionKey = "errors." + idDotted + ".desc";
		this.helpKey = "errors." + idDotted + ".help";
	}

	@Override
	public String getDescriptionTranslationKey() {
		return this.descriptionKey;
	}

	@Override
	public String getHelpTranslationKey() {
		return this.helpKey;
	}

	@Override
	public ResourceLocation getSprite() {
		return this.sprite;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}
}
