package org.sucraft.sushi.datageneration.stone.concrete;

import it.unimi.dsi.fastutil.Pair;
import java.util.stream.Stream;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;
import org.sucraft.sushi.gamecontent.common.SuCraftBlockItemTags;

public final class Concrete {

    private Concrete() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        for (var dyeColor : DyeColor.values()) {
            var custom = SuCraftBlocks.CONCRETE.get(dyeColor);
            var fullBlock = Items.CONCRETE.pick(dyeColor);
            var slab = Items.CONCRETE_SLAB.pick(dyeColor);
            var stairs = Items.CONCRETE_STAIRS.pick(dyeColor);
            var powderCustom = SuCraftBlocks.CONCRETE_POWDER.get(dyeColor);
            var powderFullBlock = Items.CONCRETE_POWDER.pick(dyeColor);
            Stream.of(
                Pair.of(ItemTags.CONCRETE, fullBlock),
                Pair.of(ItemTags.CONCRETE_SLABS, slab),
                Pair.of(ItemTags.CONCRETE_STAIRS, stairs),
                Pair.of(SuCraftBlockItemTags.CONCRETE_WALLS.item(), custom.wallItemNMS()),
                Pair.of(SuCraftBlockItemTags.CONCRETE_BUTTONS.item(), custom.buttonItemNMS()),
                Pair.of(SuCraftBlockItemTags.CONCRETE_PRESSURE_PLATES.item(), custom.pressurePlateItemNMS()),
                Pair.of(ItemTags.CONCRETE_POWDERS, powderFullBlock),
                Pair.of(SuCraftBlockItemTags.CONCRETE_POWDER_SLABS.item(), powderCustom.slabItemNMS()),
                Pair.of(SuCraftBlockItemTags.CONCRETE_POWDER_STAIRS.item(), powderCustom.stairsItemNMS())
            ).forEach(inputOutput ->
                factories.recipes().add(Recipes.start()
                    .addRecipeSupplier(function -> Recipes.RecipeSupplier.withSuffix(() -> function.provider
                            .shapeless(RecipeCategory.BUILDING_BLOCKS, inputOutput.right(), 8)
                            .requires(Ingredient.of(function.registries.lookupOrThrow(Registries.ITEM).getOrThrow(inputOutput.left())), 8)
                            .requires(Items.DYE.pick(dyeColor)),
                        "_by_dyeing"
                    ))
                )
            );
        }
    }

}
