package org.sucraft.sushi.gamecontent.block;

import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.Nullable;
import org.sucraft.sushi.datageneration.common.BlockLootTables;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Models;
import org.sucraft.sushi.gamecontent.item.SuCraftItemType;
import spout.api.SpoutRegistryKey;
import spout.api.clientview.packetmapping.blockstate.BlockStateMapping;
import spout.api.clientview.packetmapping.blockstate.macro.registry.BlockStateMappingMacroRegistryEntryBuilderNMS;
import spout.api.gamecontent.datadriven.block.registry.BlockTypeRegistryEntryBuilderNMS;
import spout.clientview.packetmapping.blockstate.macro.BlockStateMappingMacro;
import spout.clientview.packetmapping.blockstate.macro.FromToBlockMacro;
import spout.clientview.packetmapping.blockstate.macro.SlabMacro;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroType;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public final class SuCraftBlockType implements ItemLike {

    public final NamespacedKey key;
    public final Identifier identifier;
    private @Nullable SuCraftItemType item;
    private @Nullable BlockType value;
    private @Nullable Block valueNMS;
    private @Nullable Block valueForDataGeneration;

    private List<Consumer<BlockTypeRegistryEntryBuilderNMS>> blockBuilding = new ArrayList<>();
    private List<Consumer<BlockStateMappingMacroRegistryEntryBuilderNMS>> blockStateMappingMacroBuilding = new ArrayList<>();
    private @Nullable Function<BlockBehaviour.Properties, Block> constructorForDataGeneration = Block::new;
    private List<Consumer<CollectedFactories>> dataGenerating = new ArrayList<>();
    private @Nullable Consumer<Models.CollectedFactory> dataGeneratingModel;
    private @Nullable Consumer<BlockLootTables.CollectedFactory> dataGeneratingBlockLootTable;

    public SuCraftBlockType(String path, boolean item) {
        this(Identifier.fromNamespaceAndPath("sucraft", path), item);
    }

    public SuCraftBlockType(NamespacedKey key, boolean item) {
        this(Identifier.fromNamespaceAndPath(key.getNamespace(), key.getKey()), item);
    }

    public SuCraftBlockType(Identifier identifier, boolean item) {
        this.key = new NamespacedKey(identifier.getNamespace(), identifier.getPath());
        this.identifier = Identifier.fromNamespaceAndPath(key.namespace(), key.value());
        if (item) {
            this.item = new SuCraftItemType(key);
            this.item.block(this::getForDataGeneration);
        }
    }

    public SuCraftBlockType(String path) {
        this(path, true);
    }

    public SuCraftBlockType(NamespacedKey key) {
        this(key, true);
    }

    public SuCraftBlockType(Identifier identifier) {
        this(identifier, true);
    }

    @Override
    public Item asItem() {
        return Objects.requireNonNull(this.itemNMS());
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

    public @Nullable SuCraftItemType itemType() {
        return this.item;
    }

    public void applyToBlockBuilder(BlockTypeRegistryEntryBuilderNMS builder) {
        this.blockBuilding.forEach(it -> it.accept(builder));
        this.blockBuilding = null;
    }

    public SuCraftBlockType update(@Nullable Consumer<SuCraftBlockType> updater) {
        if (updater != null) updater.accept(this);
        return this;
    }

    public SuCraftBlockType blockBuild(Consumer<BlockTypeRegistryEntryBuilderNMS> builderConsumer) {
        this.blockBuilding.add(builderConsumer);
        return this;
    }

    public SuCraftBlockType slabFactory() {
        return this.blockBuild(BlockTypeRegistryEntryBuilderNMS::inheritsFromSlab);
    }

    public SuCraftBlockType stairsFactory(Supplier<BlockState> baseState) {
        return this.blockBuild(builder -> builder.factoryNMS(properties -> new StairBlock(baseState.get(), properties)));
    }

    public SuCraftBlockType wallFactory() {
        return this.blockBuild(builder -> builder.factoryNMS(WallBlock::new));
    }

    public SuCraftBlockType fenceFactory() {
        return this.blockBuild(builder -> builder.factoryNMS(FenceBlock::new));
    }

    public SuCraftBlockType fenceGateFactory(Supplier<WoodType> woodType) {
        return this.blockBuild(builder -> builder.factoryNMS(properties -> new FenceGateBlock(woodType.get(), properties)));
    }

    public SuCraftBlockType buttonFactory(Supplier<BlockSetType> blockSetType, IntSupplier ticksToStayPressed) {
        return this.blockBuild(builder -> builder.factoryNMS(properties -> new ButtonBlock(blockSetType.get(), ticksToStayPressed.getAsInt(), properties)));
    }

    public SuCraftBlockType pressurePlateFactory(Supplier<BlockSetType> blockSetType) {
        return this.blockBuild(builder -> builder.factoryNMS(properties -> new PressurePlateBlock(blockSetType.get(), properties)));
    }

    public SuCraftBlockType properties(Supplier<BlockBehaviour.Properties> properties) {
        return this.blockBuild(builder -> builder.propertiesNMS(properties.get()));
    }

    public SuCraftBlockType properties(Consumer<BlockBehaviour.Properties> properties) {
        return this.blockBuild(builder -> builder.propertiesNMS(properties));
    }

    public SuCraftBlockType propertiesLegacy(Block block) {
        return this.properties(() -> BlockBehaviour.Properties.ofLegacyCopy(block));
    }

    public SuCraftBlockType propertiesFull(Block block) {
        return this.properties(() -> BlockBehaviour.Properties.ofFullCopy(block));
    }

    public void applyToBlockStateMappingMacroBuilder(BlockStateMappingMacroRegistryEntryBuilderNMS builder) {
        this.blockStateMappingMacroBuilding.forEach(it -> it.accept(builder));
        this.blockStateMappingMacroBuilding = null;
    }

    public SuCraftBlockType blockStateMappingMacroBuild(Consumer<BlockStateMappingMacroRegistryEntryBuilderNMS> builderConsumer) {
        this.blockStateMappingMacroBuilding.add(builderConsumer);
        return this;
    }

    public SuCraftBlockType macro(Supplier<BlockStateMappingMacro> macro) {
        return this.blockStateMappingMacroBuild(builder -> builder.set(macro.get()));
    }

    public SuCraftBlockType fromToBlockMacro(BlockStateMappingMacroType type, Block fallback) {
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
            SuCraftBlockType.this.getNMS(),
            fallback
        ));
    }

    public SuCraftBlockType slabMacro(BlockStateMappingMacroType type, Block fallback, @Nullable BlockState fullBlockFallbackBlockState) {
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
            SuCraftBlockType.this.getNMS(),
            fallback,
            fullBlockFallbackBlockState
        ));
    }

    public SuCraftBlockType constructorForDataGeneration(Function<BlockBehaviour.Properties, Block> constructor) {
        this.constructorForDataGeneration = constructor;
        return this;
    }

    public SuCraftBlockType blockConstructorForDataGeneration() {
        return this.constructorForDataGeneration(Block::new);
    }

    public SuCraftBlockType slabConstructorForDataGeneration() {
        return this.constructorForDataGeneration(SlabBlock::new);
    }

    public SuCraftBlockType stairsConstructorForDataGeneration(Supplier<BlockState> base) {
        return this.constructorForDataGeneration(properties -> new StairBlock(base.get(), properties));
    }

    public SuCraftBlockType wallConstructorForDataGeneration() {
        return this.constructorForDataGeneration(WallBlock::new);
    }

    public SuCraftBlockType fenceConstructorForDataGeneration() {
        return this.constructorForDataGeneration(FenceBlock::new);
    }

    public SuCraftBlockType fenceGateConstructorForDataGeneration() {
        return this.constructorForDataGeneration(properties -> new FenceGateBlock(WoodType.OAK, properties));
    }

    public SuCraftBlockType buttonConstructorForDataGeneration() {
        return this.constructorForDataGeneration(properties -> new ButtonBlock(BlockSetType.STONE, 1, properties));
    }

    public SuCraftBlockType pressurePlateConstructorForDataGeneration() {
        return this.constructorForDataGeneration(properties -> new PressurePlateBlock(BlockSetType.STONE, properties));
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

    public SuCraftBlockType dataGenerate(Consumer<CollectedFactories> factoriesConsumer) {
        this.dataGenerating.add(factoriesConsumer);
        return this;
    }

    public SuCraftBlockType dataGenerateModel(@Nullable Consumer<Models.CollectedFactory> factoryConsumer) {
        this.dataGeneratingModel = factoryConsumer;
        return this;
    }

    public SuCraftBlockType dataGenerateModel(@Nullable BiConsumer<Models.CollectedFactory, Block> factoryAndBlockConsumer) {
        return this.dataGenerateModel(factory -> factoryAndBlockConsumer.accept(factory, this.getForDataGeneration()));
    }

    public SuCraftBlockType dataGenerateModelForBlock(Models.BlockStateModelGenerator generatorsConsumer) {
        return this.dataGenerateModel(factory -> factory.addBlock(generatorsConsumer));
    }

    public SuCraftBlockType dataGenerateBlockLootTable(@Nullable Consumer<BlockLootTables.CollectedFactory> factoryConsumer) {
        this.dataGeneratingBlockLootTable = factoryConsumer;
        return this;
    }

    public SuCraftBlockType dataGenerateBlockLootTableForDropSelf() {
        return this.dataGenerateBlockLootTable(factory -> factory.add(provider -> provider.dropSelf(this.getForDataGeneration())));
    }

    public SuCraftBlockType dataGenerateBlockLootTableForDropSelfSlab() {
        return this.dataGenerateBlockLootTable(factory -> factory.add(provider -> provider.add(this.getForDataGeneration(), provider::createSlabItemTable)));
    }

    public SuCraftBlockType dataGenerateBlockLootTableForBookshelf() {
        return this.dataGenerateBlockLootTable(factory -> factory.add(provider -> provider.dropSelf(this.getForDataGeneration())));
    }

    public SuCraftBlockType fullBlock(Block propertiesFrom, Block fallback) {
        this.propertiesFull(propertiesFrom);
        this.fromToBlockMacro(BlockStateMappingMacroTypes.FULL_BLOCK, fallback);
        this.blockConstructorForDataGeneration();
        this.dataGenerateModel(factory -> factory.addBlockTrivialCube(this.getForDataGeneration()));
        return this.dataGenerateBlockLootTableForDropSelf();
    }

    public SuCraftBlockType fullBlock(Block propertiesFromAndFallback) {
        return this.fullBlock(propertiesFromAndFallback, propertiesFromAndFallback);
    }

    public SuCraftBlockType slab(Block propertiesFrom, Block fallback, @Nullable BlockState fallbackBlockState) {
        this.slabFactory();
        this.propertiesFull(propertiesFrom);
        this.slabMacro(BlockStateMappingMacroTypes.SLAB, fallback, fallbackBlockState);
        this.slabConstructorForDataGeneration();
        this.dataGenerateModel(factory -> factory.addBlockSlab(this.getForDataGeneration()));
        return this.dataGenerateBlockLootTableForDropSelfSlab();
    }

    public SuCraftBlockType slab(Block propertiesFromAndFallback, @Nullable BlockState fallbackBlockState) {
        return this.slab(propertiesFromAndFallback, propertiesFromAndFallback, fallbackBlockState);
    }

    public SuCraftBlockType slab(Block propertiesFromAndFallback, Block fallbackBlock) {
        return this.slab(propertiesFromAndFallback, propertiesFromAndFallback, fallbackBlock.defaultBlockState());
    }

    public SuCraftBlockType slab(Block propertiesFromAndFallback) {
        return this.slab(propertiesFromAndFallback, propertiesFromAndFallback, null);
    }

    public SuCraftBlockType stairs(Supplier<BlockState> base, Block propertiesFrom, Block fallback) {
        this.stairsFactory(base);
        this.propertiesFull(propertiesFrom);
        this.fromToBlockMacro(BlockStateMappingMacroTypes.STAIRS, fallback);
        this.stairsConstructorForDataGeneration(base);
        this.dataGenerateModel(factory -> factory.addBlockStairs(this.getForDataGeneration()));
        return this.dataGenerateBlockLootTableForDropSelf();
    }

    public SuCraftBlockType stairs(Supplier<Block> base, Block propertiesFromAndFallback) {
        return this.stairs(() -> base.get().defaultBlockState(), propertiesFromAndFallback, propertiesFromAndFallback);
    }

    public SuCraftBlockType wall(Block propertiesFrom, Block fallback) {
        this.wallFactory();
        this.propertiesFull(propertiesFrom);
        this.fromToBlockMacro(BlockStateMappingMacroTypes.WALL, fallback);
        this.wallConstructorForDataGeneration();
        this.dataGenerateModel(factory -> factory.addBlockWall(this.getForDataGeneration()));
        return this.dataGenerateBlockLootTableForDropSelf();
    }

    public SuCraftBlockType wall(Block propertiesFromAndFallback) {
        return this.wall(propertiesFromAndFallback, propertiesFromAndFallback);
    }

    public SuCraftBlockType fence(Block propertiesFrom, Block fallback) {
        this.fenceFactory();
        this.propertiesFull(propertiesFrom);
        this.fromToBlockMacro(BlockStateMappingMacroTypes.FENCE, fallback);
        this.fenceConstructorForDataGeneration();
        this.dataGenerateModel(factory -> factory.addBlockFence(this.getForDataGeneration()));
        return this.dataGenerateBlockLootTableForDropSelf();
    }

    public SuCraftBlockType fence(Block propertiesFromAndFallback) {
        return this.fence(propertiesFromAndFallback, propertiesFromAndFallback);
    }

    public SuCraftBlockType fenceGate(Supplier<WoodType> woodType, Block propertiesFrom, Block fallback) {
        this.fenceGateFactory(woodType);
        this.propertiesFull(propertiesFrom);
        this.fromToBlockMacro(BlockStateMappingMacroTypes.FENCE_GATE, fallback);
        this.fenceGateConstructorForDataGeneration();
        this.dataGenerateModel(factory -> factory.addBlockFenceGate(this.getForDataGeneration()));
        return this.dataGenerateBlockLootTableForDropSelf();
    }

    public SuCraftBlockType fenceGate(Supplier<WoodType> woodType, Block propertiesFromAndFallback) {
        return this.fenceGate(woodType, propertiesFromAndFallback, propertiesFromAndFallback);
    }

    public SuCraftBlockType button(Supplier<BlockSetType> blockSetType, IntSupplier ticksToStayPressed, Block propertiesFrom, Block fallback) {
        this.buttonFactory(blockSetType, ticksToStayPressed);
        this.propertiesFull(propertiesFrom);
        this.fromToBlockMacro(BlockStateMappingMacroTypes.BUTTON, fallback);
        this.buttonConstructorForDataGeneration();
        this.dataGenerateModel(factory -> factory.addBlockButton(this.getForDataGeneration()));
        return this.dataGenerateBlockLootTableForDropSelf();
    }

    public SuCraftBlockType button(Supplier<BlockSetType> blockSetType, IntSupplier ticksToStayPressed, Block propertiesFromAndFallback) {
        return this.button(blockSetType, ticksToStayPressed, propertiesFromAndFallback, propertiesFromAndFallback);
    }

    public SuCraftBlockType pressurePlate(Supplier<BlockSetType> blockSetType, Block propertiesFrom, Block fallback) {
        this.pressurePlateFactory(blockSetType);
        this.propertiesFull(propertiesFrom);
        this.fromToBlockMacro(BlockStateMappingMacroTypes.PRESSURE_PLATE, fallback);
        this.pressurePlateConstructorForDataGeneration();
        this.dataGenerateModel(factory -> factory.addBlockPressurePlate(this.getForDataGeneration()));
        return this.dataGenerateBlockLootTableForDropSelf();
    }

    public SuCraftBlockType pressurePlate(Supplier<BlockSetType> blockSetType, Block propertiesFromAndFallback) {
        return this.pressurePlate(blockSetType, propertiesFromAndFallback, propertiesFromAndFallback);
    }

    public static @Nullable WoodType deriveWoodType(Block block) {
        if (block instanceof FenceGateBlock fenceGate) {
            return fenceGate.type;
        }
        return null;
    }

    public static @Nullable BlockSetType deriveBlockSetType(Block block) {
        if (block instanceof ButtonBlock button) {
            return button.type;
        }
        if (block instanceof BasePressurePlateBlock pressurePlate) {
            return pressurePlate.type;
        }
        return null;
    }

}
