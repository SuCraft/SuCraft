package org.sucraft.sushi.datageneration.organic.shulker;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.TransmuteRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;

public final class ShulkerBoxes {

    private ShulkerBoxes() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Add undyeing recipe
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(() ->
                TransmuteRecipeBuilder.transmute(RecipeCategory.DECORATIONS, Ingredient.of(function.registries.lookupOrThrow(Registries.ITEM).getOrThrow(ItemTags.SHULKER_BOXES)), Ingredient.of(Items.WATER_BUCKET, Items.ICE), Items.SHULKER_BOX).group("shulker_box_undye"),
                "_by_undyeing"
            ))
        );
    }

}
