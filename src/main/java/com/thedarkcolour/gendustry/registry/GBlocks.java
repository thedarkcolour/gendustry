package com.thedarkcolour.gendustry.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import forestry.modules.features.FeatureBlockGroup;
import forestry.modules.features.FeatureProvider;
import forestry.modules.features.IFeatureRegistry;
import forestry.modules.features.ModFeatureRegistry;

import com.thedarkcolour.gendustry.Gendustry;
import com.thedarkcolour.gendustry.block.GendustryMachineBlock;
import com.thedarkcolour.gendustry.block.GendustryMachineType;

@FeatureProvider
public class GBlocks {
	private static final IFeatureRegistry REGISTRY = ModFeatureRegistry.get(Gendustry.MODULE_ID);

	public static final FeatureBlockGroup<GendustryMachineBlock, GendustryMachineType> MACHINE = REGISTRY
			.blockGroup(GendustryMachineBlock::new, GendustryMachineType.values())
			.item(b -> new BlockItem(b, new Item.Properties()))
			.create();
}
