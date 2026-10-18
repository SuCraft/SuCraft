package org.sucraft.sushi.datageneration.stone.regular;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.CommonMacros;
import org.sucraft.sushi.datageneration.common.ModBlocks;
import org.sucraft.sushi.datageneration.common.ModItems;

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
        CommonMacros.addFamily(
            factories,
            ModBlocks.SNOWY_STONE_BRICKS,
            ModBlocks.SNOWY_STONE_BRICK_SLAB,
            ModBlocks.SNOWY_STONE_BRICK_STAIRS,
            ModBlocks.SNOWY_STONE_BRICK_WALL
        );
        addAddRemoveSnowRecipes(factories, ModItems.SNOWY_STONE_BRICKS, Items.STONE_BRICKS, 1);
        addAddRemoveSnowRecipes(factories, ModItems.SNOWY_STONE_BRICK_SLAB, Items.STONE_BRICK_SLAB, 2);
        addAddRemoveSnowRecipes(factories, ModItems.SNOWY_STONE_BRICK_STAIRS, Items.STONE_BRICK_STAIRS, 1);
        addAddRemoveSnowRecipes(factories, ModItems.SNOWY_STONE_BRICK_WALL, Items.STONE_BRICK_WALL, 1);
    }

}
