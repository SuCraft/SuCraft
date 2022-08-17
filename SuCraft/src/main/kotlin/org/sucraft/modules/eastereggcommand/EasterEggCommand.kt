/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.eastereggcommand

import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor.GRAY
import net.kyori.adventure.text.format.NamedTextColor.WHITE
import net.kyori.adventure.text.format.TextDecoration.UNDERLINED
import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.text.clickOpenURL
import org.sucraft.common.text.sendMessage

/**
 * Adds an Easter egg command.
 */
object EasterEggCommand : SuCraftModule<EasterEggCommand>() {

	// Commands

	object Commands {

		val easterEgg = command(
			"easteregg",
			"There is nothing here",
			PermissionDefault.TRUE
		) {
			executesPlayerSync {
				sendMessage(948776889035662433L, GRAY) {
					+"Maybe this is the vibe you are looking for:" +
							text("♩♫♪♫♪", WHITE, UNDERLINED)
								.clickOpenURL("https://www.youtube.com/watch?v=bHwzYJMaroQ")
				}
			}
		}

	}

}