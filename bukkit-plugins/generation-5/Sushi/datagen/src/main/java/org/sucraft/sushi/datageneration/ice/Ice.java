package org.sucraft.sushi.datageneration.ice;

import net.minecraft.world.level.block.Blocks;
import org.sucraft.sushi.datageneration.common.CollectedFactories;

public final class Ice {

    private Ice() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Opaque ice always drops itself without silk touch
        factories.blockLootTables().add(provider -> {
            provider.dropSelf(Blocks.PACKED_ICE);
            provider.dropSelf(Blocks.BLUE_ICE);
        });
    }

}
