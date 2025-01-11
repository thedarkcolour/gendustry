package thedarkcolour.gendustry.blockentity;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpecies;

import thedarkcolour.gendustry.registry.GBlockEntities;

public class MutatronBlockEntity extends AbstractMutatronBlockEntity {
	public MutatronBlockEntity(BlockPos pos, BlockState state) {
		super(GBlockEntities.MUTATRON.tileType(), pos, state);
	}

	@Override
	protected void onMutationsUpdated(List<IMutation<ISpecies<?>>> mutations, ItemStack primaryStack, ItemStack secondaryStack) {
		if (mutations.isEmpty()) {
			setCurrentMutation(null, primaryStack, secondaryStack);
		} else {
			int index = this.level.random.nextInt(mutations.size());
			setCurrentMutation(mutations.get(index), primaryStack, secondaryStack);
		}
	}
}
