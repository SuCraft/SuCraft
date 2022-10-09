/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.uuidcommand

import net.kyori.adventure.text.format.NamedTextColor.GRAY
import net.kyori.adventure.text.format.NamedTextColor.WHITE
import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.player.isPlayerOnline
import org.sucraft.common.text.sendMessage
import org.sucraft.common.text.times
import org.sucraft.modules.offlineplayerinformation.OfflinePlayerInformation
import org.sucraft.modules.offlineplayerinformation.OfflinePlayerInformation.getDetailedOfflinePlayer
import org.sucraft.modules.offlineplayerinformation.OfflinePlayerInformation.getName

/**
 * Adds the `/uuid` command that lets you find the UUID for a given (offline) player name,
 * and the `/uuidname` command that lets you find the last name a given UUID joined with.
 */
object UUIDCommand : SuCraftModule<UUIDCommand>() {

	// Dependencies

	override val dependencies = listOf(
		OfflinePlayerInformation
	)

	// Commands

	object Commands {

		val uuid = commandTree(
			"uuid",
			"See the UUID of an (offline) player",
			PermissionDefault.OP
		) {
			thenOfflinePlayerAsync { offlinePlayer ->
				executesAsync {
					sendMessage(
						967432758234687995L, GRAY,
						it(offlinePlayer).getName(),
						it(offlinePlayer).uuid,
						if (it(offlinePlayer).isOnline()) "online" else "offline"
					) {
						+"The UUID of" + WHITE * variable + "is:" + WHITE * variable +
								(+"(" - WHITE * variable - ")")
					}
				}
			}
		}

		val uuidName = commandTree(
			"uuidname",
			"See the last player name associated with a UUID",
			PermissionDefault.OP
		) {
			thenOfflinePlayerUUIDAsync { uuid ->
				executesAsync {
					val offlinePlayer = it(uuid).getDetailedOfflinePlayer()
					sendMessage(
						746889789037005911L, GRAY,
						it(uuid),
						offlinePlayer?.getName(),
						if (it(uuid).isPlayerOnline()) "online" else "offline"
					) {
						+"The name of" + WHITE * variable + "is:" + WHITE * variable +
								(+"(" - WHITE * variable - ")")
					}
				}
			}
		}

	}

}