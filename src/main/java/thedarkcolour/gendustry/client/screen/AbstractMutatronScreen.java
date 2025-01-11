package thedarkcolour.gendustry.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import forestry.core.gui.GuiForestryTitled;
import forestry.core.gui.widgets.TankWidget;

import thedarkcolour.gendustry.blockentity.AbstractMutatronBlockEntity;
import thedarkcolour.gendustry.menu.AbstractMutatronMenu;

public class AbstractMutatronScreen<M extends AbstractMutatronMenu<?>> extends GuiForestryTitled<M> {
	protected final AbstractMutatronBlockEntity tile;

	public AbstractMutatronScreen(ResourceLocation texture, M menu, Inventory playerInv, Component title) {
		super(texture, menu, playerInv, title);

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
