package org.sucraft.sushi.datageneration.wood;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;

public final class Chests {

    private Chests() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Recipe that skips the crafting of planks
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                    () -> function.provider
                        .shaped(RecipeCategory.BUILDING_BLOCKS, Items.CHEST, 4)
                        .pattern("LLL")
                        .pattern("L L")
                        .pattern("LLL")
                        .define('L', ItemTags.LOGS),
                    "_from_logs"
                )
            )
        );
        // Recipe for trapped chests that skips the crafting of a chest
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                    () -> function.provider
                        .shaped(RecipeCategory.REDSTONE, Items.TRAPPED_CHEST)
                        .pattern("PPP")
                        .pattern("PTP")
                        .pattern("PPP")
                        .define('P', ItemTags.PLANKS)
                        .define('T', Items.TRIPWIRE_HOOK),
                    "_from_planks"
                )
            )
        );
    }

}
