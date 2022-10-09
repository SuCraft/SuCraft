/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.icanhasbukkitcommandalias

import org.bukkit.Bukkit
import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.module.SuCraftModule

/**
 * Adds a `/icanhasbukkit` alias for the Bukkit `/version` command without arguments.
 *
 * This command is present in the Bukkit `commands.yml` configuration file by default,
 * but this functionality does not provide modern tab-completion and instead tab-completes with player names,
 * which is erroneous.
 * This module simply re-adds this alias that is present by default, but without the
 * wrong tab-completion. Also, since the alias refers to Bukkit, this alias more logically only refers to
 * the `/version` command without arguments, rather than the `/version` command with a plugin name as argument.
 */
object ICanHasBukkitCommandAlias : SuCraftModule<ICanHasBukkitCommandAlias>() {

	// Commands

	object Commands {

		val iCanHasBukkit = command(
			"icanhasbukkit",
			"Gets the version of this server",
			PermissionDefault.TRUE
		) {
			executesSync {
				Bukkit.dispatchCommand(this, "version")
			}
		}

	}

}