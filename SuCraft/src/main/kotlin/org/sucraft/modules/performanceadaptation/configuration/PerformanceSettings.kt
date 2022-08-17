/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.performanceadaptation.configuration

import io.papermc.paper.configuration.GlobalConfiguration
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.SpawnCategory
import org.sucraft.common.function.runEach
import org.sucraft.common.serverconfig.paperConfig
import org.sucraft.common.serverconfig.spigotConfig
import org.sucraft.common.time.TimeInMinutes
import org.sucraft.common.world.forceSetSimulationDistance
import org.sucraft.common.world.mainWorld
import org.sucraft.common.world.runEachWorld
import org.sucraft.modules.performanceadaptation.model.HighIntIsBestDelayedPerformanceSetting
import org.sucraft.modules.performanceadaptation.model.PerformanceSetting

// The performance settings used by the performance adapter

// Disabled due to temporarily no mob farm nerfs in 1.19
/*
val mobFarmFactor = PerformanceSetting(
	"mob farm factor",
	MobFarmWeight.get()::getWeight,
	MobFarmWeight.get()::setWeight
)
*/

val pistonDuplicationEnabled = PerformanceSetting("piston duplication",
	{ GlobalConfiguration.get().unsupportedSettings.allowPistonDuplication },
	{ GlobalConfiguration.get().unsupportedSettings.allowPistonDuplication = it })

// Disabled due to no more FartherViewDistance
/*
val fartherViewDistanceMaxChunksGeneratedPerTick = PerformanceSetting(
	"FartherViewDistance max chunks generated per tick",
	{ Index.getConfigData().serverTickMaxGenerateAmount },
	{ Index.getConfigData().serverTickMaxGenerateAmount = it }
)
*/

val simulationDistance = PerformanceSetting("simulation distance",
	{ mainWorld.simulationDistance },
	{ runEachWorld { forceSetSimulationDistance(it) } })

val maxTrackViewDistance = HighIntIsBestDelayedPerformanceSetting(
	"max track view distance",
	{ mainWorld.paperConfig.viewDistances.track.getMax() },
	{ runEachWorld { paperConfig.viewDistances.track.setMax(it) } },
	TimeInMinutes(1).asTicks(), // 1 minute
	12
)

val monsterEntityActivationRange = PerformanceSetting("activation range (monsters)",
	{ mainWorld.spigotConfig.monsterActivationRange },
	{ runEachWorld { spigotConfig.monsterActivationRange = it } })
val animalEntityActivationRange = PerformanceSetting("activation range (animals)",
	{ mainWorld.spigotConfig.animalActivationRange },
	{ runEachWorld { spigotConfig.animalActivationRange = it } })
val villagerEntityActivationRange = PerformanceSetting("activation range (villagers)",
	{ mainWorld.spigotConfig.villagerActivationRange },
	{ runEachWorld { spigotConfig.villagerActivationRange = it } })
val raiderEntityActivationRange = PerformanceSetting("activation range (raiders)",
	{ mainWorld.spigotConfig.raiderActivationRange },
	{ runEachWorld { spigotConfig.raiderActivationRange = it } })
val waterEntityActivationRange = PerformanceSetting("activation range (water entities)",
	{ mainWorld.spigotConfig.waterActivationRange },
	{ runEachWorld { spigotConfig.waterActivationRange = it } })
val flyingMonsterEntityActivationRange = PerformanceSetting("activation range (flying monsters)",
	{ mainWorld.spigotConfig.flyingMonsterActivationRange },
	{ runEachWorld { spigotConfig.flyingMonsterActivationRange = it } })
val miscEntityActivationRange = PerformanceSetting("activation range (misc)",
	{ mainWorld.spigotConfig.miscActivationRange },
	{ runEachWorld { spigotConfig.miscActivationRange = it } })

val wakeUpInactiveMonstersPerTick = PerformanceSetting("wake up inactive per tick (monsters)",
	{ mainWorld.spigotConfig.wakeUpInactiveMonsters },
	{ runEachWorld { spigotConfig.wakeUpInactiveMonsters = it } })
