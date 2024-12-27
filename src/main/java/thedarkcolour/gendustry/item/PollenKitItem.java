package thedarkcolour.gendustry.item;

import forestry.api.IForestryApi;
import forestry.api.genetics.pollen.IPollen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class PollenKitItem extends Item {
    public PollenKitItem() {
        super(new Item.Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();

        if (!level.isClientSide) {
            Player player = ctx.getPlayer();
            BlockPos pos = ctx.getClickedPos();
            IPollen<?> pollen = IForestryApi.INSTANCE.getPollenManager().getPollen(level, pos, player);

            if (pollen != null && player != null) {
                // Generate pollen item and consume kit
                ItemStack stack = pollen.createStack();
                if (!player.getInventory().add(stack)) {
                    player.drop(stack, false);
                }
                ctx.getItemInHand().shrink(1);
            }

            return InteractionResult.CONSUME;
        }

        return InteractionResult.SUCCESS;
    }
}
