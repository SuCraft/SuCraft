package org.sucraft.sushi.datageneration.common;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public final class BlockLootTables {

    private BlockLootTables() {
        throw new UnsupportedOperationException();
    }

    public static class CollectedFactory extends org.sucraft.sushi.datageneration.common.CollectedFactory<FabricBlockLootSubProvider, Consumer<FabricBlockLootSubProvider>> {

        @Override
        public FabricBlockLootSubProvider createProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            return new FabricBlockLootSubProvider(output, registriesFuture) {

                @Override
                public void generate() {
                    for (var sub : CollectedFactory.this.subs) {
                        sub.accept(this);
                    }
                }

            };
        }

    }

}
