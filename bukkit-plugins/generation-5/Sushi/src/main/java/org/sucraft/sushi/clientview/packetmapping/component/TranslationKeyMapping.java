package org.sucraft.sushi.clientview.packetmapping.component;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.registry.TypedKey;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.TranslatableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.Identifier;
import org.sucraft.sushi.gamecontent.block.OldReplacedBlocks;
import spout.api.SpoutRegistryEvents;
import spout.api.SpoutRegistryKey;
import spout.api.clientview.packetmapping.component.ComponentTarget;
import spout.api.clientview.packetmapping.component.registry.ComponentMappingRegistryEntryNMS;
import java.util.ArrayList;
import java.util.Map;

/**
 * Mostly for old versions of blocks and items,
 * so that they don't need to have their own translation keys.
 */
public final class TranslationKeyMapping {

    private TranslationKeyMapping() {
        throw new UnsupportedOperationException();
    }

    private static final Map<String, String> MAPPINGS = new Object2ObjectOpenHashMap<>();

    static {
        var blockAndItems = new ArrayList<Pair<Identifier, Identifier>>();
        for (var replaced : OldReplacedBlocks.LIST) {
            blockAndItems.add(Pair.of(replaced.oldBlock(), replaced.newBlock()));
        }
        for (var blockAndItem : blockAndItems) {
            MAPPINGS.put("block." + blockAndItem.left().getNamespace() + "." + blockAndItem.left().getPath(), "block." + blockAndItem.right().getNamespace() + "." + blockAndItem.right().getPath());
            MAPPINGS.put("item." + blockAndItem.left().getNamespace() + "." + blockAndItem.left().getPath(), "block." + blockAndItem.right().getNamespace() + "." + blockAndItem.right().getPath());
        }
    }

    public static void register(BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(
            SpoutRegistryEvents.COMPONENT_MAPPING.compose(),
            event -> event.registry().register(
                TypedKey.create(SpoutRegistryKey.COMPONENT_MAPPING, Key.key("sucraft", "map_translation_keys")),
                builder -> {
                    var builderNMS = (ComponentMappingRegistryEntryNMS.Builder) builder;
                    builderNMS.everyAwarenessLevel();
                    builderNMS.setFrom(ComponentTarget.TRANSLATABLE);
                    builderNMS.setToFunctionNMS(handle -> {
                        if (handle.getImmutable().getContents() instanceof TranslatableContents contents) {
                            var key = contents.getKey();
                            var mappedKey = MAPPINGS.get(key);
                            if (mappedKey != null) {
                                handle.set(Component.translatableWithFallback(mappedKey, contents.getFallback()).withStyle(handle.getImmutable().getStyle()));
                            }
                        }
                    });
                }
            )
        );
    }

}
