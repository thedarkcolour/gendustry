package com.thedarkcolour.gendustry.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import com.thedarkcolour.gendustry.blockentity.IndustrialApiaryBlockEntity;

public class IndustrialApiaryBlock extends Block implements EntityBlock {
	public IndustrialApiaryBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new IndustrialApiaryBlockEntity(pos, state);
	}
}
