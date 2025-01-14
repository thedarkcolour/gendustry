package thedarkcolour.gendustry.api;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import thedarkcolour.gendustry.Gendustry;

public class GendustryTags {
	public static class Items {
		public static final TagKey<Item> UPGRADES = itemTag("upgrades");
	}

	private static TagKey<Item> itemTag(String name) {
		return ItemTags.create(Gendustry.loc(name));
	}
}
