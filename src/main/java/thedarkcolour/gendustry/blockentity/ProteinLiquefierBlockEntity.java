package thedarkcolour.gendustry.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import forestry.api.core.ForestryError;
import forestry.api.core.IError;

import thedarkcolour.gendustry.recipe.ProteinRecipe;
import thedarkcolour.gendustry.recipe.cache.ProteinRecipeCache;
import thedarkcolour.gendustry.registry.GBlockEntities;
import thedarkcolour.gendustry.registry.GFluids;
import org.jetbrains.annotations.Nullable;

public class ProteinLiquefierBlockEntity extends ProcessorBlockEntity<ProteinLiquefierBlockEntity, ProteinRecipe> {
	private static final int ENERGY_PER_WORK_CYCLE = 20000;
	private static final int TICKS_PER_WORK_CYCLE = 100;

	public static final String HINTS_KEY = "gendustry.protein_liquefier";

	public ProteinLiquefierBlockEntity(BlockPos pos, BlockState state) {
		super(GBlockEntities.PROTEIN_LIQUEFIER, GFluids.PROTEIN, false, pos, state);
	}

	@Override
	public boolean isValidInput(ItemStack input) {
		return ProteinRecipeCache.INSTANCE.getRecipe(input) != null;
	}

	@Nullable
	@Override
	public ProteinRecipe getRecipe(ItemStack input) {
		return ProteinRecipeCache.INSTANCE.getRecipe(input);
	}

	@Override
	public void startWorking() {
		setTicksPerWorkCycle(TICKS_PER_WORK_CYCLE);
		setEnergyPerWorkCycle(ENERGY_PER_WORK_CYCLE);
	}

	@Override
	public IError getNoInputError() {
		return ForestryError.NO_RECIPE;
	}

	@Override
	public String getHintsKey() {
		return HINTS_KEY;
	}
}
