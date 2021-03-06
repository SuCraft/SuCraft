/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.antilag.performanceadapter

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.animalEntityActivationRange
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.areShearedSheepImmune
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.armorStandCollisionLookups
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.axolotlTargetingFishImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.axolotlTargetingSquidImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.babyImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.catTargetingRabbitImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.containerUpdateTickRate
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.disableIceAndSnow
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.disablePillagerPatrols
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.endermanTargetingEndermiteImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.experienceMergeRadius
/*
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.fartherViewDistanceMaxChunksGeneratedPerTick
*/
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.flyingMonsterEntityActivationRange
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.foxTargetingChickenImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.globalMaxConcurrentChunkLoads
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.grassSpreadTickRate
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.guardianTargetingSquidImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.itemDespawnRate
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.itemMergeRadius
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.llamaTargetingWolfImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.maxAutoSaveChunksPerTick
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.maxEntityCollisions
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.maxPrimedTNT
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.maxTrackViewDistance
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.miscEntityActivationRange
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.mobFarmFactor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.mobSpawnChunkRange
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.mobSpawnerTickRate
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.mobTargetingTurtleImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.monsterEntityActivationRange
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.ocelotTargetingChickenImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.piglinTargetingHoglinImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.piglinTargetingWitherSkeletonImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.pistonDuplicationEnabled
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.polarBearTargetingFoxImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.raiderEntityActivationRange
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.simulationDistance
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.skippedActiveEntityRatio
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.snowGolemTargetingNonPlayerImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.tickArmorStands
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.tickInactiveVillagers
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.ticksPerAmbientSpawns
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.ticksPerAnimalSpawns
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.ticksPerMonsterSpawns
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.ticksPerWaterAmbientSpawns
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.ticksPerWaterAnimalSpawns
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.ticksPerWaterUndergroundCreatureSpawns
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.updatePathfindingOnBlockUpdate
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.villagerEntityActivationRange
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.wakeUpInactiveAnimalsPerTick
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.wakeUpInactiveFlyingMonstersPerTick
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.wakeUpInactiveMonstersPerTick
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.wakeUpInactiveVillagersPerTick
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.waterEntityActivationRange
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.witchTargetingWitchImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.witherSkeletonTargetingIronGolemImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.witherSkeletonTargetingPiglinImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.witherSkeletonTargetingSnowGolemImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.wolfTargetingFoxImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.wolfTargetingSheepImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.zoglinTargetingNonPlayerImmunityFor
import org.sucraft.antilag.performanceadapter.PerformanceSetting.Companion.zombifiedPiglinPortalSpawns

