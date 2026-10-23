package org.sucraft.sushi.datageneration.common;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class Models {

    private Models() {
        throw new UnsupportedOperationException();
    }

    public static class CollectedFactory extends org.sucraft.sushi.datageneration.common.CollectedFactory<FabricModelProvider, ModelGenerator> {

        public void addBlock(BlockStateModelGenerator generator) {
            this.add(generator);
        }

        public void addBlockFamily(
            Supplier<Block> fullBlock,
            Supplier<@Nullable Block> slab,
            Supplier<@Nullable Block> stairs,
            Supplier<@Nullable Block> wall,
            Supplier<@Nullable Block> button
        ) {
            this.addBlock(generators -> {
                var provider = generators.family(fullBlock.get());
                var slabBlock = slab.get();
                if (slabBlock != null) {
                    provider.slab(slabBlock);
                }
                var stairsBlock = stairs.get();
                if (stairsBlock != null) {
                    provider.stairs(stairsBlock);
                }
                var wallBlock = wall.get();
                if (wallBlock != null) {
                    provider.wall(wallBlock);
                }
                var buttonBlock = button.get();
                if (buttonBlock != null) {
                    provider.button(buttonBlock);
                }
            });
        }

        public void addBlockTrivialCube(Block block) {
            this.addBlock(generators -> generators.createTrivialCube(block));
        }

        public void addBlockSlab(Block slab) {
            // TODO
        }

        public void addBlockStairs(Block stairs) {
            // TODO
            // this.addBlock(generators -> {
            //     MultiVariant inner = BlockModelGenerators.plainVariant(this.getOrCreateModel(ModelTemplates.STAIRS_INNER, stairs));
            //     Identifier straight = this.getOrCreateModel(ModelTemplates.STAIRS_STRAIGHT, stairs);
            //     MultiVariant outer = BlockModelGenerators.plainVariant(this.getOrCreateModel(ModelTemplates.STAIRS_OUTER, stairs));
            //     generators.blockStateOutput.accept(BlockModelGenerators.createStairs(stairs, inner, BlockModelGenerators.plainVariant(straight), outer));
            //     // generators.registerSimpleItemModel(stairs, straight);
            // });
        }

        public void addBlockWall(Block slab) {
            // TODO
        }

        public void addBlockButton(Block button) {
            // TODO
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
