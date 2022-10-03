/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.networkratelimits

import it.unimi.dsi.fastutil.objects.Object2IntMap
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.sucraft.common.event.on
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.network.rateLimitNetworkConnection
import org.sucraft.common.player.runEachPlayer
import kotlin.math.min

/**
 * Limits the network sending rate for players, to avoid hitting capacity limits at either the client or server side.
 */
object NetworkRateLimits : SuCraftModule<NetworkRateLimits>() {

	// Settings

	/**
	 * The maximum network sending rate applied to each player individually, in bytes/second.
	 */
	private const val playerNetworkSendRate = 6_250_000L // 50 Mib/s = 6.25 MiB/s

	/**
	 * The maximum network sending rate spread over each IP, in bytes/second.
	 */
	private const val ipNetworkSendRate = 10_000_000L // 80 Mib/s = 10 MiB/s

	/**
	 * The maximum network sending rate spread over the entire server, in bytes/second.
	 */
	private const val serverNetworkSendRate = 37_500_000L // 300 Mib/s = 37.5 MiB/s

	// Data

	private val numberOfConnectionsPerIP: Object2IntMap<ByteArray> = Object2IntOpenHashMap<ByteArray>(8)

	// Implementation

	private fun getIntendedRateLimit(player: Player) = min(
		playerNetworkSendRate,
		min(
			ipNetworkSendRate / numberOfConnectionsPerIP.getInt(player.address.address.address),
			serverNetworkSendRate / Bukkit.getOnlinePlayers().size
		)
	)

	// Events

	init {
		// Increment the connections per IP for the player that joined,
		// and update the rate limit for each player's network connection when a player joins
		on(PlayerJoinEvent::class) {
			numberOfConnectionsPerIP.compute(player.address.address.address) { _, count ->
				if (count == null || count <= 0) 1 else count + 1
			}
			runEachPlayer { rateLimitNetworkConnection(getIntendedRateLimit(this)) }
		}
		// Decrement the connections per IP for the player that quit
		on(PlayerQuitEvent::class) {
			numberOfConnectionsPerIP.compute(player.address.address.address) { _, count ->
				if (count == null || count <= 1) null else count - 1
			}
		}
	}

}