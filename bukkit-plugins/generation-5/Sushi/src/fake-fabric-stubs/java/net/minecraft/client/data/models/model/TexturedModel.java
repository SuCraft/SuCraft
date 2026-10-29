package net.minecraft.client.data.models.model;

import net.minecraft.world.level.block.Block;
import java.util.function.Consumer;

public interface TexturedModel {

    TexturedModel.Provider CUBE = null;
    TexturedModel.Provider CUBE_BOTTOM_TOP = null;
    TexturedModel.Provider COLUMN = null;

    interface Provider {

        TexturedModel get(Block block);

    }

    TexturedModel updateTextures(Consumer<TextureMapping> mutator);

}
