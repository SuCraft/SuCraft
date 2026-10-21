package org.sucraft.sushi.datageneration.stone.regular;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Item;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import java.util.stream.Stream;

public final class ModifiedStoneBricks {

    private ModifiedStoneBricks() {
        throw new UnsupportedOperationException();
    }

    public static void addAddModificationRecipe(CollectedFactories factories, Item modified, Item unmodified, Item modifier, int itemsPerModifier) {
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                    () -> function.provider
                        .shapeless(RecipeCategory.BUILDING_BLOCKS, modified, itemsPerModifier)
                        .requires(unmodified, itemsPerModifier)
                        .requires(modifier),
                    "_from_" + modifier.builtInRegistryHolder().key().identifier().getPath()
                )
            )
        );
    }

    public static void addRemoveModificationStonecuttingRecipe(CollectedFactories factories, Item modified, Item unmodified) {
        factories.recipes().add(Recipes.start()
            .addStonecutting(
                unmodified,
                modified
            )
        );
    }

}
