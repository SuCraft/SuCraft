/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.command

import dev.jorel.commandapi.CommandAPICommand
import org.sucraft.common.command.node.root.SuCraftCommandRootNodeByCommandAPICommand
import org.sucraft.common.module.AbstractSuCraftComponent
import org.sucraft.common.permission.SuCraftPermission

/**
 * Represents a SuCraft command backed by a [CommandAPICommand].
 */
class SuCraftCommandByCommandAPICommand(
	component: AbstractSuCraftComponent<*>,
	label: String,
	description: String,
	aliases: Array<String> = emptyArray(),
	permission: SuCraftPermission? = null,
	addFunctionality: SuCraftCommandRootNodeByCommandAPICommand.() -> SuCraftCommandRootNodeByCommandAPICommand
) : SuCraftCommand<SuCraftCommandRootNodeByCommandAPICommand>(
	component,
	label,
	description,
	aliases,
	permission,
	addFunctionality
) {

	override fun constructRootNode(label: String) =
		SuCraftCommandRootNodeByCommandAPICommand(CommandAPICommand((label)))

}