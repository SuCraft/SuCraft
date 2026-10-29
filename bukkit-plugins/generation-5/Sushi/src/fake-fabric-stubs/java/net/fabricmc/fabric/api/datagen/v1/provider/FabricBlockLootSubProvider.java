package net.fabricmc.fabric.api.datagen.v1.provider;

import net.minecraft.core.Holder;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import java.util.Set;
import java.util.function.Function;

public abstract class FabricBlockLootSubProvider extends BlockLootSubProvider {

    private FabricBlockLootSubProvider(Set<Item> explosionResistant, FeatureFlagSet enabledFeatures, Context output) {
        super(explosionResistant, enabledFeatures, output);
        throw new UnsupportedOperationException();
    }

    @Override
    public Holder<LootItemCondition> hasSilkTouch() {
        throw new UnsupportedOperationException();
    }

    @Override
    public LootItemCondition.Builder doesNotHaveSilkTouch() {
        throw new UnsupportedOperationException();
    }

    @Override
    public LootTable.Builder createSingleItemTableWithSilkTouch(Block original, ItemLike drop, Holder<ContextIntProvider> count) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LootTable.Builder createSlabItemTable(Block slab) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void dropSelf(Block block) { // Suk
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(Block block, Function<Block, LootTable.Builder> builder) {
        throw new UnsupportedOperationException();
    }

}
