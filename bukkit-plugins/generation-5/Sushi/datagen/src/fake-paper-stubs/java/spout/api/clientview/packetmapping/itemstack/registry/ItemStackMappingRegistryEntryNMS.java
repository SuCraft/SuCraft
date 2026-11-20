package spout.api.clientview.packetmapping.itemstack.registry;

import net.minecraft.world.item.Item;

public interface ItemStackMappingRegistryEntryNMS {

    interface Builder {

        void setFromNMS(Item from);

        void setToNMS(Item to);

    }

}
