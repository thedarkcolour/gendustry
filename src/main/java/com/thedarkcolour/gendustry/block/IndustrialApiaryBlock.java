package com.thedarkcolour.gendustry.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import com.thedarkcolour.gendustry.blockentity.IndustrialApiaryBlockEntity;

public class IndustrialApiaryBlock extends Block implements EntityBlock {
	public IndustrialApiaryBlock() {
		super(BlockBehaviour.Properties.of(Material.METAL).strength(3.0f));
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new IndustrialApiaryBlockEntity(pos, state);
	}
}
