package org.sucraft.sushi.datageneration.stone.regular;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.CommonMacros;
import org.sucraft.sushi.datageneration.common.ModBlocks;
import org.sucraft.sushi.datageneration.common.ModItems;

public final class DirtyStoneBricks {

    private DirtyStoneBricks() {
        throw new UnsupportedOperationException();
    }

    private static void addAddRemoveDirtRecipes(CollectedFactories factories, Item dirty, Item nonDirty, int magnitude) {
        ModifiedStoneBricks.addAddModificationRecipe(factories, dirty, nonDirty, Items.DIRT, magnitude * 4);
        ModifiedStoneBricks.addRemoveModificationStonecuttingRecipe(factories, dirty, nonDirty);
    }

    public static void add(CollectedFactories factories) {
        CommonMacros.addFamily(
            factories,
            ModBlocks.DIRTY_STONE_BRICKS,
            ModBlocks.DIRTY_STONE_BRICK_SLAB,
            ModBlocks.DIRTY_STONE_BRICK_STAIRS,
            ModBlocks.DIRTY_STONE_BRICK_WALL
        );
        addAddRemoveDirtRecipes(factories, ModItems.DIRTY_STONE_BRICKS, Items.STONE_BRICKS, 1);
        addAddRemoveDirtRecipes(factories, ModItems.DIRTY_STONE_BRICK_SLAB, Items.STONE_BRICK_SLAB, 2);
        addAddRemoveDirtRecipes(factories, ModItems.DIRTY_STONE_BRICK_STAIRS, Items.STONE_BRICK_STAIRS, 1);
        addAddRemoveDirtRecipes(factories, ModItems.DIRTY_STONE_BRICK_WALL, Items.STONE_BRICK_WALL, 1);
    }

}
