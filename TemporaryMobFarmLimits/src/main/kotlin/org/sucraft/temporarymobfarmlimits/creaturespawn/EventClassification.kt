/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.temporarymobfarmlimits.creaturespawn

import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.*


object EventClassification {

	@Suppress("DEPRECATION")
	fun isSpawnReasonThatCouldBeMobFarm(reason: CreatureSpawnEvent.SpawnReason) =
		when (reason) {
			DEFAULT,
			DISPENSE_EGG,
			EGG,
			LIGHTNING,
			NATURAL,
			NETHER_PORTAL,
			PATROL,
			RAID,
			REINFORCEMENTS,
			SPAWNER,
			VILLAGE_DEFENSE -> true
			BEEHIVE,
			BREEDING,
			BUILD_IRONGOLEM,
			BUILD_SNOWMAN,
			BUILD_WITHER,
			CHUNK_GEN,
			COMMAND,
			CURED,
			CUSTOM,
			DROWNED,
			ENDER_PEARL,
			EXPLOSION,
			FROZEN,
			INFECTION,
			JOCKEY,
			MOUNT,
			OCELOT_BABY,
			PIGLIN_ZOMBIFIED,
			SHEARED,
			SHOULDER_ENTITY,
			SILVERFISH_BLOCK,
			SLIME_SPLIT,
			SPAWNER_EGG,
			SPELL,
			TRAP,
			VILLAGE_INVASION -> false
		}

}