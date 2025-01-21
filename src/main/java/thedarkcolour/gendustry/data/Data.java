package thedarkcolour.gendustry.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;

import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.fml.common.Mod;

import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.modkit.data.DataHelper;

public class Data {
	public static void gatherData(GatherDataEvent event) {
		DataHelper helper = new DataHelper(Gendustry.ID, event);
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();

		helper.createEnglish(true, English::addTranslations);
		helper.createRecipes(Recipes::addRecipes);
		helper.createBlockModels(BlockModels::addBlockModels);
		helper.createItemModels(true, true, false, null);
		helper.createTags(Registries.BLOCK, ModTags::addBlockTags);
		helper.createTags(Registries.ITEM, ModTags::addItemTags);

		generator.addProvider(event.includeServer(), new LootProvider(output));
	}
}
