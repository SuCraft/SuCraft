package org.sucraft.sushi.gamecontent.item;

import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.Nullable;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;
import spout.api.SpoutRegistryKey;
import spout.api.clientview.packetmapping.itemstack.ItemStackMapping;
import spout.api.gamecontent.datadriven.item.registry.ItemTypeRegistryEntryBuilderNMS;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class SuCraftItems {

    private SuCraftItems() {
        throw new UnsupportedOperationException();
    }

    private static Type itemType(String path) {
        return new Type(path);
    }

    public static final class Type {

        public final NamespacedKey key;
        public final Identifier identifier;
        private @Nullable ItemType value;
        private @Nullable Item valueNMS;
        private @Nullable Item valueForDataGeneration;

        private List<Consumer<ItemTypeRegistryEntryBuilderNMS>> itemBuilding = new ArrayList<>();
        private @Nullable Function<Item.Properties, Item> constructorForDataGeneration = Item::new;

        public Type(String path) {
            this(Identifier.fromNamespaceAndPath("sucraft", path));
        }

        public Type(NamespacedKey key) {
            this(Identifier.fromNamespaceAndPath(key.getNamespace(), key.getKey()));
        }

        public Type(Identifier identifier) {
            this.key = new NamespacedKey(identifier.getNamespace(), identifier.getPath());
            this.identifier = identifier;
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

        public Item getForDataGeneration() {
            Item value = this.valueForDataGeneration;
            if (value == null) {
                var key = ResourceKey.create(Registries.ITEM, this.identifier);
                var properties = new Item.Properties().setId(key);
                value = net.minecraft.core.Registry.register(
                    BuiltInRegistries.ITEM,
                    key,
                    this.constructorForDataGeneration.apply(properties)
                );
                this.valueForDataGeneration = value;
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
            return this.itemBuild(ItemTypeRegistryEntryBuilderNMS::inheritsFromBlock);
        }

        public Type constructorForDataGeneration(Function<Item.Properties, Item> constructor) {
            this.constructorForDataGeneration = constructor;
            return this;
        }

        public Type itemConstructorForDataGeneration() {
            return this.constructorForDataGeneration(Item::new);
        }

        public Type blockConstructorForDataGeneration(Supplier<Block> block) {
            return this.constructorForDataGeneration(properties -> new BlockItem(block.get(), properties));
        }

        public Type item() {
            return this.itemConstructorForDataGeneration();
        }

        public Type block(Supplier<Block> blockForDataGeneration) {
            this.blockFactory();
            return this.blockConstructorForDataGeneration(blockForDataGeneration);
        }

    }

    private static ItemType[] values;
    private static Item[] valuesNMS;
    private static Item[] valuesForDataGeneration;
    private static Type[] types;

    public static ItemType[] values() {
        if (values == null) {
            values = Arrays.stream(types())
                .map(SuCraftItems.Type::get).toArray(ItemType[]::new);
        }
        return values;
    }

    public static Item[] valuesNMS() {
        if (valuesNMS == null) {
            valuesNMS = Arrays.stream(types())
                .map(SuCraftItems.Type::getNMS).toArray(Item[]::new);
        }
        return valuesNMS;
    }

    public static Item[] valuesForDataGeneration() {
        if (valuesForDataGeneration == null) {
            valuesForDataGeneration = Arrays.stream(types())
                .map(SuCraftItems.Type::getForDataGeneration).toArray(Item[]::new);
        }
        return valuesForDataGeneration;
    }

    public static Type[] types() {
        if (types == null) {
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
            types = Stream.concat(declared, forBlocks).toArray(Type[]::new);
        }
        return types;
    }

}
