package org.sucraft.sushi.gamecontent.item;

import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockItemTagId;import net.minecraft.tags.TagKey;import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;import net.minecraft.world.level.block.Block;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.Nullable;
import org.sucraft.sushi.datageneration.common.CollectedFactories;import org.sucraft.sushi.datageneration.common.DataGenerationFlag;import spout.api.SpoutRegistryKey;
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

    private List<Consumer<ItemTypeRegistryEntryBuilderNMS>> itemBuilding = new ArrayList<>();
    private @Nullable Function<Item.Properties, Item> constructorForDataGeneration = Item::new;
    private List<Consumer<CollectedFactories>> dataGenerating = new ArrayList<>();
    private @Nullable List<TagKey<Item>> dataGeneratingTags;

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
            if (DataGenerationFlag.isDataGeneration) {
                var key = ResourceKey.create(Registries.ITEM, this.identifier);
                var properties = new Item.Properties().setId(key);
                value = net.minecraft.core.Registry.register(
                    BuiltInRegistries.ITEM,
                    key,
                    this.constructorForDataGeneration.apply(properties)
                );
            } else {
                value = BuiltInRegistries.ITEM.getValue(this.identifier);
            }
            this.valueNMS = value;
        }
        return value;
    }

    public void applyToItemBuilder(ItemTypeRegistryEntryBuilderNMS builder) {
        this.itemBuilding.forEach(it -> it.accept(builder));
        this.itemBuilding = null;
    }

    public void applyDataGeneration(CollectedFactories factories) {
        this.dataGenerating.forEach(it -> it.accept(factories));
        this.dataGenerating = null;
        if (this.dataGeneratingTags != null) {
            for (var tag : this.dataGeneratingTags){
                factories.itemTags().add(tag, this.getNMS());
            }
            this.dataGeneratingTags = null;
        }
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

    public SuCraftItemType dataGenerate(Consumer<CollectedFactories> factoriesConsumer) {
        this.dataGenerating.add(factoriesConsumer);
        return this;
    }

    public SuCraftItemType dataGenerateTag(TagKey<Item> tag) {
        if (this.dataGeneratingTags == null) {
            this.dataGeneratingTags = new ArrayList<>(1);
        }
        this.dataGeneratingTags.add(tag);
        return this;
    }

    public SuCraftItemType dataGenerateTag(BlockItemTagId tag) {
        return this.dataGenerateTag(tag.item());
    }

    public SuCraftItemType item() {
        return this.itemConstructorForDataGeneration();
    }

    public SuCraftItemType block(Supplier<Block> blockForDataGeneration) {
        this.blockFactory();
        return this.blockConstructorForDataGeneration(blockForDataGeneration);
    }

}
