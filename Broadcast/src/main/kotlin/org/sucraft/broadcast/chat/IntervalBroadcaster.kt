/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.broadcast.chat

import org.bukkit.ChatColor.*
import org.bukkit.Bukkit
import org.sucraft.broadcast.main.SuCraftBroadcastPlugin
import org.sucraft.broadcast.player.permission.SuCraftBroadcastPermissions
import org.sucraft.core.common.bukkit.scheduler.WhilePlayersAreOnlineTimerTask
import org.sucraft.core.common.sucraft.log.SuCraftLogTexts
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent


object IntervalBroadcaster : SuCraftComponent<SuCraftBroadcastPlugin>(SuCraftBroadcastPlugin.getInstance()) {

	// Settings

	/**
	 * The interval in ticks to broadcast messages: currently 1 hour
	 */
	const val broadcastInterval = 20L * 60 * 60

	/**
	 * The minimum time in ticks before which to broadcast messages after a player logs in with no-one online before: currently 1 minute
	 */
	const val broadcastMinimumDelayAfterTurningOn = 20L * 60

	private val messages = arrayOf(
		"${GREEN}SuCraft Info${WHITE}: Want to join our Discord? Go to ${YELLOW}https://discord.gg/egH7dGX",
		"${GREEN}SuCraft Info${WHITE}: You can ${YELLOW}/vote${WHITE}, it helps to have more new people join the server :)"
	)

	// Initialization

	private var nextMessageIndex = 0

	init {
		logger.info(SuCraftLogTexts.schedulingTasks)
		WhilePlayersAreOnlineTimerTask(plugin, ::broadcastMessage, interval = broadcastInterval, minimumDelayAfterTurningOn = broadcastMinimumDelayAfterTurningOn)
	}

	// Implementation

	fun broadcastMessage() {
		val message = messages[nextMessageIndex]
		Bukkit.getOnlinePlayers().filter { !it.hasPermission(SuCraftBroadcastPermissions.IGNORE) }.forEach { it.sendMessage(message) }
		nextMessageIndex++
		if (nextMessageIndex >= messages.size) nextMessageIndex = 0
	}

}