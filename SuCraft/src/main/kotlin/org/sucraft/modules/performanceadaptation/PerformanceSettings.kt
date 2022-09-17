/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.performanceadaptation

import gg.pufferfish.pufferfish.PufferfishConfig
import io.papermc.paper.configuration.GlobalConfiguration
import net.minecraft.world.entity.EntityTypes
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.SpawnCategory
import org.sucraft.common.function.runEach
import org.sucraft.common.serverconfig.paperConfig
import org.sucraft.common.serverconfig.spigotConfig
import org.sucraft.common.time.TimeInMinutes
import org.sucraft.common.world.forceSetSimulationDistance
import org.sucraft.common.world.mainWorld
import org.sucraft.common.world.runEachWorld
import org.sucraft.modules.performanceadaptation.PerformanceSetting.Companion.boolean
import org.sucraft.modules.performanceadaptation.PerformanceSetting.Companion.byte
import org.sucraft.modules.performanceadaptation.PerformanceSetting.Companion.double
import org.sucraft.modules.performanceadaptation.PerformanceSetting.Companion.fraction
import org.sucraft.modules.performanceadaptation.PerformanceSetting.Companion.int
import org.sucraft.modules.performanceadaptation.PerformanceSetting.Companion.long
import org.sucraft.modules.performanceadaptation.PerformanceSetting.Companion.tickRateBehaviorVillager
import org.sucraft.modules.performanceadaptation.PerformanceSetting.Companion.tickRateSensorVillager
import org.sucraft.modules.shorttermbackups.BackupTaskExecutor
import org.sucraft.modules.teleportfollowingmobsbeforeunload.TeleportFollowingMobsBeforeUnload

// The performance settings used by the performance adapter

// Disabled due to temporarily no mob farm nerfs in 1.19
/*
PerformanceSetting(
	"mob farm factor",
	MobFarmWeight.get()::getWeight,
	MobFarmWeight.get()::setWeight
)
*/

//PerformanceSetting("piston duplication",
//	{ GlobalConfiguration.get().unsupportedSettings.allowPistonDuplication },
//	{ GlobalConfiguration.get().unsupportedSettings.allowPistonDuplication = it })

// Disabled due to no more FartherViewDistance
/*
PerformanceSetting(
	"FartherViewDistance max chunks generated per tick",
	{ Index.getConfigData().serverTickMaxGenerateAmount },
	{ Index.getConfigData().serverTickMaxGenerateAmount = it }
)
*/

