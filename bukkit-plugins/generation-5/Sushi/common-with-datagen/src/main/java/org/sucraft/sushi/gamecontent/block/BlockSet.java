package org.sucraft.sushi.gamecontent.block;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
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

    boolean hasButton();

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

    interface WithFullBlockAndSlab extends WithFullBlock, WithSlab {
    }

    interface WithFullBlockAndSlabAndStairs extends WithFullBlockAndSlab, WithStairs {
    }

    interface WithFullBlockAndSlabAndStairsAndWall extends WithFullBlockAndSlabAndStairs, WithWall {
    }

    interface WithFullBlockAndSlabAndStairsAndWallAndButton extends WithFullBlockAndSlabAndStairsAndWall, WithButton {
    }

    final class Impl implements WithFullBlockAndSlabAndStairsAndWallAndButton {

        private @Nullable SuCraftBlockType fullBlock;
        private @Nullable SuCraftBlockType slab;
        private @Nullable SuCraftBlockType stairs;
        private @Nullable SuCraftBlockType wall;
        private @Nullable SuCraftBlockType button;

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
            @Nullable SuCraftBlockType button
        ) {
            this.fullBlock = fullBlock;
            this.slab = slab;
            this.stairs = stairs;
            this.wall = wall;
            this.button = button;
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
                    this.button
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
        public boolean hasButton() {
            return this.button != null;
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

    }

    final class Builder {

        private @Nullable String fullBlockPath;
        private @Nullable String basePath;
        private boolean fullBlock;
        private boolean stairs;
        private boolean slab;
        private boolean wall;
        private boolean button;
        private @Nullable Consumer<BlockBehaviour.Properties> propertiesFinalizer;
        private BlockBehaviour.@Nullable Properties properties;
        private @Nullable Block propertiesFrom;
        private @Nullable Block slabPropertiesFrom;
        private @Nullable Block stairsPropertiesFrom;
        private @Nullable Block wallPropertiesFrom;
        private @Nullable Block buttonPropertiesFrom;
        private @Nullable Block fallback;
        private @Nullable Block stairsFallback;
        private @Nullable Block slabFallback;
        private @Nullable Block wallFallback;
        private @Nullable Block buttonFallback;
        private @Nullable BlockState slabFullBlockFallback;
        private @Nullable Supplier<BlockState> stairsBaseState;
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

        public Builder button() {
            this.button = true;
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

        private Block getButtonFallback() {
            if (this.buttonFallback != null) {
                return this.buttonFallback;
            }
            return Objects.requireNonNull(getShapeIfExists(this.fallback, "_button"));
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
                        () -> built[0].hasButton() ? built[0].buttonType().getForDataGeneration() : null
                    ))
                    .dataGenerateBlockLootTableForDropSelf()
                    : null,
                this.slab ? new SuCraftBlockType(this.getSuffixedIdentifier("_slab"))
                    .slabFactory()
                    .properties(this::getSlabProperties)
                    .slabMacro(BlockStateMappingMacroTypes.SLAB, this.getSlabFallback(), this.getSlabFullBlockFallback())
                    .slabConstructorForDataGeneration()
                    .dataGenerateBlockLootTableForDropSelfSlab()
                    : null,
                this.stairs ? new SuCraftBlockType(this.getSuffixedIdentifier("_stairs"))
                    .stairsFactory(this.getStairsBaseState())
                    .properties(this::getStairsProperties)
                    .fromToBlockMacro(BlockStateMappingMacroTypes.STAIRS, this.getStairsFallback())
                    .stairsConstructorForDataGeneration(this.getStairsBaseState())
                    .dataGenerateBlockLootTableForDropSelf()
                    : null,
                this.wall ? new SuCraftBlockType(this.getSuffixedIdentifier("_wall"))
                    .wallFactory()
                    .properties(this::getWallProperties)
                    .fromToBlockMacro(BlockStateMappingMacroTypes.WALL, this.getWallFallback())
                    .wallConstructorForDataGeneration()
                    .dataGenerateBlockLootTableForDropSelf()
                    : null,
                this.button ? new SuCraftBlockType(this.getSuffixedIdentifier("_button"))
                    .buttonFactory(this.getBlockSetType(), this.getButtonTicksToStayPressed())
                    .properties(this::getButtonProperties)
                    .fromToBlockMacro(BlockStateMappingMacroTypes.BUTTON, this.getButtonFallback())
                    .buttonConstructorForDataGeneration()
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
