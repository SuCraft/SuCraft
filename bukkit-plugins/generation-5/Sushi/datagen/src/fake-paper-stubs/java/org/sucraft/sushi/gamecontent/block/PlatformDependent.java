package org.sucraft.sushi.gamecontent.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.AABB;

public final class PlatformDependent {

    private PlatformDependent() {
        throw new UnsupportedOperationException();
    }

    public static final class BlockBehaviourProperties {

        private BlockBehaviourProperties() {
            throw new UnsupportedOperationException();
        }

        public static BlockBehaviour.Properties ofFullCopy(BlockBehaviour.Properties properties) {
            throw new UnsupportedOperationException(); // Paper only
        }

        public static BlockBehaviour.Properties ofLegacyCopy(BlockBehaviour.Properties properties) {
            throw new UnsupportedOperationException(); // Paper only
        }

    }

    public static final class Blocks {

        private Blocks() {
            throw new UnsupportedOperationException();
        }

        public static final BlockBehaviour.StateArgumentPredicate<AABB> NEAR_PLANE_INTERSECTS_OUTLINE = null; // Paper only

    }

}
