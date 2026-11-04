package org.sucraft.sushi.datageneration.organic.hide;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.TransmuteRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;

public final class Bundles {

    private Bundles() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Add undyeing recipe
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(() ->
                    TransmuteRecipeBuilder.transmute(RecipeCategory.TOOLS, Ingredient.of(function.registries.lookupOrThrow(Registries.ITEM).getOrThrow(ItemTags.BUNDLES)), Ingredient.of(Items.WATER_BUCKET, Items.ICE), Items.BUNDLE).group("bundle_undye"),
                "_by_undyeing"
            ))
        );
    }

}
