package net.fabricmc.fabric.api.datagen.v1.provider;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import java.util.Set;
import java.util.function.Function;

public abstract class FabricBlockLootSubProvider extends BlockLootSubProvider {

    private FabricBlockLootSubProvider(Set<Item> explosionResistant, FeatureFlagSet enabledFeatures, Context output) {
        super(explosionResistant, enabledFeatures, output);
        throw new UnsupportedOperationException();
    }

    public LootTable.Builder createSlabItemTable(Block slab) {
        return super.createSlabItemTable(slab);
    }

    public void dropSelf(Block block) { // Suk
        super.dropSelf(block);
    }

    public void add(Block block, Function<Block, LootTable.Builder> builder) {
        super.add(block, builder);
    }


}
