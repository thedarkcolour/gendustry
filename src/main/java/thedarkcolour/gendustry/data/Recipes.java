package thedarkcolour.gendustry.data;


import java.util.function.Consumer;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import net.minecraftforge.common.Tags;

import forestry.api.ForestryTags;
import forestry.api.IForestryApi;
import forestry.api.apiculture.genetics.BeeLifeStage;
import forestry.api.arboriculture.genetics.TreeLifeStage;
import forestry.api.genetics.ForestrySpeciesTypes;
import forestry.api.genetics.ILifeStage;
import forestry.api.lepidopterology.genetics.ButterflyLifeStage;
import forestry.core.features.CoreItems;

import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.item.GendustryResourceType;
import thedarkcolour.gendustry.item.GendustryUpgradeType;
import thedarkcolour.gendustry.recipe.DnaFinishedRecipe;
import thedarkcolour.gendustry.recipe.MutagenFinishedRecipe;
import thedarkcolour.gendustry.recipe.ProteinFinishedRecipe;
import thedarkcolour.gendustry.registry.GItems;
import thedarkcolour.gendustry.registry.GRecipeTypes;
import thedarkcolour.modkit.data.MKRecipeProvider;

class Recipes {
	static void addRecipes(Consumer<FinishedRecipe> writer, MKRecipeProvider recipes) {
		// Genetic Template
		recipes.special("combine_genetic_template", GRecipeTypes.GENETIC_TEMPLATE_SERIALIZER);

		// Mutagen recipes
		mutagen(writer, Items.REDSTONE, 100);
		mutagen(writer, Items.GLOWSTONE_DUST, 200);
		mutagen(writer, Items.GLOWSTONE, 800);
		mutagen(writer, Items.REDSTONE_BLOCK, 900);
		// todo Yellorium and Uranium

		// Protein recipes
		protein(writer, Items.PORKCHOP, 500);
		protein(writer, Items.BEEF, 500);
		protein(writer, Items.RABBIT, 250);
		protein(writer, Items.COD, 250);
		protein(writer, Items.SALMON, 250);
		protein(writer, Items.PUFFERFISH, 250);
		protein(writer, Items.TROPICAL_FISH, 250);

		// DNA recipes
		dna(writer, ForestrySpeciesTypes.BEE, BeeLifeStage.DRONE, 100);
		dna(writer, ForestrySpeciesTypes.BEE, BeeLifeStage.PRINCESS, 500);
		dna(writer, ForestrySpeciesTypes.BEE, BeeLifeStage.QUEEN, 600);
		dna(writer, ForestrySpeciesTypes.BEE, BeeLifeStage.LARVAE, 300);
		dna(writer, ForestrySpeciesTypes.TREE, TreeLifeStage.SAPLING, 100);
		dna(writer, ForestrySpeciesTypes.TREE, TreeLifeStage.POLLEN, 400);
		dna(writer, ForestrySpeciesTypes.BUTTERFLY, ButterflyLifeStage.BUTTERFLY, 200);
		dna(writer, ForestrySpeciesTypes.BUTTERFLY, ButterflyLifeStage.SERUM, 800);
		dna(writer, ForestrySpeciesTypes.BUTTERFLY, ButterflyLifeStage.CATERPILLAR, 1000);
		dna(writer, ForestrySpeciesTypes.BUTTERFLY, ButterflyLifeStage.COCOON, 1000);

		// Crafting recipes
		resourceCraftingRecipes(recipes);
		upgradeCraftingRecipes(recipes);
	}

