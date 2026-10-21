package org.sucraft.sushi.gamecontent.block;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.Nullable;
import org.sucraft.sushi.gamecontent.item.SuCraftItems;
import spout.clientview.packetmapping.blockstate.macro.type.BlockStateMappingMacroTypes;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface BlockSet {

    BlockType[] values();

    Block[] valuesNMS();

    SuCraftBlocks.Type[] types();

    ItemType[] items();

    Item[] itemsNMS();

    SuCraftItems.Type[] itemTypes();

    interface WithFullBlock extends BlockSet {

        BlockType fullBlock();

        Block fullBlockNMS();

        SuCraftBlocks.Type fullBlockType();

        ItemType fullBlockItem();

        Item fullBlockItemNMS();

        SuCraftItems.Type fullBlockItemType();

    }

    interface WithStairs extends BlockSet {

        BlockType slab();

        Block slabNMS();

        SuCraftBlocks.Type slabType();

        ItemType slabItem();

        Item slabItemNMS();

        SuCraftItems.Type slabItemType();

    }

    interface WithSlab extends BlockSet {

        BlockType stairs();

        Block stairsNMS();

        SuCraftBlocks.Type stairsType();

        ItemType stairsItem();

        Item stairsItemNMS();

        SuCraftItems.Type stairsItemType();

    }

    interface WithWall extends BlockSet {

        BlockType wall();

        Block wallNMS();

        SuCraftBlocks.Type wallType();

        ItemType wallItem();

        Item wallItemNMS();

        SuCraftItems.Type wallItemType();

    }

    interface WithFullBlockAndSlab extends WithFullBlock, WithStairs {
    }

    interface WithFullBlockAndSlabAndStairs extends WithFullBlockAndSlab, WithSlab {
    }

    interface WithFullBlockAndSlabAndStairsAndWall extends WithFullBlockAndSlabAndStairs, WithWall {
    }

    final class Impl implements WithFullBlockAndSlabAndStairsAndWall {

        private SuCraftBlocks.@Nullable Type fullBlock;
        private SuCraftBlocks.@Nullable Type slab;
        private SuCraftBlocks.@Nullable Type stairs;
        private SuCraftBlocks.@Nullable Type wall;

        private BlockType @Nullable [] values;
        private Block @Nullable [] valuesNMS;
        private SuCraftBlocks.Type @Nullable [] types;
        private ItemType @Nullable [] items;
        private Item @Nullable [] itemsNMS;
        private SuCraftItems.Type @Nullable [] itemTypes;

        Impl(
            SuCraftBlocks.@Nullable Type fullBlock,
            SuCraftBlocks.@Nullable Type slab,
            SuCraftBlocks.@Nullable Type stairs,
            SuCraftBlocks.@Nullable Type wall
        ) {
            this.fullBlock = fullBlock;
            this.slab = slab;
            this.stairs = stairs;
            this.wall = wall;
        }

        @Override
        public BlockType[] values() {
            if (this.values == null) {
                this.values = Arrays.stream(this.types())
                    .map(SuCraftBlocks.Type::get).toArray(BlockType[]::new);
            }
            return this.values;
        }

        @Override
        public Block[] valuesNMS() {
            if (this.valuesNMS == null) {
                this.valuesNMS = Arrays.stream(this.types())
                    .map(SuCraftBlocks.Type::getNMS).toArray(Block[]::new);
            }
            return this.valuesNMS;
        }

        @Override
        public SuCraftBlocks.Type[] types() {
            if (this.types == null) {
                this.types = Stream.of(this.fullBlock, this.slab, this.stairs, this.wall)
                    .filter(Objects::nonNull).toArray(SuCraftBlocks.Type[]::new);
            }
            return this.types;
        }

        @Override
        public ItemType[] items() {
            if (this.items == null) {
                this.items = Arrays.stream(this.itemTypes())
                    .map(SuCraftItems.Type::get).toArray(ItemType[]::new);
            }
            return this.items;
        }

        @Override
        public Item[] itemsNMS() {
            if (this.itemsNMS == null) {
                this.itemsNMS = Arrays.stream(this.itemTypes())
                    .map(SuCraftItems.Type::getNMS).toArray(Item[]::new);
            }
            return this.itemsNMS;
        }

        @Override
        public SuCraftItems.Type[] itemTypes() {
            if (this.itemTypes == null) {
                this.itemTypes = Arrays.stream(this.types())
                    .map(SuCraftBlocks.Type::item).filter(Objects::nonNull).toArray(SuCraftItems.Type[]::new);
            }
            return this.itemTypes;
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
        public SuCraftBlocks.Type fullBlockType() {
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
        public SuCraftItems.Type fullBlockItemType() {
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
        public SuCraftBlocks.Type slabType() {
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
        public SuCraftItems.Type slabItemType() {
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
        public SuCraftBlocks.Type stairsType() {
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
        public SuCraftItems.Type stairsItemType() {
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
        public SuCraftBlocks.Type wallType() {
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
        public SuCraftItems.Type wallItemType() {
            return this.wall.itemType();
        }

    }

    final class Builder {

        private @Nullable String fullBlockPath;
        private @Nullable String basePath;
        private boolean fullBlock;
        private boolean stairs;
        private boolean slab;
        private boolean wall;
        private @Nullable Consumer<BlockBehaviour.Properties> propertiesFinalizer;
        private BlockBehaviour.@Nullable Properties properties;
        private @Nullable Block propertiesFrom;
        private @Nullable Block slabPropertiesFrom;
        private @Nullable Block stairsPropertiesFrom;
        private @Nullable Block wallPropertiesFrom;
        private @Nullable Block fallback;
        private @Nullable Block stairsFallback;
        private @Nullable Block slabFallback;
        private @Nullable Block wallFallback;
        private @Nullable BlockState slabFullBlockFallback;
        private @Nullable Supplier<BlockState> stairsBaseState;

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

        public Builder slabFullBlockFallback(BlockState fullBlockFallback) {
            this.slabFullBlockFallback = fullBlockFallback;
            return this;
        }

        public Builder slabFullBlockFallback(Block fullBlockFallback) {
            return this.slabFullBlockFallback(fullBlockFallback.defaultBlockState());
        }

        public Builder stairsBaseState(Supplier<BlockState> stairsBaseState) {
            this.stairsBaseState = stairsBaseState;
            return this;
        }

        public Builder stairsBaseState(BlockState stairsBaseState) {
            return this.stairsBaseState(() -> stairsBaseState);
        }

        public Builder stairsBaseState(Block stairsBaseState) {
            return this.stairsBaseState(stairsBaseState::defaultBlockState);
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
                unfinalizedProperties = PlatformDependentOperations.ofFullCopy(this.properties);
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
                properties -> PlatformDependentOperations.ofLegacyCopy(properties).isViewBlocking(PlatformDependentOperations.NEAR_PLANE_INTERSECTS_OUTLINE),
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
                properties -> PlatformDependentOperations.ofLegacyCopy(properties).isViewBlocking(PlatformDependentOperations.NEAR_PLANE_INTERSECTS_OUTLINE),
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
                properties -> PlatformDependentOperations.ofLegacyCopy(properties).forceSolidOn(),
                () -> {
                    if (this.wallPropertiesFrom != null) {
                        return this.wallPropertiesFrom;
                    }
                    return getShapeIfExists(this.propertiesFrom, "_wall");
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

        public Impl build() {
            return new Impl(
                this.fullBlock ? new SuCraftBlocks.Type(this.getFullBlockIdentifier())
                    .properties(this.getFullBlockProperties())
                    .fromToBlockMacro(BlockStateMappingMacroTypes.FULL_BLOCK, this.getFullBlockFallback())
                    : null,
                this.slab ? new SuCraftBlocks.Type(this.getSuffixedIdentifier("_slab"))
                    .slabFactory()
                    .properties(this.getSlabProperties())
                    .slabMacro(BlockStateMappingMacroTypes.SLAB, this.getSlabFallback(), this.getSlabFullBlockFallback())
                    : null,
                this.stairs ? new SuCraftBlocks.Type(this.getSuffixedIdentifier("_stairs"))
                    .stairsFactory(this.getStairsBaseState())
                    .properties(this.getStairsProperties())
                    .fromToBlockMacro(BlockStateMappingMacroTypes.STAIRS, this.getStairsFallback())
                    : null,
                this.wall ? new SuCraftBlocks.Type(this.getSuffixedIdentifier("_wall"))
                    .wallFactory()
                    .properties(this.getWallProperties())
                    .fromToBlockMacro(BlockStateMappingMacroTypes.WALL, this.getWallFallback())
                    : null
            );
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
