package spout.api.gamecontent.datadriven.item.registry;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import java.util.function.Consumer;
import java.util.function.Function;

public interface ItemTypeRegistryEntryBuilderNMS {

    void inheritsFromBlock();

    ItemTypeRegistryEntryBuilderNMS factoryNMS(Function<Item.Properties, Item> factory);

    ItemTypeRegistryEntryBuilderNMS factoryForBlockNMS();

    ItemTypeRegistryEntryBuilderNMS factoryForBlockNMS(Identifier blockIdentifier);

    ItemTypeRegistryEntryBuilderNMS propertiesNMS(Item.Properties properties);

    ItemTypeRegistryEntryBuilderNMS propertiesNMS(Consumer<Item.Properties> properties);

}
