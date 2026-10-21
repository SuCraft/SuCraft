package org.sucraft.sushi.gamecontent.block;

import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import net.kyori.adventure.key.Key;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Registry;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.Nullable;
import org.sucraft.sushi.gamecontent.item.SuCraftItems;
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
import java.util.stream.Stream;

public final class SuCraftBlocks {

    private SuCraftBlocks() {
        throw new UnsupportedOperationException();
    }

    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall DIRTY_STONE_BRICKS =
        BlockSet.builder("dirty_stone_bricks")
            .vanilla(Blocks.STONE_BRICKS)
            .fullBlock().slab().stairs().wall()
            .build();
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall SNOWY_STONE_BRICKS =
        BlockSet.builder("snowy_stone_bricks")
            .vanilla(Blocks.STONE_BRICKS)
            .fullBlock().slab().stairs().wall()
            .build();
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall ROUGH_POLISHED_TUFF =
        BlockSet.builder("polished_tuff")
            .vanilla(Blocks.POLISHED_TUFF)
            .fullBlock().slab().stairs().wall()
            .build();
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall ROUGH_TUFF_BRICKS =
        BlockSet.builder("tuff_bricks")
            .vanilla(Blocks.TUFF_BRICKS)
            .fullBlock().slab().stairs().wall()
            .build();

    private static Type blockType(String path) {
        return new Type(path);
    }

    public static final class Type {

        public final Key key;
        public final Identifier identifier;
        private SuCraftItems.@Nullable Type item;
        private @Nullable BlockType value;
        private @Nullable Block valueNMS;

        private List<Consumer<BlockTypeRegistryEntryBuilderNMS>> blockBuilding = new ArrayList<>();
        private List<Consumer<BlockStateMappingMacroRegistryEntryBuilderNMS>> blockStateMappingMacroBuilding = new ArrayList<>();

        public Type(String path, boolean item) {
            this(Key.key("sucraft", path), item);
        }

        public Type(Key key, boolean item) {
            this.key = key;
            this.identifier = Identifier.fromNamespaceAndPath(key.namespace(), key.value());
            if (item) {
                this.item = new SuCraftItems.Type(key);
                this.item.blockFactory();
            }
        }

        public Type(Identifier identifier, boolean item) {
            this(Key.key(identifier.getNamespace(), identifier.getPath()), item);
        }

        public Type(String path) {
            this(path, true);
        }

        public Type(Key key) {
            this(key, true);
        }

        public Type(Identifier identifier) {
            this(identifier, true);
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
                value = BuiltInRegistries.BLOCK.getValue(this.identifier);
                this.valueNMS = value;
            }
            return value;
        }

        public @Nullable ItemType item() {
            return this.item != null ? this.item.get() : null;
        }

        public @Nullable Item itemNMS() {
            return this.item != null ? this.item.getNMS() : null;
        }

        public SuCraftItems.@Nullable Type itemType() {
            return this.item;
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

        public Type slabMacro(BlockStateMappingMacroType type, Block fallback, @Nullable BlockState fullBlockFallbackBlockState) {
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
                fullBlockFallbackBlockState
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

    private static BlockType[] values;
    private static Block[] valuesNMS;
    private static Type[] types;

    public static BlockType[] values() {
        if (values == null) {
            values = Arrays.stream(types())
                .map(SuCraftBlocks.Type::get).toArray(BlockType[]::new);
        }
        return values;
    }

    public static Block[] valuesNMS() {
        if (valuesNMS == null) {
            valuesNMS = Arrays.stream(types())
                .map(SuCraftBlocks.Type::getNMS).toArray(Block[]::new);
        }
        return valuesNMS;
    }

    public static Type[] types() {
        if (types == null) {
            types = Arrays.stream(SuCraftBlocks.class.getDeclaredFields())
                .filter(field ->
                    Modifier.isPublic(field.getModifiers()) &&
                        Modifier.isStatic(field.getModifiers()) &&
                        Modifier.isFinal(field.getModifiers()) &&
                        (Type.class.isAssignableFrom(field.getType()) || BlockSet.class.isAssignableFrom(field.getType())))
                .flatMap(field -> {
                    field.trySetAccessible();
                    try {
                        var value = field.get(null);
                        if (value instanceof Type type) {
                            return Stream.of(type);
                        }
                        return Arrays.stream(((BlockSet) value).types());
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(Type[]::new);
        }
        return types;
    }

}
