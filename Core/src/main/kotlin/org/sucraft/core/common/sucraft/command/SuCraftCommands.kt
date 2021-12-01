/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin


typealias CommandExecutor = (sender: CommandSender, command: Command, label: String, arguments: Array<String>) -> Unit
typealias TabCompleter = (sender: CommandSender, command: Command, label: String, arguments: Array<String>) -> List<String>?
typealias PlayerCommandExecutor = (player: Player, command: Command, label: String, arguments: Array<String>) -> Unit
typealias PlayerTabCompleter = (player: Player, command: Command, label: String, arguments: Array<String>) -> List<String>?

abstract class SuCraftCommands<P : SuCraftPlugin>(plugin: P, customName: String? = "Commands") : SuCraftComponent<P>(plugin, customName)