package spout.api.clientview.packetmapping.blockstate.registry;

import net.minecraft.world.level.block.state.BlockState;

public interface BlockStateMappingRegistryEntryNMS {

    interface Builder {

        void setFromNMS(BlockState from);

        void setToNMS(BlockState to);

    }

}
