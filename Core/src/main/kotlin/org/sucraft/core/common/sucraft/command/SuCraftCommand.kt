/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.command

import org.bukkit.Bukkit
import org.bukkit.command.*
import org.bukkit.entity.Player
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin

class SuCraftCommand private constructor(val commands: SuCraftCommands<out SuCraftPlugin>, val name: String, val executor: CommandExecutor, val tabCompleter: TabCompleter) {

	// Construction

	companion object {

		fun create(commands: SuCraftCommands<out SuCraftPlugin>, name: String, executor: CommandExecutor, tabCompleter: TabCompleter) =
			SuCraftCommand(commands, name, executor, tabCompleter)

		fun createPlayerOnly(commands: SuCraftCommands<out SuCraftPlugin>, name: String, executor: PlayerCommandExecutor, tabCompleter: PlayerTabCompleter) =
			create(commands, name,
				{ sender, command, label, arguments ->
					if (sender is Player) executor(sender, command, label, arguments) else sender.sendMessage(SuCraftCommandMessages.onlyForPlayers)
				},
				{ sender, command, label, arguments ->
					if (sender is Player) tabCompleter(sender, command, label, arguments) else null
				}
			)

	}

	val plugin: SuCraftPlugin
		get() = commands.plugin

	val bukkitCommand: PluginCommand
		get() = Bukkit.getPluginCommand(name) ?: throw IllegalStateException("Command ${plugin.name}.${name} has not been registered")

	init {
		// We assume that the command has been registered with Bukkit (preferably by defining it in plugin.yml)
		bukkitCommand.setExecutor { sender: CommandSender, command: Command, label: String, arguments: Array<String> ->
			executor(sender, command, label, arguments)
			true
		}
		bukkitCommand.setTabCompleter(tabCompleter)
	}

}