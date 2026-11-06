package org.sucraft.sushi.gamecontent.block;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.registry.event.RegistryEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import spout.api.gamecontent.datadriven.block.registry.BlockTypeRegistryEntryBuilderNMS;
import java.util.Arrays;

public final class CustomBlockAdder {

    private CustomBlockAdder() {
        throw new UnsupportedOperationException();
    }

    public static void register(BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(RegistryEvents.BLOCK_TYPE.compose(), event -> {
            Arrays.stream(SuCraftBlocks.types()).forEach(block -> {
                if (block.definedByCode) {
                    event.registry().register(block.registryKey(), builder ->
                        block.applyToBlockBuilder((BlockTypeRegistryEntryBuilderNMS) builder)
                    );
                    if (block.postConstruction != null) {
                        block.postConstruction.accept(block, BuiltInRegistries.BLOCK.getValueForCopying(ResourceKey.create(Registries.BLOCK, block.identifier)).get());
                    }
                }
            });
        });
    }

}
