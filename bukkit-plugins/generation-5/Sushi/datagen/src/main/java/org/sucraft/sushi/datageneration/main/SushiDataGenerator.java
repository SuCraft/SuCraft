package org.sucraft.sushi.datageneration.main;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sucraft.sushi.datageneration.common.BlockLootTables;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.datageneration.common.CommonBlockAdder;
import org.sucraft.sushi.datageneration.common.CommonItemAdder;
import org.sucraft.sushi.datageneration.common.Models;
import org.sucraft.sushi.datageneration.common.Recipes;
import org.sucraft.sushi.datageneration.creative.Barrier;
import org.sucraft.sushi.datageneration.creative.Light;
import org.sucraft.sushi.datageneration.flora.Coral;
import org.sucraft.sushi.datageneration.glass.Glass;
import org.sucraft.sushi.datageneration.mineral.copper.Copper;
import org.sucraft.sushi.datageneration.mineral.iron.Minecarts;
import org.sucraft.sushi.datageneration.redstone.Dispenser;
import org.sucraft.sushi.datageneration.redstone.Repeater;
import org.sucraft.sushi.datageneration.stone.deepslate.Deepslate;
import org.sucraft.sushi.datageneration.stone.regular.DirtyStoneBricks;
import org.sucraft.sushi.datageneration.stone.regular.SnowyStoneBricks;
import org.sucraft.sushi.datageneration.wood.Chests;
import org.sucraft.sushi.datageneration.wood.DyedPlanks;
import org.sucraft.sushi.datageneration.wood.Oak;
import org.sucraft.sushi.datageneration.wood.Spruce;

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

        CommonBlockAdder.add(factories);
        CommonItemAdder.add(factories);

        // Creative
        Barrier.add(factories);
        Light.add(factories);

        // Flora
        Coral.add(factories);

        // Glass
        Glass.add(factories);

        // Mineral / Copper
        Copper.add(factories);

        // Mineral / Iron
        Minecarts.add(factories);

        // Redstone
        Dispenser.add(factories);
        Repeater.add(factories);

        // Stone / Deepslate
        Deepslate.add(factories);

        // Stone / Regular
        DirtyStoneBricks.add(factories);
        SnowyStoneBricks.add(factories);

        // Wood
        Chests.add(factories);
        DyedPlanks.add(factories);
        Oak.add(factories);
        Spruce.add(factories);

        pack.addProvider(factories.models());
        pack.addProvider(factories.blockLootTables());
        pack.addProvider(factories.recipes());

    }

}
