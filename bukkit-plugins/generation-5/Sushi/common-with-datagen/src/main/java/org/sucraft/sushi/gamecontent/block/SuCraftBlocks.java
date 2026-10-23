package org.sucraft.sushi.gamecontent.block;

import it.unimi.dsi.fastutil.booleans.Boolean2ObjectFunction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.block.BlockType;
import org.sucraft.sushi.gamecontent.common.Family;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Stream;

public final class SuCraftBlocks {

    private SuCraftBlocks() {
        throw new UnsupportedOperationException();
    }

    // Stone / Andesite

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndFenceAndButtonAndPressurePlate ANDESITE_BRICKS =
        BlockSet.builder("andesite_bricks")
            .vanilla(Blocks.POLISHED_ANDESITE)
            .fullBlock().slab().stairs().wall().fence().button().pressurePlate()
            .wallFallback(Blocks.ANDESITE_WALL)
            .fenceFallback(Blocks.POPLAR_FENCE)
            .buttonFallback(Blocks.STONE_BUTTON)
            .pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();
    public static final SuCraftBlockType CHISELED_ANDESITE_BRICKS = blockType("chiseled_andesite_bricks")
        .fullBlock(Blocks.POLISHED_ANDESITE);

    // Stone / Calcite

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndFenceAndButtonAndPressurePlate POLISHED_CALCITE =
        BlockSet.builder("polished_calcite")
            .vanilla(Blocks.CALCITE)
            .fullBlock().slab().stairs().wall().fence().button().pressurePlate()
            .slabFallback(Blocks.SMOOTH_QUARTZ_SLAB).stairsFallback(Blocks.SMOOTH_QUARTZ_STAIRS).wallFallback(Blocks.DIORITE_WALL)
            .fenceFallback(Blocks.PALE_OAK_FENCE)
            .buttonFallback(Blocks.PALE_OAK_BUTTON)
            .pressurePlateFallback(Blocks.PALE_OAK_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndFenceAndButtonAndPressurePlate CALCITE_BRICKS =
        BlockSet.builder("calcite_bricks")
            .vanilla(Blocks.CALCITE)
            .fullBlock().slab().stairs().wall().fence().button().pressurePlate()
            .slabFallback(Blocks.SMOOTH_QUARTZ_SLAB).stairsFallback(Blocks.SMOOTH_QUARTZ_STAIRS).wallFallback(Blocks.DIORITE_WALL)
            .fenceFallback(Blocks.PALE_OAK_FENCE)
            .buttonFallback(Blocks.PALE_OAK_BUTTON)
            .pressurePlateFallback(Blocks.PALE_OAK_PRESSURE_PLATE)
            .build();
    public static final SuCraftBlockType CHISELED_CALCITE_BRICKS = blockType("chiseled_calcite_bricks")
        .fullBlock(Blocks.CALCITE);

    // Stone / Diorite

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndFenceAndButtonAndPressurePlate DIORITE_BRICKS =
        BlockSet.builder("diorite_bricks")
            .vanilla(Blocks.POLISHED_DIORITE)
            .fullBlock().slab().stairs().wall().fence().button().pressurePlate()
            .wallFallback(Blocks.DIORITE_WALL)
            .fenceFallback(Blocks.PALE_OAK_FENCE)
            .buttonFallback(Blocks.PALE_OAK_BUTTON)
            .pressurePlateFallback(Blocks.PALE_OAK_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType CHISELED_DIORITE_BRICKS = blockType("chiseled_diorite_bricks")
        .fullBlock(Blocks.POLISHED_DIORITE);

    // Stone / Dripstone

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndFenceAndButtonAndPressurePlate POLISHED_DRIPSTONE =
        BlockSet.builder("polished_dripstone")
            .vanilla(Blocks.DRIPSTONE_BLOCK)
            .fullBlock().slab().stairs().wall().fence().button().pressurePlate()
            .slabFallback(Blocks.GRANITE_SLAB).stairsFallback(Blocks.GRANITE_STAIRS).wallFallback(Blocks.GRANITE_WALL)
            .fenceFallback(Blocks.JUNGLE_FENCE)
            .buttonFallback(Blocks.JUNGLE_BUTTON)
            .pressurePlateFallback(Blocks.JUNGLE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndFenceAndButtonAndPressurePlate DRIPSTONE_BRICKS =
        BlockSet.builder("dripstone_bricks")
            .vanilla(Blocks.DRIPSTONE_BLOCK)
            .fullBlock().slab().stairs().wall().fence().button().pressurePlate()
            .slabFallback(Blocks.GRANITE_SLAB).stairsFallback(Blocks.GRANITE_STAIRS).wallFallback(Blocks.GRANITE_WALL)
            .fenceFallback(Blocks.JUNGLE_FENCE)
            .buttonFallback(Blocks.JUNGLE_BUTTON)
            .pressurePlateFallback(Blocks.JUNGLE_PRESSURE_PLATE)
            .build();
    public static final SuCraftBlockType CHISELED_DRIPSTONE_BRICKS = blockType("chiseled_dripstone_bricks")
        .fullBlock(Blocks.DRIPSTONE_BLOCK);

    // Stone / Granite
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndFenceAndButtonAndPressurePlate GRANITE_BRICKS =
        BlockSet.builder("granite_bricks")
            .vanilla(Blocks.POLISHED_GRANITE)
            .fullBlock().slab().stairs().wall().fence().button().pressurePlate()
            .wallFallback(Blocks.GRANITE_WALL)
            .fenceFallback(Blocks.JUNGLE_FENCE)
            .buttonFallback(Blocks.JUNGLE_BUTTON)
            .pressurePlateFallback(Blocks.JUNGLE_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType CHISELED_GRANITE_BRICKS = blockType("chiseled_granite_bricks")
        .fullBlock(Blocks.POLISHED_GRANITE);

    // Stone / Regular

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndFenceAndButtonAndPressurePlate DIRTY_STONE_BRICKS =
        BlockSet.builder("dirty_stone_bricks")
            .vanilla(Blocks.STONE_BRICKS)
            .fullBlock().slab().stairs().wall().fence().button().pressurePlate()
            .fenceFallback(Blocks.POPLAR_FENCE)
            .buttonFallback(Blocks.STONE_BUTTON)
            .pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndFenceAndButtonAndPressurePlate SNOWY_STONE_BRICKS =
        BlockSet.builder("snowy_stone_bricks")
            .vanilla(Blocks.STONE_BRICKS)
            .fullBlock().slab().stairs().wall().fence().button().pressurePlate()
            .fenceFallback(Blocks.POPLAR_FENCE)
            .buttonFallback(Blocks.STONE_BUTTON)
            .pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    // Stone / Tuff

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndFenceAndButtonAndPressurePlate ROUGH_POLISHED_TUFF =
        BlockSet.builder("polished_tuff")
            .vanilla(Blocks.POLISHED_TUFF)
            .fullBlock().slab().stairs().wall().fence().button().pressurePlate()
            .fenceFallback(Blocks.POPLAR_FENCE)
            .buttonFallback(Blocks.STONE_BUTTON)
            .pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndFenceAndButtonAndPressurePlate ROUGH_TUFF_BRICKS =
        BlockSet.builder("tuff_bricks")
            .vanilla(Blocks.TUFF_BRICKS)
            .fullBlock().slab().stairs().wall().fence().button().pressurePlate()
            .fenceFallback(Blocks.POPLAR_FENCE)
            .buttonFallback(Blocks.STONE_BUTTON)
            .pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType ROUGH_CHISELED_TUFF_BRICKS = blockType("chiseled_tuff_bricks")
        .fullBlock(Blocks.CHISELED_TUFF_BRICKS);

    // Wood / Dyed planks

    public static final class DyedPlankFamily<V> extends Family.Boolean<V> {

        public DyedPlankFamily(Boolean2ObjectFunction<V> constructor) {
            super(constructor);
        }

        public V painted() {
            return this.get(false);
        }

        public V abraded() {
            return this.get(true);
        }

    }

    public static final DyedPlankFamily<Family.DyeColor<BlockSet.WithFullBlockAndSlabAndStairsAndWallAndFenceAndFenceGateAndButtonAndPressurePlate>> DYED_PLANKS =
        new DyedPlankFamily<>(sanded ->
            new Family.DyeColor<>(dyeColor ->
                BlockSet.builder(dyeColor.getSerializedName() + (sanded ? "" : "_vibrant") + "_planks")
                    .shapeIdentifierSuffixFunction(suffix -> "_plank" + suffix)
                    .fallback(switch (dyeColor) {
                        case BLACK -> Blocks.DARK_OAK_PLANKS;
                        case BLUE -> Blocks.WARPED_PLANKS;
                        case BROWN -> Blocks.SPRUCE_PLANKS;
                        case CYAN -> Blocks.WARPED_PLANKS;
                        case GRAY -> Blocks.POPLAR_PLANKS;
                        case GREEN -> Blocks.WARPED_PLANKS;
                        case LIGHT_BLUE -> Blocks.WARPED_PLANKS;
                        case LIGHT_GRAY -> Blocks.POPLAR_PLANKS;
                        case LIME -> Blocks.WARPED_PLANKS;
                        case MAGENTA -> Blocks.CRIMSON_PLANKS;
                        case ORANGE -> Blocks.ACACIA_PLANKS;
                        case PINK -> Blocks.CHERRY_PLANKS;
                        case PURPLE -> Blocks.CRIMSON_PLANKS;
                        case RED -> Blocks.MANGROVE_PLANKS;
                        case WHITE -> Blocks.PALE_OAK_PLANKS;
                        case YELLOW -> Blocks.BAMBOO_PLANKS;
                    })
                    .propertiesFrom(Blocks.OAK_PLANKS)
                    .fullBlock().slab().stairs().wall().fence().fenceGate().button().pressurePlate()
                    .wallFallback(switch (dyeColor) {
                        case BLACK -> Blocks.POLISHED_BLACKSTONE_BRICK_WALL;
                        case BLUE -> Blocks.PRISMARINE_WALL;
                        case BROWN -> Blocks.MUD_BRICK_WALL;
                        case CYAN -> Blocks.PRISMARINE_WALL;
                        case GRAY -> Blocks.POLISHED_TUFF_WALL;
                        case GREEN -> Blocks.PRISMARINE_WALL;
                        case LIGHT_BLUE -> Blocks.PRISMARINE_WALL;
                        case LIGHT_GRAY -> Blocks.ANDESITE_WALL;
                        case LIME -> Blocks.PRISMARINE_WALL;
                        case MAGENTA -> Blocks.CINNABAR_WALL;
                        case ORANGE -> Blocks.RED_SANDSTONE_WALL;
                        case PINK -> Blocks.CINNABAR_WALL;
                        case PURPLE -> Blocks.CINNABAR_WALL;
                        case RED -> Blocks.CINNABAR_BRICK_WALL;
                        case WHITE -> Blocks.DIORITE_WALL;
                        case YELLOW -> Blocks.SULFUR_BRICK_WALL;
                    })
                    .build()
            )
        );

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

    private static Stream<SuCraftBlockType> typesFromBlockSet(BlockSet blockSet) {
        return Arrays.stream(blockSet.types());
    }

    private static Stream<SuCraftBlockType> typesFromFamily(Family<?, ?> family) {
        return family.all().stream().flatMap(value -> {
            if (value instanceof SuCraftBlockType type) {
                return Stream.of(type);
            } else if (value instanceof BlockSet blockSet) {
                return typesFromBlockSet(blockSet);
            } else if (value instanceof Family nested) {
                return typesFromFamily(nested);
            }
            return Stream.empty();
        });
    }

    private static Stream<SuCraftBlockType> typesFromField(Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isStatic(field.getModifiers()) || !Modifier.isFinal(field.getModifiers())) {
            return Stream.empty();
        }
        if (SuCraftBlockType.class.isAssignableFrom(field.getType())) {
            field.trySetAccessible();
            try {
                return Stream.of((SuCraftBlockType) field.get(null));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        if (BlockSet.class.isAssignableFrom(field.getType())) {
            field.trySetAccessible();
            try {
                return typesFromBlockSet((BlockSet) field.get(null));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        if (Family.class.isAssignableFrom(field.getType())) {
            field.trySetAccessible();
            try {
                return typesFromFamily((Family) field.get(null));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return Stream.empty();
    }

    public static SuCraftBlockType[] types() {
        if (types == null) {
            types = Arrays.stream(SuCraftBlocks.class.getDeclaredFields()).flatMap(SuCraftBlocks::typesFromField).toArray(SuCraftBlockType[]::new);
        }
        return types;
    }

}
