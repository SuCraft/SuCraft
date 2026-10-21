package org.sucraft.sushi.gamecontent.item;

import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import net.kyori.adventure.key.Key;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.bukkit.Registry;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.Nullable;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;
import spout.api.SpoutRegistryKey;
import spout.api.clientview.packetmapping.itemstack.ItemStackMapping;
import spout.api.gamecontent.datadriven.item.registry.ItemTypeRegistryEntry;
import spout.api.gamecontent.datadriven.item.registry.ItemTypeRegistryEntryBuilderNMS;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class SuCraftItems {

    private SuCraftItems() {
        throw new UnsupportedOperationException();
    }

    private static Type itemType(String path) {
        return new Type(path);
    }

    public static final class Type {

        public final Key key;
        public final Identifier identifier;
        private @Nullable ItemType value;
        private @Nullable Item valueNMS;

        private List<Consumer<ItemTypeRegistryEntryBuilderNMS>> itemBuilding = new ArrayList<>();

        public Type(String path) {
            this(Key.key("sucraft", path));
        }

        public Type(Key key) {
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
                value = BuiltInRegistries.ITEM.getValue(this.identifier);
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
            Stream<Type> declared = Arrays.stream(SuCraftItems.class.getDeclaredFields())
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
                });
            Stream<Type> forBlocks = Arrays.stream(SuCraftBlocks.types())
                .map(SuCraftBlocks.Type::itemType).filter(Objects::nonNull);
            values = Stream.concat(declared, forBlocks).toArray(Type[]::new);
        }
        return values;
    }

}
