package org.sucraft.sushi.datageneration.soil;

import java.util.stream.Stream;
import net.minecraft.tags.BlockTags;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;

public final class Dirt {

    private Dirt() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Add tags
        Stream.of(
            SuCraftBlocks.DIRT.slabNMS(),
            SuCraftBlocks.DIRT.stairsNMS()
        ).forEach(shape -> {
            factories.blockTags().add(BlockTags.CANNOT_REPLACE_BELOW_TREE_TRUNK, shape);
        });
    }

}
