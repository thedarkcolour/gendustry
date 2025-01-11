package thedarkcolour.gendustry.blockentity;

import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import forestry.api.core.ForestryError;
import forestry.api.core.IErrorLogic;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.alleles.IAllele;
import forestry.api.genetics.alleles.IChromosome;
import forestry.api.genetics.capability.IIndividualHandlerItem;
import forestry.core.tiles.TilePowered;

import thedarkcolour.gendustry.compat.forestry.GendustryError;
import thedarkcolour.gendustry.item.GeneticTemplateItem;
import thedarkcolour.gendustry.menu.ThreeInputMenu;
import thedarkcolour.gendustry.registry.GBlockEntities;

public class ImprinterBlockEntity extends TilePowered implements IHintKey {
	public static final String HINTS_KEY = "gendustry.imprinter";

	private final ImprinterInventory inventory;

	public ImprinterBlockEntity(BlockPos pos, BlockState state) {
		super(GBlockEntities.IMPRINTER.tileType(), pos, state, 10000, 1000000);

		this.inventory = new ImprinterInventory(this);
		setInternalInventory(this.inventory);

		setTicksPerWorkCycle(80);
		setEnergyPerWorkCycle(100000);
	}

	@Override
	public boolean hasWork() {
		IErrorLogic errors = getErrorLogic();
		boolean noSpecimen = errors.setCondition(this.inventory.getItem(ImprinterInventory.SLOT_INPUT).isEmpty(), ForestryError.NO_SPECIMEN);
		boolean noTemplate = errors.setCondition(this.inventory.getItem(ImprinterInventory.SLOT_TEMPLATE).isEmpty(), GendustryError.NO_TEMPLATE);
		boolean noLabware = errors.setCondition(this.inventory.getItem(ImprinterInventory.SLOT_LABWARE).isEmpty(), GendustryError.NO_LABWARE);
		return !noTemplate && !noLabware && !noSpecimen;
	}

	@Override
	protected boolean workCycle() {
		// Check for room in result slot
		if (!this.inventory.getItem(ImprinterInventory.SLOT_OUTPUT).isEmpty()) {
			return false;
		}
		// Consume inputs
		ItemStack organism = this.inventory.removeItem(ImprinterInventory.SLOT_INPUT, 1);
		this.inventory.removeItem(ImprinterInventory.SLOT_LABWARE, 1);

		return IIndividualHandlerItem.filter(organism, (individual, stage) -> {
			ItemStack template = this.inventory.getItem(ImprinterInventory.SLOT_TEMPLATE);
			Map<IChromosome<?>, IAllele> alleles = GeneticTemplateItem.getAlleles(template);
			IGenome newGenome = individual.getGenome().copyWith(alleles);

			IIndividual newIndividual = individual.copyWithGenome(newGenome);
			if (individual.getMate() != null) {
				newIndividual.setMate(newGenome);
			}
			this.inventory.setItem(ImprinterInventory.SLOT_OUTPUT, newIndividual.createStack(stage));

			return true;
		});
	}

	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory playerInv, Player player) {
		return ThreeInputMenu.imprinter(windowId, playerInv, this);
	}

	@Override
	public String getHintKey() {
		return HINTS_KEY;
	}
}
