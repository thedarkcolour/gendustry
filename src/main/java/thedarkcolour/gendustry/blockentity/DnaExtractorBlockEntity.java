package thedarkcolour.gendustry.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import forestry.api.core.ForestryError;
import forestry.api.core.IError;
import forestry.api.genetics.capability.IIndividualHandlerItem;

import thedarkcolour.gendustry.recipe.DnaRecipe;
import thedarkcolour.gendustry.recipe.cache.DnaRecipeCache;
import thedarkcolour.gendustry.registry.GBlockEntities;
import thedarkcolour.gendustry.registry.GFluids;
import org.jetbrains.annotations.Nullable;

public class DnaExtractorBlockEntity extends ProcessorBlockEntity<DnaExtractorBlockEntity, DnaRecipe> {
	private static final int ENERGY_PER_WORK_CYCLE = 80000;
	private static final int TICKS_PER_WORK_CYCLE = 50;

	public static final String HINTS_KEY = "gendustry.dna_extractor";

	public DnaExtractorBlockEntity(BlockPos pos, BlockState state) {
		super(GBlockEntities.DNA_EXTRACTOR, GFluids.LIQUID_DNA, true, pos, state);
	}

	@Override
	public boolean isValidInput(ItemStack input) {
		return IIndividualHandlerItem.isIndividual(input);
	}

	@Nullable
	@Override
	public DnaRecipe getRecipe(ItemStack input) {
		IIndividualHandlerItem handler = IIndividualHandlerItem.get(input);
		return handler == null ? null : DnaRecipeCache.INSTANCE.getRecipe(handler.getStage());
	}

	@Override
	public void startWorking() {
		setTicksPerWorkCycle(TICKS_PER_WORK_CYCLE);
		setEnergyPerWorkCycle(ENERGY_PER_WORK_CYCLE);
	}

	@Override
	public IError getNoInputError() {
		return ForestryError.NO_SPECIMEN;
	}

	@Override
	public String getHintsKey() {
		return HINTS_KEY;
	}
}
