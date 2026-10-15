package org.sucraft.sushi.datageneration.common;

public record CollectedFactories(
    Models.CollectedFactory models,
    BlockLootTables.CollectedFactory blockLootTables,
    Recipes.CollectedFactory recipes
) {
}
