package thedarkcolour.gendustry.blockentity;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.fluids.capability.IFluidHandler;

import forestry.api.IForestryApi;
import forestry.api.core.IErrorLogic;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpecies;
import forestry.api.genetics.ISpeciesType;
import forestry.api.genetics.alleles.AllelePair;
import forestry.api.genetics.alleles.IChromosome;
import forestry.api.genetics.alleles.IKaryotype;
import forestry.api.genetics.capability.IIndividualHandlerItem;
import forestry.api.plugin.IGenomeBuilder;
import forestry.core.fluids.FilteredTank;
import forestry.core.fluids.FluidHelper;

import org.jetbrains.annotations.Nullable;
import thedarkcolour.gendustry.compat.forestry.GendustryError;
import thedarkcolour.gendustry.menu.AdvancedMutatronMenu;
import thedarkcolour.gendustry.menu.MutatronMenu;
import thedarkcolour.gendustry.registry.GBlockEntities;
import thedarkcolour.gendustry.registry.GFluids;

public abstract class AbstractMutatronBlockEntity extends PoweredTankBlockEntity {
	private static final int ENERGY_PER_WORK_CYCLE = 100000;
	private static final int TICKS_PER_WORK_CYCLE = 40;

	private final FilteredTank mutagenTank;
	protected final MutatronInventory inventory;
	public final boolean isAdvanced;

	@Nullable
	private IMutation<?> currentMutation;
	protected ItemStack currentPrimary = ItemStack.EMPTY;
	protected ItemStack currentSecondary = ItemStack.EMPTY;
	@Nullable
	private UUID lastPlayer;

