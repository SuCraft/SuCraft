package org.sucraft.sushi.datageneration.mineral;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;

public final class Coal {

    private Coal() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Charcoal packing and unpacking recipes
        var charcoalBlock = SuCraftBlocks.CHARCOAL_BLOCK.fullBlockItemNMS();
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> () -> function.provider
                .shapeless(RecipeCategory.BUILDING_BLOCKS, charcoalBlock)
                .requires(Items.CHARCOAL, 9)
            )
        );
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(() -> function.provider
                    .shapeless(RecipeCategory.BUILDING_BLOCKS, Items.CHARCOAL, 9)
                    .requires(charcoalBlock),
                "_from_block")
            )
        );
    }

}
