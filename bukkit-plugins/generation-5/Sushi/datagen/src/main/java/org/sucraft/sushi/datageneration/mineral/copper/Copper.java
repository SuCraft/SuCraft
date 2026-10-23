package org.sucraft.sushi.datageneration.mineral.copper;

import it.unimi.dsi.fastutil.booleans.BooleanList;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.WeatheringCopper;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import java.util.stream.Stream;

public final class Copper {

    private Copper() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        BooleanList.of(false, true).forEach(waxed -> {
            WeatheringCopper.WeatherState.forEach(state -> {
                Item copperGrate = (waxed ? Items.COPPER_GRATE.waxed() : Items.COPPER_GRATE.weathering()).pick(state);
                Item cutCopper = (waxed ? Items.CUT_COPPER.waxed() : Items.CUT_COPPER.weathering()).pick(state);
                Item chiseledCopper = (waxed ? Items.CHISELED_COPPER.waxed() : Items.CHISELED_COPPER.weathering()).pick(state);
                Item copperBlock = (waxed ? Items.COPPER_BLOCK.waxed() : Items.COPPER_BLOCK.weathering()).pick(state);
                factories.recipes().add(Recipes.start()
                    .addStonecutting(copperGrate, cutCopper));
                factories.recipes().add(Recipes.start()
                    .addStonecutting(cutCopper, copperGrate));
                factories.recipes().add(Recipes.start()
                    .addStonecutting(cutCopper, chiseledCopper));
                Stream.of(copperGrate, cutCopper, chiseledCopper).forEach(quarter -> {
                    factories.recipes().add(Recipes.start()
                        .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                                () -> function.provider
                                    .shapeless(RecipeCategory.BUILDING_BLOCKS, copperBlock)
                                    .requires(quarter, 4),
                                "_from_" + quarter.builtInRegistryHolder().key().identifier().getPath()
                            )
                        )
                    );
                });
                factories.recipes().add(Recipes.start()
                    .addRecipeSupplier(function -> Recipes.RecipeSupplier.withId(
                            () -> function.provider
                                .shaped(RecipeCategory.BUILDING_BLOCKS, cutCopper, 16)
                                .pattern("##")
                                .pattern("##")
                                .define('#', copperBlock),
                            cutCopper.builtInRegistryHolder().key().identifier()
                        )
                    )
                );
                factories.recipes().add(Recipes.start()
                    .addRecipeSupplier(function -> Recipes.RecipeSupplier.withId(
                            () -> function.provider
                                .shaped(RecipeCategory.BUILDING_BLOCKS, copperGrate, 16)
                                .pattern(" M ")
                                .pattern("M M")
                                .pattern(" M ")
                                .define('M', copperBlock),
                            copperGrate.builtInRegistryHolder().key().identifier()
                        )
                    )
                );
            });
        });
    }

}