val wakeUpInactiveAnimalsPerTick = PerformanceSetting("wake up inactive per tick (animals)",
	{ mainWorld.spigotConfig.wakeUpInactiveAnimals },
	{ runEachWorld { spigotConfig.wakeUpInactiveAnimals = it } })
val wakeUpInactiveVillagersPerTick = PerformanceSetting("wake up inactive per tick (villagers)",
	{ mainWorld.spigotConfig.wakeUpInactiveVillagers },
	{ runEachWorld { spigotConfig.wakeUpInactiveVillagers = it } })
val wakeUpInactiveFlyingMonstersPerTick = PerformanceSetting("wake up inactive per tick (flying monsters)",
	{ mainWorld.spigotConfig.wakeUpInactiveFlying },
	{ runEachWorld { spigotConfig.wakeUpInactiveFlying = it } })

val skippedActiveEntityRatio: PerformanceSetting<Pair<Int, Int>> = PerformanceSetting("skip active entity ratio",
	{
		GlobalConfiguration.get().skippedActiveEntityRatio.numerator to
				GlobalConfiguration.get().skippedActiveEntityRatio.denominator
	},
	{ (first, second) ->
		GlobalConfiguration.get().SkippedActiveEntityRatio().numerator = first
		GlobalConfiguration.get().SkippedActiveEntityRatio().denominator = second
	})

val tickInactiveVillagers = PerformanceSetting(
	"tick inactive villagers",
	{ mainWorld.spigotConfig.tickInactiveVillagers },
	{ runEachWorld { spigotConfig.tickInactiveVillagers = it } }
)
val babyImmunityFor = PerformanceSetting(
	"baby inactivity immune time",
	{ mainWorld.spigotConfig.babyImmunityFor },
	{ runEachWorld { spigotConfig.babyImmunityFor = it } }
)
val areShearedSheepImmune = PerformanceSetting(
	"sheared sheep inactivity immunity",
	{ mainWorld.spigotConfig.areShearedSheepImmune },
	{ runEachWorld { spigotConfig.areShearedSheepImmune = it } }
)

val guardianTargetingSquidImmunityFor = PerformanceSetting(
	"guardian targeting squid inactivity immune time",
	{ mainWorld.spigotConfig.guardianTargetingSquidImmunityFor },
	{ runEachWorld { spigotConfig.guardianTargetingSquidImmunityFor = it } }
)
val endermanTargetingEndermiteImmunityFor = PerformanceSetting(
	"enderman targeting endermite inactivity immune time",
	{ mainWorld.spigotConfig.endermanTargetingEndermiteImmunityFor },
	{ runEachWorld { spigotConfig.endermanTargetingEndermiteImmunityFor = it } }
)
val piglinTargetingWitherSkeletonImmunityFor = PerformanceSetting(
	"piglin targeting wither skeleton inactivity immune time",
	{ mainWorld.spigotConfig.piglinTargetingWitherSkeletonImmunityFor },
	{ runEachWorld { spigotConfig.piglinTargetingWitherSkeletonImmunityFor = it } }
)
val witherSkeletonTargetingPiglinImmunityFor = PerformanceSetting(
	"wither skeleton targeting piglin inactivity immune time",
	{ mainWorld.spigotConfig.witherSkeletonTargetingPiglinImmunityFor },
	{ runEachWorld { spigotConfig.witherSkeletonTargetingPiglinImmunityFor = it } }
)
val witherSkeletonTargetingIronGolemImmunityFor = PerformanceSetting(
	"wither skeleton targeting iron golem inactivity immune time",
	{ mainWorld.spigotConfig.witherSkeletonTargetingIronGolemImmunityFor },
	{ runEachWorld { spigotConfig.witherSkeletonTargetingIronGolemImmunityFor = it } }
)
val witherSkeletonTargetingSnowGolemImmunityFor = PerformanceSetting(
	"wither skeleton targeting snow golem inactivity immune time",
	{ mainWorld.spigotConfig.witherSkeletonTargetingSnowGolemImmunityFor },
	{ runEachWorld { spigotConfig.witherSkeletonTargetingSnowGolemImmunityFor = it } }
)
val snowGolemTargetingNonPlayerImmunityFor = PerformanceSetting(
	"snow golem targeting non-player mob inactivity immune time",
	{ mainWorld.spigotConfig.snowGolemTargetingNonPlayerImmunityFor },
	{ runEachWorld { spigotConfig.snowGolemTargetingNonPlayerImmunityFor = it } }
)
val mobTargetingTurtleImmunityFor = PerformanceSetting(
	"mob targeting turtle inactivity immune time",
	{ mainWorld.spigotConfig.mobTargetingTurtleImmunityFor },
	{ runEachWorld { spigotConfig.mobTargetingTurtleImmunityFor = it } }
)

