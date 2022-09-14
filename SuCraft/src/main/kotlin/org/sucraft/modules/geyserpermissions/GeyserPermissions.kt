/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.geyserpermissions

import org.bukkit.event.player.PlayerJoinEvent
import org.sucraft.common.event.on
import org.sucraft.common.geyser.isConnectedViaGeyser
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.permission.addAttachmentAndUpdateCommands
import org.sucraft.modules.addpermissionstocommands.AddPermissionsToCommands

/**
 * Gives permissions for Geyser commands only to players that are connected via Geyser.
 */
object GeyserPermissions : SuCraftModule<GeyserPermissions>() {

	// Dependencies

	override val dependencies = listOf(
		AddPermissionsToCommands
	)

	// Settings

	private val permissionsToGiveGeyserPlayers by lazy {
		arrayOf(
			AddPermissionsToCommands.getOverridePermissionKey("Geyser-Spigot", "geyser"),
			"geyser.command.help",
			"geyser.command.advancements",
			"geyser.command.offhand",
			"geyser.command.settings",
			"geyser.command.statistics"
		)
	}

	// Events

	init {
		// Listen for player joins to give them permissions if needed
		on(PlayerJoinEvent::class) {
			with(player) {
				if (!isConnectedViaGeyser) return@on
				addAttachmentAndUpdateCommands(permissionsToGiveGeyserPlayers)
			}
		}
	}

}