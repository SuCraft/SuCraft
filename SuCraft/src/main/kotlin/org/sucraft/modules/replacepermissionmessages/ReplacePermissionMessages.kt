/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.replacepermissionmessages

import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor.RED
import org.bukkit.Bukkit
import org.bukkit.command.PluginIdentifiableCommand
import org.sucraft.common.command.isRegisteredSuCraftCommandLabel
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.scheduler.runLater
import org.sucraft.common.time.TimeInTicks
import org.sucraft.modules.addpermissionstocommands.AddPermissionsToCommands


/**
 * Change the message displayed for Commands for which a player has no permission,
 * so that whether a player can use and see a command are consistent.
 */
object ReplacePermissionMessages : SuCraftModule<ReplacePermissionMessages>() {

	// Dependencies

	override val dependencies = listOf(
		AddPermissionsToCommands
	)

	// Settings

	private val replacementMessage by lazy {
		text(Bukkit.spigot().spigotConfig.getString("messages.unknown-command")!!)
	}

	private val delayInReplacingMessages = TimeInTicks(
		AddPermissionsToCommands.delaysAfterServerStartToPerformAttempt.last().ticks + 1
	)

	// Initialization

	override fun onInitialize() {
		super.onInitialize()
		// Schedule replacing the messages
		runLater(delayInReplacingMessages) {
			Bukkit.getCommandMap().knownCommands.forEach { (_, command) ->
				// Easter egg for the /stop, /restart and /reload Commands
				// TODO replace this the same message but in the format of a chat message from HAL 9000
				if (command.label.lowercase().let { it == "stop" || it == "restart" || it == "reload" })
					command.permissionMessage(text("I'm sorry Dave, I'm afraid I can't do that.", RED))
				// Only do this for plugin Commands: otherwise vanilla Commands may no longer appear
				else if (command is PluginIdentifiableCommand || isRegisteredSuCraftCommandLabel(command.label))
					command.permissionMessage(replacementMessage)
			}
		}
	}

}