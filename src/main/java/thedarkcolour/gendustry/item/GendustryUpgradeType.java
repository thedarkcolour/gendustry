package thedarkcolour.gendustry.item;

import java.util.Locale;

import forestry.api.core.IItemSubtype;

public enum GendustryUpgradeType implements IItemSubtype, IGendustryUpgradeType {
	// Automatically re-breeds bees
	AUTOMATION(1, 50),
	// Increases temperature by 1 step
	HEATER(5, 100),
	// Decreases temperature by 1 step
	COOLER(5, 100),
	// Increases humidity by 1 step
	HUMIDIFIER(2, 50),
	// Decreases humidity by 1 step
	DRYER(2, 50),
	// Increases pollination
	POLLINATION(8, 100),
	// Disables pollination
	SCRUBBER(1, 50),
	// Sets temperature to HELLISH and humidity to ARID
	NETHER(1, 200),
	// Decreases lifespan
	LIFESPAN(4, 300),
	// Bees never sleep
	LIGHTING(1, 50),
	// Increases production
	PRODUCTIVITY(8, 300),
	// Bees ignore the weather
	WEATHERPROOF(1, 50),
	// Functions as alveary sieve
	SIEVE(1, 100),
	// Bees ignore obstructed sky
	SKY(1, 50),
	// Ignoble bees never die
	STABILIZER(1, 400),
	// Increases bee territory
	TERRITORY(4, 50),
	;

	private final String name;
	private final int maxStackSize;
	private final int energyCost;

	GendustryUpgradeType(int maxStackSize, int energyCost) {
		this.name = name().toLowerCase(Locale.ENGLISH);
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
