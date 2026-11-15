package org.sucraft.sushi.clientview.packetmapping.itemstack;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.registry.TypedKey;
import net.kyori.adventure.key.Key;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;
import org.bukkit.Registry;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;
import org.sucraft.sushi.gamecontent.block.OldReplacedBlocks;
import spout.api.SpoutRegistryEvents;
import spout.api.SpoutRegistryKey;
import spout.api.clientview.packetmapping.itemstack.builtin.changeonlyitem.ChangeOnlyItemUtilityNMS;
import spout.api.clientview.packetmapping.itemstack.registry.ItemStackMappingRegistryEntryNMS;

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
                        var builderNMS = (ItemStackMappingRegistryEntryNMS.Builder) builder;
                        builderNMS.everyAwarenessLevel();
                        builderNMS.setFrom(Registry.ITEM.get(CraftNamespacedKey.fromMinecraft(oldItem.keyInItemRegistry)));
                        builderNMS.setToFunctionNMS(handle -> {
                            int count = handle.getImmutable().count();
                            ChangeOnlyItemUtilityNMS.get().changeOnlyItem(handle, Items.KNOWLEDGE_BOOK);
                            var mutable = handle.getMutable();
                            mutable.set(DataComponents.ITEM_MODEL, replaced.newBlock());
                            mutable.setCount(count);
                        });
                        builderNMS.setOverrideItemModel(false);
                    });
                }
            }
        );
    }

}
