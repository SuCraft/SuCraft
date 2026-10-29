package org.sucraft.sushi.datageneration.wood;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;

public final class Barrels {

    private Barrels() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withId(
                    () -> function.provider
                        .shaped(RecipeCategory.DECORATIONS, Items.BARREL)
                        .pattern("PSP")
                        .pattern("P P")
                        .pattern("PSP")
                        .define('P', Items.SPRUCE_PLANKS)
                        .define('S', Items.SPRUCE_SLAB),
                    Items.BARREL.builtInRegistryHolder().key().identifier()
                )
            )
        );
    }

}
