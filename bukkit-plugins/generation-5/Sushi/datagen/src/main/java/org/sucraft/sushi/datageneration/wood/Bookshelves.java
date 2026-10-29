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
            Pair.of(SuCraftBlocks.ACACIA_BOOKSHELF.fullBlockItemForDataGeneration(), Items.ACACIA_PLANKS),
            Pair.of(SuCraftBlocks.BAMBOO_BOOKSHELF.fullBlockItemForDataGeneration(), Items.BAMBOO_PLANKS),
            Pair.of(SuCraftBlocks.BIRCH_BOOKSHELF.fullBlockItemForDataGeneration(), Items.BIRCH_PLANKS),
            Pair.of(SuCraftBlocks.CHERRY_BOOKSHELF.fullBlockItemForDataGeneration(), Items.CHERRY_PLANKS),
            Pair.of(SuCraftBlocks.CRIMSON_BOOKSHELF.fullBlockItemForDataGeneration(), Items.CRIMSON_PLANKS),
            Pair.of(SuCraftBlocks.DARK_OAK_BOOKSHELF.fullBlockItemForDataGeneration(), Items.DARK_OAK_PLANKS),
            Pair.of(SuCraftBlocks.JUNGLE_BOOKSHELF.fullBlockItemForDataGeneration(), Items.JUNGLE_PLANKS),
            Pair.of(SuCraftBlocks.MANGROVE_BOOKSHELF.fullBlockItemForDataGeneration(), Items.MANGROVE_PLANKS),
            Pair.of(SuCraftBlocks.SPRUCE_BOOKSHELF.fullBlockItemForDataGeneration(), Items.SPRUCE_PLANKS),
            Pair.of(SuCraftBlocks.WARPED_BOOKSHELF.fullBlockItemForDataGeneration(), Items.WARPED_PLANKS)
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
