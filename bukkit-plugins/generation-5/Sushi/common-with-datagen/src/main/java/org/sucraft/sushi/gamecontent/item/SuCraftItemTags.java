package org.sucraft.sushi.gamecontent.item;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;import org.sucraft.sushi.gamecontent.common.SuCraftBlockItemTags;

public final class SuCraftItemTags {

    private SuCraftItemTags() {
        throw new UnsupportedOperationException();
    }

    public static final TagKey<Item> WOODEN_WALLS = SuCraftBlockItemTags.WOODEN_WALLS.item();

}
