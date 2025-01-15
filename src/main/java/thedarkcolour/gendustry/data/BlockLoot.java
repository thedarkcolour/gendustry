package thedarkcolour.gendustry.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import thedarkcolour.gendustry.block.GendustryMachineBlock;
import thedarkcolour.gendustry.registry.GBlocks;

// Copied from: https://github.com/thedarkcolour/ExDeorum/blob/1.20.1/src/main/java/thedarkcolour/exdeorum/data/BlockLoot.java
class BlockLoot extends BlockLootSubProvider {
	private final List<Block> added = new ArrayList<>();

	BlockLoot() {
		super(Set.of(), FeatureFlags.DEFAULT_FLAGS);
	}

	@Override
	protected void generate() {
		for (GendustryMachineBlock block : GBlocks.MACHINE.getBlocks()) {
			dropSelf(block);
		}
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return this.added;
	}

	@Override
	protected void add(Block block, LootTable.Builder builder) {
		super.add(block, builder);
		this.added.add(block);
	}
}
