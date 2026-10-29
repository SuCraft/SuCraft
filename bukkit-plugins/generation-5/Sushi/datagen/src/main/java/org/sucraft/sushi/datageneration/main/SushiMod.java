package org.sucraft.sushi.datageneration.main;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sucraft.sushi.datageneration.common.CommonBlockAdder;
import org.sucraft.sushi.datageneration.common.CommonItemAdder;
import org.sucraft.sushi.datageneration.common.DataGenerationFlag;

public class SushiMod implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("sushi");

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Sushi mod...");
        DataGenerationFlag.isDataGeneration = true;
        CommonBlockAdder.initialize();
        CommonItemAdder.initialize();
    }

}
