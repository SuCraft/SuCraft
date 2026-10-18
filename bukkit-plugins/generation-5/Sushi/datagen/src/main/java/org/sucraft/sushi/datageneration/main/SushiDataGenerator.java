package org.sucraft.sushi.datageneration.main;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sucraft.sushi.datageneration.common.BlockLootTables;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Models;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.datageneration.stone.regular.DirtyStoneBricks;
import org.sucraft.sushi.datageneration.stone.regular.SnowyStoneBricks;
import org.sucraft.sushi.datageneration.stone.tuff.PolishedTuff;
import org.sucraft.sushi.datageneration.stone.tuff.TuffBricks;

public class SushiDataGenerator implements DataGeneratorEntrypoint {

    public static final Logger LOGGER = LoggerFactory.getLogger("sushi");

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {

        LOGGER.info("Initializing Sushi data generator...");
        var pack = generator.createPack();
        var factories = new CollectedFactories(
            new Models.CollectedFactory(),
            new BlockLootTables.CollectedFactory(),
            new Recipes.CollectedFactory()
        );

        // Stone / Regular
        DirtyStoneBricks.add(factories);
        SnowyStoneBricks.add(factories);
        // Stone / Tuff
        PolishedTuff.add(factories);
        TuffBricks.add(factories);

        pack.addProvider(factories.models());
        pack.addProvider(factories.blockLootTables());
        pack.addProvider(factories.recipes());

    }

}
