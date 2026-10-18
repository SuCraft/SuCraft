package org.sucraft.sushi.clientview.packetmapping.blockstate;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;
import spout.api.SpoutRegistryEvents;
import spout.api.clientview.packetmapping.blockstate.macro.registry.BlockStateMappingMacroRegistryEntryBuilderNMS;
import java.util.Arrays;

public final class CustomBlockStateMappingMacroAdder {

    private CustomBlockStateMappingMacroAdder() {
        throw new UnsupportedOperationException();
    }

    public static void register(BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(SpoutRegistryEvents.BLOCK_STATE_MAPPING_MACRO.compose(), event -> {
            Arrays.stream(SuCraftBlocks.values()).forEach(block -> {
                event.registry().register(block.mappingMacroRegistryKey(), builder -> {
                    block.applyToBlockStateMappingMacroBuilder((BlockStateMappingMacroRegistryEntryBuilderNMS) builder);
                });
            });
        });

    }

}
