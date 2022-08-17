/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.monsterfreeareas

import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.EntityType.*
import org.bukkit.event.EventPriority.HIGHEST
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.*
import org.sucraft.common.event.on
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.worldguard.applicableWorldGuardRegions
import org.sucraft.common.worldguard.isWorldGuardLoaded

/**
 * Prevents monsters from spawning in certain areas, like towns.
 */
object MonsterFreeAreas : SuCraftModule<MonsterFreeAreas>() {

	// Settings

	/**
	 * If true, entities of this type can always spawn in monster-free areas, no matter the spawn reason.
	 *
	 * TODO MUST POTENTIALLY BE UPDATED EVERY VERSION
	 */
	private val EntityType.canAlwaysSpawnInMonsterFreeAreas
		get() =
			when (this) {
				ENDERMITE,
				RAVAGER,
				SHULKER,
				VINDICATOR,
				VEX,
				ZOMBIE_VILLAGER,
				ILLUSIONER,
				ZOMBIE_HORSE,
				SKELETON_HORSE,
				HUSK,
				PHANTOM,
				STRAY,
				EVOKER,
				EntityType.DROWNED,
				BLAZE,
				CAVE_SPIDER,
				SPIDER,
				ZOMBIE,
				SKELETON,
				GHAST,
				MAGMA_CUBE,
				CREEPER,
				ZOMBIFIED_PIGLIN,
				ENDER_DRAGON,
				GIANT,
				SILVERFISH,
				IRON_GOLEM,
				WITCH,
				ENDERMAN,
				WITHER,
				PILLAGER,
				WOLF,
				WARDEN -> false
				else -> true
			}

	/**
	 * If true, entities can always spawn in monster-free areas for this spawn reason, no matter their type.
	 *
	 * TODO MUST POTENTIALLY BE UPDATED EVERY VERSION
	 */
	private val SpawnReason.canAlwaysSpawnInMonsterFreeAreas
		get() = when (this) {
			JOCKEY,
			SpawnReason.LIGHTNING,
			MOUNT,
			NATURAL,
			REINFORCEMENTS,
			VILLAGE_INVASION,
			PATROL,
			RAID,
			VILLAGE_DEFENSE -> false
			else -> true
		}

	/**
	 * Entities spawns are only blocked in monster-free areas when above this y,
	 * unless [isDeepOceanHostile] is true.
	 */
	private const val minimumYForBlockedSpawnsUnlessDeepOceanHostile = 45

	/**
	 * If true, entities of this type cannot spawn even below [minimumYForBlockedSpawnsUnlessDeepOceanHostile]
	 *
	 * TODO MUST POTENTIALLY BE UPDATED EVERY VERSION
	 */
	private val EntityType.isDeepOceanHostile get() = this == EntityType.DROWNED

	/**
	 * Locations inside WorldGuard regions with an id that ends with this suffix will count as monster-free, unless
	 * also inside a [farm][farmRegionIdSuffix].
	 */
	private const val townRegionIdSuffix = "city"

	/**
	 * Locations inside WorldGuard regions with this id will count as monster-free, unless
	 * also inside a [farm][farmRegionIdSuffix].
	 */
	private const val gothaRegionId = "z_gotha"

	/**
	 * Locations inside WorldGuard regions with an id that ends with this suffix will not count as monster-free,
	 * even if the location is inside a [town][townRegionIdSuffix] or inside [Gotha][gothaRegionId].
	 */
	private const val farmRegionIdSuffix = "farm"

	// Check for WorldGuard

	override fun onInitialize() {
		super.onInitialize()
		// Check for WorldGuard
		if (!isWorldGuardLoaded) {
			warning("Could not find WorldGuard plugin: preventing monster spawns in towns may not work")
		}
	}

	// Events

	init {
		// Listen for creature spawns to cancel them if they are not desired
		on(CreatureSpawnEvent::class.java, priority = HIGHEST) {

			// We cannot check the region of the location if WorldGuard is not loaded
			if (!isWorldGuardLoaded) return@on

			// Check that we want to prevent spawns of this entity type
			if (entityType.canAlwaysSpawnInMonsterFreeAreas) return@on
			// Check that we want to prevent spawns that happened for this reason
			if (spawnReason.canAlwaysSpawnInMonsterFreeAreas) return@on

			// Only block spawns above a certain minimum y,
			// except for those that spawn in the deep ocean: those will be blocked at all heights
			if (
				location.y < minimumYForBlockedSpawnsUnlessDeepOceanHostile
				&& !entityType.isDeepOceanHostile
			) return@on

			// Cancel the spawn if this is in a town but not in a farm
			if (location.isInTownButNotInFarm) isCancelled = true

		}
	}

	// Implementation

	/**
	 * Returns whether this location is in a town, but not in a farm
	 * (and thus monster-free).
	 *
	 * @throws IllegalStateException If WorldGuard is not loaded.
	 */
	private val Location.isInTownButNotInFarm
		@Throws(IllegalStateException::class) get() =
			if (isWorldGuardLoaded)
				applicableWorldGuardRegions!!.run {
					// Check that the location is in a town
					any {
						it.id.endsWith(townRegionIdSuffix, ignoreCase = true) ||
								it.id.equals(gothaRegionId, ignoreCase = true)
						// Check that the location is not in a farm
					} && none { it.id.endsWith(farmRegionIdSuffix, ignoreCase = true) }
				}
			else throw IllegalStateException("Called Location.isInTownButNotInFarm while WorldGuard is not loaded")

}