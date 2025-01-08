package thedarkcolour.gendustry.client.screen;

import java.util.List;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import forestry.core.config.Constants;
import forestry.core.gui.GuiForestryTitled;
import forestry.core.tiles.TilePowered;

import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.blockentity.SamplerBlockEntity;
import thedarkcolour.gendustry.data.TranslationKeys;
import thedarkcolour.gendustry.menu.SamplerImprinterMenu;

// Reused by both sampler and imprinter
public class SamplerImprinterScreen extends GuiForestryTitled<SamplerImprinterMenu<? extends TilePowered>> {
	public static final String SAMPLER_HINTS_KEY = "gendustry.sampler";
	public static final String IMPRINTER_HINTS_KEY = "gendustry.imprinter";

	private final TilePowered tile;
	private final String hintsKey;

	public SamplerImprinterScreen(SamplerImprinterMenu<?> menu, Inventory inv, Component title) {
		super(Gendustry.loc(Constants.TEXTURE_PATH_GUI + "/sampler.png"), menu, inv, title);

		this.tile = menu.getTile();
		this.hintsKey = this.tile instanceof SamplerBlockEntity ? SAMPLER_HINTS_KEY : IMPRINTER_HINTS_KEY;
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
		addHintLedger(this.hintsKey);
	}

	static {
		HINTS.putAll(SAMPLER_HINTS_KEY, List.of(
				TranslationKeys.HINT_SAMPLE_USAGE,
				TranslationKeys.HINT_SAMPLE_REUSE,
				TranslationKeys.HINT_SAMPLE_SELECTION
		));
		HINTS.putAll(IMPRINTER_HINTS_KEY, List.of(
				TranslationKeys.HINT_TEMPLATE_USAGE
		));
	}
}
