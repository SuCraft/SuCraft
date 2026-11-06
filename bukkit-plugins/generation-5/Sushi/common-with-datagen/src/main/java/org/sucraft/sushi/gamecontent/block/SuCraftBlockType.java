package org.sucraft.sushi.gamecontent.block;

import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockItemTagId;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.LadderBlock;import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ResolvableInt;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.Nullable;
import org.sucraft.sushi.datageneration.common.BlockLootTables;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.DataGenerationFlag;
import org.sucraft.sushi.datageneration.common.Models;
import org.sucraft.sushi.gamecontent.common.CBlockItemTags;
import org.sucraft.sushi.gamecontent.common.SpoutBlockItemTags;import org.sucraft.sushi.gamecontent.item.SuCraftItemType;
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

    public final boolean definedByCode;
    private List<Consumer<BlockTypeRegistryEntryBuilderNMS>> blockBuilding = new ArrayList<>();
    public @Nullable BiConsumer<SuCraftBlockType, Block> postConstruction;
    private List<Consumer<BlockStateMappingMacroRegistryEntryBuilderNMS>> blockStateMappingMacroBuilding = new ArrayList<>();
    private @Nullable Function<BlockBehaviour.Properties, Block> constructorForDataGeneration = Block::new;
    private List<Consumer<CollectedFactories>> dataGenerating = new ArrayList<>();
    private @Nullable Consumer<Models.CollectedFactory> dataGeneratingModel;
    private @Nullable Consumer<BlockLootTables.CollectedFactory> dataGeneratingBlockLootTable;
    private @Nullable List<TagKey<Block>> dataGeneratingTags;
    private boolean tryAutomaticFullBlockTags;
    private boolean skipAutomaticBookshelfTag;
    private boolean skipAutomaticBlocksMotionNoLeavesTag;
    private boolean tryAutomaticSlabTags;
    private boolean skipAutomaticSlabTag;
    private boolean tryAutomaticStairsTags;
    private boolean skipAutomaticStairsTag;
    private boolean tryAutomaticWallTags;
    private boolean skipAutomaticWallTag;
    private boolean tryAutomaticFenceTags;
    private boolean skipAutomaticFenceTag;
    private boolean tryAutomaticFenceGateTags;
    private boolean skipAutomaticFenceGateTag;
    private boolean tryAutomaticButtonTags;
    private boolean skipAutomaticButtonTag;
    private boolean tryAutomaticPressurePlateTags;
    private boolean skipAutomaticPressurePlateTag;

    private SuCraftBlockType(Identifier identifier, boolean item, boolean definedByCode, boolean itemDefinedByCode) {
        this.key = new NamespacedKey(identifier.getNamespace(), identifier.getPath());
        this.identifier = Identifier.fromNamespaceAndPath(key.namespace(), key.value());
        if (item) {
            if (itemDefinedByCode) {
                this.item = SuCraftItemType.create(identifier);
                this.item.block(this::getNMS);
            } else {
                this.item = SuCraftItemType.createNotDefinedByCode(identifier);
            }
        }
        this.definedByCode = definedByCode;
    }

    public static SuCraftBlockType create(String path) {
        return create(Identifier.fromNamespaceAndPath("sucraft", path));
    }

    public static SuCraftBlockType create(NamespacedKey key) {
        return create(Identifier.fromNamespaceAndPath(key.getNamespace(), key.getKey()));
    }

    public static SuCraftBlockType create(Identifier identifier) {
        return new SuCraftBlockType(identifier, true, true, true);
    }

    public static SuCraftBlockType createWithoutItem(String path) {
        return createWithoutItem(Identifier.fromNamespaceAndPath("sucraft", path));
    }

    public static SuCraftBlockType createWithoutItem(NamespacedKey key) {
        return createWithoutItem(Identifier.fromNamespaceAndPath(key.getNamespace(), key.getKey()));
    }

    public static SuCraftBlockType createWithoutItem(Identifier identifier) {
        return new SuCraftBlockType(identifier, false, true, true);
    }

    public static SuCraftBlockType createNotDefinedByCode(String path) {
        return createNotDefinedByCode(path, false);
    }

    public static SuCraftBlockType createNotDefinedByCode(NamespacedKey key) {
        return createNotDefinedByCode(key, false);
    }

    public static SuCraftBlockType createNotDefinedByCode(Identifier identifier) {
        return createNotDefinedByCode(identifier, false);
    }

    public static SuCraftBlockType createNotDefinedByCode(String path, boolean itemDefinedByCode) {
        return createNotDefinedByCode(Identifier.fromNamespaceAndPath("sucraft", path), itemDefinedByCode);
    }

    public static SuCraftBlockType createNotDefinedByCode(NamespacedKey key, boolean itemDefinedByCode) {
        return createNotDefinedByCode(Identifier.fromNamespaceAndPath(key.getNamespace(), key.getKey()), itemDefinedByCode);
    }

    public static SuCraftBlockType createNotDefinedByCode(Identifier identifier, boolean itemDefinedByCode) {
        return new SuCraftBlockType(identifier, true, false, itemDefinedByCode);
    }

    public static SuCraftBlockType createNotDefinedByCodeWithoutItem(String path) {
        return createNotDefinedByCode(Identifier.fromNamespaceAndPath("sucraft", path));
    }

    public static SuCraftBlockType createNotDefinedByCodeWithoutItem(NamespacedKey key) {
        return createNotDefinedByCode(Identifier.fromNamespaceAndPath(key.getNamespace(), key.getKey()));
    }

    public static SuCraftBlockType createNotDefinedByCodeWithoutItem(Identifier identifier) {
        return new SuCraftBlockType(identifier, false, false, false);
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
            if (DataGenerationFlag.isDataGeneration && this.definedByCode) {
                var key = ResourceKey.create(Registries.BLOCK, this.identifier);
                var properties = BlockBehaviour.Properties.of().setId(key);
                value = net.minecraft.core.Registry.register(
                    BuiltInRegistries.BLOCK,
                    key,
                    this.constructorForDataGeneration.apply(properties)
                );
            } else {
                value = BuiltInRegistries.BLOCK.getValue(this.identifier);
            }
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

    public @Nullable SuCraftItemType itemType() {
        return this.item;
    }

    public void applyToBlockBuilder(BlockTypeRegistryEntryBuilderNMS builder) {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.blockBuilding.forEach(it -> it.accept(builder));
        this.blockBuilding = null;
    }

    public void applyDataGeneration(CollectedFactories factories) {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
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
        if (this.dataGeneratingTags != null) {
            for (var tag : this.dataGeneratingTags) {
                factories.blockTags().add(tag, this.getNMS());
            }
            this.dataGeneratingTags = null;
        }
        if (this.tryAutomaticFullBlockTags) {
            if (!this.skipAutomaticBookshelfTag) {
                if (this.key.getKey().contains("bookshelf")) {
                    factories.blockTags().add(CBlockItemTags.BOOKSHELVES.block(), this.getNMS());
                    if (this.item != null) {
                        this.item.dataGenerateTag(CBlockItemTags.BOOKSHELVES);
                    }
                }
            }
            if (!this.skipAutomaticBlocksMotionNoLeavesTag) {
                Block block = this.getNMS();
                if (!(block instanceof LeavesBlock)) {
                    factories.blockTags().add(BlockTags.BLOCKS_MOTION_NO_LEAVES, block);
                }
            }
        }
        if (this.tryAutomaticSlabTags) {
            if (!this.skipAutomaticSlabTag) {
                factories.blockTags().add(BlockTags.SLABS, this.getNMS());
                if (this.item != null) {
                    this.item.dataGenerateTag(BlockItemTags.SLABS);
                }
            }
        }
        if (this.tryAutomaticStairsTags) {
            if (!this.skipAutomaticStairsTag) {
                factories.blockTags().add(BlockTags.STAIRS, this.getNMS());
                if (this.item != null) {
                    this.item.dataGenerateTag(BlockItemTags.STAIRS);
                }
            }
        }
        if (this.tryAutomaticWallTags) {
            if (!this.skipAutomaticWallTag) {
                factories.blockTags().add(BlockTags.WALLS, this.getNMS());
                if (this.item != null) {
                    this.item.dataGenerateTag(BlockItemTags.WALLS);
                }
            }
        }
        if (this.tryAutomaticFenceTags) {
            if (!this.skipAutomaticFenceTag) {
                factories.blockTags().add(BlockTags.FENCES, this.getNMS());
                if (this.item != null) {
                    this.item.dataGenerateTag(BlockItemTags.FENCES);
                }
            }
        }
        if (this.tryAutomaticFenceGateTags) {
            if (!this.skipAutomaticFenceGateTag) {
                factories.blockTags().add(BlockTags.FENCE_GATES, this.getNMS());
                if (this.item != null) {
                    this.item.dataGenerateTag(BlockItemTags.FENCE_GATES);
                }
            }
        }
        if (this.tryAutomaticButtonTags) {
            if (!this.skipAutomaticButtonTag) {
                factories.blockTags().add(BlockTags.BUTTONS, this.getNMS());
                if (this.item != null) {
                    this.item.dataGenerateTag(BlockItemTags.BUTTONS);
                }
            }
        }
        if (this.tryAutomaticPressurePlateTags) {
            if (!this.skipAutomaticPressurePlateTag) {
                factories.blockTags().add(BlockTags.PRESSURE_PLATES, this.getNMS());
            }
        }
    }

    public SuCraftBlockType update(@Nullable Consumer<SuCraftBlockType> updater) {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        if (updater != null) updater.accept(this);
        return this;
    }

    public SuCraftBlockType update(@Nullable List<Consumer<SuCraftBlockType>> updater) {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        if (updater != null) updater.forEach(consumer -> consumer.accept(this));
        return this;
    }

    public SuCraftBlockType updateItem(@Nullable Consumer<SuCraftItemType> updater) {
        if (this.item != null) this.item.update(updater);
        return this;
    }

    public SuCraftBlockType updateItem(@Nullable List<Consumer<SuCraftItemType>> updater) {
        if (this.item != null) this.item.update(updater);
        return this;
    }

    public SuCraftBlockType blockBuild(Consumer<BlockTypeRegistryEntryBuilderNMS> builderConsumer) {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
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

    public SuCraftBlockType postConstruction(@Nullable BiConsumer<SuCraftBlockType, Block> postConstruction) {
        this.postConstruction = postConstruction;
        return this;
    }

    public SuCraftBlockType flammable(int igniteOdds, int burnOdds) {
        return this.postConstruction((_, block) -> ((FireBlock) Blocks.FIRE).setFlammable(block, igniteOdds, burnOdds));
    }

    public SuCraftBlockType flammableLikeBookshelf() {
        return this.flammable(30, 20);
    }

    public SuCraftBlockType flammableLikeLog() {
        return this.flammable(5, 5);
    }

    public SuCraftBlockType flammableLikePlanks() {
        return this.flammable(5, 20);
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
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.blockStateMappingMacroBuilding.forEach(it -> it.accept(builder));
        this.blockStateMappingMacroBuilding = null;
    }

    public SuCraftBlockType blockStateMappingMacroBuild(Consumer<BlockStateMappingMacroRegistryEntryBuilderNMS> builderConsumer) {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
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
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
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

    public SuCraftBlockType dataGenerate(Consumer<CollectedFactories> factoriesConsumer) {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.dataGenerating.add(factoriesConsumer);
        return this;
    }

    public SuCraftBlockType dataGenerateModel(@Nullable Consumer<Models.CollectedFactory> factoryConsumer) {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.dataGeneratingModel = factoryConsumer;
        return this;
    }

    public SuCraftBlockType dataGenerateModel(@Nullable BiConsumer<Models.CollectedFactory, Block> factoryAndBlockConsumer) {
        return this.dataGenerateModel(factory -> factoryAndBlockConsumer.accept(factory, this.getNMS()));
    }

    public SuCraftBlockType dataGenerateModelForBlock(Models.BlockStateModelGenerator generatorsConsumer) {
        return this.dataGenerateModel(factory -> factory.addBlock(generatorsConsumer));
    }

    public SuCraftBlockType dataGenerateBlockLootTable(@Nullable Consumer<BlockLootTables.CollectedFactory> factoryConsumer) {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.dataGeneratingBlockLootTable = factoryConsumer;
        return this;
    }

    public SuCraftBlockType dataGenerateBlockLootTableForDropSelf() {
        return this.dataGenerateBlockLootTable(factory -> factory.add(provider -> provider.dropSelf(this.getNMS())));
    }

    public SuCraftBlockType dataGenerateBlockLootTableForDropSelfSlab() {
        return this.dataGenerateBlockLootTable(factory -> factory.add(provider -> provider.add(this.getNMS(), provider::createSlabItemTable)));
    }

    public SuCraftBlockType dataGenerateTagUnsafe(Object... tags) {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        if (tags.length != 0) {
            for (var tag : tags) {
                if (tag instanceof TagKey tagKey) {
                    if (((TagKey<?>) tag).registry().equals(Registries.BLOCK)) {
                        this.dataGenerateTag(tagKey);
                    } else if (((TagKey<?>) tag).registry().equals(Registries.ITEM)) {
                        this.dataGenerateItemTag(tagKey);
                    }
                } else if (tag instanceof BlockItemTagId tagId) {
                    this.dataGenerateTag(tagId);
                }
            }
        }
        return this;
    }

    public SuCraftBlockType dataGenerateTag(TagKey<Block>... tags) {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        if (tags.length != 0) {
            if (this.dataGeneratingTags == null) {
                this.dataGeneratingTags = new ArrayList<>(1);
            }
            for (var tag : tags) {
                this.dataGeneratingTags.add(tag);
            }
        }
        return this;
    }

    public SuCraftBlockType dataGenerateItemTag(TagKey<Item>... tags) {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        if (tags.length != 0 && this.item != null) {
            for (var tag : tags) {
                this.item.dataGenerateTag(tag);
            }
        }
        return this;
    }

    public SuCraftBlockType dataGenerateTag(BlockItemTagId... tags) {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        if (tags.length != 0) {
            for (var tag : tags) {
                this.dataGenerateTag(tag.block());
                if (this.item != null) {
                    this.item.dataGenerateTag(tag.item());
                }
            }
        }
        return this;
    }

    public SuCraftBlockType mineableWithAxe() {
        return this.dataGenerateTag(BlockTags.MINEABLE_WITH_AXE);
    }

    public SuCraftBlockType mineableWithHoe() {
        return this.dataGenerateTag(BlockTags.MINEABLE_WITH_HOE);
    }

    public SuCraftBlockType mineableWithPickaxe() {
        return this.dataGenerateTag(BlockTags.MINEABLE_WITH_PICKAXE);
    }

    public SuCraftBlockType mineableWithShovel() {
        return this.dataGenerateTag(BlockTags.MINEABLE_WITH_SHOVEL);
    }

    public SuCraftBlockType tryAutomaticFullBlockTags() {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.tryAutomaticFullBlockTags = true;
        return this;
    }

    public SuCraftBlockType skipAutomaticBookshelfTag() {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.skipAutomaticBookshelfTag = true;
        return this;
    }

    public SuCraftBlockType skipAutomaticBlocksMotionNoLeavesTag() {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.skipAutomaticBlocksMotionNoLeavesTag = true;
        return this;
    }

    public SuCraftBlockType tryAutomaticSlabTags() {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.tryAutomaticSlabTags = true;
        return this;
    }

    public SuCraftBlockType skipAutomaticSlabTag() {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.skipAutomaticSlabTag = true;
        return this;
    }

    public SuCraftBlockType tryAutomaticStairsTags() {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.tryAutomaticStairsTags = true;
        return this;
    }

    public SuCraftBlockType skipAutomaticStairsTag() {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.skipAutomaticStairsTag = true;
        return this;
    }

    public SuCraftBlockType tryAutomaticWallTags() {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.tryAutomaticWallTags = true;
        return this;
    }

    public SuCraftBlockType skipAutomaticWallTag() {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.skipAutomaticWallTag = true;
        return this;
    }

    public SuCraftBlockType tryAutomaticFenceTags() {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.tryAutomaticFenceTags = true;
        return this;
    }

    public SuCraftBlockType skipAutomaticFenceTag() {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.skipAutomaticFenceTag = true;
        return this;
    }

    public SuCraftBlockType tryAutomaticFenceGateTags() {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.tryAutomaticFenceGateTags = true;
        return this;
    }

    public SuCraftBlockType skipAutomaticFenceGateTag() {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.skipAutomaticFenceGateTag = true;
        return this;
    }

    public SuCraftBlockType tryAutomaticButtonTags() {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.tryAutomaticButtonTags = true;
        return this;
    }

    public SuCraftBlockType skipAutomaticButtonTag() {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.skipAutomaticButtonTag = true;
        return this;
    }

    public SuCraftBlockType tryAutomaticPressurePlateTags() {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.tryAutomaticPressurePlateTags = true;
        return this;
    }

    public SuCraftBlockType skipAutomaticPressurePlateTag() {
        if (!this.definedByCode) {
            throw new IllegalStateException();
        }
        this.skipAutomaticPressurePlateTag = true;
        return this;
    }

    public SuCraftBlockType itemProperties(Supplier<Item.Properties> properties) {
        return this.updateItem(item -> item.properties(properties));
    }

    public SuCraftBlockType itemProperties(Consumer<Item.Properties> properties) {
        return this.updateItem(item -> item.properties(properties));
    }

    public SuCraftBlockType cookingFuel(ResourceKey<ContextIntProvider> burnTime) {
        return this.updateItem(item -> item.cookingFuel(burnTime));
    }

    public SuCraftBlockType cookingFuel(ResolvableInt burnTime) {
        return this.updateItem(item -> item.cookingFuel(burnTime));
    }

    public SuCraftBlockType fullBlock(Block propertiesFrom, Block fallback) {
        this.propertiesFull(propertiesFrom);
        this.fromToBlockMacro(BlockStateMappingMacroTypes.FULL_BLOCK, fallback);
        this.blockConstructorForDataGeneration();
        this.dataGenerateModel(factory -> factory.addBlockTrivialCube(this.getNMS()));
        this.dataGenerateBlockLootTableForDropSelf();
        return this.tryAutomaticFullBlockTags();
    }

    public SuCraftBlockType fullBlock(Block propertiesFromAndFallback) {
        return this.fullBlock(propertiesFromAndFallback, propertiesFromAndFallback);
    }

    public SuCraftBlockType barrel(Block base) {
        this.blockBuild(builder -> builder.factoryNMS(BarrelBlock::new));
        this.propertiesFull(Blocks.BARREL);
        this.properties(properties -> properties.mapColor(base.defaultMapColor()));
        this.fromToBlockMacro(BlockStateMappingMacroTypes.BARREL, Blocks.BARREL);
        this.constructorForDataGeneration(BarrelBlock::new);
        this.dataGenerateModel((factory, block) -> factory.addBlock(generators -> PlatformDependent.BlockModelGenerators.addBarrel(generators, block)));
        this.dataGenerateBlockLootTableForDropSelf();
        this.dataGenerateTag(BlockTags.GUARDED_BY_PIGLINS);
        this.dataGenerateTag(CBlockItemTags.BARRELS);
        return this.tryAutomaticFullBlockTags();
    }

    public SuCraftBlockType woodenBarrel(Block planks) {
        this.barrel(planks);
        this.mineableWithAxe();
        this.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_BLOCKS);
        return this.dataGenerateTag(CBlockItemTags.BARRELS_WOODEN);
    }

    public SuCraftBlockType ladder(Block base) {
        this.blockBuild(builder -> builder.factoryNMS(LadderBlock::new));
        this.propertiesFull(Blocks.LADDER);
        this.properties(properties -> properties.mapColor(base.defaultMapColor()));
        this.fromToBlockMacro(BlockStateMappingMacroTypes.LADDER, Blocks.LADDER);
        this.constructorForDataGeneration(LadderBlock::new);
        this.dataGenerateModel((factory, block) -> factory.addBlock(generators -> PlatformDependent.BlockModelGenerators.addLadder(generators, block)));
        this.dataGenerateBlockLootTableForDropSelf();
        this.dataGenerateTag(SpoutBlockItemTags.LADDERS);
        return this.tryAutomaticFullBlockTags();
    }

    public SuCraftBlockType woodenLadder(Block planks) {
        this.ladder(planks);
        this.mineableWithAxe();
        this.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_BLOCKS);
        return this.dataGenerateTag(SpoutBlockItemTags.LADDERS_WOODEN);
    }

    public SuCraftBlockType slab(Block propertiesFrom, Block fallback, @Nullable BlockState fallbackBlockState) {
        this.slabFactory();
        this.propertiesFull(propertiesFrom);
        this.slabMacro(BlockStateMappingMacroTypes.SLAB, fallback, fallbackBlockState);
        this.slabConstructorForDataGeneration();
        this.dataGenerateModel(factory -> factory.addBlockSlab(this.getNMS()));
        this.dataGenerateBlockLootTableForDropSelfSlab();
        return this.tryAutomaticSlabTags();
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
        this.dataGenerateModel(factory -> factory.addBlockStairs(this.getNMS()));
        this.dataGenerateBlockLootTableForDropSelf();
        return this.tryAutomaticStairsTags();
    }

    public SuCraftBlockType stairs(Supplier<Block> base, Block propertiesFromAndFallback) {
        return this.stairs(() -> base.get().defaultBlockState(), propertiesFromAndFallback, propertiesFromAndFallback);
    }

    public SuCraftBlockType wall(Block propertiesFrom, Block fallback) {
        this.wallFactory();
        this.propertiesFull(propertiesFrom);
        this.fromToBlockMacro(BlockStateMappingMacroTypes.WALL, fallback);
        this.wallConstructorForDataGeneration();
        this.dataGenerateModel(factory -> factory.addBlockWall(this.getNMS()));
        this.dataGenerateBlockLootTableForDropSelf();
        return this.tryAutomaticWallTags();
    }

    public SuCraftBlockType wall(Block propertiesFromAndFallback) {
        return this.wall(propertiesFromAndFallback, propertiesFromAndFallback);
    }

    public SuCraftBlockType fence(Block propertiesFrom, Block fallback) {
        this.fenceFactory();
        this.propertiesFull(propertiesFrom);
        this.fromToBlockMacro(BlockStateMappingMacroTypes.FENCE, fallback);
        this.fenceConstructorForDataGeneration();
        this.dataGenerateModel(factory -> factory.addBlockFence(this.getNMS()));
        this.dataGenerateBlockLootTableForDropSelf();
        return this.tryAutomaticFenceTags();
    }

    public SuCraftBlockType fence(Block propertiesFromAndFallback) {
        return this.fence(propertiesFromAndFallback, propertiesFromAndFallback);
    }

    public SuCraftBlockType fenceGate(Supplier<WoodType> woodType, Block propertiesFrom, Block fallback) {
        this.fenceGateFactory(woodType);
        this.propertiesFull(propertiesFrom);
        this.fromToBlockMacro(BlockStateMappingMacroTypes.FENCE_GATE, fallback);
        this.fenceGateConstructorForDataGeneration();
        this.dataGenerateModel(factory -> factory.addBlockFenceGate(this.getNMS()));
        this.dataGenerateBlockLootTableForDropSelf();
        return this.tryAutomaticFenceGateTags();
    }

    public SuCraftBlockType fenceGate(Supplier<WoodType> woodType, Block propertiesFromAndFallback) {
        return this.fenceGate(woodType, propertiesFromAndFallback, propertiesFromAndFallback);
    }

    public SuCraftBlockType button(Supplier<BlockSetType> blockSetType, IntSupplier ticksToStayPressed, Block propertiesFrom, Block fallback) {
        this.buttonFactory(blockSetType, ticksToStayPressed);
        this.propertiesFull(propertiesFrom);
        this.fromToBlockMacro(BlockStateMappingMacroTypes.BUTTON, fallback);
        this.buttonConstructorForDataGeneration();
        this.dataGenerateModel(factory -> factory.addBlockButton(this.getNMS()));
        this.dataGenerateBlockLootTableForDropSelf();
        return this.tryAutomaticButtonTags();
    }

    public SuCraftBlockType button(Supplier<BlockSetType> blockSetType, IntSupplier ticksToStayPressed, Block propertiesFromAndFallback) {
        return this.button(blockSetType, ticksToStayPressed, propertiesFromAndFallback, propertiesFromAndFallback);
    }

    public SuCraftBlockType pressurePlate(Supplier<BlockSetType> blockSetType, Block propertiesFrom, Block fallback) {
        this.pressurePlateFactory(blockSetType);
        this.propertiesFull(propertiesFrom);
        this.fromToBlockMacro(BlockStateMappingMacroTypes.PRESSURE_PLATE, fallback);
        this.pressurePlateConstructorForDataGeneration();
        this.dataGenerateModel(factory -> factory.addBlockPressurePlate(this.getNMS()));
        this.dataGenerateBlockLootTableForDropSelf();
        return this.tryAutomaticPressurePlateTags();
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
