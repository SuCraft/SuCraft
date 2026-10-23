package org.sucraft.sushi.datageneration.wood;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.HolderSet;
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
import java.util.stream.Stream;

public final class DyedPlanks {

    private DyedPlanks() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        for (var dyeColor : DyeColor.values()) {
            var vibrant = SuCraftBlocks.DYED_PLANKS.vibrant().get(dyeColor);
            var faded = SuCraftBlocks.DYED_PLANKS.faded().get(dyeColor);
            factories.recipes().add(Recipes.start()
                .addStonecutting(faded.fullBlockItemForDataGeneration(), vibrant.fullBlockItemForDataGeneration()));
            factories.recipes().add(Recipes.start()
                .addStonecutting(faded.slabItemForDataGeneration(), vibrant.slabItemForDataGeneration()));
            factories.recipes().add(Recipes.start()
                .addStonecutting(faded.stairsItemForDataGeneration(), vibrant.stairsItemForDataGeneration()));
            factories.recipes().add(Recipes.start()
                .addStonecutting(faded.wallItemForDataGeneration(), vibrant.wallItemForDataGeneration()));
            factories.recipes().add(Recipes.start()
                .addStonecutting(faded.fenceItemForDataGeneration(), vibrant.fenceItemForDataGeneration()));
            factories.recipes().add(Recipes.start()
                .addStonecutting(faded.fenceGateItemForDataGeneration(), vibrant.fenceGateItemForDataGeneration()));
            factories.recipes().add(Recipes.start()
                .addStonecutting(faded.buttonItemForDataGeneration(), vibrant.buttonItemForDataGeneration()));
            factories.recipes().add(Recipes.start()
                .addStonecutting(faded.pressurePlateItemForDataGeneration(), vibrant.pressurePlateItemForDataGeneration()));
            Stream.of(
                Pair.of(ItemTags.PLANKS, vibrant.fullBlockItemForDataGeneration()),
                Pair.of(ItemTags.WOODEN_SLABS, vibrant.slabItemForDataGeneration()),
                Pair.of(ItemTags.WOODEN_STAIRS, vibrant.stairsItemForDataGeneration()),
                Pair.of(SuCraftItemTags.WOODEN_WALLS, vibrant.wallItemForDataGeneration()),
                Pair.of(ItemTags.WOODEN_FENCES, vibrant.fenceItemForDataGeneration()),
                Pair.of(ItemTags.FENCE_GATES, vibrant.fenceGateItemForDataGeneration()),
                Pair.of(ItemTags.WOODEN_BUTTONS, vibrant.buttonItemForDataGeneration()),
                Pair.of(ItemTags.WOODEN_PRESSURE_PLATES, vibrant.pressurePlateItemForDataGeneration())
            ).forEach(inputOutput ->
                factories.recipes().add(Recipes.start()
                    .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                            () -> function.provider
                                .shapeless(RecipeCategory.BUILDING_BLOCKS, inputOutput.right(), 8)
                                .requires(Ingredient.of(function.registries.lookupOrThrow(Registries.ITEM).getOrThrow(inputOutput.left())), 8)
                                .requires(Items.DYE.pick(dyeColor)),
                            "_by_dying"
                        )
                    )
                )
            );
        }
    }

}
