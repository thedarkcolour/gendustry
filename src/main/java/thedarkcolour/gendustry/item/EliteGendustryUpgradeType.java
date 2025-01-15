package thedarkcolour.gendustry.item;

import java.util.Locale;

import forestry.api.core.IItemSubtype;

public enum EliteGendustryUpgradeType implements IItemSubtype, IGendustryUpgradeType {
	// Increases mutation chances
	MUTATION(4, 400),
	// Functions as LIGHTING, WEATHERPROOF, and SKY
	ACTIVITY_SIMULATOR(1, 200),
	// Increases production, but even further
	PRODUCTIVITY(32, 400),
	// Increases territory, but even further
	TERRITORY(16, 100),
	// Increases lifespan
	YOUTH(4, 50),
	// Increases fertility
	FERTILITY(4, 1000),
	;

	private final String name = name().toLowerCase(Locale.ENGLISH);
	private final int maxStackSize;
	private final int energyCost;

	EliteGendustryUpgradeType(int maxStackSize, int energyCost) {
		this.maxStackSize = maxStackSize;
		this.energyCost = energyCost;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}

	@Override
	public int maxStackSize() {
		return this.maxStackSize;
	}

	@Override
	public int energyCost() {
		return this.energyCost;
	}
}
