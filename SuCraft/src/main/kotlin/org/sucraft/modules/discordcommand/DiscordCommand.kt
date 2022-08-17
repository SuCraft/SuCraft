/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.discordcommand

import net.kyori.adventure.text.format.NamedTextColor.WHITE
import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.text.sendMessage
import org.sucraft.modules.discordinformation.DiscordChannel.GENERAL
import org.sucraft.modules.discordinformation.DiscordInformation

/**
 * Adds a command to see a clickable Discord invite URL.
 */
object DiscordCommand : SuCraftModule<DiscordCommand>() {

	// Dependencies

	override val dependencies = listOf(
		DiscordInformation
	)

	// Commands

	object Commands {

		val discord = command(
			"discord",
			"Get the Discord server link",
			PermissionDefault.TRUE
		) {
			executesPlayerSync {
				sendMessage(384990382995671287L, WHITE) {
					+"Join our Discord server at:" + GENERAL.defaultURLComponent
				}
			}
		}

	}

}