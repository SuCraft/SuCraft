package org.sucraft.sushi.gamecontent.items;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.Nullable;
import org.sucraft.sushi.gamecontent.block.OldReplacedBlocks;

public class ReplaceOldItemsInInventories implements Listener {

    private static final Int2ObjectOpenHashMap<Material> map = new Int2ObjectOpenHashMap<>();
    static {
        for (var replacedBlock : OldReplacedBlocks.LIST) {
            map.put(Material.matchMaterial(replacedBlock.oldBlock().toString()).ordinal(), Material.matchMaterial(replacedBlock.newBlock().toString()));
        }
    }

    /**
     * Modifies the {@link ItemStack} in-place
     * if necessary.
     */
    private static void replace(@Nullable ItemStack itemStack) {
        if (itemStack == null) return;
        var replacement = map.get(itemStack.getType().ordinal());
        if (replacement == null) return;
        itemStack.setType(replacement);
    }

    private static void replaceAllInInventory(Inventory inventory) {
        for (var itemStack : inventory.getContents()) {
            replace(itemStack);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        replaceAllInInventory(event.getPlayer().getInventory());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPickup(EntityPickupItemEvent event) {
        replace(event.getItem().getItemStack());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        replace(event.getCursor());
        replace(event.getCurrentItem());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInventoryDrag(InventoryDragEvent event) {
        replace(event.getOldCursor());
        for (int slot : event.getRawSlots()) {
            replace(event.getView().getItem(slot));
        }
    }

}
