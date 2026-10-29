package net.minecraft.client.data.models.model;

import net.minecraft.world.level.block.Block;
import net.minecraft.client.resources.model.sprite.Material;

public interface TextureMapping {

    TextureMapping put(TextureSlot slot, Material material);

    static Material getBlockTexture(Block block) {
        throw new UnsupportedOperationException();
    }

    static Material getBlockTexture(Block block, String suffix) {
        throw new UnsupportedOperationException();
    }

}
