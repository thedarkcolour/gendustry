package thedarkcolour.gendustry.menu;

import java.util.Objects;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import forestry.api.core.HumidityType;
import forestry.api.core.TemperatureType;
import forestry.core.gui.ContainerTile;
import forestry.core.gui.slots.SlotFiltered;
import forestry.core.gui.slots.SlotOutput;
import forestry.core.network.packets.PacketGuiStream;
import forestry.core.tiles.TileUtil;

import thedarkcolour.gendustry.blockentity.IndustrialApiaryBlockEntity;
import thedarkcolour.gendustry.blockentity.IndustrialApiaryInventory;
import thedarkcolour.gendustry.registry.GMenus;

public class IndustrialApiaryMenu extends ContainerTile<IndustrialApiaryBlockEntity> {
	private int previousBeePercent = -1;
	private TemperatureType previousTemperature;
	private HumidityType previousHumidity;

	public IndustrialApiaryMenu(int windowId, Inventory playerInv, IndustrialApiaryBlockEntity tile) {
		super(windowId, GMenus.INDUSTRIAL_APIARY.menuType(), playerInv, tile, 8, 84);

		addSlot(new SlotFiltered(this.tile, IndustrialApiaryInventory.QUEEN, 26, 29));

		addSlot(new SlotFiltered(this.tile, IndustrialApiaryInventory.DRONE, 26, 52));

		for (int i = 0; i < IndustrialApiaryInventory.UPGRADE_SLOT_COUNT; i++) {
			int x = 62 + (i % 2) * 18;
			int y = 43 + (i / 2) * 18;

			addSlot(new SlotFiltered(this.tile, IndustrialApiaryInventory.UPGRADE_SLOT_START + i, x, y));
		}

		for (int i = 0; i < IndustrialApiaryInventory.OUTPUT_SLOT_COUNT; i++) {
			int x = 116 + (i % 3) * 18;
			int y = 25 + (i / 3) * 18;
			addSlot(new SlotOutput(this.tile, IndustrialApiaryInventory.OUTPUT_SLOT_START + i, x, y));
		}

		if (!tile.getLevel().isClientSide) {
			tile.getBeekeepingLogic().clearCachedValues();
		}
	}

	@Override
	public void broadcastChanges() {
		super.broadcastChanges();

		int beeProgressPercent = this.tile.getBeekeepingLogic().getBeeProgressPercent();
		TemperatureType temperature = this.tile.temperature();
		HumidityType humidity = this.tile.humidity();

		if (this.previousBeePercent != beeProgressPercent || this.previousTemperature != temperature || this.previousHumidity != humidity) {
			this.previousBeePercent = beeProgressPercent;
			this.previousTemperature = temperature;
			this.previousHumidity = humidity;

			PacketGuiStream packet = new PacketGuiStream(this.tile);
			sendPacketToListeners(packet);
		}
	}

	public static IndustrialApiaryMenu fromNetwork(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
		IndustrialApiaryBlockEntity tile = TileUtil.getTile(playerInv.player.level(), extraData.readBlockPos(), IndustrialApiaryBlockEntity.class);
		return new IndustrialApiaryMenu(windowId, playerInv, Objects.requireNonNull(tile));
	}
}
