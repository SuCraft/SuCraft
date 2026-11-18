package org.sucraft.sushi.datageneration.flora;

import java.util.Map;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Items;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;

public final class Flowers {

    private Flowers() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        // Dye recipes
        Map.of(
            SuCraftBlocks.FRINGED_LAVENDER.itemNMS(), Items.DYE.purple(),
            SuCraftBlocks.TWISTED_LAVENDER.itemNMS(), Items.DYE.purple(),
            SuCraftBlocks.TALL_LAVENDER.itemNMS(), Items.DYE.purple(),
            SuCraftBlocks.WHITE_FRINGED_LAVENDER.itemNMS(), Items.DYE.white(),
            SuCraftBlocks.WHITE_TWISTED_LAVENDER.itemNMS(), Items.DYE.white(),
            SuCraftBlocks.WHITE_TALL_LAVENDER.itemNMS(), Items.DYE.white(),
            SuCraftBlocks.SHORT_ASTILBE.itemNMS(), Items.DYE.pink(),
            SuCraftBlocks.ASTILBE.itemNMS(), Items.DYE.pink(),
            SuCraftBlocks.TALL_ASTILBE.itemNMS(), Items.DYE.pink()
        ).forEach((flower, dye) -> factories.recipes().add(Recipes.start()
            .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(
                () -> function.provider
                    .shapeless(RecipeCategory.MISC, dye)
                    .requires(flower),
                "_from_" + flower.builtInRegistryHolder().key().identifier().getPath())
            )
        ));
    }

}
