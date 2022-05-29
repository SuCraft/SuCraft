/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.discordinfo.data

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

@Suppress("MemberVisibilityCanBePrivate")
enum class DiscordChannel(val channelName: String, val actualURL: String, val visualURL: String) {

	GENERAL("general", "srdeC2B"),
	LINKS("links", "egH7dGX"),
	DEVELOPMENT("development", "pbsPkpUjG4"),
	GUIDE("guide", "5A7jh4UttV");

	constructor(channelName: String, inviteCode: String) : this(channelName, "https://discord.com/invite/${inviteCode}", "discord.gg/${inviteCode}")

	val hashtagChannelName get() = "#${channelName}"

	fun getURLComponent(color: TextColor? = null, underline: Boolean? = true) =
		Component.text(visualURL)
			.let { if (color == null) it else it.color(color) }
			.let { if (underline == null) it else it.decoration(TextDecoration.UNDERLINED, underline) }
			.clickEvent(ClickEvent.openUrl(actualURL))

}