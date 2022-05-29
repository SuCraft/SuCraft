/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.defaultdelegate

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerJoinEvent
import org.sucraft.core.common.general.log.AbstractLogger
import org.sucraft.core.common.sucraft.delegate.OfflinePlayersInformation
import org.sucraft.core.common.sucraft.player.PlayerUUID
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.core.main.SuCraftCorePlugin
import java.util.*

object OfflinePlayersInformationByBukkitOfflinePlayerCall : OfflinePlayersInformation<SuCraftCorePlugin>, SuCraftComponent<SuCraftCorePlugin>(SuCraftCorePlugin.getInstance()) {

	// Settings

	private const val cacheOfflinePlayerNamesOnInit = true

	// Initialization

	init {
		OfflinePlayersInformation.registerImplementation(this)
		if (cacheOfflinePlayerNamesOnInit) getOfflinePlayerNames()
	}

	// Delegate overrides

	override fun getDelegatePlugin(): SuCraftCorePlugin = plugin

	override fun getDelegateLogger(): AbstractLogger = logger

	// Data

	/**
	 * null if not cached yet
	 */
	private var cachedOfflinePlayerNames: MutableList<String>? = null

	// Implementation

	private class OfflinePlayerInformationByBukkitOfflinePlayerCall(val uuid: UUID) : OfflinePlayersInformation.OfflinePlayerInformation {

		override fun getName() = getOfflinePlayer().name

		override fun getOfflinePlayer() = Bukkit.getOfflinePlayer(uuid)

		override fun getPlayerUUID() = PlayerUUID.getAlreadyConfirmed(uuid)

	}

	override fun getInformation(uuid: UUID): OfflinePlayersInformation.OfflinePlayerInformation? = getInformation(Bukkit.getOfflinePlayer(uuid))

	override fun getInformation(player: OfflinePlayer): OfflinePlayersInformation.OfflinePlayerInformation? = if (player.hasPlayedBefore() || player.isOnline) OfflinePlayerInformationByBukkitOfflinePlayerCall(player.uniqueId) else null

	// Get the offline player names by making a call to Bukkit.getOfflinePlayers

	override fun getOfflinePlayerNames(): List<String> {
		if (cachedOfflinePlayerNames == null)
			cachedOfflinePlayerNames = Bukkit.getOfflinePlayers().mapNotNull(OfflinePlayer::getName).toMutableList()
		return cachedOfflinePlayerNames!!
	}

	// Since the offline player names may have been cached when a player joins, add any names of joining players to the offline player names

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onPlayerJoin(event: PlayerJoinEvent) =
		cachedOfflinePlayerNames?.add(event.player.name)

}