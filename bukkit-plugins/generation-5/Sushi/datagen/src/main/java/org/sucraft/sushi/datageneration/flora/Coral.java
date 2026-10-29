package org.sucraft.sushi.datageneration.flora;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import java.util.List;
import java.util.Map;

public final class Coral {

    private Coral() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Recipes to craft coral blocks from coral or coral fans
        Map.of(
            Items.BRAIN_CORAL_BLOCK, List.of(Items.BRAIN_CORAL, Items.BRAIN_CORAL_FAN),
            Items.BUBBLE_CORAL_BLOCK, List.of(Items.BUBBLE_CORAL, Items.BUBBLE_CORAL_FAN),
            Items.FIRE_CORAL_BLOCK, List.of(Items.FIRE_CORAL, Items.FIRE_CORAL_FAN),
            Items.HORN_CORAL_BLOCK, List.of(Items.HORN_CORAL, Items.HORN_CORAL_FAN),
            Items.TUBE_CORAL_BLOCK, List.of(Items.TUBE_CORAL, Items.TUBE_CORAL_FAN),
            Items.DEAD_BRAIN_CORAL_BLOCK, List.of(Items.DEAD_BRAIN_CORAL, Items.DEAD_BRAIN_CORAL_FAN),
            Items.DEAD_BUBBLE_CORAL_BLOCK, List.of(Items.DEAD_BUBBLE_CORAL, Items.DEAD_BUBBLE_CORAL_FAN),
            Items.DEAD_FIRE_CORAL_BLOCK, List.of(Items.DEAD_FIRE_CORAL, Items.DEAD_FIRE_CORAL_FAN),
            Items.DEAD_HORN_CORAL_BLOCK, List.of(Items.DEAD_HORN_CORAL, Items.DEAD_HORN_CORAL_FAN),
            Items.DEAD_TUBE_CORAL_BLOCK, List.of(Items.DEAD_TUBE_CORAL, Items.DEAD_TUBE_CORAL_FAN)
        ).forEach((block, plants) -> {
            plants.forEach(plant -> factories.recipes().add(Recipes.start()
                .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                    () -> function.provider
                        .shaped(RecipeCategory.DECORATIONS, block)
                        .pattern("PP")
                        .pattern("PP")
                        .define('P', plant),
                    "_from_" + plant.builtInRegistryHolder().key().identifier().getPath())
                )
            ));
        });
    }

}
