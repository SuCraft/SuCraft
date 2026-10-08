package org.sucraft.sushi.datageneration.common;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public final class BlockLootTables {

    private BlockLootTables() {
        throw new UnsupportedOperationException();
    }

    public static class CollectedFactory extends org.sucraft.sushi.datageneration.common.CollectedFactory<FabricBlockLootSubProvider, ProviderFunction> {

        public void add(Consumer<BlockLootSubProvider> action) {
            this.add(BlockLootTables.start().addAction(function -> action.accept(function.provider)));
        }

        @Override
        public FabricBlockLootSubProvider createProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            return new FabricBlockLootSubProvider(output, registriesFuture) {

                @Override
                public void generate() {
                    for (var sub : CollectedFactory.this.subs) {
                        sub.generate(this);
                    }
                }

            };
        }

    }

    public static class ProviderFunction {

        // Actions

        public ArrayList<Runnable> actions = new ArrayList<>(1);

        // Context

        public BlockLootSubProvider provider;

        // Setters

        public ProviderFunction withThis(Consumer<ProviderFunction> consumer) {
            consumer.accept(this);
            return this;
        }

        public ProviderFunction setActions(List<Runnable> action) {
            this.actions = new ArrayList<>(actions);
            return this;
        }

        public ProviderFunction setAction(Runnable action) {
            return this.setActions(List.of(action));
        }

        public ProviderFunction addAction(Runnable action) {
            this.actions.add(action);
            return this;
        }

        public ProviderFunction addAction(Consumer<ProviderFunction> action) {
            return this.addAction(() -> action.accept(this));
        }

        // Implementation of RegistryDependentFactory

        public void generate(BlockLootSubProvider provider) {
            this.provider = provider;
            for (var action : ProviderFunction.this.actions) {
                action.run();
            }
            this.provider = null;
        }

    }

    public static ProviderFunction start() {
        return new ProviderFunction();
    }

}