	private static void resourceCraftingRecipes(MKRecipeProvider recipes) {
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.RESOURCE.item(GendustryResourceType.LABWARE), 2, recipe -> {
			recipe.define('G', Tags.Items.GLASS_PANES);
			recipe.define('D', Tags.Items.GEMS_DIAMOND);
			recipe.pattern("G G");
			recipe.pattern("G G");
			recipe.pattern(" D ");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.RESOURCE.item(GendustryResourceType.UPGRADE_FRAME), 2, recipe -> {
			recipe.define('I', ForestryTags.Items.INGOTS_TIN);
			recipe.define('R', Tags.Items.DUSTS_REDSTONE);
			recipe.define('G', Tags.Items.NUGGETS_GOLD);
			recipe.pattern("IGI");
			recipe.pattern("R R");
			recipe.pattern("IGI");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.RESOURCE.item(GendustryResourceType.ELITE_UPGRADE_FRAME), 2, recipe -> {
			recipe.define('I', Tags.Items.STORAGE_BLOCKS_GOLD);
			recipe.define('R', GItems.RESOURCE.item(GendustryResourceType.POWER_MODULE));
			recipe.define('G', Items.GHAST_TEAR);
			recipe.pattern("IGI");
			recipe.pattern("R R");
			recipe.pattern("IGI");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.RESOURCE.item(GendustryResourceType.CLIMATE_CONTROL_MODULE), recipe -> {
			recipe.define('R', Tags.Items.DUSTS_REDSTONE);
			recipe.define('B', ForestryTags.Items.INGOTS_BRONZE);
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.pattern("BRB");
			recipe.pattern("BGB");
			recipe.pattern("BRB");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.RESOURCE.item(GendustryResourceType.POWER_MODULE), recipe -> {
			recipe.define('R', Tags.Items.STORAGE_BLOCKS_REDSTONE);
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('I', Tags.Items.INGOTS_GOLD);
			recipe.define('P', Items.PISTON);
			recipe.pattern("GIG");
			recipe.pattern("PRP");
			recipe.pattern("GIG");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.RESOURCE.item(GendustryResourceType.GENETICS_PROCESSOR), recipe -> {
			recipe.define('D', Tags.Items.GEMS_DIAMOND);
			recipe.define('Q', Tags.Items.GEMS_QUARTZ);
			recipe.define('P', Tags.Items.ENDER_PEARLS);
			recipe.pattern("DQD");
			recipe.pattern("QPQ");
			recipe.pattern("DQD");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.RESOURCE.item(GendustryResourceType.ENVIRONMENTAL_PROCESSOR), recipe -> {
			recipe.define('D', Tags.Items.GEMS_DIAMOND);
			recipe.define('Q', Tags.Items.GEMS_LAPIS);
			recipe.define('P', Tags.Items.INGOTS_GOLD);
			recipe.pattern("DQD");
			recipe.pattern("QPQ");
			recipe.pattern("DQD");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.RESOURCE.item(GendustryResourceType.BLANK_GENE_SAMPLE), recipe -> {
			recipe.define('I', ForestryTags.Items.INGOTS_TIN);
			recipe.define('R', Tags.Items.DUSTS_REDSTONE);
			recipe.pattern(" I ");
			recipe.pattern("IRI");
			recipe.pattern(" I ");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.RESOURCE.item(GendustryResourceType.BLANK_GENETIC_TEMPLATE), recipe -> {
			recipe.define('I', GItems.RESOURCE.item(GendustryResourceType.BLANK_GENE_SAMPLE));
			recipe.define('R', Tags.Items.DUSTS_REDSTONE);
			recipe.define('D', Tags.Items.GEMS_DIAMOND);
			recipe.pattern("RIR");
			recipe.pattern("IDI");
			recipe.pattern("RIR");
		});

		// Furnace recipes
		recipes.renameRecipes(oldId -> oldId.withSuffix("_wipe_dna"), finishedRecipe -> {
			recipes.smelting(GItems.GENE_SAMPLE, GItems.RESOURCE.item(GendustryResourceType.BLANK_GENE_SAMPLE), 0.1f);
			recipes.smelting(GItems.GENETIC_TEMPLATE, GItems.RESOURCE.item(GendustryResourceType.BLANK_GENETIC_TEMPLATE), 0.1f);
		});
	}

	private static void upgradeCraftingRecipes(MKRecipeProvider recipes) {
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.UPGRADE.item(GendustryUpgradeType.AUTOMATION), recipe -> {
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('F', GItems.RESOURCE.get(GendustryResourceType.UPGRADE_FRAME));
			recipe.define('R', Tags.Items.DUSTS_REDSTONE);
			recipe.define('C', Items.COMPARATOR);
			recipe.pattern(" G ");
			recipe.pattern("RFR");
			recipe.pattern(" C ");
		});
	}

	private static void machineCraftingRecipes(MKRecipeProvider recipes) {

	}

	private static void mutagen(Consumer<FinishedRecipe> writer, Item input, int mutagen) {
		writer.accept(new MutagenFinishedRecipe(Gendustry.loc("mutagen/" + MKRecipeProvider.path(input)), Ingredient.of(input), mutagen));
	}

	private static void protein(Consumer<FinishedRecipe> writer, Item input, int protein) {
		writer.accept(new ProteinFinishedRecipe(Gendustry.loc("protein/" + MKRecipeProvider.path(input)), Ingredient.of(input), protein));
	}

	private static void dna(Consumer<FinishedRecipe> writer, ResourceLocation speciesType, ILifeStage input, int dna) {
		writer.accept(new DnaFinishedRecipe(Gendustry.loc("protein/" + input.getSerializedName()), IForestryApi.INSTANCE.getGeneticManager().getSpeciesType(speciesType), input, dna));
	}
}
