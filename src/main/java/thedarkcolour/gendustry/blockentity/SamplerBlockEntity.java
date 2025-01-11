package thedarkcolour.gendustry.blockentity;

import java.util.Map;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import forestry.api.core.ForestryError;
import forestry.api.core.IErrorLogic;
import forestry.api.genetics.alleles.AllelePair;
import forestry.api.genetics.alleles.IAllele;
import forestry.api.genetics.alleles.IChromosome;
import forestry.api.genetics.capability.IIndividualHandlerItem;
import forestry.core.tiles.TilePowered;

import thedarkcolour.gendustry.compat.forestry.GendustryError;
import thedarkcolour.gendustry.item.GeneSampleItem;
import thedarkcolour.gendustry.menu.ThreeInputMenu;
import thedarkcolour.gendustry.registry.GBlockEntities;

public class SamplerBlockEntity extends TilePowered implements IHintKey {
	private static final int ENERGY_PER_WORK_CYCLE = 20000;
	private static final int TICKS_PER_WORK_CYCLE = 20;

	public static final String HINTS_KEY = "gendustry.sampler";

	private final SamplerInventory inventory;

	public SamplerBlockEntity(BlockPos pos, BlockState state) {
		super(GBlockEntities.SAMPLER.tileType(), pos, state, 10000, 100000);

		this.inventory = new SamplerInventory(this);

		setInternalInventory(this.inventory);
		setTicksPerWorkCycle(TICKS_PER_WORK_CYCLE);
		setEnergyPerWorkCycle(ENERGY_PER_WORK_CYCLE);
	}

	@Override
	public boolean hasWork() {
		IErrorLogic errors = getErrorLogic();
		boolean noSamples = errors.setCondition(this.inventory.getItem(SamplerInventory.SLOT_BLANK_SAMPLE).isEmpty(), GendustryError.NO_SAMPLES);
		boolean noLabware = errors.setCondition(this.inventory.getItem(SamplerInventory.SLOT_LABWARE).isEmpty(), GendustryError.NO_LABWARE);
		boolean noSpecimen = errors.setCondition(this.inventory.getItem(SamplerInventory.SLOT_INPUT).isEmpty(), ForestryError.NO_SPECIMEN);
		return !noSamples && !noLabware && !noSpecimen;
	}

	@Override
	protected boolean workCycle() {
		// Check for room in result slot
		if (!this.inventory.getItem(SamplerInventory.SLOT_OUTPUT).isEmpty()) {
			return false;
		}
		// Consume inputs
		ItemStack organism = this.inventory.removeItem(SamplerInventory.SLOT_INPUT, 1);
		this.inventory.removeItem(SamplerInventory.SLOT_LABWARE, 1);
		this.inventory.removeItem(SamplerInventory.SLOT_BLANK_SAMPLE, 1);

		return IIndividualHandlerItem.filter(organism, individual -> {
			RandomSource random = this.level.random;
			Map.Entry<IChromosome<?>, AllelePair<?>> randomEntry = Util.getRandom(individual.getGenome().getChromosomes().entrySet().asList(), random);
			AllelePair<?> randomPair = randomEntry.getValue();
			IAllele chosenAllele = random.nextBoolean() ? randomPair.active() : randomPair.inactive();

			this.inventory.setItem(SamplerInventory.SLOT_OUTPUT, GeneSampleItem.createStack(individual.getType(), randomEntry.getKey(), chosenAllele));

			return true;
		});
	}

	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory playerInv, Player player) {
		return ThreeInputMenu.sampler(windowId, playerInv, this);
	}

	@Override
	public String getHintKey() {
		return HINTS_KEY;
	}
}
