package org.sucraft.sushi.datageneration.common;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Locale;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {

    public static Block SNOWY_STONE_BRICKS;
    public static Block SNOWY_STONE_BRICK_SLAB;
    public static Block SNOWY_STONE_BRICK_STAIRS;

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
                        new Block(properties)
                    );
                    field.set(null, value);
                    return value;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).reduce((_, value) -> value).orElseThrow();
    }

}
