package org.sucraft.sushi.datageneration.wood;

import net.minecraft.world.level.block.Blocks;
import org.sucraft.sushi.datageneration.common.CollectedFactories;

public final class Campfires {

    private Campfires() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Campfires always drop themselves
        factories.blockLootTables().add(provider -> {
            provider.dropSelf(Blocks.CAMPFIRE);
            provider.dropSelf(Blocks.SOUL_CAMPFIRE);
        });
    }

}
