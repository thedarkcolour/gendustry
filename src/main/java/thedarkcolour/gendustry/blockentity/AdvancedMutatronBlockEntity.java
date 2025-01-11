package thedarkcolour.gendustry.blockentity;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpecies;

import org.jetbrains.annotations.Nullable;
import thedarkcolour.gendustry.compat.forestry.GendustryError;
import thedarkcolour.gendustry.registry.GBlockEntities;

public class AdvancedMutatronBlockEntity extends AbstractMutatronBlockEntity {
	private List<IMutation<ISpecies<?>>> possibilities = List.of();
	@Nullable
	private IMutation<?> lastChoice = null;

	public AdvancedMutatronBlockEntity(BlockPos pos, BlockState state) {
		super(GBlockEntities.ADVANCED_MUTATRON.tileType(), pos, state);
	}

	@Override
	protected void onMutationsUpdated(List<IMutation<ISpecies<?>>> mutations, ItemStack primaryStack, ItemStack secondaryStack) {
		this.possibilities = mutations;
		this.currentPrimary = primaryStack;
		this.currentSecondary = secondaryStack;

		if (this.lastChoice != null && this.possibilities.contains(this.lastChoice)) {
			setCurrentMutation(this.lastChoice, primaryStack, secondaryStack);
		}
	}

	@Override
	public void setCurrentMutation(@Nullable IMutation<?> mutation, ItemStack primary, ItemStack secondary) {
		super.setCurrentMutation(mutation, primary, secondary);

		if (mutation != null) {
			this.lastChoice = mutation;
		}
	}

	@Override
	public boolean hasWork() {
		boolean canWork = super.hasWork();
		boolean noSelection = getErrorLogic().setCondition(getCurrentMutation() == null, GendustryError.NO_SELECTION);

		return canWork && !noSelection;
	}

	@Override
	protected boolean hasMutation() {
		return !this.possibilities.isEmpty();
	}

	public List<IMutation<ISpecies<?>>> getPossibilities() {
		return this.possibilities;
	}
}
