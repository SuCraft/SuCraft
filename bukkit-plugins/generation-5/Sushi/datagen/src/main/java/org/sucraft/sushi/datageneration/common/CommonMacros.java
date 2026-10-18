package org.sucraft.sushi.datageneration.common;

import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

public final class CommonMacros {

    private CommonMacros() {
        throw new UnsupportedOperationException();
    }

    public static void addFamily(
        CollectedFactories factories,
        Block fullBlock,
        @Nullable Block slab,
        @Nullable Block stairs,
        @Nullable Block wall
    ) {
        factories.models().addBlock(
            generators -> {
                var provider = generators.family(fullBlock);
                if (slab != null) {
                    provider.slab(slab);
                }
                if (stairs != null) {
                    provider.stairs(stairs);
                }
                if (wall != null) {
                    provider.wall(wall);
                }
            }
        );
        factories.blockLootTables().add(provider -> provider.dropSelf(fullBlock));
        if (slab != null) {
            factories.blockLootTables().add(provider -> provider.add(slab, provider::createSlabItemTable));
        }
        if (stairs != null) {
            factories.blockLootTables().add(provider -> provider.dropSelf(stairs));
        }
        if (wall != null) {
            factories.blockLootTables().add(provider -> provider.dropSelf(wall));
        }
    }

}
