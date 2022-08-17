/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.permission

import org.bukkit.Bukkit
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionDefault

const val suCraftPermissionPrefix = "sucraft."

/**
 * Represents a SuCraft permission and its properties.
 *
 * The actual [permission string][key] will be prefixed with [suCraftPermissionPrefix].
 */
class SuCraftPermission(
	unprefixedKey: String,
	val description: String?,
	val default: PermissionDefault
) {

	var isRegistered = false
		private set

	val key by lazy {
		"$suCraftPermissionPrefix$unprefixedKey"
	}

	private lateinit var bukkitPermission: Permission

	fun register() {
		if (isRegistered) return
		bukkitPermission = Bukkit.getPluginManager().getPermission(key) ?: Permission(
			key,
			description,
			default
		).also(Bukkit.getPluginManager()::addPermission)
		isRegistered = true
	}

}