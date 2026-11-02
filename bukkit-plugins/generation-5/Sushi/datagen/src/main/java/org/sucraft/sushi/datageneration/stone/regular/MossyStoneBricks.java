package org.sucraft.sushi.datageneration.stone.regular;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;
import java.util.Map;
import java.util.stream.Stream;

public final class MossyStoneBricks {

    private MossyStoneBricks() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        Map.of(
            SuCraftBlocks.MOSSY_CHISELED_STONE_BRICKS, Items.CHISELED_STONE_BRICKS,
            SuCraftBlocks.MOSSY_CRACKED_STONE_BRICKS, Items.CRACKED_STONE_BRICKS
        ).forEach((mossy, regular) -> {
            Stream.of(
                Items.VINE,
                Items.MOSS_BLOCK
            ).forEach(plant -> {
                factories.recipes().add(Recipes.start()
                    .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                        () -> function.provider
                            .shapeless(RecipeCategory.BUILDING_BLOCKS, mossy)
                            .requires(regular)
                            .requires(plant),
                        "_from_" + plant.builtInRegistryHolder().key().identifier().getPath())
                    )
                );
            });
        });
    }

}
