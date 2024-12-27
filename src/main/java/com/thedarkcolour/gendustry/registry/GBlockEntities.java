package com.thedarkcolour.gendustry.registry;

import java.util.Set;

import forestry.modules.features.FeatureProvider;
import forestry.modules.features.FeatureTileType;
import forestry.modules.features.IFeatureRegistry;
import forestry.modules.features.ModFeatureRegistry;

import com.thedarkcolour.gendustry.Gendustry;
import com.thedarkcolour.gendustry.block.GendustryMachineType;
import com.thedarkcolour.gendustry.blockentity.DnaExtractorBlockEntity;
import com.thedarkcolour.gendustry.blockentity.IndustrialApiaryBlockEntity;
import com.thedarkcolour.gendustry.blockentity.MutagenProducerBlockEntity;
import com.thedarkcolour.gendustry.blockentity.ProteinLiquefierBlockEntity;

@FeatureProvider
public class GBlockEntities {
	private static final IFeatureRegistry REGISTRY = ModFeatureRegistry.get(Gendustry.MODULE_ID);

	public static final FeatureTileType<IndustrialApiaryBlockEntity> INDUSTRIAL_APIARY = REGISTRY.tile(IndustrialApiaryBlockEntity::new, "industrial_apiary", () -> Set.of(GBlocks.MACHINE.get(GendustryMachineType.INDUSTRIAL_APIARY).block()));
	public static final FeatureTileType<MutagenProducerBlockEntity> MUTAGEN_PRODUCER = REGISTRY.tile(MutagenProducerBlockEntity::new, "mutagen_producer", () -> Set.of(GBlocks.MACHINE.get(GendustryMachineType.MUTAGEN_PRODUCER).block()));
	public static final FeatureTileType<DnaExtractorBlockEntity> DNA_EXTRACTOR = REGISTRY.tile(DnaExtractorBlockEntity::new, "dna_extractor", () -> Set.of(GBlocks.MACHINE.get(GendustryMachineType.DNA_EXTRACTOR).block()));
	public static final FeatureTileType<ProteinLiquefierBlockEntity> PROTEIN_LIQUEFIER = REGISTRY.tile(ProteinLiquefierBlockEntity::new, "protein_liquefier", () -> Set.of(GBlocks.MACHINE.get(GendustryMachineType.PROTEIN_LIQUEFIER).block()));
}
