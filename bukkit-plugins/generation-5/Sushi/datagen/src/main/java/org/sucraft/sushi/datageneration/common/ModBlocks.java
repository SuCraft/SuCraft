package org.sucraft.sushi.datageneration.common;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class ModBlocks {

    // Stone / Regular / Bricks / Dirty
    public static Block DIRTY_STONE_BRICKS;
    public static SlabBlock DIRTY_STONE_BRICK_SLAB;
    public static StairBlock DIRTY_STONE_BRICK_STAIRS;
    public static WallBlock DIRTY_STONE_BRICK_WALL;

    // Stone / Regular / Bricks / Snowy
    public static Block SNOWY_STONE_BRICKS;
    public static SlabBlock SNOWY_STONE_BRICK_SLAB;
    public static StairBlock SNOWY_STONE_BRICK_STAIRS;
    public static WallBlock SNOWY_STONE_BRICK_WALL;

    // Stone / Tuff / Polished
    public static Block POLISHED_TUFF;
    public static SlabBlock POLISHED_TUFF_SLAB;
    public static StairBlock POLISHED_TUFF_STAIRS;
    public static WallBlock POLISHED_TUFF_WALL;

    // Stone / Tuff / Bricks
    public static Block TUFF_BRICKS;
    public static SlabBlock TUFF_BRICK_SLAB;
    public static StairBlock TUFF_BRICK_STAIRS;
    public static WallBlock TUFF_BRICK_WALL;

    public static Block initialize() {
        return Arrays.stream(ModBlocks.class.getDeclaredFields())
            .filter(field -> Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers()))
            .filter(field -> Block.class.isAssignableFrom(field.getType()))
            .map(field -> {
                try {
                    field.trySetAccessible();
                    var path = field.getName().toLowerCase(Locale.ROOT);
                    var id = Identifier.fromNamespaceAndPath("sucraft", path);
                    var key = ResourceKey.create(Registries.BLOCK, id);
                    var properties = BlockBehaviour.Properties.of().setId(key);
                    var value = Registry.register(
                        BuiltInRegistries.BLOCK,
                        key,
                        construct(field, properties)
                    );
                    field.set(null, value);
                    return value;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).reduce((_, value) -> value).orElseThrow();
    }

    private static @Nullable Block getAlreadyRegisteredBlock(String path) throws Exception {
        for (var owner : List.of(ModBlocks.class, Blocks.class)) {
            try {
                var field = owner.getDeclaredField(path.toUpperCase(Locale.ROOT));
                if (Modifier.isStatic(field.getModifiers()) && Block.class.isAssignableFrom(field.getType())) {
                    return (Block) field.get(null);
                }
                field.trySetAccessible();
            } catch (Exception _) {
            }
        }
        return null;
    }

    private static Block construct(Field field, BlockBehaviour.Properties properties) throws Exception {
        var type = (Class<? extends Block>) field.getType();
        if (StairBlock.class.isAssignableFrom(type)) {
            var withoutStairsName = field.getName().substring(0, field.getName().length() - "_STAIRS".length());
            var base = getAlreadyRegisteredBlock(withoutStairsName);
            if (base == null) {
                base = getAlreadyRegisteredBlock(withoutStairsName + "S");
            }
            return type.getDeclaredConstructor(BlockState.class, BlockBehaviour.Properties.class).newInstance(base.defaultBlockState(), properties);
        }
        return type.getDeclaredConstructor(BlockBehaviour.Properties.class).newInstance(properties);
    }

}
