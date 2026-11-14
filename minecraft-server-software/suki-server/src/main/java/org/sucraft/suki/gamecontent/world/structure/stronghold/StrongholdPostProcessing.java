package org.sucraft.suki.gamecontent.world.structure.stronghold;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.jspecify.annotations.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

public final class StrongholdPostProcessing {

    private StrongholdPostProcessing() {
        throw new UnsupportedOperationException();
    }

    public static final Direction[] BESIDE_OR_ABOVE_DIRECTIONS = {Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH, Direction.UP};

    public static final Direction[] BESIDE_DIRECTIONS = {Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH};

    public static boolean countsAsSolid(BlockGetter world, BlockPos pos, Direction inDirection) {
        return countsAsSolid(world, pos, world.getBlockState(pos), inDirection);
    }

    public static boolean countsAsSolid(BlockGetter world, BlockPos pos, BlockState block, Direction inDirection) {
        return !block.isAir() && !block.liquid() && block.isFaceSturdy(world, pos, inDirection);
    }

    public static boolean hasRelativeSolid(BlockGetter world, BlockPos pos, Direction direction) {
        return countsAsSolid(world, pos.relative(direction), direction.getOpposite());
    }

    public static boolean hasAnyRelativeSolid(BlockGetter world, BlockPos pos, Direction[] directions) {
        for (Direction direction : directions) {
            if (hasRelativeSolid(world, pos, direction)) {
                return true;
            }
        }
        return false;
    }

    public static boolean anyHasRelativeSolid(BlockGetter world, BlockPos[] poss, Direction direction) {
        for (BlockPos pos : poss) {
            if (hasRelativeSolid(world, pos, direction)) {
                return true;
            }
        }
        return false;
    }

    public static boolean anyHasAnyRelativeSolid(BlockGetter world, BlockPos[] poss, Direction[] directions) {
        for (BlockPos pos : poss) {
            if (hasAnyRelativeSolid(world, pos, directions)) {
                return true;
            }
        }
        return false;
    }

    public static boolean canBeFilledUpByWaterSourceIfWaterFlowsIn(BlockState state) {
        return state.isAir() || state.is(BlockTags.BUTTONS) || state.is(Blocks.TORCH) || state.is(Blocks.WALL_TORCH) || state.is(Blocks.LEVER) || state.is(Blocks.COBWEB) || state.is(BlockTags.WOOL_CARPETS) || state.is(Blocks.MOSS_CARPET);
    }

    public static @Nullable BlockState getRelativeLiquidDefaultBlockState(BlockGetter world, BlockPos pos, Direction direction) {
        BlockState block = world.getBlockState(pos);
        Optional<Boolean> blockWaterlogged = block.getOptionalValue(BlockStateProperties.WATERLOGGED);
        if (!(block.is(Blocks.WATER) || (blockWaterlogged.isPresent() && blockWaterlogged.get()))) {
            BlockState relativeBlock = world.getBlockState(pos.relative(direction));
            Optional<Boolean> relativeBlockWaterlogged = relativeBlock.getOptionalValue(BlockStateProperties.WATERLOGGED);
            if (relativeBlock.is(Blocks.WATER) || (relativeBlockWaterlogged.isPresent() && relativeBlockWaterlogged.get())) {
                if (canBeFilledUpByWaterSourceIfWaterFlowsIn(block)) {
                    return Blocks.WATER.defaultBlockState();
                } else if (blockWaterlogged.isPresent()) {
                    return block.getBlock().withPropertiesOf(block).setValue(BlockStateProperties.WATERLOGGED, true);
                }
            }
        }
        return null;
    }

    public static @Nullable BlockState getAnyRelativeLiquidDefaultBlockState(BlockGetter world, BlockPos pos, Direction[] directions) {
        for (Direction direction : directions) {
            @Nullable BlockState forDirection = getRelativeLiquidDefaultBlockState(world, pos, direction);
            if (forDirection != null) {
                return forDirection;
            }
        }
        return null;
    }

    public static void replace(WorldGenLevel world, BoundingBox boundingBox1, BoundingBox boundingBox2, Predicate<BlockState> oldState, BlockState newState, @Nullable RandomSource random, double chance, boolean requireSolidBelow, boolean requireSolidBelowOrBeside, boolean requireSolidAdjacent, @Nullable Predicate<Triple<Integer, Integer, Integer>> positionPredicate) {
        int minX = Math.max(boundingBox1.minX(), boundingBox2.minX());
        int maxX = Math.min(boundingBox1.maxX(), boundingBox2.maxX());
        int minY = Math.max(boundingBox1.minY(), boundingBox2.minY());
        int maxY = Math.min(boundingBox1.maxY(), boundingBox2.maxY());
        int minZ = Math.max(boundingBox1.minZ(), boundingBox2.minZ());
        int maxZ = Math.min(boundingBox1.maxZ(), boundingBox2.maxZ());
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    if (positionPredicate != null && !positionPredicate.test(new ImmutableTriple<>(x, y, z))) {
                        continue;
                    }
                    BlockPos pos = new BlockPos(x, y, z);
                    if (oldState.test(world.getBlockState(pos))) {
                        if (random == null || (double) random.nextFloat() < chance) {
                            boolean hasSupport = true;
                            // Check for missing support from surroundings
                            if (requireSolidBelow || requireSolidBelowOrBeside || requireSolidAdjacent) {
                                if (!hasRelativeSolid(world, pos, Direction.DOWN)) {
                                    if (requireSolidBelow) {
                                        hasSupport = false;
                                    } else if (!hasAnyRelativeSolid(world, pos, BESIDE_DIRECTIONS)) {
                                        if (requireSolidBelowOrBeside) {
                                            hasSupport = false;
                                        } else if (!hasRelativeSolid(world, pos, Direction.UP)) {
                                            hasSupport = false;
                                        }
                                    }
                                }
                            }
                            if (hasSupport) {
                                world.setBlock(pos, newState, Block.UPDATE_CLIENTS);
                            }
                        }
                    }
                }
            }
        }
    }

}
