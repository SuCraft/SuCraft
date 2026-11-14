package org.sucraft.sushi.gamecontent.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class SuCraftBlockTags {

    private SuCraftBlockTags() {
        throw new UnsupportedOperationException();
    }

    public static final TagKey<Block> SUPPORTS_VEGETATION_MANUAL = create("supports_vegetation_manual");
    public static final TagKey<Block> REQUIRED_FOR_FROST_LEAF_AMBIENCE = create("required_for_frost_leaf_ambiance");
    public static final TagKey<Block> SUPPORTS_FROST_VEGETATION = create("supports_frost_vegetation");

    private static TagKey<Block> create(final String name) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("sucraft", name));
    }

}
