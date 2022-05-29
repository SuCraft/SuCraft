/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.chat

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender

object SuCraftChatTexts {

	fun sendNoPermission(audience: CommandSender) =
		audience.sendMessage(
			Component.text("You can't do that here.").color(NamedTextColor.WHITE)
		)

}