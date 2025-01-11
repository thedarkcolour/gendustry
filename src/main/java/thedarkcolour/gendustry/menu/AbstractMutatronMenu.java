package thedarkcolour.gendustry.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

import forestry.core.gui.ContainerLiquidTanks;
import forestry.core.gui.slots.SlotFiltered;
import forestry.core.gui.slots.SlotLiquidIn;
import forestry.core.gui.slots.SlotOutput;

import thedarkcolour.gendustry.blockentity.AbstractMutatronBlockEntity;
import thedarkcolour.gendustry.blockentity.MutatronInventory;

public class AbstractMutatronMenu<T extends AbstractMutatronBlockEntity> extends ContainerLiquidTanks<T> {
	protected AbstractMutatronMenu(int windowId, MenuType<?> menuType, Inventory playerInv, T tile) {
		super(windowId, menuType, playerInv, tile, 8, 94);

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
}
