package org.sucraft.sushi.datageneration.main;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sucraft.sushi.datageneration.common.BlockLootTables;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.Models;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.datageneration.stonebricks.snowy.SnowyStoneBricks;

public class SushiDataGenerator implements DataGeneratorEntrypoint {

    public static final Logger LOGGER = LoggerFactory.getLogger("sushi");

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        LOGGER.info("Initializing Sushi data generator...");
        FabricDataGenerator.Pack pack = generator.createPack();
        CollectedFactories factories = new CollectedFactories(
            new Models.CollectedFactory(),
            new BlockLootTables.CollectedFactory(),
            new Recipes.CollectedFactory()
        );
        SnowyStoneBricks.add(factories);
        pack.addProvider(factories.models());
        pack.addProvider(factories.blockLootTables());
        pack.addProvider(factories.recipes());
    }

}
