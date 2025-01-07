package thedarkcolour.gendustry.item;

import java.util.Locale;

import forestry.api.core.IItemSubtype;

public enum GendustryResourceType implements IItemSubtype {
	LABWARE,
	GENETIC_WASTE,
	UPGRADE_FRAME,
	CLIMATE_CONTROL_MODULE,
	POWER_MODULE,
	GENETICS_PROCESSOR,
	ENVIRONMENTAL_PROCESSOR,
	BLANK_GENE_SAMPLE,
	BLANK_GENETIC_TEMPLATE,
	// This used to be called "bee receptacle" but isn't really accurate since other organisms can be manipulated
	RECEPTACLE;

	private final String name = name().toLowerCase(Locale.ENGLISH);

	@Override
	public String getSerializedName() {
		return this.name;
	}
}
