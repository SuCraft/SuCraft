package org.sucraft.sushi.datageneration.stone.regular;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import java.util.stream.Stream;

public final class ModifiedStoneBricks {

    private ModifiedStoneBricks() {
        throw new UnsupportedOperationException();
    }

    public static void addAddModificationRecipe(CollectedFactories factories, ItemLike modified, ItemLike unmodified, ItemLike modifier, int itemsPerRecipe, int modifiersPerRecipe) {
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                    () -> function.provider
                        .shapeless(RecipeCategory.BUILDING_BLOCKS, modified, itemsPerRecipe)
                        .requires(unmodified, itemsPerRecipe)
                        .requires(modifier, modifiersPerRecipe),
                    "_from_" + modifier.asItem().builtInRegistryHolder().key().identifier().getPath()
                )
            )
        );
    }

    public static void addRemoveModificationStonecuttingRecipe(CollectedFactories factories, ItemLike modified, ItemLike unmodified) {
        factories.recipes().add(Recipes.start()
            .addStonecutting(
                unmodified,
                modified
            )
        );
    }

}