val wolfTargetingSheepImmunityFor = PerformanceSetting(
	"wolf targeting sheep inactivity immune time",
	{ mainWorld.spigotConfig.wolfTargetingSheepImmunityFor },
	{ runEachWorld { spigotConfig.wolfTargetingSheepImmunityFor = it } }
)
val wolfTargetingFoxImmunityFor = PerformanceSetting(
	"wolf targeting fox inactivity immune time",
	{ mainWorld.spigotConfig.wolfTargetingFoxImmunityFor },
	{ runEachWorld { spigotConfig.wolfTargetingFoxImmunityFor = it } }
)
val llamaTargetingWolfImmunityFor = PerformanceSetting(
	"llama targeting wolf inactivity immune time",
	{ mainWorld.spigotConfig.llamaTargetingWolfImmunityFor },
	{ runEachWorld { spigotConfig.llamaTargetingWolfImmunityFor = it } }
)
val polarBearTargetingFoxImmunityFor = PerformanceSetting(
	"polar bear targeting fox inactivity immune time",
	{ mainWorld.spigotConfig.polarBearTargetingFoxImmunityFor },
	{ runEachWorld { spigotConfig.polarBearTargetingFoxImmunityFor = it } }
)
val foxTargetingChickenImmunityFor = PerformanceSetting(
	"fox targeting chicken inactivity immune time",
	{ mainWorld.spigotConfig.foxTargetingChickenImmunityFor },
	{ runEachWorld { spigotConfig.foxTargetingChickenImmunityFor = it } }
)
val ocelotTargetingChickenImmunityFor = PerformanceSetting(
	"ocelot targeting chicken inactivity immune time",
	{ mainWorld.spigotConfig.ocelotTargetingChickenImmunityFor },
	{ runEachWorld { spigotConfig.ocelotTargetingChickenImmunityFor = it } }
)
val catTargetingRabbitImmunityFor = PerformanceSetting(
	"cat targeting rabbit inactivity immune time",
	{ mainWorld.spigotConfig.catTargetingRabbitImmunityFor },
	{ runEachWorld { spigotConfig.catTargetingRabbitImmunityFor = it } }
)
val piglinTargetingHoglinImmunityFor = PerformanceSetting(
	"piglin targeting hoglin inactivity immune time",
	{ mainWorld.spigotConfig.piglinTargetingHoglinImmunityFor },
	{ runEachWorld { spigotConfig.piglinTargetingHoglinImmunityFor = it } }
)
val zoglinTargetingNonPlayerImmunityFor = PerformanceSetting(
	"zoglin targeting non-player mob inactivity immune time",
	{ mainWorld.spigotConfig.zoglinTargetingNonPlayerImmunityFor },
	{ runEachWorld { spigotConfig.zoglinTargetingNonPlayerImmunityFor = it } }
)
val axolotlTargetingFishImmunityFor = PerformanceSetting(
	"axolotl targeting fish inactivity immune time",
	{ mainWorld.spigotConfig.axolotlTargetingFishImmunityFor },
	{ runEachWorld { spigotConfig.axolotlTargetingFishImmunityFor = it } }
)
val axolotlTargetingSquidImmunityFor = PerformanceSetting(
	"axolotl targeting squid inactivity immune time",
	{ mainWorld.spigotConfig.axolotlTargetingSquidImmunityFor },
	{ runEachWorld { spigotConfig.axolotlTargetingSquidImmunityFor = it } }
)
val witchTargetingWitchImmunityFor = PerformanceSetting(
	"witch targeting witch inactivity immune time",
	{ mainWorld.spigotConfig.witchTargetingWitchImmunityFor },
	{ runEachWorld { spigotConfig.witchTargetingWitchImmunityFor = it } }
)

