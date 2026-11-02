package org.sucraft.sushi.datageneration.wood;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.tuple.Triple;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;
import java.util.List;

public final class Barrels {

    private Barrels() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        List.of(
            Triple.of(SuCraftBlocks.ACACIA_BARREL.itemNMS(), Items.ACACIA_PLANKS, Items.ACACIA_SLAB),
            Triple.of(SuCraftBlocks.BAMBOO_BARREL.itemNMS(), Items.BAMBOO_PLANKS, Items.BAMBOO_SLAB),
            Triple.of(SuCraftBlocks.BIRCH_BARREL.itemNMS(), Items.BIRCH_PLANKS, Items.BIRCH_SLAB),
            Triple.of(SuCraftBlocks.CHERRY_BARREL.itemNMS(), Items.CHERRY_PLANKS, Items.CHERRY_SLAB),
            Triple.of(SuCraftBlocks.CRIMSON_BARREL.itemNMS(), Items.CRIMSON_PLANKS, Items.CRIMSON_SLAB),
            Triple.of(SuCraftBlocks.DARK_OAK_BARREL.itemNMS(), Items.DARK_OAK_PLANKS, Items.DARK_OAK_SLAB),
            Triple.of(SuCraftBlocks.JUNGLE_BARREL.itemNMS(), Items.JUNGLE_PLANKS, Items.JUNGLE_SLAB),
            Triple.of(SuCraftBlocks.MANGROVE_BARREL.itemNMS(), Items.MANGROVE_PLANKS, Items.MANGROVE_SLAB),
            Triple.of(SuCraftBlocks.OAK_BARREL.itemNMS(), Items.OAK_PLANKS, Items.OAK_SLAB),
            Triple.of(SuCraftBlocks.PALE_OAK_BARREL.itemNMS(), Items.PALE_OAK_PLANKS, Items.PALE_OAK_SLAB),
            Triple.of(Items.BARREL, Items.SPRUCE_PLANKS, Items.SPRUCE_SLAB),
            Triple.of(SuCraftBlocks.WARPED_BARREL.itemNMS(), Items.WARPED_PLANKS, Items.WARPED_SLAB)
        ).forEach(triple -> {
            Item barrel = triple.getLeft();
            Item planks = triple.getMiddle();
            Item slab = triple.getRight();
            factories.recipes().add(Recipes.start()
                .addRecipeSupplier(function -> Recipes.RecipeSupplier.withId(
                        () -> function.provider
                            .shaped(RecipeCategory.DECORATIONS, barrel)
                            .pattern("PSP")
                            .pattern("P P")
                            .pattern("PSP")
                            .define('P', planks)
                            .define('S', slab),
                        barrel.builtInRegistryHolder().key().identifier()
                    )
                )
            );
        });
    }

}
