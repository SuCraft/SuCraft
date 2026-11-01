package org.sucraft.sushi.datageneration.wood;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;
import java.util.List;

public final class Bookshelves {

    private Bookshelves() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        List.of(
            Pair.of(Items.BOOKSHELF, Items.OAK_PLANKS),
            Pair.of(SuCraftBlocks.ACACIA_BOOKSHELF.fullBlockItemNMS(), Items.ACACIA_PLANKS),
            Pair.of(SuCraftBlocks.BAMBOO_BOOKSHELF.fullBlockItemNMS(), Items.BAMBOO_PLANKS),
            Pair.of(SuCraftBlocks.BIRCH_BOOKSHELF.fullBlockItemNMS(), Items.BIRCH_PLANKS),
            Pair.of(SuCraftBlocks.CHERRY_BOOKSHELF.fullBlockItemNMS(), Items.CHERRY_PLANKS),
            Pair.of(SuCraftBlocks.CRIMSON_BOOKSHELF.fullBlockItemNMS(), Items.CRIMSON_PLANKS),
            Pair.of(SuCraftBlocks.DARK_OAK_BOOKSHELF.fullBlockItemNMS(), Items.DARK_OAK_PLANKS),
            Pair.of(SuCraftBlocks.JUNGLE_BOOKSHELF.fullBlockItemNMS(), Items.JUNGLE_PLANKS),
            Pair.of(SuCraftBlocks.MANGROVE_BOOKSHELF.fullBlockItemNMS(), Items.MANGROVE_PLANKS),
            Pair.of(SuCraftBlocks.SPRUCE_BOOKSHELF.fullBlockItemNMS(), Items.SPRUCE_PLANKS),
            Pair.of(SuCraftBlocks.WARPED_BOOKSHELF.fullBlockItemNMS(), Items.WARPED_PLANKS)
        ).forEach(pair -> {
            Item bookshelf = pair.left();
            Item planks = pair.right();
            factories.recipes().add(Recipes.start()
                .addRecipeSupplier(function -> () -> function.provider
                    .shaped(RecipeCategory.BUILDING_BLOCKS, bookshelf)
                    .pattern("###")
                    .pattern("XXX")
                    .pattern("###")
                    .define('#', planks)
                    .define('X', Items.BOOK)
                )
            );
        });

    }

}
