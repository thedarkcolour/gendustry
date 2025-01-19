package thedarkcolour.gendustry.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import forestry.core.gui.ContainerLiquidTanks;
import forestry.core.gui.slots.SlotFiltered;
import forestry.core.gui.slots.SlotLiquidIn;
import forestry.core.gui.slots.SlotOutput;
import forestry.core.tiles.TileUtil;

import thedarkcolour.gendustry.blockentity.ProducerBlockEntity;
import thedarkcolour.gendustry.blockentity.ProducerInventory;
import thedarkcolour.gendustry.registry.GMenus;

public class ProducerMenu extends ContainerLiquidTanks<ProducerBlockEntity<?, ?>> {
	public ProducerMenu(int windowId, Inventory playerInventory, ProducerBlockEntity<?, ?> tile) {
		super(windowId, GMenus.PROCESSOR.menuType(), playerInventory, tile, 8, 84);

		// Input slot
		addSlot(new SlotFiltered(this.tile, ProducerInventory.SLOT_INPUT, 14, 41));
		// Can slot
		addSlot(new SlotLiquidIn(this.tile, ProducerInventory.SLOT_CAN_INPUT, 147, 25));
		// Output slot
		addSlot(new SlotOutput(this.tile, ProducerInventory.SLOT_CAN_OUTPUT, 147, 61));

		// Labware slot
		if (this.tile.usesLabware) {
			addSlot(new SlotFiltered(this.tile, ProducerInventory.SLOT_LABWARE, 64, 19));
		}
	}

	public static ProducerMenu fromNetwork(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
		ProducerBlockEntity<?, ?> tile = TileUtil.getTile(playerInv.player.level(), extraData.readBlockPos(), ProducerBlockEntity.class);
		return new ProducerMenu(windowId, playerInv, tile);
	}
}
