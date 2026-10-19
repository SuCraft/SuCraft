package org.sucraft.sushi.gamecontent.item;

import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import net.kyori.adventure.key.Key;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.bukkit.Registry;
import org.bukkit.craftbukkit.inventory.CraftItemType;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.Nullable;
import spout.api.SpoutRegistryKey;
import spout.api.clientview.packetmapping.itemstack.ItemStackMapping;
import spout.api.gamecontent.datadriven.item.registry.ItemTypeRegistryEntry;
import spout.api.gamecontent.datadriven.item.registry.ItemTypeRegistryEntryBuilderNMS;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public final class SuCraftItems {

    private SuCraftItems() {
        throw new UnsupportedOperationException();
    }

    // Stone / Regular / Bricks / Dirty
    public static final Type DIRTY_STONE_BRICKS = itemType("dirty_stone_bricks")
        .blockFactory();
    public static final Type DIRTY_STONE_BRICK_SLAB = itemType("dirty_stone_brick_slab")
        .blockFactory();
    public static final Type DIRTY_STONE_BRICK_STAIRS = itemType("dirty_stone_brick_stairs")
        .blockFactory();
    public static final Type DIRTY_STONE_BRICK_WALL = itemType("dirty_stone_brick_wall")
        .blockFactory();

    // Stone / Regular / Bricks / Snowy
    public static final Type SNOWY_STONE_BRICKS = itemType("snowy_stone_bricks")
        .blockFactory();
    public static final Type SNOWY_STONE_BRICK_SLAB = itemType("snowy_stone_brick_slab")
        .blockFactory();
    public static final Type SNOWY_STONE_BRICK_STAIRS = itemType("snowy_stone_brick_stairs")
        .blockFactory();
    public static final Type SNOWY_STONE_BRICK_WALL = itemType("snowy_stone_brick_wall")
        .blockFactory();

    // Stone / Tuff / Polished
    public static final Type ROUGH_POLISHED_TUFF = itemType("polished_tuff")
        .blockFactory();
    public static final Type ROUGH_POLISHED_TUFF_SLAB = itemType("polished_tuff_slab")
        .blockFactory();
    public static final Type ROUGH_POLISHED_TUFF_STAIRS = itemType("polished_tuff_stairs")
        .blockFactory();
    public static final Type ROUGH_POLISHED_TUFF_WALL = itemType("polished_tuff_wall")
        .blockFactory();

    // Stone / Tuff / Bricks
    public static final Type ROUGH_TUFF_BRICKS = itemType("tuff_bricks")
        .blockFactory();
    public static final Type ROUGH_TUFF_BRICK_SLAB = itemType("tuff_brick_slab")
        .blockFactory();
    public static final Type ROUGH_TUFF_BRICK_STAIRS = itemType("tuff_brick_stairs")
        .blockFactory();
    public static final Type ROUGH_TUFF_BRICK_WALL = itemType("tuff_brick_wall")
        .blockFactory();

    private static Type itemType(String path) {
        return new Type(path);
    }

    public static final class Type {

        public final Key key;
        public final Identifier identifier;
        private @Nullable ItemType value;
        private @Nullable Item valueNMS;

        private List<Consumer<ItemTypeRegistryEntryBuilderNMS>> itemBuilding = new ArrayList<>();

        private Type(String path) {
            this(Key.key("sucraft", path));
        }

        private Type(Key key) {
            this.key = key;
            this.identifier = Identifier.fromNamespaceAndPath(key.namespace(), key.value());
        }

        public TypedKey<ItemType> registryKey() {
            return TypedKey.create(RegistryKey.ITEM, this.key);
        }

        public TypedKey<ItemStackMapping> mappingRegistryKey() {
            return TypedKey.create(SpoutRegistryKey.ITEM_STACK_MAPPING, this.key);
        }

        public ItemType get() {
            ItemType value = this.value;
            if (value == null) {
                value = Registry.ITEM.get(this.key);
                this.value = value;
            }
            return value;
        }

        public Item getNMS() {
            Item value = this.valueNMS;
            if (value == null) {
                value = CraftItemType.bukkitToMinecraftNew(this.get());
                this.valueNMS = value;
            }
            return value;
        }

        public void applyToItemBuilder(ItemTypeRegistryEntryBuilderNMS builder) {
            this.itemBuilding.forEach(it -> it.accept(builder));
            this.itemBuilding = null;
        }

        public Type itemBuild(Consumer<ItemTypeRegistryEntryBuilderNMS> builderConsumer) {
            this.itemBuilding.add(builderConsumer);
            return this;
        }

        public Type blockFactory() {
            return this.itemBuild(ItemTypeRegistryEntry.Builder::inheritsFromBlock);
        }

    }

    private static Type[] values;

    public static Type[] values() {
        if (values == null) {
            values = Arrays.stream(SuCraftItems.class.getDeclaredFields())
                .filter(field ->
                    Modifier.isPublic(field.getModifiers()) &&
                        Modifier.isStatic(field.getModifiers()) &&
                        Modifier.isFinal(field.getModifiers()) &&
                        field.getType() == Type.class)
                .map(field -> {
                    field.trySetAccessible();
                    try {
                        return (Type) field.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(Type[]::new);
        }
        return values;
    }

}