	public AbstractMutatronBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state, 10000, 1000000);

		this.mutagenTank = new FilteredTank(10000).setFilters(Set.of(GFluids.MUTAGEN.fluid()));
		this.tankManager.add(this.mutagenTank);
		this.inventory = new MutatronInventory(this);
		this.isAdvanced = type == GBlockEntities.ADVANCED_MUTATRON.tileType();

		setInternalInventory(this.inventory);
		setTicksPerWorkCycle(TICKS_PER_WORK_CYCLE);
		setEnergyPerWorkCycle(ENERGY_PER_WORK_CYCLE);
	}

	@Override
	public void serverTick(Level level, BlockPos pos, BlockState state) {
		super.serverTick(level, pos, state);

		if (updateOnInterval(20)) {
			FluidHelper.drainContainers(tankManager, this, MutatronInventory.SLOT_CAN_INPUT);
		}
	}

	@Override
	public boolean hasWork() {
		IErrorLogic errors = getErrorLogic();

		// Check mutagen
		boolean noMutagen = this.mutagenTank.getFluid().getAmount() < 1000;
		errors.setCondition(noMutagen, GendustryError.NO_MUTAGEN);

		// Check labware
		boolean noLabware = this.inventory.getItem(MutatronInventory.SLOT_LABWARE).isEmpty();
		errors.setCondition(noLabware, GendustryError.NO_LABWARE);

		// Make sure inputs haven't changed
		if (hasMutation()) {
			if (this.inventory.getItem(MutatronInventory.SLOT_PRIMARY) != this.currentPrimary
					|| this.inventory.getItem(MutatronInventory.SLOT_SECONDARY) != this.currentSecondary
					|| this.currentSecondary.isEmpty()
					|| this.currentSecondary.isEmpty()
			) {
				onMutationsUpdated(List.of(), ItemStack.EMPTY, ItemStack.EMPTY);
			}
		}

		// Try to choose a new recipe
		if (!hasMutation()) {
			ItemStack primaryStack = this.inventory.getItem(MutatronInventory.SLOT_PRIMARY);
			ItemStack secondaryStack = this.inventory.getItem(MutatronInventory.SLOT_SECONDARY);
			IIndividual primary = IIndividualHandlerItem.getIndividual(primaryStack);
			IIndividual secondary = IIndividualHandlerItem.getIndividual(secondaryStack);

			// Check if two mates are present
			boolean noMates = primary == null || secondary == null;
			errors.setCondition(noMates, GendustryError.NO_MATES);

			if (noMates) {
				return false;
			}

			// Check if two mates are of same species type
			boolean incompatible = primary.getType() != secondary.getType();
			errors.setCondition(incompatible, GendustryError.INCOMPATIBLE_SPECIES);

			if (incompatible) {
				return false;
			}

			// Check if mutations are possible
			List<IMutation<ISpecies<?>>> mutations = IForestryApi.INSTANCE.getGeneticManager().getMutations(primary.getType()).getCombinations(primary.getSpecies(), secondary.getSpecies());
			boolean noMutations = mutations.isEmpty();
			errors.setCondition(noMutations, GendustryError.NO_MUTATIONS);

			if (noMutations) {
				return false;
			}

			// Choose mutation
			onMutationsUpdated(mutations, primaryStack, secondaryStack);
		}

		return !noMutagen && !noLabware;
	}

	protected boolean hasMutation() {
		return this.currentMutation != null;
	}

	protected abstract void onMutationsUpdated(List<IMutation<ISpecies<?>>> mutations, ItemStack primaryStack, ItemStack secondaryStack);

	@Override
	protected boolean workCycle() {
		// Check for room in result slot
		if (!this.inventory.getItem(MutatronInventory.SLOT_RESULT).isEmpty()) {
			return false;
		}
		// Consume inputs
		ItemStack primary = this.inventory.removeItem(MutatronInventory.SLOT_PRIMARY, 1);
		this.inventory.removeItem(MutatronInventory.SLOT_SECONDARY, 1);
		this.inventory.removeItem(MutatronInventory.SLOT_LABWARE, 1);
		this.mutagenTank.drain(1000, IFluidHandler.FluidAction.EXECUTE);

		// Set output
		IIndividualHandlerItem.ifPresent(primary, individual -> {
			ISpeciesType<?, ?> speciesType = individual.getType();

			// Create new genome
			IGenome genome = createMutatedGenome(this.currentMutation);

			// Create new individual
			IIndividual newIndividual = individual.copyWithGenome(genome);
			newIndividual.setMate(genome);
			ItemStack result = newIndividual.createStack(speciesType.getTypeForMutation(2));

			this.inventory.setItem(MutatronInventory.SLOT_RESULT, result);

			// Register mutation for the last player to access this block
			if (this.lastPlayer != null) {
				Player player = this.level.getPlayerByUUID(this.lastPlayer);

				if (player == null) {
					this.lastPlayer = null;
				} else {
					IBreedingTracker tracker = speciesType.getBreedingTracker(this.level, player.getGameProfile());
					tracker.registerMutation(this.currentMutation);
				}
			}
		});

		// Reset state
		setCurrentMutation(null, ItemStack.EMPTY, ItemStack.EMPTY);

		return true;
	}

	// todo make this a method in base Forestry
	public static IGenome createMutatedGenome(IMutation<?> mutation) {
		IKaryotype karyotype = mutation.getType().getKaryotype();
		IGenomeBuilder builder = karyotype.createGenomeBuilder();

		ImmutableList<AllelePair<?>> allelePairs = mutation.getResultAlleles();
		ImmutableList<IChromosome<?>> chromosomes = karyotype.getChromosomes();

		for (int i = 0; i < chromosomes.size(); i++) {
			IChromosome<?> chromosome = chromosomes.get(i);
			AllelePair<?> pair = allelePairs.get(i);

			builder.setUnchecked(chromosome, pair);
		}

		return builder.build();
	}

	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory playerInv, Player player) {
		// Track last player who opened
		this.lastPlayer = player.getUUID();

		return this.isAdvanced ? new AdvancedMutatronMenu(windowId, playerInv, (AdvancedMutatronBlockEntity) this) : new MutatronMenu(windowId, playerInv, (MutatronBlockEntity) this);
	}

	public void setCurrentMutation(@Nullable IMutation<?> mutation, ItemStack primary, ItemStack secondary) {
		this.currentMutation = mutation;
		this.currentPrimary = primary;
		this.currentSecondary = secondary;
	}

	@Nullable
	public IMutation<?> getCurrentMutation() {
		return this.currentMutation;
	}
}
