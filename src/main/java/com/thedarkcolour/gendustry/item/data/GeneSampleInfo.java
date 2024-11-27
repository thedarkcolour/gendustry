package com.thedarkcolour.gendustry.item.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import forestry.api.IForestryApi;
import forestry.api.genetics.ISpeciesType;
import forestry.api.genetics.alleles.IAllele;
import forestry.api.genetics.alleles.IChromosome;
import net.minecraft.resources.ResourceLocation;

public record GeneSampleInfo(ISpeciesType<?, ?> type, IChromosome<?> chromosome, IAllele allele) {
    public static final Codec<GeneSampleInfo> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                ResourceLocation.CODEC
                        .xmap(IForestryApi.INSTANCE.getGeneticManager()::getSpeciesType, ISpeciesType::id)
                        .fieldOf("type")
                        .forGetter(o -> o.type().cast()),
                IForestryApi.INSTANCE.getAlleleManager().chromosomeCodec()
                        .fieldOf("chromosome")
                        .forGetter(GeneSampleInfo::chromosome),
                IAllele.CODEC
                        .fieldOf("allele")
                        .forGetter(GeneSampleInfo::allele)
        ).apply(instance, GeneSampleInfo::new);
    });
}
