package net.minecraft.client.data.models;

import net.minecraft.world.item.Item;

public interface ItemModelOutput {

    void copy(Item donor, Item acceptor);

}
