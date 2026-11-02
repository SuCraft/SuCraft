package org.sucraft.sushi.gamecontent.common;

import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockItemTagId;
import spout.branding.SpoutNamespace;

public final class SpoutBlockItemTags {

    private SpoutBlockItemTags() {
        throw new UnsupportedOperationException();
    }

    public static final BlockItemTagId LADDERS = createSpout("ladders");
    public static final BlockItemTagId LADDERS_WOODEN = createSpout("ladders/wooden");

    /**
     * Analogous to {@link BlockItemTagId#create(String, String)}.
     */
    private static BlockItemTagId createSpout(String blockName, String itemName) {
        return BlockItemTagId.create(Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, blockName), Identifier.fromNamespaceAndPath(SpoutNamespace.SPOUT, itemName));
    }

    /**
     * Analogous to {@link BlockItemTagId#create(String)}.
     */
    private static BlockItemTagId createSpout(String name) {
        return createSpout(name, name);
    }

}
