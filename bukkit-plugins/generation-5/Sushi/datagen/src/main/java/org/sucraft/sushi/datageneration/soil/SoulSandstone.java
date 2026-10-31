package org.sucraft.sushi.datageneration.soil;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;

public final class SoulSandstone {

    private SoulSandstone() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                () -> function.provider
                    .shaped(RecipeCategory.BUILDING_BLOCKS, SuCraftBlocks.SOUL_SANDSTONE.fullBlockItemForDataGeneration())
                    .pattern("SS")
                    .pattern("SS")
                    .define('S', Ingredient.of(Items.SOUL_SAND, Items.SOUL_SOIL)),
                "_from_soul_sand_or_soil")
            )
        );
    }

}
