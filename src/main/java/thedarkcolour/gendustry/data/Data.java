package thedarkcolour.gendustry.data;

import net.minecraft.core.registries.Registries;

import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.fml.common.Mod;

import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.modkit.data.DataHelper;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Data {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataHelper helper = new DataHelper(Gendustry.ID, event);

		helper.createEnglish(true, English::addTranslations);
		helper.createRecipes(Recipes::addRecipes);
		helper.createBlockModels(BlockModels::addBlockModels);
		helper.createItemModels(true, true, false, null);
		helper.createTags(Registries.ITEM, Tags::addTags);
	}
}
