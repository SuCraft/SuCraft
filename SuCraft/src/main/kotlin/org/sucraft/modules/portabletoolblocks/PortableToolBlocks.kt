/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.portabletoolblocks

import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.module.SuCraftModule

/**
 * Adds Commands for a portable crafting table and ender chest interface,
 * including the ability to open other online players' ender chests.
 */
object PortableToolBlocks : SuCraftModule<PortableToolBlocks>() {

	// Commands

	object Commands {

		val craft = command(
			"craft",
			"Open the portable crafting table",
			arrayOf("wb"),
			permission(
				"craftingtable",
				"Use the /craft command",
				PermissionDefault.OP
			)
		) {
			executesPlayerSync {

				// Log to console
				info("$name opened the portable crafting table at $location")

				// Open the crafting table
				openWorkbench(null, true)

			}
		}

		val enderChest = commandTree(
			"enderchest",
			"Open the portable ender chest",
			PermissionDefault.OP
		) {
			executesPlayerSync {

				// Log to console
				info("$name opened the portable ender chest at $location")

				// Open the ender chest
				openInventory(enderChest)

			}.thenOnlinePlayerSync { otherPlayer ->
				withPermission(
					permission(
						"enderchest.other",
						"Open the ender chest of other players",
						PermissionDefault.OP
					)
				).executesPlayerSync {

					// Log to console
					info("$name opened the ender chest of ${it(otherPlayer).name}")

					// Open the ender chest
					openInventory(it(otherPlayer).enderChest)

				}
			}
		}

	}

}