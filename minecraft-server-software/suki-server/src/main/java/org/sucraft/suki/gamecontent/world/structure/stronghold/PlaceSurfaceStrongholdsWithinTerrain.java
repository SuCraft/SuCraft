package org.sucraft.suki.gamecontent.world.structure.stronghold;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class PlaceSurfaceStrongholdsWithinTerrain {

    private PlaceSurfaceStrongholdsWithinTerrain() {
        throw new UnsupportedOperationException();
    }

    public static boolean isEmpty(BlockState state) {
        if (state.is(BlockTags.LEAVES)) {
            return false;
        }
        return state.isAir() || state.liquid() || state.is(BlockTags.REPLACEABLE) || state.is(BlockTags.REPLACEABLE_BY_TREES) || state.is(BlockTags.REPLACEABLE_BY_MUSHROOMS);
    }

    public static boolean isSupport(BlockState state) {
        if (isEmpty(state) || isUnreplaceableSolid(state)) {
            return false;
        }
        return true;
    }

    public static boolean isUnreplaceableSolid(BlockState state) {
        return state.is(BlockTags.LEAVES) || state.is(BlockTags.LOGS) || state.is(BlockTags.ICE);
    }

    public static boolean canPlace(
        final ServerLevelAccessor level,
        final BlockState newState,
        final BlockPos pos
    ) {
        if (!CurrentStrongholdGeneration.getSurface()) {
            return true;
        }
        if (CurrentStrongholdGeneration.getPortalRoom()) {
            return true;
        }
        var oldState = level.getBlockState(pos);
        if (oldState == newState) {
            // No need to place
            return false;
        }
        if (isUnreplaceableSolid(oldState)) {
            if (!CurrentStrongholdGeneration.getMadeUnreplaceableSolidOnPurpose(pos)) {
                return false;
            }
        } else if (isUnreplaceableSolid(newState)) {
            CurrentStrongholdGeneration.addMadeUnreplaceableSolidOnPurpose(pos);
        }
        var stateBelow = pos.getY() == level.getMinY() ? Blocks.AIR.defaultBlockState() : level.getBlockState(pos.below());
        if (isEmpty(oldState) && (!isSupport(stateBelow) || CurrentStrongholdGeneration.getRandom().nextInt(2) == 0)) {
            if (!CurrentStrongholdGeneration.getClearedOnPurpose(pos)) {
                return false;
            }
        } else if (isEmpty(newState)) {
            CurrentStrongholdGeneration.addClearedOnPurpose(pos);
        }
        return true;
    }

}
