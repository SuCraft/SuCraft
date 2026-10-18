package org.sucraft.sushi.datageneration.stone.tuff;

import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.CommonMacros;
import org.sucraft.sushi.datageneration.common.ModBlocks;

public final class PolishedTuff {

    private PolishedTuff() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        CommonMacros.addFamily(
            factories,
            ModBlocks.POLISHED_TUFF,
            ModBlocks.POLISHED_TUFF_SLAB,
            ModBlocks.POLISHED_TUFF_STAIRS,
            ModBlocks.POLISHED_TUFF_WALL
        );
    }

}
