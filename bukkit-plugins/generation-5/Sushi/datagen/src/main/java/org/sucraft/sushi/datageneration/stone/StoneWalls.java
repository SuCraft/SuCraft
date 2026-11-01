package org.sucraft.sushi.datageneration.stone;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.WallBlock;
import org.sucraft.sushi.datageneration.common.CollectedFactories;
import org.sucraft.sushi.gamecontent.common.SuCraftBlockItemTags;

public final class StoneWalls {

    private StoneWalls() {
        throw new UnsupportedOperationException();
    }

    public static void add(CollectedFactories factories) {
        BuiltInRegistries.BLOCK.forEach(block -> {
            if (block.builtInRegistryHolder().key().identifier().getNamespace().equals(Identifier.DEFAULT_NAMESPACE)) {
                if (block instanceof WallBlock) {
                    factories.blockTags().add(SuCraftBlockItemTags.STONE_WALLS.block(), block);
                    factories.itemTags().add(SuCraftBlockItemTags.STONE_WALLS.item(), block.asItem());
                }
            }
        });
    }

}
