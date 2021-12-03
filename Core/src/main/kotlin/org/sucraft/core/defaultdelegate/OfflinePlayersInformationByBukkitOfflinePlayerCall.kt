/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.defaultdelegate

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.sucraft.core.common.general.log.AbstractLogger
import org.sucraft.core.common.sucraft.delegate.MinecraftClientLocale
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

	override fun getPlugin(): SuCraftCorePlugin = plugin

	override fun getLogger(): AbstractLogger = logger

	// Data

	/**
	 * null if not cached yet
	 */
	private var cachedOfflinePlayerNames: List<String>? = null

	// Implementation

	private class OfflinePlayerInformationByBukkitOfflinePlayerCall(val uuid: UUID) : OfflinePlayersInformation.OfflinePlayerInformation {

		override fun getName() = getOfflinePlayer().name

		override fun getOfflinePlayer() = Bukkit.getOfflinePlayer(uuid)

		override fun getPlayerUUID() = PlayerUUID.getAlreadyConfirmed(uuid)

	}

	override fun getInformation(uuid: UUID): OfflinePlayersInformation.OfflinePlayerInformation? = getInformation(Bukkit.getOfflinePlayer(uuid))

	override fun getInformation(player: OfflinePlayer): OfflinePlayersInformation.OfflinePlayerInformation? = if (player.hasPlayedBefore()) OfflinePlayerInformationByBukkitOfflinePlayerCall(player.uniqueId) else null

	override fun getOfflinePlayerNames(): List<String> {
		if (cachedOfflinePlayerNames == null)
			cachedOfflinePlayerNames = Bukkit.getOfflinePlayers().mapNotNull(OfflinePlayer::getName)
		return cachedOfflinePlayerNames!!
	}

}