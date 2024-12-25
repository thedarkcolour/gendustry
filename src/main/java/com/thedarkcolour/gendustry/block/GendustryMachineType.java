package com.thedarkcolour.gendustry.block;

import java.util.Locale;

import forestry.core.blocks.IBlockType;
import forestry.core.blocks.IMachineProperties;
import forestry.core.blocks.MachineProperties;
import forestry.core.tiles.IForestryTicker;
import forestry.core.tiles.TileForestry;
import forestry.modules.features.FeatureTileType;

import com.thedarkcolour.gendustry.blockentity.IndustrialApiaryBlockEntity;
import com.thedarkcolour.gendustry.blockentity.MutagenProducerBlockEntity;
import com.thedarkcolour.gendustry.registry.GBlockEntities;

public enum GendustryMachineType implements IBlockType {
	INDUSTRIAL_APIARY(GBlockEntities.INDUSTRIAL_APIARY, IndustrialApiaryBlockEntity::serverTick),
	MUTAGEN_PRODUCER(GBlockEntities.MUTAGEN_PRODUCER, MutagenProducerBlockEntity::serverTick);

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
		return getMachineProperties().getSerializedName();
	}
}
