/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.homequotes.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.sucraft.core.common.sucraft.delegate.home.PlayerTeleportToHomeEvent
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.homequotes.data.HomeQuotesData
import org.sucraft.homequotes.main.SuCraftHomeQuotesPlugin

object TeleportToHomeListener : SuCraftComponent<SuCraftHomeQuotesPlugin>(SuCraftHomeQuotesPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onPlayerTeleportToHome(event: PlayerTeleportToHomeEvent) {
		if (event.isOwnHome()) HomeQuotesData.sendRandomQuote(event.getPlayer())
	}

}