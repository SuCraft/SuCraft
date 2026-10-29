package org.sucraft.sushi.datageneration.mineral.iron;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import java.util.Map;

public final class Minecarts {

    private Minecarts() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Recipe that skips the crafting of a minecart
        Map.of(
            Items.CHEST_MINECART, Items.CHEST,
            Items.FURNACE_MINECART, Items.FURNACE,
            Items.HOPPER_MINECART, Items.HOPPER,
            Items.TNT_MINECART, Items.TNT
        ).forEach((minecart, block) -> {
            factories.recipes().add(Recipes.start()
                .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                    () -> function.provider
                        .shaped(RecipeCategory.TRANSPORTATION, minecart)
                        .pattern("IBI")
                        .pattern("III")
                        .define('I', Items.IRON_INGOT)
                        .define('B', block),
                    "_from_iron")
                )
            );
        });
    }

}
