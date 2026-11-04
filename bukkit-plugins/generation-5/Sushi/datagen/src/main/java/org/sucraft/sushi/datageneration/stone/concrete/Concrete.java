package org.sucraft.sushi.datageneration.stone.concrete;

import it.unimi.dsi.fastutil.Pair;
import java.util.stream.Stream;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.Identifier;
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
            var fullBlock = BuiltInRegistries.ITEM.getValue(Identifier.withDefaultNamespace(dyeColor + "_concrete"));
            var slab = BuiltInRegistries.ITEM.getValue(Identifier.withDefaultNamespace(dyeColor + "_concrete_slab"));
            var stairs = BuiltInRegistries.ITEM.getValue(Identifier.withDefaultNamespace(dyeColor + "_concrete_stairs"));
            Stream.of(
                Pair.of(ItemTags.CONCRETE, fullBlock),
                Pair.of(ItemTags.CONCRETE_SLABS, slab),
                Pair.of(ItemTags.CONCRETE_STAIRS, stairs),
                Pair.of(SuCraftBlockItemTags.CONCRETE_WALLS.item(), custom.wallItemNMS()),
                Pair.of(SuCraftBlockItemTags.CONCRETE_BUTTONS.item(), custom.buttonItemNMS()),
                Pair.of(SuCraftBlockItemTags.CONCRETE_PRESSURE_PLATES.item(), custom.pressurePlateItemNMS())
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
