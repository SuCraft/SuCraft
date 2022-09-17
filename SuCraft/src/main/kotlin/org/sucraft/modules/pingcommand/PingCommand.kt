/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.pingcommand

import net.kyori.adventure.text.format.NamedTextColor.*
import org.bukkit.Bukkit
import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.text.sendMessage
import org.sucraft.common.text.times
import org.sucraft.common.tps.tpsFormat

/**
 * Adds the /ping command that lets you check TPS and network ping,
 * and the Easter egg /pong command.
 */
object PingCommand : SuCraftModule<PingCommand>() {

	// Commands

	object Commands {

		val ping = command(
			"ping",
			"Ping the server",
			arrayOf("lag"),
			PermissionDefault.TRUE
		) {
			executesPlayerSync {
				val tps = Bukkit.getTPS()
				val tps1Minute = tps[1].coerceAtMost(20.0)
				val tps15Minutes = tps[3].coerceAtMost(20.0)
				val ping = spigot().ping
				sendMessage(
					748390591859486011L, GRAY,
					ping,
					tpsFormat.format(tps1Minute),
					tpsFormat.format(tps15Minutes)
				) {
					YELLOW * "Pong!" + cs(
						+"Network ping:" + WHITE * variable + "ms",
						+"TPS:" + WHITE * variable + "(past minute)",
						WHITE * variable + "(past 15 minutes)"
					)
				}
			}
		}

		val pong = command(
			"pong",
			"Be a curious little shit",
			PermissionDefault.TRUE
		) {
			executesPlayerSync {
				sendMessage(384927005928112384L, YELLOW) {
					"No, this isn't how you're supposed to play the game."
				}
			}
		}

	}

}