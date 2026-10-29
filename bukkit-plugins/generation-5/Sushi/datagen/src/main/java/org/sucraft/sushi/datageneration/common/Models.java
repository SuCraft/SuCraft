package org.sucraft.sushi.datageneration.common;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;
import java.util.HashMap;
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
            Supplier<@Nullable TexturedModel> fullBlockModel,
            Supplier<@Nullable Block> slab,
            Supplier<@Nullable Block> stairs,
            Supplier<@Nullable Block> wall,
            Supplier<@Nullable Block> fence,
            Supplier<@Nullable Block> fenceGate,
            Supplier<@Nullable Block> button,
            Supplier<@Nullable Block> pressurePlate
        ) {
            this.addBlock(generators -> {
                var texturedModel = fullBlockModel.get();
                if (texturedModel != null) {
                    if (!(BlockModelGenerators.TEXTURED_MODELS instanceof HashMap)) {
                        BlockModelGenerators.TEXTURED_MODELS = new HashMap<>(BlockModelGenerators.TEXTURED_MODELS);
                    }
                    BlockModelGenerators.TEXTURED_MODELS.put(fullBlock.get(), texturedModel);
                }
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
                var fenceBlock = fence.get();
                if (fenceBlock != null) {
                    provider.fence(fenceBlock);
                }
                var fenceGateBlock = fenceGate.get();
                if (fenceGateBlock != null) {
                    provider.fenceGate(fenceGateBlock);
                }
                var buttonBlock = button.get();
                if (buttonBlock != null) {
                    provider.button(buttonBlock);
                }
                var pressurePlateBlock = pressurePlate.get();
                if (pressurePlateBlock != null) {
                    provider.pressurePlate(pressurePlateBlock);
                }
            });
        }

        public void addBlockTrivialCube(Block block) {
            this.addBlock(generators -> generators.createTrivialCube(block));
        }

        public void addBlockCubeColumn(Block block, @Nullable Block side, @Nullable Block end) {
            this.addBlockCubeColumn(block, getBlockTextureIdentifier(side), getBlockTextureIdentifier(end));
        }

        public void addBlockCubeColumn(Block block, @Nullable Identifier side, @Nullable Identifier end) {
            this.addBlock(generators -> generators.createTrivialBlock(block, TexturedModel.COLUMN.updateTexture(mapping -> {
                if (side != null) {
                    mapping.put(TextureSlot.SIDE, new Material(side));
                }
                if (end != null) {
                    mapping.put(TextureSlot.END, new Material(end));
                }
            })));
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

        public void addBlockWall(Block wall) {
            // TODO
        }

        public void addBlockFence(Block fence) {
            // TODO
        }

        public void addBlockFenceGate(Block fenceGate) {
            // TODO
        }

        public void addBlockButton(Block button) {
            // TODO
        }

        public void addBlockPressurePlate(Block pressurePlate) {
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

    public static @Nullable Identifier getBlockTextureIdentifier(@Nullable Block block) {
        if (block == null) {
            return null;
        }
        return block.builtInRegistryHolder().key().identifier().withPrefix("block/");
    }

}
