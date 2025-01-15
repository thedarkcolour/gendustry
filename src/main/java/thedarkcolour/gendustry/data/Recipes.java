package thedarkcolour.gendustry.data;

import java.util.function.Consumer;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import forestry.api.ForestryConstants;
import forestry.api.ForestryTags;
import forestry.api.IForestryApi;
import forestry.api.apiculture.genetics.BeeLifeStage;
import forestry.api.arboriculture.genetics.TreeLifeStage;
import forestry.api.genetics.ForestrySpeciesTypes;
import forestry.api.genetics.ILifeStage;
import forestry.api.lepidopterology.genetics.ButterflyLifeStage;
import forestry.core.items.ItemForestry;
import forestry.modules.features.FeatureItem;

import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.block.GendustryMachineType;
import thedarkcolour.gendustry.item.EliteGendustryUpgradeType;
import thedarkcolour.gendustry.item.GendustryResourceType;
import thedarkcolour.gendustry.item.GendustryUpgradeType;
import thedarkcolour.gendustry.recipe.DnaFinishedRecipe;
import thedarkcolour.gendustry.recipe.MutagenFinishedRecipe;
import thedarkcolour.gendustry.recipe.ProteinFinishedRecipe;
import thedarkcolour.gendustry.registry.GBlocks;
import thedarkcolour.gendustry.registry.GFluids;
import thedarkcolour.gendustry.registry.GItems;
import thedarkcolour.gendustry.registry.GRecipeTypes;
import thedarkcolour.modkit.data.MKRecipeProvider;

class Recipes {
	private static final RegistryObject<Item> SILK_WISP = RegistryObject.create(ForestryConstants.forestry("silk_wisp"), ForgeRegistries.ITEMS);
	private static final RegistryObject<Item> BEESWAX = RegistryObject.create(ForestryConstants.forestry("beeswax"), ForgeRegistries.ITEMS);
	private static final RegistryObject<Item> ROYAL_JELLY = RegistryObject.create(ForestryConstants.forestry("royal_jelly"), ForgeRegistries.ITEMS);
	private static final RegistryObject<Item> POLLEN_CLUSTER = RegistryObject.create(ForestryConstants.forestry("pollen_cluster_normal"), ForgeRegistries.ITEMS);
	private static final RegistryObject<Item> STURDY_CASING = RegistryObject.create(ForestryConstants.forestry("sturdy_machine"), ForgeRegistries.ITEMS);

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
		eliteUpgradeCraftingRecipes(recipes);
		machineCraftingRecipes(recipes);

		// Pollen Kit
		recipes.shapelessCrafting(RecipeCategory.TOOLS, GItems.POLLEN_KIT, 1, GItems.RESOURCE.item(GendustryResourceType.LABWARE), Tags.Items.STRING, Items.PAPER);
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
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('T', Items.GHAST_TEAR);
			recipe.pattern("ITI");
			recipe.pattern("RGR");
			recipe.pattern("ITI");
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
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.RESOURCE.item(GendustryResourceType.RECEPTACLE), recipe -> {
			recipe.define('I', ForestryTags.Items.INGOTS_BRONZE);
			recipe.define('P', Tags.Items.GLASS_PANES);
			recipe.define('G', Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
			recipe.define('R', Tags.Items.DUSTS_REDSTONE);
			recipe.pattern("III");
			recipe.pattern("IPI");
			recipe.pattern("RGR");
		});

