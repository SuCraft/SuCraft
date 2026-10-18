package org.sucraft.sushi.gamecontent.item;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.registry.event.RegistryEvents;
import spout.api.gamecontent.datadriven.item.registry.ItemTypeRegistryEntryBuilderNMS;
import java.util.Arrays;

public final class CustomItemAdder {

    private CustomItemAdder() {
        throw new UnsupportedOperationException();
    }

    public static void register(BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(RegistryEvents.ITEM_TYPE.compose(), event -> {
            Arrays.stream(SuCraftItems.values()).forEach(block ->
                event.registry().register(block.registryKey(), builder ->
                    block.applyToItemBuilder((ItemTypeRegistryEntryBuilderNMS) builder)
                )
            );
        });
    }

}
