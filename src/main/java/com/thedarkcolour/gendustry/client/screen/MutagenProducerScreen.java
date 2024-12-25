package com.thedarkcolour.gendustry.client.screen;

import java.util.List;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import forestry.core.config.Constants;
import forestry.core.gui.GuiForestryTitled;
import forestry.core.gui.widgets.TankWidget;

import com.thedarkcolour.gendustry.Gendustry;
import com.thedarkcolour.gendustry.blockentity.MutagenProducerBlockEntity;
import com.thedarkcolour.gendustry.data.TranslationKeys;
import com.thedarkcolour.gendustry.menu.MutagenProducerMenu;

public class MutagenProducerScreen extends GuiForestryTitled<MutagenProducerMenu> {
	private static final String HINTS_KEY = "gendustry.mutagen_producer";

	private final MutagenProducerBlockEntity tile;

	public MutagenProducerScreen(MutagenProducerMenu menu, Inventory playerInv, Component title) {
		super(Gendustry.loc(Constants.TEXTURE_PATH_GUI + "/mutagen_producer.png"), menu, playerInv, title);

		this.tile = menu.getTile();
		this.widgetManager.add(new TankWidget(this.widgetManager, 80, 14, 0));
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
		super.renderBg(graphics, partialTicks, mouseX, mouseY);
	}

	@Override
	protected void addLedgers() {
		addErrorLedger(tile);
		addPowerLedger(tile.getEnergyManager());
		addHintLedger(HINTS_KEY);
	}

	static {
		HINTS.putAll(HINTS_KEY, List.of(
				TranslationKeys.HINT_MUTAGEN_USAGE,
				TranslationKeys.HINT_MUTAGEN_INGREDIENTS
		));
	}
}
