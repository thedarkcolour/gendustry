package thedarkcolour.gendustry.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;

import forestry.modules.features.FeatureBlock;

import thedarkcolour.gendustry.block.GendustryMachineType;
import thedarkcolour.gendustry.registry.GBlocks;
import thedarkcolour.gendustry.registry.GFluids;
import thedarkcolour.modkit.data.MKBlockModelProvider;
import static forestry.core.data.models.ForestryBlockStateProvider.path;

class BlockModels {
	static void addBlockModels(MKBlockModelProvider models) {
		for (GendustryMachineType type : GendustryMachineType.values()) {
			machine(models, GBlocks.MACHINE.get(type));
		}

		for (GFluids fluid : GFluids.values()) {
			Block block = fluid.getFeature().fluidBlock().block();
			ModelFile blockModel = models.models().getBuilder(path(block)).texture("particle", fluid.getFeature().properties().resources[0]);
			models.getVariantBuilder(block).partialState().modelForState().modelFile(blockModel).addModel();
		}
	}

	private static void machine(MKBlockModelProvider models, FeatureBlock<?, ?> block) {
		ResourceLocation texture = models.blockTexture(block.block());
		BlockModelBuilder model = models.models().cubeBottomTop(block.getName(), texture.withSuffix("_side"), texture.withSuffix("_bottom"), texture.withSuffix("_top"));

		models.getVariantBuilder(block.block()).partialState().setModels(new ConfiguredModel(model, 0, 0, false));
	}
}
