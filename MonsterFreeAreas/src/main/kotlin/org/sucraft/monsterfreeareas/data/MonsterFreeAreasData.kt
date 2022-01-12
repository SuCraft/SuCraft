/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.monsterfreeareas.data

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldguard.WorldGuard
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.EntityType.*
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.*


object MonsterFreeAreasData {

	// Settings

	// TODO MUST POTENTIALLY BE UPDATED EVERY VERSION
	fun isTypeThatIsUndesirableToSpawnInCities(type: EntityType) =
		when (type) {
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
			WOLF -> true
			else -> false
		}

	// TODO MUST POTENTIALLY BE UPDATED EVERY VERSION
	fun isSpawnReasonCertainlyUndesirableIfMonsterInCity(spawnReason: CreatureSpawnEvent.SpawnReason)  =
		when (spawnReason) {
			JOCKEY,
			CreatureSpawnEvent.SpawnReason.LIGHTNING,
			MOUNT,
			NATURAL,
			REINFORCEMENTS,
			VILLAGE_INVASION,
			PATROL,
			RAID,
			VILLAGE_DEFENSE -> true
			else -> false
		}

	const val minimumYForSpawnToBeUndesirableUnlessDeepOceanHostile = 45

	// TODO MUST POTENTIALLY BE UPDATED EVERY VERSION
	fun isDeepOceanHostileType(type: EntityType) = type == EntityType.DROWNED

	const val cityRegionIdSuffix = "city"
	const val gothaRegionId = "z_gotha"
	const val farmRegionIdSuffix = "farm"

	// Implementation

	fun isInCityButNotInFarm(location: Location): Boolean {
		val worldGuardRegionManager = WorldGuard.getInstance().platform.regionContainer.get(BukkitAdapter.adapt(location.world))!!
		val applicableRegions = worldGuardRegionManager.getApplicableRegions(BlockVector3.at(location.x, location.y, location.z))
		// Check that the location is in a city region
		if (!applicableRegions.any { it.id.endsWith(cityRegionIdSuffix) || it.id.equals(gothaRegionId, ignoreCase = true) }) return false
		// Check that the location is not in a farm region
		if (applicableRegions.any { it.id.endsWith(farmRegionIdSuffix) }) return false
		return true
	}

}