package org.sucraft.suki.gamecontent.world.structure.stronghold;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.sucraft.suki.gamecontent.block.SukiBlocks;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CustomStrongholdGenerationSettings {

    private CustomStrongholdGenerationSettings() {
        throw new UnsupportedOperationException();
    }

    public static final class DecreasedY {

        private DecreasedY() {
            throw new UnsupportedOperationException();
        }

        public static final int ALWAYS = 5;
        public static final int POTENTIAL_EXTRA = 11;

    }

    public static final class PotentialExtraIncreasedY {

        private PotentialExtraIncreasedY() {
            throw new UnsupportedOperationException();
        }

        public static final int ALL = 0;
        public static final int BADLANDS = 16;
        public static final int HILL = 12;
        public static final int MOUNTAIN = 24;
        public static final int PLATEAU = 24;
        public static final int WINDSWEPT = 16;

    }

    public static final class MinimumPortalRoomYDifference {

        private MinimumPortalRoomYDifference() {
            throw new UnsupportedOperationException();
        }

        public static final int MIN = 20;
        public static final int MAX = 30;

        public static final int MIN_Y_BELOW_SEA_LEVEL = 15;

    }

    public static final class Size {

        private Size() {
            throw new UnsupportedOperationException();
        }

        public static final int MAX_DEPTH = 60; // Vanilla is 50
        public static final int MAX_DEPTH_SURFACE = 115;

        public static final int MAX_PLANAR_DISTANCE = 112; // Vanilla is 112
        public static final int MAX_PLANAR_DISTANCE_SURFACE = 112;

        public static final int LIBRARY_MIN_DEPTH = 5; // Vanilla is 4
        public static final int LIBRARY_MIN_DEPTH_SURFACE = 8;

        public static final int PORTAL_ROOM_MIN_DEPTH = 6; // Vanilla is 5
        public static final int PORTAL_ROOM_MIN_DEPTH_SURFACE = 14;

        public static final int LIBRARY_MAX_AMOUNT = 2; // Vanilla is 2
        public static final int LIBRARY_MAX_AMOUNT_SURFACE = 3;


    }

    public static final class Fountain {

        private Fountain() {
            throw new UnsupportedOperationException();
        }

        public static final int LAVA_RARITY = 5;

    }

    public static final class Cobwebs {

        private Cobwebs() {
            throw new UnsupportedOperationException();
        }

        public record Opportunity(double chance, double ratio) {
        }

        public static final Opportunity FIRST = new Opportunity(0.2, 0.03);
        public static final Opportunity SECOND = new Opportunity(0.16, 0.012);
        public static final Opportunity THIRD = new Opportunity(0.1, 0.007);

        public static final Opportunity[] ALL = new Opportunity[]{FIRST, SECOND, THIRD};

    }

    public static final class StairsReplacements {

        private StairsReplacements() {
            throw new UnsupportedOperationException();
        }

        public static final List<Block> OUTPUTS = List.of(
            Blocks.COBBLESTONE_STAIRS,
            Blocks.COBBLESTONE_STAIRS,
            Blocks.COBBLESTONE_STAIRS,
            Blocks.STONE_STAIRS,
            Blocks.STONE_STAIRS,
            Blocks.STONE_BRICK_STAIRS,
            Blocks.MOSSY_STONE_BRICK_STAIRS,
            SukiBlocks.CRACKED_STONE_BRICK_STAIRS.get(),
            SukiBlocks.MOSSY_CRACKED_STONE_BRICK_STAIRS.get()
        );

        public static final List<Block> OUTPUTS_COLD = List.of(
            Blocks.COBBLESTONE_STAIRS,
            Blocks.COBBLESTONE_STAIRS,
            Blocks.COBBLESTONE_STAIRS,
            Blocks.STONE_STAIRS,
            Blocks.STONE_STAIRS,
            Blocks.STONE_BRICK_STAIRS,
            SukiBlocks.CRACKED_STONE_BRICK_STAIRS.get(),
            SukiBlocks.SNOWY_STONE_BRICK_STAIRS.get(),
            SukiBlocks.SNOWY_STONE_BRICK_STAIRS.get()
        );

        public static final ObjectOpenHashSet<Block> INPUTS = new ObjectOpenHashSet<>();

        static {
            INPUTS.addAll(OUTPUTS);
            INPUTS.addAll(OUTPUTS_COLD);
        }

    }

    public static final class ChipBlocks {

        private ChipBlocks() {
            throw new UnsupportedOperationException();
        }

        public record Chippable(Block fullBlock, Block stairs, Block slab) {
        }

        public static final List<Chippable> LIST = List.of(
            new Chippable(Blocks.STONE_BRICKS, Blocks.STONE_BRICK_STAIRS, Blocks.STONE_BRICK_SLAB),
            new Chippable(Blocks.MOSSY_STONE_BRICKS, Blocks.MOSSY_STONE_BRICK_STAIRS, Blocks.MOSSY_STONE_BRICK_SLAB),
            new Chippable(Blocks.CRACKED_STONE_BRICKS, SukiBlocks.CRACKED_STONE_BRICK_STAIRS.get(), SukiBlocks.CRACKED_STONE_BRICK_SLAB.get()),
            new Chippable(SukiBlocks.MOSSY_CRACKED_STONE_BRICKS.get(), SukiBlocks.MOSSY_CRACKED_STONE_BRICK_STAIRS.get(), SukiBlocks.MOSSY_CRACKED_STONE_BRICK_SLAB.get()),
            new Chippable(SukiBlocks.DIRTY_STONE_BRICKS.get(), SukiBlocks.DIRTY_STONE_BRICK_STAIRS.get(), SukiBlocks.DIRTY_STONE_BRICK_SLAB.get()),
            new Chippable(SukiBlocks.SNOWY_STONE_BRICKS.get(), SukiBlocks.SNOWY_STONE_BRICK_STAIRS.get(), SukiBlocks.SNOWY_STONE_BRICK_SLAB.get())
        );

        public static final Map<Block, Chippable> BY_FULL_BLOCK = LIST.stream().collect(Collectors.toMap(Chippable::fullBlock, Function.identity()));
        public static final Map<Block, Chippable> BY_STAIRS = LIST.stream().collect(Collectors.toMap(Chippable::stairs, Function.identity()));

        public static final int FROM_ABOVE_RARITY = 8;
        public static final int FROM_BELOW_RARITY = 5;
        public static final int FROM_ABOVE_SLAB_RARITY = 10;
        public static final int FROM_BELOW_SLAB_RARITY = 2;

    }

    public static final class LibraryWoodTypes {

        private LibraryWoodTypes() {
            throw new UnsupportedOperationException();
        }

        public record Type(
            Block planks,
            Block bookshelf,
            Block fence,
            Block ladder
        ) {
        }

        public static final Type OAK = new Type(Blocks.OAK_PLANKS, Blocks.BOOKSHELF, Blocks.OAK_FENCE, Blocks.LADDER);
        public static final Type ACACIA = new Type(Blocks.ACACIA_PLANKS, SukiBlocks.ACACIA_BOOKSHELF.get(), Blocks.ACACIA_FENCE, SukiBlocks.ACACIA_LADDER.get());
        public static final Type BIRCH = new Type(Blocks.BIRCH_PLANKS, SukiBlocks.BIRCH_BOOKSHELF.get(), Blocks.BIRCH_FENCE, SukiBlocks.BIRCH_LADDER.get());
        public static final Type CHERRY = new Type(Blocks.CHERRY_PLANKS, SukiBlocks.CHERRY_BOOKSHELF.get(), Blocks.CHERRY_FENCE, SukiBlocks.CHERRY_LADDER.get());
        public static final Type DARK_OAK = new Type(Blocks.DARK_OAK_PLANKS, SukiBlocks.DARK_OAK_BOOKSHELF.get(), Blocks.DARK_OAK_FENCE, SukiBlocks.DARK_OAK_LADDER.get());
        public static final Type JUNGLE = new Type(Blocks.JUNGLE_PLANKS, SukiBlocks.JUNGLE_BOOKSHELF.get(), Blocks.JUNGLE_FENCE, SukiBlocks.JUNGLE_LADDER.get());
        public static final Type MANGROVE = new Type(Blocks.MANGROVE_PLANKS, SukiBlocks.MANGROVE_BOOKSHELF.get(), Blocks.MANGROVE_FENCE, SukiBlocks.MANGROVE_LADDER.get());
        public static final Type SPRUCE = new Type(Blocks.SPRUCE_PLANKS, SukiBlocks.SPRUCE_BOOKSHELF.get(), Blocks.SPRUCE_FENCE, SukiBlocks.SPRUCE_LADDER.get());
        public static final Type SWAMP = new Type(SukiBlocks.SWAMP_PLANKS.get(), SukiBlocks.SWAMP_BOOKSHELF.get(), SukiBlocks.SWAMP_FENCE.get(), SukiBlocks.SWAMP_LADDER.get());

        public static final List<Type> PICKABLE_TYPES = List.of(
            OAK,
            OAK,
            OAK,
            OAK,
            OAK,
            ACACIA,
            ACACIA,
            ACACIA,
            BIRCH,
            BIRCH,
            CHERRY,
            DARK_OAK,
            DARK_OAK,
            DARK_OAK,
            JUNGLE,
            JUNGLE,
            JUNGLE,
            MANGROVE,
            SPRUCE,
            SPRUCE,
            SPRUCE,
            SPRUCE,
            SPRUCE,
            SWAMP
        );

    }

    public static final class CoverWithFoliage {

        private CoverWithFoliage() {
            throw new UnsupportedOperationException();
        }

        public static final int GAP_RARITY = 20;
        public static final int SHORT_GRASS_RARITY = 14;
        public static final int LEAF_LITTER_RARITY = 11;

    }

}
