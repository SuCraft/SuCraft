package org.sucraft.sushi.datageneration.common;

public record CollectedFactories(
    BlockLootTables.CollectedFactory blockLootTables,
    Recipes.CollectedFactory recipes
) {
}
