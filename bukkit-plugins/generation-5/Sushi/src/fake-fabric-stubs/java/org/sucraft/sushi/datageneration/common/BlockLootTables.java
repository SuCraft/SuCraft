package org.sucraft.sushi.datageneration.common;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import java.util.function.Consumer;

public interface BlockLootTables {

    interface CollectedFactory {

        void add(Consumer<FabricBlockLootSubProvider> sub);

    }

}
