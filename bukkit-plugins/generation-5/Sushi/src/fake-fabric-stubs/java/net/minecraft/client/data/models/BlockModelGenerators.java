package net.minecraft.client.data.models;

import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.resources.Identifier;
import java.util.function.Consumer;

public class BlockModelGenerators {

    public Consumer<BlockModelDefinitionGenerator> blockStateOutput;
    public ItemModelOutput itemModelOutput;

    public static MultiVariant plainVariant(Identifier model) {
        throw new UnsupportedOperationException();
    }

}
