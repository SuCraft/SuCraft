package org.sucraft.sushi.datageneration.soil;

import net.minecraft.tags.BlockTags;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.gamecontent.block.SuCraftBlockTags;

public final class Sand {

    private Sand() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Allow vegetation to be placed on sand
        factories.blockTags().add(SuCraftBlockTags.SUPPORTS_VEGETATION_MANUAL, BlockTags.SAND);
    }

}
