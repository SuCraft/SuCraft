package org.sucraft.sushi.datageneration.mineral;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;

public final class Gunpowder {

    private Gunpowder() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Gunpowder packing and unpacking recipes
        var gunpowderBlock = SuCraftBlocks.GUNPOWDER_BLOCK.fullBlockItemNMS();
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> () -> function.provider
                .shaped(RecipeCategory.BUILDING_BLOCKS, gunpowderBlock)
                .pattern("GG")
                .pattern("GG")
                .define('G', Items.GUNPOWDER)
            )
        );
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(() -> function.provider
                    .shapeless(RecipeCategory.BUILDING_BLOCKS, Items.GUNPOWDER, 4)
                    .requires(gunpowderBlock),
                "_from_block")
            )
        );
    }

}
