package org.sucraft.sushi.datageneration.stone.regular;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;

public final class SnowyStoneBricks {

    private SnowyStoneBricks() {
        throw new UnsupportedOperationException();
    }

    private static void addAddRemoveSnowRecipes(CollectedFactories factories, Item snowy, Item nonSnowy, int magnitude) {
        ModifiedStoneBricks.addAddModificationRecipe(factories, snowy, nonSnowy, Items.SNOWBALL, magnitude);
        ModifiedStoneBricks.addAddModificationRecipe(factories, snowy, nonSnowy, Items.SNOW_BLOCK, magnitude * 4);
        ModifiedStoneBricks.addRemoveModificationStonecuttingRecipe(factories, snowy, nonSnowy);
    }

    public static void add(CollectedFactories factories) {
        addAddRemoveSnowRecipes(factories, SuCraftBlocks.SNOWY_STONE_BRICKS.fullBlockItemForDataGeneration(), Items.STONE_BRICKS, 1);
        addAddRemoveSnowRecipes(factories, SuCraftBlocks.SNOWY_STONE_BRICKS.slabItemForDataGeneration(), Items.STONE_BRICK_SLAB, 2);
        addAddRemoveSnowRecipes(factories, SuCraftBlocks.SNOWY_STONE_BRICKS.stairsItemForDataGeneration(), Items.STONE_BRICK_STAIRS, 1);
        addAddRemoveSnowRecipes(factories, SuCraftBlocks.SNOWY_STONE_BRICKS.wallItemForDataGeneration(), Items.STONE_BRICK_WALL, 1);
    }

}
