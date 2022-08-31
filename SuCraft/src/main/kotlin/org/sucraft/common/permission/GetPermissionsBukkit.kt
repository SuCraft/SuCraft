/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.permission

import com.platymuus.bukkit.permissions.PermissionsPlugin
import org.bukkit.Bukkit

const val permissionsBukkitPluginName = "PermissionsBukkit"

val isPermissionsBukkitLoaded
	get() =
		Bukkit.getPluginManager().getPlugin(permissionsBukkitPluginName) != null

val permissionsBukkitInstance
	get() =
		Bukkit.getPluginManager().getPlugin(permissionsBukkitPluginName) as PermissionsPlugin