package org.sucraft.sushi.clientview.packetmapping.itemstack;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import java.util.Arrays;
import org.sucraft.sushi.gamecontent.item.SuCraftItems;
import spout.api.SpoutRegistryEvents;
import spout.api.clientview.packetmapping.itemstack.registry.ItemStackMappingRegistryEntryNMS;

public final class CustomItemStackMappingAdder {

    private CustomItemStackMappingAdder() {
        throw new UnsupportedOperationException();
    }

    public static void register(BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(SpoutRegistryEvents.ITEM_STACK_MAPPING.compose(), event -> {
            Arrays.stream(SuCraftItems.types()).forEach(item -> {
                if (item.itemStackMappingBuilding != null && !item.itemStackMappingBuilding.isEmpty()) {
                    event.registry().register(item.mappingRegistryKey(), builder ->
                        item.applyToItemStackMappingBuilder((ItemStackMappingRegistryEntryNMS.Builder) builder)
                    );
                }
            });
        });
    }

}
