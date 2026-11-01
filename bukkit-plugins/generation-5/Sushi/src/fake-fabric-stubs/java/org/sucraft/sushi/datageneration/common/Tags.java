package org.sucraft.sushi.datageneration.common;

import net.minecraft.core.Holder;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface Tags {

    interface BlockCollectedFactory {

        void add(TagKey<Block> tag, BlockItemId blockItemId);

        void add(TagKey<Block> tag, ResourceKey<Block> key);

        void add(TagKey<Block> tag, Holder.Reference<Block> holder);

        void add(TagKey<Block> tag, Block block);

        void add(TagKey<Block> tag, TagKey<Block> child);

    }

    interface ItemCollectedFactory {

        void add(TagKey<Item> tag, BlockItemId blockItemId);

        void add(TagKey<Item> tag, ResourceKey<Item> key);

        void add(TagKey<Item> tag, Holder.Reference<Item> holder);

        void add(TagKey<Item> tag, Item item);

        void add(TagKey<Item> tag, TagKey<Item> child);

    }

}
