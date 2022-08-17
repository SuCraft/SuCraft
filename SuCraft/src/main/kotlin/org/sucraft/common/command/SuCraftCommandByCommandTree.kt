/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.command

import dev.jorel.commandapi.CommandTree
import org.sucraft.common.command.node.root.SuCraftCommandRootNodeByCommandTree
import org.sucraft.common.module.AbstractSuCraftComponent
import org.sucraft.common.permission.SuCraftPermission


/**
 * Represents a SuCraft command backed by a [CommandTree].
 */
class SuCraftCommandByCommandTree(
	component: AbstractSuCraftComponent<*>,
	label: String,
	description: String,
	aliases: Array<String> = emptyArray(),
	permission: SuCraftPermission? = null,
	addFunctionality: SuCraftCommandRootNodeByCommandTree.() -> SuCraftCommandRootNodeByCommandTree
) : SuCraftCommand<SuCraftCommandRootNodeByCommandTree>(
	component,
	label,
	description,
	aliases,
	permission,
	addFunctionality
) {

	override fun constructRootNode(label: String) =
		SuCraftCommandRootNodeByCommandTree(CommandTree(label))

}