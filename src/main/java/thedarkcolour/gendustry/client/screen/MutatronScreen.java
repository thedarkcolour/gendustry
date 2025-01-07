package thedarkcolour.gendustry.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import forestry.core.config.Constants;
import forestry.core.gui.GuiForestryTitled;
import forestry.core.gui.widgets.TankWidget;

import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.blockentity.MutatronBlockEntity;
import thedarkcolour.gendustry.menu.MutatronMenu;

public class MutatronScreen extends GuiForestryTitled<MutatronMenu> {
	private final MutatronBlockEntity tile;

	public MutatronScreen(MutatronMenu menu, Inventory playerInv, Component title) {
		super(Gendustry.loc(Constants.TEXTURE_PATH_GUI + "/mutatron.png"), menu, playerInv, title);

		this.tile = menu.getTile();
		this.widgetManager.add(new TankWidget(this.widgetManager, 11, 8, 0));
		this.imageHeight = 176;
	}

	@Override
	protected void drawWidgets(GuiGraphics graphics) {
		super.drawWidgets(graphics);
		int progress = this.tile.getProgressScaled(55);
		graphics.blit(this.textureFile, 68, 38, 176, 60, progress, 18);
	}

	@Override
	protected void addLedgers() {
		addErrorLedger(this.tile);
		addPowerLedger(this.tile.getEnergyManager());
		// todo hints
	}
}
