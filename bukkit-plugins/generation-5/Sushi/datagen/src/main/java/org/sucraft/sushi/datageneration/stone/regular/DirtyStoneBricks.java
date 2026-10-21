package org.sucraft.sushi.datageneration.stone.regular;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;

public final class DirtyStoneBricks {

    private DirtyStoneBricks() {
        throw new UnsupportedOperationException();
    }

    private static void addAddRemoveDirtRecipes(CollectedFactories factories, Item dirty, Item nonDirty, int magnitude) {
        ModifiedStoneBricks.addAddModificationRecipe(factories, dirty, nonDirty, Items.DIRT, magnitude * 4);
        ModifiedStoneBricks.addRemoveModificationStonecuttingRecipe(factories, dirty, nonDirty);
    }

    public static void add(CollectedFactories factories) {
        addAddRemoveDirtRecipes(factories, SuCraftBlocks.DIRTY_STONE_BRICKS.fullBlockItemForDataGeneration(), Items.STONE_BRICKS, 1);
        addAddRemoveDirtRecipes(factories, SuCraftBlocks.DIRTY_STONE_BRICKS.slabItemForDataGeneration(), Items.STONE_BRICK_SLAB, 2);
        addAddRemoveDirtRecipes(factories, SuCraftBlocks.DIRTY_STONE_BRICKS.stairsItemForDataGeneration(), Items.STONE_BRICK_STAIRS, 1);
        addAddRemoveDirtRecipes(factories, SuCraftBlocks.DIRTY_STONE_BRICKS.wallItemForDataGeneration(), Items.STONE_BRICK_WALL, 1);
    }

}
