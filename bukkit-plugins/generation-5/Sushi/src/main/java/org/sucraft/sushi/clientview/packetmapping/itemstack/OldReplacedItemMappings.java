package org.sucraft.sushi.clientview.packetmapping.itemstack;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.registry.TypedKey;
import net.kyori.adventure.key.Key;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import org.bukkit.Registry;
import org.bukkit.craftbukkit.inventory.CraftItemType;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.inventory.ItemType;
import org.sucraft.sushi.gamecontent.block.OldReplacedBlocks;
import spout.api.SpoutRegistryEvents;
import spout.api.SpoutRegistryKey;
import spout.api.clientview.packetmapping.itemstack.builtin.changeonlyitem.ChangeOnlyItemUtility;

public final class OldReplacedItemMappings {

    private OldReplacedItemMappings() {
        throw new UnsupportedOperationException();
    }

    public static void register(BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(
            SpoutRegistryEvents.ITEM_STACK_MAPPING.compose(),
            event -> {
                var registry = event.registry();
                for (var replaced : OldReplacedBlocks.LIST) {
                    var oldItem = BuiltInRegistries.ITEM.getValue(replaced.oldBlock());
                    registry.register(TypedKey.create(SpoutRegistryKey.ITEM_STACK_MAPPING, Key.key("sucraft", "map_old_replaced_item_" + replaced.oldBlock().getNamespace() + "_" + replaced.oldBlock().getPath())), builder -> {
                        builder.everyAwarenessLevel();
                        builder.setFrom(Registry.ITEM.get(CraftNamespacedKey.fromMinecraft(oldItem.keyInItemRegistry)));
                        builder.setToFunction(handle -> {
                            int count = handle.getImmutable().getAmount();
                            ChangeOnlyItemUtility.get().changeOnlyItem(handle, ItemType.KNOWLEDGE_BOOK);
                            handle.getMutable().setData(DataComponentTypes.ITEM_MODEL, CraftNamespacedKey.fromMinecraft(replaced.newBlock()));
                            handle.getMutable().setAmount(count);
                        });
                    });
                }
            }
        );
    }

}
