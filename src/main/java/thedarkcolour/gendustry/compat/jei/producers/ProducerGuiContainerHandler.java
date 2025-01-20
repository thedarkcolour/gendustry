package thedarkcolour.gendustry.compat.jei.producers;

import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.world.level.block.entity.BlockEntity;
import thedarkcolour.gendustry.blockentity.DnaExtractorBlockEntity;
import thedarkcolour.gendustry.blockentity.MutagenProducerBlockEntity;
import thedarkcolour.gendustry.blockentity.ProteinLiquefierBlockEntity;
import thedarkcolour.gendustry.client.screen.ProducerScreen;
import thedarkcolour.gendustry.compat.jei.GendustryRecipeType;

import java.util.Collection;
import java.util.Collections;

public class ProducerGuiContainerHandler implements IGuiContainerHandler<ProducerScreen> {

    @Override
    public Collection<IGuiClickableArea> getGuiClickableAreas(ProducerScreen containerScreen, double guiMouseX, double guiMouseY) {
        BlockEntity blockEntity = containerScreen.getMenu().getTile();
        if (blockEntity instanceof MutagenProducerBlockEntity) {
            return Collections.singleton(IGuiClickableArea.createBasic(48, 40, 55, 18, GendustryRecipeType.MUTAGEN_PRODUCER));
        } else if (blockEntity instanceof DnaExtractorBlockEntity) {
            return Collections.singleton(IGuiClickableArea.createBasic(48, 40, 55, 18, GendustryRecipeType.DNA_EXTRACTOR));
        } else if (blockEntity instanceof ProteinLiquefierBlockEntity) {
            return Collections.singleton(IGuiClickableArea.createBasic(48, 40, 55, 18, GendustryRecipeType.PROTEIN_LIQUEFIER));
        }
        return Collections.emptyList();
    }
}
