package thedarkcolour.gendustry.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import forestry.api.core.ForestryError;
import forestry.api.core.IError;

import thedarkcolour.gendustry.recipe.MutagenRecipe;
import thedarkcolour.gendustry.recipe.cache.MutagenRecipeCache;
import thedarkcolour.gendustry.registry.GBlockEntities;
import thedarkcolour.gendustry.registry.GFluids;
import org.jetbrains.annotations.Nullable;

// Based somewhat on the squeezer
public class MutagenProducerBlockEntity extends ProducerBlockEntity<MutagenProducerBlockEntity, MutagenRecipe> {
	// All recipes take 100000 RF to process. Choose your mutagens wisely!
	private static final int ENERGY_PER_WORK_CYCLE = 100000;
	private static final int TICKS_PER_WORK_CYCLE = 200;

	public static final String HINTS_KEY = "gendustry.mutagen_producer";

	public MutagenProducerBlockEntity(BlockPos pos, BlockState state) {
		super(GBlockEntities.MUTAGEN_PRODUCER, GFluids.MUTAGEN, false, pos, state);
	}

	@Override
	public boolean isValidInput(ItemStack input) {
		return MutagenRecipeCache.INSTANCE.getRecipe(input) != null;
	}

	@Override
	public @Nullable MutagenRecipe getRecipe(ItemStack input) {
		return MutagenRecipeCache.INSTANCE.getRecipe(input);
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
