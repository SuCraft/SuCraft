package org.sucraft.sushi.datageneration.mineral;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;

public final class Flint {

    private Flint() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> () -> function.provider
                .shaped(RecipeCategory.BUILDING_BLOCKS, SuCraftBlocks.FLINT_BLOCK.fullBlockItemNMS())
                .pattern("FF")
                .pattern("FF")
                .define('F', Items.FLINT)
            )
        );
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                    () -> function.provider
                        .shapeless(RecipeCategory.BUILDING_BLOCKS, Items.FLINT, 4)
                        .requires(SuCraftBlocks.FLINT_BLOCK.fullBlockItemNMS()),
                    "_from_block"
                )
            )
        );
    }

}
