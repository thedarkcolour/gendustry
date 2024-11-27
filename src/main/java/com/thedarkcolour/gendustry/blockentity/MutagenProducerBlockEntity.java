package com.thedarkcolour.gendustry.blockentity;

import com.thedarkcolour.gendustry.registry.GFluids;
import com.thedarkcolour.gendustry.registry.GBlockEntities;
import forestry.core.fluids.FilteredTank;
import forestry.core.fluids.TankManager;
import forestry.core.tiles.TilePowered;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class MutagenProducerBlockEntity extends TilePowered {
    private final FilteredTank resourceTank;
    private final TankManager tankManager;

    public MutagenProducerBlockEntity(BlockPos pos, BlockState state) {
        super(GBlockEntities.MUTAGEN_PRODUCER.get(), pos, state, 1000, 40000);

        this.resourceTank = new FilteredTank(10000).setFilters(Set.of(GFluids.MUTAGEN));
    }

    @Override
    public boolean hasWork() {
        return false;
    }

    @Override
    protected boolean workCycle() {
        return false;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return null;
    }
}
