package com.thedarkcolour.gendustry.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import forestry.core.gui.ContainerLiquidTanks;
import forestry.core.gui.slots.SlotFiltered;
import forestry.core.gui.slots.SlotLiquidIn;
import forestry.core.gui.slots.SlotOutput;
import forestry.core.tiles.TileUtil;

import com.thedarkcolour.gendustry.blockentity.MutagenProducerBlockEntity;
import com.thedarkcolour.gendustry.blockentity.MutagenProducerInventory;
import com.thedarkcolour.gendustry.registry.GMenus;

public class MutagenProducerMenu extends ContainerLiquidTanks<MutagenProducerBlockEntity> {
	public static MutagenProducerMenu fromNetwork(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
		MutagenProducerBlockEntity tile = TileUtil.getTile(playerInv.player.level(), extraData.readBlockPos(), MutagenProducerBlockEntity.class);
		return new MutagenProducerMenu(windowId, playerInv, tile);
	}

	public MutagenProducerMenu(int windowId, Inventory playerInv, MutagenProducerBlockEntity tile) {
		super(windowId, GMenus.MUTAGEN_PRODUCER.menuType(), playerInv, tile, 8, 84);

		// Input slot
		addSlot(new SlotFiltered(this.tile, MutagenProducerInventory.SLOT_INPUT, 14, 41));
		// Can slot
		addSlot(new SlotLiquidIn(this.tile, MutagenProducerInventory.SLOT_CAN_INPUT, 147, 25));
		// Output slot
		addSlot(new SlotOutput(this.tile, MutagenProducerInventory.SLOT_CAN_OUTPUT, 147, 61));
	}
}
