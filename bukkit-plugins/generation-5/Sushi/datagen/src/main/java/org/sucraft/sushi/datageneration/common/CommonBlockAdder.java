package org.sucraft.sushi.datageneration.common;

import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;

public final class CommonBlockAdder {

    private CommonBlockAdder() {
        throw new UnsupportedOperationException();
    }

    public static void initialize() {
        // Make sure all blocks are registered
        SuCraftBlocks.valuesForDataGeneration();
    }

    public static void add(CollectedFactories factories) {
        for (var type : SuCraftBlocks.types()) {
            type.applyDataGeneration(factories);
        }
    }

}
