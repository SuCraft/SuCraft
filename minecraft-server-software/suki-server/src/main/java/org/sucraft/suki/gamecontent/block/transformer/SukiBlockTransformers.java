package org.sucraft.suki.gamecontent.block.transformer;

import net.minecraft.core.component.BlockTransformer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.component.BlockTransformers;

/**
 * Analogous to {@link BlockTransformers}.
 */
public final class SukiBlockTransformers {

    private SukiBlockTransformers() {
        throw new UnsupportedOperationException();
    }

    public static final ResourceKey<BlockTransformer> RED_DYE = createKey("red_dye");

    private static ResourceKey<BlockTransformer> createKey(final String name) {
        return ResourceKey.create(Registries.BLOCK_TRANSFORMER, Identifier.fromNamespaceAndPath("sucraft", name));
    }

}
