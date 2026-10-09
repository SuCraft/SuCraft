package org.sucraft.sushi.datageneration.common;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Locale;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModItems {

    public static BlockItem SNOWY_STONE_BRICKS;
    public static BlockItem SNOWY_STONE_BRICK_SLAB;
    public static BlockItem SNOWY_STONE_BRICK_STAIRS;

    public static Item initialize() {
        return Arrays.stream(ModItems.class.getDeclaredFields())
            .filter(field -> Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers()))
            .filter(field -> Item.class.isAssignableFrom(field.getType()))
            .map(field -> {
                try {
                    field.trySetAccessible();
                    var path = field.getName().toLowerCase(Locale.ROOT);
                    var id = Identifier.fromNamespaceAndPath("sucraft", path);
                    var key = ResourceKey.create(Registries.ITEM, id);
                    var properties = new Item.Properties().setId(key);
                    Block block;
                    try {
                        Field blockField = ModBlocks.class.getDeclaredField(field.getName());
                        block = (Block) blockField.get(null);
                    } catch (NoSuchFieldException _) {
                        block = null;
                    }
                    var value = Registry.register(
                        BuiltInRegistries.ITEM,
                        key,
                        block != null ? new BlockItem(block, properties) : new Item(properties)
                    );
                    field.set(null, value);
                    return value;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).reduce((_, value) -> value).orElseThrow();
    }

}
