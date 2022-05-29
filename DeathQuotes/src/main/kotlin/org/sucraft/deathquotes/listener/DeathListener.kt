/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.deathquotes.listener

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.PlayerDeathEvent
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.deathquotes.data.DeathQuotesData
import org.sucraft.deathquotes.main.SuCraftDeathQuotesPlugin

object DeathListener : SuCraftComponent<SuCraftDeathQuotesPlugin>(SuCraftDeathQuotesPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onPlayerDeath(event: PlayerDeathEvent) =
		Bukkit.getScheduler().runTaskLater(SuCraftDeathQuotesPlugin.getInstance(), Runnable {
			DeathQuotesData.sendRandomQuote(event.player)
		}, 1);

}