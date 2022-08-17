/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.permission

import org.bukkit.permissions.Permissible

fun Permissible.hasPermission(permission: SuCraftPermission) =
	hasPermission(permission.key)