package org.sucraft.sushi.datageneration.soil;

import net.minecraft.world.level.block.Blocks;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.gamecontent.block.SuCraftBlockTags;

public final class Gravel {

    private Gravel() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Allow vegetation to be placed on gravel
        factories.blockTags().add(SuCraftBlockTags.SUPPORTS_VEGETATION_MANUAL, Blocks.GRAVEL);
        factories.blockTags().add(SuCraftBlockTags.SUPPORTS_VEGETATION_MANUAL, Blocks.SUSPICIOUS_GRAVEL);
    }

}
