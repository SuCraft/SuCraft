package org.sucraft.sushi.gamecontent.material;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.registry.TypedKey;
import net.kyori.adventure.key.Key;
import spout.api.SpoutRegistryEvents;
import spout.api.SpoutRegistryKey;
import java.util.Locale;

/**
 * Needed for compatibility with existing plugin data.
 */
public final class MaterialNameRewriting {

    private MaterialNameRewriting() {
        throw new UnsupportedOperationException();
    }

    public static void register(BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(
            SpoutRegistryEvents.MATERIAL_NAME_REWRITER.compose(),
            event -> event.registry().register(
                TypedKey.create(SpoutRegistryKey.MATERIAL_NAME_REWRITER, Key.key("sucraft", "add_only_sucraft_prefix")),
                builder -> builder.setToFunction(handle -> {
                    handle.set("SUCRAFT_" + handle.getSourceValue().getLeft().getKey().toUpperCase(Locale.ROOT));
                })
            )
        );
    }

}
