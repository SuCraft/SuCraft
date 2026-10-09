package org.sucraft.sushi.datageneration.common;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.BlockItem;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public final class Models {

    private Models() {
        throw new UnsupportedOperationException();
    }

    public static class CollectedFactory extends org.sucraft.sushi.datageneration.common.CollectedFactory<FabricModelProvider, ModelGenerator> {

        public void addBlock(BlockStateModelGenerator generator) {
            this.add(generator);
        }

        public void addItem(ItemModelGenerator generator) {
            this.add(generator);
        }

        @Override
        public FabricModelProvider createProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            return new FabricModelProvider(output) {

                @Override
                public void generateBlockStateModels(BlockModelGenerators generators) {
                    for (var sub : CollectedFactory.this.subs) {
                        if (sub instanceof BlockStateModelGenerator generator) {
                            generator.accept(generators);
                        }
                    }
                }

                @Override
                public void generateItemModels(ItemModelGenerators generators) {
                    for (var sub : CollectedFactory.this.subs) {
                        if (sub instanceof ItemModelGenerator generator) {
                            generator.accept(generators);
                        }
                    }
                }

            };
        }

    }

    public sealed interface ModelGenerator permits BlockStateModelGenerator, ItemModelGenerator {
    }

    public non-sealed interface BlockStateModelGenerator extends Consumer<BlockModelGenerators>, ModelGenerator {
    }

    public non-sealed interface ItemModelGenerator extends Consumer<ItemModelGenerators>, ModelGenerator {
    }

    public static ItemModelGenerator blockModelForItem(BlockItem item) {
        return generators -> generators.itemModelOutput.accept(
            item,
            ItemModelUtils.plainModel(
                item.getBlock().builtInRegistryHolder().key().identifier().withPrefix("block/")
            )
        );
    }

}
