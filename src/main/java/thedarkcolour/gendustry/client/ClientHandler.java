package thedarkcolour.gendustry.client;

import net.minecraft.client.gui.screens.MenuScreens;

import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import forestry.api.client.IClientModuleHandler;

import thedarkcolour.gendustry.client.screen.AdvancedMutatronScreen;
import thedarkcolour.gendustry.client.screen.MutatronScreen;
import thedarkcolour.gendustry.client.screen.ProcessorScreen;
import thedarkcolour.gendustry.client.screen.ThreeInputScreen;
import thedarkcolour.gendustry.registry.GMenus;

public class ClientHandler implements IClientModuleHandler {
	@Override
	public void registerEvents(IEventBus modBus) {
		modBus.addListener(ClientHandler::clientSetup);
	}

	private static void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(GMenus.PROCESSOR.menuType(), ProcessorScreen::new);
			MenuScreens.register(GMenus.SAMPLER.menuType(), ThreeInputScreen::new);
			MenuScreens.register(GMenus.IMPRINTER.menuType(), ThreeInputScreen::new);
			MenuScreens.register(GMenus.MUTATRON.menuType(), MutatronScreen::new);
			MenuScreens.register(GMenus.ADVANCED_MUTATRON.menuType(), AdvancedMutatronScreen::new);
		});
	}
}
