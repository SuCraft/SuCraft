package org.sucraft.suki.gamecontent.world.tree;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;

/**
 * Analogous to {@link TreeFeatures}.
 */
public final class SuCraftTreeFeatures {

    private SuCraftTreeFeatures() {
        throw new UnsupportedOperationException();
    }

    public static final ResourceKey<Feature> CHERRY_FROST_TREE = createKey("cherry_frost_tree");
    public static final ResourceKey<Feature> FANCY_YELLOW_MAPLE = createKey("fancy_yellow_maple");
    public static final ResourceKey<Feature> FANCY_ORANGE_MAPLE = createKey("fancy_orange_maple");
    public static final ResourceKey<Feature> FANCY_RED_MAPLE = createKey("fancy_red_maple");

    private static ResourceKey<Feature> createKey(String path) {
        return ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath("sucraft", path));
    }

}
