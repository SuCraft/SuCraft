package org.sucraft.sushi.datageneration.mineral;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.tuple.Triple;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import java.util.stream.Stream;

public final class UnpackingStorageBlocks {

    private UnpackingStorageBlocks() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        Stream.of(
            Triple.of(Items.BRICKS, Items.BRICK, 4),
            Triple.of(Items.CLAY, Items.CLAY_BALL, 4),
            Triple.of(Items.NETHER_BRICKS, Items.NETHER_BRICK, 4),
            Triple.of(Items.SNOW_BLOCK, Items.SNOWBALL, 4)
        ).forEach(triple -> {
            factories.recipes().add(Recipes.start()
                .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                        () -> function.provider
                            .shapeless(RecipeCategory.BUILDING_BLOCKS, triple.getMiddle(), triple.getRight())
                            .requires(triple.getLeft()),
                        "_from_block"
                    )
                )
            );
        });
    }

}
