package thedarkcolour.gendustry.registry;

import java.util.Set;

import forestry.modules.features.FeatureProvider;
import forestry.modules.features.FeatureTileType;
import forestry.modules.features.IFeatureRegistry;
import forestry.modules.features.ModFeatureRegistry;

import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.block.GendustryMachineType;
import thedarkcolour.gendustry.blockentity.DnaExtractorBlockEntity;
import thedarkcolour.gendustry.blockentity.IndustrialApiaryBlockEntity;
import thedarkcolour.gendustry.blockentity.MutagenProducerBlockEntity;
import thedarkcolour.gendustry.blockentity.ProteinLiquefierBlockEntity;
import thedarkcolour.gendustry.blockentity.SamplerBlockEntity;

@FeatureProvider
public class GBlockEntities {
	private static final IFeatureRegistry REGISTRY = ModFeatureRegistry.get(Gendustry.MODULE_ID);

	public static final FeatureTileType<IndustrialApiaryBlockEntity> INDUSTRIAL_APIARY = REGISTRY.tile(IndustrialApiaryBlockEntity::new, "industrial_apiary", () -> Set.of(GBlocks.MACHINE.get(GendustryMachineType.INDUSTRIAL_APIARY).block()));
	public static final FeatureTileType<MutagenProducerBlockEntity> MUTAGEN_PRODUCER = REGISTRY.tile(MutagenProducerBlockEntity::new, "mutagen_producer", () -> Set.of(GBlocks.MACHINE.get(GendustryMachineType.MUTAGEN_PRODUCER).block()));
	public static final FeatureTileType<DnaExtractorBlockEntity> DNA_EXTRACTOR = REGISTRY.tile(DnaExtractorBlockEntity::new, "dna_extractor", () -> Set.of(GBlocks.MACHINE.get(GendustryMachineType.DNA_EXTRACTOR).block()));
	public static final FeatureTileType<ProteinLiquefierBlockEntity> PROTEIN_LIQUEFIER = REGISTRY.tile(ProteinLiquefierBlockEntity::new, "protein_liquefier", () -> Set.of(GBlocks.MACHINE.get(GendustryMachineType.PROTEIN_LIQUEFIER).block()));
	public static final FeatureTileType<SamplerBlockEntity> SAMPLER = REGISTRY.tile(SamplerBlockEntity::new, "sampler", () -> Set.of(GBlocks.MACHINE.get(GendustryMachineType.SAMPLER).block()));
}
