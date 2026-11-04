package org.sucraft.sushi.datageneration.wood;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;
import org.sucraft.sushi.gamecontent.item.SuCraftItemTags;
import java.util.Map;
import java.util.stream.Stream;

public final class DyedPlanks {

    private DyedPlanks() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        for (var dyeColor : DyeColor.values()) {
            var vibrant = SuCraftBlocks.DYED_PLANKS.vibrant().get(dyeColor);
            var faded = SuCraftBlocks.DYED_PLANKS.abraded().get(dyeColor);
            Map.of(
                vibrant, faded,
                faded, vibrant
            ).forEach((from, to) -> {
                factories.recipes().add(Recipes.start()
                    .addStonecutting(to.fullBlockItemNMS(), from.fullBlockItemNMS()));
                factories.recipes().add(Recipes.start()
                    .addStonecutting(to.slabItemNMS(), from.slabItemNMS()));
                factories.recipes().add(Recipes.start()
                    .addStonecutting(to.stairsItemNMS(), from.stairsItemNMS()));
                factories.recipes().add(Recipes.start()
                    .addStonecutting(to.wallItemNMS(), from.wallItemNMS()));
                factories.recipes().add(Recipes.start()
                    .addStonecutting(to.fenceItemNMS(), from.fenceItemNMS()));
                factories.recipes().add(Recipes.start()
                    .addStonecutting(to.fenceGateItemNMS(), from.fenceGateItemNMS()));
                factories.recipes().add(Recipes.start()
                    .addStonecutting(to.buttonNMS(), from.buttonItemNMS()));
                factories.recipes().add(Recipes.start()
                    .addStonecutting(to.pressurePlateNMS(), from.pressurePlateItemNMS()));
            });
            Stream.of(
                Pair.of(ItemTags.PLANKS, vibrant.fullBlockItemNMS()),
                Pair.of(ItemTags.WOODEN_SLABS, vibrant.slabItemNMS()),
                Pair.of(ItemTags.WOODEN_STAIRS, vibrant.stairsItemNMS()),
                Pair.of(SuCraftItemTags.WOODEN_WALLS, vibrant.wallItemNMS()),
                Pair.of(ItemTags.WOODEN_FENCES, vibrant.fenceItemNMS()),
                Pair.of(ItemTags.FENCE_GATES, vibrant.fenceGateItemNMS()),
                Pair.of(ItemTags.WOODEN_BUTTONS, vibrant.buttonItemNMS()),
                Pair.of(ItemTags.WOODEN_PRESSURE_PLATES, vibrant.pressurePlateItemNMS())
            ).forEach(inputOutput ->
                factories.recipes().add(Recipes.start()
                    .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                            () -> function.provider
                                .shapeless(RecipeCategory.BUILDING_BLOCKS, inputOutput.right(), 8)
                                .requires(Ingredient.of(function.registries.lookupOrThrow(Registries.ITEM).getOrThrow(inputOutput.left())), 8)
                                .requires(Items.DYE.pick(dyeColor)),
                            "_by_dyeing"
                        )
                    )
                )
            );
        }
    }

}
