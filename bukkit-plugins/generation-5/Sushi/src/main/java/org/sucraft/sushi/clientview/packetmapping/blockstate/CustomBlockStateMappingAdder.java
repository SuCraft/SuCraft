package org.sucraft.sushi.clientview.packetmapping.blockstate;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import java.util.Arrays;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;
import spout.api.SpoutRegistryEvents;
import spout.api.clientview.packetmapping.blockstate.macro.registry.BlockStateMappingMacroRegistryEntryBuilderNMS;
import spout.api.clientview.packetmapping.blockstate.registry.BlockStateMappingRegistryEntryNMS;

public final class CustomBlockStateMappingAdder {

    private CustomBlockStateMappingAdder() {
        throw new UnsupportedOperationException();
    }

    public static void register(BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(SpoutRegistryEvents.BLOCK_STATE_MAPPING.compose(), event -> {
            Arrays.stream(SuCraftBlocks.types()).forEach(block -> {
                if (block.blockStateMappingBuilding != null && !block.blockStateMappingBuilding.isEmpty()) {
                    event.registry().register(block.mappingRegistryKey(), builder ->
                        block.applyToBlockStateMappingBuilder((BlockStateMappingRegistryEntryNMS.Builder) builder)
                    );
                }
            });
        });
    }

}
