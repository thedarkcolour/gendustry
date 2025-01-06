package thedarkcolour.gendustry.client.screen;

import java.util.List;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import forestry.core.config.Constants;
import forestry.core.gui.GuiForestryTitled;

import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.blockentity.SamplerBlockEntity;
import thedarkcolour.gendustry.data.TranslationKeys;
import thedarkcolour.gendustry.menu.SamplerMenu;

public class SamplerScreen extends GuiForestryTitled<SamplerMenu> {
	public static final String HINTS_KEY = "gendustry.sampler";

	private final SamplerBlockEntity tile;

	public SamplerScreen(SamplerMenu menu, Inventory inv, Component title) {
		super(Gendustry.loc(Constants.TEXTURE_PATH_GUI + "/sampler.png"), menu, inv, title);

		this.tile = menu.getTile();
	}

	@Override
	protected void drawWidgets(GuiGraphics graphics) {
		super.drawWidgets(graphics);
		int progress = this.tile.getProgressScaled(68);
		graphics.blit(this.textureFile, 53, 48, 176, 0, progress, 18);
	}

	@Override
	protected void addLedgers() {
		addErrorLedger(this.tile);
		addPowerLedger(this.tile.getEnergyManager());
		addHintLedger(HINTS_KEY);
	}

	static {
		HINTS.putAll(HINTS_KEY, List.of(
				TranslationKeys.HINT_SAMPLE_USAGE,
				TranslationKeys.HINT_SAMPLE_REUSE,
				TranslationKeys.HINT_SAMPLE_SELECTION
		));
	}
}
