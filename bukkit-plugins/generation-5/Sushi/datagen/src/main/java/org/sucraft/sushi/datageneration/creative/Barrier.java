package org.sucraft.sushi.datageneration.creative;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;

public final class Barrier {

    private Barrier() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function ->
                () -> function.provider
                    .shaped(RecipeCategory.DECORATIONS, Items.BARRIER, 10)
                    .pattern("RRR")
                    .pattern("RDR")
                    .pattern("RRR")
                    .define('R', Items.WOOL.red())
                    .define('D', Items.DIAMOND)
            )
        );
    }

}
