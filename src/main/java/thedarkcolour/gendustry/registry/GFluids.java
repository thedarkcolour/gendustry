package thedarkcolour.gendustry.registry;

import java.util.Locale;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;

import net.minecraftforge.fluids.FluidStack;

import forestry.modules.features.FeatureFluid;
import forestry.modules.features.FeatureItem;
import forestry.modules.features.FeatureProvider;
import forestry.modules.features.IFeatureRegistry;
import forestry.modules.features.ModFeatureRegistry;

import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.GendustryModule;

@FeatureProvider
public enum GFluids {
	MUTAGEN,
	LIQUID_DNA,
	PROTEIN;

	private final ResourceLocation id;
	private final FeatureFluid feature;
	private final FeatureItem<BucketItem> bucket;

	GFluids() {
		IFeatureRegistry registry = ModFeatureRegistry.get(GendustryModule.MODULE_ID);
		this.feature = registry
				.fluid(name().toLowerCase(Locale.ENGLISH))
				.bucket(this::getBucket)
				.create();
		this.bucket = registry
				.item(() -> new BucketItem(this::fluid, new Item.Properties()
								.craftRemainder(Items.BUCKET)
								.stacksTo(1)),
						"bucket_" + name().toLowerCase(Locale.ENGLISH)
				);
		this.id = Gendustry.loc(feature.getName());
	}

	public final ResourceLocation getId() {
		return this.id;
	}

	public FeatureFluid getFeature() {
		return this.feature;
	}

	public BucketItem getBucket() {
		return this.bucket.item();
	}

	public Fluid fluid() {
		return this.feature.fluid();
	}

	public Fluid getFlowing() {
		return this.feature.flowing();
	}

	public FluidStack fluidStack(int mb) {
		return new FluidStack(fluid(), mb);
	}
}
