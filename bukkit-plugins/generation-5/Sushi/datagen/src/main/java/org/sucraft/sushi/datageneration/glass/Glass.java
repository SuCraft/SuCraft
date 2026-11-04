package org.sucraft.sushi.datageneration.glass;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import org.apache.commons.lang3.tuple.Triple;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.gamecontent.common.CBlockItemTags;
import java.util.Arrays;
import java.util.stream.Stream;

public final class Glass {

    private Glass() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Glass always drops itself
        factories.blockLootTables().add(provider -> {
            provider.dropSelf(Blocks.GLASS);
            provider.dropSelf(Blocks.GLASS_PANE);
            for (var color : DyeColor.values()) {
                provider.dropSelf(Blocks.STAINED_GLASS.pick(color));
                provider.dropSelf(Blocks.STAINED_GLASS_PANE.pick(color));
            }
        });
        // Add redyeing and undyeing recipes
        Stream.of(
            Triple.of(Items.STAINED_GLASS, Items.GLASS, CBlockItemTags.GLASS_BLOCKS.item()),
            Triple.of(Items.STAINED_GLASS_PANE, Items.GLASS_PANE, CBlockItemTags.GLASS_PANES.item())
        ).forEach(triple -> {
            var stained = triple.getLeft();
            var unstained = triple.getMiddle();
            var tag = triple.getRight();
            // Undyeing
            for (int amount = 1; amount <= 8; amount++) {
                final int finalAmount = amount;
                factories.recipes().add(Recipes.start()
                    .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(() -> function.provider
                            .shapeless(RecipeCategory.BUILDING_BLOCKS, unstained, finalAmount)
                            .requires(Ingredient.of(function.registries.lookupOrThrow(Registries.ITEM).getOrThrow(tag)), finalAmount)
                            .requires(Ingredient.of(Items.WATER_BUCKET, Items.ICE))
                            .group(unstained.builtInRegistryHolder().key().identifier().getPath() + "_undye"),
                        "_by_undyeing_" + finalAmount
                    ))
                );
            }
            // Redyeing
            for (var dyeColor : DyeColor.values()) {
                factories.recipes().add(Recipes.start()
                    .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(() -> function.provider
                            .shapeless(RecipeCategory.BUILDING_BLOCKS, stained.pick(dyeColor), 8)
                            .requires(Ingredient.of(Stream.concat(Stream.of(unstained), Arrays.stream(DyeColor.values()).filter(otherDyeColor -> otherDyeColor != dyeColor).map(stained::pick)).toArray(ItemLike[]::new)), 8)
                            .requires(Items.DYE.pick(dyeColor)),
                        unstained == Items.GLASS_PANE ? "_from_glass_pane" : ""
                    ))
                );
            }
        });
    }

}
