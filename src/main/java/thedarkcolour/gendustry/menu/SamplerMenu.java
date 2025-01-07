package thedarkcolour.gendustry.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import forestry.core.gui.ContainerTile;
import forestry.core.gui.slots.SlotFiltered;
import forestry.core.gui.slots.SlotOutput;
import forestry.core.tiles.TileUtil;

import thedarkcolour.gendustry.blockentity.SamplerBlockEntity;
import thedarkcolour.gendustry.blockentity.SamplerInventory;
import thedarkcolour.gendustry.registry.GMenus;

public class SamplerMenu extends ContainerTile<SamplerBlockEntity> {
	public SamplerMenu(int windowId, Inventory playerInventory, SamplerBlockEntity tile) {
		super(windowId, GMenus.SAMPLER.menuType(), playerInventory, tile, 8, 84);

		// Input slot
		addSlot(new SlotFiltered(this.tile, SamplerInventory.SLOT_INPUT, 32, 49));
		// Blank sample slot
		addSlot(new SlotFiltered(this.tile, SamplerInventory.SLOT_BLANK_SAMPLE, 65, 28));
		// Labware slot
		addSlot(new SlotFiltered(this.tile, SamplerInventory.SLOT_LABWARE, 89, 28));
		// Output slot
		addSlot(new SlotOutput(this.tile, SamplerInventory.SLOT_OUTPUT, 128, 49));
	}

	public static SamplerMenu fromNetwork(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
		SamplerBlockEntity tile = TileUtil.getTile(playerInv.player.level(), extraData.readBlockPos(), SamplerBlockEntity.class);
		return new SamplerMenu(windowId, playerInv, tile);
	}
}
