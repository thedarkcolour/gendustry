package thedarkcolour.gendustry.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import forestry.core.gui.ContainerLiquidTanks;
import forestry.core.gui.slots.SlotFiltered;
import forestry.core.gui.slots.SlotLiquidIn;
import forestry.core.gui.slots.SlotOutput;
import forestry.core.tiles.TileUtil;

import thedarkcolour.gendustry.blockentity.MutatronBlockEntity;
import thedarkcolour.gendustry.blockentity.MutatronInventory;
import thedarkcolour.gendustry.registry.GMenus;

public class MutatronMenu extends ContainerLiquidTanks<MutatronBlockEntity> {
	public MutatronMenu(int windowId, Inventory playerInventory, MutatronBlockEntity tile) {
		super(windowId, GMenus.MUTATRON.menuType(), playerInventory, tile, 8, 94);

		// Princess slot
		addSlot(new SlotFiltered(this.tile, MutatronInventory.SLOT_PRIMARY, 41, 26));
		// Drone slot
		addSlot(new SlotFiltered(this.tile, MutatronInventory.SLOT_SECONDARY, 41, 49));
		// Labware slot
		addSlot(new SlotFiltered(this.tile, MutatronInventory.SLOT_LABWARE, 84, 20));
		// Can slot
		addSlot(new SlotLiquidIn(this.tile, MutatronInventory.SLOT_CAN_INPUT, 11, 71));
		// Output Queen slot
		addSlot(new SlotOutput(this.tile, MutatronInventory.SLOT_RESULT, 133, 39));
	}

	public static MutatronMenu fromNetwork(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
		MutatronBlockEntity tile = TileUtil.getTile(playerInv.player.level(), extraData.readBlockPos(), MutatronBlockEntity.class);
		return new MutatronMenu(windowId, playerInv, tile);
	}
}