enum class PerformanceMode(

	val displayName: String,
	val tpsMinima: TPSMinima,

	vararg settingValues: SettingValue<*>

) {

	/**
	 * Increase some performance values as a bonus even more than [BONUS]
	 */
	BONUS_PLUS(

		displayName = "Best",//"Bonus+",
		tpsMinima = TPSMinima(
			19.99,
			19.85
		),

		SettingValue(mobFarmFactor, 1.0),

		SettingValue(simulationDistance, 8),
		SettingValue(maxTrackViewDistance, 12),

		/*
		SettingValue(fartherViewDistanceMaxChunksGeneratedPerTick, 2),
		*/

		SettingValue(monsterEntityActivationRange, 32),
		SettingValue(animalEntityActivationRange, 32),
		SettingValue(villagerEntityActivationRange, 32),
		SettingValue(raiderEntityActivationRange, 80),
		SettingValue(waterEntityActivationRange, 32),
		SettingValue(flyingMonsterEntityActivationRange, 90),
		SettingValue(miscEntityActivationRange, 16),

		SettingValue(wakeUpInactiveMonstersPerTick, 8),
		SettingValue(wakeUpInactiveFlyingMonstersPerTick, 12),

		SettingValue(skippedActiveEntityRatio, Pair(0, 1)), // 0

		SettingValue(mobSpawnChunkRange, 6),

		SettingValue(globalMaxConcurrentChunkLoads, 700.0),
		SettingValue(maxAutoSaveChunksPerTick, 40),

		SettingValue(itemMergeRadius, 1.0),
		SettingValue(experienceMergeRadius, 0.3),
		SettingValue(itemDespawnRate, 18000)

	),

	/**
	 * Increase some performance values as a bonus
	 */
	BONUS(

		displayName = "Excellent",//"Bonus",
		tpsMinima = TPSMinima(
			19.98,
			19.7
		),

		SettingValue(mobFarmFactor, 0.35),

		SettingValue(monsterEntityActivationRange, 29),
		SettingValue(animalEntityActivationRange, 24),
		SettingValue(villagerEntityActivationRange, 26),
		SettingValue(raiderEntityActivationRange, 64),
		SettingValue(waterEntityActivationRange, 24),
		SettingValue(flyingMonsterEntityActivationRange, 64),
		SettingValue(miscEntityActivationRange, 12),

		SettingValue(wakeUpInactiveMonstersPerTick, 6),
		SettingValue(wakeUpInactiveAnimalsPerTick, 4),
		SettingValue(wakeUpInactiveVillagersPerTick, 4),
		SettingValue(wakeUpInactiveFlyingMonstersPerTick, 10),

		SettingValue(skippedActiveEntityRatio, Pair(1, 4)), // 0.25

		SettingValue(tickInactiveVillagers, true),

		SettingValue(mobSpawnChunkRange, 5),

		SettingValue(globalMaxConcurrentChunkLoads, 475.0),
		SettingValue(maxAutoSaveChunksPerTick, 32),

		SettingValue(itemMergeRadius, 1.5),
		SettingValue(experienceMergeRadius, 0.7),
		SettingValue(itemDespawnRate, 15000)

	),

	/**
	 * Same as [REGULAR] but with farms enabled that may have excessive performance hit
	 */
	REGULAR_PLUS_FARMS(

		displayName = "Great",//"Regular + farms",
		tpsMinima = TPSMinima(
			19.96,
			19.5
		),

		SettingValue(mobFarmFactor, 0.1),
		SettingValue(pistonDuplicationEnabled, true),

		SettingValue(monsterEntityActivationRange, 26),
		SettingValue(animalEntityActivationRange, 20),
		SettingValue(villagerEntityActivationRange, 23),
		SettingValue(raiderEntityActivationRange, 60),
		SettingValue(waterEntityActivationRange, 20),
		SettingValue(flyingMonsterEntityActivationRange, 60),
		SettingValue(miscEntityActivationRange, 10),

		SettingValue(wakeUpInactiveMonstersPerTick, 5),
		SettingValue(wakeUpInactiveAnimalsPerTick, 3),
		SettingValue(wakeUpInactiveVillagersPerTick, 3),
		SettingValue(wakeUpInactiveFlyingMonstersPerTick, 8),

		SettingValue(skippedActiveEntityRatio, Pair(2, 7)), // ~ 0.2857
		
		SettingValue(guardianTargetingSquidImmunityFor, 20),
		SettingValue(snowGolemTargetingNonPlayerImmunityFor, 20),
		SettingValue(mobTargetingTurtleImmunityFor, 20),

		SettingValue(ticksPerWaterAnimalSpawns, 1),

		SettingValue(zombifiedPiglinPortalSpawns, true)

	),

	/**
	 * Regular performance, most options to vanilla or default
	 * In comparison to [REGULAR_MINUS] this enables some performance benefits
	 */
	REGULAR(

		displayName = "Very good",//"Regular",
		tpsMinima = TPSMinima(
			19.93,
			19.3
		),

		/*
		SettingValue(fartherViewDistanceMaxChunksGeneratedPerTick, 1),
		*/

		SettingValue(simulationDistance, 7),

		SettingValue(monsterEntityActivationRange, 24),
		SettingValue(animalEntityActivationRange, 16),
		SettingValue(villagerEntityActivationRange, 20),
		SettingValue(raiderEntityActivationRange, 56),
		SettingValue(waterEntityActivationRange, 16),
		SettingValue(flyingMonsterEntityActivationRange, 56),
		SettingValue(miscEntityActivationRange, 8),

		SettingValue(wakeUpInactiveFlyingMonstersPerTick, 7),

		SettingValue(skippedActiveEntityRatio, Pair(1, 3)), // ~ 0.3333
		
		SettingValue(endermanTargetingEndermiteImmunityFor, 20),

		SettingValue(ticksPerMonsterSpawns, 1),
		SettingValue(ticksPerWaterAnimalSpawns, 2),
		SettingValue(ticksPerWaterAmbientSpawns, 1),
		SettingValue(ticksPerWaterUndergroundCreatureSpawns, 1),
		SettingValue(ticksPerAmbientSpawns, 1),

		SettingValue(globalMaxConcurrentChunkLoads, 400.0),

		SettingValue(grassSpreadTickRate, 1),

		SettingValue(maxPrimedTNT, 50),

		SettingValue(maxEntityCollisions, 8),

		SettingValue(experienceMergeRadius, 1.1),
		SettingValue(itemDespawnRate, 12000)

	),

	/**
	 * This sets most of the normal options to their [REGULAR] setting, but does not enable some unnecessary benefits yet
	 */
	REGULAR_MINUS(

		displayName = "Good",//"Regular-",
		tpsMinima = TPSMinima(
			19.9,
			19.0
		),

		SettingValue(maxTrackViewDistance, 11),

		SettingValue(monsterEntityActivationRange, 22),
		SettingValue(animalEntityActivationRange, 15),
		SettingValue(villagerEntityActivationRange, 18),
		SettingValue(raiderEntityActivationRange, 50),
		SettingValue(waterEntityActivationRange, 15),

		SettingValue(wakeUpInactiveFlyingMonstersPerTick, 6),

		SettingValue(skippedActiveEntityRatio, Pair(3, 8)), // 0.375

		SettingValue(piglinTargetingWitherSkeletonImmunityFor, 20),
		SettingValue(witherSkeletonTargetingPiglinImmunityFor, 20),
		SettingValue(witherSkeletonTargetingIronGolemImmunityFor, 20),
		SettingValue(witherSkeletonTargetingSnowGolemImmunityFor, 20),

		SettingValue(ticksPerMonsterSpawns, 2),
		SettingValue(ticksPerWaterAnimalSpawns, 3),
		SettingValue(ticksPerWaterAmbientSpawns, 2),
		SettingValue(ticksPerWaterUndergroundCreatureSpawns, 2),
		SettingValue(ticksPerAmbientSpawns, 2),

		SettingValue(mobSpawnerTickRate, 1),

		SettingValue(globalMaxConcurrentChunkLoads, 360.0),

		SettingValue(containerUpdateTickRate, 1),
		SettingValue(grassSpreadTickRate, 2),

		SettingValue(maxPrimedTNT, 40),

		SettingValue(maxEntityCollisions, 6),

		SettingValue(itemMergeRadius, 2.0),
		SettingValue(experienceMergeRadius, 1.5),
		SettingValue(itemDespawnRate, 9000)

	),

	/**
	 * This is okay performance, not as desired but very acceptable and hard to notice something is wrong
	 */
	FINE(

		displayName = "Good-medium",//"Fine",
		tpsMinima = TPSMinima(
			19.85,
			18.0
		),

		SettingValue(simulationDistance, 6),
		SettingValue(maxTrackViewDistance, 10),

		SettingValue(monsterEntityActivationRange, 20),
		SettingValue(animalEntityActivationRange, 14),
		SettingValue(villagerEntityActivationRange, 16),
		SettingValue(raiderEntityActivationRange, 45),
		SettingValue(waterEntityActivationRange, 14),

		SettingValue(wakeUpInactiveMonstersPerTick, 4),
		SettingValue(wakeUpInactiveFlyingMonstersPerTick, 5),

		SettingValue(skippedActiveEntityRatio, Pair(2, 5)), // 0.4

		SettingValue(wakeUpInactiveMonstersPerTick, 8),
		SettingValue(wakeUpInactiveAnimalsPerTick, 4),
		SettingValue(wakeUpInactiveVillagersPerTick, 4),
		SettingValue(wakeUpInactiveFlyingMonstersPerTick, 8),

		SettingValue(ticksPerMonsterSpawns, 3),
		SettingValue(ticksPerWaterAnimalSpawns, 8),
		SettingValue(ticksPerWaterAmbientSpawns, 6),
		SettingValue(ticksPerWaterUndergroundCreatureSpawns, 3),
		SettingValue(ticksPerAmbientSpawns, 3),

		SettingValue(globalMaxConcurrentChunkLoads, 320.0),

		SettingValue(grassSpreadTickRate, 3),

		SettingValue(maxPrimedTNT, 30),

		SettingValue(maxEntityCollisions, 4),

		SettingValue(itemMergeRadius, 2.5),
		SettingValue(experienceMergeRadius, 2.25)

	),

	/**
	 * This is barely okay performance, definitely not as desired but still acceptable
	 */
	BARELY_FINE(

		displayName = "Medium",//"Barely fine",
		tpsMinima = TPSMinima(
			19.8,
			17.0
		),

		SettingValue(maxTrackViewDistance, 9),

		SettingValue(monsterEntityActivationRange, 18),
		SettingValue(animalEntityActivationRange, 13),
		SettingValue(villagerEntityActivationRange, 14),
		SettingValue(raiderEntityActivationRange, 40),
		SettingValue(waterEntityActivationRange, 13),
		SettingValue(flyingMonsterEntityActivationRange, 48),
		SettingValue(miscEntityActivationRange, 7),

		SettingValue(wakeUpInactiveMonstersPerTick, 3),
		SettingValue(wakeUpInactiveAnimalsPerTick, 2),
		SettingValue(wakeUpInactiveVillagersPerTick, 2),
		SettingValue(wakeUpInactiveFlyingMonstersPerTick, 4),

		SettingValue(skippedActiveEntityRatio, Pair(3, 7)), // ~ 0.4286

		SettingValue(babyImmunityFor, 5),
		SettingValue(areShearedSheepImmune, true),

		SettingValue(wolfTargetingSheepImmunityFor, 20),
		SettingValue(wolfTargetingFoxImmunityFor, 20),
		SettingValue(llamaTargetingWolfImmunityFor, 20),
		SettingValue(polarBearTargetingFoxImmunityFor, 20),
		SettingValue(foxTargetingChickenImmunityFor, 20),
		SettingValue(ocelotTargetingChickenImmunityFor, 20),
		SettingValue(catTargetingRabbitImmunityFor, 20),
		SettingValue(piglinTargetingHoglinImmunityFor, 20),
		SettingValue(zoglinTargetingNonPlayerImmunityFor, 20),
		SettingValue(axolotlTargetingFishImmunityFor, 20),
		SettingValue(axolotlTargetingSquidImmunityFor, 20),
		SettingValue(witchTargetingWitchImmunityFor, 20),

		SettingValue(ticksPerMonsterSpawns, 4),
		SettingValue(ticksPerWaterAnimalSpawns, 12),
		SettingValue(ticksPerWaterAmbientSpawns, 8),
		SettingValue(ticksPerWaterUndergroundCreatureSpawns, 4),
		SettingValue(ticksPerAmbientSpawns, 4),

		SettingValue(mobSpawnerTickRate, 2),

		SettingValue(globalMaxConcurrentChunkLoads, 280.0),
		SettingValue(maxAutoSaveChunksPerTick, 24),

		SettingValue(disableIceAndSnow, false),
		SettingValue(tickArmorStands, true),
		SettingValue(containerUpdateTickRate, 2),
		SettingValue(grassSpreadTickRate, 4),
		SettingValue(updatePathfindingOnBlockUpdate, true),

		SettingValue(maxPrimedTNT, 20),

		SettingValue(maxEntityCollisions, 3),

		SettingValue(itemMergeRadius, 3.0),
		SettingValue(experienceMergeRadius, 3.25)

	),

	/**
	 * This keeps the simulation distance at 5, so as to not break most redstone contraptions
	 * (This is the last reasonable setting, below this becomes noticeable limited performance)
	 * All these values should be what is reasonable for them to be without breaking redstone
	 */
	MINIMUM_NON_REDSTONE_BREAKING(

		displayName = "Medium-low",//"Minimum non-redstone-breaking",
		tpsMinima = TPSMinima(
			19.5,
			10.0
		),

		SettingValue(simulationDistance, 5),
		SettingValue(maxTrackViewDistance, 8),

		SettingValue(monsterEntityActivationRange, 16),
		SettingValue(animalEntityActivationRange, 10),
		SettingValue(villagerEntityActivationRange, 12),
		SettingValue(raiderEntityActivationRange, 30),
		SettingValue(waterEntityActivationRange, 10),
		SettingValue(flyingMonsterEntityActivationRange, 36),
		SettingValue(miscEntityActivationRange, 6),

		SettingValue(wakeUpInactiveFlyingMonstersPerTick, 3),

		SettingValue(skippedActiveEntityRatio, Pair(1, 2)), // ~ 0.5

		SettingValue(ticksPerMonsterSpawns, 6),
		SettingValue(ticksPerAnimalSpawns, 400),
		SettingValue(ticksPerWaterAnimalSpawns, 16),
		SettingValue(ticksPerWaterAmbientSpawns, 10),
		SettingValue(ticksPerWaterUndergroundCreatureSpawns, 6),
		SettingValue(ticksPerAmbientSpawns, 6),

		SettingValue(globalMaxConcurrentChunkLoads, 250.0),
		SettingValue(maxAutoSaveChunksPerTick, 22),

		SettingValue(maxPrimedTNT, 10),

		SettingValue(experienceMergeRadius, 4.25)

	),

	/**
	 * This is quite poor performance, we don't want this
	 */
	POOR(

		displayName = "Low",//"Poor",
		tpsMinima = TPSMinima(
			19.0,
			5.0,
		),

		SettingValue(simulationDistance, 4),
		SettingValue(maxTrackViewDistance, 7),

		SettingValue(monsterEntityActivationRange, 14),
		SettingValue(animalEntityActivationRange, 8),
		SettingValue(villagerEntityActivationRange, 12),
		SettingValue(raiderEntityActivationRange, 28),
		SettingValue(waterEntityActivationRange, 8),
		SettingValue(flyingMonsterEntityActivationRange, 32),

		SettingValue(wakeUpInactiveMonstersPerTick, 2),
		SettingValue(wakeUpInactiveFlyingMonstersPerTick, 2),

		SettingValue(skippedActiveEntityRatio, Pair(5, 9)), // ~ 0.5556

		SettingValue(ticksPerMonsterSpawns, 10),
		SettingValue(ticksPerWaterAnimalSpawns, 24),
		SettingValue(ticksPerWaterAmbientSpawns, 16),
		SettingValue(ticksPerWaterUndergroundCreatureSpawns, 10),
		SettingValue(ticksPerAmbientSpawns, 10),

		SettingValue(globalMaxConcurrentChunkLoads, 220.0),
		SettingValue(maxAutoSaveChunksPerTick, 20),

		SettingValue(grassSpreadTickRate, 5),

		SettingValue(maxPrimedTNT, 8),

		SettingValue(armorStandCollisionLookups, true),

		SettingValue(itemMergeRadius, 3.5),
		SettingValue(experienceMergeRadius, 5.0)

	),

	/**
	 * This is very poor performance, we really don't want this
	 */
	VERY_POOR(

		displayName = "Very low",//"Very poor",
		tpsMinima = TPSMinima(
			18.0,
			0.0,
		),

		SettingValue(simulationDistance, 3),
		SettingValue(maxTrackViewDistance, 6),

		SettingValue(monsterEntityActivationRange, 13),
		SettingValue(animalEntityActivationRange, 7),
		SettingValue(villagerEntityActivationRange, 10),
		SettingValue(raiderEntityActivationRange, 24),
		SettingValue(waterEntityActivationRange, 7),
		SettingValue(flyingMonsterEntityActivationRange, 28),
		SettingValue(miscEntityActivationRange, 5),

		SettingValue(skippedActiveEntityRatio, Pair(3, 5)), // ~ 0.6

		SettingValue(ticksPerMonsterSpawns, 20),
		SettingValue(ticksPerAnimalSpawns, 800),
		SettingValue(ticksPerWaterAnimalSpawns, 48),
		SettingValue(ticksPerWaterAmbientSpawns, 36),
		SettingValue(ticksPerWaterUndergroundCreatureSpawns, 20),
		SettingValue(ticksPerAmbientSpawns, 20),

		SettingValue(mobSpawnerTickRate, 4),

		SettingValue(globalMaxConcurrentChunkLoads, 190.0),
		SettingValue(maxAutoSaveChunksPerTick, 18),

		SettingValue(containerUpdateTickRate, 3),
		SettingValue(grassSpreadTickRate, 9),
		SettingValue(disablePillagerPatrols, false),

		SettingValue(maxPrimedTNT, 6),

		SettingValue(maxEntityCollisions, 2),

		SettingValue(itemMergeRadius, 4.0),
		SettingValue(experienceMergeRadius, 6.0),
		SettingValue(itemDespawnRate, 6000)

	),

	/**
	 * This is the absolute minimum the server can run at
	 */
	MINIMUM(

		displayName = "Lowest",//"Minimum",
		tpsMinima = TPSMinima(
			0.0,
			0.0
		),

		SettingValue(mobFarmFactor, 0.0),
		SettingValue(pistonDuplicationEnabled, false),

		/*
		SettingValue(fartherViewDistanceMaxChunksGeneratedPerTick, 0),
		*/

		SettingValue(simulationDistance, 2),
		SettingValue(maxTrackViewDistance, 5),

		SettingValue(monsterEntityActivationRange, 12),
		SettingValue(animalEntityActivationRange, 6),
		SettingValue(villagerEntityActivationRange, 9),
		SettingValue(raiderEntityActivationRange, 20),
		SettingValue(waterEntityActivationRange, 6),
		SettingValue(flyingMonsterEntityActivationRange, 24),
		SettingValue(miscEntityActivationRange, 4),

		SettingValue(wakeUpInactiveMonstersPerTick, 1),
		SettingValue(wakeUpInactiveAnimalsPerTick, 1),
		SettingValue(wakeUpInactiveVillagersPerTick, 1),
		SettingValue(wakeUpInactiveFlyingMonstersPerTick, 1),

		SettingValue(skippedActiveEntityRatio, Pair(2, 3)), // ~ 0.6667

		SettingValue(tickInactiveVillagers, false),
		SettingValue(babyImmunityFor, -1),
		SettingValue(areShearedSheepImmune, false),

		SettingValue(guardianTargetingSquidImmunityFor, -1),
		SettingValue(endermanTargetingEndermiteImmunityFor, -1),
		SettingValue(piglinTargetingWitherSkeletonImmunityFor, -1),
		SettingValue(witherSkeletonTargetingPiglinImmunityFor, -1),
		SettingValue(witherSkeletonTargetingIronGolemImmunityFor, -1),
		SettingValue(witherSkeletonTargetingSnowGolemImmunityFor, -1),
		SettingValue(snowGolemTargetingNonPlayerImmunityFor, -1),
		SettingValue(mobTargetingTurtleImmunityFor, -1),

		SettingValue(wolfTargetingSheepImmunityFor, -1),
		SettingValue(wolfTargetingFoxImmunityFor, -1),
		SettingValue(llamaTargetingWolfImmunityFor, -1),
		SettingValue(polarBearTargetingFoxImmunityFor, -1),
		SettingValue(foxTargetingChickenImmunityFor, -1),
		SettingValue(ocelotTargetingChickenImmunityFor, -1),
		SettingValue(catTargetingRabbitImmunityFor, -1),
		SettingValue(piglinTargetingHoglinImmunityFor, -1),
		SettingValue(zoglinTargetingNonPlayerImmunityFor, -1),
		SettingValue(axolotlTargetingFishImmunityFor, -1),
		SettingValue(axolotlTargetingSquidImmunityFor, -1),
		SettingValue(witchTargetingWitchImmunityFor, -1),

		SettingValue(ticksPerMonsterSpawns, 40),
		SettingValue(ticksPerAnimalSpawns, 1200),
		SettingValue(ticksPerWaterAnimalSpawns, 64),
		SettingValue(ticksPerWaterAmbientSpawns, 50),
		SettingValue(ticksPerWaterUndergroundCreatureSpawns, 40),
		SettingValue(ticksPerAmbientSpawns, 40),

		SettingValue(mobSpawnChunkRange, 4),

		SettingValue(mobSpawnerTickRate, 5),

		SettingValue(globalMaxConcurrentChunkLoads, 160.0),
		SettingValue(maxAutoSaveChunksPerTick, 16),

		SettingValue(disableIceAndSnow, true),
		SettingValue(tickArmorStands, false),
		SettingValue(containerUpdateTickRate, 4),
		SettingValue(grassSpreadTickRate, 15),
		SettingValue(updatePathfindingOnBlockUpdate, false),
		SettingValue(disablePillagerPatrols, true),
		SettingValue(zombifiedPiglinPortalSpawns, false),

		SettingValue(maxPrimedTNT, 4),

		SettingValue(maxEntityCollisions, 1),
		SettingValue(armorStandCollisionLookups, false),

		SettingValue(itemMergeRadius, 5.0),
		SettingValue(experienceMergeRadius, 10.0),
		SettingValue(itemDespawnRate, 4500)

	);

	private val valuesBySetting: Int2ObjectOpenHashMap<*> = Int2ObjectOpenHashMap(settingValues.asSequence().map { Pair(it.setting.index, it.value) }.toMap())

	fun <T> getSettingValue(setting: PerformanceSetting<T>): T =
		if (setting.index in valuesBySetting)
			@Suppress("UNCHECKED_CAST")
			valuesBySetting[setting.index] as T
		else
			getLowerMode().getSettingValue(setting)

	fun getLowerMode() = values()[ordinal + 1]

	fun getHigherMode() = values()[ordinal - 1]

	fun orLower() = sequence {
		var mode = this@PerformanceMode
		while (true) {
			yield(mode)
			if (mode == lowest) break
			mode = mode.getLowerMode()
		}
	}

	fun orHigher() = sequence {
		var mode = this@PerformanceMode
		while (true) {
			yield(mode)
			if (mode == highest) break
			mode = mode.getHigherMode()
		}
	}

	fun <T> applySettingValue(setting: PerformanceSetting<T>) =
		setting.set(getSettingValue(setting))

	fun <T> applySettingValueIfDiffering(setting: PerformanceSetting<T>, otherValue: T) =
		getSettingValue(setting).let { if (otherValue != it) setting.set(it) }

	fun <T> applySettingValueIfDifferingWithMode(setting: PerformanceSetting<T>, otherMode: PerformanceMode) =
		applySettingValueIfDiffering(setting, otherMode.getSettingValue(setting))

	fun applyAllSettingValues() =
		PerformanceSetting.values.forEach { applySettingValue(it) }

	fun applyDifferingSettingValues(otherMode: PerformanceMode) =
		PerformanceSetting.values.forEach { applySettingValueIfDifferingWithMode(it, otherMode) }

	companion object {

		val highest = values()[0]
		val lowest = values()[values().size - 1]

	}

}