package org.sucraft.sushi.gamecontent.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.block.BlockType;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Stream;

public final class SuCraftBlocks {

    private SuCraftBlocks() {
        throw new UnsupportedOperationException();
    }

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall DIRTY_STONE_BRICKS =
        BlockSet.builder("dirty_stone_bricks")
            .vanilla(Blocks.STONE_BRICKS)
            .fullBlock().slab().stairs().wall().button()
            .buttonFallback(Blocks.STONE_BUTTON)
            .build();
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall SNOWY_STONE_BRICKS =
        BlockSet.builder("snowy_stone_bricks")
            .vanilla(Blocks.STONE_BRICKS)
            .fullBlock().slab().stairs().wall().button()
            .buttonFallback(Blocks.STONE_BUTTON)
            .build();
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall ANDESITE_BRICKS =
        BlockSet.builder("andesite_bricks")
            .vanilla(Blocks.POLISHED_ANDESITE)
            .fullBlock().slab().stairs().wall().button()
            .wallFallback(Blocks.ANDESITE_WALL)
            .buttonFallback(Blocks.STONE_BUTTON)
            .build();
    public static final SuCraftBlockType CHISELED_ANDESITE_BRICKS = blockType("chiseled_andesite_bricks")
        .fullBlock(Blocks.POLISHED_ANDESITE);
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall DIORITE_BRICKS =
        BlockSet.builder("diorite_bricks")
            .vanilla(Blocks.POLISHED_DIORITE)
            .fullBlock().slab().stairs().wall().button()
            .wallFallback(Blocks.DIORITE_WALL)
            .buttonFallback(Blocks.STONE_BUTTON)
            .build();
    public static final SuCraftBlockType CHISELED_DIORITE_BRICKS = blockType("chiseled_diorite_bricks")
        .fullBlock(Blocks.POLISHED_DIORITE);
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall GRANITE_BRICKS =
        BlockSet.builder("granite_bricks")
            .vanilla(Blocks.POLISHED_GRANITE)
            .fullBlock().slab().stairs().wall().button()
            .wallFallback(Blocks.GRANITE_WALL)
            .buttonFallback(Blocks.STONE_BUTTON)
            .build();
    public static final SuCraftBlockType CHISELED_GRANITE_BRICKS = blockType("chiseled_granite_bricks")
        .fullBlock(Blocks.POLISHED_GRANITE);
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall ROUGH_POLISHED_TUFF =
        BlockSet.builder("polished_tuff")
            .vanilla(Blocks.POLISHED_TUFF)
            .fullBlock().slab().stairs().wall().button()
            .buttonFallback(Blocks.STONE_BUTTON)
            .build();
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall ROUGH_TUFF_BRICKS =
        BlockSet.builder("tuff_bricks")
            .vanilla(Blocks.TUFF_BRICKS)
            .fullBlock().slab().stairs().wall().button()
            .buttonFallback(Blocks.STONE_BUTTON)
            .build();
    public static final SuCraftBlockType ROUGH_CHISELED_TUFF_BRICKS = blockType("chiseled_tuff_bricks")
        .fullBlock(Blocks.CHISELED_TUFF_BRICKS);
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall POLISHED_CALCITE =
        BlockSet.builder("polished_calcite")
            .vanilla(Blocks.CALCITE)
            .fullBlock().slab().stairs().wall().button()
            .slabFallback(Blocks.SMOOTH_QUARTZ_SLAB).stairsFallback(Blocks.SMOOTH_QUARTZ_STAIRS).wallFallback(Blocks.DIORITE_WALL)
            .buttonFallback(Blocks.STONE_BUTTON)
            .build();
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall CALCITE_BRICKS =
        BlockSet.builder("calcite_bricks")
            .vanilla(Blocks.CALCITE)
            .fullBlock().slab().stairs().wall().button()
            .slabFallback(Blocks.SMOOTH_QUARTZ_SLAB).stairsFallback(Blocks.SMOOTH_QUARTZ_STAIRS).wallFallback(Blocks.DIORITE_WALL)
            .buttonFallback(Blocks.STONE_BUTTON)
            .build();
    public static final SuCraftBlockType CHISELED_CALCITE_BRICKS = blockType("chiseled_calcite_bricks")
        .fullBlock(Blocks.CALCITE);
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall POLISHED_DRIPSTONE =
        BlockSet.builder("polished_dripstone")
            .vanilla(Blocks.DRIPSTONE_BLOCK)
            .fullBlock().slab().stairs().wall().button()
            .slabFallback(Blocks.GRANITE_SLAB).stairsFallback(Blocks.GRANITE_STAIRS).wallFallback(Blocks.GRANITE_WALL)
            .buttonFallback(Blocks.STONE_BUTTON)
            .build();
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall DRIPSTONE_BRICKS =
        BlockSet.builder("dripstone_bricks")
            .vanilla(Blocks.DRIPSTONE_BLOCK)
            .fullBlock().slab().stairs().wall().button()
            .slabFallback(Blocks.GRANITE_SLAB).stairsFallback(Blocks.GRANITE_STAIRS).wallFallback(Blocks.GRANITE_WALL)
            .buttonFallback(Blocks.STONE_BUTTON)
            .build();
    public static final SuCraftBlockType CHISELED_DRIPSTONE_BRICKS = blockType("chiseled_dripstone_bricks")
        .fullBlock(Blocks.DRIPSTONE_BLOCK);

