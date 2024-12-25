package com.thedarkcolour.gendustry.data;

import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.fml.common.Mod;

import com.thedarkcolour.gendustry.Gendustry;
import thedarkcolour.modkit.data.DataHelper;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Data {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataHelper helper = new DataHelper(Gendustry.ID, event);

		helper.createEnglish(true, English::addTranslations);
		helper.createRecipes(Recipes::addRecipes);
	}
}
