package org.sucraft.sushi.gamecontent.common;

import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockItemTagId;

public final class SuCraftBlockItemTags {

    private SuCraftBlockItemTags() {
        throw new UnsupportedOperationException();
    }

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
