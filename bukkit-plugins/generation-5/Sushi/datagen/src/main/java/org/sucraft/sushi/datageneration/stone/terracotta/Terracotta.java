package org.sucraft.sushi.datageneration.stone.terracotta;

import it.unimi.dsi.fastutil.Pair;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;
import org.sucraft.sushi.gamecontent.common.SuCraftBlockItemTags;

public final class Terracotta {

    private Terracotta() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Add undyeing recipes
        var uncoloredCustom = SuCraftBlocks.TERRACOTTA;
        Map.of(
            Items.TERRACOTTA, ItemTags.TERRACOTTA,
            uncoloredCustom.slabItemNMS(), SuCraftBlockItemTags.TERRACOTTA_SLABS.item(),
            uncoloredCustom.stairsItemNMS(), SuCraftBlockItemTags.TERRACOTTA_STAIRS.item(),
            uncoloredCustom.wallItemNMS(), SuCraftBlockItemTags.TERRACOTTA_WALLS.item(),
            uncoloredCustom.buttonItemNMS(), SuCraftBlockItemTags.TERRACOTTA_BUTTONS.item(),
            uncoloredCustom.pressurePlateItemNMS(), SuCraftBlockItemTags.TERRACOTTA_PRESSURE_PLATES.item()
        ).forEach((uncolored, tag) -> {
            for (int amount = 1; amount <= 8; amount++) {
                final int finalAmount = amount;
                factories.recipes().add(Recipes.start()
                    .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(() -> function.provider
                            .shapeless(RecipeCategory.BUILDING_BLOCKS, uncolored, finalAmount)
                            .requires(Ingredient.of(function.registries.lookupOrThrow(Registries.ITEM).getOrThrow(tag)), finalAmount)
                            .requires(Ingredient.of(Items.WATER_BUCKET, Items.ICE))
                            .group(uncolored.builtInRegistryHolder().key().identifier().getPath() + "_undye"),
                        "_by_undyeing_" + finalAmount
                    ))
                );
            }
        });
        // Add dyeing recipes
        for (var dyeColor : DyeColor.values()) {
            var coloredCustom = SuCraftBlocks.DYED_TERRACOTTA.get(dyeColor);
            var coloredFullBlock = BuiltInRegistries.ITEM.getValue(Identifier.withDefaultNamespace(dyeColor + "_terracotta"));
            Stream.of(
                Pair.of(ItemTags.TERRACOTTA, coloredFullBlock),
                Pair.of(SuCraftBlockItemTags.TERRACOTTA_SLABS.item(), coloredCustom.slabItemNMS()),
                Pair.of(SuCraftBlockItemTags.TERRACOTTA_STAIRS.item(), coloredCustom.stairsItemNMS()),
                Pair.of(SuCraftBlockItemTags.TERRACOTTA_WALLS.item(), coloredCustom.wallItemNMS()),
                Pair.of(SuCraftBlockItemTags.TERRACOTTA_BUTTONS.item(), coloredCustom.buttonItemNMS()),
                Pair.of(SuCraftBlockItemTags.TERRACOTTA_PRESSURE_PLATES.item(), coloredCustom.pressurePlateItemNMS())
            ).forEach(inputOutput ->
                factories.recipes().add(Recipes.start()
                    .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(() -> function.provider
                            .shapeless(RecipeCategory.BUILDING_BLOCKS, inputOutput.right(), 8)
                            .requires(Ingredient.of(function.registries.lookupOrThrow(Registries.ITEM).getOrThrow(inputOutput.left())), 8)
                            .requires(Items.DYE.pick(dyeColor)),
                        inputOutput.right().builtInRegistryHolder().is(ItemTags.TERRACOTTA) ? "" : "_by_dyeing"
                    ))
                )
            );
        }
    }

}
