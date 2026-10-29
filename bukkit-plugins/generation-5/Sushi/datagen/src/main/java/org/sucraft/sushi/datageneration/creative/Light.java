package org.sucraft.sushi.datageneration.creative;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;

public final class Light {

    private Light() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function ->
                () -> function.provider
                    .shaped(RecipeCategory.DECORATIONS, Items.LIGHT, 10)
                    .pattern("GGG")
                    .pattern("GDG")
                    .pattern("GGG")
                    .define('G', Items.GLASS)
                    .define('D', Items.DIAMOND)
            )
        );
    }

}
