package org.sucraft.sushi.gamecontent.technical;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

/**
 * Analogous to {@link ContextIntProviders}.
 */
public final class SuCraftContextIntProviders {

    private SuCraftContextIntProviders() {
        throw new UnsupportedOperationException();
    }

    public static final ResourceKey<ContextIntProvider> COOKING_TIME_WOOL_ITEMS_EXTRA_SMALL = createKey("cooking/time_wool_items_extra_small");

    private static ResourceKey<ContextIntProvider> createKey(final String location) {
        return ResourceKey.create(Registries.CONTEXT_INT_PROVIDER, Identifier.fromNamespaceAndPath("sucraft", location));
    }

}
