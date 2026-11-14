package org.sucraft.suki.gamecontent.world.biome;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

/**
 * Analogous to {@link Biomes}.
 */
public final class SuCraftBiomes {

    private SuCraftBiomes() {
        throw new UnsupportedOperationException();
    }

    public static final ResourceKey<Biome> FROST_FOREST = register("frost_forest");
    public static final ResourceKey<Biome> ORANGE_FOREST = register("orange_forest");

    private static ResourceKey<Biome> register(final String name) {
        return ResourceKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath("sucraft", name));
    }

}
