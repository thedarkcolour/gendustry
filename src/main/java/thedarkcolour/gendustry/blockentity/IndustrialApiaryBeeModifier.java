package thedarkcolour.gendustry.blockentity;

import net.minecraft.core.Vec3i;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.genetics.IBeeSpecies;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IMutation;
import forestry.core.inventory.IInventoryAdapter;

import org.jetbrains.annotations.Nullable;
import thedarkcolour.gendustry.item.EliteGendustryUpgradeType;
import thedarkcolour.gendustry.item.GendustryUpgradeItem;
import thedarkcolour.gendustry.item.GendustryUpgradeType;
import thedarkcolour.gendustry.item.IGendustryUpgradeType;

class IndustrialApiaryBeeModifier implements IBeeModifier {
	float territory;
	float mutation;
	float lifespan;
	float productivity;
	float pollination;
	int throttle;
	int fertility;
	int temperature;
	int humidity;
	boolean automated;
	boolean stabilized;
	boolean weatherproof;
	boolean lighting;
	boolean sky;
	boolean nether;
	boolean scrubber;
	boolean sieve;

	IndustrialApiaryBeeModifier() {
		reset();
	}

	private void reset() {
		this.territory = 1f;
		this.mutation = 1f;
		this.lifespan = 1f;
		this.productivity = 1f;
		this.pollination = 1f;
		this.throttle = 0;
		this.fertility = 0;
		this.temperature = 0;
		this.humidity = 0;
		this.automated = false;
		this.stabilized = false;
		this.weatherproof = false;
		this.lighting = false;
		this.sky = false;
		this.nether = false;
		this.scrubber = false;
		this.sieve = false;
	}

	// Returns the sum of the energy costs of all upgrades
	public int recalculate(IInventoryAdapter inventory) {
		reset();

		int energyCost = 0;

		for (int i = 0; i < IndustrialApiaryInventory.UPGRADE_SLOT_COUNT; ++i) {
			ItemStack stack = inventory.getItem(IndustrialApiaryInventory.UPGRADE_SLOT_START + i);
			Item item = stack.getItem();
			int count = stack.getCount();

			// Hardcoded for now. If you want an API, open an Issue on GitHub.
			if (item instanceof GendustryUpgradeItem upgrade) {
				IGendustryUpgradeType upgradeType = upgrade.getType();
				energyCost += upgradeType.energyCost() * count;

				if (upgradeType instanceof GendustryUpgradeType type) {
					// Regular upgrades
					switch (type) {
						case AUTOMATION -> this.automated = true;
						case HEATER -> this.temperature += count;
						case COOLER -> this.temperature -= count;
						case HUMIDIFIER -> this.humidity += count;
						case DRYER -> this.humidity -= count;
						case POLLINATION -> this.pollination += 0.25f * count;
						case SCRUBBER -> this.scrubber = true;
						case NETHER -> this.nether = true;
						case LIFESPAN -> this.lifespan += 2f * count;
						case LIGHTING -> this.lighting = true;
						case PRODUCTIVITY -> this.productivity += 0.25f * count;
						case WEATHERPROOF -> this.weatherproof = true;
						case SIEVE -> this.sieve = true;
						case SKY -> this.sky = true;
						case STABILIZER -> this.stabilized = true;
						case TERRITORY -> this.territory += 0.25f * count;
					}
				} else if (upgradeType instanceof EliteGendustryUpgradeType type) {
					// Elite upgrades
					switch (type) {
						case MUTATION -> this.mutation += 0.25f;
						case ACTIVITY_SIMULATOR -> {
							this.lighting = true;
							this.sky = true;
							this.weatherproof = true;
						}
						case PRODUCTIVITY -> {
							this.productivity += 0.25f * count;
							this.throttle += 15 * count;
						}
						case TERRITORY -> this.territory += 0.25f * count;
						case YOUTH -> this.mutation -= 0.2f * count;
						case FERTILITY -> this.fertility += count;
					}
				}
			}
		}

		return energyCost;
	}

	@Override
	public Vec3i modifyTerritory(IGenome genome, Vec3i currentModifier) {
		return new Vec3i((int) (currentModifier.getX() * this.territory), (int) (currentModifier.getY() * this.territory), (int) (currentModifier.getZ() * this.territory));
	}

	@Override
	public float modifyMutationChance(IGenome genome, IGenome mate, IMutation<IBeeSpecies> mutation, float currentChance) {
		return currentChance * this.mutation;
	}

	@Override
	public float modifyAging(IGenome genome, @Nullable IGenome mate, float currentAging) {
		return currentAging * this.lifespan;
	}

	@Override
	public float modifyProductionSpeed(IGenome genome, float currentSpeed) {
		return currentSpeed * this.productivity;
	}

	@Override
	public float modifyPollination(IGenome genome, float currentPollination) {
		return this.scrubber ? 0.0f : currentPollination * this.pollination;
	}

	@Override
	public float modifyGeneticDecay(IGenome genome, float currentDecay) {
		return this.stabilized ? 0.0f : currentDecay;
	}

	@Override
	public boolean isSealed() {
		return this.weatherproof;
	}

	@Override
	public boolean isAlwaysActive(IGenome genome) {
		return this.lighting;
	}

	@Override
	public boolean isSunlightSimulated() {
		return this.sky;
	}

	@Override
	public boolean isHellish() {
		return this.nether;
	}
}
