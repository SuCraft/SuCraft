package org.sucraft.sushi.datageneration.common;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Models {

    interface CollectedFactory {

        void addBlock(BlockStateModelGenerator generator);

        void addBlockFamily(
            Supplier<Block> fullBlock,
            Supplier<@Nullable Block> slab,
            Supplier<@Nullable Block> stairs,
            Supplier<@Nullable Block> wall
        );

        void addBlockTrivialCube(Block block);

        void addBlockSlab(Block slab);

        void addBlockStairs(Block stairs);

        void addBlockWall(Block slab);

    }

    interface BlockStateModelGenerator extends Consumer<BlockModelGenerators> {
    }

}