    private static SuCraftBlockType blockType(String path) {
        return new SuCraftBlockType(path);
    }

    private static BlockType[] values;
    private static Block[] valuesNMS;
    private static Block[] valuesForDataGeneration;
    private static BlockSet[] blockSets;
    private static SuCraftBlockType[] types;

    public static BlockType[] values() {
        if (values == null) {
            values = Arrays.stream(types())
                .map(SuCraftBlockType::get).toArray(BlockType[]::new);
        }
        return values;
    }

    public static Block[] valuesNMS() {
        if (valuesNMS == null) {
            valuesNMS = Arrays.stream(types())
                .map(SuCraftBlockType::getNMS).toArray(Block[]::new);
        }
        return valuesNMS;
    }

    public static Block[] valuesForDataGeneration() {
        if (valuesForDataGeneration == null) {
            valuesForDataGeneration = Arrays.stream(types())
                .map(SuCraftBlockType::getForDataGeneration).toArray(Block[]::new);
        }
        return valuesForDataGeneration;
    }

    public static BlockSet[] blockSets() {
        if (blockSets == null) {
            blockSets = Arrays.stream(SuCraftBlocks.class.getDeclaredFields())
                .filter(field ->
                    Modifier.isPublic(field.getModifiers()) &&
                        Modifier.isStatic(field.getModifiers()) &&
                        Modifier.isFinal(field.getModifiers()) &&
                        BlockSet.class.isAssignableFrom(field.getType()))
                .map(field -> {
                    field.trySetAccessible();
                    try {
                        return (BlockSet) field.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(BlockSet[]::new);
        }
        return blockSets;
    }

    public static SuCraftBlockType[] types() {
        if (types == null) {
            Stream<SuCraftBlockType> declared = Arrays.stream(SuCraftBlocks.class.getDeclaredFields())
                .filter(field ->
                    Modifier.isPublic(field.getModifiers()) &&
                        Modifier.isStatic(field.getModifiers()) &&
                        Modifier.isFinal(field.getModifiers()) &&
                        SuCraftBlockType.class.isAssignableFrom(field.getType()))
                .map(field -> {
                    field.trySetAccessible();
                    try {
                        return (SuCraftBlockType) field.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
            Stream<SuCraftBlockType> fromBlockSets = Arrays.stream(blockSets())
                .map(BlockSet::types).flatMap(Arrays::stream);
            types = Stream.concat(declared, fromBlockSets).toArray(SuCraftBlockType[]::new);
        }
        return types;
    }

}
