package org.sucraft.sushi.datageneration.wood;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;

public final class Oak {

    private Oak() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> () -> function.provider
                .shaped(RecipeCategory.BUILDING_BLOCKS, Items.BOOKSHELF)
                .pattern("###")
                .pattern("XXX")
                .pattern("###")
                .define('#', Items.OAK_PLANKS)
                .define('X', Items.BOOK)
            )
        );
    }

}
