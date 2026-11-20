package org.sucraft.suki.gamecontent.block.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

/**
 * Analogous to {@link BlockTags}.
 */
public final class SukiBlockTags {

    private SukiBlockTags() {
        throw new UnsupportedOperationException();
    }

    public static final TagKey<Block> SUPPORTS_VEGETATION_MANUAL = create("supports_vegetation_manual");
    public static final TagKey<Block> MAY_PLACE_ON_WATER = create("may_place_on_water");

    private static TagKey<Block> create(final String name) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("sucraft", name));
    }

}
