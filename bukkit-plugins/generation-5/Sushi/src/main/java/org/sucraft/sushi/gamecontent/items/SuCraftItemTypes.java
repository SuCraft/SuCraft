package org.sucraft.sushi.gamecontent.items;

import com.google.common.base.Suppliers;
import java.util.function.Supplier;
import net.kyori.adventure.key.Key;
import org.bukkit.Registry;
import org.bukkit.inventory.ItemType;

public final class SuCraftItemTypes {

    private SuCraftItemTypes() {
        throw new UnsupportedOperationException();
    }

    public static Supplier<ItemType> SNOWY_STONE_BRICK_SLAB = itemType("snowy_stone_brick_slab");
    public static Supplier<ItemType> SNOWY_STONE_BRICK_STAIRS = itemType("snowy_stone_brick_stairs");
    public static Supplier<ItemType> SNOWY_STONE_BRICKS = itemType("snowy_stone_bricks");

    private static Supplier<ItemType> itemType(String path) {
        return Suppliers.memoize(() -> Registry.ITEM.get(Key.key("sucraft", path)));
    }

}
