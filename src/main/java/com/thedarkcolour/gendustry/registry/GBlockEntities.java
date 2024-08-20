package com.thedarkcolour.gendustry.registry;

import java.util.Set;

import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import com.thedarkcolour.gendustry.Gendustry;
import com.thedarkcolour.gendustry.blockentity.IndustrialApiaryBlockEntity;

public class GBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(Registry.BLOCK_ENTITY_TYPE_REGISTRY, Gendustry.ID);

	public static final RegistryObject<BlockEntityType<IndustrialApiaryBlockEntity>> INDUSTRIAL_APIARY = REGISTRY.register("industrial_apiary", () -> new BlockEntityType<>(IndustrialApiaryBlockEntity::new, Set.of(GBlocks.INDUSTRIAL_APIARY.get()), null));
}
