package org.sucraft.sushi.gamecontent.block;

import it.unimi.dsi.fastutil.booleans.Boolean2ObjectFunction;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchBlock;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import org.bukkit.block.BlockType;
import org.jspecify.annotations.Nullable;
import org.sucraft.sushi.datageneration.common.DataGenerationFlag;
import org.sucraft.sushi.gamecontent.common.Family;
import org.sucraft.sushi.gamecontent.common.SuCraftBlockItemTags;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class SuCraftBlocks {

    private SuCraftBlocks() {
        throw new UnsupportedOperationException();
    }

    // Mineral / Flint

    private static void finalizeFlintBlockProperties(BlockBehaviour.Properties properties) {
        properties.instrument(Blocks.NETHERITE_BLOCK.properties().instrument);
        properties.sound(SoundType.METAL);
        properties.mapColor(MapColor.COLOR_GRAY);
        properties.strength(3.5f, 6.0f);
        PlatformDependent.BlockBehaviourProperties.setRequiresCorrectToolForDrops(properties, false);
    }

    public static final BlockSet.WithFullBlockAndSlabAndStairs FLINT_BLOCK =
        BlockSet.builder("flint_block").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.DEEPSLATE_TILES).fullBlockModelProvider(block -> TexturedModel.CUBE_BOTTOM_TOP.get(block).updateTextures(mappings -> {
                mappings.put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(block));
            }))
            .slab()
            .stairs()
            .propertiesFinalizer(SuCraftBlocks::finalizeFlintBlockProperties)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairs FLINT_TILES =
        BlockSet.builder("flint_tiles").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.DEEPSLATE_TILES)
            .slab()
            .stairs()
            .propertiesFinalizer(SuCraftBlocks::finalizeFlintBlockProperties)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairs FLINT_PILLAR;

    static {
        var definedByCode =
            BlockSet.builder("flint_pillar").mineableWithPickaxe()
                .fullBlockOnlyForDataGeneration().vanilla(Blocks.POLISHED_BASALT)
                .fullBlockModelProvider(block -> TexturedModel.COLUMN.get(block).updateTextures(mappings -> {
                    mappings.put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top"));
                }))
                .slab().slabFallback(Blocks.DEEPSLATE_TILE_SLAB)
                .stairs().stairsFallback(Blocks.DEEPSLATE_TILE_STAIRS)
                .propertiesFinalizer(SuCraftBlocks::finalizeFlintBlockProperties)
                .build();
        FLINT_PILLAR = new BlockSet.IndirectImpl(
            () -> ifInitializingElse(definedByCode.fullBlockType(), blockTypeNotDefinedByCode("flint_pillar")),
            () -> definedByCode.slabType(),
            () -> definedByCode.stairsType(),
            null,
            null,
            null,
            null,
            null
        );
    }

    // Soil / Dirt

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate DIRT_BRICKS =
        BlockSet.builder("dirt_bricks").mineableWithShovel()
            .fullBlock().vanilla(Blocks.DIRT)
            .nonVolatileShapeTag(BlockTags.AZALEA_GROWS_ON).nonVolatileShapeTag(BlockTags.SUPPORTS_BAMBOO).nonVolatileShapeTag(BlockTags.SUPPORTS_BIG_DRIPLEAF).nonVolatileShapeTag(BlockTags.SUPPORTS_SUGAR_CANE).nonVolatileShapeTag(BlockTags.SUPPORTS_VEGETATION)
            .slab().slabFallback(Blocks.MUD_BRICK_SLAB)
            .stairs().stairsFallback(Blocks.MUD_BRICK_STAIRS)
            .stoneWall().wallFallback(Blocks.MUD_BRICK_WALL)
            /*.stoneFence().fenceFallback(Blocks.JUNGLE_FENCE)*/
            .button().buttonFallback(Blocks.JUNGLE_BUTTON).blockSetType(BlockSetType.STONE)
            .pressurePlate().pressurePlateFallback(Blocks.JUNGLE_PRESSURE_PLATE)
            .build();

    // Soil - Mud

    public static final SuCraftBlockType CHISELED_MUD_BRICKS = blockType("chiseled_mud_bricks")
        .fullBlock(Blocks.MUD_BRICKS)
        .mineableWithPickaxe()
        .dataGenerateItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY);

    // Soil - Red sand

    // public static final BlockSet.WithButtonAndPressurePlate RED_SANDSTONE =
    //     BlockSet.builder("red_sandstone").mineableWithPickaxe()
    //         .fullBlockOnlyForDataGeneration().vanilla(Blocks.RED_SANDSTONE)
    //         .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.ACACIA_BUTTON)
    //         .stonePressurePlate().pressurePlateFallback(Blocks.ACACIA_PRESSURE_PLATE)
    //         .build();

    public static final BlockSet.WithStairs CUT_RED_SANDSTONE =
        BlockSet.builder("cut_red_sandstone").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.CUT_RED_SANDSTONE)
            .stairs().stairsFallback(Blocks.RED_SANDSTONE_STAIRS)
            // .stoneWall().wallFallback(Blocks.RED_SANDSTONE_WALL)
            // .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.ACACIA_BUTTON)
            // .stonePressurePlate().pressurePlateFallback(Blocks.ACACIA_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithWallAndButtonAndPressurePlate SMOOTH_RED_SANDSTONE =
        BlockSet.builder("smooth_red_sandstone").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.SMOOTH_RED_SANDSTONE).fullBlockModelProvider(block -> TexturedModel.CUBE.get(block).updateTextures(mappings -> {
                mappings.put(TextureSlot.WALL, TextureMapping.getBlockTexture(Blocks.RED_SANDSTONE, "_top"));
            }))
            .stoneWall().wallFallback(Blocks.RED_SANDSTONE_WALL)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.ACACIA_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.ACACIA_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate ROUGH_RED_SANDSTONE =
        BlockSet.builder("rough_red_sandstone").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.RED_SANDSTONE).fullBlockModelProvider(block -> TexturedModel.CUBE.get(block).updateTextures(mappings -> {
                mappings.put(TextureSlot.ALL, TextureMapping.getBlockTexture(Blocks.RED_SANDSTONE, "_bottom"));
                mappings.put(TextureSlot.WALL, TextureMapping.getBlockTexture(Blocks.RED_SANDSTONE, "_bottom"));
            })).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab()
            .stairs()
            .stoneWall()
            /*.stoneFence().fenceFallback(Blocks.ACACIA_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.ACACIA_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.ACACIA_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate RED_SANDSTONE_BRICKS =
        BlockSet.builder("red_sandstone_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.RED_SANDSTONE).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab()
            .stairs()
            .stoneWall()
            /*.stoneFence().fenceFallback(Blocks.ACACIA_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.ACACIA_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.ACACIA_PRESSURE_PLATE)
            .build();

    // Soil - Sand

    // public static final BlockSet.WithButtonAndPressurePlate SANDSTONE =
    //     BlockSet.builder("sandstone").mineableWithPickaxe()
    //         .fullBlockOnlyForDataGeneration().vanilla(Blocks.SANDSTONE)
    //         .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.BIRCH_BUTTON)
    //         .stonePressurePlate().pressurePlateFallback(Blocks.BIRCH_PRESSURE_PLATE)
    //         .build();

    public static final BlockSet.WithStairs CUT_SANDSTONE =
        BlockSet.builder("cut_sandstone").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.CUT_SANDSTONE)
            .stairs().stairsFallback(Blocks.SANDSTONE_STAIRS)
            // .stoneWall().wallFallback(Blocks.SANDSTONE_WALL)
            // .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.BIRCH_BUTTON)
            // .stonePressurePlate().pressurePlateFallback(Blocks.BIRCH_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithWallAndButtonAndPressurePlate SMOOTH_SANDSTONE =
        BlockSet.builder("smooth_sandstone").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.SMOOTH_SANDSTONE).fullBlockModelProvider(block -> TexturedModel.CUBE.get(block).updateTextures(mappings -> {
                mappings.put(TextureSlot.WALL, TextureMapping.getBlockTexture(Blocks.SANDSTONE, "_top"));
            }))
            .stoneWall().wallFallback(Blocks.SANDSTONE_WALL)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.BIRCH_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.BIRCH_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate ROUGH_SANDSTONE =
        BlockSet.builder("rough_sandstone").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.SANDSTONE).fullBlockModelProvider(block -> TexturedModel.CUBE.get(block).updateTextures(mappings -> {
                mappings.put(TextureSlot.ALL, TextureMapping.getBlockTexture(Blocks.SANDSTONE, "_bottom"));
                mappings.put(TextureSlot.WALL, TextureMapping.getBlockTexture(Blocks.SANDSTONE, "_bottom"));
            })).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab()
            .stairs()
            .stoneWall()
            /*.stoneFence().fenceFallback(Blocks.BIRCH_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.BIRCH_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.BIRCH_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate SANDSTONE_BRICKS =
        BlockSet.builder("sandstone_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.SANDSTONE).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab()
            .stairs()
            .stoneWall()
            /*.stoneFence().fenceFallback(Blocks.BIRCH_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.BIRCH_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.BIRCH_PRESSURE_PLATE)
            .build();

    // Soil - Soul

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall SOUL_SANDSTONE =
        BlockSet.builder("soul_sandstone").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.SOUL_SOIL).fullBlockModelProvider(block -> TexturedModel.CUBE_BOTTOM_TOP.get(block).updateTextures(mappings -> {
                mappings.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block));
                mappings.put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(block));
                mappings.put(TextureSlot.WALL, TextureMapping.getBlockTexture(block));
            })).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .nonVolatileShapeTag(BlockItemTags.SOUL_FIRE_BASE_BLOCKS).nonVolatileShapeTag(BlockTags.SOUL_SPEED_BLOCKS)
            .slab().slabFallback(Blocks.DARK_OAK_SLAB)
            .stairs().stairsFallback(Blocks.DARK_OAK_STAIRS)
            .stoneWall().wallFallback(Blocks.MUD_BRICK_WALL)
            /*.stoneFence().fenceFallback(Blocks.DARK_OAK_FENCE)*/
            // .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.DARK_OAK_BUTTON)
            // .stonePressurePlate().pressurePlateFallback(Blocks.DARK_OAK_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairs CUT_SOUL_SANDSTONE =
        BlockSet.builder("cut_soul_sandstone").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.SOUL_SOIL).fullBlockModelProvider(block -> TexturedModel.COLUMN.get(block).updateTextures(mappings -> {
                mappings.put(TextureSlot.END, TextureMapping.getBlockTexture(SOUL_SANDSTONE.fullBlockNMS(), "_top"));
                mappings.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block));
                mappings.put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(block));
                mappings.put(TextureSlot.WALL, TextureMapping.getBlockTexture(block));
            })).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .nonVolatileShapeTag(BlockItemTags.SOUL_FIRE_BASE_BLOCKS).nonVolatileShapeTag(BlockTags.SOUL_SPEED_BLOCKS)
            .slab().slabFallback(Blocks.DARK_OAK_SLAB)
            .stairs().stairsFallback(Blocks.DARK_OAK_STAIRS)
            // .stoneWall().wallFallback(Blocks.MUD_BRICK_WALL)
            /*.stoneFence().fenceFallback(Blocks.DARK_OAK_FENCE)*/
            // .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.DARK_OAK_BUTTON)
            // .stonePressurePlate().pressurePlateFallback(Blocks.DARK_OAK_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate SMOOTH_SOUL_SANDSTONE =
        BlockSet.builder("smooth_soul_sandstone").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.SOUL_SOIL).fullBlockModelProvider(block -> TexturedModel.CUBE.get(block).updateTextures(mappings -> {
                mappings.put(TextureSlot.ALL, TextureMapping.getBlockTexture(SOUL_SANDSTONE.fullBlockNMS(), "_top"));
                mappings.put(TextureSlot.WALL, TextureMapping.getBlockTexture(SOUL_SANDSTONE.fullBlockNMS(), "_top"));
            })).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .nonVolatileShapeTag(BlockItemTags.SOUL_FIRE_BASE_BLOCKS).nonVolatileShapeTag(BlockTags.SOUL_SPEED_BLOCKS)
            .slab().slabFallback(Blocks.DARK_OAK_SLAB)
            .stairs().stairsFallback(Blocks.DARK_OAK_STAIRS)
            .stoneWall().wallFallback(Blocks.MUD_BRICK_WALL)
            /*.stoneFence().fenceFallback(Blocks.DARK_OAK_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.DARK_OAK_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.DARK_OAK_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate ROUGH_SOUL_SANDSTONE =
        BlockSet.builder("rough_soul_sandstone").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.SOUL_SOIL).fullBlockModelProvider(block -> TexturedModel.CUBE.get(block).updateTextures(mappings -> {
                mappings.put(TextureSlot.ALL, TextureMapping.getBlockTexture(SOUL_SANDSTONE.fullBlockNMS(), "_bottom"));
                mappings.put(TextureSlot.WALL, TextureMapping.getBlockTexture(SOUL_SANDSTONE.fullBlockNMS(), "_bottom"));
            })).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .nonVolatileShapeTag(BlockItemTags.SOUL_FIRE_BASE_BLOCKS).nonVolatileShapeTag(BlockTags.SOUL_SPEED_BLOCKS)
            .slab().slabFallback(Blocks.DARK_OAK_SLAB)
            .stairs().stairsFallback(Blocks.DARK_OAK_STAIRS)
            .stoneWall().wallFallback(Blocks.MUD_BRICK_WALL)
            /*.stoneFence().fenceFallback(Blocks.DARK_OAK_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.DARK_OAK_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.DARK_OAK_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate SOUL_SANDSTONE_BRICKS =
        BlockSet.builder("soul_sandstone_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.SOUL_SOIL).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .nonVolatileShapeTag(BlockItemTags.SOUL_FIRE_BASE_BLOCKS).nonVolatileShapeTag(BlockTags.SOUL_SPEED_BLOCKS)
            .slab().slabFallback(Blocks.DARK_OAK_SLAB)
            .stairs().stairsFallback(Blocks.DARK_OAK_STAIRS)
            .stoneWall().wallFallback(Blocks.MUD_BRICK_WALL)
            /*.stoneFence().fenceFallback(Blocks.DARK_OAK_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.DARK_OAK_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.DARK_OAK_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType CHISELED_SOUL_SANDSTONE = blockType("chiseled_soul_sandstone")
        .fullBlock(Blocks.SOUL_SOIL)
        .dataGenerateModel((factory, block) ->
            factory.addBlockCubeColumn(
                block,
                block.builtInRegistryHolder().key().identifier().withPrefix("block/"),
                SOUL_SANDSTONE.fullBlockType().identifier.withSuffix("_top").withPrefix("block/")
            )
        ).mineableWithPickaxe()
        .dataGenerateItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
        .dataGenerateTag(BlockItemTags.SOUL_FIRE_BASE_BLOCKS)
        .dataGenerateTag(BlockTags.SOUL_SPEED_BLOCKS);

    // Stone / Andesite

    private static BlockSet.WithFullBlockAndSlabAndStairs stonePillarSet(
        String fullBlockPath,
        Block vanilla,
        Block slabFallback,
        Block stairsFallback
    ) {
        return BlockSet.builder(fullBlockPath).mineableWithPickaxe()
            .fullBlock().vanilla(vanilla).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .fullBlockBlockBuild(builder -> builder.factoryNMS(RotatedPillarBlock::new))
            .fullBlockConstructorForDataGeneration(RotatedPillarBlock::new)
            .fullBlockModelProvider(block -> TexturedModel.COLUMN.get(block).updateTextures(mappings -> {
                mappings.put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top"));
                mappings.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block));
            }))
            .fullBlockModelCustomGenerator(PlatformDependent.BlockModelGenerators::createColumnGenerator)
            .slab().slabFallback(slabFallback)
            .stairs().stairsFallback(stairsFallback)
            .build();
    }

    public static final BlockSet.WithButtonAndPressurePlate ANDESITE =
        BlockSet.builder("andesite").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.ANDESITE)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithWallAndButtonAndPressurePlate POLISHED_ANDESITE =
        BlockSet.builder("polished_andesite").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.POLISHED_ANDESITE)
            .stoneWall().wallFallback(Blocks.ANDESITE_WALL)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate ANDESITE_BRICKS =
        BlockSet.builder("andesite_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.POLISHED_ANDESITE).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab()
            .stairs()
            .stoneWall().wallFallback(Blocks.ANDESITE_WALL)
            /*.stoneFence().fenceFallback(Blocks.POPLAR_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate ANDESITE_SMALL_BRICKS =
        BlockSet.builder("andesite_small_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.POLISHED_ANDESITE).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab()
            .stairs()
            .stoneWall().wallFallback(Blocks.ANDESITE_WALL)
            /*.stoneFence().fenceFallback(Blocks.POPLAR_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType CHISELED_ANDESITE_BRICKS = blockType("chiseled_andesite_bricks")
        .fullBlock(Blocks.POLISHED_ANDESITE)
        .mineableWithPickaxe()
        .dataGenerateItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY);

    public static final BlockSet.WithFullBlockAndSlabAndStairs ANDESITE_PILLAR = stonePillarSet(
        "andesite_pillar",
        Blocks.POLISHED_ANDESITE,
        Blocks.POLISHED_ANDESITE_SLAB,
        Blocks.POLISHED_ANDESITE_STAIRS
    );

    // Stone / Blackstone

    public static final BlockSet.WithButtonAndPressurePlate BLACKSTONE =
        BlockSet.builder("blackstone").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.BLACKSTONE)
            .stoneButton(BlockSetType.POLISHED_BLACKSTONE).buttonFallback(Blocks.POLISHED_BLACKSTONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithButtonAndPressurePlate POLISHED_BLACKSTONE_BRICKS =
        BlockSet.builder("polished_blackstone_bricks").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.POLISHED_BLACKSTONE_BRICKS)
            .stoneButton(BlockSetType.POLISHED_BLACKSTONE).buttonFallback(Blocks.POLISHED_BLACKSTONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE)
            .build();

    // Stone / Calcite

    public static final BlockSet.WithSlabAndStairsAndWallAndButtonAndPressurePlate CALCITE =
        BlockSet.builder("calcite").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.CALCITE)
            .slab().slabFallback(Blocks.WOOL_SLAB.white())
            .stairs().stairsFallback(Blocks.WOOL_STAIRS.white())
            .stoneWall().wallFallback(Blocks.DIORITE_WALL)
            /*.stoneFence().fenceFallback(Blocks.PALE_OAK_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.PALE_OAK_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.PALE_OAK_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate POLISHED_CALCITE =
        BlockSet.builder("polished_calcite").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.CALCITE).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab().slabFallback(Blocks.POLISHED_DIORITE_SLAB)
            .stairs().stairsFallback(Blocks.POLISHED_DIORITE_STAIRS)
            .stoneWall().wallFallback(Blocks.DIORITE_WALL)
            /*.stoneFence().fenceFallback(Blocks.PALE_OAK_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.PALE_OAK_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.PALE_OAK_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate CALCITE_BRICKS =
        BlockSet.builder("calcite_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.CALCITE).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab().slabFallback(Blocks.POLISHED_DIORITE_SLAB)
            .stairs().stairsFallback(Blocks.POLISHED_DIORITE_STAIRS)
            .stoneWall().wallFallback(Blocks.DIORITE_WALL)
            /*.stoneFence().fenceFallback(Blocks.PALE_OAK_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.PALE_OAK_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.PALE_OAK_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType CHISELED_CALCITE_BRICKS = blockType("chiseled_calcite_bricks")
        .fullBlock(Blocks.CALCITE)
        .mineableWithPickaxe()
        .dataGenerateItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY);

    public static final BlockSet.WithFullBlockAndSlabAndStairs CALCITE_PILLAR = stonePillarSet(
        "calcite_pillar",
        Blocks.CALCITE,
        Blocks.POLISHED_DIORITE_SLAB,
        Blocks.POLISHED_DIORITE_STAIRS
    );

    // Stone / Cinnabar

    public static final BlockSet.WithButtonAndPressurePlate CINNABAR =
        BlockSet.builder("cinnabar").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.CINNABAR)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.MANGROVE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.MANGROVE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithButtonAndPressurePlate POLISHED_CINNABAR =
        BlockSet.builder("polished_cinnabar").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.POLISHED_CINNABAR)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.MANGROVE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.MANGROVE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithButtonAndPressurePlate CINNABAR_BRICKS =
        BlockSet.builder("cinnabar_bricks").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.CINNABAR_BRICKS)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.MANGROVE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.MANGROVE_PRESSURE_PLATE)
            .build();

    // Stone / Clay bricks

    public static final BlockSet.WithButtonAndPressurePlate BRICKS =
        BlockSet.builder("bricks").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.BRICKS)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.MANGROVE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.MANGROVE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate RECESSED_BRICKS =
        BlockSet.builder("recessed_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.BRICKS).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab()
            .stairs()
            .stoneWall()
            /*.stoneFence().fenceFallback(Blocks.MANGROVE_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.MANGROVE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.MANGROVE_PRESSURE_PLATE)
            .build();

    // Stone / Cobblestone

    public static final BlockSet.WithButtonAndPressurePlate COBBLESTONE =
        BlockSet.builder("cobblestone").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.COBBLESTONE)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithButtonAndPressurePlate MOSSY_COBBLESTONE =
        BlockSet.builder("mossy_cobblestone").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.MOSSY_COBBLESTONE)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    // Stone / Concrete

    private static Block wallFallbackForSmoothDyeColor(DyeColor dyeColor) {
        return switch (dyeColor) {
            case BLACK -> Blocks.BLACKSTONE_WALL;
            case BLUE -> Blocks.PRISMARINE_WALL;
            case BROWN -> Blocks.MUD_BRICK_WALL;
            case CYAN -> Blocks.PRISMARINE_WALL;
            case GRAY -> Blocks.POLISHED_TUFF_WALL;
            case GREEN -> Blocks.MOSSY_COBBLESTONE_WALL;
            case LIGHT_BLUE -> Blocks.PRISMARINE_WALL;
            case LIGHT_GRAY -> Blocks.ANDESITE_WALL;
            case LIME -> Blocks.MOSSY_COBBLESTONE_WALL;
            case MAGENTA -> Blocks.CINNABAR_WALL;
            case ORANGE -> Blocks.RED_SANDSTONE_WALL;
            case PINK -> Blocks.CINNABAR_WALL;
            case PURPLE -> Blocks.CINNABAR_WALL;
            case RED -> Blocks.POLISHED_CINNABAR_WALL;
            case WHITE -> Blocks.DIORITE_WALL;
            case YELLOW -> Blocks.POLISHED_SULFUR_WALL;
        };
    }

    private static Block buttonFallbackForSmoothDyeColor(DyeColor dyeColor) {
        return switch (dyeColor) {
            case BLACK -> Blocks.POLISHED_BLACKSTONE_BUTTON;
            case BLUE -> Blocks.WARPED_BUTTON;
            case BROWN -> Blocks.SPRUCE_BUTTON;
            case CYAN -> Blocks.WARPED_BUTTON;
            case GRAY -> Blocks.STONE_BUTTON;
            case GREEN -> Blocks.WARPED_BUTTON;
            case LIGHT_BLUE -> Blocks.WARPED_BUTTON;
            case LIGHT_GRAY -> Blocks.STONE_BUTTON;
            case LIME -> Blocks.WARPED_BUTTON;
            case MAGENTA -> Blocks.CHERRY_BUTTON;
            case ORANGE -> Blocks.ACACIA_BUTTON;
            case PINK -> Blocks.CHERRY_BUTTON;
            case PURPLE -> Blocks.CRIMSON_BUTTON;
            case RED -> Blocks.MANGROVE_BUTTON;
            case WHITE -> Blocks.PALE_OAK_BUTTON;
            case YELLOW -> Blocks.BAMBOO_BUTTON;
        };
    }

    private static Block pressurePlateFallbackForSmoothDyeColor(DyeColor dyeColor) {
        return switch (dyeColor) {
            case BLACK -> Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE;
            case BLUE -> Blocks.WARPED_PRESSURE_PLATE;
            case BROWN -> Blocks.SPRUCE_PRESSURE_PLATE;
            case CYAN -> Blocks.WARPED_PRESSURE_PLATE;
            case GRAY -> Blocks.STONE_PRESSURE_PLATE;
            case GREEN -> Blocks.WARPED_PRESSURE_PLATE;
            case LIGHT_BLUE -> Blocks.WARPED_PRESSURE_PLATE;
            case LIGHT_GRAY -> Blocks.STONE_PRESSURE_PLATE;
            case LIME -> Blocks.WARPED_PRESSURE_PLATE;
            case MAGENTA -> Blocks.CHERRY_PRESSURE_PLATE;
            case ORANGE -> Blocks.ACACIA_PRESSURE_PLATE;
            case PINK -> Blocks.CHERRY_PRESSURE_PLATE;
            case PURPLE -> Blocks.CRIMSON_PRESSURE_PLATE;
            case RED -> Blocks.MANGROVE_PRESSURE_PLATE;
            case WHITE -> Blocks.PALE_OAK_PRESSURE_PLATE;
            case YELLOW -> Blocks.BAMBOO_PRESSURE_PLATE;
        };
    }

    public static final Family.DyeColor<BlockSet.WithWallAndButtonAndPressurePlate> CONCRETE =
        new Family.DyeColor<>(dyeColor ->
            BlockSet.builder(dyeColor.getSerializedName() + "_concrete").mineableWithPickaxe()
                .fullBlockOnlyForDataGeneration().vanilla(Blocks.CONCRETE.pick(dyeColor))
                .stoneWall().wallFallback(wallFallbackForSmoothDyeColor(dyeColor)).wallTag(SuCraftBlockItemTags.CONCRETE_WALLS)
                .stoneButton().blockSetType(BlockSetType.STONE).buttonFallback(buttonFallbackForSmoothDyeColor(dyeColor)).buttonTag(SuCraftBlockItemTags.CONCRETE_BUTTONS)
                .stonePressurePlate().pressurePlateFallback(pressurePlateFallbackForSmoothDyeColor(dyeColor)).pressurePlateTag(SuCraftBlockItemTags.CONCRETE_PRESSURE_PLATES)
                .build()
        );

    // Stone / Deepslate

    public static final BlockSet.WithButtonAndPressurePlate COBBLED_DEEPSLATE =
        BlockSet.builder("cobbled_deepslate").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.COBBLED_DEEPSLATE)
            .stoneButton(BlockSetType.POLISHED_BLACKSTONE).buttonFallback(Blocks.POLISHED_BLACKSTONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithSlabAndStairsAndWallAndButtonAndPressurePlate DEEPSLATE =
        BlockSet.builder("deepslate").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.DEEPSLATE)
            .slab().slabFallback(Blocks.COBBLED_DEEPSLATE_SLAB)
            .stairs().stairsFallback(Blocks.COBBLED_DEEPSLATE_STAIRS)
            .stoneWall().wallFallback(Blocks.COBBLED_DEEPSLATE_WALL)
            /*.stoneFence().fenceFallback(Blocks.NETHER_BRICK_FENCE)*/
            .stoneButton(BlockSetType.POLISHED_BLACKSTONE).buttonFallback(Blocks.POLISHED_BLACKSTONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithButtonAndPressurePlate POLISHED_DEEPSLATE =
        BlockSet.builder("polished_deepslate").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.POLISHED_DEEPSLATE)
            .stoneButton(BlockSetType.POLISHED_BLACKSTONE).buttonFallback(Blocks.POLISHED_BLACKSTONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithButtonAndPressurePlate DEEPSLATE_BRICKS =
        BlockSet.builder("deepslate_bricks").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.DEEPSLATE_BRICKS)
            .stoneButton(BlockSetType.POLISHED_BLACKSTONE).buttonFallback(Blocks.POLISHED_BLACKSTONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithButtonAndPressurePlate DEEPSLATE_TILES =
        BlockSet.builder("deepslate_tiles").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.DEEPSLATE_TILES)
            .stoneButton(BlockSetType.POLISHED_BLACKSTONE).buttonFallback(Blocks.POLISHED_BLACKSTONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType WARDEN_CHISELED_DEEPSLATE = blockType("warden_chiseled_deepslate")
        .fullBlock(Blocks.CHISELED_DEEPSLATE)
        .mineableWithPickaxe()
        .dataGenerateItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY);

    // Stone / Diorite

    public static final BlockSet.WithButtonAndPressurePlate DIORITE =
        BlockSet.builder("diorite").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.DIORITE)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.PALE_OAK_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.PALE_OAK_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithWallAndButtonAndPressurePlate POLISHED_DIORITE =
        BlockSet.builder("polished_diorite").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.POLISHED_DIORITE)
            .stoneWall().wallFallback(Blocks.DIORITE_WALL)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.PALE_OAK_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.PALE_OAK_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate DIORITE_BRICKS =
        BlockSet.builder("diorite_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.POLISHED_DIORITE).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab()
            .stairs()
            .stoneWall().wallFallback(Blocks.DIORITE_WALL)
            /*.stoneFence().fenceFallback(Blocks.PALE_OAK_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.PALE_OAK_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.PALE_OAK_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate DIORITE_SMALL_BRICKS =
        BlockSet.builder("diorite_small_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.POLISHED_DIORITE).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab()
            .stairs()
            .stoneWall().wallFallback(Blocks.DIORITE_WALL)
            /*.stoneFence().fenceFallback(Blocks.PALE_OAK_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.PALE_OAK_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.PALE_OAK_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType CHISELED_DIORITE_BRICKS = blockType("chiseled_diorite_bricks")
        .fullBlock(Blocks.POLISHED_DIORITE)
        .mineableWithPickaxe()
        .dataGenerateItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY);

    public static final BlockSet.WithFullBlockAndSlabAndStairs DIORITE_PILLAR = stonePillarSet(
        "diorite_pillar",
        Blocks.POLISHED_DIORITE,
        Blocks.POLISHED_DIORITE_SLAB,
        Blocks.POLISHED_DIORITE_STAIRS
    );

    // Stone / Dripstone

    public static final BlockSet.WithSlabAndStairsAndWallAndButtonAndPressurePlate DRIPSTONE =
        BlockSet.builder("dripstone").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.DRIPSTONE_BLOCK)
            .slab().slabFallback(Blocks.GRANITE_SLAB)
            .stairs().stairsFallback(Blocks.GRANITE_STAIRS)
            .stoneWall().wallFallback(Blocks.GRANITE_WALL)
            /*.stoneFence().fenceFallback(Blocks.JUNGLE_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.JUNGLE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.JUNGLE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate POLISHED_DRIPSTONE =
        BlockSet.builder("polished_dripstone").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.DRIPSTONE_BLOCK).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab().slabFallback(Blocks.POLISHED_GRANITE_SLAB)
            .stairs().stairsFallback(Blocks.POLISHED_GRANITE_STAIRS)
            .stoneWall().wallFallback(Blocks.GRANITE_WALL)
            /*.stoneFence().fenceFallback(Blocks.JUNGLE_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.JUNGLE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.JUNGLE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate DRIPSTONE_BRICKS =
        BlockSet.builder("dripstone_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.DRIPSTONE_BLOCK).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab().slabFallback(Blocks.POLISHED_GRANITE_SLAB)
            .stairs().stairsFallback(Blocks.POLISHED_GRANITE_STAIRS)
            .stoneWall().wallFallback(Blocks.GRANITE_WALL)
            /*.stoneFence().fenceFallback(Blocks.JUNGLE_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.JUNGLE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.JUNGLE_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType CHISELED_DRIPSTONE_BRICKS = blockType("chiseled_dripstone_bricks")
        .fullBlock(Blocks.DRIPSTONE_BLOCK)
        .mineableWithPickaxe()
        .dataGenerateItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY);

    public static final BlockSet.WithFullBlockAndSlabAndStairs DRIPSTONE_PILLAR = stonePillarSet(
        "dripstone_pillar",
        Blocks.DRIPSTONE_BLOCK,
        Blocks.POLISHED_GRANITE_SLAB,
        Blocks.POLISHED_GRANITE_STAIRS
    );

    // Stone / End stone

    public static final BlockSet.WithSlabAndStairsAndWallAndButtonAndPressurePlate END_STONE =
        BlockSet.builder("end_stone").mineableWithPickaxe().tag(BlockTags.DRAGON_IMMUNE)
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.END_STONE)
            .nonVolatileShapeTag(BlockTags.SUPPORTS_CHORUS_FLOWER).nonVolatileShapeTag(BlockTags.SUPPORTS_CHORUS_PLANT)
            .slab().slabFallback(Blocks.END_STONE_BRICK_SLAB)
            .stairs().stairsFallback(Blocks.END_STONE_BRICK_STAIRS)
            .stoneWall().wallFallback(Blocks.END_STONE_BRICK_WALL)
            /*.stoneFence().fenceFallback(Blocks.BIRCH_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.BIRCH_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.BIRCH_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithButtonAndPressurePlate END_STONE_BRICKS =
        BlockSet.builder("end_stone_bricks").mineableWithPickaxe().tag(BlockTags.DRAGON_IMMUNE)
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.END_STONE_BRICKS)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.BIRCH_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.BIRCH_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType CHISELED_END_STONE_BRICKS = blockType("chiseled_end_stone_bricks")
        .fullBlock(Blocks.END_STONE_BRICKS)
        .mineableWithPickaxe()
        .dataGenerateTag(BlockTags.DRAGON_IMMUNE)
        .dataGenerateItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY);

    public static final SuCraftBlockType DRACONIC_END_STONE = blockType("draconic_end_stone")
        .fullBlock(Blocks.END_STONE_BRICKS)
        .mineableWithPickaxe()
        .dataGenerateTag(BlockTags.DRAGON_IMMUNE)
        .dataGenerateItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY);

    // Stone / Granite

    public static final BlockSet.WithButtonAndPressurePlate GRANITE =
        BlockSet.builder("granite").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.GRANITE)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.JUNGLE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.JUNGLE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithWallAndButtonAndPressurePlate POLISHED_GRANITE =
        BlockSet.builder("polished_granite").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.POLISHED_GRANITE)
            .stoneWall().wallFallback(Blocks.GRANITE_WALL)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.JUNGLE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.JUNGLE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate GRANITE_BRICKS =
        BlockSet.builder("granite_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.POLISHED_GRANITE).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab()
            .stairs()
            .stoneWall()
            /*.stoneFence().fenceFallback(Blocks.JUNGLE_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.JUNGLE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.JUNGLE_PRESSURE_PLATE)
            .wallFallback(Blocks.GRANITE_WALL)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate GRANITE_SMALL_BRICKS =
        BlockSet.builder("granite_small_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.POLISHED_GRANITE).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab()
            .stairs()
            .stoneWall()
            /*.stoneFence().fenceFallback(Blocks.JUNGLE_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.JUNGLE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.JUNGLE_PRESSURE_PLATE)
            .wallFallback(Blocks.GRANITE_WALL)
            .build();

    public static final SuCraftBlockType CHISELED_GRANITE_BRICKS = blockType("chiseled_granite_bricks")
        .fullBlock(Blocks.POLISHED_GRANITE)
        .mineableWithPickaxe()
        .dataGenerateItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY);

    public static final BlockSet.WithFullBlockAndSlabAndStairs GRANITE_PILLAR = stonePillarSet(
        "granite_pillar",
        Blocks.POLISHED_GRANITE,
        Blocks.POLISHED_GRANITE_SLAB,
        Blocks.POLISHED_GRANITE_STAIRS
    );

    // Stone / Nether bricks

    public static final BlockSet.WithButtonAndPressurePlate NETHER_BRICKS =
        BlockSet.builder("nether_bricks").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.NETHER_BRICKS)
            .stoneButton(BlockSetType.POLISHED_BLACKSTONE).buttonFallback(Blocks.MANGROVE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.MANGROVE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFenceAndButtonAndPressurePlate RED_NETHER_BRICKS =
        BlockSet.builder("red_nether_bricks").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.RED_NETHER_BRICKS)
            .stoneFence().fenceFallback(Blocks.MANGROVE_FENCE)
            .stoneButton(BlockSetType.POLISHED_BLACKSTONE).buttonFallback(Blocks.MANGROVE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.MANGROVE_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType CHISELED_RED_NETHER_BRICKS = blockType("chiseled_red_nether_bricks")
        .fullBlock(Blocks.RED_NETHER_BRICKS)
        .mineableWithPickaxe()
        .dataGenerateItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY);

    // Stone / Prismarine

    public static final BlockSet.WithButtonAndPressurePlate PRISMARINE =
        BlockSet.builder("prismarine").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.PRISMARINE)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.WARPED_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.WARPED_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithWallAndButtonAndPressurePlate DARK_PRISMARINE =
        BlockSet.builder("dark_prismarine").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.DARK_PRISMARINE)
            .stoneWall().wallFallback(Blocks.PRISMARINE_WALL)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.WARPED_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.WARPED_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithWallAndButtonAndPressurePlate PRISMARINE_BRICKS =
        BlockSet.builder("prismarine_bricks").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.PRISMARINE_BRICKS)
            .stoneWall().wallFallback(Blocks.PRISMARINE_WALL)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.WARPED_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.WARPED_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate ENGRAVED_PRISMARINE_BRICKS =
        BlockSet.builder("engraved_prismarine_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.PRISMARINE_BRICKS).fullBlockTag(BlockTags.CONDUIT_EFFECT_BLOCK).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab()
            .stairs()
            .stoneWall().wallFallback(Blocks.PRISMARINE_WALL)
            /*.stoneFence().fenceFallback(Blocks.WARPED_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.WARPED_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.WARPED_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate PRISMARINE_DIAGONAL_TILES =
        BlockSet.builder("prismarine_diagonal_tiles").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.PRISMARINE_BRICKS).fullBlockTag(BlockTags.CONDUIT_EFFECT_BLOCK).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab()
            .stairs()
            .stoneWall().wallFallback(Blocks.PRISMARINE_WALL)
            /*.stoneFence().fenceFallback(Blocks.WARPED_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.WARPED_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.WARPED_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate PRISMARINE_SCALES =
        BlockSet.builder("prismarine_scales").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.PRISMARINE_BRICKS).fullBlockTag(BlockTags.CONDUIT_EFFECT_BLOCK).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab()
            .stairs()
            .stoneWall().wallFallback(Blocks.PRISMARINE_WALL)
            /*.stoneFence().fenceFallback(Blocks.WARPED_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.WARPED_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.WARPED_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate PRISMARINE_STONE_BRICKS =
        BlockSet.builder("prismarine_stone_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.PRISMARINE_BRICKS).fullBlockTag(BlockTags.CONDUIT_EFFECT_BLOCK).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab()
            .stairs()
            .stoneWall().wallFallback(Blocks.PRISMARINE_WALL)
            /*.stoneFence().fenceFallback(Blocks.WARPED_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.WARPED_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.WARPED_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType CHISELED_PRISMARINE_BRICKS = blockType("chiseled_prismarine_bricks")
        .fullBlock(Blocks.PRISMARINE_BRICKS)
        .mineableWithPickaxe()
        .dataGenerateTag(BlockTags.CONDUIT_EFFECT_BLOCK)
        .dataGenerateItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY);

    public static final SuCraftBlockType ELDER_CHISELED_PRISMARINE_BRICKS = blockType("elder_chiseled_prismarine_bricks")
        .fullBlock(Blocks.PRISMARINE_BRICKS)
        .mineableWithPickaxe()
        .dataGenerateTag(BlockTags.CONDUIT_EFFECT_BLOCK)
        .dataGenerateItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY);

    // Stone / Purpur

    public static final BlockSet.WithWallAndButtonAndPressurePlate PURPUR_BLOCK =
        BlockSet.builder("purpur_block").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.PURPUR_BLOCK)
            .stoneWall().wallFallback(Blocks.NETHER_BRICK_WALL)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.CRIMSON_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.CRIMSON_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithSlabAndStairs PURPUR_PILLAR =
        BlockSet.builder("purpur_pillar").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.PURPUR_PILLAR).fullBlockModelProvider(block -> TexturedModel.COLUMN.get(block).updateTextures(mappings -> {
                mappings.put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.PURPUR_PILLAR, "_top"));
                mappings.put(TextureSlot.END, TextureMapping.getBlockTexture(Blocks.PURPUR_PILLAR, "_top"));
                mappings.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.PURPUR_PILLAR, "_side"));
                mappings.put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.PURPUR_PILLAR, "_top"));
            }))
            .slab().slabFallback(Blocks.PURPUR_SLAB)
            .stairs().stairsFallback(Blocks.PURPUR_STAIRS)
            // .stoneWall().wallFallback(Blocks.NETHER_BRICK_WALL)
            // .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.CRIMSON_BUTTON)
            // .stonePressurePlate().pressurePlateFallback(Blocks.CRIMSON_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType SHULKER_CHISELED_PURPUR = blockType("shulker_chiseled_purpur")
        .fullBlock(Blocks.PURPUR_BLOCK)
        .mineableWithPickaxe()
        .dataGenerateItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY);

    public static final SuCraftBlockType ENDER_CHISELED_PURPUR = blockType("ender_chiseled_purpur")
        .fullBlock(Blocks.PURPUR_BLOCK)
        .mineableWithPickaxe()
        .dataGenerateItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY);

    // Stone / Quartz

    public static final BlockSet.WithWall QUARTZ_BLOCK =
        BlockSet.builder("quartz").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.QUARTZ_BLOCK).fullBlockModelProvider(block -> TexturedModel.COLUMN.get(block).updateTextures(mappings -> {
                // mappings.put(TextureSlot.END, TextureMapping.getBlockTexture(Blocks.QUARTZ_BLOCK, "_top"));
                // mappings.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.QUARTZ_BLOCK, "_side"));
                // mappings.put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(Blocks.QUARTZ_BLOCK, "_top"));
                mappings.put(TextureSlot.WALL, TextureMapping.getBlockTexture(Blocks.QUARTZ_BLOCK, "_side"));
            }))
            .stoneWall().wallFallback(Blocks.DIORITE_WALL)
            // .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.PALE_OAK_BUTTON)
            // .stonePressurePlate().pressurePlateFallback(Blocks.PALE_OAK_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithSlabAndStairsAndWallAndButtonAndPressurePlate QUARTZ_BRICKS =
        BlockSet.builder("quartz_bricks").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.QUARTZ_BRICKS)
            .slab().slabFallback(Blocks.QUARTZ_SLAB)
            .stairs().stairsFallback(Blocks.QUARTZ_STAIRS)
            .stoneWall().wallFallback(Blocks.DIORITE_WALL)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.PALE_OAK_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.PALE_OAK_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithSlabAndStairs QUARTZ_PILLAR =
        BlockSet.builder("quartz_pillar").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.QUARTZ_PILLAR).fullBlockModelProvider(block -> TexturedModel.COLUMN.get(block).updateTextures(mappings -> {
                mappings.put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.QUARTZ_PILLAR, "_top"));
                mappings.put(TextureSlot.END, TextureMapping.getBlockTexture(Blocks.QUARTZ_PILLAR, "_top"));
                mappings.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.QUARTZ_PILLAR, "_side"));
                mappings.put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.QUARTZ_PILLAR, "_top"));
            }))
            .slab().slabFallback(Blocks.QUARTZ_SLAB)
            .stairs().stairsFallback(Blocks.QUARTZ_STAIRS)
            // .stoneWall().wallFallback(Blocks.DIORITE_WALL)
            // .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.PALE_OAK_BUTTON)
            // .stonePressurePlate().pressurePlateFallback(Blocks.PALE_OAK_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithSlabAndStairsAndWall CHISELED_QUARTZ_BLOCK =
        BlockSet.builder("chiseled_quartz_block").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.CHISELED_QUARTZ_BLOCK)
            .slab().slabFallback(Blocks.QUARTZ_SLAB)
            .stairs().stairsFallback(Blocks.QUARTZ_STAIRS)
            .stoneWall().wallFallback(Blocks.DIORITE_WALL)
            // .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.PALE_OAK_BUTTON)
            // .stonePressurePlate().pressurePlateFallback(Blocks.PALE_OAK_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithWallAndButtonAndPressurePlate SMOOTH_QUARTZ =
        BlockSet.builder("smooth_quartz").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.SMOOTH_QUARTZ)
            .stoneWall().wallFallback(Blocks.DIORITE_WALL)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.PALE_OAK_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.PALE_OAK_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate QUARTZ_SPIRAL_MEANDER =
        BlockSet.builder("quartz_spiral_meander").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.CHISELED_QUARTZ_BLOCK).fullBlockModelProvider(block -> TexturedModel.CUBE.get(block).updateTextures(mappings -> {
                mappings.put(TextureSlot.ALL, TextureMapping.getBlockTexture(Blocks.CHISELED_QUARTZ_BLOCK, "_top"));
                mappings.put(TextureSlot.WALL, TextureMapping.getBlockTexture(Blocks.CHISELED_QUARTZ_BLOCK, "_top"));
            })).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab().slabFallback(Blocks.QUARTZ_SLAB)
            .stairs().stairsFallback(Blocks.QUARTZ_STAIRS)
            .stoneWall().wallFallback(Blocks.DIORITE_WALL)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.PALE_OAK_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.PALE_OAK_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType GHASTLY_CHISELED_QUARTZ_BLOCK = blockType("ghastly_chiseled_quartz_block")
        .fullBlock(Blocks.CHISELED_QUARTZ_BLOCK)
        .mineableWithPickaxe()
        .dataGenerateItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY);

    // Stone / Regular

    public static final BlockSet.WithWall STONE =
        BlockSet.builder("stone").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.STONE)
            .stoneWall().wallFallback(Blocks.ANDESITE_WALL)
            .build();

    public static final BlockSet.WithButtonAndPressurePlate STONE_BRICKS =
        BlockSet.builder("stone_bricks").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.STONE_BRICKS)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithSlabAndStairsAndWallAndButtonAndPressurePlate CRACKED_STONE_BRICKS =
        BlockSet.builder("cracked_stone_bricks").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.CRACKED_STONE_BRICKS)
            .slab().slabFallback(Blocks.STONE_BRICK_SLAB)
            .stairs().stairsFallback(Blocks.STONE_BRICK_STAIRS)
            .stoneWall().wallFallback(Blocks.STONE_BRICK_WALL)
            /*.stoneFence().fenceFallback(Blocks.POPLAR_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithButtonAndPressurePlate MOSSY_STONE_BRICKS =
        BlockSet.builder("mossy_stone_bricks").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.MOSSY_STONE_BRICKS)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithSlabAndStairsAndWallAndButtonAndPressurePlate CHISELED_STONE_BRICKS =
        BlockSet.builder("chiseled_stone_bricks").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.CHISELED_STONE_BRICKS)
            .slab().slabFallback(Blocks.STONE_BRICK_SLAB)
            .stairs().stairsFallback(Blocks.STONE_BRICK_STAIRS)
            .stoneWall().wallFallback(Blocks.STONE_BRICK_WALL)
            /*.stoneFence().fenceFallback(Blocks.POPLAR_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType SCULPTED_STONE_BRICKS = blockType("sculpted_stone_bricks")
        .fullBlock(Blocks.CHISELED_STONE_BRICKS)
        .mineableWithPickaxe()
        .dataGenerateItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY);

    private static SuCraftBlockType infested(
        String path,
        Block vanilla,
        Supplier<Block> hostBlock,
        Supplier<Block> hostBlockForDataGeneration
    ) {
        var type = blockType(path)
            .fullBlock(vanilla)
            .blockBuild(builder -> builder.factoryNMS(properties -> new InfestedBlock(hostBlock.get(), properties)));
        type.dataGenerateBlockLootTable(factory -> factory.add(provider -> provider.otherWhenSilkTouch(type.getNMS(), hostBlockForDataGeneration.get())));
        type.dataGenerateModel((factory, block) ->
            factory.addBlock(generators -> {
                Block host = hostBlockForDataGeneration.get();
                MultiVariant model = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(host));
                generators.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, model));
                generators.itemModelOutput.copy(host.asItem(), block.asItem());
            })
        );
        return type;
    }

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate DIRTY_STONE_BRICKS =
        BlockSet.builder("dirty_stone_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.STONE_BRICKS).fullBlockTag(BlockItemTags.STONE_BRICKS).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab()
            .stairs()
            .stoneWall()
            /*.stoneFence().fenceFallback(Blocks.POPLAR_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType INFESTED_DIRTY_STONE_BRICKS = infested(
        "infested_dirty_stone_bricks",
        Blocks.INFESTED_STONE_BRICKS,
        DIRTY_STONE_BRICKS::fullBlockNMS,
        DIRTY_STONE_BRICKS::fullBlockNMS
    ).mineableWithPickaxe()
        .dataGenerateTag(SuCraftBlockItemTags.INFESTED_STONE_BRICKS);

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate SNOWY_STONE_BRICKS =
        BlockSet.builder("snowy_stone_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.STONE_BRICKS).fullBlockTag(BlockItemTags.STONE_BRICKS).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab()
            .stairs()
            .stoneWall()
            /*.stoneFence().fenceFallback(Blocks.POPLAR_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType INFESTED_SNOWY_STONE_BRICKS = infested(
        "infested_snowy_stone_bricks",
        Blocks.INFESTED_STONE_BRICKS,
        SNOWY_STONE_BRICKS::fullBlockNMS,
        SNOWY_STONE_BRICKS::fullBlockNMS
    ).mineableWithPickaxe()
        .dataGenerateTag(SuCraftBlockItemTags.INFESTED_STONE_BRICKS);

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate MOSSY_CHISELED_STONE_BRICKS =
        BlockSet.builder("mossy_chiseled_stone_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.MOSSY_STONE_BRICKS).fallback(Blocks.CHISELED_STONE_BRICKS).fullBlockTag(BlockItemTags.STONE_BRICKS).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab().slabFallback(Blocks.MOSSY_STONE_BRICK_SLAB)
            .stairs().stairsFallback(Blocks.MOSSY_STONE_BRICK_STAIRS)
            .stoneWall().wallFallback(Blocks.MOSSY_STONE_BRICK_WALL)
            /*.stoneFence().fenceFallback(Blocks.POPLAR_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate MOSSY_CRACKED_STONE_BRICKS =
        BlockSet.builder("mossy_cracked_stone_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.MOSSY_STONE_BRICKS).fullBlockTag(BlockItemTags.STONE_BRICKS).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab()
            .stairs()
            .stoneWall()
            /*.stoneFence().fenceFallback(Blocks.POPLAR_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    // Stone / Sulfur

    public static final BlockSet.WithButtonAndPressurePlate SULFUR =
        BlockSet.builder("sulfur").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.SULFUR)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.BAMBOO_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.BAMBOO_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithButtonAndPressurePlate POLISHED_SULFUR =
        BlockSet.builder("polished_sulfur").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.POLISHED_SULFUR)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.BAMBOO_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.BAMBOO_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithButtonAndPressurePlate SULFUR_BRICKS =
        BlockSet.builder("sulfur_bricks").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.SULFUR_BRICKS)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.BAMBOO_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.BAMBOO_PRESSURE_PLATE)
            .build();

    // Stone / Terracotta

    public static final BlockSet.WithSlabAndStairsAndWallAndButtonAndPressurePlate TERRACOTTA =
        BlockSet.builder("terracotta").mineableWithPickaxe().nonVolatileShapeTag(BlockTags.AZALEA_GROWS_ON).nonVolatileShapeTag(BlockTags.SUPPORTS_DRY_VEGETATION).nonVolatileShapeTag(BlockTags.TRIGGERS_AMBIENT_DESERT_DRY_VEGETATION_BLOCK_SOUNDS)
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.TERRACOTTA)
            .slab().slabFallback(Blocks.SMOOTH_RED_SANDSTONE_SLAB).slabTag(SuCraftBlockItemTags.TERRACOTTA_SLABS)
            .stairs().stairsFallback(Blocks.SMOOTH_RED_SANDSTONE_STAIRS).stairsTag(SuCraftBlockItemTags.TERRACOTTA_STAIRS)
            .stoneWall().wallFallback(Blocks.RED_SANDSTONE_WALL).wallTag(SuCraftBlockItemTags.TERRACOTTA_WALLS)
            .stoneButton().buttonFallback(Blocks.JUNGLE_BUTTON).buttonTag(SuCraftBlockItemTags.TERRACOTTA_BUTTONS).blockSetType(BlockSetType.STONE)
            .stonePressurePlate().pressurePlateFallback(Blocks.JUNGLE_PRESSURE_PLATE).pressurePlateTag(SuCraftBlockItemTags.TERRACOTTA_PRESSURE_PLATES)
            .build();

    public static final Family.DyeColor<BlockSet.WithSlabAndStairsAndWallAndButtonAndPressurePlate> DYED_TERRACOTTA =
        new Family.DyeColor<>(dyeColor ->
            BlockSet.builder(dyeColor.getSerializedName() + "_terracotta").mineableWithPickaxe().nonVolatileShapeTag(BlockTags.AZALEA_GROWS_ON).nonVolatileShapeTag(BlockTags.SUPPORTS_DRY_VEGETATION).nonVolatileShapeTag(BlockTags.TRIGGERS_AMBIENT_DESERT_DRY_VEGETATION_BLOCK_SOUNDS)
                .fullBlockOnlyForDataGeneration().vanilla(Blocks.DYED_TERRACOTTA.pick(dyeColor))
                .slab().slabFallback(switch (dyeColor) {
                    case BLACK -> Blocks.CONCRETE_SLAB.black();
                    case BLUE -> Blocks.CONCRETE_SLAB.blue();
                    case BROWN -> Blocks.CONCRETE_SLAB.brown();
                    case CYAN -> Blocks.POLISHED_TUFF_SLAB;
                    case GRAY -> Blocks.CONCRETE_SLAB.brown();
                    case GREEN -> Blocks.CONCRETE_SLAB.green();
                    case LIGHT_BLUE -> Blocks.CONCRETE_SLAB.lightBlue();
                    case LIGHT_GRAY -> Blocks.CONCRETE_SLAB.brown();
                    case LIME -> Blocks.CONCRETE_SLAB.lime();
                    case MAGENTA -> Blocks.CONCRETE_SLAB.pink();
                    case ORANGE -> Blocks.SMOOTH_RED_SANDSTONE_SLAB;
                    case PINK -> Blocks.SMOOTH_RED_SANDSTONE_SLAB;
                    case PURPLE -> Blocks.CONCRETE_SLAB.purple();
                    case RED -> Blocks.POLISHED_CINNABAR_SLAB;
                    case WHITE -> Blocks.SMOOTH_QUARTZ_SLAB;
                    case YELLOW -> Blocks.CONCRETE_SLAB.yellow();
                }).slabTag(SuCraftBlockItemTags.TERRACOTTA_SLABS)
                .stairs().stairsFallback(switch (dyeColor) {
                    case BLACK -> Blocks.CONCRETE_STAIRS.black();
                    case BLUE -> Blocks.CONCRETE_STAIRS.blue();
                    case BROWN -> Blocks.CONCRETE_STAIRS.brown();
                    case CYAN -> Blocks.POLISHED_TUFF_STAIRS;
                    case GRAY -> Blocks.CONCRETE_STAIRS.brown();
                    case GREEN -> Blocks.CONCRETE_STAIRS.green();
                    case LIGHT_BLUE -> Blocks.CONCRETE_STAIRS.lightBlue();
                    case LIGHT_GRAY -> Blocks.CONCRETE_STAIRS.brown();
                    case LIME -> Blocks.CONCRETE_STAIRS.lime();
                    case MAGENTA -> Blocks.CONCRETE_STAIRS.pink();
                    case ORANGE -> Blocks.SMOOTH_RED_SANDSTONE_STAIRS;
                    case PINK -> Blocks.SMOOTH_RED_SANDSTONE_STAIRS;
                    case PURPLE -> Blocks.CONCRETE_STAIRS.purple();
                    case RED -> Blocks.POLISHED_CINNABAR_STAIRS;
                    case WHITE -> Blocks.SMOOTH_QUARTZ_STAIRS;
                    case YELLOW -> Blocks.CONCRETE_STAIRS.yellow();
                }).stairsTag(SuCraftBlockItemTags.TERRACOTTA_STAIRS)
                .stoneWall().wallFallback(switch (dyeColor) {
                    case BLACK -> Blocks.NETHER_BRICK_WALL;
                    case BLUE -> Blocks.POLISHED_DEEPSLATE_WALL;
                    case BROWN -> Blocks.MUD_BRICK_WALL;
                    case CYAN -> Blocks.POLISHED_TUFF_WALL;
                    case GRAY -> Blocks.MUD_BRICK_WALL;
                    case GREEN -> Blocks.MOSSY_COBBLESTONE_WALL;
                    case LIGHT_BLUE -> Blocks.POLISHED_TUFF_WALL;
                    case LIGHT_GRAY -> Blocks.MUD_BRICK_WALL;
                    case LIME -> Blocks.MOSSY_COBBLESTONE_WALL;
                    case MAGENTA -> Blocks.CINNABAR_WALL;
                    case ORANGE -> Blocks.RED_SANDSTONE_WALL;
                    case PINK -> Blocks.POLISHED_CINNABAR_WALL;
                    case PURPLE -> Blocks.CINNABAR_WALL;
                    case RED -> Blocks.POLISHED_CINNABAR_WALL;
                    case WHITE -> Blocks.DIORITE_WALL;
                    case YELLOW -> Blocks.RED_SANDSTONE_WALL;
                }).wallTag(SuCraftBlockItemTags.TERRACOTTA_WALLS)
                .stoneButton().blockSetType(BlockSetType.STONE).buttonFallback(switch (dyeColor) {
                    case BLACK -> Blocks.DARK_OAK_BUTTON;
                    case BLUE -> Blocks.DARK_OAK_BUTTON;
                    case BROWN -> Blocks.SPRUCE_BUTTON;
                    case CYAN -> Blocks.STONE_BUTTON;
                    case GRAY -> Blocks.DARK_OAK_BUTTON;
                    case GREEN -> Blocks.WARPED_BUTTON;
                    case LIGHT_BLUE -> Blocks.POPLAR_BUTTON;
                    case LIGHT_GRAY -> Blocks.JUNGLE_BUTTON;
                    case LIME -> Blocks.BIRCH_BUTTON;
                    case MAGENTA -> Blocks.MANGROVE_BUTTON;
                    case ORANGE -> Blocks.ACACIA_BUTTON;
                    case PINK -> Blocks.JUNGLE_BUTTON;
                    case PURPLE -> Blocks.CRIMSON_BUTTON;
                    case RED -> Blocks.MANGROVE_BUTTON;
                    case WHITE -> Blocks.CHERRY_BUTTON;
                    case YELLOW -> Blocks.OAK_BUTTON;
                }).buttonTag(SuCraftBlockItemTags.TERRACOTTA_BUTTONS)
                .stonePressurePlate().pressurePlateFallback(switch (dyeColor) {
                    case BLACK -> Blocks.DARK_OAK_PRESSURE_PLATE;
                    case BLUE -> Blocks.DARK_OAK_PRESSURE_PLATE;
                    case BROWN -> Blocks.SPRUCE_PRESSURE_PLATE;
                    case CYAN -> Blocks.STONE_PRESSURE_PLATE;
                    case GRAY -> Blocks.DARK_OAK_PRESSURE_PLATE;
                    case GREEN -> Blocks.WARPED_PRESSURE_PLATE;
                    case LIGHT_BLUE -> Blocks.POPLAR_PRESSURE_PLATE;
                    case LIGHT_GRAY -> Blocks.JUNGLE_PRESSURE_PLATE;
                    case LIME -> Blocks.BIRCH_PRESSURE_PLATE;
                    case MAGENTA -> Blocks.MANGROVE_PRESSURE_PLATE;
                    case ORANGE -> Blocks.ACACIA_PRESSURE_PLATE;
                    case PINK -> Blocks.JUNGLE_PRESSURE_PLATE;
                    case PURPLE -> Blocks.CRIMSON_PRESSURE_PLATE;
                    case RED -> Blocks.MANGROVE_PRESSURE_PLATE;
                    case WHITE -> Blocks.CHERRY_PRESSURE_PLATE;
                    case YELLOW -> Blocks.OAK_PRESSURE_PLATE;
                }).pressurePlateTag(SuCraftBlockItemTags.TERRACOTTA_PRESSURE_PLATES)
                .build()
        );

    // Stone / Tuff

    public static final BlockSet.WithButtonAndPressurePlate TUFF =
        BlockSet.builder("tuff").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.TUFF)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate STONE_POLISHED_TUFF =
        BlockSet.builder("polished_tuff").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.POLISHED_TUFF).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab()
            .stairs()
            .stoneWall()
            /*.stoneFence().fenceFallback(Blocks.POPLAR_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate TUFF_STONE_BRICKS =
        BlockSet.builder("tuff_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.TUFF_BRICKS).fullBlockItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
            .slab()
            .stairs()
            .stoneWall()
            /*.stoneFence().fenceFallback(Blocks.POPLAR_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType CHISELED_TUFF_STONE_BRICKS = blockType("chiseled_tuff_bricks")
        .fullBlock(Blocks.CHISELED_TUFF_BRICKS)
        .mineableWithPickaxe()
        .dataGenerateItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY);

    public static final BlockSet.WithFullBlockAndSlabAndStairs TUFF_PILLAR = stonePillarSet(
        "tuff_pillar",
        Blocks.POLISHED_TUFF,
        Blocks.POLISHED_TUFF_SLAB,
        Blocks.POLISHED_TUFF_STAIRS
    );

    // public static final SuCraftBlockType BEVELED
    //
    // public static final BlockSet.WithButtonAndPressurePlate BEVELED_POLISHED_TUFF =
    //     BlockSet.builder("polished_tuff").mineableWithPickaxe()
    //         .shapeIdentifierSuffixFunction(suffix -> suffix + "_new")
    //         .vanilla(Blocks.POLISHED_TUFF)
    //         .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
    //         .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
    //         .build();
    //
    // public static final BlockSet.WithButtonAndPressurePlate TUFF_BEVELED_BRICKS =
    //     BlockSet.builder("tuff_bricks").mineableWithPickaxe()
    //         .shapeIdentifierSuffixFunction(suffix -> suffix + "_new")
    //         .vanilla(Blocks.TUFF_BRICKS)
    //         .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
    //         .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
    //         .build();

    // Wood / Barrels

    private static SuCraftBlockType woodenBarrel(String path, Block planks) {
        return blockType(path).woodenBarrel(planks);
    }

    public static final SuCraftBlockType ACACIA_BARREL = woodenBarrel("acacia_barrel", Blocks.ACACIA_PLANKS);
    public static final SuCraftBlockType BAMBOO_BARREL = woodenBarrel("bamboo_barrel", Blocks.BAMBOO_PLANKS);
    public static final SuCraftBlockType BIRCH_BARREL = woodenBarrel("birch_barrel", Blocks.BIRCH_PLANKS);
    public static final SuCraftBlockType CHERRY_BARREL = woodenBarrel("cherry_barrel", Blocks.CHERRY_PLANKS);
    public static final SuCraftBlockType CRIMSON_BARREL = woodenBarrel("crimson_barrel", Blocks.CRIMSON_PLANKS);
    public static final SuCraftBlockType DARK_OAK_BARREL = woodenBarrel("dark_oak_barrel", Blocks.DARK_OAK_PLANKS);
    public static final SuCraftBlockType JUNGLE_BARREL = woodenBarrel("jungle_barrel", Blocks.JUNGLE_PLANKS);
    public static final SuCraftBlockType MANGROVE_BARREL = woodenBarrel("mangrove_barrel", Blocks.MANGROVE_PLANKS);
    public static final SuCraftBlockType OAK_BARREL = woodenBarrel("oak_barrel", Blocks.OAK_PLANKS);
    public static final SuCraftBlockType PALE_OAK_BARREL = woodenBarrel("pale_oak_barrel", Blocks.PALE_OAK_PLANKS);
    public static final SuCraftBlockType WARPED_BARREL = woodenBarrel("warped_barrel", Blocks.WARPED_PLANKS);

    // Wood / Bookshelves

    private static BlockSet.WithFullBlockAndSlabAndStairs bookshelf(
        String fullBlockPath,
        Block fullBlockPlanks,
        Block slabFallback,
        Block stairsFallback,
        Block wallFallback
    ) {
        return bookshelf(fullBlockPath, fullBlockPlanks, slabFallback, stairsFallback, wallFallback, null);
    }

    private static BlockSet.DirectImpl bookshelf(
        String fullBlockPath,
        Block fullBlockPlanks,
        Block slabFallback,
        Block stairsFallback,
        Block wallFallback,
        @Nullable Block vanillaFullBlock
    ) {
        return BlockSet.builder(fullBlockPath).mineableWithAxe().tag(BlockTags.ENCHANTMENT_POWER_PROVIDER)
            .fullBlock(vanillaFullBlock != null).vanilla(Blocks.BOOKSHELF)
            .slab().slabFallback(slabFallback).slabTag(SuCraftBlockItemTags.BOOKSHELF_SLABS)
            .stairs().stairsFallback(stairsFallback).stairsTag(SuCraftBlockItemTags.BOOKSHELF_STAIRS)
            /*.woodenWall().wallFallback(wallFallback)*/
            .fullBlockModelProvider(block -> TexturedModel.COLUMN.get(block).updateTextures(mappings -> {
                mappings.put(TextureSlot.END, TextureMapping.getBlockTexture(fullBlockPlanks));
                mappings.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(vanillaFullBlock != null ? vanillaFullBlock : block));
            }))
            .propertiesFinalizer(properties -> properties.mapColor(fullBlockPlanks.defaultMapColor()))
            .fullBlockFinalizer(type -> type.dataGenerateBlockLootTable(factory -> {
                if (vanillaFullBlock == null) {
                    factory.add(provider -> provider.add(type.getNMS(),
                        block -> provider.createSingleItemTableWithSilkTouch(block, Items.BOOK, ContextIntProviders.exactly(3))
                    ));
                }
            }))
            .slabFinalizer(type -> type.dataGenerateBlockLootTable(factory -> factory.add(provider -> provider.add(type.getNMS(),
                block -> LootTable.lootTable()
                    // Silk Touch: drop the block itself
                    .withPool(
                        LootPool.lootPool()
                            .when(provider.hasSilkTouch())
                            .setRolls(ContextIntProviders.exactly(1))
                            .add(LootItem.lootTableItem(block).apply(
                                SetItemCountFunction.setCount(ContextIntProviders.exactly(2)).when(
                                    MatchBlock.blockMatches(BuiltInRegistries.BLOCK, block,
                                        StatePropertiesPredicate.Builder.properties()
                                            .hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)
                                    )
                                )
                            ))
                    )
                    // Otherwise: drop books
                    .withPool(
                        LootPool.lootPool()
                            .when(provider.doesNotHaveSilkTouch())
                            .setRolls(ContextIntProviders.exactly(1))
                            .add(LootItem.lootTableItem(Items.BOOK).apply(
                                SetItemCountFunction.setCount(ContextIntProviders.exactly(3)).when(
                                    MatchBlock.blockMatches(BuiltInRegistries.BLOCK, block,
                                        StatePropertiesPredicate.Builder.properties()
                                            .hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)
                                    )
                                )
                            ))
                    )
            ))))
            .stairsFinalizer(type -> type.dataGenerateBlockLootTable(factory -> factory.add(provider -> provider.add(type.getNMS(),
                block -> provider.createSingleItemTableWithSilkTouch(block, Items.BOOK, ContextIntProviders.exactly(3))
            ))))
            /*.wallFinalizer(type -> type.dataGenerateBlockLootTable(factory -> factory.add(provider -> provider.add(type.getForDataGeneration(),
                block -> provider.createSingleItemTableWithSilkTouch(block, Items.BOOK, ContextIntProviders.exactly(3))
            ))))*/
            .build();
    }

    public static final BlockSet.WithFullBlockAndSlabAndStairs ACACIA_BOOKSHELF = bookshelf(
        "acacia_bookshelf",
        Blocks.ACACIA_PLANKS, Blocks.ACACIA_SLAB, Blocks.ACACIA_STAIRS, Blocks.RED_SANDSTONE_WALL
    );

    public static final BlockSet.WithFullBlockAndSlabAndStairs BAMBOO_BOOKSHELF = bookshelf(
        "bamboo_bookshelf",
        Blocks.BAMBOO_PLANKS, Blocks.BAMBOO_SLAB, Blocks.BAMBOO_STAIRS, Blocks.SULFUR_BRICK_WALL
    );

    public static final BlockSet.WithFullBlockAndSlabAndStairs BIRCH_BOOKSHELF = bookshelf(
        "birch_bookshelf",
        Blocks.BIRCH_PLANKS, Blocks.BIRCH_SLAB, Blocks.BIRCH_STAIRS, Blocks.SANDSTONE_WALL
    );

    public static final BlockSet.WithFullBlockAndSlabAndStairs CHERRY_BOOKSHELF = bookshelf(
        "cherry_bookshelf",
        Blocks.CHERRY_PLANKS, Blocks.CHERRY_SLAB, Blocks.CHERRY_STAIRS, Blocks.GRANITE_WALL
    );

    public static final BlockSet.WithFullBlockAndSlabAndStairs CRIMSON_BOOKSHELF = bookshelf(
        "crimson_bookshelf",
        Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_SLAB, Blocks.CRIMSON_STAIRS, Blocks.NETHER_BRICK_WALL
    );

    public static final BlockSet.WithFullBlockAndSlabAndStairs DARK_OAK_BOOKSHELF = bookshelf(
        "dark_oak_bookshelf",
        Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_SLAB, Blocks.DARK_OAK_STAIRS, Blocks.MUD_BRICK_WALL
    );

    public static final BlockSet.WithFullBlockAndSlabAndStairs JUNGLE_BOOKSHELF = bookshelf(
        "jungle_bookshelf",
        Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_SLAB, Blocks.JUNGLE_STAIRS, Blocks.GRANITE_WALL
    );

    public static final BlockSet.WithFullBlockAndSlabAndStairs MANGROVE_BOOKSHELF = bookshelf(
        "mangrove_bookshelf",
        Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_SLAB, Blocks.MANGROVE_STAIRS, Blocks.CINNABAR_BRICK_WALL
    );

    public static final BlockSet.WithSlabAndStairs OAK_BOOKSHELF = bookshelf(
        "oak_bookshelf",
        Blocks.OAK_PLANKS, Blocks.OAK_SLAB, Blocks.OAK_STAIRS, Blocks.MUD_BRICK_WALL, Blocks.BOOKSHELF
    );

    public static final BlockSet.WithFullBlockAndSlabAndStairs SPRUCE_BOOKSHELF = bookshelf(
        "spruce_bookshelf",
        Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_SLAB, Blocks.SPRUCE_STAIRS, Blocks.MUD_BRICK_WALL
    );

    public static final BlockSet.WithFullBlockAndSlabAndStairs WARPED_BOOKSHELF = bookshelf(
        "warped_bookshelf",
        Blocks.WARPED_PLANKS, Blocks.WARPED_SLAB, Blocks.WARPED_STAIRS, Blocks.PRISMARINE_WALL
    );

    // Wood / Dyed planks

    public static final class DyedPlankFamily<V> extends Family.Boolean<V> {

        public DyedPlankFamily(Boolean2ObjectFunction<V> constructor) {
            super(constructor);
        }

        public V vibrant() {
            return this.get(false);
        }

        public V abraded() {
            return this.get(true);
        }

    }

    public static final DyedPlankFamily<Family.DyeColor<BlockSet.WithFullBlockAndSlabAndStairsAndWallAndFenceAndFenceGateAndButtonAndPressurePlate>> DYED_PLANKS =
        new DyedPlankFamily<>(abraded ->
            new Family.DyeColor<>(dyeColor ->
                BlockSet.builder(dyeColor.getSerializedName() + (abraded ? "" : "_vibrant") + "_planks").mineableWithAxe()
                    .shapeIdentifierSuffixFunction(suffix -> "_plank" + suffix)
                    .fullBlock().fallback(switch (dyeColor) {
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
                    }).propertiesFrom(Blocks.OAK_PLANKS).fullBlockTag(BlockItemTags.PLANKS)
                    .woodenSlab()
                    .woodenStairs()
                    .woodenWall().wallFallback(switch (dyeColor) {
                        case BLACK -> Blocks.POLISHED_BLACKSTONE_BRICK_WALL;
                        case BLUE -> Blocks.PRISMARINE_WALL;
                        case BROWN -> Blocks.MUD_BRICK_WALL;
                        case CYAN -> Blocks.PRISMARINE_WALL;
                        case GRAY -> Blocks.POLISHED_TUFF_WALL;
                        case GREEN -> Blocks.MOSSY_COBBLESTONE_WALL;
                        case LIGHT_BLUE -> Blocks.PRISMARINE_WALL;
                        case LIGHT_GRAY -> Blocks.ANDESITE_WALL;
                        case LIME -> Blocks.MOSSY_COBBLESTONE_WALL;
                        case MAGENTA -> Blocks.CINNABAR_WALL;
                        case ORANGE -> Blocks.RED_SANDSTONE_WALL;
                        case PINK -> Blocks.CINNABAR_WALL;
                        case PURPLE -> Blocks.CINNABAR_WALL;
                        case RED -> Blocks.CINNABAR_BRICK_WALL;
                        case WHITE -> Blocks.DIORITE_WALL;
                        case YELLOW -> Blocks.SULFUR_BRICK_WALL;
                    })
                    .woodenFence()
                    .woodenFenceGate()
                    .woodenButton()
                    .woodenPressurePlate()
                    .build()
            )
        );

    // Wood / Ladders

    private static SuCraftBlockType woodenLadder(String path, Block planks) {
        return blockType(path).woodenLadder(planks);
    }

    public static final SuCraftBlockType ACACIA_LADDER = woodenLadder("acacia_ladder", Blocks.ACACIA_PLANKS);
    public static final SuCraftBlockType BAMBOO_LADDER = woodenLadder("bamboo_ladder", Blocks.BAMBOO_PLANKS);
    public static final SuCraftBlockType BIRCH_LADDER = woodenLadder("birch_ladder", Blocks.BIRCH_PLANKS);
    public static final SuCraftBlockType CHERRY_LADDER = woodenLadder("cherry_ladder", Blocks.CHERRY_PLANKS);
    public static final SuCraftBlockType CRIMSON_LADDER = woodenLadder("crimson_ladder", Blocks.CRIMSON_PLANKS);
    public static final SuCraftBlockType DARK_OAK_LADDER = woodenLadder("dark_oak_ladder", Blocks.DARK_OAK_PLANKS);
    public static final SuCraftBlockType JUNGLE_LADDER = woodenLadder("jungle_ladder", Blocks.JUNGLE_PLANKS);
    public static final SuCraftBlockType MANGROVE_LADDER = woodenLadder("mangrove_ladder", Blocks.MANGROVE_PLANKS);
    public static final SuCraftBlockType PALE_OAK_LADDER = woodenLadder("pale_oak_ladder", Blocks.PALE_OAK_PLANKS);
    public static final SuCraftBlockType SPRUCE_LADDER = woodenLadder("spruce_ladder", Blocks.SPRUCE_PLANKS);
    public static final SuCraftBlockType WARPED_LADDER = woodenLadder("warped_ladder", Blocks.WARPED_PLANKS);

    // Wood / Walls

    public static final Family.WoodType<SuCraftBlockType> VANILLA_PLANK_WALLS =
        Family.WoodType.forVanillaWoodTypes(woodType ->
            blockType(woodType.name() + "_wall")
                .wall(BuiltInRegistries.BLOCK.getValue(Identifier.withDefaultNamespace(woodType.name() + "_fence")), switch (woodType.name()) {
                    case "acacia" -> Blocks.RED_SANDSTONE_WALL;
                    case "bamboo" -> Blocks.SULFUR_BRICK_WALL;
                    case "birch" -> Blocks.SANDSTONE_WALL;
                    case "cherry" -> Blocks.BRICK_WALL;
                    case "crimson" -> Blocks.RED_NETHER_BRICK_WALL;
                    case "dark_oak" -> Blocks.NETHER_BRICK_WALL;
                    case "jungle" -> Blocks.DIORITE_WALL;
                    case "mangrove" -> Blocks.CINNABAR_BRICK_WALL;
                    case "oak" -> Blocks.MUD_BRICK_WALL;
                    case "pale_oak" -> Blocks.DIORITE_WALL;
                    case "poplar" -> Blocks.ANDESITE_WALL;
                    case "spruce" -> Blocks.MUD_BRICK_WALL;
                    case "warped" -> Blocks.PRISMARINE_WALL;
                    default -> throw new IllegalArgumentException();
                }).dataGenerateModel((factory, block) ->
                    factory.addBlock(generators -> PlatformDependent.BlockModelGenerators.addWall(generators, block, Identifier.withDefaultNamespace("block/" + woodType.name() + "_planks")))
                )
                .mineableWithAxe()
                .dataGenerateTag(SuCraftBlockItemTags.WOODEN_WALLS)
        );

    // Wool

    public static final Family.DyeColor<BlockSet.WithWallAndButtonAndPressurePlate> WOOL =
        new Family.DyeColor<>(dyeColor ->
            BlockSet.builder(dyeColor.getSerializedName() + "_wool").tag(BlockItemTags.DAMPENS_VIBRATIONS).tag(BlockTags.SHEARS_MAJOR_BREAKING_SPEED)
                .fullBlockOnlyForDataGeneration().vanilla(Blocks.WOOL.pick(dyeColor))
                .wall().wallFallback(wallFallbackForSmoothDyeColor(dyeColor)).wallTag(SuCraftBlockItemTags.WOOL_WALLS)
                .button().blockSetType(BlockSetType.STONE).buttonFallback(buttonFallbackForSmoothDyeColor(dyeColor)).buttonTag(SuCraftBlockItemTags.WOOL_BUTTONS)
                .pressurePlate().pressurePlateFallback(pressurePlateFallbackForSmoothDyeColor(dyeColor)).pressurePlateTag(SuCraftBlockItemTags.WOOL_PRESSURE_PLATES)
                .build()
        );

    private static SuCraftBlockType ifInitializingElse(SuCraftBlockType ifInitializing, SuCraftBlockType otherwise) {
        return DataGenerationFlag.isDataGeneration || !PlatformDependent.SushiPlugin.hasEnablingStarted() ? ifInitializing : otherwise;
    }

    private static SuCraftBlockType blockType(String path) {
        return SuCraftBlockType.create(path);
    }

    private static SuCraftBlockType blockTypeWithoutItem(String path) {
        return SuCraftBlockType.createWithoutItem(path);
    }

    private static SuCraftBlockType blockTypeNotDefinedByCode(String path) {
        return SuCraftBlockType.createNotDefinedByCode(path);
    }

    private static SuCraftBlockType blockTypeNotDefinedByCode(String path, boolean itemDefinedByCode) {
        return SuCraftBlockType.createNotDefinedByCode(path, itemDefinedByCode);
    }

    private static BlockType[] values;
    private static Block[] valuesNMS;
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

    private static Stream<SuCraftBlockType> typesFromBlockSet(BlockSet blockSet) {
        return Arrays.stream(blockSet.types());
    }

    private static Stream<SuCraftBlockType> typesFromFamily(Family<?, ?> family) {
        return family.stream().flatMap(value -> {
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