val performanceSettings: Array<PerformanceSetting<*>> = arrayOf(

	int(
		"chunk-distance/simulation",
		{ mainWorld.simulationDistance },
		{ runEachWorld { forceSetSimulationDistance(it) } }
	),

	HighIntIsBestDelayedPerformanceSetting(
		"chunk-distance/max-see",
		{ mainWorld.paperConfig.viewDistances.see.getMax() },
		{ runEachWorld { paperConfig.viewDistances.see.setMax(it) } },
		TimeInMinutes(1).asTicks(), // 1 minute
		32
	),

	HighIntIsBestDelayedPerformanceSetting(
		"chunk-distance/max-track",
		{ mainWorld.paperConfig.viewDistances.track.getMax() },
		{ runEachWorld { paperConfig.viewDistances.track.setMax(it) } },
		TimeInMinutes(1).asTicks(), // 1 minute
		12
	),

	HighIntIsBestDelayedPerformanceSetting(
		"max-total-chunks/see",
		{ GlobalConfiguration.get().viewDistances.see.getMaxTotalChunks() },
		{ GlobalConfiguration.get().viewDistances.see.setMaxTotalChunks(it) },
		TimeInMinutes(1).asTicks(), // 1 minute
		16500
	),

	HighIntIsBestDelayedPerformanceSetting(
		"max-total-chunks/track",
		{ GlobalConfiguration.get().viewDistances.track.getMaxTotalChunks() },
		{ GlobalConfiguration.get().viewDistances.track.setMaxTotalChunks(it) },
		TimeInMinutes(1).asTicks(), // 1 minute
		3500
	),

	byte(
		"chunk-distance/mob-spawn",
		{ mainWorld.spigotConfig.mobSpawnRange },
		{ runEachWorld { spigotConfig.mobSpawnRange = it } }
	),

	int(
		"entity-activation-range/animal",
		{ mainWorld.spigotConfig.animalActivationRange },
		{ runEachWorld { spigotConfig.animalActivationRange = it } }
	),

	int(
		"entity-activation-range/water",
		{ mainWorld.spigotConfig.waterActivationRange },
		{ runEachWorld { spigotConfig.waterActivationRange = it } }
	),

	int(
		"entity-activation-range/villager",
		{ mainWorld.spigotConfig.villagerActivationRange },
		{ runEachWorld { spigotConfig.villagerActivationRange = it } }
	),

	int(
		"entity-activation-range/monster",
		{ mainWorld.spigotConfig.monsterActivationRange },
		{ runEachWorld { spigotConfig.monsterActivationRange = it } }
	),

	int(
		"entity-activation-range/flying-monster",
		{ mainWorld.spigotConfig.flyingMonsterActivationRange },
		{ runEachWorld { spigotConfig.flyingMonsterActivationRange = it } }
	),

	int(
		"entity-activation-range/raider",
		{ mainWorld.spigotConfig.raiderActivationRange },
		{ runEachWorld { spigotConfig.raiderActivationRange = it } }
	),

	int(
		"entity-activation-range/misc",
		{ mainWorld.spigotConfig.miscActivationRange },
		{ runEachWorld { spigotConfig.miscActivationRange = it } }
	),

	int(
		"wake-up-inactive-per-tick/animal",
		{ mainWorld.spigotConfig.wakeUpInactiveAnimals },
		{ runEachWorld { spigotConfig.wakeUpInactiveAnimals = it } }
	),

	int(
		"wake-up-inactive-per-tick/villager",
		{ mainWorld.spigotConfig.wakeUpInactiveVillagers },
		{ runEachWorld { spigotConfig.wakeUpInactiveVillagers = it } }
	),

	int(
		"wake-up-inactive-per-tick/monster",
		{ mainWorld.spigotConfig.wakeUpInactiveMonsters },
		{ runEachWorld { spigotConfig.wakeUpInactiveMonsters = it } }
	),

	int(
		"wake-up-inactive-per-tick/flying-monster",
		{ mainWorld.spigotConfig.wakeUpInactiveFlying },
		{ runEachWorld { spigotConfig.wakeUpInactiveFlying = it } }
	),

	fraction(
		"skip-active-entity-ratio",
		{
			GlobalConfiguration.get().skippedActiveEntityRatio.run {
				numerator to denominator
			}
		},
		{ (first, second) ->
			GlobalConfiguration.get().skippedActiveEntityRatio.apply {
				numerator = first
				denominator = second
			}
		}
	),

	boolean(
		"tick-entity/inactive-villager",
		{ mainWorld.spigotConfig.tickInactiveVillagers },
		{ runEachWorld { spigotConfig.tickInactiveVillagers = it } }
	),

	boolean(
		"tick-entity/armor-stand",
		{ mainWorld.paperConfig.entities.armorStands.tick },
		{
			runEachWorld {
				paperConfig.entities.armorStands.tick = it
				getEntitiesByClass(ArmorStand::class.java).runEach { setCanTick(it) }
			}
		}
	),

	boolean(
		"immunity/sheared-sheep",
		{ mainWorld.spigotConfig.areShearedSheepImmune },
		{ runEachWorld { spigotConfig.areShearedSheepImmune = it } }
	),

	int(
		"immunity/for/baby",
		{ mainWorld.spigotConfig.babyImmunityFor },
		{ runEachWorld { spigotConfig.babyImmunityFor = it } }
	),

	int(
		"immunity/for/targeting/guardian/squid",
		{ mainWorld.spigotConfig.guardianTargetingSquidImmunityFor },
		{ runEachWorld { spigotConfig.guardianTargetingSquidImmunityFor = it } }
	),

	int(
		"immunity/for/targeting/enderman/endermite",
		{ mainWorld.spigotConfig.endermanTargetingEndermiteImmunityFor },
		{ runEachWorld { spigotConfig.endermanTargetingEndermiteImmunityFor = it } }
	),

	int(
		"immunity/for/targeting/piglin/wither-skeleton",
		{ mainWorld.spigotConfig.piglinTargetingWitherSkeletonImmunityFor },
		{ runEachWorld { spigotConfig.piglinTargetingWitherSkeletonImmunityFor = it } }
	),

	int(
		"immunity/for/targeting/wither-skeleton/piglin",
		{ mainWorld.spigotConfig.witherSkeletonTargetingPiglinImmunityFor },
		{ runEachWorld { spigotConfig.witherSkeletonTargetingPiglinImmunityFor = it } }
	),

	int(
		"immunity/for/targeting/wither-skeleton/iron-golem",
		{ mainWorld.spigotConfig.witherSkeletonTargetingIronGolemImmunityFor },
		{ runEachWorld { spigotConfig.witherSkeletonTargetingIronGolemImmunityFor = it } }
	),

	int(
		"immunity/for/targeting/wither-skeleton/snow-golem",
		{ mainWorld.spigotConfig.witherSkeletonTargetingSnowGolemImmunityFor },
		{ runEachWorld { spigotConfig.witherSkeletonTargetingSnowGolemImmunityFor = it } }
	),

	int(
		"immunity/for/targeting/snow-golem/non-player",
		{ mainWorld.spigotConfig.snowGolemTargetingNonPlayerImmunityFor },
		{ runEachWorld { spigotConfig.snowGolemTargetingNonPlayerImmunityFor = it } }
	),

	int(
		"immunity/for/targeting/mob/turtle",
		{ mainWorld.spigotConfig.mobTargetingTurtleImmunityFor },
		{ runEachWorld { spigotConfig.mobTargetingTurtleImmunityFor = it } }
	),

	int(
		"immunity/for/targeting/wolf/sheep",
		{ mainWorld.spigotConfig.wolfTargetingSheepImmunityFor },
		{ runEachWorld { spigotConfig.wolfTargetingSheepImmunityFor = it } }
	),

	int(
		"immunity/for/targeting/wolf/fox",
		{ mainWorld.spigotConfig.wolfTargetingFoxImmunityFor },
		{ runEachWorld { spigotConfig.wolfTargetingFoxImmunityFor = it } }
	),

	int(
		"immunity/for/targeting/llama/wolf",
		{ mainWorld.spigotConfig.llamaTargetingWolfImmunityFor },
		{ runEachWorld { spigotConfig.llamaTargetingWolfImmunityFor = it } }
	),

	int(
		"immunity/for/targeting/polar-bear/fox",
		{ mainWorld.spigotConfig.polarBearTargetingFoxImmunityFor },
		{ runEachWorld { spigotConfig.polarBearTargetingFoxImmunityFor = it } }
	),

	int(
		"immunity/for/targeting/fox/chicken",
		{ mainWorld.spigotConfig.foxTargetingChickenImmunityFor },
		{ runEachWorld { spigotConfig.foxTargetingChickenImmunityFor = it } }
	),

	int(
		"immunity/for/targeting/ocelot/chicken",
		{ mainWorld.spigotConfig.ocelotTargetingChickenImmunityFor },
		{ runEachWorld { spigotConfig.ocelotTargetingChickenImmunityFor = it } }
	),

	int(
		"immunity/for/targeting/cat/rabbit",
		{ mainWorld.spigotConfig.catTargetingRabbitImmunityFor },
		{ runEachWorld { spigotConfig.catTargetingRabbitImmunityFor = it } }
	),

	int(
		"immunity/for/targeting/piglin/hoglin",
		{ mainWorld.spigotConfig.piglinTargetingHoglinImmunityFor },
		{ runEachWorld { spigotConfig.piglinTargetingHoglinImmunityFor = it } }
	),

	int(
		"immunity/for/targeting/zoglin/non-player",
		{ mainWorld.spigotConfig.zoglinTargetingNonPlayerImmunityFor },
		{ runEachWorld { spigotConfig.zoglinTargetingNonPlayerImmunityFor = it } }
	),

	int(
		"immunity/for/targeting/axolotl/fish",
		{ mainWorld.spigotConfig.axolotlTargetingFishImmunityFor },
		{ runEachWorld { spigotConfig.axolotlTargetingFishImmunityFor = it } }
	),

	int(
		"immunity/for/targeting/axolotl/squid",
		{ mainWorld.spigotConfig.axolotlTargetingSquidImmunityFor },
		{ runEachWorld { spigotConfig.axolotlTargetingSquidImmunityFor = it } }
	),

	int(
		"immunity/for/targeting/witch/witch",
		{ mainWorld.spigotConfig.witchTargetingWitchImmunityFor },
		{ runEachWorld { spigotConfig.witchTargetingWitchImmunityFor = it } }
	),

	long(
		"ticks-per-spawns/animal",
		{ mainWorld.getTicksPerSpawns(SpawnCategory.ANIMAL) },
		{ runEachWorld { setTicksPerSpawns(SpawnCategory.ANIMAL, it.toInt()) } }
	),

	long(
		"ticks-per-spawns/water-animal",
		{ mainWorld.getTicksPerSpawns(SpawnCategory.WATER_ANIMAL) },
		{ runEachWorld { setTicksPerSpawns(SpawnCategory.WATER_ANIMAL, it.toInt()) } }
	),

	long(
		"ticks-per-spawns/water-underground-creature",
		{ mainWorld.getTicksPerSpawns(SpawnCategory.WATER_UNDERGROUND_CREATURE) },
		{ runEachWorld { setTicksPerSpawns(SpawnCategory.WATER_UNDERGROUND_CREATURE, it.toInt()) } }
	),

	long(
		"ticks-per-spawns/ambient",
		{ mainWorld.getTicksPerSpawns(SpawnCategory.AMBIENT) },
		{ runEachWorld { setTicksPerSpawns(SpawnCategory.AMBIENT, it.toInt()) } }
	),

	long(
		"ticks-per-spawns/water-ambient",
		{ mainWorld.getTicksPerSpawns(SpawnCategory.WATER_AMBIENT) },
		{ runEachWorld { setTicksPerSpawns(SpawnCategory.WATER_AMBIENT, it.toInt()) } }
	),

	long(
		"ticks-per-spawns/monster",
		{ mainWorld.getTicksPerSpawns(SpawnCategory.MONSTER) },
		{ runEachWorld { setTicksPerSpawns(SpawnCategory.MONSTER, it.toInt()) } }
	),

	int(
		"tick-rate/monster-spawner",
		{ mainWorld.paperConfig.tickRates.mobSpawner },
		{ runEachWorld { paperConfig.tickRates.mobSpawner = it } }
	),

	int(
		"tick-rate/container-update",
		{ mainWorld.paperConfig.tickRates.containerUpdate },
		{ runEachWorld { paperConfig.tickRates.containerUpdate = it } }
	),

	int(
		"tick-rate/grass-spread",
		{ mainWorld.paperConfig.tickRates.grassSpread },
		{ runEachWorld { paperConfig.tickRates.grassSpread = it } }
	),

	int(
		"tick-rate/hanging",
		{ mainWorld.spigotConfig.hangingTickFrequency },
		{ runEachWorld { spigotConfig.hangingTickFrequency = it } }
	),

	int(
		"tick-rate/player-auto-save",
		{ GlobalConfiguration.get().playerAutoSave.rate },
		{ GlobalConfiguration.get().playerAutoSave.rate = it }
	),

	tickRateBehaviorVillager("validatenearbypoi"),

	tickRateBehaviorVillager("acquirepoi"),

	tickRateSensorVillager("secondarypoisensor"),

	tickRateSensorVillager("nearestbedsensor"),

	tickRateSensorVillager("villagerbabiessensor"),

	tickRateSensorVillager("playersensor"),

	tickRateSensorVillager("nearestlivingentitysensor"),

	double(
		"max-concurrent/chunk-loads",
		{ GlobalConfiguration.get().chunkLoading.globalMaxConcurrentLoads },
		{ GlobalConfiguration.get().chunkLoading.globalMaxConcurrentLoads = it }
	),

	int(
		"max-concurrent/sends",
		{ GlobalConfiguration.get().chunkLoading.maxConcurrentSends },
		{ GlobalConfiguration.get().chunkLoading.maxConcurrentSends = it }
	),

	int(
		"per-tick/auto-save-chunks",
		{ mainWorld.paperConfig.chunks.maxAutoSaveChunksPerTick },
		{ runEachWorld { paperConfig.chunks.maxAutoSaveChunksPerTick = it } }
	),

	int(
		"per-tick/max-joins",
		{ GlobalConfiguration.get().misc.maxJoinsPerTick },
		{ GlobalConfiguration.get().misc.maxJoinsPerTick = it }
	),

	boolean(
		"short-term-backups",
		{ BackupTaskExecutor.areBackupsEnabled },
		{ BackupTaskExecutor.areBackupsEnabled = it }
	),

	boolean(
		"chunk-unload-check-followers",
		{ TeleportFollowingMobsBeforeUnload.checkOnChunkUnload },
		{ TeleportFollowingMobsBeforeUnload.checkOnChunkUnload = it }
	),

	boolean(
		"disable-ice-and-snow",
		{ mainWorld.paperConfig.environment.disableIceAndSnow },
		{ mainWorld.paperConfig.environment.disableIceAndSnow = it }
	),

	boolean(
		"update-pathfinding-on-block-update",
		{ mainWorld.paperConfig.misc.updatePathfindingOnBlockUpdate },
		{ runEachWorld { paperConfig.misc.updatePathfindingOnBlockUpdate = it } }
	),

	boolean(
		"disable-pillager-patrols",
		{ mainWorld.paperConfig.entities.behavior.pillagerPatrols.disable },
		{ runEachWorld { paperConfig.entities.behavior.pillagerPatrols.disable = it } }
	),

	boolean(
		"zombified-piglin-portal-spawns",
		{ mainWorld.spigotConfig.enableZombiePigmenPortalSpawns },
		{ runEachWorld { spigotConfig.enableZombiePigmenPortalSpawns = it } }
	),

	boolean(
		"suffocation-optimization",
		{ PufferfishConfig.enableSuffocationOptimization },
		{ PufferfishConfig.enableSuffocationOptimization = it }
	),

	boolean(
		"inactive-goal-selector-throttle",
		{ PufferfishConfig.throttleInactiveGoalSelectorTick },
		{ PufferfishConfig.throttleInactiveGoalSelectorTick = it }
	),

	boolean(
		"async/pathfinding",
		{ PufferfishConfig.enableAsyncPathfinding },
		{ PufferfishConfig.enableAsyncPathfinding = it }
	),

	boolean(
		"async/mob-spawning",
		{ PufferfishConfig.enableAsyncMobSpawning },
		{ PufferfishConfig.enableAsyncMobSpawning = it }
	),

	boolean(
		"async/entity-tracker",
		{ PufferfishConfig.enableAsyncEntityTracker },
		{ PufferfishConfig.enableAsyncEntityTracker = it }
	),

	boolean(
		"dab/enabled",
		{ PufferfishConfig.dearEnabled },
		{ PufferfishConfig.dearEnabled = it }
	),

	int(
		"dab/max-tick-freq",
		{ PufferfishConfig.maximumActivationPrio },
		{ PufferfishConfig.maximumActivationPrio = it }
	),

	int(
		"dab/start-distance",
		{ PufferfishConfig.startDistance },
		{
			PufferfishConfig.startDistance = it
			PufferfishConfig.startDistanceSquared = it * it
		}
	),

	int(
		"dab/distance-mod",
		{ PufferfishConfig.activationDistanceMod },
		{ PufferfishConfig.activationDistanceMod = it }
	),

	int(
		"max-primed-tnt",
		{ mainWorld.spigotConfig.maxTntTicksPerTick },
		{ runEachWorld { spigotConfig.maxTntTicksPerTick = it } }
	),

	int(
		"collisions/max",
		{ mainWorld.paperConfig.collisions.maxEntityCollisions },
		{ runEachWorld { paperConfig.collisions.maxEntityCollisions = it } }
	),

	boolean(
		"collisions/armor-stand",
		{ mainWorld.paperConfig.entities.armorStands.doCollisionEntityLookups },
		{ runEachWorld { paperConfig.entities.armorStands.doCollisionEntityLookups = it } }
	),

	double(
		"merge-radius/item",
		{ mainWorld.spigotConfig.itemMerge },
		{ runEachWorld { spigotConfig.itemMerge = it } }
	),

	double(
		"merge-radius/experience",
		{ mainWorld.spigotConfig.expMerge },
		{ runEachWorld { spigotConfig.expMerge = it } }
	),

	int(
		"despawn-rate/item",
		{ mainWorld.spigotConfig.itemDespawnRate },
		{ runEachWorld { spigotConfig.itemDespawnRate = it } }
	),

	int(
		"despawn-rate/arrow",
		{ mainWorld.spigotConfig.arrowDespawnRate },
		{ runEachWorld { spigotConfig.arrowDespawnRate = it } }
	),

	int(
		"despawn-rate/trident",
		{ mainWorld.spigotConfig.tridentDespawnRate },
		{ runEachWorld { spigotConfig.tridentDespawnRate = it } }
	)

)