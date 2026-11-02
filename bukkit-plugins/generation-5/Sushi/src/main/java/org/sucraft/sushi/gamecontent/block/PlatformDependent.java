package org.sucraft.sushi.gamecontent.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class PlatformDependent {

    private PlatformDependent() {
        throw new UnsupportedOperationException();
    }

    public static final class BlockBehaviourProperties {

        private BlockBehaviourProperties() {
            throw new UnsupportedOperationException();
        }

        public static BlockBehaviour.Properties ofFullCopy(BlockBehaviour.Properties properties) {
            return BlockBehaviour.Properties.ofFullCopy(properties);
        }

        public static BlockBehaviour.Properties ofLegacyCopy(BlockBehaviour.Properties properties) {
            return BlockBehaviour.Properties.ofLegacyCopy(properties);
        }

    }

    public static final class BlockModelGenerators {

        private BlockModelGenerators() {
            throw new UnsupportedOperationException();
        }

        public static void addBarrel(net.minecraft.client.data.models.BlockModelGenerators generators, Block block) {
            throw new UnsupportedOperationException();
        }

        public static void addLadder(net.minecraft.client.data.models.BlockModelGenerators generators, Block block) {
            throw new UnsupportedOperationException();
        }

    }

}
