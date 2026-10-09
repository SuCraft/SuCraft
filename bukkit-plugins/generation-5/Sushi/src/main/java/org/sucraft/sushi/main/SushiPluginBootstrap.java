package org.sucraft.sushi.main;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import io.papermc.paper.registry.TypedKey;
import net.kyori.adventure.key.Key;
import spout.api.SpoutRegistryEvents;
import spout.api.SpoutRegistryKey;
import spout.server.paper.api.SpoutEvents;

import java.util.Locale;
import java.util.Objects;

@SuppressWarnings({"unused", "UnstableApiUsage"})
class SushiPluginBootstrap implements PluginBootstrap {

    @Override
    public void bootstrap(BootstrapContext context) {
        // Don't do anything else if the server doesn't support Spout
        if (!CheckSpout.checkSpout()) return;

        // Needed for compatibility with existing plugin data
        context.getLifecycleManager().registerEventHandler(
            SpoutRegistryEvents.MATERIAL_NAME_REWRITER.compose(),
            event -> event.registry().register(
                TypedKey.create(SpoutRegistryKey.MATERIAL_NAME_REWRITER, Key.key("sucraft", "add_only_sucraft_prefix")),
                builder -> builder.setToFunction(handle -> {
                    handle.set("SUCRAFT_" + handle.getSourceValue().getLeft().getKey().toUpperCase(Locale.ROOT));
                })
            )
        );

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
