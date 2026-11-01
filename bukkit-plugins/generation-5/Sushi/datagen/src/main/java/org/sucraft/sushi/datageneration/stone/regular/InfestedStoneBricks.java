package org.sucraft.sushi.datageneration.stone.regular;

import net.minecraft.world.level.block.Blocks;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.gamecontent.common.SuCraftBlockItemTags;
import java.util.stream.Stream;

public final class InfestedStoneBricks {

    private InfestedStoneBricks() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        Stream.of(
            Blocks.INFESTED_CHISELED_STONE_BRICKS,
            Blocks.INFESTED_CRACKED_STONE_BRICKS,
            Blocks.INFESTED_MOSSY_STONE_BRICKS,
             Blocks.INFESTED_STONE_BRICKS
        ).forEach(block -> {
            factories.blockTags().add(SuCraftBlockItemTags.INFESTED_STONE_BRICKS.block(), block);
            factories.itemTags().add(SuCraftBlockItemTags.INFESTED_STONE_BRICKS.item(), block.asItem());
        });
    }

}
