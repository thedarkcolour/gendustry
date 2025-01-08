package thedarkcolour.gendustry.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;

import forestry.core.gui.ContainerTile;
import forestry.core.gui.slots.SlotFiltered;
import forestry.core.gui.slots.SlotOutput;
import forestry.core.tiles.IFilterSlotDelegate;
import forestry.core.tiles.TilePowered;
import forestry.core.tiles.TileUtil;

import thedarkcolour.gendustry.blockentity.ImprinterBlockEntity;
import thedarkcolour.gendustry.blockentity.SamplerBlockEntity;
import thedarkcolour.gendustry.blockentity.SamplerInventory;
import thedarkcolour.gendustry.registry.GMenus;

// Menus have same code, so we reuse it for two machines
public class SamplerImprinterMenu<T extends TilePowered & Container & IFilterSlotDelegate> extends ContainerTile<T> {
	public SamplerImprinterMenu(int windowId, Inventory playerInventory, T tile, MenuType<?> menuType) {
		super(windowId, menuType, playerInventory, tile, 8, 84);

		// Input slot
		addSlot(new SlotFiltered(this.tile, SamplerInventory.SLOT_INPUT, 32, 49));
		// Blank sample slot (template slot for Imprinter)
		addSlot(new SlotFiltered(this.tile, SamplerInventory.SLOT_BLANK_SAMPLE, 65, 28));
		// Labware slot
		addSlot(new SlotFiltered(this.tile, SamplerInventory.SLOT_LABWARE, 89, 28));
		// Output slot
		addSlot(new SlotOutput(this.tile, SamplerInventory.SLOT_OUTPUT, 128, 49));
	}

	public static SamplerImprinterMenu<SamplerBlockEntity> samplerFromNetwork(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
		SamplerBlockEntity tile = TileUtil.getTile(playerInv.player.level(), extraData.readBlockPos(), SamplerBlockEntity.class);
		return SamplerImprinterMenu.sampler(windowId, playerInv, tile);
	}

	public static SamplerImprinterMenu<ImprinterBlockEntity> imprinterFromNetwork(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
		ImprinterBlockEntity tile = TileUtil.getTile(playerInv.player.level(), extraData.readBlockPos(), ImprinterBlockEntity.class);
		return SamplerImprinterMenu.imprinter(windowId, playerInv, tile);
	}

	public static SamplerImprinterMenu<SamplerBlockEntity> sampler(int windowId, Inventory playerInventory, SamplerBlockEntity tile) {
		return new SamplerImprinterMenu<>(windowId, playerInventory, tile, GMenus.SAMPLER.menuType());
	}

	public static SamplerImprinterMenu<ImprinterBlockEntity> imprinter(int windowId, Inventory playerInventory, ImprinterBlockEntity tile) {
		return new SamplerImprinterMenu<>(windowId, playerInventory, tile, GMenus.IMPRINTER.menuType());
	}
}
