package org.sucraft.sushi.datageneration.wood;

import it.unimi.dsi.fastutil.Pair;
import java.util.List;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;

public final class Ladders {

    private Ladders() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        List.of(
            Pair.of(SuCraftBlocks.ACACIA_LADDER.itemNMS(), Items.ACACIA_PLANKS),
            Pair.of(SuCraftBlocks.BAMBOO_LADDER.itemNMS(), Items.BAMBOO_PLANKS),
            Pair.of(SuCraftBlocks.BIRCH_LADDER.itemNMS(), Items.BIRCH_PLANKS),
            Pair.of(SuCraftBlocks.CHERRY_LADDER.itemNMS(), Items.CHERRY_PLANKS),
            Pair.of(SuCraftBlocks.CRIMSON_LADDER.itemNMS(), Items.CRIMSON_PLANKS),
            Pair.of(SuCraftBlocks.DARK_OAK_LADDER.itemNMS(), Items.DARK_OAK_PLANKS),
            Pair.of(SuCraftBlocks.JUNGLE_LADDER.itemNMS(), Items.JUNGLE_PLANKS),
            Pair.of(SuCraftBlocks.MANGROVE_LADDER.itemNMS(), Items.MANGROVE_PLANKS),
            Pair.of(Items.LADDER, Items.OAK_PLANKS),
            Pair.of(SuCraftBlocks.SPRUCE_LADDER.itemNMS(), Items.SPRUCE_PLANKS),
            Pair.of(SuCraftBlocks.WARPED_LADDER.itemNMS(), Items.WARPED_PLANKS)
        ).forEach(pair -> {
            Item ladder = pair.left();
            Item planks = pair.right();
            factories.recipes().add(Recipes.start()
                .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                        () -> function.provider
                            .shaped(RecipeCategory.BUILDING_BLOCKS, ladder, 4)
                            .pattern("S S")
                            .pattern("SPS")
                            .pattern("S S")
                            .define('S', Items.STICK)
                            .define('P', planks),
                        "_using_planks"
                    )
                )
            );
        });

    }

}
