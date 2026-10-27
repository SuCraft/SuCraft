package org.sucraft.sushi.datageneration.stone.regular;

import com.google.common.math.IntMath;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;

public final class SnowyStoneBricks {

    private SnowyStoneBricks() {
        throw new UnsupportedOperationException();
    }

    private static void addAddRemoveSnowRecipes(CollectedFactories factories, Item snowy, Item nonSnowy, int itemsPerRecipeMultiplier, int modifiersPerRecipeMultiplier) {
        int gcd1 = IntMath.gcd(itemsPerRecipeMultiplier, modifiersPerRecipeMultiplier);
        ModifiedStoneBricks.addAddModificationRecipe(factories, snowy, nonSnowy, Items.SNOWBALL, itemsPerRecipeMultiplier / gcd1, modifiersPerRecipeMultiplier / gcd1);
        int gcd2 = IntMath.gcd(itemsPerRecipeMultiplier * 4, modifiersPerRecipeMultiplier);
        ModifiedStoneBricks.addAddModificationRecipe(factories, snowy, nonSnowy, Items.SNOW_BLOCK, itemsPerRecipeMultiplier * 4 / gcd2, modifiersPerRecipeMultiplier / gcd2);
        ModifiedStoneBricks.addRemoveModificationStonecuttingRecipe(factories, snowy, nonSnowy);
    }

    public static void add(CollectedFactories factories) {
        addAddRemoveSnowRecipes(factories, SuCraftBlocks.SNOWY_STONE_BRICKS.fullBlockItemForDataGeneration(), Items.STONE_BRICKS, 1, 1);
        addAddRemoveSnowRecipes(factories, SuCraftBlocks.SNOWY_STONE_BRICKS.slabItemForDataGeneration(), Items.STONE_BRICK_SLAB, 2, 1);
        addAddRemoveSnowRecipes(factories, SuCraftBlocks.SNOWY_STONE_BRICKS.stairsItemForDataGeneration(), Items.STONE_BRICK_STAIRS, 1, 1);
        addAddRemoveSnowRecipes(factories, SuCraftBlocks.SNOWY_STONE_BRICKS.wallItemForDataGeneration(), Items.STONE_BRICK_WALL, 1, 1);
        // addAddRemoveSnowRecipes(factories, SuCraftBlocks.SNOWY_STONE_BRICKS.buttonItemForDataGeneration(), SuCraftBlocks.STONE_BRICKS.buttonItemForDataGeneration(), 1, 1); // TODO add
        // addAddRemoveSnowRecipes(factories, SuCraftBlocks.SNOWY_STONE_BRICKS.pressurePlateItemForDataGeneration(), SuCraftBlocks.STONE_BRICKS.pressurePlateItemForDataGeneration(), 1, 2); // TODO add
    }

}
