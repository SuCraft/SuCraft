package org.sucraft.sushi.gamecontent.common;

import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockItemTagId;

public final class CBlockItemTags {

    private CBlockItemTags() {
        throw new UnsupportedOperationException();
    }

    public static final BlockItemTagId BARRELS = createC("barrels");
    public static final BlockItemTagId BARRELS_WOODEN = createC("barrels/wooden");
    public static final BlockItemTagId BOOKSHELVES = createC("bookshelves");

    /**
     * Analogous to {@link BlockItemTagId#create(String, String)}.
     */
    private static BlockItemTagId createC(String blockName, String itemName) {
        return BlockItemTagId.create(Identifier.fromNamespaceAndPath("c", blockName), Identifier.fromNamespaceAndPath("c", itemName));
    }

    /**
     * Analogous to {@link BlockItemTagId#create(String)}.
     */
    private static BlockItemTagId createC(String name) {
        return createC(name, name);
    }

}
