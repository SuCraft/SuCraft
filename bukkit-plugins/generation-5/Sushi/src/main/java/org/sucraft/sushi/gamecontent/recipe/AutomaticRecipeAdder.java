package org.sucraft.sushi.gamecontent.recipe;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import org.jspecify.annotations.Nullable;
import org.sucraft.sushi.gamecontent.block.OldReplacedBlocks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class AutomaticRecipeAdder {

    public static final int STAIRS_CRAFTING_OUTPUT_COUNT = 6;

    public static final Set<Item> STORAGE_BLOCKS = Stream.of(
        Items.BAMBOO_BLOCK,
        Items.BONE_BLOCK,
        Items.BRICKS,
        Items.CLAY,
        Items.COAL_BLOCK,
        Items.COPPER_BLOCK.weathering().unaffected(),
        Items.DIAMOND_BLOCK,
        Items.DRIED_KELP_BLOCK,
        Items.EMERALD_BLOCK,
        Items.GOLD_BLOCK,
        Items.HAY_BLOCK,
        Items.HONEY_BLOCK,
        Items.IRON_BLOCK,
        Items.LAPIS_BLOCK,
        Items.NETHERITE_BLOCK,
        Items.NETHER_BRICKS,
        Items.NETHER_WART_BLOCK,
        Items.REDSTONE_BLOCK,
        Items.RESIN_BLOCK,
        Items.SLIME_BLOCK,
        Items.SNOW_BLOCK
    ).collect(Collectors.toSet());

    private AutomaticRecipeAdder() {
        throw new UnsupportedOperationException();
    }

    private record SimpleRecipeDescription(
        ResourceKey<Recipe<?>> id,
        Recipe<?> recipe,
        Item output,
        int resultCount,
        Set<Item> inputs
    ) {

        SimpleRecipeDescription(ResourceKey<Recipe<?>> id, Recipe<?> recipe, ItemStackTemplate output, Set<Item> inputs) {
            this(id, recipe, output.item().value(), output.count(), inputs);
        }

        @Override
        public String toString() {
            return MinecraftServer.getServer().getRecipeManager().getRecipes().stream().filter(holder -> holder.value() == recipe).findFirst().map(holder -> holder.id().identifier()).orElse(null) + " : " + resultCount + " x " + output.keyInItemRegistry + " (" + recipe.getType() + ")";
        }

    }

    private static @Nullable SimpleRecipeDescription wrap(RecipeHolder<?> holder) {
        var recipe = holder.value();
        if (recipe instanceof SingleItemRecipe singleItemRecipe) {
            return new SimpleRecipeDescription(
                holder.id(),
                recipe,
                singleItemRecipe.result,
                singleItemRecipe.input().items().map(Holder::value).collect(Collectors.toSet())
            );
        } else if (recipe instanceof ShapelessRecipe shapelessRecipe) {
            return new SimpleRecipeDescription(
                holder.id(),
                recipe,
                shapelessRecipe.result,
                ((ShapelessCraftingRecipeDisplay) shapelessRecipe.display().getFirst()).ingredients().stream()
                    .flatMap(AutomaticRecipeAdder::getSlotDisplayItems).collect(Collectors.toSet())
            );
        } else if (recipe instanceof ShapedRecipe shapedRecipe) {
            return new SimpleRecipeDescription(
                holder.id(),
                recipe,
                shapedRecipe.result,
                shapedRecipe.getIngredients().stream().filter(Optional::isPresent)
                    .flatMap(ingredient -> ingredient.get().items())
                    .map(Holder::value).collect(Collectors.toSet())
            );
        }
        return null;
    }

    private static Stream<Item> getSlotDisplayItems(SlotDisplay display) {
        if (display instanceof SlotDisplay.Composite(List<SlotDisplay> contents)) {
            return contents.stream().flatMap(AutomaticRecipeAdder::getSlotDisplayItems);
        } else if (display instanceof SlotDisplay.TagSlotDisplay(HolderSet<Item> tag)) {
            return tag.stream().map(Holder::value);
        } else if (display instanceof SlotDisplay.ItemSlotDisplay(Holder<Item> item)) {
            return Stream.of(item.value());
        } else if (display instanceof SlotDisplay.ItemStackSlotDisplay(ItemStackTemplate itemStack)) {
            return Stream.of(itemStack.item().value());
        } else {
            throw new UnsupportedOperationException("Cannot process slot display type: " + display.type());
        }
    }

    private static @Nullable Item filterOut(@Nullable Item item, Set<Item> filter) {
        return item == null ? null : filter.contains(item) ? null : item;
    }

    private static BiConsumer<Item, Item> invert(BiConsumer<Item, Item> adder) {
        return (input, output) -> adder.accept(output, input);
    }

    private static BiConsumer<Item, Item> conditional(BiPredicate<Item, Item> predicate, BiConsumer<Item, Item> adder) {
        return (input, output) -> {
            if (predicate.test(input, output)) {
                adder.accept(input, output);
            }
        };
    }

    public static void run() {

        // Collect ignored items
        var ignoredItems = OldReplacedBlocks.LIST.stream()
            .map(it -> BuiltInRegistries.ITEM.get(it.oldBlock()))
            .filter(Optional::isPresent).map(Optional::get).map(Holder.Reference::value).collect(Collectors.toSet());

        // Get the existing recipes (by their potential input and output)
        var recipesByInputOutput = new Object2ObjectOpenHashMap<Pair<Item, Item>, List<SimpleRecipeDescription>>();

        class RecipeAdders {

            private @Nullable SimpleRecipeDescription mapRecipeByInputOutput(RecipeHolder<?> holder) {
                var description = wrap(holder);
                if (description != null) {
                    final var finalOutput = description.output;
                    description.inputs().forEach(
                        input -> recipesByInputOutput.computeIfAbsent(
                            Pair.of(input, finalOutput),
                            _ -> new ArrayList<>(2)
                        ).add(description)
                    );
                }
                return description;
            }

            private void unmapRecipeByInputOutput(SimpleRecipeDescription description) {
                final var finalOutput = description.output;
                description.inputs().forEach(input -> {
                    var recipesForInputOutput = recipesByInputOutput.get(Pair.of(input, finalOutput));
                    if (recipesForInputOutput != null) {
                        recipesForInputOutput.remove(description);
                    }
                });
            }

            private @Nullable SimpleRecipeDescription getExistingRecipe(Item output, RecipeInput input, @Nullable List<SimpleRecipeDescription> recipes, Set<RecipeType<?>> types) {
                if (recipes == null) return null;
                for (var recipe : recipes) {
                    try {
                        if (types.contains(recipe.recipe.getType())) {
                            if (((Recipe) recipe.recipe).matches(input, null)) {
                                if (recipe.output == output) {
                                    return recipe;
                                }
                            }
                        }
                    } catch (Exception _) {
                    }
                }
                return null;
            }

            private @Nullable SimpleRecipeDescription getExistingCraftingRecipe(Item output, CraftingInput input) {
                return getExistingRecipe(output, input, recipesByInputOutput.get(Pair.of(input.items().getFirst().getItem(), output)), Set.of(RecipeType.CRAFTING));
            }

            private @Nullable SimpleRecipeDescription getExistingSingleItemRecipe(Item output, SingleRecipeInput input, Set<RecipeType<?>> types) {
                return getExistingRecipe(output, input, recipesByInputOutput.get(Pair.of(input.item().getItem(), output)), types);
            }

            private @Nullable SimpleRecipeDescription getExistingSingleItemRecipe(Item output, Item ingredient, Set<RecipeType<?>> types) {
                return this.getExistingSingleItemRecipe(output, new SingleRecipeInput(new ItemStack(ingredient)), types);
            }

            private @Nullable SimpleRecipeDescription getExistingCraftingRecipe3x3(Item output, List<Item> inputs) {
                return this.getExistingCraftingRecipe(output, CraftingInput.of(3, 3, inputs.stream().map(item -> item == Items.AIR ? ItemStack.EMPTY : new ItemStack(item)).toList()));
            }

            private @Nullable SimpleRecipeDescription getExistingCraftingRecipeShapeless(Item output, List<Item> inputs) {
                List<ItemStack> inputItemStacks = inputs.stream().map(item -> item == Items.AIR ? ItemStack.EMPTY : new ItemStack(item)).toList();
                Stream<int[]> options = Stream.of(new int[0]);
                for (int inputI = 0; inputI < inputItemStacks.size(); inputI++) {
                    options = options.flatMap(option -> IntStream.range(0, 9).mapToObj(newIndex -> {
                        for (int existing : option) {
                            if (existing == newIndex) {
                                return null;
                            }
                        }
                        var newOption = Arrays.copyOf(option, option.length + 1);
                        newOption[option.length] = newIndex;
                        return newOption;
                    }).filter(Objects::nonNull));
                }
                return options.map(option -> {
                    var shapedInputs = new ItemStack[9];
                    Arrays.fill(shapedInputs, ItemStack.EMPTY);
                    for (int optionI = 0; optionI < option.length; optionI++) {
                        shapedInputs[option[optionI]] = inputItemStacks.get(optionI);
                    }
                    return this.getExistingCraftingRecipe(output, CraftingInput.of(3, 3, Arrays.asList(shapedInputs)));
                }).filter(Objects::nonNull).findFirst().orElse(null);
            }

            private void addRecipes(Item output, @Nullable Item input, BiConsumer<Item, Item>... adders) {
                if (input == null) return;
                for (var adder : adders) {
                    adder.accept(input, output);
                }
            }

            private void removeRecipeFromServer(SimpleRecipeDescription recipe) {
                MinecraftServer.getServer().getRecipeManager().removeRecipe(recipe.id());
                this.unmapRecipeByInputOutput(recipe);
            }

            private void addRecipeToServer(Identifier key, @Nullable String reason, Recipe<?> recipe) {
                var holder = new RecipeHolder<>(ResourceKey.create(Registries.RECIPE, key.withSuffix(reason != null ? "_" + reason : "")), recipe);
                MinecraftServer.getServer().getRecipeManager().addRecipe(holder);
                this.mapRecipeByInputOutput(holder);
            }

            private void addShapelessCraftingRecipeToServer(Identifier key, @Nullable String reason, Item output, int outputCount, List<Item> inputs, CraftingBookCategory category, @Nullable Function<Item, @Nullable String> group) {
                if (ignoredItems.contains(output) || inputs.stream().anyMatch(ignoredItems::contains)) {
                    return;
                }
                this.addRecipeToServer(key, reason, new ShapelessRecipe(new Recipe.CommonInfo(true), new CraftingRecipe.CraftingBookInfo(category, group != null ? Optional.ofNullable(group.apply(output)).orElse("") : ""), new ItemStackTemplate(output.builtInRegistryHolder(), outputCount, DataComponentPatch.EMPTY), inputs.stream().map(Ingredient::of).toList()));
            }

            private void addShapelessCraftingRecipeToServer(Identifier key, @Nullable String reason, Item output, int outputCount, Item input, CraftingBookCategory category, @Nullable BiFunction<Item, Item, @Nullable String> group) {
                this.addShapelessCraftingRecipeToServer(key, reason, output, outputCount, List.of(input), category, group == null ? null : actualOutput -> group.apply(input, actualOutput));
            }

            private void add3x3CraftingRecipeToServer(Identifier key, @Nullable String reason, Item output, int outputCount, List<Item> inputs, CraftingBookCategory category, @Nullable Function<Item, @Nullable String> group) {
                if (ignoredItems.contains(output) || inputs.stream().filter(Objects::nonNull).anyMatch(ignoredItems::contains)) {
                    return;
                }
                this.addRecipeToServer(key, reason, new ShapedRecipe(new Recipe.CommonInfo(true), new CraftingRecipe.CraftingBookInfo(category, group != null ? Optional.ofNullable(group.apply(output)).orElse("") : ""), new ShapedRecipePattern(3, 3, inputs.stream().map(input -> input == Items.AIR ? Optional.<Ingredient>empty() : Optional.of(Ingredient.of(input))).toList(), Optional.empty()), new ItemStackTemplate(output.builtInRegistryHolder(), outputCount, DataComponentPatch.EMPTY)));
            }

            private void addStonecuttingRecipeToServer(Identifier key, @Nullable String reason, Item output, int outputCount, Item input) {
                if (ignoredItems.contains(output) || ignoredItems.contains(input)) {
                    return;
                }
                this.addRecipeToServer(key, reason != null ? "stonecutting_" + reason : "stonecutting", new StonecutterRecipe(new Recipe.CommonInfo(true), Ingredient.of(input), new ItemStackTemplate(output.builtInRegistryHolder(), outputCount, DataComponentPatch.EMPTY)));
            }

            private void addSmeltingRecipeToServer(Identifier key, @Nullable String reason, Item output, Item input, CookingBookCategory category, @Nullable BiFunction<Item, Item, @Nullable String> group, float experience, int cookingTime) {
                if (ignoredItems.contains(output) || ignoredItems.contains(input)) {
                    return;
                }
                this.addRecipeToServer(key, reason != null ? "smelting_" + reason : "smelting", new SmeltingRecipe(new Recipe.CommonInfo(true), new AbstractCookingRecipe.CookingBookInfo(category, group != null ? Optional.ofNullable(group.apply(input, output)).orElse("") : ""), Ingredient.of(input), new ItemStackTemplate(output.builtInRegistryHolder(), 1, DataComponentPatch.EMPTY), experience, cookingTime));
            }

            private BiConsumer<Item, Item> addShapelessCrafting(int outputCount, @Nullable String reason, Function<Item, List<Item>> inputFunction, CraftingBookCategory category, @Nullable Function<Item, @Nullable String> group) {
                return (input, output) -> {
                    var inputs = inputFunction.apply(input);
                    var existingRecipe = this.getExistingCraftingRecipeShapeless(output, inputs);
                    if (existingRecipe != null) {
                        if (existingRecipe.output != output || existingRecipe.resultCount >= outputCount) {
                            return;
                        }
                        this.removeRecipeFromServer(existingRecipe);
                    }
                    this.addShapelessCraftingRecipeToServer(output.keyInItemRegistry, reason, output, outputCount, inputs, category, group);
                };
            }

            private BiConsumer<Item, Item> add3x3Crafting(int outputCount, @Nullable String reason, Function<Item, List<Item>> inputFunction, CraftingBookCategory category, @Nullable BiFunction<Item, Item, @Nullable String> group) {
                return (input, output) -> {
                    var inputs = inputFunction.apply(input);
                    var existingRecipe = this.getExistingCraftingRecipe3x3(output, inputs);
                    if (existingRecipe != null) {
                        if (existingRecipe.output != output || existingRecipe.resultCount >= outputCount) {
                            return;
                        }
                        this.removeRecipeFromServer(existingRecipe);
                    }
                    this.add3x3CraftingRecipeToServer(output.keyInItemRegistry, reason, output, outputCount, inputs, category, group == null ? null : actualOutput -> group.apply(input, actualOutput));
                };
            }

            private BiConsumer<Item, Item> add3x3Crafting(int outputCount, @Nullable String reason, Function<Item, List<Item>> inputFunction, CraftingBookCategory category) {
                return this.add3x3Crafting(outputCount, reason, inputFunction, category, null);
            }

            private BiConsumer<Item, Item> add1x2RectangleCrafting(@Nullable String reason, int outputCount, CraftingBookCategory category) {
                return this.add3x3Crafting(outputCount, reason, input -> List.of(input, Items.AIR, Items.AIR, input, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR), category);
            }

            private BiConsumer<Item, Item> add1x3RectangleCrafting(@Nullable String reason, int outputCount, CraftingBookCategory category) {
                return this.add3x3Crafting(outputCount, reason, input -> List.of(input, Items.AIR, Items.AIR, input, Items.AIR, Items.AIR, input, Items.AIR, Items.AIR), category);
            }

            private BiConsumer<Item, Item> add2x2RectangleCrafting(@Nullable String reason, int outputCount, CraftingBookCategory category) {
                return this.add3x3Crafting(outputCount, reason, input -> List.of(input, input, Items.AIR, input, input, Items.AIR, Items.AIR, Items.AIR, Items.AIR), category);
            }

            private BiConsumer<Item, Item> add3x2RectangleCrafting(@Nullable String reason, int outputCount, CraftingBookCategory category) {
                return this.add3x3Crafting(outputCount, reason, input -> List.of(input, input, input, input, input, input, Items.AIR, Items.AIR, Items.AIR), category);
            }

            private BiConsumer<Item, Item> addStairsCrafting() {
                return this.add3x3Crafting(STAIRS_CRAFTING_OUTPUT_COUNT, null, input -> List.of(input, Items.AIR, Items.AIR, input, input, Items.AIR, input, input, input), CraftingBookCategory.BUILDING, (input, _) -> input.builtInRegistryHolder().is(ItemTags.PLANKS) ? "wooden_stairs" : null);
            }

            private BiConsumer<Item, Item> addSlabCrafting() {
                return this.add3x3Crafting(6, null, input -> List.of(input, input, input, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR), CraftingBookCategory.BUILDING, (input, _) -> input.builtInRegistryHolder().is(ItemTags.PLANKS) ? "wooden_slab" : null);
            }

            private BiConsumer<Item, Item> addFenceCrafting() {
                return conditional((input, _) -> input.builtInRegistryHolder().is(ItemTags.PLANKS), this.add3x3Crafting(3, null, input -> List.of(input, Items.STICK, input, input, Items.STICK, input, Items.AIR, Items.AIR, Items.AIR), CraftingBookCategory.MISC, (input, _) -> input.builtInRegistryHolder().is(ItemTags.PLANKS) ? "wooden_fence" : null));
            }

            private BiConsumer<Item, Item> addFenceGateCrafting() {
                return conditional((input, _) -> input.builtInRegistryHolder().is(ItemTags.PLANKS), this.add3x3Crafting(1, null, input -> List.of(Items.STICK, input, Items.STICK, Items.STICK, input, Items.STICK, Items.AIR, Items.AIR, Items.AIR), CraftingBookCategory.REDSTONE, (input, _) -> input.builtInRegistryHolder().is(ItemTags.PLANKS) ? "wooden_fence_gate" : null));
            }

            private BiConsumer<Item, Item> addSignCrafting() {
                return this.add3x3Crafting(3, null, input -> List.of(input, input, input, input, input, input, Items.AIR, Items.STICK, Items.AIR), CraftingBookCategory.MISC, (input, _) -> input.builtInRegistryHolder().is(ItemTags.PLANKS) ? "wooden_sign" : null);
            }

            private BiConsumer<Item, Item> addButtonCrafting() {
                return this.add3x3Crafting(1, null, input -> List.of(input, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR), CraftingBookCategory.REDSTONE, (input, _) -> input.builtInRegistryHolder().is(ItemTags.PLANKS) ? "wooden_button" : null);
            }

            private BiConsumer<Item, Item> addDoorCrafting() {
                return this.add3x3Crafting(3, null, input -> List.of(input, input, Items.AIR, input, input, Items.AIR, input, input, Items.AIR), CraftingBookCategory.REDSTONE, (input, _) -> input.builtInRegistryHolder().is(ItemTags.PLANKS) ? "wooden_door" : null);
            }

            private BiConsumer<Item, Item> addPressurePlateCrafting() {
                return this.add3x3Crafting(1, null, input -> List.of(input, input, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR), CraftingBookCategory.REDSTONE, (input, _) -> input.builtInRegistryHolder().is(ItemTags.PLANKS) ? "wooden_pressure_plate" : null);
            }

            private BiConsumer<Item, Item> addTrapdoorCrafting() {
                return this.add3x3Crafting(2, null, input -> List.of(input, input, input, input, input, input, Items.AIR, Items.AIR, Items.AIR), CraftingBookCategory.REDSTONE, (input, _) -> input.builtInRegistryHolder().is(ItemTags.PLANKS) ? "wooden_trapdoor" : null);
            }

            private BiConsumer<Item, Item> addStonecutting(int outputCount, @Nullable String reason) {
                return (input, output) -> {
                    var existingRecipe = this.getExistingSingleItemRecipe(output, input, Set.of(RecipeType.STONECUTTING));
                    if (existingRecipe != null) {
                        if (existingRecipe.output != output || existingRecipe.resultCount >= outputCount) {
                            return;
                        }
                        this.removeRecipeFromServer(existingRecipe);
                    }
                    this.addStonecuttingRecipeToServer(output.keyInItemRegistry, reason, output, outputCount, input);
                };
            }

            private BiConsumer<Item, Item> addStonecutting(int outputCount) {
                return this.addStonecutting(outputCount, null);
            }

            private BiConsumer<Item, Item> addSmelting(float experience, int cookingTime, @Nullable String reason) {
                return (input, output) -> {
                    var existingRecipe = this.getExistingSingleItemRecipe(output, input, Set.of(RecipeType.SMELTING));
                    if (existingRecipe != null) {
                        if (existingRecipe.output != output || existingRecipe.resultCount >= 1) {
                            return;
                        }
                        this.removeRecipeFromServer(existingRecipe);
                    }
                    this.addSmeltingRecipeToServer(output.keyInItemRegistry, reason, input, output, CookingBookCategory.BLOCKS, null, experience, cookingTime);
                };
            }

            private BiConsumer<Item, Item> addSmelting(float experience, int cookingTime) {
                return this.addSmelting(experience, cookingTime, null);
            }

        }
        var adders = new RecipeAdders();

        MinecraftServer.getServer().getRecipeManager().getRecipes().forEach(adders::mapRecipeByInputOutput);

        // Go over all items to see if we need to add recipes for them
        BuiltInRegistries.ITEM.forEach(item -> {
            adders.addRecipes(
                item,
                getSuffixBase(item, "_stairs"),
                adders.addStairsCrafting(),
                adders.addStonecutting(1),
                // adders::addToBaseSingleCrafting,
                invert(adders.addStonecutting(1, "from_stairs"))
            );
            adders.addRecipes(
                item,
                getSuffixBase(item, "_slab"),
                adders.addSlabCrafting(),
                adders.addStonecutting(2),
                conditional((input, _) -> getPrefixShape(input, "chiseled_") == null, invert(adders.addShapelessCrafting(1, "from_slabs", slab -> List.of(slab, slab), CraftingBookCategory.BUILDING, null)))
            );
            adders.addRecipes(
                item,
                getSuffixBase(item, "_wall"),
                adders.add3x2RectangleCrafting(null, 6, CraftingBookCategory.BUILDING),
                adders.addStonecutting(1),
                // adders::addToBaseSingleCrafting,
                invert(adders.addStonecutting(1, "from_wall"))
            );
            adders.addRecipes(
                item,
                getSuffixBase(item, "_pillar"),
                adders.add1x3RectangleCrafting("from_non_pillar", 3, CraftingBookCategory.BUILDING),
                adders.addStonecutting(1),
                // adders::addToBaseSingleCrafting,
                invert(adders.addStonecutting(1, "from_pillar"))
            );
            adders.addRecipes(
                item,
                filterOut(getSuffixBase(item, "_fence"), STORAGE_BLOCKS),
                adders.addFenceCrafting()
            );
            adders.addRecipes(
                item,
                filterOut(getSuffixBase(item, "_fence_gate"), STORAGE_BLOCKS),
                adders.addFenceGateCrafting()
            );
            adders.addRecipes(
                item,
                filterOut(getSuffixBase(item, "_sign", List.of("_planks")), STORAGE_BLOCKS),
                adders.addSignCrafting()
            );
            adders.addRecipes(
                item,
                filterOut(getSuffixBase(item, "_button"), STORAGE_BLOCKS),
                adders.addButtonCrafting(),
                adders.addStonecutting(1)
                // invert(adders.addStonecutting(1, "from_button"))
            );
            adders.addRecipes(
                item,
                filterOut(getSuffixBase(item, "_door", List.of("_planks")), STORAGE_BLOCKS),
                adders.addDoorCrafting()
            );
            adders.addRecipes(
                item,
                filterOut(getSuffixBase(item, "_pressure_plate"), STORAGE_BLOCKS),
                adders.addPressurePlateCrafting()
            );
            adders.addRecipes(
                item,
                filterOut(getSuffixBase(item, "_trapdoor", List.of("_planks")), STORAGE_BLOCKS),
                adders.addTrapdoorCrafting()
            );
            adders.addRecipes(
                item,
                getSuffixBase(item, "_tiles"),
                adders.addStonecutting(1),
                invert(adders.addStonecutting(1, "from_tiles"))
            );
            adders.addRecipes(
                item,
                getSuffixBase(item, "_bricks"),
                (input, output) -> {
                    Item polished = getPrefixShape(input, "polished_");
                    if (polished != null) {
                        adders.add2x2RectangleCrafting("from_polished", 4, CraftingBookCategory.BUILDING).accept(polished, output);
                    } else {
                        adders.add2x2RectangleCrafting("from_non_brick", 4, CraftingBookCategory.BUILDING).accept(input, output);
                    }
                },
                adders.addStonecutting(1),
                invert(adders.addStonecutting(1, "from_bricks"))
            );
            adders.addRecipes(
                item,
                getStartOrMiddleBase(item, "polished_"),
                adders.add2x2RectangleCrafting("from_unpolished", 4, CraftingBookCategory.BUILDING),
                adders.addStonecutting(1),
                invert(adders.addStonecutting(1, "from_polished"))
            );
            adders.addRecipes(
                item,
                getStartOrMiddleBase(item, "cut_"),
                adders.add2x2RectangleCrafting("from_uncut", 4, CraftingBookCategory.BUILDING),
                adders.addStonecutting(1),
                invert(adders.addStonecutting(1, "from_cut"))
            );
            adders.addRecipes(
                item,
                getStartOrMiddleBase(item, "chiseled_"),
                adders.addStonecutting(1),
                invert(adders.addStonecutting(1, "from_chiseled"))
            );
            adders.addRecipes(
                item,
                getSuffixShape(getStartOrMiddleBase(item, "chiseled_"), "_slab"),
                adders.add1x2RectangleCrafting("from_unchiseled_slabs", 1, CraftingBookCategory.BUILDING)
            );
            adders.addRecipes(
                item,
                getStartOrMiddleBase(item, "smooth_"),
                adders.addSmelting(0.1f, 200)
            );
            adders.addRecipes(
                item,
                getStartOrMiddleBase(item, "cracked_"),
                adders.addSmelting(0.1f, 200)
            );
            adders.addRecipes(
                item,
                getStartOrMiddleBase(item, "cobbled_"),
                adders.addStonecutting(1, "from_uncobbled"),
                adders.addSmelting(0.1f, 200)
            );
            adders.addRecipes(
                item,
                getStartOrMiddleBase(item, "stripped_"),
                adders.addStonecutting(1, "from_unstripped")
            );
            adders.addRecipes(
                item,
                getSuffixBase(item, "_log", List.of("_wood")),
                adders.addStonecutting(1, "from_wood")
            );
            adders.addRecipes(
                item,
                getSuffixBase(item, "_planks", List.of("_log")),
                adders.addStonecutting(4, "from_log")
            );
            adders.addRecipes(
                item,
                getPrefixShape(getSuffixBase(item, "_planks", List.of("_log")), "stripped_"),
                adders.addStonecutting(4, "from_stripped_log")
            );
        });

    }

    private static @Nullable String getSuffixBasePath(String path, String suffix) {
        if (!path.endsWith(suffix)) return null;
        return path.substring(0, path.length() - suffix.length());
    }

    private static @Nullable String getStartOrMiddleBasePath(String path, String substring) {
        int chiseledIndex = path.indexOf(substring);
        if (chiseledIndex == -1) return null;
        return (chiseledIndex == 0 ? "" : path.substring(0, chiseledIndex)) + path.substring(chiseledIndex + substring.length());
    }

    private static @Nullable Item getSuffixBase(Item item, String suffix, List<String> baseSuffixes) {
        if (!(item instanceof BlockItem)) return null;
        var key = item.keyInItemRegistry;
        var path = key.getPath();
        var base = getSuffixBasePath(path, suffix);
        if (base == null) return null;
        for (var baseSuffix : baseSuffixes) {
            for (var namespace : List.of(key.getNamespace(), Identifier.DEFAULT_NAMESPACE)) {
                var blockItem = BuiltInRegistries.ITEM.get(Identifier.fromNamespaceAndPath(namespace, base + baseSuffix));
                if (blockItem.isPresent() && blockItem.get().value() instanceof BlockItem) {
                    return blockItem.get().value();
                }
            }
        }
        return null;
    }

    private static @Nullable Item getSuffixBase(Item item, String suffix) {
        return getSuffixBase(item, suffix, List.of("_planks", "_plank", "s_block", "_block", "s", ""));
    }

    private static @Nullable Item getStartOrMiddleBase(Item item, String substring) {
        if (!(item instanceof BlockItem)) return null;
        var key = item.keyInItemRegistry;
        var path = key.getPath();
        var base = getStartOrMiddleBasePath(path, substring);
        if (base == null) return null;
        for (var namespace : List.of(key.getNamespace(), Identifier.DEFAULT_NAMESPACE)) {
            var blockItem = BuiltInRegistries.ITEM.get(Identifier.fromNamespaceAndPath(namespace, base));
            if (blockItem.isPresent() && blockItem.get().value() instanceof BlockItem) {
                return blockItem.get().value();
            }
        }
        return null;
    }

    public static @Nullable Item getSuffixShape(Item base, String suffix) {
        return getSuffixShape(base, suffix, List.of("_planks", "_plank", "s_block", "_block", "s", ""));
    }

    public static @Nullable Item getSuffixShape(Item base, String suffix, List<String> baseSuffixes) {
        if (!(base instanceof BlockItem)) return null;
        var key = base.keyInItemRegistry;
        var path = key.getPath();
        for (var baseSuffix : baseSuffixes) {
            String pathWithoutBaseSuffix;
            if (baseSuffix.isEmpty()) {
                pathWithoutBaseSuffix = path;
            } else if (path.endsWith(baseSuffix)) {
                pathWithoutBaseSuffix = path.substring(0, path.length() - baseSuffix.length());
            } else {
                continue;
            }
            for (var namespace : List.of(key.getNamespace(), Identifier.DEFAULT_NAMESPACE)) {
                var blockItem = BuiltInRegistries.ITEM.get(Identifier.fromNamespaceAndPath(namespace, pathWithoutBaseSuffix + suffix));
                if (blockItem.isPresent() && blockItem.get().value() instanceof BlockItem) {
                    return blockItem.get().value();
                }
            }
        }
        return null;
    }

    public static @Nullable Item getPrefixShape(Item base, String prefix) {
        if (!(base instanceof BlockItem)) return null;
        var key = base.keyInItemRegistry;
        var path = key.getPath();
        for (var namespace : List.of(key.getNamespace(), Identifier.DEFAULT_NAMESPACE)) {
            var blockItem = BuiltInRegistries.ITEM.get(Identifier.fromNamespaceAndPath(namespace, prefix + path));
            if (blockItem.isPresent() && blockItem.get().value() instanceof BlockItem) {
                return blockItem.get().value();
            }
        }
        return null;
    }

}
