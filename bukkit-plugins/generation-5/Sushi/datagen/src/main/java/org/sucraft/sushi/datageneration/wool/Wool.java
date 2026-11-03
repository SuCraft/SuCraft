package org.sucraft.sushi.datageneration.wool;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;

public final class Wool {

    private Wool() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Add carpet to pressure plate recipes
        for (var dyeColor : DyeColor.values()) {
            var woolType = SuCraftBlocks.WOOL.get(dyeColor);
            factories.recipes().add(Recipes.start()
                .addRecipeSupplier(function -> () -> function.provider
                    .shaped(RecipeCategory.REDSTONE, woolType.pressurePlateItemNMS())
                    .pattern("CCC")
                    .define('C', Items.CARPET.pick(dyeColor))
                )
            );
        }
    }

}
