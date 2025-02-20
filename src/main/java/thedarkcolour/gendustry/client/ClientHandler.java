package thedarkcolour.gendustry.client;

import net.minecraft.client.gui.screens.MenuScreens;

import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import forestry.api.client.IClientModuleHandler;

import thedarkcolour.gendustry.client.screen.AdvancedMutatronScreen;
import thedarkcolour.gendustry.client.screen.IndustrialApiaryScreen;
import thedarkcolour.gendustry.client.screen.MutatronScreen;
import thedarkcolour.gendustry.client.screen.ProducerScreen;
import thedarkcolour.gendustry.client.screen.ReplicatorScreen;
import thedarkcolour.gendustry.client.screen.ThreeInputScreen;
import thedarkcolour.gendustry.registry.GMenus;

public class ClientHandler implements IClientModuleHandler {
	@Override
	public void registerEvents(IEventBus modBus) {
		modBus.addListener(ClientHandler::clientSetup);
	}

	private static void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(GMenus.PROCESSOR.menuType(), ProducerScreen::new);
			MenuScreens.register(GMenus.SAMPLER.menuType(), ThreeInputScreen::new);
			MenuScreens.register(GMenus.IMPRINTER.menuType(), ThreeInputScreen::new);
			MenuScreens.register(GMenus.GENETIC_TRANSPOSER.menuType(), ThreeInputScreen::new);
			MenuScreens.register(GMenus.MUTATRON.menuType(), MutatronScreen::new);
			MenuScreens.register(GMenus.ADVANCED_MUTATRON.menuType(), AdvancedMutatronScreen::new);
			MenuScreens.register(GMenus.REPLICATOR.menuType(), ReplicatorScreen::new);
			MenuScreens.register(GMenus.INDUSTRIAL_APIARY.menuType(), IndustrialApiaryScreen::new);
		});
	}
}
