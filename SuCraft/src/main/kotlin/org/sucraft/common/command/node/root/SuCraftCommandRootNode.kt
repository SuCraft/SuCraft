/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.command.node.root

import org.sucraft.common.command.SuCraftCommand
import org.sucraft.common.command.node.SuCraftCommandPermissibleNode
import org.sucraft.common.permission.SuCraftPermission

interface SuCraftCommandRootNode<RN : SuCraftCommandRootNode<RN>> :
	SuCraftCommandPermissibleNode<RN, RN> {

	var _command: SuCraftCommand<RN>

	override fun getCommand() = _command

	fun withAliases(vararg aliases: String): RN

	fun withFullDescription(fullDescription: String): RN

	override fun withPermission(permission: SuCraftPermission): RN

	fun register()

}