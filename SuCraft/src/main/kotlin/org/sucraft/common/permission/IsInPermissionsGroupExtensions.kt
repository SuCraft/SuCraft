/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.permission

import com.platymuus.bukkit.permissions.Group
import org.bukkit.entity.Player

fun Player.isInPermissionsGroup(groupName: String) =
	permissionsBukkitInstance.getPlayerInfo(uniqueId)?.groups?.any { it.name == groupName } ?: false

fun Player.isInPermissionsGroup(group: Group) =
	permissionsBukkitInstance.getPlayerInfo(uniqueId)?.groups?.let { group in it } ?: false