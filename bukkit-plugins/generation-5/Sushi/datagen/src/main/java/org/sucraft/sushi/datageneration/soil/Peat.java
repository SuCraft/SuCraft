package org.sucraft.sushi.datageneration.soil;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;

public final class Peat {

    private Peat() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Add crafting recipe
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(() -> function.provider
                    .shapeless(RecipeCategory.BUILDING_BLOCKS, SuCraftBlocks.PEAT.fullBlockItemNMS(), 2)
                    .requires(ItemTags.LEAVES)
                    .requires(ItemTags.DIRT),
                "_from_leaves"
            ))
        );
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(() -> function.provider
                    .shapeless(RecipeCategory.BUILDING_BLOCKS, SuCraftBlocks.PEAT.fullBlockItemNMS(), 2)
                    .requires(Items.LEAF_LITTER)
                    .requires(ItemTags.DIRT),
                "_from_leaf_litter"
            ))
        );
    }

}
