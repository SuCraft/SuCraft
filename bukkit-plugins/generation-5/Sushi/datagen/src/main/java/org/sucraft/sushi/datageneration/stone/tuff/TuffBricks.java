package org.sucraft.sushi.datageneration.stone.tuff;

import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.CommonMacros;
import org.sucraft.sushi.datageneration.common.ModBlocks;

public final class TuffBricks {

    private TuffBricks() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        CommonMacros.addFamily(
            factories,
            ModBlocks.TUFF_BRICKS,
            ModBlocks.TUFF_BRICK_SLAB,
            ModBlocks.TUFF_BRICK_STAIRS,
            ModBlocks.TUFF_BRICK_WALL
        );
    }

}
