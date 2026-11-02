package org.sucraft.sushi.gamecontent.block;

import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

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

    }

}
