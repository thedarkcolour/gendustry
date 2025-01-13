package thedarkcolour.gendustry.item;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;

public class GendustryUpgradeItem extends Item {
	public GendustryUpgradeItem(IGendustryUpgradeType type) {
		super(new Properties().stacksTo(type.maxStackSize()));
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag advanced) {
	}
}
