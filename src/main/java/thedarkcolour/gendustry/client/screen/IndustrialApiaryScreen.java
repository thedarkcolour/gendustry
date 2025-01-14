package thedarkcolour.gendustry.client.screen;

import java.util.List;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import forestry.api.apiculture.genetics.BeeLifeStage;
import forestry.api.genetics.capability.IIndividualHandlerItem;
import forestry.core.config.Constants;
import forestry.core.gui.GuiForestryTitled;

import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.blockentity.IndustrialApiaryBlockEntity;
import thedarkcolour.gendustry.data.TranslationKeys;
import thedarkcolour.gendustry.menu.IndustrialApiaryMenu;

public class IndustrialApiaryScreen extends GuiForestryTitled<IndustrialApiaryMenu> {
	public IndustrialApiaryScreen(IndustrialApiaryMenu menu, Inventory playerInv, Component title) {
		super(Gendustry.loc(Constants.TEXTURE_PATH_GUI + "/apiary.png"), menu, playerInv, title);
	}

	@Override
	protected void drawWidgets(GuiGraphics graphics) {
		super.drawWidgets(graphics);
		graphics.blit(this.textureFile, 60, 21, 176, 0, getProgress(38), 18);
	}

	@Override
	protected void addLedgers() {
		addErrorLedger(this.menu.getTile());
		addPowerLedger(this.menu.getTile().getEnergyManager());
		addOwnerLedger(this.menu.getTile());
		addHintLedger(IndustrialApiaryBlockEntity.HINTS_KEY);
		addClimateLedger(this.menu.getTile());
	}

	@Override
	protected void renderTooltip(GuiGraphics pGuiGraphics, int pX, int pY) {
		super.renderTooltip(pGuiGraphics, pX, pY);

		if (isHovering(60, 21, 38, 18, pX, pY)) {
			pGuiGraphics.renderTooltip(this.font, Component.literal(getProgress(100) + "%"), pX, pY);
		}
	}

	private int getProgress(int pixels) {
		int progress = this.menu.getTile().getHealthScaled(pixels);
		if (IIndividualHandlerItem.filter(this.menu.getTile().getBeeInventory().getQueen(), (i, stage) -> stage == BeeLifeStage.QUEEN)) {
			progress = pixels - progress;
		}
		return progress;
	}

	static {
		HINTS.putAll(IndustrialApiaryBlockEntity.HINTS_KEY, List.of(
				TranslationKeys.HINT_INDUSTRIAL_APIARY_USAGE,
				TranslationKeys.HINT_INDUSTRIAL_APIARY_UPGRADES
		));
	}
}
