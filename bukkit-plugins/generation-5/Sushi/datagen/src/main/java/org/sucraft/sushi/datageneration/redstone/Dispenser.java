package org.sucraft.sushi.datageneration.redstone;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;

public final class Dispenser {

    private Dispenser() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Recipes that use a dropper instead of the common ingredients
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                () -> function.provider
                    .shaped(RecipeCategory.REDSTONE, Items.DISPENSER)
                    .pattern("ST ")
                    .pattern("SDT")
                    .pattern("ST ")
                    .define('S', Items.STRING)
                    .define('T', Items.STICK)
                    .define('D', Items.DROPPER),
                "_from_dropper")
            )
        );
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                () -> function.provider
                    .shapeless(RecipeCategory.REDSTONE, Items.DISPENSER)
                    .requires(Items.DROPPER)
                    .requires(Items.BOW),
                "_from_dropper_and_bow")
            )
        );
    }

}
