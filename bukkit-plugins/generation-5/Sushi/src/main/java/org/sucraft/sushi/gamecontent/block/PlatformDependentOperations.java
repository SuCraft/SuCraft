package org.sucraft.sushi.gamecontent.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.AABB;

public final class PlatformDependentOperations {

    private PlatformDependentOperations() {
        throw new UnsupportedOperationException();
    }

    // Paper only

    public static BlockBehaviour.Properties ofFullCopy(BlockBehaviour.Properties properties) {
        return BlockBehaviour.Properties.ofFullCopy(properties);
    }

    public static BlockBehaviour.Properties ofLegacyCopy(BlockBehaviour.Properties properties) {
        return BlockBehaviour.Properties.ofLegacyCopy(properties);
    }

    public static final BlockBehaviour.StateArgumentPredicate<AABB> NEAR_PLANE_INTERSECTS_OUTLINE = Blocks.NEAR_PLANE_INTERSECTS_OUTLINE;

}
