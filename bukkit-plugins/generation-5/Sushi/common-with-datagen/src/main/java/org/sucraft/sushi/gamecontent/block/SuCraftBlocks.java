package org.sucraft.sushi.gamecontent.block;

import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.Nullable;
import org.sucraft.sushi.datageneration.common.BlockLootTables;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Models;
import org.sucraft.sushi.gamecontent.item.SuCraftItems;
import spout.api.SpoutRegistryKey;
import spout.api.clientview.packetmapping.blockstate.BlockStateMapping;
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
import java.util.function.Function;
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
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall ANDESITE_BRICKS =
        BlockSet.builder("andesite_bricks")
            .vanilla(Blocks.POLISHED_ANDESITE)
            .fullBlock().slab().stairs().wall()
            .wallFallback(Blocks.ANDESITE_WALL)
            .build();
    public static final Type CHISELED_ANDESITE_BRICKS = blockType("chiseled_andesite_bricks")
        .fullBlock(Blocks.POLISHED_ANDESITE);
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall DIORITE_BRICKS =
        BlockSet.builder("diorite_bricks")
            .vanilla(Blocks.POLISHED_DIORITE)
            .fullBlock().slab().stairs().wall()
            .wallFallback(Blocks.DIORITE_WALL)
            .build();
    public static final Type CHISELED_DIORITE_BRICKS = blockType("chiseled_diorite_bricks")
        .fullBlock(Blocks.POLISHED_DIORITE);
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall GRANITE_BRICKS =
        BlockSet.builder("granite_bricks")
            .vanilla(Blocks.POLISHED_GRANITE)
            .fullBlock().slab().stairs().wall()
            .wallFallback(Blocks.GRANITE_WALL)
            .build();
    public static final Type CHISELED_GRANITE_BRICKS = blockType("chiseled_granite_bricks")
        .fullBlock(Blocks.POLISHED_GRANITE);
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
    public static final Type ROUGH_CHISELED_TUFF_BRICKS = blockType("chiseled_tuff_bricks")
        .fullBlock(Blocks.CHISELED_TUFF_BRICKS);
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall POLISHED_CALCITE =
        BlockSet.builder("polished_calcite")
            .vanilla(Blocks.CALCITE)
            .fullBlock().slab().stairs().wall()
            .slabFallback(Blocks.SMOOTH_QUARTZ_SLAB).stairsFallback(Blocks.SMOOTH_QUARTZ_STAIRS).wallFallback(Blocks.DIORITE_WALL)
            .build();
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall CALCITE_BRICKS =
        BlockSet.builder("calcite_bricks")
            .vanilla(Blocks.CALCITE)
            .fullBlock().slab().stairs().wall()
            .slabFallback(Blocks.SMOOTH_QUARTZ_SLAB).stairsFallback(Blocks.SMOOTH_QUARTZ_STAIRS).wallFallback(Blocks.DIORITE_WALL)
            .build();
    public static final Type CHISELED_CALCITE_BRICKS = blockType("chiseled_calcite_bricks")
        .fullBlock(Blocks.CALCITE);
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall POLISHED_DRIPSTONE =
        BlockSet.builder("polished_dripstone")
            .vanilla(Blocks.DRIPSTONE_BLOCK)
            .fullBlock().slab().stairs().wall()
            .slabFallback(Blocks.GRANITE_SLAB).stairsFallback(Blocks.GRANITE_STAIRS).wallFallback(Blocks.GRANITE_WALL)
            .build();
    public static final BlockSet.WithFullBlockAndSlabAndStairsAndWall DRIPSTONE_BRICKS =
        BlockSet.builder("dripstone_bricks")
            .vanilla(Blocks.DRIPSTONE_BLOCK)
            .fullBlock().slab().stairs().wall()
            .slabFallback(Blocks.GRANITE_SLAB).stairsFallback(Blocks.GRANITE_STAIRS).wallFallback(Blocks.GRANITE_WALL)
            .build();
    public static final Type CHISELED_DRIPSTONE_BRICKS = blockType("chiseled_dripstone_bricks")
        .fullBlock(Blocks.DRIPSTONE_BLOCK);

    private static Type blockType(String path) {
        return new Type(path);
    }

    public static final class Type {

        public final NamespacedKey key;
        public final Identifier identifier;
        private SuCraftItems.@Nullable Type item;
        private @Nullable BlockType value;
        private @Nullable Block valueNMS;
        private @Nullable Block valueForDataGeneration;

        private List<Consumer<BlockTypeRegistryEntryBuilderNMS>> blockBuilding = new ArrayList<>();
        private List<Consumer<BlockStateMappingMacroRegistryEntryBuilderNMS>> blockStateMappingMacroBuilding = new ArrayList<>();
        private @Nullable Function<BlockBehaviour.Properties, Block> constructorForDataGeneration = Block::new;
        private List<Consumer<CollectedFactories>> dataGenerating = new ArrayList<>();
        private @Nullable Consumer<Models.CollectedFactory> dataGeneratingModel;
        private @Nullable Consumer<BlockLootTables.CollectedFactory> dataGeneratingBlockLootTable;

        public Type(String path, boolean item) {
            this(Identifier.fromNamespaceAndPath("sucraft", path), item);
        }

        public Type(NamespacedKey key, boolean item) {
            this(Identifier.fromNamespaceAndPath(key.getNamespace(), key.getKey()), item);
        }

        public Type(Identifier identifier, boolean item) {
            this.key = new NamespacedKey(identifier.getNamespace(), identifier.getPath());
            this.identifier = Identifier.fromNamespaceAndPath(key.namespace(), key.value());
            if (item) {
                this.item = new SuCraftItems.Type(key);
                this.item.block(this::getForDataGeneration);
            }
        }

        public Type(String path) {
            this(path, true);
        }

        public Type(NamespacedKey key) {
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

        public Block getForDataGeneration() {
            Block value = this.valueForDataGeneration;
            if (value == null) {
                var key = ResourceKey.create(Registries.BLOCK, this.identifier);
                var properties = BlockBehaviour.Properties.of().setId(key);
                value = net.minecraft.core.Registry.register(
                    BuiltInRegistries.BLOCK,
                    key,
                    this.constructorForDataGeneration.apply(properties)
                );
                this.valueForDataGeneration = value;
            }
            return value;
        }

        public @Nullable ItemType item() {
            return this.item != null ? this.item.get() : null;
        }

        public @Nullable Item itemNMS() {
            return this.item != null ? this.item.getNMS() : null;
        }

        public @Nullable Item itemForDataGeneration() {
            return this.item != null ? this.item.getForDataGeneration() : null;
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
            return this.blockBuild(BlockTypeRegistryEntryBuilderNMS::inheritsFromSlab);
        }

        public Type stairsFactory(Supplier<BlockState> baseState) {
            return this.blockBuild(builder -> builder.factoryNMS(properties -> new StairBlock(baseState.get(), properties)));
        }

        public Type wallFactory() {
            return this.blockBuild(builder -> builder.factoryNMS(WallBlock::new));
        }

        public Type properties(Supplier<BlockBehaviour.Properties> properties) {
            return this.blockBuild(builder -> builder.propertiesNMS(properties.get()));
        }

        public Type properties(Consumer<BlockBehaviour.Properties> properties) {
            return this.blockBuild(builder -> builder.propertiesNMS(properties));
        }

        public Type propertiesLegacy(Block block) {
            return this.properties(() -> BlockBehaviour.Properties.ofLegacyCopy(block));
        }

        public Type propertiesFull(Block block) {
            return this.properties(() -> BlockBehaviour.Properties.ofFullCopy(block));
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

        public Type constructorForDataGeneration(Function<BlockBehaviour.Properties, Block> constructor) {
            this.constructorForDataGeneration = constructor;
            return this;
        }

        public Type blockConstructorForDataGeneration() {
            return this.constructorForDataGeneration(Block::new);
        }

        public Type slabConstructorForDataGeneration() {
            return this.constructorForDataGeneration(SlabBlock::new);
        }

        public Type stairsConstructorForDataGeneration(Supplier<BlockState> base) {
            return this.constructorForDataGeneration(properties -> new StairBlock(base.get(), properties));
        }

        public Type wallConstructorForDataGeneration() {
            return this.constructorForDataGeneration(WallBlock::new);
        }

        public void applyDataGeneration(CollectedFactories factories) {
            this.dataGenerating.forEach(it -> it.accept(factories));
            this.dataGenerating = null;
            if (this.dataGeneratingModel != null) {
                this.dataGeneratingModel.accept(factories.models());
                this.dataGeneratingModel = null;
            }
            if (this.dataGeneratingBlockLootTable != null) {
                this.dataGeneratingBlockLootTable.accept(factories.blockLootTables());
                this.dataGeneratingBlockLootTable = null;
            }
        }

        public Type dataGenerate(Consumer<CollectedFactories> factoriesConsumer) {
            this.dataGenerating.add(factoriesConsumer);
            return this;
        }

        public Type dataGenerateModel(@Nullable Consumer<Models.CollectedFactory> factoryConsumer) {
            this.dataGeneratingModel = factoryConsumer;
            return this;
        }

        public Type dataGenerateModelForBlock(Models.BlockStateModelGenerator generatorsConsumer) {
            return this.dataGenerateModel(factory -> factory.addBlock(generatorsConsumer));
        }

        public Type dataGenerateBlockLootTable(@Nullable Consumer<BlockLootTables.CollectedFactory> factoryConsumer) {
            this.dataGeneratingBlockLootTable = factoryConsumer;
            return this;
        }

        public Type dataGenerateBlockLootTableForDropSelf() {
            return this.dataGenerateBlockLootTable(factory -> factory.add(provider -> provider.dropSelf(this.getForDataGeneration())));
        }

        public Type dataGenerateBlockLootTableForDropSelfSlab() {
            return this.dataGenerateBlockLootTable(factory -> factory.add(provider -> provider.add(this.getForDataGeneration(), provider::createSlabItemTable)));
        }

        public Type fullBlock(Block propertiesFrom, Block fallback) {
            this.propertiesFull(propertiesFrom);
            this.fromToBlockMacro(BlockStateMappingMacroTypes.FULL_BLOCK, fallback);
            this.blockConstructorForDataGeneration();
            this.dataGenerateModel(factory -> factory.addBlockTrivialCube(this.getForDataGeneration()));
            return this.dataGenerateBlockLootTableForDropSelf();
        }

        public Type fullBlock(Block propertiesFromAndFallback) {
            return this.fullBlock(propertiesFromAndFallback, propertiesFromAndFallback);
        }

        public Type slab(Block propertiesFrom, Block fallback, @Nullable BlockState fallbackBlockState) {
            this.slabFactory();
            this.propertiesFull(propertiesFrom);
            this.slabMacro(BlockStateMappingMacroTypes.SLAB, fallback, fallbackBlockState);
            this.slabConstructorForDataGeneration();
            this.dataGenerateModel(factory -> factory.addBlockSlab(this.getForDataGeneration()));
            return this.dataGenerateBlockLootTableForDropSelfSlab();
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
            this.fromToBlockMacro(BlockStateMappingMacroTypes.STAIRS, fallback);
            this.stairsConstructorForDataGeneration(base);
            this.dataGenerateModel(factory -> factory.addBlockStairs(this.getForDataGeneration()));
            return this.dataGenerateBlockLootTableForDropSelf();
        }

        public Type stairs(Supplier<Block> base, Block propertiesFromAndFallback) {
            return this.stairs(() -> base.get().defaultBlockState(), propertiesFromAndFallback, propertiesFromAndFallback);
        }

        public Type wall(Block propertiesFrom, Block fallback) {
            this.wallFactory();
            this.propertiesFull(propertiesFrom);
            this.fromToBlockMacro(BlockStateMappingMacroTypes.WALL, fallback);
            this.wallConstructorForDataGeneration();
            this.dataGenerateModel(factory -> factory.addBlockWall(this.getForDataGeneration()));
            return this.dataGenerateBlockLootTableForDropSelf();
        }

        public Type wall(Block propertiesFromAndFallback) {
            return this.wall(propertiesFromAndFallback, propertiesFromAndFallback);
        }

    }

    private static BlockType[] values;
    private static Block[] valuesNMS;
    private static Block[] valuesForDataGeneration;
    private static BlockSet[] blockSets;
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

    public static Block[] valuesForDataGeneration() {
        if (valuesForDataGeneration == null) {
            valuesForDataGeneration = Arrays.stream(types())
                .map(SuCraftBlocks.Type::getForDataGeneration).toArray(Block[]::new);
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

    public static Type[] types() {
        if (types == null) {
            Stream<Type> declared = Arrays.stream(SuCraftBlocks.class.getDeclaredFields())
                .filter(field ->
                    Modifier.isPublic(field.getModifiers()) &&
                        Modifier.isStatic(field.getModifiers()) &&
                        Modifier.isFinal(field.getModifiers()) &&
                        Type.class.isAssignableFrom(field.getType()))
                .map(field -> {
                    field.trySetAccessible();
                    try {
                        return (Type) field.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
            Stream<Type> fromBlockSets = Arrays.stream(blockSets())
                .map(BlockSet::types).flatMap(Arrays::stream);
            types = Stream.concat(declared, fromBlockSets).toArray(Type[]::new);
        }
        return types;
    }

}
