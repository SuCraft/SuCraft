package org.sucraft.suki.gamecontent.world.structure;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;

/**
 * Analogous to {@link BuiltinStructures}.
 */
public final class SuCraftBuiltinStructures {

    private SuCraftBuiltinStructures() {
        throw new UnsupportedOperationException();
    }

    public static final ResourceKey<Structure> STRONGHOLD_SURFACE = createKey("stronghold_surface");

    private static ResourceKey<Structure> createKey(final String name) {
        return ResourceKey.create(Registries.STRUCTURE, Identifier.fromNamespaceAndPath("sucraft", name));
    }

}
