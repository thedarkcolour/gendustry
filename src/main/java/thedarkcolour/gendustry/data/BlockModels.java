package thedarkcolour.gendustry.data;

import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import forestry.modules.features.FeatureBlock;

import thedarkcolour.gendustry.block.GendustryMachineType;
import thedarkcolour.gendustry.registry.GBlocks;
import thedarkcolour.modkit.data.MKBlockModelProvider;

class BlockModels {
	static void addBlockModels(MKBlockModelProvider models) {
		for (GendustryMachineType type : GendustryMachineType.values()) {
			machine(models, GBlocks.MACHINE.get(type));
		}
	}

	private static void machine(MKBlockModelProvider models, FeatureBlock<?, ?> block) {
		ResourceLocation texture = models.blockTexture(block.block());
		BlockModelBuilder model = models.models().cubeBottomTop(block.getName(), texture.withSuffix("_side"), texture.withSuffix("_bottom"), texture.withSuffix("_top"));

		models.getVariantBuilder(block.block()).partialState().setModels(new ConfiguredModel(model, 0, 0, false));
	}
}
