package org.sucraft.sushi.datageneration.stone.deepslate;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;

public final class Deepslate {

    private Deepslate() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                    () -> function.provider
                        .shaped(RecipeCategory.BUILDING_BLOCKS, Items.POLISHED_DEEPSLATE, 4)
                        .pattern("SS")
                        .pattern("SS")
                        .define('S', Items.DEEPSLATE),
                    "_from_" + Items.DEEPSLATE.builtInRegistryHolder().key().identifier().getPath()
                )
            )
        );
        factories.recipes().add(Recipes.start()
            .addStonecutting(Items.COBBLED_DEEPSLATE, Items.DEEPSLATE_TILES));
        factories.recipes().add(Recipes.start()
            .addStonecutting(Items.COBBLED_DEEPSLATE, Items.CHISELED_DEEPSLATE));
    }

}
