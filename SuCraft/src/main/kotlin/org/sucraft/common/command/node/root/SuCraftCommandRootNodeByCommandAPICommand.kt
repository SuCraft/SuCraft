/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.command.node.root

import dev.jorel.commandapi.CommandAPICommand
import org.sucraft.common.command.SuCraftCommand
import org.sucraft.common.command.node.SuCraftCommandNodeByCommandAPICommand
import org.sucraft.common.permission.SuCraftPermission

class SuCraftCommandRootNodeByCommandAPICommand(
	commandAPICommand: CommandAPICommand
) : SuCraftCommandNodeByCommandAPICommand<SuCraftCommandRootNodeByCommandAPICommand,
		SuCraftCommandRootNodeByCommandAPICommand>(
	commandAPICommand
), SuCraftCommandRootNode<SuCraftCommandRootNodeByCommandAPICommand> {

	override lateinit var _command: SuCraftCommand<SuCraftCommandRootNodeByCommandAPICommand>

	override fun withAliases(vararg aliases: String) = apply {
		commandAPICommand.withAliases(*aliases)
	}

	override fun withFullDescription(fullDescription: String) = apply {
		commandAPICommand.withFullDescription(fullDescription)
	}

	override fun withPermission(permission: SuCraftPermission) = apply {
		commandAPICommand.withPermission(permission.key)
	}

	override fun register() =
		commandAPICommand.register()

}