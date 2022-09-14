/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.performanceadaptation.configuration

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import org.sucraft.modules.performanceadaptation.model.PerformanceSetting
import org.sucraft.modules.performanceadaptation.model.SettingValue
import org.sucraft.modules.performanceadaptation.model.TPSMinima
import org.sucraft.modules.performanceadaptation.model.at

/**
 * An enum of all the possible performance modes.
 *
 * Each performance mode has a [displayName], a pair of minimum TPS for this performance mode (or better) to be used,
 * and a list of values for settings (that overwrite the values of those settings compared to the mode right below).
 */
enum class PerformanceMode(
	val displayName: String,
	val tpsMinima: TPSMinima,
	vararg settingValues: SettingValue<*>
) {

	/**
	 * Increase some performance values as a bonus even more than [BONUS].
	 */
	BONUS_PLUS(

		displayName = "Best",
		tpsMinima = TPSMinima(
			19.9,
			19.7
		),

//		mobFarmFactor at 1.0, // Disabled due to temporarily no mob farm nerfs in 1.19

		simulationDistance at 7,
		maxTrackViewDistance at 12,

		monsterEntityActivationRange at 32,
		animalEntityActivationRange at 32,
		villagerEntityActivationRange at 32,
		raiderEntityActivationRange at 80,
		waterEntityActivationRange at 32,
		flyingMonsterEntityActivationRange at 90,
		miscEntityActivationRange at 16,

		wakeUpInactiveMonstersPerTick at 8,
		wakeUpInactiveFlyingMonstersPerTick at 12,

		skippedActiveEntityRatio at (0 to 1), // 0

		mobSpawnChunkRange at 6,

		globalMaxConcurrentChunkLoads at 700.0,
		maxAutoSaveChunksPerTick at 40,

		itemMergeRadius at 0.5,
		experienceMergeRadius at 0.0,
		itemDespawnRate at 18000

	),

	/**
	 * Increase some performance values as a bonus.
	 */
	BONUS(

		displayName = "Excellent",
		tpsMinima = TPSMinima(
			19.5,
			19.0
		),

//		mobFarmFactor at 0.35, // Disabled due to temporarily no mob farm nerfs in 1.19

		simulationDistance at 6,
		maxTrackViewDistance at 10,

		monsterEntityActivationRange at 29,
		animalEntityActivationRange at 24,
		villagerEntityActivationRange at 26,
		raiderEntityActivationRange at 64,
		waterEntityActivationRange at 24,
		flyingMonsterEntityActivationRange at 64,
		miscEntityActivationRange at 12,

		wakeUpInactiveMonstersPerTick at 6,
		wakeUpInactiveAnimalsPerTick at 4,
		wakeUpInactiveVillagersPerTick at 4,
		wakeUpInactiveFlyingMonstersPerTick at 10,

		skippedActiveEntityRatio at (1 to 4), // 0.25

		tickInactiveVillagers at true,

		mobSpawnChunkRange at 5,

		globalMaxConcurrentChunkLoads at 475.0,
		maxAutoSaveChunksPerTick at 32,

		itemMergeRadius at 1.0,
		experienceMergeRadius at 0.3,
		itemDespawnRate at 15000

	),

	/**
	 * Same as [REGULAR] but with farms enabled that may have excessive performance hit.
	 */
	REGULAR_PLUS_FARMS(

		displayName = "Great",
		tpsMinima = TPSMinima(
			19.0,
			18.0
		),

//		mobFarmFactor at 0.1, // Disabled due to temporarily no mob farm nerfs in 1.19
//		pistonDuplicationEnabled at true, // Disabled temp due to 225624 tree farm trouble

		simulationDistance at 5,
		maxTrackViewDistance at 9,

		monsterEntityActivationRange at 26,
		animalEntityActivationRange at 20,
		villagerEntityActivationRange at 23,
		raiderEntityActivationRange at 60,
		waterEntityActivationRange at 20,
		flyingMonsterEntityActivationRange at 60,
		miscEntityActivationRange at 10,

		wakeUpInactiveMonstersPerTick at 5,
		wakeUpInactiveAnimalsPerTick at 3,
		wakeUpInactiveVillagersPerTick at 3,
		wakeUpInactiveFlyingMonstersPerTick at 8,

		skippedActiveEntityRatio at (2 to 7), // ~ 0.2857

		guardianTargetingSquidImmunityFor at 20,
		snowGolemTargetingNonPlayerImmunityFor at 20,
		mobTargetingTurtleImmunityFor at 20,

		ticksPerWaterAnimalSpawns at 1,

		zombifiedPiglinPortalSpawns at true,

		itemMergeRadius at 1.5,
		experienceMergeRadius at 0.7

	),

	/**
	 * Regular performance, most options to vanilla or default.
	 * In comparison to [REGULAR_MINUS], this enables some performance benefits.
	 */
	REGULAR(

		displayName = "Very good",
		tpsMinima = TPSMinima(
			18.25,
			17.0
		),

		monsterEntityActivationRange at 24,
		animalEntityActivationRange at 16,
		villagerEntityActivationRange at 20,
		raiderEntityActivationRange at 56,
		waterEntityActivationRange at 16,
		flyingMonsterEntityActivationRange at 56,
		miscEntityActivationRange at 8,

		wakeUpInactiveFlyingMonstersPerTick at 7,

		skippedActiveEntityRatio at (1 to 3), // ~ 0.3333

		endermanTargetingEndermiteImmunityFor at 20,

		ticksPerMonsterSpawns at 1,
		ticksPerWaterAnimalSpawns at 2,
		ticksPerWaterAmbientSpawns at 1,
		ticksPerWaterUndergroundCreatureSpawns at 1,
		ticksPerAmbientSpawns at 1,

		globalMaxConcurrentChunkLoads at 400.0,

		grassSpreadTickRate at 1,

		maxPrimedTNT at 50,

		maxEntityCollisions at 8,

		experienceMergeRadius at 1.1,
		itemDespawnRate at 12000

	),

	/**
	 * This sets most of the normal options to their [REGULAR] setting,
	 * but does not enable some unnecessary benefits yet.
	 */
	REGULAR_MINUS(

		displayName = "Good",
		tpsMinima = TPSMinima(
			17.5,
			16.0
		),

		maxTrackViewDistance at 11,

		monsterEntityActivationRange at 22,
		animalEntityActivationRange at 15,
		villagerEntityActivationRange at 18,
		raiderEntityActivationRange at 50,
		waterEntityActivationRange at 15,

		wakeUpInactiveFlyingMonstersPerTick at 6,

		skippedActiveEntityRatio at (3 to 8), // 0.375

		piglinTargetingWitherSkeletonImmunityFor at 20,
		witherSkeletonTargetingPiglinImmunityFor at 20,
		witherSkeletonTargetingIronGolemImmunityFor at 20,
		witherSkeletonTargetingSnowGolemImmunityFor at 20,

		ticksPerMonsterSpawns at 2,
		ticksPerWaterAnimalSpawns at 3,
		ticksPerWaterAmbientSpawns at 2,
		ticksPerWaterUndergroundCreatureSpawns at 2,
		ticksPerAmbientSpawns at 2,

		mobSpawnerTickRate at 1,

		globalMaxConcurrentChunkLoads at 360.0,

		containerUpdateTickRate at 1,
		grassSpreadTickRate at 2,

		maxPrimedTNT at 40,

		maxEntityCollisions at 6,

		itemMergeRadius at 2.0,
		experienceMergeRadius at 1.5,
		itemDespawnRate at 9000

	),

	/**
	 * This is okay performance: not as desired, but very acceptable and hard to notice that something is wrong.
	 */
	FINE(

		displayName = "Good-medium",
		tpsMinima = TPSMinima(
			16.75,
			14.0
		),

		simulationDistance at 6,
		maxTrackViewDistance at 10,

		monsterEntityActivationRange at 20,
		animalEntityActivationRange at 14,
		villagerEntityActivationRange at 16,
		raiderEntityActivationRange at 45,
		waterEntityActivationRange at 14,

		wakeUpInactiveMonstersPerTick at 4,
		wakeUpInactiveFlyingMonstersPerTick at 5,

		skippedActiveEntityRatio at (2 to 5), // 0.4

		wakeUpInactiveMonstersPerTick at 8,
		wakeUpInactiveAnimalsPerTick at 4,
		wakeUpInactiveVillagersPerTick at 4,
		wakeUpInactiveFlyingMonstersPerTick at 8,

		ticksPerMonsterSpawns at 3,
		ticksPerWaterAnimalSpawns at 8,
		ticksPerWaterAmbientSpawns at 6,
		ticksPerWaterUndergroundCreatureSpawns at 3,
		ticksPerAmbientSpawns at 3,

		globalMaxConcurrentChunkLoads at 320.0,

		grassSpreadTickRate at 3,

		maxPrimedTNT at 30,

		maxEntityCollisions at 4,

		itemMergeRadius at 2.5,
		experienceMergeRadius at 2.25

	),

	/**
	 * This is barely okay performance, definitely not as desired but still acceptable.
	 */
	BARELY_FINE(

		displayName = "Medium",
		tpsMinima = TPSMinima(
			16.0,
			12.0
		),

		maxTrackViewDistance at 9,

		monsterEntityActivationRange at 18,
		animalEntityActivationRange at 13,
		villagerEntityActivationRange at 14,
		raiderEntityActivationRange at 40,
		waterEntityActivationRange at 13,
		flyingMonsterEntityActivationRange at 48,
		miscEntityActivationRange at 7,

		wakeUpInactiveMonstersPerTick at 3,
		wakeUpInactiveAnimalsPerTick at 2,
		wakeUpInactiveVillagersPerTick at 2,
		wakeUpInactiveFlyingMonstersPerTick at 4,

		skippedActiveEntityRatio at (3 to 7), // ~ 0.4286

		babyImmunityFor at 5,
		areShearedSheepImmune at true,

		wolfTargetingSheepImmunityFor at 20,
		wolfTargetingFoxImmunityFor at 20,
		llamaTargetingWolfImmunityFor at 20,
		polarBearTargetingFoxImmunityFor at 20,
		foxTargetingChickenImmunityFor at 20,
		ocelotTargetingChickenImmunityFor at 20,
		catTargetingRabbitImmunityFor at 20,
		piglinTargetingHoglinImmunityFor at 20,
		zoglinTargetingNonPlayerImmunityFor at 20,
		axolotlTargetingFishImmunityFor at 20,
		axolotlTargetingSquidImmunityFor at 20,
		witchTargetingWitchImmunityFor at 20,

		ticksPerMonsterSpawns at 4,
		ticksPerWaterAnimalSpawns at 12,
		ticksPerWaterAmbientSpawns at 8,
		ticksPerWaterUndergroundCreatureSpawns at 4,
		ticksPerAmbientSpawns at 4,

		mobSpawnerTickRate at 2,

		globalMaxConcurrentChunkLoads at 280.0,
		maxAutoSaveChunksPerTick at 24,

		disableIceAndSnow at false,
		tickArmorStands at true,
		containerUpdateTickRate at 2,
		grassSpreadTickRate at 4,
		updatePathfindingOnBlockUpdate at true,

		maxPrimedTNT at 20,

		maxEntityCollisions at 3,

		itemMergeRadius at 3.0,
		experienceMergeRadius at 3.25

	),

	/**
	 * This keeps the simulation distance at 5, so as to not break most redstone contraptions.
	 * (This is the last reasonable setting, below this becomes noticeable limited performance.)
	 * All these values should be what is reasonable for them to be without breaking redstone.
	 */
	MINIMUM_NON_REDSTONE_BREAKING(

		displayName = "Medium-low",
		tpsMinima = TPSMinima(
			14.0,
			10.0
		),

		simulationDistance at 5,
		maxTrackViewDistance at 8,

		monsterEntityActivationRange at 16,
		animalEntityActivationRange at 10,
		villagerEntityActivationRange at 12,
		raiderEntityActivationRange at 30,
		waterEntityActivationRange at 10,
		flyingMonsterEntityActivationRange at 36,
		miscEntityActivationRange at 6,

		wakeUpInactiveFlyingMonstersPerTick at 3,

		skippedActiveEntityRatio at (1 to 2), // 0.5

		ticksPerMonsterSpawns at 6,
		ticksPerAnimalSpawns at 400,
		ticksPerWaterAnimalSpawns at 16,
		ticksPerWaterAmbientSpawns at 10,
		ticksPerWaterUndergroundCreatureSpawns at 6,
		ticksPerAmbientSpawns at 6,

		globalMaxConcurrentChunkLoads at 250.0,
		maxAutoSaveChunksPerTick at 22,

		maxPrimedTNT at 10,

		experienceMergeRadius at 4.25

	),

	/**
	 * This is quite poor performance, we don't want this.
	 */
	POOR(

		displayName = "Low",
		tpsMinima = TPSMinima(
			12.0,
			5.0,
		),

		simulationDistance at 4,
		maxTrackViewDistance at 7,

		monsterEntityActivationRange at 14,
		animalEntityActivationRange at 8,
		villagerEntityActivationRange at 12,
		raiderEntityActivationRange at 28,
		waterEntityActivationRange at 8,
		flyingMonsterEntityActivationRange at 32,

		wakeUpInactiveMonstersPerTick at 2,
		wakeUpInactiveFlyingMonstersPerTick at 2,

		skippedActiveEntityRatio at (5 to 9), // ~ 0.5556

		ticksPerMonsterSpawns at 10,
		ticksPerWaterAnimalSpawns at 24,
		ticksPerWaterAmbientSpawns at 16,
		ticksPerWaterUndergroundCreatureSpawns at 10,
		ticksPerAmbientSpawns at 10,

		globalMaxConcurrentChunkLoads at 220.0,
		maxAutoSaveChunksPerTick at 20,

		grassSpreadTickRate at 5,

		maxPrimedTNT at 8,

		armorStandCollisionLookups at true,

		itemMergeRadius at 3.5,
		experienceMergeRadius at 5.0

	),

	/**
	 * This is very poor performance, we really don't want this.
	 */
	VERY_POOR(

		displayName = "Very low",
		tpsMinima = TPSMinima(
			10.0,
			0.0,
		),

		simulationDistance at 3,
		maxTrackViewDistance at 6,

		monsterEntityActivationRange at 13,
		animalEntityActivationRange at 7,
		villagerEntityActivationRange at 10,
		raiderEntityActivationRange at 24,
		waterEntityActivationRange at 7,
		flyingMonsterEntityActivationRange at 28,
		miscEntityActivationRange at 5,

		skippedActiveEntityRatio at (3 to 5), // 0.6

		ticksPerMonsterSpawns at 20,
		ticksPerAnimalSpawns at 800,
		ticksPerWaterAnimalSpawns at 48,
		ticksPerWaterAmbientSpawns at 36,
		ticksPerWaterUndergroundCreatureSpawns at 20,
		ticksPerAmbientSpawns at 20,

		mobSpawnerTickRate at 4,

		globalMaxConcurrentChunkLoads at 190.0,
		maxAutoSaveChunksPerTick at 18,

		containerUpdateTickRate at 3,
		grassSpreadTickRate at 9,
		disablePillagerPatrols at false,

		maxPrimedTNT at 6,

		maxEntityCollisions at 2,

		itemMergeRadius at 4.0,
		experienceMergeRadius at 6.0,
		itemDespawnRate at 6000

	),

	/**
	 * This is the absolute minimum the server can run at.
	 */
	MINIMUM(

		displayName = "Lowest",
		tpsMinima = TPSMinima(
			0.0,
			0.0
		),

//		mobFarmFactor at 0.0, // Disabled due to temporarily no mob farm nerfs in 1.19
		pistonDuplicationEnabled at true, // Temp due to 225624 tree farm trouble
//		pistonDuplicationEnabled at false, // Disabled temp due to 225624 tree farm trouble

//		fartherViewDistanceMaxChunksGeneratedPerTick at 0, // Disabled due to no more FartherViewDistance

		simulationDistance at 2,
		maxTrackViewDistance at 5,

		monsterEntityActivationRange at 12,
		animalEntityActivationRange at 6,
		villagerEntityActivationRange at 9,
		raiderEntityActivationRange at 20,
		waterEntityActivationRange at 6,
		flyingMonsterEntityActivationRange at 24,
		miscEntityActivationRange at 4,

		wakeUpInactiveMonstersPerTick at 1,
		wakeUpInactiveAnimalsPerTick at 1,
		wakeUpInactiveVillagersPerTick at 1,
		wakeUpInactiveFlyingMonstersPerTick at 1,

		skippedActiveEntityRatio at (2 to 3), // ~ 0.6667

		tickInactiveVillagers at false,
		SettingValue(babyImmunityFor, -1),
		areShearedSheepImmune at false,

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

		ticksPerMonsterSpawns at 40,
		ticksPerAnimalSpawns at 1200,
		ticksPerWaterAnimalSpawns at 64,
		ticksPerWaterAmbientSpawns at 50,
		ticksPerWaterUndergroundCreatureSpawns at 40,
		ticksPerAmbientSpawns at 40,

		mobSpawnChunkRange at 4,

		mobSpawnerTickRate at 5,

		globalMaxConcurrentChunkLoads at 160.0,
		maxAutoSaveChunksPerTick at 16,

		disableIceAndSnow at true,
		tickArmorStands at false,
		containerUpdateTickRate at 4,
		grassSpreadTickRate at 15,
		updatePathfindingOnBlockUpdate at false,
		disablePillagerPatrols at true,
		zombifiedPiglinPortalSpawns at false,

		maxPrimedTNT at 4,

		maxEntityCollisions at 1,
		armorStandCollisionLookups at false,

		itemMergeRadius at 5.0,
		experienceMergeRadius at 10.0,
		itemDespawnRate at 4500

	);

	private val valuesBySetting: Int2ObjectOpenHashMap<*> =
		Int2ObjectOpenHashMap(settingValues.asSequence().map { Pair(it.setting.index, it.value) }.toMap())

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

	private fun <T> applySettingValue(setting: PerformanceSetting<T>) =
		setting.set(getSettingValue(setting))

	private fun <T> applySettingValueIfDiffering(setting: PerformanceSetting<T>, otherValue: T) =
		getSettingValue(setting).let { if (otherValue != it) setting.set(it) }

	private fun <T> applySettingValueIfDifferingWithMode(setting: PerformanceSetting<T>, otherMode: PerformanceMode) =
		applySettingValueIfDiffering(setting, otherMode.getSettingValue(setting))

	fun applyAllSettingValues() =
		performanceSettings.forEach { applySettingValue(it) }

	fun applyDifferingSettingValues(otherMode: PerformanceMode) =
		performanceSettings.forEach { applySettingValueIfDifferingWithMode(it, otherMode) }

	companion object {

		val highest = values()[0]
		val lowest = values()[values().size - 1]

	}

}