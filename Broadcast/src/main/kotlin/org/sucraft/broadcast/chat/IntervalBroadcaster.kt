/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.broadcast.chat

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.sucraft.broadcast.main.SuCraftBroadcastPlugin
import org.sucraft.broadcast.player.permission.SuCraftBroadcastPermissions
import org.sucraft.core.common.bukkit.scheduler.WhilePlayersAreOnlineTimerTask
import org.sucraft.core.common.sucraft.log.SuCraftLogTexts
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.discordinfo.data.DiscordChannel


@Suppress("MemberVisibilityCanBePrivate")
object IntervalBroadcaster : SuCraftComponent<SuCraftBroadcastPlugin>(SuCraftBroadcastPlugin.getInstance()) {

	// Settings

	/**
	 * The interval in ticks to broadcast messages: currently 1 hour
	 */
	private const val broadcastInterval = 20L * 60 * 60

	/**
	 * The minimum time in ticks before which to broadcast messages after a player logs in with no-one online before: currently 1 minute
	 */
	private const val broadcastMinimumDelayAfterTurningOn = 20L * 60

	private val messages = arrayOf(
		Component.join(
			JoinConfiguration.noSeparators(),
			Component.text("SuCraft Info").color(NamedTextColor.GREEN),
			Component.text(": "),
			Component.text("Want to join our Discord server? Go to "),
			DiscordChannel.GENERAL.getURLComponent(color = NamedTextColor.YELLOW)
		).color(NamedTextColor.WHITE),
		Component.join(
			JoinConfiguration.noSeparators(),
			Component.text("SuCraft Info").color(NamedTextColor.GREEN),
			Component.text(": "),
			Component.text("You can "),
			Component.text("/vote").color(NamedTextColor.YELLOW).clickEvent(ClickEvent.runCommand("/vote")),
			Component.text(", it helps to have more new people join the server :)")
		).color(NamedTextColor.WHITE)
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