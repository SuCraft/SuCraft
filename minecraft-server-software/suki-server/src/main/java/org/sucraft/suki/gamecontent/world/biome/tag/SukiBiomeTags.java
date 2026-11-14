package org.sucraft.suki.gamecontent.world.biome.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

/**
 * Analogous to {@link BiomeTags}.
 */
public final class SukiBiomeTags {

    private SukiBiomeTags() {
        throw new UnsupportedOperationException();
    }

    public static final TagKey<Biome> HAS_COLD_BLOCKS = create("has_cold_blocks");
    public static final TagKey<Biome> HAS_GREEN_GRASSY_BLOCKS = create("has_green_grassy_blocks");
    public static final TagKey<Biome> HAS_PALE_GRASSY_BLOCKS = create("has_pale_grassy_blocks");
    public static final TagKey<Biome> HAS_LEAF_LITTER_OFTEN = create("has_leaf_litter_often");
    public static final TagKey<Biome> HAS_STRONGHOLD_SURFACE = create("has_structure/stronghold_surface");
    public static final TagKey<Biome> SURFACE_STRONGHOLD_MOVE_UP_NEVER = create("surface_stronghold_move_up/never");
    public static final TagKey<Biome> SURFACE_STRONGHOLD_MOVE_UP_BADLANDS = create("surface_stronghold_move_up/badlands");
    public static final TagKey<Biome> SURFACE_STRONGHOLD_MOVE_UP_HILL = create("surface_stronghold_move_up/hill");
    public static final TagKey<Biome> SURFACE_STRONGHOLD_MOVE_UP_MOUNTAIN = create("surface_stronghold_move_up/mountain");
    public static final TagKey<Biome> SURFACE_STRONGHOLD_MOVE_UP_PLATEAU = create("surface_stronghold_move_up/plateau");
    public static final TagKey<Biome> SURFACE_STRONGHOLD_MOVE_UP_WINDSWEPT = create("surface_stronghold_move_up/windswept");

    private static TagKey<Biome> create(final String name) {
        return TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath("sucraft", name));
    }

}
