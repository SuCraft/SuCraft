/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.modules.discordinformation

import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration.UNDERLINED
import org.sucraft.common.function.runIf
import org.sucraft.common.text.DISCORD

/**
 * Represents a channel of the Discord server, and contains some information about it.
 */
@Suppress("SpellCheckingInspection")
enum class DiscordChannel(val channelName: String, val actualURL: String, val visualURL: String) {

	GENERAL("general", "srdeC2B"),
	LINKS("links", "egH7dGX"),
	DEVELOPMENT("development", "pbsPkpUjG4"),
	GUIDE("guide", "5A7jh4UttV");

	constructor(channelName: String, inviteCode: String) : this(
		channelName,
		"https://discord.com/invite/${inviteCode}",
		"discord.gg/${inviteCode}"
	)

	val hashtagChannelName
		get() = "#${channelName}"

	val defaultURLComponent by lazy {
		getURLComponent(DISCORD, true)
	}

	fun getURLComponent(color: TextColor? = null, underline: Boolean? = true) =
		text(visualURL)
			.runIf(color != null) { color(color!!) }
			.runIf(underline != null) { decoration(UNDERLINED, underline!!) }
			.clickEvent(ClickEvent.openUrl(actualURL))

}