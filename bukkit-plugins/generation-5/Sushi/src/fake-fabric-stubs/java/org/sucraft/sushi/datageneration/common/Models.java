package org.sucraft.sushi.datageneration.common;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.resources.Identifier;
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
            Supplier<@Nullable Block> wall,
            Supplier<@Nullable Block> fence,
            Supplier<@Nullable Block> fenceGate,
            Supplier<@Nullable Block> button,
            Supplier<@Nullable Block> pressurePlate
        );

        void addBlockTrivialCube(Block block);

        void addBlockCubeColumn(Block block, @Nullable Block side, @Nullable Block end);

        void addBlockCubeColumn(Block block, @Nullable Identifier side, @Nullable Identifier end);

        void addBlockSlab(Block slab);

        void addBlockStairs(Block stairs);

        void addBlockWall(Block wall);

        void addBlockFence(Block fence);

        void addBlockFenceGate(Block fenceGate);

        void addBlockButton(Block button);

        void addBlockPressurePlate(Block pressurePlate);

    }

    interface BlockStateModelGenerator extends Consumer<BlockModelGenerators> {
    }

}
