package com.thedarkcolour.gendustry.client.screen;

import java.util.List;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import forestry.core.config.Constants;
import forestry.core.gui.GuiForestryTitled;
import forestry.core.gui.widgets.TankWidget;

import com.thedarkcolour.gendustry.Gendustry;
import com.thedarkcolour.gendustry.blockentity.DnaExtractorBlockEntity;
import com.thedarkcolour.gendustry.blockentity.MutagenProducerBlockEntity;
import com.thedarkcolour.gendustry.blockentity.ProcessorBlockEntity;
import com.thedarkcolour.gendustry.blockentity.ProteinLiquefierBlockEntity;
import com.thedarkcolour.gendustry.data.TranslationKeys;
import com.thedarkcolour.gendustry.menu.ProcessorMenu;

public class ProcessorScreen extends GuiForestryTitled<ProcessorMenu> {
	private final ProcessorBlockEntity<?, ?> tile;

	public ProcessorScreen(ProcessorMenu menu, Inventory playerInv, Component title) {
		super(Gendustry.loc(Constants.TEXTURE_PATH_GUI + "/mutagen_producer.png"), menu, playerInv, title);

		this.tile = menu.getTile();
		this.widgetManager.add(new TankWidget(this.widgetManager, 122, 19, 0));
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
		super.renderBg(graphics, partialTicks, mouseX, mouseY);
	}

	@Override
	protected void drawWidgets(GuiGraphics graphics) {
		super.drawWidgets(graphics);
		int progress = this.tile.getProgressScaled(55);
		graphics.blit(this.textureFile, 48, 40, 176, 60, progress, 18);

		if (this.tile.usesLabware) {
			graphics.blit(this.textureFile, 63, 18, 176, 78, 18, 18);
		}
	}

	@Override
	protected void addLedgers() {
		addErrorLedger(this.tile);
		addPowerLedger(this.tile.getEnergyManager());
		addHintLedger(this.tile.getHintsKey());
	}

	static {
		HINTS.putAll(MutagenProducerBlockEntity.HINTS_KEY, List.of(
				TranslationKeys.HINT_MUTAGEN_USAGE,
				TranslationKeys.HINT_MUTAGEN_INGREDIENTS
		));
		HINTS.putAll(DnaExtractorBlockEntity.HINTS_KEY, List.of(
				TranslationKeys.HINT_DNA_USAGE,
				TranslationKeys.HINT_DNA_INGREDIENTS
		));
		HINTS.putAll(ProteinLiquefierBlockEntity.HINTS_KEY, List.of(
				TranslationKeys.HINT_PROTEIN_USAGE,
				TranslationKeys.HINT_PROTEIN_INGREDIENTS
		));
	}
}