val ticksPerMonsterSpawns = PerformanceSetting(
	"ticks per spawns (monsters)",
	{ mainWorld.getTicksPerSpawns(SpawnCategory.MONSTER) },
	{ runEachWorld { setTicksPerSpawns(SpawnCategory.MONSTER, it.toInt()) } }
)
val ticksPerAnimalSpawns = PerformanceSetting(
	"ticks per spawns (animals)",
	{ mainWorld.getTicksPerSpawns(SpawnCategory.ANIMAL) },
	{ runEachWorld { setTicksPerSpawns(SpawnCategory.ANIMAL, it.toInt()) } }
)
val ticksPerWaterAnimalSpawns = PerformanceSetting(
	"ticks per spawns (water animals)",
	{ mainWorld.getTicksPerSpawns(SpawnCategory.WATER_ANIMAL) },
	{ runEachWorld { setTicksPerSpawns(SpawnCategory.WATER_ANIMAL, it.toInt()) } }
)
val ticksPerWaterAmbientSpawns = PerformanceSetting(
	"ticks per spawns (water ambient)",
	{ mainWorld.getTicksPerSpawns(SpawnCategory.WATER_AMBIENT) },
	{ runEachWorld { setTicksPerSpawns(SpawnCategory.WATER_AMBIENT, it.toInt()) } }
)
val ticksPerWaterUndergroundCreatureSpawns = PerformanceSetting(
	"ticks per spawns (water underground creatures)",
	{ mainWorld.getTicksPerSpawns(SpawnCategory.WATER_UNDERGROUND_CREATURE) },
	{ runEachWorld { setTicksPerSpawns(SpawnCategory.WATER_UNDERGROUND_CREATURE, it.toInt()) } }
)
val ticksPerAmbientSpawns = PerformanceSetting(
	"ticks per spawns (ambient)",
	{ mainWorld.getTicksPerSpawns(SpawnCategory.AMBIENT) },
	{ runEachWorld { setTicksPerSpawns(SpawnCategory.AMBIENT, it.toInt()) } }
)

val mobSpawnChunkRange = PerformanceSetting(
	"mob spawn chunk range",
	{ mainWorld.spigotConfig.mobSpawnRange },
	{ runEachWorld { spigotConfig.mobSpawnRange = it } }
)

val mobSpawnerTickRate = PerformanceSetting(
	"mob spawner tick rate",
	{ mainWorld.paperConfig.tickRates.mobSpawner },
	{ runEachWorld { paperConfig.tickRates.mobSpawner = it } }
)

val globalMaxConcurrentChunkLoads = PerformanceSetting(
	"max concurrent chunk loads",
	{ GlobalConfiguration.get().chunkLoading.globalMaxConcurrentLoads },
	{ GlobalConfiguration.get().chunkLoading.globalMaxConcurrentLoads = it }
)
val maxAutoSaveChunksPerTick = PerformanceSetting(
	"max auto-save chunks per tick",
	{ mainWorld.paperConfig.chunks.maxAutoSaveChunksPerTick },
	{ runEachWorld { paperConfig.chunks.maxAutoSaveChunksPerTick = it } }
)

