package org.sucraft.sushi.datageneration.stonebricks.snowy;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.ModBlocks;
import org.sucraft.sushi.datageneration.common.ModItems;
import org.sucraft.sushi.datageneration.common.Recipes;

public final class SnowyStoneBricks {

    private SnowyStoneBricks() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {

        // Block model family
        factories.models().addBlock(
            generators -> generators.family(ModBlocks.SNOWY_STONE_BRICKS)
                .slab(ModBlocks.SNOWY_STONE_BRICK_SLAB)
                .stairs(ModBlocks.SNOWY_STONE_BRICK_STAIRS)
        );

        // Full block
        factories.blockLootTables().add(provider -> provider.dropSelf(ModBlocks.SNOWY_STONE_BRICKS));
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> () -> function.provider
                .shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.SNOWY_STONE_BRICKS)
                .requires(Items.STONE_BRICKS)
                .requires(Items.SNOWBALL)
            )
        );
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                    () -> function.provider
                        .shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.SNOWY_STONE_BRICKS, 4)
                        .requires(Items.STONE_BRICKS, 4)
                        .requires(Items.SNOW_BLOCK),
                    "_from_" + Items.SNOW_BLOCK.builtInRegistryHolder().key().identifier().getPath()
                )
            )
        );
        factories.recipes().add(Recipes.start() // Remove snow
            .addStonecutting(
                Items.STONE_BRICKS,
                ModItems.SNOWY_STONE_BRICKS
            )
        );

        // Slab
        factories.blockLootTables().add(provider -> provider.add(ModBlocks.SNOWY_STONE_BRICK_SLAB, provider::createSlabItemTable));
        factories.recipes().add(Recipes.start() // Remove snow
            .addStonecutting(
                Items.STONE_BRICK_SLAB,
                ModItems.SNOWY_STONE_BRICK_SLAB
            )
        );

        // Stairs
        factories.blockLootTables().add(provider -> provider.dropSelf(ModBlocks.SNOWY_STONE_BRICK_STAIRS));
        factories.recipes().add(Recipes.start() // Remove snow
            .addStonecutting(
                Items.STONE_BRICK_STAIRS,
                ModItems.SNOWY_STONE_BRICK_STAIRS
            )
        );

    }

}
