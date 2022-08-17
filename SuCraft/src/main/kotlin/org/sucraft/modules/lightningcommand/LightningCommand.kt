/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.lightningcommand

import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.location.strikeLightningEffect
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.permission.hasPermission
import org.sucraft.modules.donators.Donators
import org.sucraft.modules.donators.Donators.sendOnlyDonatorsHaveAbility

/**
 * Adds a command to strike decorative lightning for donators.
 */
object LightningCommand : SuCraftModule<LightningCommand>() {

	// Dependencies

	override val dependencies = listOf(
		Donators
	)

	// Permissions

	object Permissions {

		val lightning = permission(
			"lightning",
			"Use the /lightning command",
			PermissionDefault.OP
		)

	}

	// Commands

	object Commands {

		val lightning = command(
			"lightning",
			"Strike decorative lightning"
		) {
			executesPlayerSync executes@{

				// Make sure the player has permission to use this command
				if (!hasPermission(Permissions.lightning)) {
					sendOnlyDonatorsHaveAbility("summon decorative lightning")
					return@executes
				}

				// Log to console
				info("$name struck decorative lightning at $location")

				// Strike the lightning
				location.strikeLightningEffect()

			}
		}

	}

}