val disableIceAndSnow = PerformanceSetting(
	"disable ice and snow",
	{ mainWorld.paperConfig.environment.disableIceAndSnow },
	{ mainWorld.paperConfig.environment.disableIceAndSnow = it }
)
val tickArmorStands = PerformanceSetting(
	"tick armor stands",
	{ mainWorld.paperConfig.entities.armorStands.tick },
	{
		runEachWorld {
			paperConfig.entities.armorStands.tick = it
			getEntitiesByClass(ArmorStand::class.java).runEach { setCanTick(it) }
		}
	}
)
val containerUpdateTickRate = PerformanceSetting(
	"container update tick rate",
	{ mainWorld.paperConfig.tickRates.containerUpdate },
	{ runEachWorld { paperConfig.tickRates.containerUpdate = it } }
)
val grassSpreadTickRate = PerformanceSetting(
	"grass spread tick rate",
	{ mainWorld.paperConfig.tickRates.grassSpread },
	{ runEachWorld { paperConfig.tickRates.grassSpread = it } }
)
val updatePathfindingOnBlockUpdate = PerformanceSetting(
	"update pathfinding on block update",
	{ mainWorld.paperConfig.misc.updatePathfindingOnBlockUpdate },
	{ runEachWorld { paperConfig.misc.updatePathfindingOnBlockUpdate = it } }
)
val disablePillagerPatrols = PerformanceSetting(
	"disable pillager patrols",
	{ mainWorld.paperConfig.entities.behavior.pillagerPatrols.disable },
	{ runEachWorld { paperConfig.entities.behavior.pillagerPatrols.disable = it } }
)
val zombifiedPiglinPortalSpawns = PerformanceSetting(
	"zombified piglin portal spawns",
	{ mainWorld.spigotConfig.enableZombiePigmenPortalSpawns },
	{ runEachWorld { spigotConfig.enableZombiePigmenPortalSpawns = it } }
)

val maxPrimedTNT = PerformanceSetting(
	"max primed TNT",
	{ mainWorld.spigotConfig.maxTntTicksPerTick },
	{ runEachWorld { spigotConfig.maxTntTicksPerTick = it } }
)

val maxEntityCollisions = PerformanceSetting(
	"max collisions per entity",
	{ mainWorld.paperConfig.collisions.maxEntityCollisions },
	{ runEachWorld { paperConfig.collisions.maxEntityCollisions = it } }
)
val armorStandCollisionLookups = PerformanceSetting(
	"armor stand collision lookups",
	{ mainWorld.paperConfig.entities.armorStands.doCollisionEntityLookups },
	{ runEachWorld { paperConfig.entities.armorStands.doCollisionEntityLookups = it } }
)

val itemMergeRadius = PerformanceSetting(
	"merge radius (items)",
	{ mainWorld.spigotConfig.itemMerge },
	{ runEachWorld { spigotConfig.itemMerge = it } }
)
val experienceMergeRadius = PerformanceSetting(
	"merge radius (experience)",
	{ mainWorld.spigotConfig.expMerge },
	{ runEachWorld { spigotConfig.expMerge = it } }
)
val itemDespawnRate = PerformanceSetting(
	"item despawn rate",
	{ mainWorld.spigotConfig.itemDespawnRate },
	{ runEachWorld { spigotConfig.itemDespawnRate = it } }
)

val performanceSettings = arrayOf(

	// Disabled due to temporarily no mob farm nerfs in 1.19
//	mobFarmFactor,

	pistonDuplicationEnabled,

	// Disabled due to no more FartherViewDistance
//	fartherViewDistanceMaxChunksGeneratedPerTick,

	simulationDistance,

	monsterEntityActivationRange,
	animalEntityActivationRange,
	villagerEntityActivationRange,
	raiderEntityActivationRange,
	waterEntityActivationRange,
	flyingMonsterEntityActivationRange,
	miscEntityActivationRange,

	wakeUpInactiveMonstersPerTick,
	wakeUpInactiveAnimalsPerTick,
	wakeUpInactiveVillagersPerTick,
	wakeUpInactiveFlyingMonstersPerTick,

	tickInactiveVillagers,

	ticksPerMonsterSpawns,
	ticksPerAnimalSpawns,
	ticksPerWaterAnimalSpawns,
	ticksPerWaterAmbientSpawns,
	ticksPerWaterUndergroundCreatureSpawns,
	ticksPerAmbientSpawns,

	mobSpawnChunkRange,

	mobSpawnerTickRate,

	maxAutoSaveChunksPerTick,

	disableIceAndSnow,
	tickArmorStands,
	containerUpdateTickRate,
	grassSpreadTickRate,
	updatePathfindingOnBlockUpdate,
	disablePillagerPatrols,
	zombifiedPiglinPortalSpawns,

	maxPrimedTNT,

	maxEntityCollisions,
	armorStandCollisionLookups,

	itemMergeRadius,
	experienceMergeRadius,
	itemDespawnRate

)