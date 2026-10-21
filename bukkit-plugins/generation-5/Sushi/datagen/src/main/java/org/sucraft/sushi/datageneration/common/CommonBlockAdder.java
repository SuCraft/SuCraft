package org.sucraft.sushi.datageneration.common;

import net.minecraft.world.level.block.Block;
import org.sucraft.sushi.gamecontent.block.BlockSet;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;
import java.util.Arrays;
import java.util.HashSet;

public final class CommonBlockAdder {

    private CommonBlockAdder() {
        throw new UnsupportedOperationException();
    }

    public static void initialize() {
        // Make sure all blocks are registered
        SuCraftBlocks.valuesForDataGeneration();
    }

    public static void add(CollectedFactories factories) {
        // First process all block sets
        var processedInBlockSet = new HashSet<Block>();
        for (BlockSet set : SuCraftBlocks.blockSets()) {
            System.out.println("Adding block set: " + Arrays.stream(set.valuesForDataGeneration()).map(it -> it.properties().blockId().identifier()).toList());
            if (set.hasFullBlock()) {
                CommonMacros.addFamily(
                    factories,
                    ((BlockSet.WithFullBlock) set).fullBlockForDataGeneration(),
                    set.hasSlab() ? ((BlockSet.WithSlab) set).slabForDataGeneration() : null,
                    set.hasStairs() ? ((BlockSet.WithStairs) set).stairsForDataGeneration() : null,
                    set.hasWall() ? ((BlockSet.WithWall) set).wallForDataGeneration() : null
                );
            }
        }
        // Then process individual blocks
        for (Block block : SuCraftBlocks.valuesForDataGeneration()) {
            if (!processedInBlockSet.contains(block)) {
                System.out.println("Adding loose block: " + block.properties().blockId().identifier());
            }
        }
    }

}
