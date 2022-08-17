/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.worldguard

import org.bukkit.World

/**
 * The WorldGuard region manager for this world, or null if WorldGuard is not loaded.
 */
val World.worldGuardRegionManager
	get() =
		if (isWorldGuardLoaded)
			com.sk89q.worldguard.WorldGuard.getInstance()
				.platform
				.regionContainer
				.get(com.sk89q.worldedit.bukkit.BukkitAdapter.adapt(this))!!
		else
			null