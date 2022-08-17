/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.command.node.root

import dev.jorel.commandapi.CommandTree
import org.sucraft.common.command.SuCraftCommand
import org.sucraft.common.command.node.ResolvableArgument
import org.sucraft.common.command.node.SuCraftCommandNodeByCommandTree
import org.sucraft.common.permission.SuCraftPermission

class SuCraftCommandRootNodeByCommandTree(
	commandTree: CommandTree
) : SuCraftCommandNodeByCommandTree<SuCraftCommandRootNodeByCommandTree, SuCraftCommandRootNodeByCommandTree>(
	commandTree
), SuCraftCommandRootNode<SuCraftCommandRootNodeByCommandTree> {

	override lateinit var _command: SuCraftCommand<SuCraftCommandRootNodeByCommandTree>

	override fun withAliases(vararg aliases: String) = apply {
		commandTree.withAliases(*aliases)
	}

	override fun withFullDescription(fullDescription: String) = apply {
		commandTree.withFullDescription(fullDescription)
	}

	override fun withPermission(permission: SuCraftPermission) = apply {
		commandTree.withPermission(permission.key)
	}

	override fun getResolvableArguments(): Sequence<ResolvableArgument<*, *>> =
		// Root commands do not have resolvable arguments
		emptySequence()

	override fun register() =
		commandTree.register()

}