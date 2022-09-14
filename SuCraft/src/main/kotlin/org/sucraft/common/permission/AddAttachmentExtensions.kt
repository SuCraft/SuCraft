/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.permission

import org.bukkit.entity.Player
import org.sucraft.main.SuCraftPlugin

fun Player.addAttachment() = addAttachment(SuCraftPlugin.instance)

fun Player.addAttachment(permissions: Array<String>) =
	addAttachment().apply {
		permissions.forEach { setPermission(it, true) }
	}

fun Player.addAttachment(permissions: Array<Pair<String, Boolean>>) =
	addAttachment().apply {
		permissions.forEach { (permission, value) -> setPermission(permission, value) }
	}

fun Player.addAttachmentAndUpdateCommands(permissions: Array<String>) =
	addAttachment(permissions).also {
		updateCommands()
	}

fun Player.addAttachmentAndUpdateCommands(permissions: Array<Pair<String, Boolean>>) =
	addAttachment(permissions).also {
		updateCommands()
	}