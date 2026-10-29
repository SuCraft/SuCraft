package org.sucraft.sushi.gamecontent.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.predicates.DataComponentMatchers;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.MinMaxBounds;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.advancements.triggers.RecipeUnlockedTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import org.sucraft.sushi.main.SushiPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public final class AutomaticRecipeAdvancementAdder {

    private AutomaticRecipeAdvancementAdder() {
        throw new UnsupportedOperationException();
    }

    public static void run() {

        SushiPlugin.getPluginLogger().info("Automatically adding recipe advancements...");

        // Get the advancements map as a mutable map
        var advancements = new HashMap<>(MinecraftServer.getServer().getAdvancements().advancements);
        MinecraftServer.getServer().getAdvancements().advancements = advancements;

        // Remove all advancements that were only added because it was mandatory during data generation
        var toRemove = new ArrayList<Identifier>();
        advancements.forEach((id, holder) -> {
            if (holder.value().requirements().requirements().stream().anyMatch(list -> list.contains("mandatory"))) {
                toRemove.add(id);
            }
        });
        toRemove.forEach(advancements::remove);

        // See which recipes are already rewarded by an advancement
        var alreadyRewardedRecipes = new HashSet<ResourceKey<Recipe<?>>>();
        MinecraftServer.getServer().getAdvancements().getAllAdvancements().forEach(advancementHolder -> {
            var advancement = advancementHolder.value();
            alreadyRewardedRecipes.addAll(advancement.rewards().recipes());
        });

        // Create advancements for all other recipes
        MinecraftServer.getServer().getRecipeManager().getRecipes().forEach(recipeHolder -> {
            if (alreadyRewardedRecipes.contains(recipeHolder.id())) return;
            var recipe = recipeHolder.value();
            List<Ingredient> ingredients = null;
            Holder<Item> result = null;
            if (recipe instanceof ShapedRecipe shaped) {
                ingredients = new ArrayList<>(shaped.getIngredients().stream().filter(Optional::isPresent).map(Optional::get).toList());
                result = shaped.result.item();
            } else if (recipe instanceof ShapelessRecipe shapeless) {
                ingredients = shapeless.placementInfo().ingredients();
                result = shapeless.result.item();
            } else if (recipe instanceof SingleItemRecipe singleItem) {
                ingredients = List.of(singleItem.input());
                result = singleItem.result.item();
            }
            if (ingredients != null) {
                ingredients = ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).toList();
                if (!ingredients.isEmpty()) {
                    addAdvancementForNonEmptyIngredients(recipeHolder, ingredients, result);
                }
            }
        });

    }

    private static JsonElement getItemCriterium(String item) {
        return JsonParser.parseString("""
            {
              "conditions": {
                "items": [
                  {
                    "items": "%s"
                  }
                ]
              },
              "trigger": "minecraft:inventory_changed"
            }
            """.formatted(item));
    }

    private static JsonElement getItemCriterium(Item item) {
        return getItemCriterium(item.keyInItemRegistry.toString());
    }

    private static JsonElement getItemCriterium(TagKey<Item> tag) {
        return getItemCriterium(tag.location().toString());
    }

    private static void addAdvancementForNonEmptyIngredients(RecipeHolder<?> recipeHolder, List<Ingredient> ingredients, Holder<Item> result) {
        var recipeKey = recipeHolder.id();
        var advancementId = recipeKey.identifier().withPrefix("recipes/");
        if (MinecraftServer.getServer().getAdvancements().advancements.containsKey(advancementId)) {
            return;
        }
        // Initialize data
        var requirementStrings = new ArrayList<String>();
        var criteriaMap = new Object2ObjectArrayMap<String, Criterion<?>>(4);
        // Populate data
        int[] actuallyAddedIngredientI = {0};
        var itemsAlreadyAdded = new HashSet<Item>();
        var tagsAlreadyAdded = new HashSet<TagKey<Item>>();
        for (var ingredient : ingredients) {
            var unwrapped = ingredient.values.unwrap();
            HolderSet<Item>[] items = new HolderSet[1];
            unwrapped.ifLeft(tag -> {
                if (tagsAlreadyAdded.add(tag)) {
                    items[0] = BuiltInRegistries.ITEM.getOrThrow(tag);
                }
            });
            unwrapped.ifRight(holders -> {
                var filteredHolders = holders.stream()
                    .filter(holder -> itemsAlreadyAdded.add(holder.value())).toList();
                if (!filteredHolders.isEmpty()) {
                    items[0] = HolderSet.direct(filteredHolders);
                }
            });
            if (items[0] != null) {
                if (items[0].size() == 1 && items[0].iterator().next().is(ItemTags.DYES)) { // Any dying recipe is unlocked with any dye
                    items[0] = BuiltInRegistries.ITEM.getOrThrow(ItemTags.DYES);
                }
                String key = "has_ingredient_" + (++actuallyAddedIngredientI[0]);
                criteriaMap.put(key, new Criterion<>(
                    CriteriaTriggers.INVENTORY_CHANGED,
                    new InventoryChangeTrigger.TriggerInstance(
                        Optional.empty(),
                        InventoryChangeTrigger.TriggerInstance.Slots.ANY,
                        List.of(
                            new ItemPredicate(
                                Optional.of(items[0]),
                                MinMaxBounds.Ints.ANY,
                                DataComponentMatchers.ANY
                            )
                        )
                    )
                ));
                requirementStrings.add(key);
            }
        }
        criteriaMap.put("has_result", new Criterion<>(
            CriteriaTriggers.INVENTORY_CHANGED,
            new InventoryChangeTrigger.TriggerInstance(
                Optional.empty(),
                InventoryChangeTrigger.TriggerInstance.Slots.ANY,
                List.of(
                    new ItemPredicate(
                        Optional.of(HolderSet.direct(result)),
                        MinMaxBounds.Ints.ANY,
                        DataComponentMatchers.ANY
                    )
                )
            )
        ));
        requirementStrings.add("has_result");
        criteriaMap.put("has_the_recipe", new Criterion<>(
            CriteriaTriggers.RECIPE_UNLOCKED,
            new RecipeUnlockedTrigger.TriggerInstance(
                Optional.empty(),
                HolderSet.direct(Holder.direct(recipeHolder.value()))
            )
        ));
        requirementStrings.add("has_the_recipe");
        requirementStrings.trimToSize();
        // Create and add advancement
        MinecraftServer.getServer().getAdvancements().advancements.put(advancementId, new AdvancementHolder(advancementId, new Advancement(
            Optional.of(Identifier.fromNamespaceAndPath(Identifier.DEFAULT_NAMESPACE, "recipes/root")),
            Optional.empty(),
            new AdvancementRewards(0, HolderSet.empty(), List.of(recipeKey), Optional.empty()),
            criteriaMap,
            new AdvancementRequirements(
                List.of(requirementStrings)
            ),
            false
        )));
    }

}
