package org.sucraft.sushi.datageneration.soil;

import net.minecraft.tags.BlockTags;
import org.sucraft.sushi.datageneration.common.CollectedFactories;

public final class Sand {

    private Sand() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Allow vegetation to be placed on sand
        factories.blockTags().add(BlockTags.SUPPORTS_VEGETATION, BlockTags.SAND);
    }

}
