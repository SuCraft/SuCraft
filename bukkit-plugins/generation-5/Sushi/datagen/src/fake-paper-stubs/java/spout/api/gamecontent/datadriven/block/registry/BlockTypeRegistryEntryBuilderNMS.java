package spout.api.gamecontent.datadriven.block.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import java.util.function.Consumer;
import java.util.function.Function;

public interface BlockTypeRegistryEntryBuilderNMS {

    BlockTypeRegistryEntryBuilderNMS inheritsFromSlab();

    BlockTypeRegistryEntryBuilderNMS factoryNMS(Function<BlockBehaviour.Properties, Block> factory);

    BlockTypeRegistryEntryBuilderNMS propertiesNMS(BlockBehaviour.Properties properties);

    BlockTypeRegistryEntryBuilderNMS propertiesNMS(Consumer<BlockBehaviour.Properties> properties);

}
