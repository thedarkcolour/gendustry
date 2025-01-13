package thedarkcolour.gendustry.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import forestry.core.config.Constants;
import forestry.core.gui.GuiForestryTitled;

import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.menu.IndustrialApiaryMenu;

public class IndustrialApiaryScreen extends GuiForestryTitled<IndustrialApiaryMenu> {
	public IndustrialApiaryScreen(IndustrialApiaryMenu menu, Inventory playerInv, Component title) {
		super(Gendustry.loc(Constants.TEXTURE_PATH_GUI + "/apiary.png"), menu, playerInv, title);
	}

	@Override
	protected void drawWidgets(GuiGraphics graphics) {
		super.drawWidgets(graphics);
		int progress = this.menu.getTile().getHealthScaled(36);
		graphics.blit(this.textureFile, 60, 21, 176, 0, progress, 18);
	}

	@Override
	protected void addLedgers() {
		addErrorLedger(this.menu.getTile());
		addPowerLedger(this.menu.getTile().getEnergyManager());
	}
}
