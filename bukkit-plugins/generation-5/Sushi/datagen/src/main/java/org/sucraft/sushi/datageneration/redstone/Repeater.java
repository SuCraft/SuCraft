package org.sucraft.sushi.datageneration.redstone;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;

public final class Repeater {

    private Repeater() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Recipe that skips the crafting of redstone torches
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                () -> function.provider
                    .shaped(RecipeCategory.BUILDING_BLOCKS, Items.REPEATER)
                    .pattern("R R")
                    .pattern("SRS")
                    .pattern("BBB")
                    .define('R', Items.REDSTONE)
                    .define('S', Items.STICK)
                    .define('B', Items.STONE),
                "_skipping_redstone_torches")
            )
        );
    }

}
