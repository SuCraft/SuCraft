/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.rulescommand

import net.kyori.adventure.text.format.NamedTextColor.*
import net.kyori.adventure.text.format.TextColor.color
import net.kyori.adventure.text.format.TextDecoration.BOLD
import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.text.component
import org.sucraft.common.text.times

/**
 * Adds a command that shows the server rules.
 */
object RulesCommand : SuCraftModule<RulesCommand>() {

	// Settings

	private val message = component(color = WHITE) {
		val bulletPrefix = color(0xD4D4D4) * " - "
		val continuedLinePrefix = +"     "
		fun bullet(vararg lines: Any?) = nl(
			bulletPrefix + lines[0],
			*lines.copyOfRange(1, lines.size).map { continuedLinePrefix + it }.toTypedArray()
		)
		return@component nl(
			BOLD * "Not allowed",
			bullet("Griefing"),
			bullet(
				"Cheats/hacks, including: xray, fly hacks,",
				"movement hacks, combat hacks, block hacks,",
				"autominers, bots"
			),
			bullet("Verbal abuse, spam"),
			bullet("Bug abuse"),
			BOLD * "Allowed",
			bullet("Automatic/mob farms"),
			bullet("Standing AFK"),
			bullet("Non-official launchers"),
			bullet(
				"Mods: graphics mods, optimization mods,",
				"HUDs (like minimaps), schematic viewers,",
				"inventory organization mods, macro clickers,"
			),
			bullet(
				"Swear words, objectionable language",
				"when not targeted at someone specifically"
			)
		)
	}

	// Commands

	object Commands {

		val rules = command(
			"rules",
			"See the rules",
			PermissionDefault.TRUE
		) {
			executesAsync {
				sendMessage(message)
			}
		}

	}

}