package org.sucraft.sushi.datageneration.stonebricks.snowy;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.ModBlocks;
import org.sucraft.sushi.datageneration.common.ModItems;
import org.sucraft.sushi.datageneration.stonebricks.ModifiedStoneBricks;

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

        // Block model family
        factories.models().addBlock(
            generators -> generators.family(ModBlocks.SNOWY_STONE_BRICKS)
                .slab(ModBlocks.SNOWY_STONE_BRICK_SLAB)
                .stairs(ModBlocks.SNOWY_STONE_BRICK_STAIRS)
                .wall(ModBlocks.SNOWY_STONE_BRICK_WALL)
        );

        // Full block
        factories.blockLootTables().add(provider -> provider.dropSelf(ModBlocks.SNOWY_STONE_BRICKS));
        addAddRemoveSnowRecipes(factories, ModItems.SNOWY_STONE_BRICKS, Items.STONE_BRICKS, 1);

        // Slab
        factories.blockLootTables().add(provider -> provider.add(ModBlocks.SNOWY_STONE_BRICK_SLAB, provider::createSlabItemTable));
        addAddRemoveSnowRecipes(factories, ModItems.SNOWY_STONE_BRICK_SLAB, Items.STONE_BRICK_SLAB, 2);

        // Stairs
        factories.blockLootTables().add(provider -> provider.dropSelf(ModBlocks.SNOWY_STONE_BRICK_STAIRS));
        addAddRemoveSnowRecipes(factories, ModItems.SNOWY_STONE_BRICK_STAIRS, Items.STONE_BRICK_STAIRS, 1);

        // Wall
        factories.blockLootTables().add(provider -> provider.dropSelf(ModBlocks.SNOWY_STONE_BRICK_WALL));
        addAddRemoveSnowRecipes(factories, ModItems.SNOWY_STONE_BRICK_WALL, Items.STONE_BRICK_WALL, 1);

    }

}
