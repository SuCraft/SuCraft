package org.sucraft.sushi.datageneration.wool;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.gamecontent.block.BlockSet;
import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;
import org.sucraft.sushi.gamecontent.common.SuCraftBlockItemTags;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

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
        // Add redyeing recipes
        for (var dyeColor : DyeColor.values()) {
            var custom = SuCraftBlocks.WOOL.get(dyeColor);
            List.<Function<BlockSet.WithWallAndButtonAndPressurePlate, ItemLike>>of(
                BlockSet.WithWall::wallItemNMS,
                BlockSet.WithButton::buttonItemNMS,
                BlockSet.WithPressurePlate::pressurePlateItemNMS
            ).forEach(itemGetter ->
                factories.recipes().add(Recipes.start()
                    .addRecipeSupplier(function -> Recipes.RecipeSupplier.withPrefix(() -> function.provider
                            .shapeless(RecipeCategory.BUILDING_BLOCKS, itemGetter.apply(custom))
                            .requires(Ingredient.of(Arrays.stream(DyeColor.values()).filter(otherDyeColor -> otherDyeColor != dyeColor).map(otherDyeColor -> itemGetter.apply(SuCraftBlocks.WOOL.get(otherDyeColor))).toArray(ItemLike[]::new)))
                            .requires(Items.DYE.pick(dyeColor)),
                        "dye_"
                    ))
                )
            );
        }
    }

}
