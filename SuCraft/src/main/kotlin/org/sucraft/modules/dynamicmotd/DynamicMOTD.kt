/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.dynamicmotd

import com.destroystokyo.paper.event.server.PaperServerListPingEvent
import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.event.on
import org.sucraft.common.module.SuCraftModule
import org.sucraft.modules.offlineplayerinformation.OfflinePlayerInformation

/**
 * Customizes the MOTD shown in the server ping list, based on the number of online players.
 */
object DynamicMOTD : SuCraftModule<DynamicMOTD>() {

	// Dependencies

	override val dependencies = listOf(
		OfflinePlayerInformation
	)

	// Permissions

	object Permissions {

		val appearInDonatorMOTD = permission(
			"appearindonatormotd",
			"Appear in a Donator MOTD",
			PermissionDefault.FALSE
		)

	}

	// Initialization

	override fun onInitialize() {
		super.onInitialize()
		// Make sure we know the donator MOTDs
		// Disabled because of some glitch in PermissionsBukkit that breaks the config.yml if this is called
//		startComputingDonatorMOTDs()
	}

	// Events

	init {
		on(PaperServerListPingEvent::class) {
			motd(pickMOTD(address))
		}
	}

}