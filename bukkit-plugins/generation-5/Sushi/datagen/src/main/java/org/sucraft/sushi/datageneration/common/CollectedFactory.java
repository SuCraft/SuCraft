package org.sucraft.sushi.datageneration.common;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class CollectedFactory<T extends DataProvider, S> implements FabricDataGenerator.Pack.RegistryDependentFactory<T> {

    protected final List<S> subs = new ArrayList<>();

    public void add(S sub) {
        this.subs.add(sub);
    }

    public abstract T createProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture);

    @Override
    public T create(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        return this.createProvider(output, registriesFuture);
    }

}
