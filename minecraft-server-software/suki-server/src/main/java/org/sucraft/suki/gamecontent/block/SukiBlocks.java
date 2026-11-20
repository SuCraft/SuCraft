package org.sucraft.suki.gamecontent.block;

import com.google.common.base.Suppliers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Locale;
import java.util.function.Supplier;

/**
 * A class that holds lookups for SuCraft blocks.
 */
public final class SukiBlocks {

    private SukiBlocks() {
        throw new UnsupportedOperationException();
    }

    public static Supplier<Block> ACACIA_BOOKSHELF;
    public static Supplier<Block> ACACIA_LADDER;
    public static Supplier<Block> BIRCH_BOOKSHELF;
    public static Supplier<Block> BIRCH_LADDER;
    public static Supplier<Block> CHERRY_BOOKSHELF;
    public static Supplier<Block> CHERRY_LADDER;
    public static Supplier<Block> CRACKED_STONE_BRICKS;
    public static Supplier<Block> CRACKED_STONE_BRICK_SLAB;
    public static Supplier<Block> CRACKED_STONE_BRICK_STAIRS;
    public static Supplier<Block> DARK_OAK_BOOKSHELF;
    public static Supplier<Block> DARK_OAK_LADDER;
    public static Supplier<Block> DIRTY_STONE_BRICKS;
    public static Supplier<Block> DIRTY_STONE_BRICK_SLAB;
    public static Supplier<Block> DIRTY_STONE_BRICK_STAIRS;
    public static Supplier<Block> FROST_FENCE;
    public static Supplier<Block> FROST_LEAVES;
    public static Supplier<Block> FROST_LOG;
    public static Supplier<Block> FROST_PLANKS;
    public static Supplier<Block> JUNGLE_BOOKSHELF;
    public static Supplier<Block> JUNGLE_LADDER;
    public static Supplier<Block> MANGROVE_BOOKSHELF;
    public static Supplier<Block> MANGROVE_LADDER;
    public static Supplier<Block> MAPLE_FENCE;
    public static Supplier<Block> MAPLE_LOG;
    public static Supplier<Block> MAPLE_PLANKS;
    public static Supplier<Block> MOSSY_CRACKED_STONE_BRICKS;
    public static Supplier<Block> MOSSY_CRACKED_STONE_BRICK_SLAB;
    public static Supplier<Block> MOSSY_CRACKED_STONE_BRICK_STAIRS;
    public static Supplier<Block> ORANGE_MAPLE_LEAVES;
    public static Supplier<Block> PEAT;
    public static Supplier<Block> RED_MAPLE_LEAVES;
    public static Supplier<Block> SMOULDERING_PEAT;
    public static Supplier<Block> SPRUCE_BOOKSHELF;
    public static Supplier<Block> SPRUCE_LADDER;
    public static Supplier<Block> SNOWY_STONE_BRICKS;
    public static Supplier<Block> SNOWY_STONE_BRICK_SLAB;
    public static Supplier<Block> SNOWY_STONE_BRICK_STAIRS;
    public static Supplier<Block> SWAMP_BOOKSHELF;
    public static Supplier<Block> SWAMP_FENCE;
    public static Supplier<Block> SWAMP_LADDER;
    public static Supplier<Block> SWAMP_LEAVES;
    public static Supplier<Block> SWAMP_LOG;
    public static Supplier<Block> SWAMP_PLANKS;
    public static Supplier<Block> SWAMP_SLAB;
    public static Supplier<Block> SWAMP_STAIRS;
    public static Supplier<Block> YELLOW_MAPLE_LEAVES;

    private static Supplier<Block> lookup(String name) {
        return Suppliers.memoize(() ->
            BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("sucraft", name))
        );
    }

    static {
        Arrays.stream(SukiBlocks.class.getDeclaredFields())
            .filter(field -> Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers()))
            .filter(field -> field.getType() == Supplier.class)
            .forEach(field -> {
                field.trySetAccessible();
                try {
                    field.set(null, lookup(field.getName().toLowerCase(Locale.ROOT)));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
    }

}
