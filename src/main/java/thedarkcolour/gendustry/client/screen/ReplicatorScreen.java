package thedarkcolour.gendustry.client.screen;

import java.util.List;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import forestry.core.config.Constants;
import forestry.core.gui.GuiForestryTitled;
import forestry.core.gui.widgets.TankWidget;

import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.blockentity.ReplicatorBlockEntity;
import thedarkcolour.gendustry.data.TranslationKeys;
import thedarkcolour.gendustry.menu.ReplicatorMenu;

public class ReplicatorScreen extends GuiForestryTitled<ReplicatorMenu> {
	private final ReplicatorBlockEntity tile;

	public ReplicatorScreen(ReplicatorMenu menu, Inventory playerInv, Component title) {
		super(Gendustry.loc(Constants.TEXTURE_PATH_GUI + "/replicator.png"), menu, playerInv, title);

		this.tile = menu.getTile();
		this.widgetManager.add(new TankWidget(this.widgetManager, 11, 8, 0));
		this.widgetManager.add(new TankWidget(this.widgetManager, 31, 8, 1));
		this.imageHeight = 176;
	}

	@Override
	protected void addLedgers() {
		addErrorLedger(this.tile);
		addPowerLedger(this.tile.getEnergyManager());
		addHintLedger(ReplicatorBlockEntity.HINTS_KEY);
	}

	@Override
	protected void drawWidgets(GuiGraphics graphics) {
		super.drawWidgets(graphics);
		int progress = this.tile.getProgressScaled(42);
		graphics.blit(this.textureFile, 70, 46, 176, 60, progress, 18);
	}

	static {
		HINTS.putAll(ReplicatorBlockEntity.HINTS_KEY, List.of(
				TranslationKeys.HINT_DNA_USAGE,
				TranslationKeys.HINT_PROTEIN_USAGE,
				TranslationKeys.HINT_REPLICATOR_USAGE
		));
	}
}
