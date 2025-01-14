package thedarkcolour.gendustry.item;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;
import thedarkcolour.gendustry.data.TranslationKeys;

public class GendustryUpgradeItem extends Item {
	private final IGendustryUpgradeType type;
	@Nullable
	private String descriptionKey;

	public GendustryUpgradeItem(IGendustryUpgradeType type) {
		super(new Properties().stacksTo(type.maxStackSize()));

		this.type = type;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag advanced) {
		if (this.descriptionKey == null) {
			this.descriptionKey = getOrCreateDescriptionId() + ".tooltip";
		}
		tooltip.add(Component.translatable(this.descriptionKey).withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.empty());
		tooltip.add(Component.translatable(TranslationKeys.UPGRADE_ENERGY_COST, Component.literal(String.valueOf(this.type.energyCost())).withStyle(ChatFormatting.YELLOW)).withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable(TranslationKeys.UPGRADE_STACK_LIMIT, Component.literal(String.valueOf(this.type.maxStackSize())).withStyle(ChatFormatting.YELLOW)).withStyle(ChatFormatting.GRAY));
	}

	public IGendustryUpgradeType getType() {
		return this.type;
	}
}
