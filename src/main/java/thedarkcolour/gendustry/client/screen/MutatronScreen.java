package thedarkcolour.gendustry.client.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import forestry.core.config.Constants;

import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.menu.MutatronMenu;

public class MutatronScreen extends AbstractMutatronScreen<MutatronMenu> {
	public MutatronScreen(MutatronMenu menu, Inventory playerInv, Component title) {
		super(Gendustry.loc(Constants.TEXTURE_PATH_GUI + "/mutatron.png"), menu, playerInv, title);
	}
}
