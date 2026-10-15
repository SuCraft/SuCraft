package org.sucraft.sushi.clientview.packetmapping.blockstate;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.registry.TypedKey;
import net.kyori.adventure.key.Key;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.state.BlockState;
import org.sucraft.sushi.gamecontent.block.OldReplacedBlocks;
import spout.api.SpoutRegistryEvents;
import spout.api.SpoutRegistryKey;
import spout.api.clientview.packetmapping.blockstate.registry.BlockStateMappingRegistryEntryNMS;

public final class OldReplacedBlockMappings {

    private OldReplacedBlockMappings() {
        throw new UnsupportedOperationException();
    }

    public static void register(BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(
            SpoutRegistryEvents.BLOCK_STATE_MAPPING.compose(),
            event -> {
                var registry = event.registry();
                for (var replaced : OldReplacedBlocks.LIST) {
                    var oldBlock = BuiltInRegistries.BLOCK.getValue(replaced.oldBlock());
                    var newBlock = BuiltInRegistries.BLOCK.getValue(replaced.newBlock());
                    int stateI = 0;
                    for (var oldBlockState : oldBlock.getStateDefinition().getPossibleStates()) {
                        var newBlockState = newBlock.defaultBlockState().withPropertiesOf(oldBlockState);
                        registry.register(TypedKey.create(SpoutRegistryKey.BLOCK_STATE_MAPPING, Key.key("sucraft", "map_old_replaced_block_" + replaced.oldBlock().getNamespace() + "_" + replaced.oldBlock().getPath() + "_" + stateI)), builder -> {
                            var builderNMS = (BlockStateMappingRegistryEntryNMS.Builder) builder;
                            builderNMS.everyAwarenessLevel();
                            builderNMS.setFrom(oldBlockState.asBlockData());
                            builderNMS.setTo(newBlockState.asBlockData());
                        });
                        stateI++;
                    }
                }
            }
        );
    }

}
