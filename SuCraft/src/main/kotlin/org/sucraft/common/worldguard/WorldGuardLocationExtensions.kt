/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.worldguard

import org.bukkit.Location


/**
 * The WorldGuard region manager for this location, or null if WorldGuard is not loaded.
 */
val Location.worldGuardRegionManager get() = world.worldGuardRegionManager

/**
 * The WorldGuard block vector of this location, or null if WorldGuard is not loaded.
 */
val Location.worldGuardBlockVector
	get() =
		if (isWorldGuardLoaded)
			com.sk89q.worldedit.math.BlockVector3.at(x, y, z)
		else
			null

/**
 * The applicable WorldGuard regions for this location, or null if WorldGuard is not loaded.
 */
val Location.applicableWorldGuardRegions
	get() =
		worldGuardRegionManager?.getApplicableRegions(worldGuardBlockVector)