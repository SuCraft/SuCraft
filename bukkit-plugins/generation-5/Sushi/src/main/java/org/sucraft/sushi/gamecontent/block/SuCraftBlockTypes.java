package org.sucraft.sushi.gamecontent.block;

import com.google.common.base.Suppliers;
import net.kyori.adventure.key.Key;
import org.bukkit.Registry;
import org.bukkit.block.BlockType;
import java.util.function.Supplier;

public final class SuCraftBlockTypes {

    private SuCraftBlockTypes() {
        throw new UnsupportedOperationException();
    }

    public static Supplier<BlockType> SNOWY_STONE_BRICK_SLAB = blockType("snowy_stone_brick_slab");
    public static Supplier<BlockType> SNOWY_STONE_BRICK_STAIRS = blockType("snowy_stone_brick_stairs");
    public static Supplier<BlockType> SNOWY_STONE_BRICK_WALL = blockType("snowy_stone_brick_wall");
    public static Supplier<BlockType> SNOWY_STONE_BRICKS = blockType("snowy_stone_bricks");

    private static Supplier<BlockType> blockType(String path) {
        return Suppliers.memoize(() -> Registry.BLOCK.get(Key.key("sucraft", path)));
    }

}
