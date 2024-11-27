package com.thedarkcolour.gendustry.registry;

import com.thedarkcolour.gendustry.Gendustry;
import forestry.modules.features.FeatureFluid;
import forestry.modules.features.IFeatureRegistry;
import forestry.modules.features.ModFeatureRegistry;

public class GFluids {
    private static final IFeatureRegistry REGISTRY = ModFeatureRegistry.get(Gendustry.MODULE_ID);

    public static final FeatureFluid MUTAGEN = REGISTRY.fluid("mutagen")
            .create();
}
