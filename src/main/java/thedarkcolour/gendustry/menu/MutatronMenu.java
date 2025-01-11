package thedarkcolour.gendustry.menu;

import java.util.Objects;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import forestry.core.tiles.TileUtil;

import thedarkcolour.gendustry.blockentity.MutatronBlockEntity;
import thedarkcolour.gendustry.registry.GMenus;

public class MutatronMenu extends AbstractMutatronMenu<MutatronBlockEntity> {
	public MutatronMenu(int windowId, Inventory playerInv, MutatronBlockEntity tile) {
		super(windowId, GMenus.MUTATRON.menuType(), playerInv, tile);
	}

	public static MutatronMenu fromNetwork(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
		MutatronBlockEntity tile = TileUtil.getTile(playerInv.player.level(), extraData.readBlockPos(), MutatronBlockEntity.class);
		return new MutatronMenu(windowId, playerInv, Objects.requireNonNull(tile));
	}
}
