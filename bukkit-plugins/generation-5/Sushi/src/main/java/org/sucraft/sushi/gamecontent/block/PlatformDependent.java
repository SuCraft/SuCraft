package org.sucraft.sushi.gamecontent.block;

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

}
