package org.sucraft.sushi.gamecontent.block;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.sucraft.sushi.main.SushiPlugin;
import java.util.function.BiConsumer;

public final class PlatformDependent {

    private PlatformDependent() {
        throw new UnsupportedOperationException();
    }

    public static final class BlockBehaviourProperties {

        private BlockBehaviourProperties() {
            throw new UnsupportedOperationException();
        }

        public static BlockBehaviour.Properties ofFullCopy(BlockBehaviour.Properties properties) {
            return BlockBehaviour.Properties.ofFullCopy(properties);
        }

        public static BlockBehaviour.Properties ofLegacyCopy(BlockBehaviour.Properties properties) {
            return BlockBehaviour.Properties.ofLegacyCopy(properties);
        }

        public static BlockBehaviour.Properties setRequiresCorrectToolForDrops(BlockBehaviour.Properties properties, boolean requiresCorrectToolForDrops) {
            properties.requiresCorrectToolForDrops = requiresCorrectToolForDrops;
            return properties;
        }

    }

    public static final class BlockModelGenerators {

        private BlockModelGenerators() {
            throw new UnsupportedOperationException();
        }

        public static Object createColumnGenerator(final Block block, final Object normal, final Object mapping, final BiConsumer<Identifier, ?> modelOutput) {
            throw new UnsupportedOperationException();
        }

        public static void addBarrel(net.minecraft.client.data.models.BlockModelGenerators generators, Block block) {
            throw new UnsupportedOperationException();
        }

        public static void addLadder(net.minecraft.client.data.models.BlockModelGenerators generators, Block block) {
            throw new UnsupportedOperationException();
        }

        public static void addPillar(net.minecraft.client.data.models.BlockModelGenerators generators, Block block) {
            throw new UnsupportedOperationException();
        }

        public static void addWall(net.minecraft.client.data.models.BlockModelGenerators generators, Block wall, Identifier texture) {
            throw new UnsupportedOperationException();
        }

    }

    public static final class SushiPlugin {

        private SushiPlugin() {
            throw new UnsupportedOperationException();
        }

        public static boolean hasEnablingStarted() {
            return org.sucraft.sushi.main.SushiPlugin.hasEnablingStarted();
        }

    }

}
