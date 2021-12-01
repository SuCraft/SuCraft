/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.invisibleitemframes.listener

import org.bukkit.ChatColor.*;
import org.bukkit.Material
import org.bukkit.entity.ItemFrame
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.invisibleitemframes.main.SuCraftInvisibleItemFramesPlugin


object MakeItemFrameVisibleOnDamageListener : SuCraftComponent<SuCraftInvisibleItemFramesPlugin>(SuCraftInvisibleItemFramesPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onEntityDamageByEntityMonitor(event: EntityDamageByEntityEvent) {
		(event.entity as? ItemFrame)?.isVisible = true
	}

}