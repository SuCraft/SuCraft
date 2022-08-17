/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.opmecommand

import net.kyori.adventure.text.format.NamedTextColor.DARK_GRAY
import net.kyori.adventure.text.format.NamedTextColor.GRAY
import net.kyori.adventure.text.format.TextDecoration.ITALIC
import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.text.sendMessage
import org.sucraft.common.text.times

/**
 * Adds the Easter egg /opme command.
 */
object OpMeCommand : SuCraftModule<OpMeCommand>() {

	// Commands

	object Commands {

		val opme = command(
			"opme",
			"Become Moderator",
			PermissionDefault.TRUE
		) {
			executesPlayerSync {
				sendMessage(
					758333640985647128L, null,
					name
				) {
					ITALIC * (GRAY * "[Server: Made" + variable + "a server operator]") +
							DARK_GRAY * "*SIKE*"
				}
			}
		}

	}

}