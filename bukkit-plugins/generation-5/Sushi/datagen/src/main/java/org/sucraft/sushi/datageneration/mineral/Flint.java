package org.sucraft.sushi.datageneration.mineral;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;

public final class Flint {

    private Flint() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Add flint packing and unpacking recipes
        var flintBlock = SuCraftBlocks.FLINT_BLOCK.fullBlockItemNMS();
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> () -> function.provider
                .shaped(RecipeCategory.BUILDING_BLOCKS, flintBlock)
                .pattern("FF")
                .pattern("FF")
                .define('F', Items.FLINT)
            )
        );
        factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                    () -> function.provider
                        .shapeless(RecipeCategory.BUILDING_BLOCKS, Items.FLINT, 4)
                        .requires(flintBlock),
                    "_from_block"
                )
            )
        );
        // Add the loot table and tags for flint pillar
        var flintPillar = SuCraftBlocks.FLINT_PILLAR.fullBlockNMS();
        factories.blockLootTables().add(provider -> {
            provider.dropSelf(flintPillar);
        });
        factories.blockTags().add(BlockTags.BLOCKS_MOTION_NO_LEAVES, flintPillar);
        factories.blockTags().add(BlockTags.MINEABLE_WITH_PICKAXE, flintPillar);
    }

}
