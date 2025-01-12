package thedarkcolour.gendustry.menu;

import java.util.Objects;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import forestry.core.gui.ContainerLiquidTanks;
import forestry.core.gui.slots.SlotFiltered;
import forestry.core.gui.slots.SlotLiquidIn;
import forestry.core.gui.slots.SlotOutput;
import forestry.core.tiles.TileUtil;

import thedarkcolour.gendustry.blockentity.ReplicatorBlockEntity;
import thedarkcolour.gendustry.blockentity.ReplicatorInventory;
import thedarkcolour.gendustry.registry.GMenus;

public class ReplicatorMenu extends ContainerLiquidTanks<ReplicatorBlockEntity> {
	public ReplicatorMenu(int windowId, Inventory playerInv, ReplicatorBlockEntity tile) {
		super(windowId, GMenus.REPLICATOR.menuType(), playerInv, tile, 8, 94);

		// Template slot
		addSlot(new SlotFiltered(this.tile, ReplicatorInventory.SLOT_TEMPLATE, 80, 23));
		// Liquid DNA input slot
		addSlot(new SlotLiquidIn(this.tile, ReplicatorInventory.SLOT_DNA_CAN_INPUT, 11, 71));
		// Protein input slot
		addSlot(new SlotLiquidIn(this.tile, ReplicatorInventory.SLOT_PROTEIN_CAN_INPUT, 31, 71));
		// Output Queen slot
		addSlot(new SlotOutput(this.tile, ReplicatorInventory.SLOT_OUTPUT, 124, 47));
	}

	public static ReplicatorMenu fromNetwork(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
		ReplicatorBlockEntity tile = TileUtil.getTile(playerInv.player.level(), extraData.readBlockPos(), ReplicatorBlockEntity.class);
		return new ReplicatorMenu(windowId, playerInv, Objects.requireNonNull(tile));
	}
}
