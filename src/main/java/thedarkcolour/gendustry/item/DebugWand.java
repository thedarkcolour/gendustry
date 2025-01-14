package thedarkcolour.gendustry.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import thedarkcolour.gendustry.blockentity.IndustrialApiaryBlockEntity;

public class DebugWand extends Item {
	public DebugWand() {
		super(new Properties().stacksTo(1));
	}

	@Override
	public InteractionResult useOn(UseOnContext ctx) {
		Level level = ctx.getLevel();
		BlockPos pos = ctx.getClickedPos();

		if (!level.isClientSide && level.getBlockEntity(pos) instanceof IndustrialApiaryBlockEntity apiary) {
			ctx.getPlayer().sendSystemMessage(Component.literal("Yep, that's an Industrial Apiary."));
		}

		return InteractionResult.sidedSuccess(level.isClientSide);
	}
}
