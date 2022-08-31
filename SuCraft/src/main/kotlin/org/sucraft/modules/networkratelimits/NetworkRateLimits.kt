/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.networkratelimits

import org.bukkit.event.player.PlayerJoinEvent
import org.sucraft.common.event.on
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.network.rateLimitNetworkConnection

/**
 * Limits the network sending rate for players, to avoid hitting capacity limits at either the client or server side.
 */
object NetworkRateLimits : SuCraftModule<NetworkRateLimits>() {

	// Settings

	/**
	 * The maximum network sending rate applied to each player individually, in bytes/second.
	 */
	private const val playerNetworkSendRate = 2500000L // 20 Mib/s = 2.5 MiB/s

	// Events

	init {
		// Rate-limit the network connection when they join
		on(PlayerJoinEvent::class) {
			player.rateLimitNetworkConnection(playerNetworkSendRate)
		}
	}

}