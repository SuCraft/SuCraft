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
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchBlock;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import org.bukkit.block.BlockType;
import org.jspecify.annotations.Nullable;
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

    // Soil - Red sand

    public static final BlockSet.WithButtonAndPressurePlate RED_SANDSTONE =
        BlockSet.builder("red_sandstone").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.RED_SANDSTONE)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.ACACIA_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.ACACIA_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithStairsAndWallAndButtonAndPressurePlate CUT_RED_SANDSTONE =
        BlockSet.builder("cut_red_sandstone").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.CUT_RED_SANDSTONE)
            .stairs().stairsFallback(Blocks.RED_SANDSTONE_STAIRS)
            .stoneWall().wallFallback(Blocks.RED_SANDSTONE_WALL)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.ACACIA_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.ACACIA_PRESSURE_PLATE)
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

    public static final BlockSet.WithButtonAndPressurePlate SANDSTONE =
        BlockSet.builder("sandstone").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.SANDSTONE)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.BIRCH_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.BIRCH_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithStairsAndWallAndButtonAndPressurePlate CUT_SANDSTONE =
        BlockSet.builder("cut_sandstone").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.CUT_SANDSTONE)
            .stairs().stairsFallback(Blocks.SANDSTONE_STAIRS)
            .stoneWall().wallFallback(Blocks.SANDSTONE_WALL)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.BIRCH_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.BIRCH_PRESSURE_PLATE)
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

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate SOUL_SANDSTONE =
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
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.DARK_OAK_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.DARK_OAK_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate CUT_SOUL_SANDSTONE =
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
            .stoneWall().wallFallback(Blocks.MUD_BRICK_WALL)
            /*.stoneFence().fenceFallback(Blocks.DARK_OAK_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.DARK_OAK_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.DARK_OAK_PRESSURE_PLATE)
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
        .dataGenerateModel((factory, block) -> {
            factory.addBlockCubeColumn(
                block,
                block.builtInRegistryHolder().key().identifier().withPrefix("block/"),
                SOUL_SANDSTONE.fullBlockType().identifier.withSuffix("_top").withPrefix("block/")
            );
        }).mineableWithPickaxe()
        .dataGenerateItemTag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
        .dataGenerateTag(BlockItemTags.SOUL_FIRE_BASE_BLOCKS)
        .dataGenerateTag(BlockTags.SOUL_SPEED_BLOCKS);

    // Stone / Andesite

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
            .fullBlock().vanilla(Blocks.POLISHED_ANDESITE)
            .slab()
            .stairs()
            .stoneWall().wallFallback(Blocks.ANDESITE_WALL)
            /*.stoneFence().fenceFallback(Blocks.POPLAR_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType CHISELED_ANDESITE_BRICKS = blockType("chiseled_andesite_bricks")
        .fullBlock(Blocks.POLISHED_ANDESITE)
        .mineableWithPickaxe();

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
            .fullBlock().vanilla(Blocks.CALCITE)
            .slab().slabFallback(Blocks.POLISHED_DIORITE_SLAB)
            .stairs().stairsFallback(Blocks.POLISHED_DIORITE_STAIRS)
            .stoneWall().wallFallback(Blocks.DIORITE_WALL)
            /*.stoneFence().fenceFallback(Blocks.PALE_OAK_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.PALE_OAK_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.PALE_OAK_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate CALCITE_BRICKS =
        BlockSet.builder("calcite_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.CALCITE)
            .slab().slabFallback(Blocks.POLISHED_DIORITE_SLAB)
            .stairs().stairsFallback(Blocks.POLISHED_DIORITE_STAIRS)
            .stoneWall().wallFallback(Blocks.DIORITE_WALL)
            /*.stoneFence().fenceFallback(Blocks.PALE_OAK_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.PALE_OAK_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.PALE_OAK_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType CHISELED_CALCITE_BRICKS = blockType("chiseled_calcite_bricks")
        .fullBlock(Blocks.CALCITE)
        .mineableWithPickaxe();

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

    // Stone / Cobblestone

    public static final BlockSet.WithWall COBBLESTONE =
        BlockSet.builder("cobblestone").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.COBBLESTONE)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithWall MOSSY_COBBLESTONE =
        BlockSet.builder("mossy_cobblestone").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.MOSSY_COBBLESTONE)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

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
            .fullBlock().vanilla(Blocks.POLISHED_DIORITE)
            .slab()
            .stairs()
            .stoneWall().wallFallback(Blocks.DIORITE_WALL)
            /*.stoneFence().fenceFallback(Blocks.PALE_OAK_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.PALE_OAK_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.PALE_OAK_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType CHISELED_DIORITE_BRICKS = blockType("chiseled_diorite_bricks")
        .fullBlock(Blocks.POLISHED_DIORITE)
        .mineableWithPickaxe();

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
            .fullBlock().vanilla(Blocks.DRIPSTONE_BLOCK)
            .slab().slabFallback(Blocks.POLISHED_GRANITE_SLAB)
            .stairs().stairsFallback(Blocks.POLISHED_GRANITE_STAIRS)
            .stoneWall().wallFallback(Blocks.GRANITE_WALL)
            /*.stoneFence().fenceFallback(Blocks.JUNGLE_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.JUNGLE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.JUNGLE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate DRIPSTONE_BRICKS =
        BlockSet.builder("dripstone_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.DRIPSTONE_BLOCK)
            .slab().slabFallback(Blocks.POLISHED_GRANITE_SLAB)
            .stairs().stairsFallback(Blocks.POLISHED_GRANITE_STAIRS)
            .stoneWall().wallFallback(Blocks.GRANITE_WALL)
            /*.stoneFence().fenceFallback(Blocks.JUNGLE_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.JUNGLE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.JUNGLE_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType CHISELED_DRIPSTONE_BRICKS = blockType("chiseled_dripstone_bricks")
        .fullBlock(Blocks.DRIPSTONE_BLOCK)
        .mineableWithPickaxe();

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
            .fullBlock().vanilla(Blocks.POLISHED_GRANITE)
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
        .mineableWithPickaxe();

    // Stone / Prismarine

    public static final BlockSet.WithButtonAndPressurePlate PRISMARINE =
        BlockSet.builder("prismarine").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.PRISMARINE)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.WARPED_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.WARPED_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithButtonAndPressurePlate DARK_PRISMARINE =
        BlockSet.builder("dark_prismarine").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.DARK_PRISMARINE)
            .stoneWall().wallFallback(Blocks.PRISMARINE_WALL)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.WARPED_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.WARPED_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithButtonAndPressurePlate PRISMARINE_BRICKS =
        BlockSet.builder("prismarine_bricks").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.PRISMARINE_BRICKS)
            .stoneWall().wallFallback(Blocks.PRISMARINE_WALL)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.WARPED_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.WARPED_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate ENGRAVED_PRISMARINE_BRICKS =
        BlockSet.builder("engraved_prismarine_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.PRISMARINE_BRICKS)
            .slab()
            .stairs()
            .stoneWall().wallFallback(Blocks.PRISMARINE_WALL)
            /*.stoneFence().fenceFallback(Blocks.WARPED_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.WARPED_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.WARPED_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate PRISMARINE_DIAGONAL_TILES =
        BlockSet.builder("prismarine_diagonal_tiles").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.PRISMARINE_BRICKS)
            .slab()
            .stairs()
            .stoneWall().wallFallback(Blocks.PRISMARINE_WALL)
            /*.stoneFence().fenceFallback(Blocks.WARPED_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.WARPED_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.WARPED_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate PRISMARINE_SCALES =
        BlockSet.builder("prismarine_scales").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.PRISMARINE_BRICKS)
            .slab()
            .stairs()
            .stoneWall().wallFallback(Blocks.PRISMARINE_WALL)
            /*.stoneFence().fenceFallback(Blocks.WARPED_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.WARPED_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.WARPED_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate PRISMARINE_STONE_BRICKS =
        BlockSet.builder("prismarine_stone_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.PRISMARINE_BRICKS)
            .slab()
            .stairs()
            .stoneWall().wallFallback(Blocks.PRISMARINE_WALL)
            /*.stoneFence().fenceFallback(Blocks.WARPED_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.WARPED_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.WARPED_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType CHISELED_PRISMARINE_BRICKS = blockType("chiseled_prismarine_bricks")
        .fullBlock(Blocks.PRISMARINE_BRICKS)
        .mineableWithPickaxe();

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

    public static final BlockSet.WithButtonAndPressurePlate CRACKED_STONE_BRICKS =
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

    public static final BlockSet.WithButtonAndPressurePlate CHISELED_STONE_BRICKS =
        BlockSet.builder("chiseled_stone_bricks").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.CHISELED_STONE_BRICKS)
            .slab().slabFallback(Blocks.STONE_BRICK_SLAB)
            .stairs().stairsFallback(Blocks.STONE_BRICK_STAIRS)
            .stoneWall().wallFallback(Blocks.STONE_BRICK_WALL)
            /*.stoneFence().fenceFallback(Blocks.POPLAR_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

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
        type.dataGenerateModel((factory, block) -> {
            factory.addBlock(generators -> {
                Block host = hostBlockForDataGeneration.get();
                MultiVariant model = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(host));
                generators.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, model));
                generators.itemModelOutput.copy(host.asItem(), block.asItem());
            });
        });
        return type;
    }

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate DIRTY_STONE_BRICKS =
        BlockSet.builder("dirty_stone_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.STONE_BRICKS).fullBlockTag(BlockItemTags.STONE_BRICKS)
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
            .fullBlock().vanilla(Blocks.STONE_BRICKS).fullBlockTag(BlockItemTags.STONE_BRICKS)
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

    // Stone / Tuff

    public static final BlockSet.WithButtonAndPressurePlate TUFF =
        BlockSet.builder("tuff").mineableWithPickaxe()
            .fullBlockOnlyForDataGeneration().vanilla(Blocks.TUFF)
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate ROUGH_POLISHED_TUFF =
        BlockSet.builder("polished_tuff").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.POLISHED_TUFF)
            .slab()
            .stairs()
            .stoneWall()
            /*.stoneFence().fenceFallback(Blocks.POPLAR_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate ROUGH_TUFF_BRICKS =
        BlockSet.builder("tuff_bricks").mineableWithPickaxe()
            .fullBlock().vanilla(Blocks.TUFF_BRICKS)
            .slab()
            .stairs()
            .stoneWall()
            /*.stoneFence().fenceFallback(Blocks.POPLAR_FENCE)*/
            .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
            .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
            .build();

    public static final SuCraftBlockType ROUGH_CHISELED_TUFF_BRICKS = blockType("chiseled_tuff_bricks")
        .fullBlock(Blocks.CHISELED_TUFF_BRICKS)
        .mineableWithPickaxe();

    // public static final SuCraftBlockType SMOO
    //
    // public static final BlockSet.WithButtonAndPressurePlate SMOOTH_POLISHED_TUFF =
    //     BlockSet.builder("polished_tuff").mineableWithPickaxe()
    //         .shapeIdentifierSuffixFunction(suffix -> suffix + "_new")
    //         .vanilla(Blocks.POLISHED_TUFF)
    //         .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
    //         .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
    //         .build();
    //
    // public static final BlockSet.WithButtonAndPressurePlate SMOOTH_TUFF_BRICKS =
    //     BlockSet.builder("tuff_bricks").mineableWithPickaxe()
    //         .shapeIdentifierSuffixFunction(suffix -> suffix + "_new")
    //         .vanilla(Blocks.TUFF_BRICKS)
    //         .stoneButton(BlockSetType.STONE).buttonFallback(Blocks.STONE_BUTTON)
    //         .stonePressurePlate().pressurePlateFallback(Blocks.STONE_PRESSURE_PLATE)
    //         .build();

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

    private static BlockSet.Impl bookshelf(
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
                    .woodenFence()
                    .woodenFenceGate()
                    .woodenButton()
                    .woodenPressurePlate()
                    .build()
            )
        );

    private static SuCraftBlockType blockType(String path) {
        return new SuCraftBlockType(path);
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
