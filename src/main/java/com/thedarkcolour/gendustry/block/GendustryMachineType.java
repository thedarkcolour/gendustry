package com.thedarkcolour.gendustry.block;

import java.util.Locale;

import forestry.core.blocks.IBlockType;
import forestry.core.blocks.IMachineProperties;
import forestry.core.blocks.MachineProperties;
import forestry.core.tiles.IForestryTicker;
import forestry.core.tiles.TileForestry;
import forestry.modules.features.FeatureTileType;

import com.thedarkcolour.gendustry.blockentity.DnaExtractorBlockEntity;
import com.thedarkcolour.gendustry.blockentity.IndustrialApiaryBlockEntity;
import com.thedarkcolour.gendustry.blockentity.MutagenProducerBlockEntity;
import com.thedarkcolour.gendustry.blockentity.ProteinLiquefierBlockEntity;
import com.thedarkcolour.gendustry.registry.GBlockEntities;

public enum GendustryMachineType implements IBlockType {
	INDUSTRIAL_APIARY(GBlockEntities.INDUSTRIAL_APIARY, IndustrialApiaryBlockEntity::serverTick),
	MUTAGEN_PRODUCER(GBlockEntities.MUTAGEN_PRODUCER, MutagenProducerBlockEntity::serverTick),
	DNA_EXTRACTOR(GBlockEntities.DNA_EXTRACTOR, DnaExtractorBlockEntity::serverTick),
	PROTEIN_LIQUEFIER(GBlockEntities.PROTEIN_LIQUEFIER, ProteinLiquefierBlockEntity::serverTick);

	private final IMachineProperties<?> properties;

	<T extends TileForestry> GendustryMachineType(FeatureTileType<T> teClass, IForestryTicker<T> serverTicker) {
		String name = name().toLowerCase(Locale.ENGLISH);

		this.properties = new MachineProperties.Builder<>(teClass, name)
				.setServerTicker(serverTicker)
				.create();
	}

	@Override
	public IMachineProperties<?> getMachineProperties() {
		return this.properties;
	}

	@Override
	public String getSerializedName() {
		return this.properties.getSerializedName();
	}
}
