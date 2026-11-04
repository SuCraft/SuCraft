package org.sucraft.sushi.datageneration.organic.wax;

import java.util.Arrays;
import java.util.stream.Stream;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;

public final class Candles {

    private Candles() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Add undyeing recipes
        for (int amount = 1; amount <= 8; amount++) {
            final int finalAmount = amount;
            factories.recipes().add(Recipes.start()
                .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(() -> function.provider
                        .shapeless(RecipeCategory.BUILDING_BLOCKS, Items.CANDLE, finalAmount)
                        .requires(Ingredient.of(function.registries.lookupOrThrow(Registries.ITEM).getOrThrow(BlockItemTags.CANDLES.item())), finalAmount)
                        .requires(Ingredient.of(Items.WATER_BUCKET, Items.ICE))
                        .group("candle_undye"),
                    "_by_undyeing_" + finalAmount
                ))
            );
        }
        // Add redyeing recipes
        for (var dyeColor : DyeColor.values()) {
            factories.recipes().add(Recipes.start()
                .addRecipeSupplier(function -> () -> function.provider
                    .shapeless(RecipeCategory.DECORATIONS, Items.DYED_CANDLE.pick(dyeColor))
                    .requires(Ingredient.of(Stream.concat(Stream.of(Items.CANDLE), Arrays.stream(DyeColor.values()).filter(otherDyeColor -> otherDyeColor != dyeColor).map(Items.DYED_CANDLE::pick)).toArray(ItemLike[]::new)))
                    .requires(Items.DYE.pick(dyeColor)))
            );
        }
    }

}
