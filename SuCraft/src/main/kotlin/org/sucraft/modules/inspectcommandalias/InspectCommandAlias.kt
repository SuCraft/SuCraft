/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.inspectcommandalias

import org.bukkit.Bukkit
import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.module.SuCraftModule

/**
 * Adds a shorthand `/inspect` (and `/in`) alias for the CoreProtect `/co inspect` command.
 */
object InspectCommandAlias : SuCraftModule<InspectCommandAlias>() {

	// Commands

	object Commands {

		val inspect = command(
			"inspect",
			"Inspect block history",
			arrayOf("in"),
			PermissionDefault.OP
		) {
			executesSync {
				Bukkit.dispatchCommand(this, "co inspect")
			}
		}

	}

}