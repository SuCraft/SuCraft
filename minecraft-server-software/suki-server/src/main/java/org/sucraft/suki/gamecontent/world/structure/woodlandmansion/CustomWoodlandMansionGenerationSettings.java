package org.sucraft.suki.gamecontent.world.structure.woodlandmansion;

import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public final class CustomWoodlandMansionGenerationSettings {

    private CustomWoodlandMansionGenerationSettings() {
        throw new UnsupportedOperationException();
    }

    public static final class BookshelfWoodTypes {

        private BookshelfWoodTypes() {
            throw new UnsupportedOperationException();
        }

        public record Type(
            Block bookshelf,
            Block planks,
            Block slab,
            Block stairs,
            Block fence
        ) {
        }

        public static final Type OAK = new Type(Blocks.BOOKSHELF, Blocks.OAK_PLANKS, Blocks.OAK_SLAB, Blocks.OAK_STAIRS, Blocks.OAK_FENCE);
        public static final Type ACACIA = new Type(BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "acacia_bookshelf")), Blocks.ACACIA_PLANKS, Blocks.ACACIA_SLAB, Blocks.ACACIA_STAIRS, Blocks.ACACIA_FENCE);
        public static final Type BIRCH = new Type(BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "birch_bookshelf")), Blocks.BIRCH_PLANKS, Blocks.BIRCH_SLAB, Blocks.BIRCH_STAIRS, Blocks.BIRCH_FENCE);
        public static final Type DARK_OAK = new Type(BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "dark_oak_bookshelf")), Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_SLAB, Blocks.DARK_OAK_STAIRS, Blocks.DARK_OAK_FENCE);
        public static final Type JUNGLE = new Type(BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "jungle_bookshelf")), Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_SLAB, Blocks.JUNGLE_STAIRS, Blocks.JUNGLE_FENCE);
        public static final Type MANGROVE = new Type(BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "mangrove_bookshelf")), Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_SLAB, Blocks.MANGROVE_STAIRS, Blocks.MANGROVE_FENCE);
        public static final Type SPRUCE = new Type(BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "spruce_bookshelf")), Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_SLAB, Blocks.SPRUCE_STAIRS, Blocks.SPRUCE_FENCE);

        public static final List<Type> PICKABLE_TYPES = List.of(
            OAK,
            OAK,
            OAK,
            OAK,
            OAK,
            ACACIA,
            ACACIA,
            BIRCH,
            BIRCH,
            BIRCH,
            DARK_OAK,
            DARK_OAK,
            JUNGLE,
            JUNGLE,
            MANGROVE,
            SPRUCE,
            SPRUCE,
            SPRUCE,
            SPRUCE
        );

    }

    public static final class TreeTypes {

        private TreeTypes() {
            throw new UnsupportedOperationException();
        }

        public record Type(
            Block leaves,
            Block log,
            Block planks,
            Block fence
        ) {
        }

        public static final Type OAK = new Type(Blocks.OAK_LEAVES, Blocks.OAK_LOG, Blocks.OAK_PLANKS, Blocks.OAK_FENCE);
        public static final Type ACACIA = new Type(Blocks.ACACIA_LEAVES, Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS, Blocks.ACACIA_FENCE);
        public static final Type BIRCH = new Type(Blocks.BIRCH_LEAVES, Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS, Blocks.BIRCH_FENCE);
        public static final Type CHERRY = new Type(Blocks.CHERRY_LEAVES, Blocks.CHERRY_LOG, Blocks.CHERRY_PLANKS, Blocks.CHERRY_FENCE);
        public static final Type DARK_OAK = new Type(Blocks.DARK_OAK_LEAVES, Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_FENCE);
        public static final Type FROST = new Type(BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "frost_leaves")), BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "frost_log")), BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "frost_planks")), BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "frost_fence")));
        public static final Type JUNGLE = new Type(Blocks.JUNGLE_LEAVES, Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_FENCE);
        public static final Type ORANGE_MAPLE = new Type(BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "orange_maple_leaves")), BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "maple_log")), BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "maple_planks")), BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "maple_fence")));
        public static final Type ORANGE_POPLAR = new Type(Blocks.ORANGE_POPLAR_LEAVES, Blocks.POPLAR_LOG, Blocks.POPLAR_PLANKS, Blocks.POPLAR_FENCE);
        public static final Type PALE_OAK = new Type(Blocks.PALE_OAK_LEAVES, Blocks.PALE_OAK_LOG, Blocks.PALE_OAK_PLANKS, Blocks.PALE_OAK_FENCE);
        public static final Type RED_MAPLE = new Type(BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "red_maple_leaves")), BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "maple_log")), BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "maple_planks")), BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "maple_fence")));
        public static final Type RED_POPLAR = new Type(Blocks.RED_POPLAR_LEAVES, Blocks.POPLAR_LOG, Blocks.POPLAR_PLANKS, Blocks.POPLAR_FENCE);
        public static final Type SPRUCE = new Type(Blocks.SPRUCE_LEAVES, Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_FENCE);
        public static final Type YELLOW_MAPLE = new Type(BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "yellow_maple_leaves")), BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "maple_log")), BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "maple_planks")), BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", "maple_fence")));
        public static final Type YELLOW_POPLAR = new Type(Blocks.YELLOW_POPLAR_LEAVES, Blocks.POPLAR_LOG, Blocks.POPLAR_PLANKS, Blocks.POPLAR_FENCE);

        public static final List<Type> PICKABLE_TYPES = List.of(
            OAK,
            ACACIA,
            BIRCH,
            CHERRY,
            DARK_OAK,
            DARK_OAK,
            DARK_OAK,
            DARK_OAK,
            DARK_OAK,
            DARK_OAK,
            DARK_OAK,
            DARK_OAK,
            FROST,
            FROST,
            FROST,
            JUNGLE,
            ORANGE_MAPLE,
            ORANGE_POPLAR,
            PALE_OAK,
            PALE_OAK,
            PALE_OAK,
            PALE_OAK,
            RED_MAPLE,
            RED_POPLAR,
            SPRUCE,
            YELLOW_MAPLE,
            YELLOW_POPLAR
        );

    }

}
