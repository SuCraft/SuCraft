package org.sucraft.sushi.datageneration.stone.claybrick;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;

public final class Bricks {

    private Bricks() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                    () -> function.provider
                        .shapeless(RecipeCategory.MISC, Items.FIELD_MASONED_BANNER_PATTERN)
                        .requires(Items.PAPER)
                        .requires(SuCraftBlocks.RECESSED_BRICKS),
                "_from_recessed")
            )
        );
    }

}