		// Furnace recipes
		recipes.renameRecipes(oldId -> oldId.withSuffix("_wipe_dna"), finishedRecipe -> {
			recipes.smelting(GItems.GENE_SAMPLE, GItems.RESOURCE.item(GendustryResourceType.BLANK_GENE_SAMPLE), 0.1f);
			recipes.smelting(GItems.GENETIC_TEMPLATE, GItems.RESOURCE.item(GendustryResourceType.BLANK_GENETIC_TEMPLATE), 0.1f);
		});
	}

	private static void upgradeCraftingRecipes(MKRecipeProvider recipes) {
		FeatureItem<ItemForestry> upgradeFrame = GItems.RESOURCE.get(GendustryResourceType.UPGRADE_FRAME);

		recipes.shapedCrafting(RecipeCategory.MISC, GItems.UPGRADE.item(GendustryUpgradeType.AUTOMATION), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('R', Tags.Items.DUSTS_REDSTONE);
			recipe.define('C', Items.COMPARATOR);
			recipe.pattern(" G ");
			recipe.pattern("RFR");
			recipe.pattern(" C ");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.UPGRADE.item(GendustryUpgradeType.HEATER), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('C', GItems.RESOURCE.item(GendustryResourceType.CLIMATE_CONTROL_MODULE));
			recipe.define('P', Items.BLAZE_POWDER);
			recipe.define('I', ForestryTags.Items.INGOTS_BRONZE);
			recipe.pattern("PPP");
			recipe.pattern("IFI");
			recipe.pattern("ICI");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.UPGRADE.item(GendustryUpgradeType.COOLER), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('C', GItems.RESOURCE.item(GendustryResourceType.CLIMATE_CONTROL_MODULE));
			recipe.define('P', Items.ICE);
			recipe.define('I', ForestryTags.Items.INGOTS_BRONZE);
			recipe.pattern("PPP");
			recipe.pattern("IFI");
			recipe.pattern("ICI");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.UPGRADE.item(GendustryUpgradeType.HUMIDIFIER), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('C', GItems.RESOURCE.item(GendustryResourceType.CLIMATE_CONTROL_MODULE));
			recipe.define('P', Tags.Items.MUSHROOMS);
			recipe.define('I', ForestryTags.Items.INGOTS_BRONZE);
			recipe.pattern("PPP");
			recipe.pattern("IFI");
			recipe.pattern("ICI");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.UPGRADE.item(GendustryUpgradeType.DRYER), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('C', GItems.RESOURCE.item(GendustryResourceType.CLIMATE_CONTROL_MODULE));
			recipe.define('P', Items.DEAD_BUSH);
			recipe.define('I', ForestryTags.Items.INGOTS_BRONZE);
			recipe.pattern("PPP");
			recipe.pattern("IFI");
			recipe.pattern("ICI");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.UPGRADE.item(GendustryUpgradeType.POLLINATION), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('P', ItemTags.SMALL_FLOWERS);
			recipe.define('I', ForestryTags.Items.INGOTS_BRONZE);
			recipe.pattern("PPP");
			recipe.pattern("IFI");
			recipe.pattern("IGI");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.UPGRADE.item(GendustryUpgradeType.SCRUBBER), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('S', SILK_WISP.get());
			recipe.pattern("SGS");
			recipe.pattern("GFG");
			recipe.pattern("SGS");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.UPGRADE.item(GendustryUpgradeType.NETHER), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('C', Items.CRIMSON_NYLIUM);
			recipe.define('W', Items.WARPED_NYLIUM);
			recipe.define('S', Items.SOUL_SAND);
			recipe.define('I', ForestryTags.Items.INGOTS_BRONZE);
			recipe.pattern("CWS");
			recipe.pattern("IFI");
			recipe.pattern("IGI");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.UPGRADE.item(GendustryUpgradeType.LIFESPAN), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('E', Items.WITHER_ROSE);
			recipe.define('I', ForestryTags.Items.INGOTS_BRONZE);
			recipe.define('R', Tags.Items.DUSTS_REDSTONE);
			recipe.pattern("ERE");
			recipe.pattern("RFR");
			recipe.pattern("IGI");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.UPGRADE.item(GendustryUpgradeType.LIGHTING), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('D', Items.GLOWSTONE);
			recipe.define('I', ForestryTags.Items.INGOTS_BRONZE);
			recipe.define('R', Tags.Items.DUSTS_REDSTONE);
			recipe.pattern("DRD");
			recipe.pattern("RFR");
			recipe.pattern("IGI");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.UPGRADE.item(GendustryUpgradeType.PRODUCTIVITY), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('D', Tags.Items.GEMS_EMERALD);
			recipe.define('I', ForestryTags.Items.INGOTS_BRONZE);
			recipe.define('R', Tags.Items.DUSTS_REDSTONE);
			recipe.pattern("DRD");
			recipe.pattern("RFR");
			recipe.pattern("IGI");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.UPGRADE.item(GendustryUpgradeType.WEATHERPROOF), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('D', BEESWAX);
			recipe.define('I', ForestryTags.Items.INGOTS_BRONZE);
			recipe.define('R', Tags.Items.DUSTS_REDSTONE);
			recipe.pattern("DRD");
			recipe.pattern("RFR");
			recipe.pattern("IGI");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.UPGRADE.item(GendustryUpgradeType.SIEVE), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('S', SILK_WISP.get());
			recipe.define('I', ForestryTags.Items.INGOTS_BRONZE);
			recipe.pattern("SSS");
			recipe.pattern("SFS");
			recipe.pattern("IGI");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.UPGRADE.item(GendustryUpgradeType.SKY), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('L', Tags.Items.GEMS_LAPIS);
			recipe.define('I', ForestryTags.Items.INGOTS_BRONZE);
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.pattern("LLL");
			recipe.pattern("IFI");
			recipe.pattern("IGI");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.UPGRADE.item(GendustryUpgradeType.STABILIZER), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('I', ForestryTags.Items.INGOTS_BRONZE);
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('R', Tags.Items.DUSTS_REDSTONE);
			recipe.define('P', GItems.RESOURCE.item(GendustryResourceType.GENETICS_PROCESSOR));
			recipe.pattern("RPR");
			recipe.pattern("RFR");
			recipe.pattern("GIG");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.UPGRADE.item(GendustryUpgradeType.TERRITORY), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('R', Tags.Items.DUSTS_REDSTONE);
			recipe.define('B', Items.GRASS_BLOCK);
			recipe.pattern("RBR");
			recipe.pattern("BFB");
			recipe.pattern("RGR");
		});
	}

	private static void eliteUpgradeCraftingRecipes(MKRecipeProvider recipes) {
		FeatureItem<ItemForestry> upgradeFrame = GItems.RESOURCE.get(GendustryResourceType.ELITE_UPGRADE_FRAME);

		recipes.shapedCrafting(RecipeCategory.MISC, GItems.ELITE_UPGRADE.item(EliteGendustryUpgradeType.MUTATION), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('P', GItems.RESOURCE.item(GendustryResourceType.GENETICS_PROCESSOR));
			recipe.define('D', GFluids.LIQUID_DNA.getBucket());
			recipe.define('M', GFluids.MUTAGEN.getBucket());
			recipe.define('R', Tags.Items.DUSTS_REDSTONE);
			recipe.pattern("RMR");
			recipe.pattern("PFP");
			recipe.pattern("RDR");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.ELITE_UPGRADE.item(EliteGendustryUpgradeType.ACTIVITY_SIMULATOR), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('S', GItems.UPGRADE.item(GendustryUpgradeType.SKY));
			recipe.define('W', GItems.UPGRADE.item(GendustryUpgradeType.WEATHERPROOF));
			recipe.define('L', GItems.UPGRADE.item(GendustryUpgradeType.LIGHTING));
			recipe.define('R', Tags.Items.DUSTS_REDSTONE);
			recipe.define('I', ForestryTags.Items.INGOTS_BRONZE);
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);

			recipe.pattern("RWR");
			recipe.pattern("LFS");
			recipe.pattern("IGI");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.ELITE_UPGRADE.item(EliteGendustryUpgradeType.PRODUCTIVITY), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('D', Tags.Items.GEMS_EMERALD);
			recipe.define('C', POLLEN_CLUSTER);
			recipe.define('R', ROYAL_JELLY);
			recipe.pattern("DRD");
			recipe.pattern("RFR");
			recipe.pattern("CGC");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.ELITE_UPGRADE.item(EliteGendustryUpgradeType.TERRITORY), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('R', Tags.Items.DUSTS_REDSTONE);
			recipe.define('B', Items.GRASS_BLOCK);
			recipe.pattern("RBR");
			recipe.pattern("BFB");
			recipe.pattern("RGR");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.ELITE_UPGRADE.item(EliteGendustryUpgradeType.YOUTH), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('T', Items.GHAST_TEAR);
			recipe.define('R', Tags.Items.DUSTS_REDSTONE);
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('I', ForestryTags.Items.INGOTS_BRONZE);
			recipe.pattern("TRT");
			recipe.pattern("RFR");
			recipe.pattern("IGI");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GItems.ELITE_UPGRADE.item(EliteGendustryUpgradeType.FERTILITY), recipe -> {
			recipe.define('F', upgradeFrame);
			recipe.define('P', GItems.RESOURCE.item(GendustryResourceType.GENETICS_PROCESSOR));
			recipe.define('D', GFluids.LIQUID_DNA.getBucket());
			recipe.define('M', GFluids.PROTEIN.getBucket());
			recipe.define('R', Tags.Items.DUSTS_REDSTONE);
			recipe.pattern("RMR");
			recipe.pattern("PFP");
			recipe.pattern("RDR");
		});
	}

	private static void machineCraftingRecipes(MKRecipeProvider recipes) {
		recipes.shapedCrafting(RecipeCategory.MISC, GBlocks.MACHINE.get(GendustryMachineType.INDUSTRIAL_APIARY), recipe -> {
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('C', Tags.Items.GLASS);
			recipe.define('S', STURDY_CASING);
			recipe.define('P', Items.PISTON);
			recipe.define('R', GItems.RESOURCE.item(GendustryResourceType.RECEPTACLE));
			recipe.pattern("CRC");
			recipe.pattern("CSC");
			recipe.pattern("GPG");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GBlocks.MACHINE.get(GendustryMachineType.MUTAGEN_PRODUCER), recipe -> {
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('I', ForestryTags.Items.INGOTS_BRONZE);
			recipe.define('C', Items.CAULDRON);
			recipe.define('S', STURDY_CASING);
			recipe.define('H', Items.HOPPER);
			recipe.define('P', GItems.RESOURCE.item(GendustryResourceType.POWER_MODULE));
			recipe.pattern("IHI");
			recipe.pattern("PSP");
			recipe.pattern("GCG");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GBlocks.MACHINE.get(GendustryMachineType.DNA_EXTRACTOR), recipe -> {
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('S', STURDY_CASING);
			recipe.define('H', Items.HOPPER);
			recipe.define('P', GItems.RESOURCE.item(GendustryResourceType.POWER_MODULE));
			recipe.define('D', GItems.RESOURCE.item(GendustryResourceType.GENETICS_PROCESSOR));
			recipe.pattern("GHG");
			recipe.pattern("DSD");
			recipe.pattern("GPG");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GBlocks.MACHINE.get(GendustryMachineType.PROTEIN_LIQUEFIER), recipe -> {
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('S', STURDY_CASING);
			recipe.define('H', Items.HOPPER);
			recipe.define('P', Items.PISTON);
			recipe.define('M', GItems.RESOURCE.item(GendustryResourceType.POWER_MODULE));
			recipe.pattern("GHG");
			recipe.pattern("PSP");
			recipe.pattern("GMG");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GBlocks.MACHINE.get(GendustryMachineType.SAMPLER), recipe -> {
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('S', STURDY_CASING);
			recipe.define('H', GItems.RESOURCE.item(GendustryResourceType.GENETICS_PROCESSOR));
			recipe.define('P', GItems.RESOURCE.item(GendustryResourceType.RECEPTACLE));
			recipe.define('M', GItems.RESOURCE.item(GendustryResourceType.POWER_MODULE));
			recipe.define('D', Tags.Items.GEMS_DIAMOND);
			recipe.pattern("GHG");
			recipe.pattern("PSD");
			recipe.pattern("GMG");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GBlocks.MACHINE.get(GendustryMachineType.MUTATRON), recipe -> {
			recipe.define('I', ForestryTags.Items.INGOTS_BRONZE);
			recipe.define('S', STURDY_CASING);
			recipe.define('H', GItems.RESOURCE.item(GendustryResourceType.GENETICS_PROCESSOR));
			recipe.define('R', GItems.RESOURCE.item(GendustryResourceType.RECEPTACLE));
			recipe.define('M', GItems.RESOURCE.item(GendustryResourceType.POWER_MODULE));
			recipe.define('C', Items.CAULDRON);
			recipe.pattern("RHI");
			recipe.pattern("MSR");
			recipe.pattern("RCI");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GBlocks.MACHINE.get(GendustryMachineType.ADVANCED_MUTATRON), recipe -> {
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('S', GBlocks.MACHINE.get(GendustryMachineType.MUTATRON));
			recipe.define('H', Tags.Items.GEMS_QUARTZ);
			recipe.define('P', GItems.RESOURCE.item(GendustryResourceType.GENETICS_PROCESSOR));
			recipe.define('M', GItems.RESOURCE.item(GendustryResourceType.POWER_MODULE));
			recipe.pattern("GHG");
			recipe.pattern("PSP");
			recipe.pattern("GMG");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GBlocks.MACHINE.get(GendustryMachineType.IMPRINTER), recipe -> {
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('S', STURDY_CASING);
			recipe.define('H', GItems.RESOURCE.item(GendustryResourceType.GENETICS_PROCESSOR));
			recipe.define('R', GItems.RESOURCE.item(GendustryResourceType.RECEPTACLE));
			recipe.define('M', GItems.RESOURCE.item(GendustryResourceType.POWER_MODULE));
			recipe.pattern("GHG");
			recipe.pattern("RSR");
			recipe.pattern("GMG");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GBlocks.MACHINE.get(GendustryMachineType.GENETIC_TRANSPOSER), recipe -> {
			recipe.define('I', ForestryTags.Items.INGOTS_BRONZE);
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('S', STURDY_CASING);
			recipe.define('H', GItems.RESOURCE.item(GendustryResourceType.GENETICS_PROCESSOR));
			recipe.define('M', GItems.RESOURCE.item(GendustryResourceType.POWER_MODULE));
			recipe.pattern("GIG");
			recipe.pattern("HSH");
			recipe.pattern("GMG");
		});
		recipes.shapedCrafting(RecipeCategory.MISC, GBlocks.MACHINE.get(GendustryMachineType.REPLICATOR), recipe -> {
			recipe.define('G', ForestryTags.Items.GEARS_BRONZE);
			recipe.define('S', STURDY_CASING);
			recipe.define('H', GItems.RESOURCE.item(GendustryResourceType.GENETICS_PROCESSOR));
			recipe.define('M', GItems.RESOURCE.item(GendustryResourceType.POWER_MODULE));
			recipe.pattern("GHG");
			recipe.pattern("MSM");
			recipe.pattern("GHG");
		});
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
