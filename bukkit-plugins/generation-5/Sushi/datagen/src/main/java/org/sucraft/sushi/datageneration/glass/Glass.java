package org.sucraft.sushi.datageneration.glass;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Blocks;
import org.sucraft.sushi.datageneration.common.CollectedFactories;

public final class Glass {

    private Glass() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        factories.blockLootTables().add(provider -> {
            provider.dropSelf(Blocks.GLASS);
            provider.dropSelf(Blocks.GLASS_PANE);
            for (var color : DyeColor.values()) {
                provider.dropSelf(Blocks.STAINED_GLASS.pick(color));
                provider.dropSelf(Blocks.STAINED_GLASS_PANE.pick(color));
            }
        });
    }

}
