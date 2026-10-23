package org.sucraft.sushi.gamecontent.item;

import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;import net.minecraft.world.level.block.Block;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.Nullable;
import spout.api.SpoutRegistryKey;
import spout.api.clientview.packetmapping.itemstack.ItemStackMapping;
import spout.api.gamecontent.datadriven.item.registry.ItemTypeRegistryEntryBuilderNMS;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class SuCraftItemType implements ItemLike {

    public final NamespacedKey key;
    public final Identifier identifier;
    private @Nullable ItemType value;
    private @Nullable Item valueNMS;
    private @Nullable Item valueForDataGeneration;

    private List<Consumer<ItemTypeRegistryEntryBuilderNMS>> itemBuilding = new ArrayList<>();
    private @Nullable Function<Item.Properties, Item> constructorForDataGeneration = Item::new;

    public SuCraftItemType(String path) {
        this(Identifier.fromNamespaceAndPath("sucraft", path));
    }

    public SuCraftItemType(NamespacedKey key) {
        this(Identifier.fromNamespaceAndPath(key.getNamespace(), key.getKey()));
    }

    public SuCraftItemType(Identifier identifier) {
        this.key = new NamespacedKey(identifier.getNamespace(), identifier.getPath());
        this.identifier = identifier;
    }

    @Override
    public Item asItem() {
        return this.getNMS();
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

    public SuCraftItemType itemBuild(Consumer<ItemTypeRegistryEntryBuilderNMS> builderConsumer) {
        this.itemBuilding.add(builderConsumer);
        return this;
    }

    public SuCraftItemType blockFactory() {
        return this.itemBuild(ItemTypeRegistryEntryBuilderNMS::inheritsFromBlock);
    }

    public SuCraftItemType constructorForDataGeneration(Function<Item.Properties, Item> constructor) {
        this.constructorForDataGeneration = constructor;
        return this;
    }

    public SuCraftItemType itemConstructorForDataGeneration() {
        return this.constructorForDataGeneration(Item::new);
    }

    public SuCraftItemType blockConstructorForDataGeneration(Supplier<Block> block) {
        return this.constructorForDataGeneration(properties -> new BlockItem(block.get(), properties));
    }

    public SuCraftItemType item() {
        return this.itemConstructorForDataGeneration();
    }

    public SuCraftItemType block(Supplier<Block> blockForDataGeneration) {
        this.blockFactory();
        return this.blockConstructorForDataGeneration(blockForDataGeneration);
    }

}
