package com.thedarkcolour.gendustry.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import com.thedarkcolour.gendustry.blockentity.MutagenProducerBlockEntity;

public class MutagenProducerBlock extends Block implements EntityBlock {
	public MutagenProducerBlock() {
		super(Properties.of(Material.METAL));
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new MutagenProducerBlockEntity(pos, state);
	}
}
