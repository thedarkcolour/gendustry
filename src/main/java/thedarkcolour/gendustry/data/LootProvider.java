package thedarkcolour.gendustry.data;

import java.util.List;
import java.util.Set;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

class LootProvider extends LootTableProvider {
	public LootProvider(PackOutput pOutput) {
		super(pOutput, Set.of(), List.of(new SubProviderEntry(BlockLoot::new, LootContextParamSets.BLOCK)));
	}
}
