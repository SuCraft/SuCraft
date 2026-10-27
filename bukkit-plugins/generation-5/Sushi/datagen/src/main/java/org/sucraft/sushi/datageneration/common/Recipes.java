package org.sucraft.sushi.datageneration.common;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class Recipes {

    private Recipes() {
        throw new UnsupportedOperationException();
    }

    public static abstract class OpenProvider extends FabricRecipeProvider {

        public OpenProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        public abstract RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements);

    }

    public static class CollectedFactory extends org.sucraft.sushi.datageneration.common.CollectedFactory<OpenProvider, FabricDataGenerator.Pack.RegistryDependentFactory<OpenProvider>> {

        @Override
        public OpenProvider createProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            var fabricOutput = output;
            return new OpenProvider(output, registriesFuture) {

                @Override
                public RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
                    return new RecipeProvider(recipes, advancements) {

                        @Override
                        public void buildRecipes() {
                            for (var sub : CollectedFactory.this.subs) {
                                sub.create(fabricOutput, registriesFuture).createRecipeProvider(registries, recipes, advancements).buildRecipes();
                            }
                        }

                    };
                }

                @Override
                protected Identifier getRecipeIdentifier(Identifier identifier) {
                    return identifier;
                }

            };
        }

    }

    @FunctionalInterface
    public interface RecipeSupplier {

        @Nullable RecipeBuilder build();

        default Identifier id() {
            return this.id(this.build());
        }

        default Identifier id(RecipeBuilder builder) {
            return builder.defaultId().identifier();
        }

        default RecipeSupplier map(Function<@Nullable RecipeBuilder, @Nullable RecipeBuilder> function) {
            var original = this;
            return withId(() -> function.apply(original.build()), builder -> original.id(builder));
        }

        static RecipeSupplier withId(Supplier<@Nullable RecipeBuilder> build, Identifier id) {
            return withId(build, _ -> id);
        }

        static RecipeSupplier withId(Supplier<@Nullable RecipeBuilder> build, Function<RecipeBuilder, Identifier> id) {
            return new RecipeSupplier() {

                @Override
                public @Nullable RecipeBuilder build() {
                    return build.get();
                }

                @Override
                public Identifier id(RecipeBuilder builder) {
                    return id.apply(builder);
                }

            };
        }

        static RecipeSupplier withPath(Supplier<@Nullable RecipeBuilder> build, String path) {
            return withPath(build, () -> path);
        }

        static RecipeSupplier withPath(Supplier<@Nullable RecipeBuilder> build, Supplier<String> path) {
            return new RecipeSupplier() {

                @Override
                public @Nullable RecipeBuilder build() {
                    return build.get();
                }

                @Override
                public Identifier id(RecipeBuilder builder) {
                    return RecipeSupplier.super.id(builder).withPath(path.get());
                }

            };
        }

        static RecipeSupplier withSuffix(Supplier<@Nullable RecipeBuilder> build, String suffix) {
            return withSuffix(build, () -> suffix);
        }

        static RecipeSupplier withSuffix(Supplier<@Nullable RecipeBuilder> build, Supplier<String> suffix) {
            return new RecipeSupplier() {

                @Override
                public @Nullable RecipeBuilder build() {
                    return build.get();
                }

                @Override
                public Identifier id(RecipeBuilder builder) {
                    return RecipeSupplier.super.id(builder).withSuffix(suffix.get());
                }

            };
        }

    }

    public static class ProviderFunction implements FabricDataGenerator.Pack.RegistryDependentFactory<OpenProvider> {

        // Inputs

        public ItemLike result;
        public @Nullable Integer resultCount = null;
        public boolean unlockedByResult = true;
        public List<Ingredient> ingredients;
        public Predicate<Ingredient> unlockedByIngredientPredicate = _ -> true;
        public @Nullable RecipeCategory category = null;
        public @Nullable Boolean reverse = null;

        // Actions

        public Runnable buildRecipesAction = this::buildSuppliedRecipes;
        public ArrayList<RecipeSupplier> recipeSuppliers = new ArrayList<>(6);

        // Context

        public RecipeOutput output;
        public RecipeProvider provider;
        public HolderLookup.Provider registries;
        public BootstrapContext<Recipe<?>> recipes;
        public BootstrapContext<Advancement> advancements;

        // Constructor

        public ProviderFunction() {
        }

        public ProviderFunction(ItemLike result) {
            this();
            this.result = result;
        }

        // Getters

        public int getResultCountOrElse(int fallback) {
            return this.resultCount != null ? this.resultCount : fallback;
        }

        public Ingredient getFirstIngredient() {
            return this.ingredients.getFirst();
        }

        public ItemLike getFirstIngredientItem() {
            return this.getFirstIngredient().getSingleItem().orElseThrow().value();
        }

        public RecipeCategory getCategoryOrElse(RecipeCategory fallback) {
            return this.category != null ? this.category : fallback;
        }

        public boolean getReverseOrElse(boolean fallback) {
            return this.reverse != null ? this.reverse : fallback;
        }

        // Setters

        public ProviderFunction withThis(Consumer<ProviderFunction> consumer) {
            consumer.accept(this);
            return this;
        }

        public ProviderFunction setResult(ItemLike result) {
            this.result = result;
            return this;
        }

        public ProviderFunction setResultCount(int resultCount) {
            this.resultCount = resultCount;
            return this;
        }

        public ProviderFunction setUnlockedByResult(boolean unlockedByResult) {
            this.unlockedByResult = unlockedByResult;
            return this;
        }

        public ProviderFunction setIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public ProviderFunction setIngredientsFromItems(List<ItemLike> ingredients) {
            return this.setIngredients(ingredients.stream().map(Ingredient::of).toList());
        }

        public ProviderFunction setIngredientsFromItemStreams(List<Stream<ItemLike>> ingredients) {
            return this.setIngredients(ingredients.stream().map(Ingredient::of).toList());
        }

        public ProviderFunction setIngredientsFromTags(List<HolderSet<Item>> ingredients) {
            return this.setIngredients(ingredients.stream().map(Ingredient::of).toList());
        }

        public ProviderFunction setIngredient(Ingredient ingredient) {
            return this.setIngredients(List.of(ingredient));
        }

        public ProviderFunction setIngredientFromItem(ItemLike ingredient) {
            return this.setIngredient(Ingredient.of(ingredient));
        }

        public ProviderFunction setIngredientFromItemStream(Stream<ItemLike> ingredient) {
            return this.setIngredient(Ingredient.of(ingredient));
        }

        public ProviderFunction setIngredientFromTag(HolderSet<Item> ingredient) {
            return this.setIngredient(Ingredient.of(ingredient));
        }

        public ProviderFunction setUnlockedByIngredientPredicate(Predicate<Ingredient> unlockedByIngredientPredicate) {
            this.unlockedByIngredientPredicate = unlockedByIngredientPredicate;
            return this;
        }

        public ProviderFunction setUnlockedByIngredients(boolean unlockedByIngredients) {
            return this.setUnlockedByIngredientPredicate(_ -> true);
        }

        public ProviderFunction setCategory(RecipeCategory category) {
            this.category = category;
            return this;
        }

        public ProviderFunction setBuildRecipesAction(Runnable buildRecipesAction) {
            this.buildRecipesAction = buildRecipesAction;
            return this;
        }

        public ProviderFunction setRecipeSuppliers(List<RecipeSupplier> recipeSuppliers) {
            this.recipeSuppliers = new ArrayList<>(recipeSuppliers);
            return this;
        }

        public ProviderFunction setRecipeSupplier(RecipeSupplier recipeSupplier) {
            return this.setRecipeSuppliers(List.of(recipeSupplier));
        }

        public ProviderFunction addRecipeSupplier(RecipeSupplier recipeSupplier, boolean appendFinalizers) {
            this.recipeSuppliers.add(appendFinalizers ? this.appendFinalizers(recipeSupplier) : recipeSupplier);
            return this;
        }

        public ProviderFunction addRecipeSupplier(RecipeSupplier recipeSupplier) {
            return this.addRecipeSupplier(recipeSupplier, true);
        }

        public ProviderFunction addRecipeSupplier(Function<ProviderFunction, RecipeSupplier> function, boolean appendFinalizers) {
            return this.addRecipeSupplier(function.apply(this), appendFinalizers);
        }

        public ProviderFunction addRecipeSupplier(Function<ProviderFunction, RecipeSupplier> function) {
            return this.addRecipeSupplier(function, true);
        }

        public ProviderFunction addAllStairs() {
            this.addRecipeSupplier(this.createStairs());
            this.addRecipeSupplier(this.createStairsStonecutting());
            this.addRecipeSupplier(this.createStairsReverseConditional());
            this.addRecipeSupplier(this.createStairsStonecuttingReverseConditional());
            return this;
        }

        public ProviderFunction addAllStairs(ItemLike stairs, ItemLike fullBlock) {
            this.setIngredientFromItem(fullBlock);
            this.setResult(stairs);
            return this.addAllStairs();
        }

        public ProviderFunction addAllSlabs() {
            this.addRecipeSupplier(this.createSlab());
            this.addRecipeSupplier(this.createSlabStonecutting());
            this.addRecipeSupplier(this.createSlabReverseConditional());
            return this;
        }

        public ProviderFunction addAllSlabs(ItemLike slab, ItemLike fullBlock) {
            this.setIngredientFromItem(fullBlock);
            this.setResult(slab);
            return this.addAllSlabs();
        }

        public ProviderFunction addStonecutting() {
            return this.addRecipeSupplier(this.createStonecutting());
        }

        public ProviderFunction addStonecutting(ItemLike result, ItemLike ingredient) {
            this.setIngredientFromItem(ingredient);
            this.setResult(result);
            return this.addStonecutting();
        }

        public ProviderFunction addStonecutting(ItemLike result, ItemLike ingredient, int count) {
            this.setResultCount(count);
            return this.addStonecutting(result, ingredient);
        }

        // Default implementations

        public void buildSuppliedRecipes() {
            for (var recipeSupplier : this.recipeSuppliers) {
                var builder = recipeSupplier.build();
                if (builder == null) continue;
                var id = recipeSupplier.id(builder);
                builder.save(this.output, ResourceKey.create(Registries.RECIPE, id));
            }
        }

        public RecipeSupplier appendFinalizers(RecipeSupplier recipeSupplier) {
            var result = appendMandatoryUnlockFinalizer(recipeSupplier);
            result = appendUnlockedByResultFinalizer(result);
            result = appendUnlockedByIngredientsFinalizer(result);
            return result;
        }

        public RecipeSupplier appendMandatoryUnlockFinalizer(RecipeSupplier recipeSupplier) {
            return recipeSupplier.map(builder -> {
                builder.unlockedBy("mandatory", new Criterion<>(CriteriaTriggers.TICK, new PlayerTrigger.TriggerInstance(Optional.empty())));
                return builder;
            });
        }

        public RecipeSupplier appendUnlockedByResultFinalizer(RecipeSupplier recipeSupplier) {
            return recipeSupplier; // Replaced by AutomaticRecipeAdvancementAdder in Sushi
            /*
            return recipeSupplier.map(builder -> {
                if (this.unlockedByResult) {
                    ItemLike result = null;
                    if (builder instanceof ShapedRecipeBuilder shapedBuilder) {
                        result = shapedBuilder.result.item().value();
                    } else if (builder instanceof ShapelessRecipeBuilder shapelessBuilder) {
                        result = shapelessBuilder.result.item().value();
                    } else if (builder instanceof SingleItemRecipeBuilder singleItemBuilder) {
                        result = singleItemBuilder.result.item().value();
                    }
                    if (result != null) {
                        builder.unlockedBy(
                            "has_result",
                            RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(this.registries.lookupOrThrow(Registries.ITEM), result))
                        );
                    }
                }
                return builder;
            });
            */
        }

        public RecipeSupplier appendUnlockedByIngredientsFinalizer(RecipeSupplier recipeSupplier) {
            return recipeSupplier; // Replaced by AutomaticRecipeAdvancementAdder in Sushi
            // return recipeSupplier.map(builder -> {
            //     List<Ingredient> ingredients = null;
            //     if (builder instanceof ShapedRecipeBuilder shapedBuilder) {
            //         ingredients = new ArrayList<>(shapedBuilder.key.values());
            //     } else if (builder instanceof ShapelessRecipeBuilder shapelessBuilder) {
            //         ingredients = shapelessBuilder.ingredients;
            //     } else if (builder instanceof SingleItemRecipeBuilder singleItemBuilder) {
            //         ingredients = List.of(singleItemBuilder.ingredient);
            //     }
            //     if (ingredients != null) {
            //         int[] actuallyAddedIngredientI = {0};
            //         var itemsAlreadyAdded = new HashSet<Item>();
            //         var tagsAlreadyAdded = new HashSet<TagKey<Item>>();
            //         for (int i = 0; i < ingredients.size(); i++) {
            //             var ingredient = ingredients.get(i);
            //             boolean sameIngredientAlreadyChecked = false;
            //             for (int j = 0; j < i; j++) {
            //                 if (ingredients.get(j).equals(ingredient)) {
            //                     sameIngredientAlreadyChecked = true;
            //                     break;
            //                 }
            //             }
            //             if (sameIngredientAlreadyChecked) continue;
            //             if (this.unlockedByIngredientPredicate.test(ingredient)) {
            //                 var unwrapped = ingredient.values.unwrap();
            //                 unwrapped.ifLeft(tag -> {
            //                     if (tagsAlreadyAdded.add(tag)) {
            //                         int ingredientI = ++actuallyAddedIngredientI[0];
            //                         builder.unlockedBy(
            //                             "has_ingredient_" + ingredientI,
            //                             RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(this.registries.lookupOrThrow(Registries.ITEM), tag))
            //                         );
            //                     }
            //                 });
            //                 unwrapped.ifRight(items -> items.forEach(item -> {
            //                     if (itemsAlreadyAdded.add(item.value())) {
            //                         int ingredientI = ++actuallyAddedIngredientI[0];
            //                         builder.unlockedBy(
            //                             "has_ingredient_" + ingredientI,
            //                             RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(this.registries.lookupOrThrow(Registries.ITEM), item.value()))
            //                         );
            //                     }
            //                 }));
            //             }
            //         }
            //     }
            //     return builder;
            // });
        }

        public RecipeSupplier createReverseConditional(RecipeSupplier reverse) {
            return reverse.map(builder -> this.getReverseOrElse(true) ? builder : null);
        }

        public RecipeSupplier createStairs() {
            return () -> this.provider.shaped(RecipeCategory.BUILDING_BLOCKS, this.result, this.getResultCountOrElse(6)).define('#', this.getFirstIngredient()).pattern("#  ").pattern("## ").pattern("###");
        }

        public RecipeSupplier createStairsStonecutting() {
            return this.createStonecutting();
        }

        public RecipeSupplier createStairsReverse() {
            // Disabled because this is done by AddStairsReverseRecipes
            return () -> null;
            // return RecipeSupplier.withId(() -> this.provider.shapeless(this.getCategoryOrElse(RecipeCategory.BUILDING_BLOCKS), this.getFirstIngredientItem()).requires(this.result), _ -> this.createStairs().id().withSuffix("_reverse"));
        }

        public RecipeSupplier createStairsReverseConditional() {
            return this.createReverseConditional(this.createStairsReverse());
        }

        public RecipeSupplier createStairsStonecuttingReverse() {
            return RecipeSupplier.withId(() -> SingleItemRecipeBuilder.stonecutting(Ingredient.of(this.result), this.getCategoryOrElse(RecipeCategory.BUILDING_BLOCKS), this.getFirstIngredientItem(), this.getResultCountOrElse(1)), builder -> this.createStairsStonecutting().id().withSuffix("_reverse"));
        }

        public RecipeSupplier createStairsStonecuttingReverseConditional() {
            // Disabled because this is done by AddStairsReverseRecipes
            return () -> null;
            // return this.createReverseConditional(this.createStairsStonecuttingReverse());
        }

        public RecipeSupplier createSlab() {
            return () -> this.provider.slabBuilder(RecipeCategory.BUILDING_BLOCKS, this.result, this.getFirstIngredient());
        }

        public RecipeSupplier createSlabStonecutting() {
            return this.createStonecutting(2);
        }

        public RecipeSupplier createSlabReverse() {
            // Disabled because this is done by AddSlabReverseRecipes
            return () -> null;
            // return RecipeSupplier.withId(() -> this.provider.shapeless(this.getCategoryOrElse(RecipeCategory.BUILDING_BLOCKS), this.getFirstIngredientItem()).requires(this.result, this.getResultCountOrElse(2)), _ -> this.createSlab().id().withSuffix("_reverse"));
        }

        public RecipeSupplier createSlabReverseConditional() {
            return this.createReverseConditional(this.createSlabReverse());
        }

        public RecipeSupplier createStonecutting(int defaultCount) {
            return RecipeSupplier.withPath(() -> SingleItemRecipeBuilder.stonecutting(this.getFirstIngredient(), this.getCategoryOrElse(RecipeCategory.BUILDING_BLOCKS), this.result, this.getResultCountOrElse(defaultCount)), () -> RecipeProvider.getConversionRecipeName(this.result, this.getFirstIngredientItem()) + "_stonecutting");
        }

        public RecipeSupplier createStonecutting() {
            return this.createStonecutting(1);
        }

        // Implementation of RegistryDependentFactory

        @Override
        public OpenProvider create(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            return new OpenProvider(output, registriesFuture) {

                @Override
                public RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
                    return new RecipeProvider(recipes, advancements) {

                        @Override
                        public void buildRecipes() {
                            ProviderFunction.this.output = this.output;
                            ProviderFunction.this.provider = this;
                            ProviderFunction.this.registries = registries;
                            ProviderFunction.this.recipes = recipes;
                            ProviderFunction.this.advancements = advancements;
                            ProviderFunction.this.buildRecipesAction.run();
                            ProviderFunction.this.output = null;
                            ProviderFunction.this.provider = null;
                            ProviderFunction.this.registries = null;
                            ProviderFunction.this.recipes = null;
                            ProviderFunction.this.advancements = null;
                        }

                    };
                }

                @Override
                protected Identifier getRecipeIdentifier(Identifier identifier) {
                    return identifier;
                }

            };
        }

    }

    public static ProviderFunction start() {
        return new ProviderFunction();
    }

}
