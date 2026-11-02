package org.sucraft.sushi.datageneration.block;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import java.util.function.BiConsumer;

public interface UnsafeBlockStateGeneratorSupplier {

    Object create(Block block, Object normal, Object mapping, BiConsumer<Identifier, ?> modelOutput);

}
