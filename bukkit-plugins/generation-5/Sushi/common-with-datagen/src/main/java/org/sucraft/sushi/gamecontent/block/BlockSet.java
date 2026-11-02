package org.sucraft.sushi.gamecontent.block;

import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockItemTagId;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.client.resources.model.sprite.Material;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.Nullable;
import org.sucraft.sushi.datageneration.block.UnsafeBlockStateGeneratorSupplier;import org.sucraft.sushi.datageneration.common.DataGenerationFlag;
import org.sucraft.sushi.gamecontent.common.SuCraftBlockItemTags;
import org.sucraft.sushi.gamecontent.item.SuCraftItemType;
import spout.api.gamecontent.datadriven.block.registry.BlockTypeRegistryEntryBuilderNMS;import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroTypes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface BlockSet {

    BlockType[] values();

    Block[] valuesNMS();

    SuCraftBlockType[] types();

    ItemType[] items();

    Item[] itemsNMS();

    SuCraftItemType[] itemTypes();

    boolean hasFullBlock();

    boolean hasSlab();

    boolean hasStairs();

    boolean hasWall();

    boolean hasFence();

    boolean hasFenceGate();

    boolean hasButton();

    boolean hasPressurePlate();

    interface WithFullBlock extends BlockSet, ItemLike {

        BlockType fullBlock();

        Block fullBlockNMS();

        SuCraftBlockType fullBlockType();

        ItemType fullBlockItem();

        Item fullBlockItemNMS();

        SuCraftItemType fullBlockItemType();

        default Item asItem() {
            return this.fullBlockItemNMS();
        }

    }

    interface WithSlab extends BlockSet {

        BlockType slab();

        Block slabNMS();

        SuCraftBlockType slabType();

        ItemType slabItem();

        Item slabItemNMS();

        SuCraftItemType slabItemType();

    }

    interface WithStairs extends BlockSet {

        BlockType stairs();

        Block stairsNMS();

        SuCraftBlockType stairsType();

        ItemType stairsItem();

        Item stairsItemNMS();

        SuCraftItemType stairsItemType();

    }

    interface WithWall extends BlockSet {

        BlockType wall();

        Block wallNMS();

        SuCraftBlockType wallType();

        ItemType wallItem();

        Item wallItemNMS();

        SuCraftItemType wallItemType();

    }

    interface WithFence extends BlockSet {

        BlockType fence();

        Block fenceNMS();

        SuCraftBlockType fenceType();

        ItemType fenceItem();

        Item fenceItemNMS();

        SuCraftItemType fenceItemType();

    }

    interface WithFenceGate extends BlockSet {

        BlockType fenceGate();

        Block fenceGateNMS();

        SuCraftBlockType fenceGateType();

        ItemType fenceGateItem();

        Item fenceGateItemNMS();

        SuCraftItemType fenceGateItemType();

    }

    interface WithButton extends BlockSet {

        BlockType button();

        Block buttonNMS();

        SuCraftBlockType buttonType();

        ItemType buttonItem();

        Item buttonItemNMS();

        SuCraftItemType buttonItemType();

    }

    interface WithPressurePlate extends BlockSet {

        BlockType pressurePlate();

        Block pressurePlateNMS();

        SuCraftBlockType pressurePlateType();

        ItemType pressurePlateItem();

        Item pressurePlateItemNMS();

        SuCraftItemType pressurePlateItemType();

    }

    interface WithFullBlockAndSlab extends WithFullBlock, WithSlab {
    }

    interface WithFullBlockAndSlabAndStairs extends WithFullBlockAndSlab, WithStairs {
    }

    interface WithFullBlockAndSlabAndStairsAndWall extends WithFullBlockAndSlabAndStairs, WithWall {
    }

    interface WithFullBlockAndSlabAndStairsAndWallAndFence extends WithFullBlockAndSlabAndStairsAndWall, WithFence {
    }

    interface WithFullBlockAndSlabAndStairsAndWallAndFenceAndFenceGate extends WithFullBlockAndSlabAndStairsAndWallAndFence, WithFenceGate {
    }

    interface WithFullBlockAndSlabAndStairsAndWallAndFenceAndFenceGateAndButton extends WithFullBlockAndSlabAndStairsAndWallAndFenceAndFenceGate, WithButton {
    }

    interface WithFullBlockAndSlabAndStairsAndWallAndFenceAndFenceGateAndButtonAndPressurePlate extends WithFullBlockAndSlabAndStairsAndWallAndFenceAndFenceGateAndButton, WithPressurePlate {
    }

    interface WithFullBlockAndSlabAndStairsAndWallAndFenceAndButton extends WithFullBlockAndSlabAndStairsAndWallAndFence, WithButton {
    }

    interface WithFullBlockAndSlabAndStairsAndWallAndFenceAndButtonAndPressurePlate extends WithFullBlockAndSlabAndStairsAndWallAndFenceAndButton, WithPressurePlate {
    }

    interface WithFullBlockAndSlabAndStairsAndWallAndButton extends WithFullBlockAndSlabAndStairsAndWall, WithButton {
    }

    interface WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate extends WithFullBlockAndSlabAndStairsAndWallAndButton, WithPressurePlate {
    }

    interface WithSlabAndStairs extends WithSlab, WithStairs {
    }

    interface WithSlabAndStairsAndWall extends WithSlabAndStairs, WithWall {
    }

    interface WithSlabAndStairsAndWallAndButton extends WithSlabAndStairsAndWall, WithButton {
    }

    interface WithSlabAndStairsAndWallAndButtonAndPressurePlate extends WithSlabAndStairsAndWallAndButton, WithPressurePlate {
    }

    interface WithStairsAndWall extends WithStairs, WithWall {
    }

    interface WithStairsAndWallAndButton extends WithStairsAndWall, WithButton {
    }

    interface WithStairsAndWallAndButtonAndPressurePlate extends WithStairsAndWallAndButton, WithPressurePlate {
    }

    interface WithWallAndButton extends WithWall, WithButton {
    }

    interface WithWallAndButtonAndPressurePlate extends WithWallAndButton, WithPressurePlate {
    }

    interface WithFenceAndButton extends WithFence, WithButton {
    }

    interface WithFenceAndButtonAndPressurePlate extends WithFenceAndButton, WithPressurePlate {
    }

    interface WithButtonAndPressurePlate extends WithButton, WithPressurePlate {
    }

    final class Impl implements
        WithFullBlockAndSlabAndStairsAndWallAndFenceAndFenceGateAndButtonAndPressurePlate,
        WithFullBlockAndSlabAndStairsAndWallAndFenceAndButtonAndPressurePlate,
        WithFullBlockAndSlabAndStairsAndWallAndButtonAndPressurePlate,
        WithSlabAndStairsAndWallAndButtonAndPressurePlate,
        WithStairsAndWallAndButtonAndPressurePlate,
        WithWallAndButtonAndPressurePlate,
        WithFenceAndButtonAndPressurePlate,
        WithButtonAndPressurePlate {

        private @Nullable SuCraftBlockType fullBlock;
        private @Nullable SuCraftBlockType slab;
        private @Nullable SuCraftBlockType stairs;
        private @Nullable SuCraftBlockType wall;
        private @Nullable SuCraftBlockType fence;
        private @Nullable SuCraftBlockType fenceGate;
        private @Nullable SuCraftBlockType button;
        private @Nullable SuCraftBlockType pressurePlate;

        private BlockType @Nullable [] values;
        private Block @Nullable [] valuesNMS;
        private SuCraftBlockType @Nullable [] types;
        private ItemType @Nullable [] items;
        private Item @Nullable [] itemsNMS;
        private Item @Nullable [] itemsForDataGeneration;
        private SuCraftItemType @Nullable [] itemTypes;

        Impl(
            @Nullable SuCraftBlockType fullBlock,
            @Nullable SuCraftBlockType slab,
            @Nullable SuCraftBlockType stairs,
            @Nullable SuCraftBlockType wall,
            @Nullable SuCraftBlockType fence,
            @Nullable SuCraftBlockType fenceGate,
            @Nullable SuCraftBlockType button,
            @Nullable SuCraftBlockType pressurePlate
        ) {
            this.fullBlock = fullBlock;
            this.slab = slab;
            this.stairs = stairs;
            this.wall = wall;
            this.fence = fence;
            this.fenceGate = fenceGate;
            this.button = button;
            this.pressurePlate = pressurePlate;
        }

        @Override
        public BlockType[] values() {
            if (this.values == null) {
                this.values = Arrays.stream(this.types())
                    .map(SuCraftBlockType::get).toArray(BlockType[]::new);
            }
            return this.values;
        }

        @Override
        public Block[] valuesNMS() {
            if (this.valuesNMS == null) {
                this.valuesNMS = Arrays.stream(this.types())
                    .map(SuCraftBlockType::getNMS).toArray(Block[]::new);
            }
            return this.valuesNMS;
        }

        @Override
        public SuCraftBlockType[] types() {
            if (this.types == null) {
                this.types = Stream.of(
                    this.fullBlock,
                    this.slab,
                    this.stairs,
                    this.wall,
                    this.fence,
                    this.fenceGate,
                    this.button,
                    this.pressurePlate
                ).filter(Objects::nonNull).toArray(SuCraftBlockType[]::new);
            }
            return this.types;
        }

        @Override
        public ItemType[] items() {
            if (this.items == null) {
                this.items = Arrays.stream(this.itemTypes())
                    .map(SuCraftItemType::get).toArray(ItemType[]::new);
            }
            return this.items;
        }

        @Override
        public Item[] itemsNMS() {
            if (this.itemsNMS == null) {
                this.itemsNMS = Arrays.stream(this.itemTypes())
                    .map(SuCraftItemType::getNMS).toArray(Item[]::new);
            }
            return this.itemsNMS;
        }

        @Override
        public SuCraftItemType[] itemTypes() {
            if (this.itemTypes == null) {
                this.itemTypes = Arrays.stream(this.types())
                    .map(SuCraftBlockType::item).filter(Objects::nonNull).toArray(SuCraftItemType[]::new);
            }
            return this.itemTypes;
        }

        @Override
        public boolean hasFullBlock() {
            return this.fullBlock != null;
        }

        @Override
        public boolean hasSlab() {
            return this.slab != null;
        }

        @Override
        public boolean hasStairs() {
            return this.stairs != null;
        }

        @Override
        public boolean hasWall() {
            return this.wall != null;
        }

        @Override
        public boolean hasFence() {
            return this.fence != null;
        }

        @Override
        public boolean hasFenceGate() {
            return this.fenceGate != null;
        }

        @Override
        public boolean hasButton() {
            return this.button != null;
        }

        @Override
        public boolean hasPressurePlate() {
            return this.pressurePlate != null;
        }

        @Override
        public BlockType fullBlock() {
            return this.fullBlock.get();
        }

        @Override
        public Block fullBlockNMS() {
            return this.fullBlock.getNMS();
        }

        @Override
        public SuCraftBlockType fullBlockType() {
            return this.fullBlock;
        }

        @Override
        public ItemType fullBlockItem() {
            return this.fullBlock.item();
        }

        @Override
        public Item fullBlockItemNMS() {
            return this.fullBlock.itemNMS();
        }

        @Override
        public SuCraftItemType fullBlockItemType() {
            return this.fullBlock.itemType();
        }

        @Override
        public BlockType slab() {
            return this.slab.get();
        }

        @Override
        public Block slabNMS() {
            return this.slab.getNMS();
        }

        @Override
        public SuCraftBlockType slabType() {
            return this.slab;
        }

        @Override
        public ItemType slabItem() {
            return this.slab.item();
        }

        @Override
        public Item slabItemNMS() {
            return this.slab.itemNMS();
        }

        @Override
        public SuCraftItemType slabItemType() {
            return this.slab.itemType();
        }

        @Override
        public BlockType stairs() {
            return this.stairs.get();
        }

        @Override
        public Block stairsNMS() {
            return this.stairs.getNMS();
        }

        @Override
        public SuCraftBlockType stairsType() {
            return this.stairs;
        }

        @Override
        public ItemType stairsItem() {
            return this.stairs.item();
        }

        @Override
        public Item stairsItemNMS() {
            return this.stairs.itemNMS();
        }

        @Override
        public SuCraftItemType stairsItemType() {
            return this.stairs.itemType();
        }

        @Override
        public BlockType wall() {
            return this.wall.get();
        }

        @Override
        public Block wallNMS() {
            return this.wall.getNMS();
        }

        @Override
        public SuCraftBlockType wallType() {
            return this.wall;
        }

        @Override
        public ItemType wallItem() {
            return this.wall.item();
        }

        @Override
        public Item wallItemNMS() {
            return this.wall.itemNMS();
        }

        @Override
        public SuCraftItemType wallItemType() {
            return this.wall.itemType();
        }

        @Override
        public BlockType fence() {
            return this.fence.get();
        }

        @Override
        public Block fenceNMS() {
            return this.fence.getNMS();
        }

        @Override
        public SuCraftBlockType fenceType() {
            return this.fence;
        }

        @Override
        public ItemType fenceItem() {
            return this.fence.item();
        }

        @Override
        public Item fenceItemNMS() {
            return this.fence.itemNMS();
        }

        @Override
        public SuCraftItemType fenceItemType() {
            return this.fence.itemType();
        }

        @Override
        public BlockType fenceGate() {
            return this.fenceGate.get();
        }

        @Override
        public Block fenceGateNMS() {
            return this.fenceGate.getNMS();
        }

        @Override
        public SuCraftBlockType fenceGateType() {
            return this.fenceGate;
        }

        @Override
        public ItemType fenceGateItem() {
            return this.fenceGate.item();
        }

        @Override
        public Item fenceGateItemNMS() {
            return this.fenceGate.itemNMS();
        }

        @Override
        public SuCraftItemType fenceGateItemType() {
            return this.fenceGate.itemType();
        }

        @Override
        public BlockType button() {
            return this.button.get();
        }

        @Override
        public Block buttonNMS() {
            return this.button.getNMS();
        }

        @Override
        public SuCraftBlockType buttonType() {
            return this.button;
        }

        @Override
        public ItemType buttonItem() {
            return this.button.item();
        }

        @Override
        public Item buttonItemNMS() {
            return this.button.itemNMS();
        }

        @Override
        public SuCraftItemType buttonItemType() {
            return this.button.itemType();
        }

        @Override
        public BlockType pressurePlate() {
            return this.pressurePlate.get();
        }

        @Override
        public Block pressurePlateNMS() {
            return this.pressurePlate.getNMS();
        }

        @Override
        public SuCraftBlockType pressurePlateType() {
            return this.pressurePlate;
        }

        @Override
        public ItemType pressurePlateItem() {
            return this.pressurePlate.item();
        }

        @Override
        public Item pressurePlateItemNMS() {
            return this.pressurePlate.itemNMS();
        }

        @Override
        public SuCraftItemType pressurePlateItemType() {
            return this.pressurePlate.itemType();
        }

    }

    final class Builder {

        private @Nullable String fullBlockPath;
        private @Nullable String basePath;
        private boolean fullBlock;
        private boolean fullBlockOnlyForDataGeneration;
        private @Nullable List<Consumer<BlockTypeRegistryEntryBuilderNMS>> fullBlockBlockBuilders;
        private @Nullable Function<BlockBehaviour.Properties, Block> fullBlockConstructorForDataGeneration;
        private @Nullable Function<Block, Object> fullBlockModelProvider;
        private @Nullable UnsafeBlockStateGeneratorSupplier fullBlockModelCustomGenerator;
        private boolean stairs;
        private boolean slab;
        private boolean wall;
        private boolean fence;
        private boolean fenceGate;
        private boolean button;
        private boolean pressurePlate;
        private Function<String, String> shapeIdentifierSuffixFunction = Function.identity();
        private @Nullable Consumer<BlockBehaviour.Properties> propertiesFinalizer;
        private BlockBehaviour.@Nullable Properties properties;
        private @Nullable Block propertiesFrom;
        private @Nullable Block slabPropertiesFrom;
        private @Nullable Block stairsPropertiesFrom;
        private @Nullable Block wallPropertiesFrom;
        private @Nullable Block fencePropertiesFrom;
        private @Nullable Block fenceGatePropertiesFrom;
        private @Nullable Block buttonPropertiesFrom;
        private @Nullable Block pressurePlatePropertiesFrom;
        private @Nullable Block fallback;
        private @Nullable Block stairsFallback;
        private @Nullable Block slabFallback;
        private @Nullable Block wallFallback;
        private @Nullable Block fenceFallback;
        private @Nullable Block fenceGateFallback;
        private @Nullable Block buttonFallback;
        private @Nullable Block pressurePlateFallback;
        private @Nullable BlockState slabFullBlockFallback;
        private @Nullable Supplier<BlockState> stairsBaseState;
        private @Nullable Supplier<WoodType> woodType;
        private @Nullable Supplier<BlockSetType> blockSetType;
        private @Nullable IntSupplier buttonTicksToStayPressed;
        private @Nullable List<Object> fullBlockTags;
        private @Nullable List<Object> slabTags;
        private @Nullable List<Object> stairsTags;
        private @Nullable List<Object> wallTags;
        private @Nullable List<Object> fenceTags;
        private @Nullable List<Object> fenceGateTags;
        private @Nullable List<Object> buttonTags;
        private @Nullable List<Object> pressurePlateTags;
        private @Nullable Consumer<SuCraftBlockType> fullBlockFinalizer;
        private @Nullable Consumer<SuCraftBlockType> slabFinalizer;
        private @Nullable Consumer<SuCraftBlockType> stairsFinalizer;
        private @Nullable Consumer<SuCraftBlockType> wallFinalizer;
        private @Nullable Consumer<SuCraftBlockType> fenceFinalizer;
        private @Nullable Consumer<SuCraftBlockType> fenceGateFinalizer;
        private @Nullable Consumer<SuCraftBlockType> buttonFinalizer;
        private @Nullable Consumer<SuCraftBlockType> pressurePlateFinalizer;

        Builder() {
        }

        Builder(String fullBlockPath) {
            this.fullBlockPath = fullBlockPath;
        }

        public Builder fullBlockPath(String fullBlockPath) {
            this.fullBlockPath = fullBlockPath;
            return this;
        }

        public Builder basePath(String basePath) {
            this.basePath = basePath;
            return this;
        }

        public Builder fullBlock() {
            this.fullBlock = true;
            return this;
        }

        public Builder fullBlockOnlyForDataGeneration() {
            this.fullBlockOnlyForDataGeneration = true;
            return this;
        }

        public Builder fullBlock(boolean onlyForDataGeneration) {
            return onlyForDataGeneration ? this.fullBlockOnlyForDataGeneration() : this.fullBlock();
        }

        public Builder fullBlockBlockBuild(Consumer<BlockTypeRegistryEntryBuilderNMS> builder) {
            if (this.fullBlockBlockBuilders == null) {
                this.fullBlockBlockBuilders = new ArrayList<>(1);
            }
            this.fullBlockBlockBuilders.add(builder);
            return this;
        }

        public Builder fullBlockConstructorForDataGeneration(Function<BlockBehaviour.Properties, Block> constructor) {
            this.fullBlockConstructorForDataGeneration = constructor;
            return this;
        }

        public Builder fullBlockModelProvider(Function<Block, Object> provider) {
            this.fullBlockModelProvider = provider;
            return this;
        }

        public Builder fullBlockModelCustomGenerator(UnsafeBlockStateGeneratorSupplier supplier) {
            this.fullBlockModelCustomGenerator = supplier;
            return this;
        }

        public Builder fullBlockModelProviderFromCube(Material texture) {
            return this.fullBlockModelProvider(block -> TexturedModel.CUBE.get(block).updateTextures(mappings ->
                mappings.put(TextureSlot.ALL, texture)
            ));
        }

        public Builder fullBlockModelProviderFromCube(Block blockWithTexture) {
            return this.fullBlockModelProviderFromCube(TextureMapping.getBlockTexture(blockWithTexture));
        }

        public Builder slab() {
            this.slab = true;
            return this;
        }

        public Builder woodenSlab() {
            this.slab();
            return this.slabTag(BlockItemTags.WOODEN_SLABS);
        }

        public Builder stairs() {
            this.stairs = true;
            return this;
        }

        public Builder woodenStairs() {
            this.stairs();
            return this.stairsTag(BlockItemTags.WOODEN_STAIRS);
        }

        public Builder wall() {
            this.wall = true;
            return this;
        }

        public Builder woodenWall() {
            this.wall();
            return this.wallTag(SuCraftBlockItemTags.WOODEN_WALLS);
        }

        public Builder stoneWall() {
            this.wall();
            return this.wallTag(SuCraftBlockItemTags.STONE_WALLS);
        }

        public Builder fence() {
            this.fence = true;
            return this;
        }

        public Builder woodenFence() {
            this.fence();
            return this.fenceTag(BlockItemTags.WOODEN_FENCES);
        }

        public Builder stoneFence() {
            return this.fence();
        }

        public Builder fenceGate() {
            this.fenceGate = true;
            return this;
        }

        public Builder woodenFenceGate() {
            return this.fenceGate();
        }

        public Builder stoneFenceGate() {
            return this.fenceGate();
        }

        public Builder button() {
            this.button = true;
            return this;
        }

        public Builder stoneButton() {
            this.button();
            return this.buttonTag(BlockItemTags.STONE_BUTTONS);
        }

        public Builder stoneButton(BlockSetType blockSetType) {
            this.stoneButton();
            return this.blockSetType(blockSetType);
        }

        public Builder woodenButton() {
            this.button();
            return this.buttonTag(BlockItemTags.WOODEN_BUTTONS);
        }

        public Builder woodenButton(BlockSetType blockSetType) {
            this.woodenButton();
            return this.blockSetType(blockSetType);
        }

        public Builder pressurePlate() {
            this.pressurePlate = true;
            return this;
        }

        public Builder stonePressurePlate() {
            this.pressurePlate();
            return this.pressurePlateTag(BlockTags.STONE_PRESSURE_PLATES);
        }

        public Builder stonePressurePlate(BlockSetType blockSetType) {
            this.stonePressurePlate();
            return this.blockSetType(blockSetType);
        }

        public Builder woodenPressurePlate() {
            this.pressurePlate();
            return this.pressurePlateTag(BlockItemTags.WOODEN_PRESSURE_PLATES);
        }

        public Builder woodenPressurePlate(BlockSetType blockSetType) {
            this.woodenPressurePlate();
            return this.blockSetType(blockSetType);
        }

        public Builder shapeIdentifierSuffixFunction(Function<String, String> function) {
            this.shapeIdentifierSuffixFunction = function;
            return this;
        }

        public Builder propertiesFinalizer(Consumer<BlockBehaviour.Properties> propertiesFinalizer) {
            this.propertiesFinalizer = propertiesFinalizer;
            return this;
        }

        public Builder properties(BlockBehaviour.Properties properties) {
            this.properties = properties;
            return this;
        }

        public Builder propertiesFrom(Block propertiesFrom) {
            this.propertiesFrom = propertiesFrom;
            return this;
        }

        public Builder fallback(Block fallback) {
            this.fallback = fallback;
            return this;
        }

        public Builder vanilla(Block propertiesFromAndFallback) {
            this.propertiesFrom = propertiesFromAndFallback;
            this.fallback = propertiesFromAndFallback;
            if (this.fullBlockPath == null) {
                this.fullBlockPath = propertiesFromAndFallback.builtInRegistryHolder().key().identifier().getPath();
            }
            return this;
        }

        public Builder slabPropertiesFrom(Block propertiesFrom) {
            this.slabPropertiesFrom = propertiesFrom;
            return this;
        }

        public Builder slabFallback(Block fallback) {
            this.slabFallback = fallback;
            return this;
        }

        public Builder slabVanilla(Block propertiesFromAndFallback) {
            this.slabPropertiesFrom = propertiesFromAndFallback;
            this.slabFallback = propertiesFromAndFallback;
            return this;
        }

        public Builder stairsPropertiesFrom(Block propertiesFrom) {
            this.stairsPropertiesFrom = propertiesFrom;
            return this;
        }

        public Builder stairsFallback(Block fallback) {
            this.stairsFallback = fallback;
            return this;
        }

        public Builder stairsVanilla(Block propertiesFromAndFallback) {
            this.stairsPropertiesFrom = propertiesFromAndFallback;
            this.stairsFallback = propertiesFromAndFallback;
            return this;
        }

        public Builder wallPropertiesFrom(Block propertiesFrom) {
            this.wallPropertiesFrom = propertiesFrom;
            return this;
        }

        public Builder wallFallback(Block fallback) {
            this.wallFallback = fallback;
            return this;
        }

        public Builder wallVanilla(Block propertiesFromAndFallback) {
            this.wallPropertiesFrom = propertiesFromAndFallback;
            this.wallFallback = propertiesFromAndFallback;
            return this;
        }

        public Builder fencePropertiesFrom(Block propertiesFrom) {
            this.fencePropertiesFrom = propertiesFrom;
            return this;
        }

        public Builder fenceFallback(Block fallback) {
            this.fenceFallback = fallback;
            return this;
        }

        public Builder fenceVanilla(Block propertiesFromAndFallback) {
            this.fencePropertiesFrom = propertiesFromAndFallback;
            this.fenceFallback = propertiesFromAndFallback;
            return this;
        }

        public Builder fenceGatePropertiesFrom(Block propertiesFrom) {
            this.fenceGatePropertiesFrom = propertiesFrom;
            return this;
        }

        public Builder fenceGateFallback(Block fallback) {
            this.fenceGateFallback = fallback;
            return this;
        }

        public Builder fenceGateVanilla(Block propertiesFromAndFallback) {
            this.fenceGatePropertiesFrom = propertiesFromAndFallback;
            this.fenceGateFallback = propertiesFromAndFallback;
            return this;
        }

        public Builder buttonPropertiesFrom(Block propertiesFrom) {
            this.buttonPropertiesFrom = propertiesFrom;
            return this;
        }

        public Builder buttonFallback(Block fallback) {
            this.buttonFallback = fallback;
            return this;
        }

        public Builder buttonVanilla(Block propertiesFromAndFallback) {
            this.buttonPropertiesFrom = propertiesFromAndFallback;
            this.buttonFallback = propertiesFromAndFallback;
            return this;
        }

        public Builder pressurePlatePropertiesFrom(Block propertiesFrom) {
            this.pressurePlatePropertiesFrom = propertiesFrom;
            return this;
        }

        public Builder pressurePlateFallback(Block fallback) {
            this.pressurePlateFallback = fallback;
            return this;
        }

        public Builder pressurePlateVanilla(Block propertiesFromAndFallback) {
            this.pressurePlatePropertiesFrom = propertiesFromAndFallback;
            this.pressurePlateFallback = propertiesFromAndFallback;
            return this;
        }

        public Builder slabFullBlockFallback(BlockState fullBlockFallback) {
            this.slabFullBlockFallback = fullBlockFallback;
            return this;
        }

        public Builder slabFullBlockFallback(Block fullBlockFallback) {
            return this.slabFullBlockFallback(fullBlockFallback.defaultBlockState());
        }

        public Builder stairsBaseState(Supplier<BlockState> baseState) {
            this.stairsBaseState = baseState;
            return this;
        }

        public Builder stairsBaseState(BlockState baseState) {
            return this.stairsBaseState(() -> baseState);
        }

        public Builder stairsBaseState(Block baseState) {
            return this.stairsBaseState(baseState::defaultBlockState);
        }

        public Builder woodType(Supplier<WoodType> woodType) {
            this.woodType = woodType;
            return this;
        }

        public Builder woodType(WoodType woodType) {
            return this.woodType(() -> woodType);
        }

        public Builder blockSetType(Supplier<BlockSetType> blockSetType) {
            this.blockSetType = blockSetType;
            return this;
        }

        public Builder blockSetType(BlockSetType blockSetType) {
            return this.blockSetType(() -> blockSetType);
        }

        public Builder buttonTicksToStayPressed(IntSupplier ticksToStayPressed) {
            this.buttonTicksToStayPressed = ticksToStayPressed;
            return this;
        }

        public Builder buttonTicksToStayPressed(int ticksToStayPressed) {
            return this.buttonTicksToStayPressed(() -> ticksToStayPressed);
        }

        private Builder fullBlockTagUnsafe(Object tag) {
            if (this.fullBlockTags == null) {
                this.fullBlockTags = new ArrayList<>(1);
            }
            this.fullBlockTags.add(tag);
            return this;
        }

        public Builder fullBlockTag(TagKey<Block> tag) {
            return this.fullBlockTagUnsafe(tag);
        }

        public Builder fullBlockItemTag(TagKey<Item> tag) {
            return this.fullBlockTagUnsafe(tag);
        }

        public Builder fullBlockTag(BlockItemTagId tag) {
            return this.fullBlockTagUnsafe(tag);
        }

        private Builder slabTagUnsafe(Object tag) {
            if (this.slabTags == null) {
                this.slabTags = new ArrayList<>(1);
            }
            this.slabTags.add(tag);
            return this;
        }

        public Builder slabTag(TagKey<Block> tag) {
            return this.slabTagUnsafe(tag);
        }

        public Builder slabItemTag(TagKey<Item> tag) {
            return this.slabTagUnsafe(tag);
        }

        public Builder slabTag(BlockItemTagId tag) {
            return this.slabTagUnsafe(tag);
        }

        private Builder stairsTagUnsafe(Object tag) {
            if (this.stairsTags == null) {
                this.stairsTags = new ArrayList<>(1);
            }
            this.stairsTags.add(tag);
            return this;
        }

        public Builder stairsTag(TagKey<Block> tag) {
            return this.stairsTagUnsafe(tag);
        }

        public Builder stairsItemTag(TagKey<Item> tag) {
            return this.stairsTagUnsafe(tag);
        }

        public Builder stairsTag(BlockItemTagId tag) {
            return this.stairsTagUnsafe(tag);
        }

        private Builder wallTagUnsafe(Object tag) {
            if (this.wallTags == null) {
                this.wallTags = new ArrayList<>(1);
            }
            this.wallTags.add(tag);
            return this;
        }

        public Builder wallTag(TagKey<Block> tag) {
            return this.wallTagUnsafe(tag);
        }

        public Builder wallItemTag(TagKey<Item> tag) {
            return this.wallTagUnsafe(tag);
        }

        public Builder wallTag(BlockItemTagId tag) {
            return this.wallTagUnsafe(tag);
        }

        private Builder fenceTagUnsafe(Object tag) {
            if (this.fenceTags == null) {
                this.fenceTags = new ArrayList<>(1);
            }
            this.fenceTags.add(tag);
            return this;
        }

        public Builder fenceTag(TagKey<Block> tag) {
            return this.fenceTagUnsafe(tag);
        }

        public Builder fenceItemTag(TagKey<Item> tag) {
            return this.fenceTagUnsafe(tag);
        }

        public Builder fenceTag(BlockItemTagId tag) {
            return this.fenceTagUnsafe(tag);
        }

        private Builder fenceGateTagUnsafe(Object tag) {
            if (this.fenceGateTags == null) {
                this.fenceGateTags = new ArrayList<>(1);
            }
            this.fenceGateTags.add(tag);
            return this;
        }

        public Builder fenceGateTag(TagKey<Block> tag) {
            return this.fenceGateTagUnsafe(tag);
        }

        public Builder fenceGateItemTag(TagKey<Item> tag) {
            return this.fenceGateTagUnsafe(tag);
        }

        public Builder fenceGateTag(BlockItemTagId tag) {
            return this.fenceGateTagUnsafe(tag);
        }

        private Builder buttonTagUnsafe(Object tag) {
            if (this.buttonTags == null) {
                this.buttonTags = new ArrayList<>(1);
            }
            this.buttonTags.add(tag);
            return this;
        }

        public Builder buttonTag(TagKey<Block> tag) {
            return this.buttonTagUnsafe(tag);
        }

        public Builder buttonItemTag(TagKey<Item> tag) {
            return this.buttonTagUnsafe(tag);
        }

        public Builder buttonTag(BlockItemTagId tag) {
            return this.buttonTagUnsafe(tag);
        }

        private Builder pressurePlateTagUnsafe(Object tag) {
            if (this.pressurePlateTags == null) {
                this.pressurePlateTags = new ArrayList<>(1);
            }
            this.pressurePlateTags.add(tag);
            return this;
        }

        public Builder pressurePlateTag(TagKey<Block> tag) {
            return this.pressurePlateTagUnsafe(tag);
        }

        public Builder pressurePlateItemTag(TagKey<Item> tag) {
            return this.pressurePlateTagUnsafe(tag);
        }

        public Builder pressurePlateTag(BlockItemTagId tag) {
            return this.pressurePlateTagUnsafe(tag);
        }

        private Builder tagUnsafe(Object tag) {
            this.fullBlockTagUnsafe(tag);
            this.slabTagUnsafe(tag);
            this.stairsTagUnsafe(tag);
            this.wallTagUnsafe(tag);
            this.fenceTagUnsafe(tag);
            this.fenceGateTagUnsafe(tag);
            this.buttonTagUnsafe(tag);
            return this.pressurePlateTagUnsafe(tag);
        }

        public Builder tag(TagKey<Block> tag) {
            return this.tagUnsafe(tag);
        }

        public Builder itemTag(TagKey<Item> tag) {
            return this.tagUnsafe(tag);
        }

        public Builder tag(BlockItemTagId tag) {
            return this.tagUnsafe(tag);
        }

        private Builder nonVolatileShapeTagUnsafe(Object tag) {
            this.fullBlockTagUnsafe(tag);
            this.slabTagUnsafe(tag);
            this.stairsTagUnsafe(tag);
            this.wallTagUnsafe(tag);
            this.fenceTagUnsafe(tag);
            return this.fenceGateTagUnsafe(tag);
        }

        public Builder nonVolatileShapeTag(TagKey<Block> tag) {
            return this.nonVolatileShapeTagUnsafe(tag);
        }

        public Builder itemNonVolatileShapeTag(TagKey<Item> tag) {
            return this.nonVolatileShapeTagUnsafe(tag);
        }

        public Builder nonVolatileShapeTag(BlockItemTagId tag) {
            return this.nonVolatileShapeTagUnsafe(tag);
        }

        public Builder mineableWithAxe() {
            return this.tag(BlockTags.MINEABLE_WITH_AXE);
        }

        public Builder mineableWithHoe() {
            return this.tag(BlockTags.MINEABLE_WITH_HOE);
        }

        public Builder mineableWithPickaxe() {
            return this.tag(BlockTags.MINEABLE_WITH_PICKAXE);
        }

        public Builder mineableWithShovel() {
            return this.tag(BlockTags.MINEABLE_WITH_SHOVEL);
        }

        public Builder fullBlockFinalizer(@Nullable Consumer<SuCraftBlockType> finalizer) {
            this.fullBlockFinalizer = finalizer;
            return this;
        }

        public Builder slabFinalizer(@Nullable Consumer<SuCraftBlockType> finalizer) {
            this.slabFinalizer = finalizer;
            return this;
        }

        public Builder stairsFinalizer(@Nullable Consumer<SuCraftBlockType> finalizer) {
            this.stairsFinalizer = finalizer;
            return this;
        }

        public Builder wallFinalizer(@Nullable Consumer<SuCraftBlockType> finalizer) {
            this.wallFinalizer = finalizer;
            return this;
        }

        public Builder fenceFinalizer(@Nullable Consumer<SuCraftBlockType> finalizer) {
            this.fenceFinalizer = finalizer;
            return this;
        }

        public Builder fenceGateFinalizer(@Nullable Consumer<SuCraftBlockType> finalizer) {
            this.fenceGateFinalizer = finalizer;
            return this;
        }

        public Builder buttonFinalizer(@Nullable Consumer<SuCraftBlockType> finalizer) {
            this.buttonFinalizer = finalizer;
            return this;
        }

        public Builder pressurePlateFinalizer(@Nullable Consumer<SuCraftBlockType> finalizer) {
            this.pressurePlateFinalizer = finalizer;
            return this;
        }

        private Identifier getBaseIdentifier() {
            if (this.basePath != null) {
                return Identifier.fromNamespaceAndPath("sucraft", this.basePath);
            }
            String fullBlockPath = Objects.requireNonNull(this.fullBlockPath);
            return Identifier.fromNamespaceAndPath("sucraft", getBasePath(fullBlockPath));
        }

        private Identifier getFullBlockIdentifier() {
            return this.getBaseIdentifier().withPath(Objects.requireNonNull(this.fullBlockPath));
        }

        private Identifier getSuffixedIdentifier(String suffix) {
            return this.getBaseIdentifier().withSuffix(suffix);
        }

        private Identifier getShapeIdentifier(String suffix) {
            return this.getBaseIdentifier().withSuffix(this.shapeIdentifierSuffixFunction.apply(suffix));
        }

        private BlockBehaviour.Properties finalizeProperties(BlockBehaviour.Properties properties) {
            if (this.propertiesFinalizer == null) {
                return properties;
            }
            this.propertiesFinalizer.accept(properties);
            return properties;
        }

        public @Nullable Consumer<SuCraftBlockType> getFullBlockFinalizer() {
            if (this.fullBlockFinalizer != null) {
                return this.fullBlockFinalizer;
            }
            if (this.fullBlockOnlyForDataGeneration) {
                this.fullBlockFinalizer(type -> type.dataGenerateBlockLootTable(_ -> {
                }));
                return this.fullBlockFinalizer;
            }
            return null;
        }

        public @Nullable Function<Block, Object> getFullBlockModelProvider() {
            if (this.fullBlockModelProvider != null) {
                return this.fullBlockModelProvider;
            }
            if (this.fullBlockOnlyForDataGeneration) {
                this.fullBlockModelProviderFromCube(this.getFullBlockFallback());
                return this.fullBlockModelProvider;
            }
            return null;
        }

        public @Nullable UnsafeBlockStateGeneratorSupplier getFullBlockModelCustomGenerator() {
            return this.fullBlockModelCustomGenerator;
        }

        private BlockBehaviour.Properties getFullBlockProperties() {
            BlockBehaviour.Properties unfinalizedProperties;
            if (this.properties != null) {
                unfinalizedProperties = PlatformDependent.BlockBehaviourProperties.ofFullCopy(this.properties);
            } else {
                unfinalizedProperties = BlockBehaviour.Properties.ofFullCopy(Objects.requireNonNull(this.propertiesFrom));
            }
            return this.finalizeProperties(unfinalizedProperties);
        }

        private BlockBehaviour.Properties getShapeProperties(
            Function<BlockBehaviour.Properties, BlockBehaviour.Properties> fullBlockPropertiesMapper,
            Supplier<@Nullable Block> shapePropertiesFromSupplier
        ) {
            BlockBehaviour.Properties unfinalizedProperties;
            if (this.properties != null) {
                unfinalizedProperties = fullBlockPropertiesMapper.apply(this.properties);
            } else {
                var shapePropertiesFrom = shapePropertiesFromSupplier.get();
                if (shapePropertiesFrom != null) {
                    unfinalizedProperties = BlockBehaviour.Properties.ofFullCopy(shapePropertiesFrom);
                } else {
                    unfinalizedProperties = fullBlockPropertiesMapper.apply(this.getFullBlockProperties());
                }
            }
            return this.finalizeProperties(unfinalizedProperties);
        }

        private BlockBehaviour.Properties getSlabProperties() {
            return this.getShapeProperties(
                properties -> PlatformDependent.BlockBehaviourProperties.ofLegacyCopy(properties).isViewBlocking(Blocks.NEAR_PLANE_INTERSECTS_OUTLINE),
                () -> {
                    if (this.slabPropertiesFrom != null) {
                        return this.slabPropertiesFrom;
                    }
                    return getShapeIfExists(this.propertiesFrom, "_slab");
                }
            );
        }

        private BlockBehaviour.Properties getStairsProperties() {
            return this.getShapeProperties(
                properties -> PlatformDependent.BlockBehaviourProperties.ofLegacyCopy(properties).isViewBlocking(Blocks.NEAR_PLANE_INTERSECTS_OUTLINE),
                () -> {
                    if (this.stairsPropertiesFrom != null) {
                        return this.stairsPropertiesFrom;
                    }
                    return getShapeIfExists(this.propertiesFrom, "_stairs");
                }
            );
        }

        private BlockBehaviour.Properties getWallProperties() {
            return this.getShapeProperties(
                properties -> PlatformDependent.BlockBehaviourProperties.ofLegacyCopy(properties).forceSolidOn(),
                () -> {
                    if (this.wallPropertiesFrom != null) {
                        return this.wallPropertiesFrom;
                    }
                    return getShapeIfExists(this.propertiesFrom, "_wall");
                }
            );
        }

        private BlockBehaviour.Properties getFenceProperties() {
            return this.getShapeProperties(
                properties -> {
                    var newProperties = BlockBehaviour.Properties.of()
                        .mapColor(properties.mapColor)
                        .forceSolidOn()
                        .instrument(properties.instrument)
                        .strength(properties.destroyTime, properties.explosionResistance)
                        .sound(properties.soundType);
                    if (properties.ignitedByLava) {
                        newProperties.ignitedByLava();
                    }
                    return newProperties;
                },
                () -> {
                    if (this.fencePropertiesFrom != null) {
                        return this.fencePropertiesFrom;
                    }
                    return getShapeIfExists(this.propertiesFrom, "_fence");
                }
            );
        }

        private BlockBehaviour.Properties getFenceGateProperties() {
            return this.getShapeProperties(
                properties -> {
                    var newProperties = BlockBehaviour.Properties.of()
                        .mapColor(properties.mapColor)
                        .forceSolidOn()
                        .instrument(properties.instrument)
                        .strength(properties.destroyTime, properties.explosionResistance);
                    if (properties.ignitedByLava) {
                        newProperties.ignitedByLava();
                    }
                    return newProperties;
                },
                () -> {
                    if (this.fenceGatePropertiesFrom != null) {
                        return this.fenceGatePropertiesFrom;
                    }
                    return getShapeIfExists(this.propertiesFrom, "_fence_gate");
                }
            );
        }

        private BlockBehaviour.Properties getButtonProperties() {
            return this.getShapeProperties(
                _ -> Blocks.buttonProperties(),
                () -> {
                    if (this.buttonPropertiesFrom != null) {
                        return this.buttonPropertiesFrom;
                    }
                    return getShapeIfExists(this.propertiesFrom, "_button");
                }
            );
        }

        private BlockBehaviour.Properties getPressurePlateProperties() {
            return this.getShapeProperties(
                properties -> {
                    var newProperties = BlockBehaviour.Properties.of()
                        .mapColor(properties.mapColor)
                        .forceSolidOn()
                        .instrument(properties.instrument)
                        .noCollision()
                        .strength(0.5f)
                        .pushReaction(PushReaction.POPPED);
                    if (properties.ignitedByLava) {
                        newProperties.ignitedByLava();
                    }
                    return newProperties;
                },
                () -> {
                    if (this.pressurePlatePropertiesFrom != null) {
                        return this.pressurePlatePropertiesFrom;
                    }
                    return getShapeIfExists(this.propertiesFrom, "_pressure_plate");
                }
            );
        }

        private Block getFullBlockFallback() {
            return Objects.requireNonNull(this.fallback);
        }

        private Block getSlabFallback() {
            if (this.slabFallback != null) {
                return this.slabFallback;
            }
            return Objects.requireNonNull(getShapeIfExists(this.fallback, "_slab"));
        }

        private Block getStairsFallback() {
            if (this.stairsFallback != null) {
                return this.stairsFallback;
            }
            return Objects.requireNonNull(getShapeIfExists(this.fallback, "_stairs"));
        }

        private Block getWallFallback() {
            if (this.wallFallback != null) {
                return this.wallFallback;
            }
            return Objects.requireNonNull(getShapeIfExists(this.fallback, "_wall"));
        }

        private Block getFenceFallback() {
            if (this.fenceFallback != null) {
                return this.fenceFallback;
            }
            return Objects.requireNonNull(getShapeIfExists(this.fallback, "_fence"));
        }

        private Block getFenceGateFallback() {
            if (this.fenceGateFallback != null) {
                return this.fenceGateFallback;
            }
            return Objects.requireNonNull(getShapeIfExists(this.fallback, "_fence_gate"));
        }

        private Block getButtonFallback() {
            if (this.buttonFallback != null) {
                return this.buttonFallback;
            }
            return Objects.requireNonNull(getShapeIfExists(this.fallback, "_button"));
        }

        private Block getPressurePlateFallback() {
            if (this.pressurePlateFallback != null) {
                return this.pressurePlateFallback;
            }
            return Objects.requireNonNull(getShapeIfExists(this.fallback, "_pressure_plate"));
        }

        public @Nullable BlockState getSlabFullBlockFallback() {
            if (this.slabFullBlockFallback != null) {
                return this.slabFullBlockFallback;
            }
            if (this.fallback != null) {
                return this.fallback.defaultBlockState();
            }
            return null;
        }

        public Supplier<BlockState> getStairsBaseState() {
            if (this.stairsBaseState != null) {
                return this.stairsBaseState;
            }
            return () -> {
                if (this.fullBlock) {
                    return BuiltInRegistries.BLOCK.getValue(this.getFullBlockIdentifier()).defaultBlockState();
                }
                if (this.fullBlockOnlyForDataGeneration) {
                    return this.getFullBlockFallback().defaultBlockState();
                }
                return Objects.requireNonNull(this.fallback).defaultBlockState();
            };
        }

        public Supplier<WoodType> getWoodType() {
            if (this.woodType != null) {
                return this.woodType;
            }
            return () -> {
                if (this.fullBlock) {
                    WoodType fromFullBlock = SuCraftBlockType.deriveWoodType(BuiltInRegistries.BLOCK.getValue(this.getFullBlockIdentifier()));
                    if (fromFullBlock != null) {
                        return fromFullBlock;
                    }
                }
                if (this.fullBlockOnlyForDataGeneration) {
                    return SuCraftBlockType.deriveWoodType(this.getFullBlockFallback());
                }
                if (this.fenceGate) {
                    Block fenceGateFallback = this.getFenceGateFallback();
                    WoodType fromFenceGateFallback = SuCraftBlockType.deriveWoodType(fenceGateFallback);
                    if (fromFenceGateFallback != null) {
                        return fromFenceGateFallback;
                    }
                }
                throw new NullPointerException("No wood type given");
            };
        }

        public Supplier<BlockSetType> getBlockSetType() {
            if (this.blockSetType != null) {
                return this.blockSetType;
            }
            return () -> {
                if (this.fullBlock) {
                    BlockSetType fromFullBlock = SuCraftBlockType.deriveBlockSetType(BuiltInRegistries.BLOCK.getValue(this.getFullBlockIdentifier()));
                    if (fromFullBlock != null) {
                        return fromFullBlock;
                    }
                }
                if (this.fullBlockOnlyForDataGeneration) {
                    return SuCraftBlockType.deriveBlockSetType(this.getFullBlockFallback());
                }
                if (this.button) {
                    Block buttonFallback = this.getButtonFallback();
                    BlockSetType fromButtonFallback = SuCraftBlockType.deriveBlockSetType(buttonFallback);
                    if (fromButtonFallback != null) {
                        return fromButtonFallback;
                    }
                }
                if (this.pressurePlate) {
                    Block pressurePlateFallback = this.getButtonFallback();
                    BlockSetType fromPressurePlateFallback = SuCraftBlockType.deriveBlockSetType(pressurePlateFallback);
                    if (fromPressurePlateFallback != null) {
                        return fromPressurePlateFallback;
                    }
                }
                throw new NullPointerException("No block set type given");
            };
        }

        public IntSupplier getButtonTicksToStayPressed() {
            if (this.buttonTicksToStayPressed != null) {
                return this.buttonTicksToStayPressed;
            }
            return () -> ((ButtonBlock) this.getButtonFallback()).ticksToStayPressed;
        }

        public Object[] getFullBlockTags() {
            if (this.fullBlockTags == null) {
                return new Object[0];
            } else {
                return this.fullBlockTags.toArray();
            }
        }

        public Object[] getSlabTags() {
            if (this.slabTags == null) {
                return new Object[0];
            } else {
                return this.slabTags.toArray();
            }
        }

        public Object[] getStairsTags() {
            if (this.stairsTags == null) {
                return new Object[0];
            } else {
                return this.stairsTags.toArray();
            }
        }

        public Object[] getWallTags() {
            if (this.wallTags == null) {
                return new Object[0];
            } else {
                return this.wallTags.toArray();
            }
        }

        public Object[] getFenceTags() {
            if (this.fenceTags == null) {
                return new Object[0];
            } else {
                return this.fenceTags.toArray();
            }
        }

        public Object[] getFenceGateTags() {
            if (this.fenceGateTags == null) {
                return new Object[0];
            } else {
                return this.fenceGateTags.toArray();
            }
        }

        public Object[] getButtonTags() {
            if (this.buttonTags == null) {
                return new Object[0];
            } else {
                return this.buttonTags.toArray();
            }
        }

        public Object[] getPressurePlateTags() {
            if (this.pressurePlateTags == null) {
                return new Object[0];
            } else {
                return this.pressurePlateTags.toArray();
            }
        }

        public Impl build() {
            final Impl[] built = new Impl[1];
            built[0] = new Impl(
                (DataGenerationFlag.isDataGeneration ? this.fullBlock || this.fullBlockOnlyForDataGeneration : this.fullBlock) ? new SuCraftBlockType(this.getFullBlockIdentifier())
                    .update(type -> {
                        if (this.fullBlockBlockBuilders != null) {
                            this.fullBlockBlockBuilders.forEach(type::blockBuild);
                        }
                    })
                    .properties(this::getFullBlockProperties)
                    .fromToBlockMacro(BlockStateMappingMacroTypes.FULL_BLOCK, this.getFullBlockFallback())
                    .update(type -> {
                        if (this.fullBlockConstructorForDataGeneration != null) {
                            type.constructorForDataGeneration(this.fullBlockConstructorForDataGeneration);
                        } else {
                            type.blockConstructorForDataGeneration();
                        }
                    })
                    .dataGenerateModel(factory -> {
                        factory.addBlockFamily(
                            () -> built[0].fullBlockNMS(),
                            () -> this.getFullBlockModelProvider() != null ? (TexturedModel) this.getFullBlockModelProvider().apply(built[0].fullBlockNMS()) : null,
                            () -> this.getFullBlockModelCustomGenerator(),
                            () -> built[0].hasSlab() ? built[0].slabNMS() : null,
                            () -> built[0].hasStairs() ? built[0].stairsNMS() : null,
                            () -> built[0].hasWall() ? built[0].wallNMS() : null,
                            () -> built[0].hasFence() ? built[0].fenceNMS() : null,
                            () -> built[0].hasFenceGate() ? built[0].fenceGateNMS() : null,
                            () -> built[0].hasButton() ? built[0].buttonNMS() : null,
                            () -> built[0].hasPressurePlate() ? built[0].pressurePlateNMS() : null
                        );
                    })
                    .dataGenerateBlockLootTableForDropSelf()
                    .update(type -> {
                        if (!this.fullBlockOnlyForDataGeneration) {
                            type.tryAutomaticFullBlockTags();
                            type.dataGenerateTagUnsafe(this.getFullBlockTags());
                        }
                    })
                    .update(this.getFullBlockFinalizer())
                    : null,
                this.slab ? new SuCraftBlockType(this.getShapeIdentifier("_slab"))
                    .slabFactory()
                    .properties(this::getSlabProperties)
                    .slabMacro(BlockStateMappingMacroTypes.SLAB, this.getSlabFallback(), this.getSlabFullBlockFallback())
                    .slabConstructorForDataGeneration()
                    .dataGenerateModel(factory -> {
                        if (!this.fullBlock) {
                            factory.addBlockSlab(built[0].slabNMS());
                        }
                    })
                    .dataGenerateBlockLootTableForDropSelfSlab()
                    .tryAutomaticSlabTags()
                    .dataGenerateTagUnsafe(this.getSlabTags())
                    .update(this.slabFinalizer)
                    : null,
                this.stairs ? new SuCraftBlockType(this.getShapeIdentifier("_stairs"))
                    .stairsFactory(this.getStairsBaseState())
                    .properties(this::getStairsProperties)
                    .fromToBlockMacro(BlockStateMappingMacroTypes.STAIRS, this.getStairsFallback())
                    .stairsConstructorForDataGeneration(this.getStairsBaseState())
                    .dataGenerateBlockLootTableForDropSelf()
                    .tryAutomaticStairsTags()
                    .dataGenerateTagUnsafe(this.getStairsTags())
                    .update(this.stairsFinalizer)
                    : null,
                this.wall ? new SuCraftBlockType(this.getShapeIdentifier("_wall"))
                    .wallFactory()
                    .properties(this::getWallProperties)
                    .fromToBlockMacro(BlockStateMappingMacroTypes.WALL, this.getWallFallback())
                    .wallConstructorForDataGeneration()
                    .dataGenerateBlockLootTableForDropSelf()
                    .tryAutomaticWallTags()
                    .dataGenerateTagUnsafe(this.getWallTags())
                    .update(this.wallFinalizer)
                    : null,
                this.fence ? new SuCraftBlockType(this.getShapeIdentifier("_fence"))
                    .fenceFactory()
                    .properties(this::getFenceProperties)
                    .fromToBlockMacro(BlockStateMappingMacroTypes.FENCE, this.getFenceFallback())
                    .fenceConstructorForDataGeneration()
                    .dataGenerateBlockLootTableForDropSelf()
                    .tryAutomaticFenceTags()
                    .dataGenerateTagUnsafe(this.getFenceTags())
                    .update(this.fenceFinalizer)
                    : null,
                this.fenceGate ? new SuCraftBlockType(this.getShapeIdentifier("_fence_gate"))
                    .fenceGateFactory(this.getWoodType())
                    .properties(this::getFenceGateProperties)
                    .fromToBlockMacro(BlockStateMappingMacroTypes.FENCE_GATE, this.getFenceGateFallback())
                    .fenceGateConstructorForDataGeneration()
                    .dataGenerateBlockLootTableForDropSelf()
                    .tryAutomaticFenceGateTags()
                    .dataGenerateTagUnsafe(this.getFenceGateTags())
                    .update(this.fenceGateFinalizer)
                    : null,
                this.button ? new SuCraftBlockType(this.getShapeIdentifier("_button"))
                    .buttonFactory(this.getBlockSetType(), this.getButtonTicksToStayPressed())
                    .properties(this::getButtonProperties)
                    .fromToBlockMacro(BlockStateMappingMacroTypes.BUTTON, this.getButtonFallback())
                    .buttonConstructorForDataGeneration()
                    .dataGenerateBlockLootTableForDropSelf()
                    .tryAutomaticButtonTags()
                    .dataGenerateTagUnsafe(this.getButtonTags())
                    .update(this.buttonFinalizer)
                    : null,
                this.pressurePlate ? new SuCraftBlockType(this.getShapeIdentifier("_pressure_plate"))
                    .pressurePlateFactory(this.getBlockSetType())
                    .properties(this::getPressurePlateProperties)
                    .fromToBlockMacro(BlockStateMappingMacroTypes.PRESSURE_PLATE, this.getPressurePlateFallback())
                    .pressurePlateConstructorForDataGeneration()
                    .dataGenerateBlockLootTableForDropSelf()
                    .tryAutomaticPressurePlateTags()
                    .dataGenerateTagUnsafe(this.getPressurePlateTags())
                    .update(this.pressurePlateFinalizer)
                    : null
            );
            return built[0];
        }

        private static String getBasePath(String fullBlockPath) {
            return fullBlockPath.substring(0,
                fullBlockPath.length() - Stream.of("_planks", "_plank", "s_block", "_block", "s", "")
                    .filter(fullBlockPath::endsWith).findFirst().get().length());
        }

        private static @Nullable Block getShapeIfExists(@Nullable Block fullBlock, String suffix) {
            if (fullBlock == null) {
                return null;
            }
            var fullBlockIdentifier = fullBlock.builtInRegistryHolder().key().identifier();
            var shapePath = getBasePath(fullBlockIdentifier.getPath()) + suffix;
            return BuiltInRegistries.BLOCK.getOptional(fullBlockIdentifier.withPath(shapePath)).orElse(null);
        }

    }

    static Builder builder(String fullBlockPath) {
        return new Builder(fullBlockPath);
    }

}
