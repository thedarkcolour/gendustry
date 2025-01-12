package thedarkcolour.gendustry.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

import forestry.core.gui.ContainerTile;
import forestry.core.gui.slots.SlotFiltered;
import forestry.core.gui.slots.SlotOutput;
import forestry.core.tiles.IFilterSlotDelegate;
import forestry.core.tiles.TilePowered;
import forestry.core.tiles.TileUtil;

import thedarkcolour.gendustry.blockentity.GeneticTransposerBlockEntity;
import thedarkcolour.gendustry.blockentity.IHintTile;
import thedarkcolour.gendustry.blockentity.ImprinterBlockEntity;
import thedarkcolour.gendustry.blockentity.SamplerBlockEntity;
import thedarkcolour.gendustry.blockentity.SamplerInventory;
import thedarkcolour.gendustry.registry.GMenus;

// Reused by Sampler, Imprinter, and Genetic Transposer
public class ThreeInputMenu<T extends TilePowered & Container & IFilterSlotDelegate & IHintTile> extends ContainerTile<T> {
	public ThreeInputMenu(int windowId, Inventory playerInventory, T tile, MenuType<?> menuType) {
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

	public static ThreeInputMenu<SamplerBlockEntity> samplerFromNetwork(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
		SamplerBlockEntity tile = TileUtil.getTile(playerInv.player.level(), extraData.readBlockPos(), SamplerBlockEntity.class);
		return ThreeInputMenu.sampler(windowId, playerInv, tile);
	}

	public static ThreeInputMenu<ImprinterBlockEntity> imprinterFromNetwork(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
		ImprinterBlockEntity tile = TileUtil.getTile(playerInv.player.level(), extraData.readBlockPos(), ImprinterBlockEntity.class);
		return ThreeInputMenu.imprinter(windowId, playerInv, tile);
	}

	public static ThreeInputMenu<GeneticTransposerBlockEntity> geneticTransposerFromNetwork(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
		GeneticTransposerBlockEntity tile = TileUtil.getTile(playerInv.player.level(), extraData.readBlockPos(), GeneticTransposerBlockEntity.class);
		return ThreeInputMenu.geneticTransposer(windowId, playerInv, tile);
	}

	public static ThreeInputMenu<SamplerBlockEntity> sampler(int windowId, Inventory playerInventory, SamplerBlockEntity tile) {
		return new ThreeInputMenu<>(windowId, playerInventory, tile, GMenus.SAMPLER.menuType());
	}

	public static ThreeInputMenu<ImprinterBlockEntity> imprinter(int windowId, Inventory playerInventory, ImprinterBlockEntity tile) {
		return new ThreeInputMenu<>(windowId, playerInventory, tile, GMenus.IMPRINTER.menuType());
	}

	public static ThreeInputMenu<GeneticTransposerBlockEntity> geneticTransposer(int windowId, Inventory playerInventory, GeneticTransposerBlockEntity tile) {
		return new ThreeInputMenu<>(windowId, playerInventory, tile, GMenus.GENETIC_TRANSPOSER.menuType());
	}
}
