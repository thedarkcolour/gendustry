package com.thedarkcolour.gendustry.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import forestry.core.gui.ContainerLiquidTanks;
import forestry.core.gui.slots.SlotFiltered;
import forestry.core.gui.slots.SlotLiquidIn;
import forestry.core.gui.slots.SlotOutput;
import forestry.core.tiles.TileUtil;

import com.thedarkcolour.gendustry.blockentity.MutagenProducerBlockEntity;
import com.thedarkcolour.gendustry.blockentity.ProcessorBlockEntity;
import com.thedarkcolour.gendustry.blockentity.ProcessorInventory;
import com.thedarkcolour.gendustry.registry.GMenus;

public class ProcessorMenu extends ContainerLiquidTanks<ProcessorBlockEntity<?, ?>> {
	public ProcessorMenu(int windowId, Inventory playerInventory, ProcessorBlockEntity<?, ?> tile) {
		super(windowId, GMenus.PROCESSOR.menuType(), playerInventory, tile, 8, 84);

		// Input slot
		addSlot(new SlotFiltered(this.tile, ProcessorInventory.SLOT_INPUT, 14, 41));
		// Can slot
		addSlot(new SlotLiquidIn(this.tile, ProcessorInventory.SLOT_CAN_INPUT, 147, 25));
		// Output slot
		addSlot(new SlotOutput(this.tile, ProcessorInventory.SLOT_CAN_OUTPUT, 147, 61));

		// Labware slot
		if (this.tile.usesLabware) {
			addSlot(new SlotFiltered(this.tile, ProcessorInventory.SLOT_LABWARE, 64, 19));
		}
	}

	public static ProcessorMenu fromNetwork(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
		ProcessorBlockEntity<?, ?> tile = TileUtil.getTile(playerInv.player.level(), extraData.readBlockPos(), ProcessorBlockEntity.class);
		return new ProcessorMenu(windowId, playerInv, tile);
	}
}
