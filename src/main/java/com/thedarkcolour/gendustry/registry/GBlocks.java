package com.thedarkcolour.gendustry.registry;

import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import com.thedarkcolour.gendustry.Gendustry;
import com.thedarkcolour.gendustry.block.IndustrialApiaryBlock;

public class GBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(Registry.BLOCK_REGISTRY, Gendustry.ID);

	public static final RegistryObject<Block> INDUSTRIAL_APIARY = REGISTRY.register("industrial_apiary", () -> new IndustrialApiaryBlock(BlockBehaviour.Properties.of(Material.METAL).strength(3.0f)));
}
