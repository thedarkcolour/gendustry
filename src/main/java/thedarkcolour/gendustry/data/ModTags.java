package thedarkcolour.gendustry.data;

import net.minecraft.world.item.Item;

import thedarkcolour.gendustry.api.GendustryTags;
import thedarkcolour.gendustry.registry.GItems;
import thedarkcolour.modkit.data.MKTagsProvider;

class ModTags {
	static void addTags(MKTagsProvider<Item> tags) {
		tags.tag(GendustryTags.Items.UPGRADES).add(GItems.UPGRADE.getItems().toArray(Item[]::new));
		tags.tag(GendustryTags.Items.UPGRADES).add(GItems.ELITE_UPGRADE.getItems().toArray(Item[]::new));
	}
}
