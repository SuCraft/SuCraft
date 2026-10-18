package org.sucraft.sushi.main;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.sucraft.sushi.clientview.packetmapping.blockstate.CustomBlockStateMappingMacroAdder;
import org.sucraft.sushi.clientview.packetmapping.blockstate.OldReplacedBlockMappings;
import org.sucraft.sushi.clientview.packetmapping.itemstack.OldReplacedItemMappings;
import org.sucraft.sushi.gamecontent.block.CustomBlockAdder;
import org.sucraft.sushi.gamecontent.item.CustomItemAdder;
import org.sucraft.sushi.gamecontent.material.MaterialNameRewriting;
import org.sucraft.sushi.clientview.packetmapping.component.TranslationKeyMapping;
import spout.server.paper.api.SpoutEvents;

import java.util.Objects;

@SuppressWarnings({"unused", "UnstableApiUsage"})
class SushiPluginBootstrap implements PluginBootstrap {

    @Override
    public void bootstrap(BootstrapContext context) {
        // Don't do anything else if the server doesn't support Spout
        if (!CheckSpout.checkSpout()) return;

        CustomBlockAdder.register(context);
        CustomItemAdder.register(context);
        CustomBlockStateMappingMacroAdder.register(context);
        MaterialNameRewriting.register(context);
        TranslationKeyMapping.register(context);
        OldReplacedBlockMappings.register(context);
        OldReplacedItemMappings.register(context);

        // Register included data and resource packs
        registerIncludedDataPack(context);
        registerIncludedResourcePack(context);
    }

    /**
     * Makes sure the included data pack is loaded.
     * It contains drop tables, crafting recipes and more.
     */
    private void registerIncludedDataPack(BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(
            LifecycleEvents.DATAPACK_DISCOVERY,
            event -> {
                try {
                    event.registrar().discoverPack(Objects.requireNonNull(this.getClass().getResource("/data_pack")).toURI(), "provided");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        );
    }

    /**
     * Makes sure the included resource pack is loaded.
     * It contains textures, models and more.
     */
    private void registerIncludedResourcePack(BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(
            SpoutEvents.PLUGIN_RESOURCE_PACK_DISCOVERY,
            event -> event.register(this, context)
        );
    }

}
