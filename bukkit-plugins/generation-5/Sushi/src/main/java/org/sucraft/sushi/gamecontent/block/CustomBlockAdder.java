package org.sucraft.sushi.gamecontent.block;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.registry.event.RegistryEvents;
import spout.api.gamecontent.datadriven.block.registry.BlockTypeRegistryEntryBuilderNMS;
import java.util.Arrays;

public final class CustomBlockAdder {

    private CustomBlockAdder() {
        throw new UnsupportedOperationException();
    }

    public static void register(BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(RegistryEvents.BLOCK_TYPE.compose(), event -> {
            Arrays.stream(SuCraftBlocks.values()).forEach(block ->
                event.registry().register(block.registryKey(), builder ->
                    block.applyToBlockBuilder((BlockTypeRegistryEntryBuilderNMS) builder)
                )
            );
        });
    }

}
