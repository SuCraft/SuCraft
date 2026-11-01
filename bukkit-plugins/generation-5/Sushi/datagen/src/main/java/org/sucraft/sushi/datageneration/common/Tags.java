package org.sucraft.sushi.datageneration.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class Tags {

    private Tags() {
        throw new UnsupportedOperationException();
    }

    public static class BlockCollectedFactory implements FabricDataGenerator.Pack.RegistryDependentFactory<FabricTagsProvider.BlockTagsProvider> {

        private Map<TagKey<Block>, Set<Object>> links = new HashMap<>();

        private void addInternal(TagKey<Block> tag, Object element) {
            this.links.computeIfAbsent(tag, _ -> new HashSet<>(1)).add(element);
        }

        public void add(TagKey<Block> tag, BlockItemId blockItemId) {
            this.addInternal(tag, blockItemId);
        }

        public void add(TagKey<Block> tag, ResourceKey<Block> key) {
            this.addInternal(tag, key);
        }

        public void add(TagKey<Block> tag, Holder.Reference<Block> holder) {
            this.add(tag, holder.key());
        }

        public void add(TagKey<Block> tag, Block block) {
            this.add(tag, block.builtInRegistryHolder());
        }

        public void add(TagKey<Block> tag, TagKey<Block> child) {
            this.addInternal(tag, child);
        }

        @Override
        public FabricTagsProvider.BlockTagsProvider create(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            return new FabricTagsProvider.BlockTagsProvider(output, registriesFuture) {

                @Override
                public void addTags(HolderLookup.Provider wrapperLookup) {
                    BlockCollectedFactory.this.links.forEach((tag, elements) -> {
                        var builder = this.builder(tag);
                        elements.forEach(element -> {
                            if (element instanceof BlockItemId blockItemId) {
                                builder.add(blockItemId);
                            } else if (element instanceof ResourceKey resourceKey) {
                                builder.add(resourceKey);
                            } else if (element instanceof TagKey tagKey) {
                                builder.addTag(tagKey);
                            }
                        });
                    });
                }

            };
        }

    }

    public static class ItemCollectedFactory implements FabricDataGenerator.Pack.RegistryDependentFactory<FabricTagsProvider.ItemTagsProvider> {

        private Map<TagKey<Item>, Set<Object>> links = new HashMap<>();

        private void addInternal(TagKey<Item> tag, Object element) {
            this.links.computeIfAbsent(tag, _ -> new HashSet<>(1)).add(element);
        }

        public void add(TagKey<Item> tag, BlockItemId blockItemId) {
            this.addInternal(tag, blockItemId);
        }

        public void add(TagKey<Item> tag, ResourceKey<Item> key) {
            this.addInternal(tag, key);
        }

        public void add(TagKey<Item> tag, Holder.Reference<Item> holder) {
            this.add(tag, holder.key());
        }

        public void add(TagKey<Item> tag, Item item) {
            this.add(tag, item.builtInRegistryHolder());
        }

        public void add(TagKey<Item> tag, TagKey<Item> child) {
            this.addInternal(tag, child);
        }

        @Override
        public FabricTagsProvider.ItemTagsProvider create(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            return new FabricTagsProvider.ItemTagsProvider(output, registriesFuture) {

                @Override
                public void addTags(HolderLookup.Provider wrapperLookup) {
                    ItemCollectedFactory.this.links.forEach((tag, elements) -> {
                        var builder = this.builder(tag);
                        elements.forEach(element -> {
                            if (element instanceof BlockItemId blockItemId) {
                                builder.add(blockItemId);
                            } else if (element instanceof ResourceKey resourceKey) {
                                builder.add(resourceKey);
                            } else if (element instanceof TagKey tagKey) {
                                builder.addTag(tagKey);
                            }
                        });
                    });
                }
            };
        }

    }

}
