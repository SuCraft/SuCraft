package net.minecraft.client.data.models;

import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import java.util.function.Consumer;

public abstract class BlockModelGenerators {

    public Consumer<BlockModelDefinitionGenerator> blockStateOutput;
    public ItemModelOutput itemModelOutput;

    public static MultiVariant plainVariant(Identifier model) {
        throw new UnsupportedOperationException();
    }

    public abstract void registerSimpleItemModel(final Item item, final Identifier model);

    public enum PlantType {

        TINTED,
        NOT_TINTED,
        EMISSIVE_NOT_TINTED;

        public Identifier createItemModelUsingBlockTexture(final BlockModelGenerators generator, final Block block) {
            throw new UnsupportedOperationException();
        }

    }

}
