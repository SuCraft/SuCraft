package org.sucraft.sushi.datageneration.organic;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;

public final class Bone {

    private Bone() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Smelt wither bone block from bone block
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(_ -> Recipes.RecipeSupplier.withSuffix(() ->
                    SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.BONE_BLOCK), RecipeCategory.BUILDING_BLOCKS, CookingBookCategory.BLOCKS, SuCraftBlocks.WITHER_BONE_BLOCK.fullBlockItemNMS(), 0.1f, 200),
                "_smelting"
            ))
        );
    }

}
