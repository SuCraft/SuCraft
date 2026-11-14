package org.sucraft.sushi.datageneration.soil;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.BlockTags;
import org.apache.commons.lang3.tuple.Triple;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;
import java.util.stream.Stream;

public final class CoarseDirt {

    private CoarseDirt() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Add tags
        Stream.of(
            SuCraftBlocks.COARSE_DIRT.slabNMS(),
            SuCraftBlocks.COARSE_DIRT.stairsNMS()
        ).forEach(shape -> {
            factories.blockTags().add(BlockTags.CANNOT_REPLACE_BELOW_TREE_TRUNK, shape);
        });
        // Direct crafting recipes for coarse dirt shapes
        var dirt = SuCraftBlocks.DIRT;
        var gravel = SuCraftBlocks.GRAVEL;
        var coarseDirt = SuCraftBlocks.COARSE_DIRT;
        Stream.of(
            Triple.of(dirt.slabItemNMS(), gravel.slabItemNMS(), coarseDirt.slabItemNMS()),
            Triple.of(dirt.stairsItemNMS(), gravel.stairsItemNMS(), coarseDirt.stairsItemNMS())
        ).forEach(triple -> {
            var dirtShape = triple.getLeft();
            var gravelShape = triple.getMiddle();
            var coarseDirtShape = triple.getRight();
            factories.recipes().add(Recipes.start()
                .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(() -> function.provider
                        .shaped(RecipeCategory.BUILDING_BLOCKS, coarseDirtShape, 4)
                        .pattern("DG")
                        .pattern("GD")
                        .define('D', dirtShape)
                        .define('G', gravelShape),
                    "_from_dirt_and_gravel"
                ))
            );
        });
    }

}
