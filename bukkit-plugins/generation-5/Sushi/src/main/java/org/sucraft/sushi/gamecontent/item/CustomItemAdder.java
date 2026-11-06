package org.sucraft.sushi.gamecontent.item;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.registry.event.RegistryEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import spout.api.gamecontent.datadriven.item.registry.ItemTypeRegistryEntryBuilderNMS;
import java.util.Arrays;

public final class CustomItemAdder {

    private CustomItemAdder() {
        throw new UnsupportedOperationException();
    }

    public static void register(BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(RegistryEvents.ITEM_TYPE.compose(), event -> {
            Arrays.stream(SuCraftItems.types()).forEach(item -> {
                if (item.definedByCode) {
                    event.registry().register(item.registryKey(), builder ->
                        item.applyToItemBuilder((ItemTypeRegistryEntryBuilderNMS) builder)
                    );
                    if (item.postConstruction != null) {
                        item.postConstruction.accept(item, BuiltInRegistries.ITEM.getValueForCopying(ResourceKey.create(Registries.ITEM, item.identifier)).get());
                    }
                }
            });
        });
    }

}
