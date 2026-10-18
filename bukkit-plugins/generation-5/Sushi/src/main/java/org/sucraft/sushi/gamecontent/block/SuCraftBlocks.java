package org.sucraft.sushi.gamecontent.block;

import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import net.kyori.adventure.key.Key;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Registry;
import org.bukkit.block.BlockType;
import org.bukkit.craftbukkit.block.CraftBlockType;
import org.jspecify.annotations.Nullable;
import spout.api.SpoutRegistryKey;
import spout.api.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.api.gamecontent.datadriven.block.registry.BlockTypeRegistryEntry;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.api.clientview.packetmapping.blockstate.macro.registry.BlockStateMappingMacroRegistryEntryBuilderNMS;
import spout.api.gamecontent.datadriven.block.registry.BlockTypeRegistryEntryBuilderNMS;
import spout.clientview.packetmapping.blockstate.macro.FromToBlockMacro;
import spout.clientview.packetmapping.blockstate.macro.SlabMacro;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroType;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroTypes;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class SuCraftBlocks {

    private SuCraftBlocks() {
        throw new UnsupportedOperationException();
    }

    // Stone / Regular / Bricks / Dirty
    public static final Type DIRTY_STONE_BRICKS = blockType("dirty_stone_bricks")
        .fullBlock(Blocks.STONE_BRICKS);
    public static final Type DIRTY_STONE_BRICK_SLAB = blockType("dirty_stone_brick_slab")
        .slab(Blocks.STONE_BRICK_SLAB, Blocks.STONE_BRICKS);
    public static final Type DIRTY_STONE_BRICK_STAIRS = blockType("dirty_stone_brick_stairs")
        .stairs(DIRTY_STONE_BRICKS::getNMS, Blocks.STONE_BRICK_STAIRS);
    public static final Type DIRTY_STONE_BRICK_WALL = blockType("dirty_stone_brick_wall")
        .wall(Blocks.STONE_BRICK_WALL);

    // Stone / Regular / Bricks / Snowy
    public static final Type SNOWY_STONE_BRICKS = blockType("snowy_stone_bricks")
        .fullBlock(Blocks.STONE_BRICKS, Blocks.STONE_BRICKS);
    public static final Type SNOWY_STONE_BRICK_SLAB = blockType("snowy_stone_brick_slab")
        .slab(Blocks.STONE_BRICK_SLAB);
    public static final Type SNOWY_STONE_BRICK_STAIRS = blockType("snowy_stone_brick_stairs")
        .stairs(SNOWY_STONE_BRICKS::getNMS, Blocks.STONE_BRICK_STAIRS);
    public static final Type SNOWY_STONE_BRICK_WALL = blockType("snowy_stone_brick_wall")
        .wall(Blocks.STONE_BRICK_WALL);

    // Stone / Tuff / Polished
    public static final Type ROUGH_POLISHED_TUFF = blockType("polished_tuff")
        .fullBlock(Blocks.POLISHED_TUFF);
    public static final Type ROUGH_POLISHED_TUFF_SLAB = blockType("polished_tuff_slab")
        .slab(Blocks.POLISHED_TUFF_SLAB, Blocks.POLISHED_TUFF);
    public static final Type ROUGH_POLISHED_TUFF_STAIRS = blockType("polished_tuff_stairs")
        .stairs(ROUGH_POLISHED_TUFF::getNMS, Blocks.POLISHED_TUFF_STAIRS);
    public static final Type ROUGH_POLISHED_TUFF_WALL = blockType("polished_tuff_wall")
        .wall(Blocks.POLISHED_TUFF_WALL);

    // Stone / Tuff / Bricks
    public static final Type ROUGH_TUFF_BRICKS = blockType("tuff_bricks")
        .fullBlock(Blocks.TUFF_BRICKS);
    public static final Type ROUGH_TUFF_BRICK_SLAB = blockType("tuff_brick_slab")
        .slab(Blocks.TUFF_BRICK_SLAB, Blocks.TUFF_BRICKS);
    public static final Type ROUGH_TUFF_BRICK_STAIRS = blockType("tuff_brick_stairs")
        .stairs(ROUGH_TUFF_BRICKS::getNMS, Blocks.TUFF_BRICK_STAIRS);
    public static final Type ROUGH_TUFF_BRICK_WALL = blockType("tuff_brick_wall")
        .wall(Blocks.TUFF_BRICK_WALL);

    private static Type blockType(String path) {
        return new Type(path);
    }

    public static final class Type {

        public final Key key;
        public final Identifier identifier;
        private @Nullable BlockType value;
        private @Nullable Block valueNMS;

        private List<Consumer<BlockTypeRegistryEntryBuilderNMS>> blockBuilding = new ArrayList<>();
        private List<Consumer<BlockStateMappingMacroRegistryEntryBuilderNMS>> blockStateMappingMacroBuilding = new ArrayList<>();

        private Type(String path) {
            this(Key.key("sucraft", path));
        }

        private Type(Key key) {
            this.key = key;
            this.identifier = Identifier.fromNamespaceAndPath(key.namespace(), key.value());
        }

        public TypedKey<BlockType> registryKey() {
            return TypedKey.create(RegistryKey.BLOCK, this.key);
        }

        public TypedKey<BlockStateMapping> mappingRegistryKey() {
            return TypedKey.create(SpoutRegistryKey.BLOCK_STATE_MAPPING, this.key);
        }

        public TypedKey<spout.api.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro> mappingMacroRegistryKey() {
            return TypedKey.create(SpoutRegistryKey.BLOCK_STATE_MAPPING_MACRO, this.key);
        }

        public BlockType get() {
            BlockType value = this.value;
            if (value == null) {
                value = Registry.BLOCK.get(this.key);
                this.value = value;
            }
            return value;
        }

        public Block getNMS() {
            Block value = this.valueNMS;
            if (value == null) {
                value = CraftBlockType.bukkitToMinecraftNew(this.get());
                this.valueNMS = value;
            }
            return value;
        }

        public void applyToBlockBuilder(BlockTypeRegistryEntryBuilderNMS builder) {
            this.blockBuilding.forEach(it -> it.accept(builder));
            this.blockBuilding = null;
        }

        public Type blockBuild(Consumer<BlockTypeRegistryEntryBuilderNMS> builderConsumer) {
            this.blockBuilding.add(builderConsumer);
            return this;
        }

        public Type slabFactory() {
            return this.blockBuild(BlockTypeRegistryEntry.Builder::inheritsFromSlab);
        }

        public Type stairsFactory(Supplier<BlockState> baseState) {
            return this.blockBuild(builder -> builder.factoryNMS(properties -> new StairBlock(baseState.get(), properties)));
        }

        public Type wallFactory() {
            return this.blockBuild(builder -> builder.factoryNMS(WallBlock::new));
        }

        public Type properties(BlockBehaviour.Properties properties) {
            return this.blockBuild(builder -> builder.propertiesNMS(properties));
        }

        public Type properties(Consumer<BlockBehaviour.Properties> properties) {
            return this.blockBuild(builder -> builder.propertiesNMS(properties));
        }

        public Type propertiesLegacy(Block block) {
            return this.properties(BlockBehaviour.Properties.ofLegacyCopy(block));
        }

        public Type propertiesFull(Block block) {
            return this.properties(BlockBehaviour.Properties.ofFullCopy(block));
        }

        public void applyToBlockStateMappingMacroBuilder(BlockStateMappingMacroRegistryEntryBuilderNMS builder) {
            this.blockStateMappingMacroBuilding.forEach(it -> it.accept(builder));
            this.blockStateMappingMacroBuilding = null;
        }

        public Type blockStateMappingMacroBuild(Consumer<BlockStateMappingMacroRegistryEntryBuilderNMS> builderConsumer) {
            this.blockStateMappingMacroBuilding.add(builderConsumer);
            return this;
        }

        public Type macro(Supplier<BlockStateMappingMacro> macro) {
            return this.blockStateMappingMacroBuild(builder -> builder.set(macro.get()));
        }

        public Type fromToBlockMacro(BlockStateMappingMacroType type, Block fallback) {
            return this.macro(() -> new FromToBlockMacro(
                type,
                true,
                true,
                true,
                true,
                true,
                null,
                null,
                null,
                Type.this.getNMS(),
                fallback
            ));
        }

        public Type slabMacro(BlockStateMappingMacroType type, Block fallback, @Nullable BlockState fallbackBlockState) {
            return this.macro(() -> new SlabMacro(
                type,
                true,
                true,
                true,
                true,
                true,
                null,
                null,
                null,
                Type.this.getNMS(),
                fallback,
                fallbackBlockState
            ));
        }

        public Type fullBlock(Block propertiesFrom, Block fallback) {
            this.propertiesFull(propertiesFrom);
            return this.fromToBlockMacro(BlockStateMappingMacroTypes.FULL_BLOCK, fallback);
        }

        public Type fullBlock(Block propertiesFromAndFallback) {
            return this.fullBlock(propertiesFromAndFallback, propertiesFromAndFallback);
        }

        public Type slab(Block propertiesFrom, Block fallback, @Nullable BlockState fallbackBlockState) {
            this.slabFactory();
            this.propertiesFull(propertiesFrom);
            return this.slabMacro(BlockStateMappingMacroTypes.SLAB, fallback, fallbackBlockState);
        }

        public Type slab(Block propertiesFromAndFallback, @Nullable BlockState fallbackBlockState) {
            return this.slab(propertiesFromAndFallback, propertiesFromAndFallback, fallbackBlockState);
        }

        public Type slab(Block propertiesFromAndFallback, Block fallbackBlock) {
            return this.slab(propertiesFromAndFallback, propertiesFromAndFallback, fallbackBlock.defaultBlockState());
        }

        public Type slab(Block propertiesFromAndFallback) {
            return this.slab(propertiesFromAndFallback, propertiesFromAndFallback, null);
        }

        public Type stairs(Supplier<BlockState> base, Block propertiesFrom, Block fallback) {
            this.stairsFactory(base);
            this.propertiesFull(propertiesFrom);
            return this.fromToBlockMacro(BlockStateMappingMacroTypes.STAIRS, fallback);
        }

        public Type stairs(Supplier<Block> base, Block propertiesFromAndFallback) {
            return this.stairs(() -> base.get().defaultBlockState(), propertiesFromAndFallback, propertiesFromAndFallback);
        }

        public Type wall(Block propertiesFrom, Block fallback) {
            this.wallFactory();
            this.propertiesFull(propertiesFrom);
            return this.fromToBlockMacro(BlockStateMappingMacroTypes.WALL, fallback);
        }

        public Type wall(Block propertiesFromAndFallback) {
            return this.wall(propertiesFromAndFallback, propertiesFromAndFallback);
        }

    }

    private static Type[] values;

    public static Type[] values() {
        if (values == null) {
            values = Arrays.stream(SuCraftBlocks.class.getDeclaredFields())
                .filter(field ->
                    Modifier.isPublic(field.getModifiers()) &&
                        Modifier.isStatic(field.getModifiers()) &&
                        Modifier.isFinal(field.getModifiers()) &&
                        field.getType() == Type.class)
                .map(field -> {
                    field.trySetAccessible();
                    try {
                        return (Type) field.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(Type[]::new);
        }
        return values;
    }

}
