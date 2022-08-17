/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.worldguard

import org.bukkit.Bukkit

const val worldGuardPluginName = "WorldGuard"

val isWorldGuardLoaded
	get() =
		Bukkit.getPluginManager().getPlugin(worldGuardPluginName) != null

val worldGuardInstance
	get() =
		Bukkit.getPluginManager().getPlugin(worldGuardPluginName) as com.sk89q.worldguard.bukkit.WorldGuardPlugin