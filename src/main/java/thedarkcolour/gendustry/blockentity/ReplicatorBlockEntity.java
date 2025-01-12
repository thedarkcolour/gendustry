package thedarkcolour.gendustry.blockentity;

import java.util.Map;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.fluids.capability.IFluidHandler;

import forestry.api.apiculture.genetics.IBee;
import forestry.api.core.IErrorLogic;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpecies;
import forestry.api.genetics.ISpeciesType;
import forestry.api.genetics.alleles.IAllele;
import forestry.api.genetics.alleles.IChromosome;
import forestry.api.genetics.alleles.IValueAllele;
import forestry.api.genetics.capability.IIndividualHandlerItem;
import forestry.core.fluids.FilteredTank;
import forestry.core.fluids.FluidHelper;

import org.jetbrains.annotations.Nullable;
import thedarkcolour.gendustry.compat.forestry.GendustryError;
import thedarkcolour.gendustry.item.GeneticTemplateItem;
import thedarkcolour.gendustry.menu.ReplicatorMenu;
import thedarkcolour.gendustry.registry.GBlockEntities;
import thedarkcolour.gendustry.registry.GFluids;

public class ReplicatorBlockEntity extends PoweredTankBlockEntity {
	public static final String HINTS_KEY = "gendustry.replicator";

	private final FilteredTank dnaTank;
	private final FilteredTank proteinTank;
	private final ReplicatorInventory inventory;

	public ReplicatorBlockEntity(BlockPos pos, BlockState state) {
		super(GBlockEntities.REPLICATOR.tileType(), pos, state, 10000, 1000000);

		this.dnaTank = new FilteredTank(10000).setFilters(Set.of(GFluids.LIQUID_DNA.fluid()));
		this.proteinTank = new FilteredTank(10000).setFilters(Set.of(GFluids.PROTEIN.fluid()));
		this.tankManager.add(this.dnaTank);
		this.tankManager.add(this.proteinTank);
		this.inventory = new ReplicatorInventory(this);
		setInternalInventory(this.inventory);

		setEnergyPerWorkCycle(200000);
		setTicksPerWorkCycle(50);
	}

	@Override
	public void serverTick(Level level, BlockPos pos, BlockState state) {
		super.serverTick(level, pos, state);

		if (updateOnInterval(20)) {
			FluidHelper.drainContainers(this.tankManager, this, ReplicatorInventory.SLOT_DNA_CAN_INPUT);
			FluidHelper.drainContainers(this.tankManager, this, ReplicatorInventory.SLOT_PROTEIN_CAN_INPUT);
		}
	}

	@Override
	public boolean hasWork() {
		IErrorLogic errors = getErrorLogic();
		boolean noDna = errors.setCondition(this.dnaTank.getFluidAmount() < 1000, GendustryError.NO_DNA);
		boolean noProtein = errors.setCondition(this.proteinTank.getFluidAmount() < 1000, GendustryError.NO_PROTEIN);
		boolean noTemplate = errors.setCondition(this.inventory.getItem(ReplicatorInventory.SLOT_TEMPLATE).isEmpty(), GendustryError.NO_TEMPLATE);
		return !noDna && !noProtein && !noTemplate;
	}

	@Override
	protected boolean workCycle() {
		// Check for room in result slot
		if (!this.inventory.getItem(ReplicatorInventory.SLOT_OUTPUT).isEmpty()) {
			return false;
		}

		// Read template
		ItemStack template = this.inventory.getItem(ReplicatorInventory.SLOT_TEMPLATE);
		ISpeciesType<?, ?> speciesType = GeneticTemplateItem.getSpeciesType(template);
		Map<IChromosome<?>, IAllele> alleles = GeneticTemplateItem.getAlleles(template);
		if (speciesType == null) {
			return false;
		}

		// Consume inputs
		this.dnaTank.drain(1000, IFluidHandler.FluidAction.EXECUTE);
		this.proteinTank.drain(1000, IFluidHandler.FluidAction.EXECUTE);

		// Create new individual
		IValueAllele<ISpecies<?>> speciesAllele = alleles.get(speciesType.getKaryotype().getSpeciesChromosome()).cast();
		IIndividual individual = speciesAllele.value().createIndividual(alleles);
		// Ignoble stock
		if (individual instanceof IBee bee) {
			bee.setPristine(false);
		}
		this.inventory.setItem(ReplicatorInventory.SLOT_OUTPUT, individual.createStack(speciesType.getTypeForMutation(2)));

		return true;
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory playerInv, Player player) {
		return new ReplicatorMenu(windowId, playerInv, this);
	}
}
