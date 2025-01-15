package thedarkcolour.gendustry.data;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import thedarkcolour.gendustry.api.GendustryTags;
import thedarkcolour.gendustry.registry.GBlocks;
import thedarkcolour.gendustry.registry.GItems;
import thedarkcolour.modkit.data.MKTagsProvider;

class ModTags {
	static void addItemTags(MKTagsProvider<Item> tags) {
		tags.tag(GendustryTags.Items.UPGRADES).add(GItems.UPGRADE.getItems().toArray(Item[]::new));
		tags.tag(GendustryTags.Items.UPGRADES).add(GItems.ELITE_UPGRADE.getItems().toArray(Item[]::new));
	}

	static void addBlockTags(MKTagsProvider<Block> tags) {
		tags.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(GBlocks.MACHINE.blockArray());
	}
}
