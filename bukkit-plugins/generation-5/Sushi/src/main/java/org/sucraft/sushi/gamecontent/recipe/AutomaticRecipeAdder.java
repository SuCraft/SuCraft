package org.sucraft.sushi.gamecontent.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
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
import org.sucraft.sushi.main.SushiPlugin;
import spout.gamecontent.builtin.recipe.MissingTransitiveStonecuttingRecipeAdder;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class AutomaticRecipeAdder {

    private static int VERSION = 1;

    public static final int STAIRS_CRAFTING_OUTPUT_COUNT = 6;

    /**
     * Blocks that have a block item with a path similar to them, but without some suffix (like "s" or "_block"),
     * which may accidentally get picked up as the base block for some shaped block.
     */
    public static final Set<Item> STORAGE_BLOCKS = Stream.of(
        Items.BAMBOO_BLOCK,
        // Items.BONE_BLOCK, // Bone is not a block
        // Items.BRICKS, // Brick is not a block
        // Items.CLAY, // Clay ball is not a block
        // Items.COAL_BLOCK, // Coal is not a block
        // Items.COPPER_BLOCK.weathering().unaffected(), // Copper is not a block
        // Items.DIAMOND_BLOCK, // Diamond is not a block
        // Items.DRIED_KELP_BLOCK, // Dried kelp is not a block
        // Items.EMERALD_BLOCK, // Emerald is not a block
        // Items.GOLD_BLOCK, // Gold ingot is not a block
        // Items.HAY_BLOCK, // Wheat is not a block
        // Items.HONEY_BLOCK, // Honey bottle or honeycomb is not a block
        // Items.IRON_BLOCK, // Iron ingot is not a block
        // Items.LAPIS_BLOCK, // Lapis lazuli is not a blcok
        // Items.NETHERITE_BLOCK, // Netherite ingot is not a block
        // Items.NETHER_BRICKS, // Nether brick is not a block
        Items.NETHER_WART_BLOCK,
        Items.REDSTONE_BLOCK,
        // Items.RESIN_BLOCK, // Resin is not a block
        // Items.SLIME_BLOCK, // Slimeball is not a block
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

    private static ShapedRecipePattern createShapedRecipePattern(final int width, final int height, final List<Optional<Ingredient>> ingredients) {
        var key = new HashMap<Character, Ingredient>(width * height);
        char nextKey = 'A';
        var pattern = new ArrayList<String>(height);
        for (int y = 0; y < height; y++) {
            var line = new StringBuilder();
            for (int x = 0; x < width; x++) {
                int i = y * width + x;
                var ingredient = ingredients.get(i);
                if (ingredient.isEmpty()) {
                    line.append(' ');
                } else {
                    line.append(nextKey);
                    key.put(nextKey, ingredient.get());
                    nextKey++;
                }
            }
            pattern.add(line.toString());
        }
        return new ShapedRecipePattern(width, height, ingredients, Optional.of(new ShapedRecipePattern.Data(key, pattern)));
    }

    private static @Nullable Item firstNotNullOf(@Nullable Item... items) {
        if (items == null) return null;
        for (var item : items) {
            if (item != null) {
                return item;
            }
        }
        return null;
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

        SushiPlugin.getPluginLogger().info("Automatically adding recipes...");

        // Check if we have a valid cache
        var keyCodec = ResourceKey.codec(Registries.RECIPE);
        MapCodec<RecipeHolder<?>> recipeHolderCodec = RecordCodecBuilder.mapCodec(instance -> instance.group(
            keyCodec.fieldOf("key").forGetter(RecipeHolder::id),
            Recipe.DIRECT_CODEC.fieldOf("recipe").forGetter(RecipeHolder::value)
        ).apply(instance, (key, recipe) -> new RecipeHolder<>(key, recipe, true)));
        var cacheInputFile = SushiPlugin.get().getDataFolder().toPath().resolve("automatic_recipe_cache/input.txt");
        var cacheOutputRemovedFile = SushiPlugin.get().getDataFolder().toPath().resolve("automatic_recipe_cache/output_removed.txt");
        var cacheOutputAddedFile = SushiPlugin.get().getDataFolder().toPath().resolve("automatic_recipe_cache/output_added.txt");
        var expectedCacheInput = Stream.concat(Stream.of("" + VERSION), BuiltInRegistries.ITEM.stream().map(item -> item.keyInItemRegistry.toString()).sorted()).toList();
        try {
            if (Files.exists(cacheInputFile) && Files.readAllLines(cacheInputFile).equals(expectedCacheInput)) {
                if (Files.exists(cacheOutputRemovedFile) && Files.exists(cacheOutputAddedFile)) {
                    // Do the same as the last time
                    MissingTransitiveStonecuttingRecipeAdder.startQueue();
                    var removedRecipeLines = Files.readAllLines(cacheOutputRemovedFile);
                    SushiPlugin.getPluginLogger().info("Removing " + removedRecipeLines.size() + " recipes based on cache...");
                    if (!removedRecipeLines.isEmpty()) {
                        removedRecipeLines.stream().map(JsonParser::parseString).map(json ->
                            keyCodec.decode(JsonOps.INSTANCE, json).getOrThrow().getFirst()
                        ).forEach(MinecraftServer.getServer().getRecipeManager().recipes::removeRecipe);
                    }
                    var addedRecipeLines = Files.readAllLines(cacheOutputAddedFile);
                    SushiPlugin.getPluginLogger().info("Adding " + addedRecipeLines.size() + " recipes based on cache...");
                    if (!addedRecipeLines.isEmpty()) {
                        addedRecipeLines.stream().map(JsonParser::parseString).map(json ->
                            recipeHolderCodec.decoder().decode(JsonOps.INSTANCE, json).getOrThrow().getFirst()
                        ).forEach(MinecraftServer.getServer().getRecipeManager().recipes::addRecipe);
                    }
                    if (!removedRecipeLines.isEmpty() || !addedRecipeLines.isEmpty()) {
                        MinecraftServer.getServer().getRecipeManager().finalizeRecipeLoading();
                        SushiPlugin.getPluginLogger().info("Flushing transitive stonecutting recipe queue...");
                        MissingTransitiveStonecuttingRecipeAdder.flushQueue();
                    }
                    SushiPlugin.getPluginLogger().info("Finished automatically adding recipes from cache");
                    return;
                }
            }
        } catch (IOException e) {
            SushiPlugin.getPluginLogger().log(Level.WARNING, "Exception while reading automatic recipe cache:", e);
        }
        // Prepare to write the cache later
        var removedRecipes = new ArrayList<ResourceKey<Recipe<?>>>();
        var addedRecipes = new ArrayList<RecipeHolder<?>>();

        // Collect ignored items
        var ignoredItems = new HashSet<>(OldReplacedBlocks.LIST.stream()
            .map(it -> BuiltInRegistries.ITEM.get(it.oldBlock()))
            .filter(Optional::isPresent).map(Optional::get).map(Holder.Reference::value).collect(Collectors.toSet()));
        // Regular deepslate is also ignored because it is obtained by smelting, while all other forms are obtained by crafting and/or stonecutting
        ignoredItems.add(Items.DEEPSLATE);

        // Get the existing recipes (by their potential input and output)
        var recipesByInputOutput = new Object2ObjectOpenHashMap<Item, Object2ObjectMap<Item, List<SimpleRecipeDescription>>>();

        class RecipeAdders {

            private void recomputeRecipesByInputByOutput() {
                recipesByInputOutput.clear();
                MinecraftServer.getServer().getRecipeManager().getRecipes().forEach(this::mapRecipeByInputOutput);
            }

            private @Nullable SimpleRecipeDescription mapRecipeByInputOutput(RecipeHolder<?> holder) {
                var description = wrap(holder);
                if (description != null) {
                    final var finalOutput = description.output;
                    description.inputs().forEach(
                        input -> recipesByInputOutput.computeIfAbsent(input, _ -> new Object2ObjectOpenHashMap<>(2))
                            .computeIfAbsent(finalOutput, _ -> new ArrayList<>(2))
                            .add(description)
                    );
                }
                return description;
            }

            private void unmapRecipeByInputOutput(SimpleRecipeDescription description) {
                final var finalOutput = description.output;
                description.inputs().forEach(input -> {
                    var recipesForInput = recipesByInputOutput.get(input);
                    if (recipesForInput != null) {
                        var recipesForOutput = recipesForInput.get(finalOutput);
                        if (recipesForOutput != null) {
                            recipesForOutput.remove(description);
                        }
                    }
                });
            }

            private @Nullable SimpleRecipeDescription getExistingRecipe(Item output, RecipeInput input, @Nullable List<SimpleRecipeDescription> recipes, Set<RecipeType<?>> types, boolean mustMatchOutput) {
                if (recipes == null) return null;
                for (var recipe : recipes) {
                    try {
                        if (types.contains(recipe.recipe.getType())) {
                            if (((Recipe) recipe.recipe).matches(input, null)) {
                                if (!mustMatchOutput || recipe.output == output) {
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
                return getExistingRecipe(output, input, recipesByInputOutput.getOrDefault(input.items().getFirst().getItem(), Object2ObjectMaps.emptyMap()).values().stream().flatMap(Collection::stream).toList(), Set.of(RecipeType.CRAFTING), false);
            }

            private @Nullable SimpleRecipeDescription getExistingSingleItemRecipe(Item output, SingleRecipeInput input, Set<RecipeType<?>> types, boolean mustMatchOutput) {
                return getExistingRecipe(output, input, recipesByInputOutput.getOrDefault(input.item().getItem(), Object2ObjectMaps.emptyMap()).get(output), types, mustMatchOutput);
            }

            private @Nullable SimpleRecipeDescription getExistingSingleItemRecipe(Item output, Item ingredient, Set<RecipeType<?>> types, boolean mustMatchOutput) {
                return this.getExistingSingleItemRecipe(output, new SingleRecipeInput(new ItemStack(ingredient)), types, mustMatchOutput);
            }

            private @Nullable SimpleRecipeDescription getExistingCraftingRecipeShaped(Item output, int width, int height, List<Item> inputs) {
                return this.getExistingCraftingRecipe(output, CraftingInput.of(width, height, inputs.stream().map(item -> item == Items.AIR ? ItemStack.EMPTY : new ItemStack(item)).toList()));
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
                removedRecipes.add(recipe.id());
            }

            private void addRecipeToServer(Identifier key, @Nullable String reason, Recipe<?> recipe) {
                var holder = new RecipeHolder<>(ResourceKey.create(Registries.RECIPE, key.withSuffix(reason != null ? "_" + reason : "")), recipe, true);
                MinecraftServer.getServer().getRecipeManager().recipes.addRecipe(holder);
                this.mapRecipeByInputOutput(holder);
                addedRecipes.add(holder);
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

            private void addShapedCraftingRecipeToServer(Identifier key, @Nullable String reason, Item output, int outputCount, int width, int height, List<Item> inputs, CraftingBookCategory category, @Nullable Function<Item, @Nullable String> group) {
                if (ignoredItems.contains(output) || inputs.stream().filter(Objects::nonNull).anyMatch(ignoredItems::contains)) {
                    return;
                }
                this.addRecipeToServer(key, reason, new ShapedRecipe(new Recipe.CommonInfo(true), new CraftingRecipe.CraftingBookInfo(category, group != null ? Optional.ofNullable(group.apply(output)).orElse("") : ""), createShapedRecipePattern(width, height, inputs.stream().map(input -> input == Items.AIR ? Optional.<Ingredient>empty() : Optional.of(Ingredient.of(input))).toList()), new ItemStackTemplate(output.builtInRegistryHolder(), outputCount, DataComponentPatch.EMPTY)));
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

            private BiConsumer<Item, Item> addShapedCrafting(int outputCount, @Nullable String reason, int width, int height, Function<Item, List<Item>> inputFunction, CraftingBookCategory category, @Nullable BiFunction<Item, Item, @Nullable String> group) {
                return (input, output) -> {
                    var inputs = inputFunction.apply(input);
                    var existingRecipe = this.getExistingCraftingRecipeShaped(output, width, height, inputs);
                    if (existingRecipe != null) {
                        if (existingRecipe.output != output || existingRecipe.resultCount >= outputCount) {
                            return;
                        }
                        this.removeRecipeFromServer(existingRecipe);
                    }
                    this.addShapedCraftingRecipeToServer(output.keyInItemRegistry, reason, output, outputCount, width, height, inputs, category, group == null ? null : actualOutput -> group.apply(input, actualOutput));
                };
            }

            private BiConsumer<Item, Item> addShapedCrafting(int outputCount, @Nullable String reason, int width, int height, Function<Item, List<Item>> inputFunction, CraftingBookCategory category) {
                return this.addShapedCrafting(outputCount, reason, width, height, inputFunction, category, null);
            }

            private BiConsumer<Item, Item> add1x2RectangleCrafting(@Nullable String reason, int outputCount, CraftingBookCategory category) {
                return this.addShapedCrafting(outputCount, reason, 1, 2, input -> List.of(input, input), category);
            }

            private BiConsumer<Item, Item> add1x3RectangleCrafting(@Nullable String reason, int outputCount, CraftingBookCategory category) {
                return this.addShapedCrafting(outputCount, reason, 1, 3, input -> List.of(input, input, input), category);
            }

            private BiConsumer<Item, Item> add2x2RectangleCrafting(@Nullable String reason, int outputCount, CraftingBookCategory category) {
                return this.addShapedCrafting(outputCount, reason, 2, 2, input -> List.of(input, input, input, input), category);
            }

            private BiConsumer<Item, Item> add3x1RectangleCrafting(@Nullable String reason, int outputCount, CraftingBookCategory category) {
                return (item1, item2) -> {
                    this.addShapedCrafting(outputCount, reason, 3, 1, input -> List.of(input, input, input), category).accept(item1, item2);
                };
            }

            private BiConsumer<Item, Item> add3x2RectangleCrafting(@Nullable String reason, int outputCount, CraftingBookCategory category) {
                return (item1, item2) -> {
                    this.addShapedCrafting(outputCount, reason, 3, 2, input -> List.of(input, input, input, input, input, input), category).accept(item1, item2);
                };
            }

            private static boolean isPlankBase(Item item) {
                return item.builtInRegistryHolder().is(ItemTags.PLANKS);
            }

            private static boolean isPlankBase(Item input, Item output) {
                return isPlankBase(input);
            }

            private static boolean isLogBase(Item item) {
                return item.builtInRegistryHolder().is(ItemTags.LOGS);
            }

            private static boolean isLogBase(Item input, Item output) {
                return isLogBase(input);
            }

            private static boolean isWoodenBase(Item item) {
                return isPlankBase(item) || isLogBase(item);
            }

            private static boolean isWoodenBase(Item input, Item output) {
                return isWoodenBase(input);
            }

            private BiConsumer<Item, Item> addStairsCrafting() {
                return this.addShapedCrafting(STAIRS_CRAFTING_OUTPUT_COUNT, null, 3, 3, input -> List.of(input, Items.AIR, Items.AIR, input, input, Items.AIR, input, input, input), CraftingBookCategory.BUILDING, (input, _) -> isPlankBase(input) ? "wooden_stairs" : isLogBase(input) ? "log_stairs" : null);
            }

            private BiConsumer<Item, Item> addSlabCrafting() {
                return this.addShapedCrafting(6, null, 3, 1, input -> List.of(input, input, input), CraftingBookCategory.BUILDING, (input, _) -> isPlankBase(input) ? "wooden_slab" : isLogBase(input) ? "log_slab" : null);
            }

            private BiConsumer<Item, Item> addWoodenFenceCrafting() {
                return conditional((input, _) -> isWoodenBase(input), this.addShapedCrafting(3, null, 3, 2, input -> List.of(input, Items.STICK, input, input, Items.STICK, input), CraftingBookCategory.MISC, (input, _) -> isPlankBase(input) ? "wooden_fence" : isLogBase(input) ? "log_fence" : null));
            }

            private BiConsumer<Item, Item> addNetherBrickFenceCrafting() {
                return conditional(RecipeAdders::isNetherBrickFence, this.addShapedCrafting(6, null, 3, 2, input -> List.of(input, Items.NETHER_BRICK, input, input, Items.NETHER_BRICK, input), CraftingBookCategory.MISC));
            }

            public static boolean isNetherBrickFence(Item item) {
                return item == Items.NETHER_BRICKS || item == Items.RED_NETHER_BRICKS;
            }

            public static boolean isNetherBrickFence(Item input, Item output) {
                return isNetherBrickFence(input);
            }

            public static boolean isNonWoodenNewFence(Item item) {
                return !isNetherBrickFence(item) && !isWoodenBase(item);
            }

            public static boolean isNonWoodenNewFence(Item input, Item output) {
                return isNonWoodenNewFence(input);
            }

            private BiConsumer<Item, Item> addNewNonWoodenFenceStonecutting() {
                return conditional(RecipeAdders::isNonWoodenNewFence, this.addStonecutting(1, null));
            }

            private BiConsumer<Item, Item> addReverseNewNonWoodenFenceStonecutting() {
                return conditional(RecipeAdders::isNonWoodenNewFence, invert(this.addStonecutting(1, null)));
            }

            private BiConsumer<Item, Item> addFenceGateCrafting() {
                return conditional((input, _) -> isWoodenBase(input), this.addShapedCrafting(1, null, 3, 2, input -> List.of(Items.STICK, input, Items.STICK, Items.STICK, input, Items.STICK), CraftingBookCategory.REDSTONE, (input, _) -> isPlankBase(input) ? "wooden_fence_gate" : isLogBase(input) ? "log_fence_gate" : null));
            }

            private BiConsumer<Item, Item> addSignCrafting() {
                return this.addShapedCrafting(3, null, 3, 3, input -> List.of(input, input, input, input, input, input, Items.AIR, Items.STICK, Items.AIR), CraftingBookCategory.MISC, (input, _) -> isPlankBase(input) ? "wooden_sign" : isLogBase(input) ? "log_sign" : null);
            }

            private BiConsumer<Item, Item> addButtonCrafting() {
                return this.addShapelessCrafting(1, null, input -> List.of(input), CraftingBookCategory.REDSTONE, input -> isPlankBase(input) ? "wooden_button" : isLogBase(input) ? "log_button" : null);
            }

            private BiConsumer<Item, Item> addDoorCrafting() {
                return this.addShapedCrafting(3, null, 2, 3, input -> List.of(input, input, input, input, input, input), CraftingBookCategory.REDSTONE, (input, _) -> isPlankBase(input) ? "wooden_door" : isLogBase(input) ? "log_door" : null);
            }

            private BiConsumer<Item, Item> addPressurePlateCrafting() {
                return this.addShapedCrafting(1, null, 2, 1, input -> List.of(input, input), CraftingBookCategory.REDSTONE, (input, _) -> isPlankBase(input) ? "wooden_pressure_plate" : isLogBase(input) ? "log_pressure_plate" : null);
            }

            private BiConsumer<Item, Item> addTrapdoorCrafting() {
                return this.addShapedCrafting(2, null, 3, 2, input -> List.of(input, input, input, input, input, input), CraftingBookCategory.REDSTONE, (input, _) -> isPlankBase(input) ? "wooden_trapdoor" : isLogBase(input) ? "log_trapdoor" : null);
            }

            private BiConsumer<Item, Item> addStonecutting(int outputCount, @Nullable String reason) {
                return (input, output) -> {
                    var existingRecipe = this.getExistingSingleItemRecipe(output, input, Set.of(RecipeType.STONECUTTING), true);
                    if (existingRecipe != null) {
                        if (existingRecipe.output != output || existingRecipe.resultCount >= outputCount) {
                            return;
                        }
                        this.removeRecipeFromServer(existingRecipe);
                    }
                    this.addStonecuttingRecipeToServer(Identifier.fromNamespaceAndPath("sucraft", input.keyInItemRegistry.getNamespace() + "_" + input.keyInItemRegistry.getPath() + "_to_" + output.keyInItemRegistry.getNamespace() + "_" + output.keyInItemRegistry.getPath()), reason, output, outputCount, input);
                };
            }

            private BiConsumer<Item, Item> addStonecutting(int outputCount) {
                return this.addStonecutting(outputCount, null);
            }

            private BiConsumer<Item, Item> addSmelting(float experience, int cookingTime, @Nullable String reason) {
                return (input, output) -> {
                    var existingRecipe = this.getExistingSingleItemRecipe(output, input, Set.of(RecipeType.SMELTING), false);
                    if (existingRecipe != null) {
                        if (existingRecipe.output != output || existingRecipe.resultCount >= 1) {
                            return;
                        }
                        this.removeRecipeFromServer(existingRecipe);
                    }
                    this.addSmeltingRecipeToServer(Identifier.fromNamespaceAndPath("sucraft", input.keyInItemRegistry.getNamespace() + "_" + input.keyInItemRegistry.getPath() + "_to_" + output.keyInItemRegistry.getNamespace() + "_" + output.keyInItemRegistry.getPath()), reason, output, input, CookingBookCategory.BLOCKS, null, experience, cookingTime);
                };
            }

            private BiConsumer<Item, Item> addSmelting(float experience, int cookingTime) {
                return this.addSmelting(experience, cookingTime, null);
            }

        }
        var adders = new RecipeAdders();

        adders.recomputeRecipesByInputByOutput();

        // Start a queue of transitive recipe additions
        MissingTransitiveStonecuttingRecipeAdder.startQueue();

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
                conditional((input, _) -> filterOut(getPrefixShape(input, "chiseled_"), Set.of(Items.CHISELED_BOOKSHELF)) == null, invert(adders.add1x2RectangleCrafting("from_slabs", 1, CraftingBookCategory.BUILDING)))
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
                getSuffixBase(item, "_fence"),
                adders.addNewNonWoodenFenceStonecutting(),
                adders.addReverseNewNonWoodenFenceStonecutting()
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
                getSuffixBase(item, "_spiral_meander"),
                adders.addStonecutting(1),
                invert(adders.addStonecutting(1, "from_spiral_meander"))
            );
            adders.addRecipes(
                item,
                filterOut(getSuffixBase(item, "_fence"), STORAGE_BLOCKS),
                adders.addWoodenFenceCrafting(),
                adders.addNetherBrickFenceCrafting()
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
                conditional(RecipeAdders::isLogBase, adders.addStonecutting(1))
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
                firstNotNullOf(getSuffixBase(item, "_tiles"), getMiddleOrEndBase(item, "_tile")),
                adders.addStonecutting(1),
                invert(adders.addStonecutting(1, "from_tiles"))
            );
            adders.addRecipes(
                item,
                firstNotNullOf(getSuffixBase(item, "_diagonal_tiles"), getMiddleOrEndBase(item, "_diagonal_tile")),
                adders.addStonecutting(1),
                invert(adders.addStonecutting(1, "from_diagonal_tiles"))
            );
            adders.addRecipes(
                item,
                firstNotNullOf(getSuffixBase(item, "_scales"), getMiddleOrEndBase(item, "_scale")),
                adders.addStonecutting(1),
                invert(adders.addStonecutting(1, "from_scales"))
            );
            adders.addRecipes(
                item,
                firstNotNullOf(getSuffixBase(item, "_small_bricks"), getMiddleOrEndBase(item, "_small_brick")),
                adders.addStonecutting(1),
                invert(adders.addStonecutting(1, "from_small_bricks"))
            );
            adders.addRecipes(
                item,
                firstNotNullOf(getSuffixBase(item, "_bricks"), getMiddleOrEndBase(item, "_brick")),
                (input, output) -> {
                    Item polished = getPrefixShape(getSpecialNamespaceIfPresent(input, output), input, "polished_");
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
            @Nullable Item withoutChiseled = filterOut(getStartOrMiddleBase(item, "chiseled_"), Set.of(Items.BOOKSHELF));
            adders.addRecipes(
                item,
                withoutChiseled,
                adders.addStonecutting(1),
                invert(adders.addStonecutting(1, "from_chiseled"))
            );
            adders.addRecipes(
                item,
                getStartOrMiddleBase(item, "engraved_"),
                adders.addStonecutting(1),
                invert(adders.addStonecutting(1, "from_engraved"))
            );
            Stream.of("elder", "ender", "shulker", "warden", "ghastly").forEach(chiselType -> {
                adders.addRecipes(
                    item,
                    getStartOrMiddleBase(item, chiselType + "_chiseled_"),
                    adders.addStonecutting(1),
                    invert(adders.addStonecutting(1, "from_" + chiselType + "_chiseled"))
                );
            });
            adders.addRecipes(
                item,
                getStartOrMiddleBase(item, "recessed_"),
                adders.addStonecutting(1),
                invert(adders.addStonecutting(1, "from_recessed"))
            );
            adders.addRecipes(
                item,
                getStartOrMiddleBase(item, "sculpted_"),
                adders.addStonecutting(1),
                invert(adders.addStonecutting(1, "from_sculpted"))
            );
            adders.addRecipes(
                item,
                getStartOrMiddleBase(item, "draconic_"),
                adders.addStonecutting(1),
                invert(adders.addStonecutting(1, "from_draconic"))
            );
            adders.addRecipes(
                item,
                getSuffixShape(getSpecialNamespaceIfPresent(item, withoutChiseled), withoutChiseled, "_slab"),
                adders.add1x2RectangleCrafting("from_unchiseled_slabs", 1, CraftingBookCategory.BUILDING)
            );
            adders.addRecipes(
                item,
                getStartOrMiddleBase(item, "rough_"),
                adders.addStonecutting(1, "from_non_rough"),
                invert(adders.addStonecutting(1, "from_rough"))
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
                getSuffixBase(item, "_stem", List.of("_hyphae")),
                adders.addStonecutting(1, "from_hyphae")
            );
            @Nullable Item log = getSuffixBase(item, "_planks", List.of("_log", "_stem"));
            adders.addRecipes(
                item,
                log,
                adders.addStonecutting(4, "from_log")
            );
            adders.addRecipes(
                item,
                getPrefixShape(getSpecialNamespaceIfPresent(item, log), log, "stripped_"),
                adders.addStonecutting(4, "from_stripped_log")
            );
            adders.addRecipes(
                item,
                getReplaced(item, "sandstone", "sand"),
                adders.add2x2RectangleCrafting("from_sand", 1, CraftingBookCategory.BUILDING)
            );
        });

        // Flush the queued transitive recipe additions
        MinecraftServer.getServer().getRecipeManager().finalizeRecipeLoading();
        MissingTransitiveStonecuttingRecipeAdder.flushQueue();

        // Write the cache
        try {
            SushiPlugin.getPluginLogger().info("Writing automatic recipes to cache...");
            cacheInputFile.getParent().toFile().mkdirs();
            Files.write(cacheInputFile, expectedCacheInput);
            cacheOutputRemovedFile.getParent().toFile().mkdirs();
            Files.write(cacheOutputRemovedFile, removedRecipes.stream().map(removed -> keyCodec.encodeStart(JsonOps.INSTANCE, removed).getOrThrow()).map(JsonElement::toString).toList());
            cacheOutputAddedFile.getParent().toFile().mkdirs();
            Files.write(cacheOutputAddedFile, addedRecipes.stream().map(added -> recipeHolderCodec.encoder().encodeStart(JsonOps.INSTANCE, added).getOrThrow()).map(JsonElement::toString).toList());
        } catch (IOException e) {
            SushiPlugin.getPluginLogger().log(Level.WARNING, "Exception while writing automatic recipe cache:", e);
        }

    }

    private static @Nullable BlockItem getBlockItemByPath(String originalNamespace, String path) {
        for (var namespace : List.of(originalNamespace, Identifier.DEFAULT_NAMESPACE)) {
            var item = BuiltInRegistries.ITEM.get(Identifier.fromNamespaceAndPath(namespace, path));
            if (item.isPresent() && item.get().value() instanceof BlockItem blockItem) {
                return blockItem;
            }
        }
        return (BlockItem) BuiltInRegistries.ITEM.stream().filter(item -> item.keyInItemRegistry.getPath().equals(path))
            .filter(item -> item instanceof BlockItem).findFirst().orElse(null);
    }

    private static @Nullable String getReplacedPath(String path, String from, String to) {
        if (!path.contains(from)) return null;
        return path.replaceFirst(from, to);
    }

    private static @Nullable Item getReplaced(Item item, String from, String to) {
        if (!(item instanceof BlockItem)) return null;
        var key = item.keyInItemRegistry;
        var path = key.getPath();
        var replacedPath = getReplacedPath(path, from, to);
        if (replacedPath == null) return null;
        return getBlockItemByPath(key.getNamespace(), replacedPath);
    }

    private static @Nullable String getSuffixBasePath(String path, String suffix) {
        if (!path.endsWith(suffix)) return null;
        String base = path.substring(0, path.length() - suffix.length());
        if (base.contains("mud") && !base.contains("mud_brick") && path.contains("mud_brick")) {
            base = base.replace("mud", "packed_mud");
        }
        return base;
    }

    private static @Nullable String getStartOrMiddleBasePath(String path, String substring) {
        int substringIndex = path.indexOf(substring);
        if (substringIndex == -1) return null;
        String base = (substringIndex == 0 ? "" : path.substring(0, substringIndex)) + path.substring(substringIndex + substring.length());
        if (base.contains("mud") && !base.contains("mud_brick") && path.contains("mud_brick")) {
            base = base.replace("mud", "packed_mud");
        }
        return base;
    }

    private static @Nullable String getMiddleOrEndBasePath(String path, String substring) {
        int substringIndex = path.indexOf(substring);
        if (substringIndex == -1) return null;
        String base = path.substring(0, substringIndex) + (substringIndex + substring.length() == path.length() ? "" : path.substring(substringIndex + substring.length()));
        if (base.contains("mud") && !base.contains("mud_brick") && path.contains("mud_brick")) {
            base = base.replace("mud", "packed_mud");
        }
        return base;
    }

    private static @Nullable Item getSuffixBase(Item item, String suffix) {
        return getSuffixBase(item, suffix, List.of("_planks", "_plank", "s_block", "_block", "s", ""));
    }

    private static @Nullable Item getSuffixBase(Item item, String suffix, List<String> baseSuffixes) {
        if (!(item instanceof BlockItem)) return null;
        var key = item.keyInItemRegistry;
        var path = key.getPath();
        var base = getSuffixBasePath(path, suffix);
        if (base == null) return null;
        List<String> basePrefixes = List.of("", "oak_");
        return basePrefixes.stream().flatMap(basePrefix -> {
            String baseWithoutPrefix;
            if (basePrefix.isEmpty()) {
                baseWithoutPrefix = base;
            } else if (base.startsWith(basePrefix)) {
                baseWithoutPrefix = base.substring(basePrefix.length());
            } else {
                return Stream.empty();
            }
            return baseSuffixes.stream().map(baseSuffix -> getBlockItemByPath(key.getNamespace(), baseWithoutPrefix + baseSuffix));
        }).filter(Objects::nonNull).findFirst().orElse(null);
    }

    private static @Nullable Item getStartOrMiddleBase(Item item, String substring) {
        return getStartOrMiddleBase(item, substring, List.of("_planks", "_plank", "s_block", "_block", "s", ""));
    }

    private static @Nullable Item getStartOrMiddleBase(Item item, String substring, List<String> baseSuffixes) {
        if (!(item instanceof BlockItem)) return null;
        var key = item.keyInItemRegistry;
        var path = key.getPath();
        var base = getStartOrMiddleBasePath(path, substring);
        if (base == null) return null;
        return baseSuffixes.stream().map(baseSuffix -> getBlockItemByPath(key.getNamespace(), base + baseSuffix))
            .filter(Objects::nonNull).findFirst().orElse(null);
    }

    private static @Nullable Item getMiddleOrEndBase(Item item, String substring) {
        return getMiddleOrEndBase(item, substring, List.of("_planks", "_plank", "s_block", "_block", "s", ""));
    }

    private static @Nullable Item getMiddleOrEndBase(Item item, String substring, List<String> baseSuffixes) {
        if (!(item instanceof BlockItem)) return null;
        var key = item.keyInItemRegistry;
        var path = key.getPath();
        var base = getMiddleOrEndBasePath(path, substring);
        if (base == null) return null;
        return baseSuffixes.stream().map(baseSuffix -> getBlockItemByPath(key.getNamespace(), base + baseSuffix))
            .filter(Objects::nonNull).findFirst().orElse(null);
    }

    public static @Nullable Item getSuffixShape(Item base, String suffix) {
        return getSuffixShape(null, base, suffix);
    }

    public static @Nullable Item getSuffixShape(@Nullable String preferredNamespace, Item base, String suffix) {
        return getSuffixShape(preferredNamespace, base, suffix, List.of("_planks", "_plank", "s_block", "_block", "s", ""));
    }

    public static @Nullable Item getSuffixShape(Item base, String suffix, List<String> baseSuffixes) {
        return getSuffixShape(null, base, suffix, baseSuffixes);
    }

    public static @Nullable Item getSuffixShape(String preferredNamespace, Item base, String suffix, List<String> baseSuffixes) {
        if (!(base instanceof BlockItem)) return null;
        var key = base.keyInItemRegistry;
        var unfinalPath = key.getPath();
        if (suffix.contains("brick")) {
            if (unfinalPath.contains("packed_mud")) {
                unfinalPath = unfinalPath.replace("packed_mud", "mud");
            } else if (unfinalPath.contains("mud")) {
                return null;
            }
        }
        var path = unfinalPath;
        List<String> basePrefixes = List.of("", "oak_");
        return basePrefixes.stream().flatMap(basePrefix -> baseSuffixes.stream().map(baseSuffix -> {
            String pathWithoutBaseSuffix;
            if (baseSuffix.isEmpty()) {
                pathWithoutBaseSuffix = path;
            } else if (path.endsWith(baseSuffix)) {
                pathWithoutBaseSuffix = path.substring(0, path.length() - baseSuffix.length());
            } else {
                return null;
            }
            String pathWithBasePrefixWithoutBaseSuffix = basePrefix + pathWithoutBaseSuffix;
            return getBlockItemByPath(preferredNamespace != null ? preferredNamespace : key.getNamespace(), pathWithBasePrefixWithoutBaseSuffix + suffix);
        })).filter(Objects::nonNull).findFirst().orElse(null);
    }

    public static @Nullable Item getPrefixShape(Item base, String prefix) {
        return getPrefixShape(null, base, prefix);
    }

    public static @Nullable Item getPrefixShape(@Nullable String preferredNamespace, Item base, String prefix) {
        return getPrefixShape(preferredNamespace, base, prefix, List.of("_planks", "_plank", "s_block", "_block", "s", ""));
    }

    public static @Nullable Item getPrefixShape(Item base, String prefix, List<String> baseSuffixes) {
        return getPrefixShape(null, base, prefix, baseSuffixes);
    }

    public static @Nullable Item getPrefixShape(@Nullable String preferredNamespace, Item base, String prefix, List<String> baseSuffixes) {
        if (!(base instanceof BlockItem)) return null;
        var key = base.keyInItemRegistry;
        var path = key.getPath();
        return baseSuffixes.stream().map(baseSuffix -> {
            String pathWithoutBaseSuffix;
            if (baseSuffix.isEmpty()) {
                pathWithoutBaseSuffix = path;
            } else if (path.endsWith(baseSuffix)) {
                pathWithoutBaseSuffix = path.substring(0, path.length() - baseSuffix.length());
            } else {
                return null;
            }
            return getBlockItemByPath(preferredNamespace != null ? preferredNamespace : key.getNamespace(), prefix + pathWithoutBaseSuffix);
        }).filter(Objects::nonNull).findFirst().orElse(null);
    }

    public static @Nullable String getSpecialNamespaceIfPresent(@Nullable Item... items) {
        if (items == null) {
            return null;
        }
        for (Item item : items) {
            if (item != null) {
                String namespace = item.keyInItemRegistry.getNamespace();
                if (!namespace.equals(Identifier.DEFAULT_NAMESPACE)) {
                    return namespace;
                }
            }
        }
        return null;
    }

}
