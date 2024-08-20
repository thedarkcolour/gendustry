package com.thedarkcolour.gendustry.registry;

import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import com.thedarkcolour.gendustry.Gendustry;

public class GItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(Registry.ITEM_REGISTRY, Gendustry.ID);

	public static final RegistryObject<Item> INDUSTRIAL_APIARY = REGISTRY.register("industrial_apiary", () -> new BlockItem(GBlocks.INDUSTRIAL_APIARY.get(), new Item.Properties()));
}
