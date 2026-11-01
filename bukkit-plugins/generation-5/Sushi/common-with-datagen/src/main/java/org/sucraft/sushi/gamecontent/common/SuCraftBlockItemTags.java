package org.sucraft.sushi.gamecontent.common;

import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockItemTagId;

public final class SuCraftBlockItemTags {

    private SuCraftBlockItemTags() {
        throw new UnsupportedOperationException();
    }

    public static final BlockItemTagId BOOKSHELF_SLABS = createSuCraft("bookshelf_slabs");
    public static final BlockItemTagId BOOKSHELF_STAIRS = createSuCraft("bookshelf_stairs");
    public static final BlockItemTagId INFESTED_STONE_BRICKS = createSuCraft("infested_stone_bricks");
    public static final BlockItemTagId STONE_WALLS = createSuCraft("stone_walls");
    public static final BlockItemTagId WOODEN_WALLS = createSuCraft("wooden_walls");

    /**
     * Analogous to {@link BlockItemTagId#create(String, String)}.
     */
    private static BlockItemTagId createSuCraft(String blockName, String itemName) {
        return BlockItemTagId.create(Identifier.fromNamespaceAndPath("sucraft", blockName), Identifier.fromNamespaceAndPath("sucraft", itemName));
    }

    /**
     * Analogous to {@link BlockItemTagId#create(String)}.
     */
    private static BlockItemTagId createSuCraft(String name) {
        return createSuCraft(name, name);
    }

}
