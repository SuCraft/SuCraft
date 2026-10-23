package org.sucraft.sushi.gamecontent.block;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
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
import org.bukkit.block.BlockType;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.Nullable;
import org.sucraft.sushi.gamecontent.item.SuCraftItemType;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroTypes;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface BlockSet {

    BlockType[] values();

    Block[] valuesNMS();

    Block[] valuesForDataGeneration();

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

        Block fullBlockForDataGeneration();

        SuCraftBlockType fullBlockType();

        ItemType fullBlockItem();

        Item fullBlockItemNMS();

        Item fullBlockItemForDataGeneration();

        SuCraftItemType fullBlockItemType();

        default Item asItem() {
            return this.fullBlockItemNMS();
        }

    }

    interface WithSlab extends BlockSet {

        BlockType slab();

        Block slabNMS();

        Block slabForDataGeneration();

        SuCraftBlockType slabType();

        ItemType slabItem();

        Item slabItemNMS();

        Item slabItemForDataGeneration();

        SuCraftItemType slabItemType();

    }

    interface WithStairs extends BlockSet {

        BlockType stairs();

        Block stairsNMS();

        Block stairsForDataGeneration();

        SuCraftBlockType stairsType();

        ItemType stairsItem();

        Item stairsItemNMS();

        Item stairsItemForDataGeneration();

        SuCraftItemType stairsItemType();

    }

    interface WithWall extends BlockSet {

        BlockType wall();

        Block wallNMS();

        SuCraftBlockType wallType();

        Block wallForDataGeneration();

        ItemType wallItem();

        Item wallItemNMS();

        SuCraftItemType wallItemType();

        Item wallItemForDataGeneration();

    }

    interface WithFence extends BlockSet {

        BlockType fence();

        Block fenceNMS();

        SuCraftBlockType fenceType();

        Block fenceForDataGeneration();

        ItemType fenceItem();

        Item fenceItemNMS();

        SuCraftItemType fenceItemType();

        Item fenceItemForDataGeneration();

    }

    interface WithFenceGate extends BlockSet {

        BlockType fenceGate();

        Block fenceGateNMS();

        SuCraftBlockType fenceGateType();

        Block fenceGateForDataGeneration();

        ItemType fenceGateItem();

        Item fenceGateItemNMS();

        SuCraftItemType fenceGateItemType();

        Item fenceGateItemForDataGeneration();

    }

    interface WithButton extends BlockSet {

        BlockType button();

        Block buttonNMS();

        SuCraftBlockType buttonType();

        Block buttonForDataGeneration();

        ItemType buttonItem();

        Item buttonItemNMS();

        SuCraftItemType buttonItemType();

        Item buttonItemForDataGeneration();

    }

    interface WithPressurePlate extends BlockSet {

        BlockType pressurePlate();

        Block pressurePlateNMS();

        SuCraftBlockType pressurePlateType();

        Block pressurePlateForDataGeneration();

        ItemType pressurePlateItem();

        Item pressurePlateItemNMS();

        SuCraftItemType pressurePlateItemType();

        Item pressurePlateItemForDataGeneration();

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

    final class Impl implements WithFullBlockAndSlabAndStairsAndWallAndFenceAndFenceGateAndButtonAndPressurePlate, WithFullBlockAndSlabAndStairsAndWallAndFenceAndButtonAndPressurePlate {

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
        private Block @Nullable [] valuesForDataGeneration;
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
        public Block[] valuesForDataGeneration() {
            if (this.valuesForDataGeneration == null) {
                this.valuesForDataGeneration = Arrays.stream(this.types())
                    .map(SuCraftBlockType::getForDataGeneration).toArray(Block[]::new);
            }
            return this.valuesForDataGeneration;
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
        public Block fullBlockForDataGeneration() {
            return this.fullBlock.getForDataGeneration();
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
        public Item fullBlockItemForDataGeneration() {
            return this.fullBlock.itemForDataGeneration();
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
        public Block slabForDataGeneration() {
            return this.slab.getForDataGeneration();
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
        public Item slabItemForDataGeneration() {
            return this.slab.itemForDataGeneration();
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
        public Block stairsForDataGeneration() {
            return this.stairs.getForDataGeneration();
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
        public Item stairsItemForDataGeneration() {
            return this.stairs.itemForDataGeneration();
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
        public Block wallForDataGeneration() {
            return this.wall.getForDataGeneration();
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
        public Item wallItemForDataGeneration() {
            return this.wall.itemForDataGeneration();
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
        public Block fenceForDataGeneration() {
            return this.fence.getForDataGeneration();
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
        public Item fenceItemForDataGeneration() {
            return this.fence.itemForDataGeneration();
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
        public Block fenceGateForDataGeneration() {
            return this.fenceGate.getForDataGeneration();
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
        public Item fenceGateItemForDataGeneration() {
            return this.fenceGate.itemForDataGeneration();
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
        public Block buttonForDataGeneration() {
            return this.button.getForDataGeneration();
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
        public Item buttonItemForDataGeneration() {
            return this.button.itemForDataGeneration();
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
        public Block pressurePlateForDataGeneration() {
            return this.pressurePlate.getForDataGeneration();
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
        public Item pressurePlateItemForDataGeneration() {
            return this.pressurePlate.itemForDataGeneration();
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

        public Builder slab() {
            this.slab = true;
            return this;
        }

        public Builder stairs() {
            this.stairs = true;
            return this;
        }

        public Builder wall() {
            this.wall = true;
            return this;
        }

        public Builder fence() {
            this.fence = true;
            return this;
        }

        public Builder fenceGate() {
            this.fenceGate = true;
            return this;
        }

        public Builder button() {
            this.button = true;
            return this;
        }

        public Builder pressurePlate() {
            this.pressurePlate = true;
            return this;
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

        public Impl build() {
            final Impl[] built = new Impl[1];
            built[0] = new Impl(
                this.fullBlock ? new SuCraftBlockType(this.getFullBlockIdentifier())
                    .properties(this::getFullBlockProperties)
                    .fromToBlockMacro(BlockStateMappingMacroTypes.FULL_BLOCK, this.getFullBlockFallback())
                    .blockConstructorForDataGeneration()
                    .dataGenerateModel(factory -> factory.addBlockFamily(
                        () -> built[0].fullBlockType().getForDataGeneration(),
                        () -> built[0].hasSlab() ? built[0].slabType().getForDataGeneration() : null,
                        () -> built[0].hasStairs() ? built[0].stairsType().getForDataGeneration() : null,
                        () -> built[0].hasWall() ? built[0].wallType().getForDataGeneration() : null,
                        () -> built[0].hasFence() ? built[0].fenceType().getForDataGeneration() : null,
                        () -> built[0].hasFenceGate() ? built[0].fenceGateType().getForDataGeneration() : null,
                        () -> built[0].hasButton() ? built[0].buttonType().getForDataGeneration() : null,
                        () -> built[0].hasPressurePlate() ? built[0].pressurePlateType().getForDataGeneration() : null
                    ))
                    .dataGenerateBlockLootTableForDropSelf()
                    : null,
                this.slab ? new SuCraftBlockType(this.getShapeIdentifier("_slab"))
                    .slabFactory()
                    .properties(this::getSlabProperties)
                    .slabMacro(BlockStateMappingMacroTypes.SLAB, this.getSlabFallback(), this.getSlabFullBlockFallback())
                    .slabConstructorForDataGeneration()
                    .dataGenerateBlockLootTableForDropSelfSlab()
                    : null,
                this.stairs ? new SuCraftBlockType(this.getShapeIdentifier("_stairs"))
                    .stairsFactory(this.getStairsBaseState())
                    .properties(this::getStairsProperties)
                    .fromToBlockMacro(BlockStateMappingMacroTypes.STAIRS, this.getStairsFallback())
                    .stairsConstructorForDataGeneration(this.getStairsBaseState())
                    .dataGenerateBlockLootTableForDropSelf()
                    : null,
                this.wall ? new SuCraftBlockType(this.getShapeIdentifier("_wall"))
                    .wallFactory()
                    .properties(this::getWallProperties)
                    .fromToBlockMacro(BlockStateMappingMacroTypes.WALL, this.getWallFallback())
                    .wallConstructorForDataGeneration()
                    .dataGenerateBlockLootTableForDropSelf()
                    : null,
                this.fence ? new SuCraftBlockType(this.getShapeIdentifier("_fence"))
                    .fenceFactory()
                    .properties(this::getFenceProperties)
                    .fromToBlockMacro(BlockStateMappingMacroTypes.FENCE, this.getFenceFallback())
                    .fenceConstructorForDataGeneration()
                    .dataGenerateBlockLootTableForDropSelf()
                    : null,
                this.fenceGate ? new SuCraftBlockType(this.getShapeIdentifier("_fence_gate"))
                    .fenceGateFactory(this.getWoodType())
                    .properties(this::getFenceGateProperties)
                    .fromToBlockMacro(BlockStateMappingMacroTypes.FENCE_GATE, this.getFenceGateFallback())
                    .fenceGateConstructorForDataGeneration()
                    .dataGenerateBlockLootTableForDropSelf()
                    : null,
                this.button ? new SuCraftBlockType(this.getShapeIdentifier("_button"))
                    .buttonFactory(this.getBlockSetType(), this.getButtonTicksToStayPressed())
                    .properties(this::getButtonProperties)
                    .fromToBlockMacro(BlockStateMappingMacroTypes.BUTTON, this.getButtonFallback())
                    .buttonConstructorForDataGeneration()
                    .dataGenerateBlockLootTableForDropSelf()
                    : null,
                this.pressurePlate ? new SuCraftBlockType(this.getShapeIdentifier("_pressure_plate"))
                    .pressurePlateFactory(this.getBlockSetType())
                    .properties(this::getPressurePlateProperties)
                    .fromToBlockMacro(BlockStateMappingMacroTypes.PRESSURE_PLATE, this.getPressurePlateFallback())
                    .pressurePlateConstructorForDataGeneration()
                    .dataGenerateBlockLootTableForDropSelf()
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
