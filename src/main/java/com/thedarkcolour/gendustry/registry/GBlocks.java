package com.thedarkcolour.gendustry.registry;

import com.thedarkcolour.gendustry.Gendustry;
import com.thedarkcolour.gendustry.block.IndustrialApiaryBlock;
import com.thedarkcolour.gendustry.block.MutagenProducerBlock;

import forestry.modules.features.FeatureBlock;
import forestry.modules.features.FeatureProvider;
import forestry.modules.features.IFeatureRegistry;
import forestry.modules.features.ModFeatureRegistry;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

@FeatureProvider
public class GBlocks {
    private static final IFeatureRegistry REGISTRY = ModFeatureRegistry.get(Gendustry.MODULE_ID);

    public static final FeatureBlock<IndustrialApiaryBlock, ?> INDUSTRIAL_APIARY = REGISTRY.block(IndustrialApiaryBlock::new, "industrial_apiary");
    public static final FeatureBlock<MutagenProducerBlock, ?> MUTAGEN_PRODUCER = REGISTRY.block(MutagenProducerBlock::new, "mutagen_producer");
}
