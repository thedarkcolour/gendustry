package thedarkcolour.gendustry.compat.forestry;

import net.minecraft.resources.ResourceLocation;

import forestry.api.core.IError;

import thedarkcolour.gendustry.Gendustry;

public enum GendustryError implements IError {
	NO_LABWARE("no_labware");

	private final ResourceLocation id;
	private final ResourceLocation sprite;
	private final String descriptionKey;
	private final String helpKey;

	GendustryError(String id) {
		this.id = Gendustry.loc(id);
		this.sprite = Gendustry.loc("errors/" + id);
		String idDotted = Gendustry.ID + '.' + this.id.getPath();
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
