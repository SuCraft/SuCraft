package org.sucraft.sushi.gamecontent.block;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class PlatformDependent {

    private PlatformDependent() {
        throw new UnsupportedOperationException();
    }

    public static final class BlockBehaviourProperties {

        private BlockBehaviourProperties() {
            throw new UnsupportedOperationException();
        }

        public static BlockBehaviour.Properties ofFullCopy(BlockBehaviour.Properties properties) {
            throw new UnsupportedOperationException();
        }

        public static BlockBehaviour.Properties ofLegacyCopy(BlockBehaviour.Properties properties) {
            throw new UnsupportedOperationException();
        }

    }

    public static final class BlockModelGenerators {

        private BlockModelGenerators() {
            throw new UnsupportedOperationException();
        }

        public static Object createColumnGenerator(final Block block, final Object normal, final Object mapping, final BiConsumer<Identifier, ?> modelOutput) {
            return MultiVariantGenerator.dispatch(block, new MultiVariant(WeightedList.of((Variant) normal))).with(net.minecraft.client.data.models.BlockModelGenerators.createRotatedPillar());
        }

        /**
         * @see net.minecraft.client.data.models.BlockModelGenerators#createBarrel()
         */
        public static void addBarrel(net.minecraft.client.data.models.BlockModelGenerators generators, Block block) {
            Material openTop = TextureMapping.getBlockTexture(block, "_top_open");
            MultiVariant closedModel = net.minecraft.client.data.models.BlockModelGenerators.plainVariant(TexturedModel.CUBE_BOTTOM_TOP.create(block, generators.modelOutput));
            MultiVariant openModel = net.minecraft.client.data.models.BlockModelGenerators.plainVariant(TexturedModel.CUBE_BOTTOM_TOP.get(block).updateTextures((t) -> t.put(TextureSlot.TOP, openTop)).createWithSuffix(block, "_open", generators.modelOutput));
            generators.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(BlockStateProperties.OPEN).select(false, closedModel).select(true, openModel)).with(net.minecraft.client.data.models.BlockModelGenerators.ROTATIONS_COLUMN_WITH_FACING));
        }

        public static void addLadder(net.minecraft.client.data.models.BlockModelGenerators generators, Block block) {
            generators.createNonTemplateHorizontalBlock(block);
            generators.registerSimpleFlatItemModel(block);
        }

        /**
         * @see net.minecraft.client.data.models.BlockModelGenerators.BlockFamilyProvider#pillar(Block)
         */
        public static void addPillar(net.minecraft.client.data.models.BlockModelGenerators generators, Block block) {
            Consumer<TextureMapping> mappingUpdater = mappings -> {
                mappings.put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top"));
                mappings.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block));
            };
            var verticalTexturedModelProvider = TexturedModel.COLUMN.updateTexture(mappingUpdater);
            var verticalTexturedModel = verticalTexturedModelProvider.create(block, generators.modelOutput);
            MultiVariant verticalModel = net.minecraft.client.data.models.BlockModelGenerators.plainVariant(verticalTexturedModel);
            MultiVariant horizontalModel = net.minecraft.client.data.models.BlockModelGenerators.plainVariant(TexturedModel.COLUMN_HORIZONTAL.updateTexture(mappingUpdater).create(block, generators.modelOutput));
            generators.blockStateOutput.accept(net.minecraft.client.data.models.BlockModelGenerators.createRotatedPillarWithHorizontalVariant(block, verticalModel, horizontalModel));
            net.minecraft.client.data.models.BlockModelGenerators.TEXTURED_MODELS.put(block, verticalTexturedModelProvider.get(block));
        }

        public static void addSlab(net.minecraft.client.data.models.BlockModelGenerators generators, Block slab, Block fullBlock, ModelTemplate fullBlockModelTemplate, TextureMapping mapping) {
            Identifier bottom = ModelTemplates.SLAB_BOTTOM.create(slab, mapping, generators.modelOutput);
            MultiVariant top = net.minecraft.client.data.models.BlockModelGenerators.plainVariant(ModelTemplates.SLAB_TOP.create(slab, mapping, generators.modelOutput));
            var fullBlockVariant = net.minecraft.client.data.models.BlockModelGenerators.plainModel(fullBlockModelTemplate.create(fullBlock, mapping, generators.modelOutput));
            generators.blockStateOutput.accept(net.minecraft.client.data.models.BlockModelGenerators.createSlab(slab, net.minecraft.client.data.models.BlockModelGenerators.plainVariant(bottom), top, net.minecraft.client.data.models.BlockModelGenerators.variant(fullBlockVariant)));
            generators.registerSimpleItemModel(slab, bottom);
        }

    }

}
