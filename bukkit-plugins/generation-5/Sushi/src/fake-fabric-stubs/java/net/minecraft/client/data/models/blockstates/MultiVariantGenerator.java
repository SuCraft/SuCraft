package net.minecraft.client.data.models.blockstates;

import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.world.level.block.Block;

public interface MultiVariantGenerator extends BlockModelDefinitionGenerator {

    static MultiVariantGenerator dispatch(Block block, MultiVariant initialModel) {
        throw new UnsupportedOperationException();
    }

}
