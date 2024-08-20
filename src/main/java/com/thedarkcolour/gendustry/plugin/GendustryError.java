package com.thedarkcolour.gendustry.plugin;

import net.minecraft.resources.ResourceLocation;

import forestry.api.core.IError;

import com.thedarkcolour.gendustry.Gendustry;

public enum GendustryError implements IError {
	DISABLED("disabled");

	private final ResourceLocation id;
	private final ResourceLocation sprite;
	private final String descriptionKey;
	private final String helpKey;

	GendustryError(String id) {
		this(id, id);
	}

	GendustryError(String id, String iconName) {
		this.id = new ResourceLocation(Gendustry.ID, id);
		this.sprite = new ResourceLocation(Gendustry.ID, "errors/" + iconName);
		String idDotted = this.id.getNamespace() + '.' + this.id.getPath();
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
