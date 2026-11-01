package org.sucraft.sushi.datageneration.common;

public interface CollectedFactories {

    Models.CollectedFactory models();

    BlockLootTables.CollectedFactory blockLootTables();

    Recipes.CollectedFactory recipes();

    Tags.BlockCollectedFactory blockTags();

    Tags.ItemCollectedFactory itemTags();

}
