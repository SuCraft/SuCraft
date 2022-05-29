/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.antilag.performanceadapter

import com.destroystokyo.paper.PaperConfig
import org.bukkit.Bukkit
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.SpawnCategory
import org.sucraft.core.common.bukkit.config.PaperConfigUtils
import org.sucraft.core.common.bukkit.world.ViewDistanceUtils
import org.sucraft.core.common.bukkit.world.mainWorld
import org.sucraft.core.common.sucraft.delegate.MobFarmWeight
import xuan.cat.fartherviewdistance.code.Index

open class PerformanceSetting<T>(
	val displayName: String,
	private val getter: () -> T,
	private val setter: (T) -> Unit
) {

	fun get() = getter()

	open fun set(value: T) = setter(value)

	val index: Int

	init {
		index = nextPerformanceSettingIndex
		nextPerformanceSettingIndex++
	}

	override fun hashCode() = index

	override fun equals(other: Any?) = (other as? PerformanceSetting<*>)?.let { it.index == this.index } ?: false

	companion object {

		var nextPerformanceSettingIndex: Int = 0

		val mobFarmFactor = PerformanceSetting(
			"mob farm factor",
			MobFarmWeight.get()::getWeight,
			MobFarmWeight.get()::setWeight
		)
		val pistonDuplicationEnabled = PerformanceSetting(
			"piston duplication",
			{ PaperConfig.allowPistonDuplication },
			{ PaperConfig.allowPistonDuplication = it }
		)

		val fartherViewDistanceMaxChunksGeneratedPerTick = PerformanceSetting(
			"FartherViewDistance max chunks generated per tick",
			{ Index.getConfigData().serverTickMaxGenerateAmount },
			{ Index.getConfigData().serverTickMaxGenerateAmount = it }
		)

		val simulationDistance = PerformanceSetting(
			"simulation distance",
			{ mainWorld.simulationDistance },
			{ value ->
				//Bukkit.getWorlds().forEach { it.viewDistance = value }
				Bukkit.getWorlds().forEach { ViewDistanceUtils.setSimulationDistance(it, value) }
			}
		)

		val maxTrackViewDistance = HighIntIsBestDelayedPerformanceSetting(
			"max track view distance",
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.maxTrackViewDistance } },
			{ value -> PaperConfigUtils.modifyInPaperWorldsConfig { it.maxTrackViewDistance = value } },
			20L * 60, // 1 minute
			12
		)

		val monsterEntityActivationRange = PerformanceSetting(
			"activation range (monsters)",
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.monsterActivationRange } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.monsterActivationRange = value } }
		)
		val animalEntityActivationRange = PerformanceSetting(
			"activation range (animals)",
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.animalActivationRange } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.animalActivationRange = value } }
		)
		val villagerEntityActivationRange = PerformanceSetting(
			"activation range (villagers)",
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.villagerActivationRange } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.villagerActivationRange = value } }
		)
		val raiderEntityActivationRange = PerformanceSetting(
			"activation range (raiders)",
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.raiderActivationRange } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.raiderActivationRange = value } }
		)
		val waterEntityActivationRange = PerformanceSetting(
			"activation range (water entities)",
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.waterActivationRange } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.waterActivationRange = value } }
		)
		val flyingMonsterEntityActivationRange = PerformanceSetting(
			"activation range (flying monsters)",
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.flyingMonsterActivationRange } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.flyingMonsterActivationRange = value } }
		)
		val miscEntityActivationRange = PerformanceSetting(
			"activation range (misc)",
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.miscActivationRange } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.miscActivationRange = value } }
		)

		val wakeUpInactiveMonstersPerTick = PerformanceSetting(
			"wake up inactive per tick (monsters)",
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.wakeUpInactiveMonsters } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.wakeUpInactiveMonsters = value } }
		)
		val wakeUpInactiveAnimalsPerTick = PerformanceSetting(
			"wake up inactive per tick (animals)",
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.wakeUpInactiveAnimals } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.wakeUpInactiveAnimals = value } }
		)
		val wakeUpInactiveVillagersPerTick = PerformanceSetting(
			"wake up inactive per tick (villagers)",
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.wakeUpInactiveVillagers } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.wakeUpInactiveVillagers = value } }
		)
		val wakeUpInactiveFlyingMonstersPerTick = PerformanceSetting(
			"wake up inactive per tick (flying monsters)",
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.wakeUpInactiveFlying } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.wakeUpInactiveFlying = value } }
		)

		val skippedActiveEntityRatio: PerformanceSetting<Pair<Int, Int>> = PerformanceSetting(
			"skip active entity ratio",
			{ Pair(PaperConfig.skippedActiveEntityRatioNumerator, PaperConfig.skippedActiveEntityRatioDenominator) },
			{ value ->
				PaperConfig.skippedActiveEntityRatioNumerator = value.first
				PaperConfig.skippedActiveEntityRatioDenominator = value.second
			}
		)

		val tickInactiveVillagers = PerformanceSetting(
			"tick inactive villagers",
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.tickInactiveVillagers } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.tickInactiveVillagers = value } }
		)
		val babyImmunityFor = PerformanceSetting(
			"baby inactivity immune time",
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.babyImmunityFor } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.babyImmunityFor = value } }
		)
		val areShearedSheepImmune = PerformanceSetting(
			"sheared sheep inactivity immunity",
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.areShearedSheepImmune } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.areShearedSheepImmune = value } }
		)

		val guardianTargetingSquidImmunityFor = PerformanceSetting(
            "guardian targeting squid inactivity immune time",
            { PaperConfigUtils.getFromSpigotMainWorldConfig { it.guardianTargetingSquidImmunityFor } },
            { value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.guardianTargetingSquidImmunityFor = value } }
        )
		val endermanTargetingEndermiteImmunityFor = PerformanceSetting(
            "enderman targeting endermite inactivity immune time",
            { PaperConfigUtils.getFromSpigotMainWorldConfig { it.endermanTargetingEndermiteImmunityFor } },
            { value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.endermanTargetingEndermiteImmunityFor = value } }
        )
		val piglinTargetingWitherSkeletonImmunityFor = PerformanceSetting(
            "piglin targetin wither skeleton inactivity immune time",
            { PaperConfigUtils.getFromSpigotMainWorldConfig { it.piglinTargetingWitherSkeletonImmunityFor } },
            { value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.piglinTargetingWitherSkeletonImmunityFor = value } }
        )
		val witherSkeletonTargetingPiglinImmunityFor = PerformanceSetting(
            "wither skeleton targeting piglin inactivity immune time",
            { PaperConfigUtils.getFromSpigotMainWorldConfig { it.witherSkeletonTargetingPiglinImmunityFor } },
            { value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.witherSkeletonTargetingPiglinImmunityFor = value } }
        )
		val witherSkeletonTargetingIronGolemImmunityFor = PerformanceSetting(
            "wither skeleton targeting iron golem inactivity immune time",
            { PaperConfigUtils.getFromSpigotMainWorldConfig { it.witherSkeletonTargetingIronGolemImmunityFor } },
            { value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.witherSkeletonTargetingIronGolemImmunityFor = value } }
        )
		val witherSkeletonTargetingSnowGolemImmunityFor = PerformanceSetting(
            "wither skeleton targeting snow golem inactivity immune time",
            { PaperConfigUtils.getFromSpigotMainWorldConfig { it.witherSkeletonTargetingSnowGolemImmunityFor } },
            { value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.witherSkeletonTargetingSnowGolemImmunityFor = value } }
        )
		val snowGolemTargetingNonPlayerImmunityFor = PerformanceSetting(
            "snow golem targeting non-player mob inactivity immune time",
            { PaperConfigUtils.getFromSpigotMainWorldConfig { it.snowGolemTargetingNonPlayerImmunityFor } },
            { value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.snowGolemTargetingNonPlayerImmunityFor = value } }
        )
		val mobTargetingTurtleImmunityFor = PerformanceSetting(
            "mob targeting turtle inactivity immune time",
            { PaperConfigUtils.getFromSpigotMainWorldConfig { it.mobTargetingTurtleImmunityFor } },
            { value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.mobTargetingTurtleImmunityFor = value } }
        )

		val wolfTargetingSheepImmunityFor = PerformanceSetting(
            "wolf targeting sheep inactivity immune time",
            { PaperConfigUtils.getFromSpigotMainWorldConfig { it.wolfTargetingSheepImmunityFor } },
            { value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.wolfTargetingSheepImmunityFor = value } }
        )
		val wolfTargetingFoxImmunityFor = PerformanceSetting(
            "wolf targeting fox inactivity immune time",
            { PaperConfigUtils.getFromSpigotMainWorldConfig { it.wolfTargetingFoxImmunityFor } },
            { value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.wolfTargetingFoxImmunityFor = value } }
        )
		val llamaTargetingWolfImmunityFor = PerformanceSetting(
            "llama targeting wolf inactivity immune time",
            { PaperConfigUtils.getFromSpigotMainWorldConfig { it.llamaTargetingWolfImmunityFor } },
            { value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.llamaTargetingWolfImmunityFor = value } }
        )
		val polarBearTargetingFoxImmunityFor = PerformanceSetting(
            "polar bear targeting fox inactivity immune time",
            { PaperConfigUtils.getFromSpigotMainWorldConfig { it.polarBearTargetingFoxImmunityFor } },
            { value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.polarBearTargetingFoxImmunityFor = value } }
        )
		val foxTargetingChickenImmunityFor = PerformanceSetting(
            "fox targeting chicken inactivity immune time",
            { PaperConfigUtils.getFromSpigotMainWorldConfig { it.foxTargetingChickenImmunityFor } },
            { value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.foxTargetingChickenImmunityFor = value } }
        )
		val ocelotTargetingChickenImmunityFor = PerformanceSetting(
            "ocelot targeting chicken inactivity immune time",
            { PaperConfigUtils.getFromSpigotMainWorldConfig { it.ocelotTargetingChickenImmunityFor } },
            { value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.ocelotTargetingChickenImmunityFor = value } }
        )
		val catTargetingRabbitImmunityFor = PerformanceSetting(
            "cat targeting rabbit inactivity immune time",
            { PaperConfigUtils.getFromSpigotMainWorldConfig { it.catTargetingRabbitImmunityFor } },
            { value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.catTargetingRabbitImmunityFor = value } }
        )
		val piglinTargetingHoglinImmunityFor = PerformanceSetting(
            "piglin targeting hoglin inactivity immune time",
            { PaperConfigUtils.getFromSpigotMainWorldConfig { it.piglinTargetingHoglinImmunityFor } },
            { value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.piglinTargetingHoglinImmunityFor = value } }
        )
		val zoglinTargetingNonPlayerImmunityFor = PerformanceSetting(
            "zoglin targeting non-player mob inactivity immune time",
            { PaperConfigUtils.getFromSpigotMainWorldConfig { it.zoglinTargetingNonPlayerImmunityFor } },
            { value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.zoglinTargetingNonPlayerImmunityFor = value } }
        )
		val axolotlTargetingFishImmunityFor = PerformanceSetting(
            "axolotl targeting fish inactivity immune time",
            { PaperConfigUtils.getFromSpigotMainWorldConfig { it.axolotlTargetingFishImmunityFor } },
            { value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.axolotlTargetingFishImmunityFor = value } }
        )
		val axolotlTargetingSquidImmunityFor = PerformanceSetting(
            "axolotl targeting squid inactivity immune time",
            { PaperConfigUtils.getFromSpigotMainWorldConfig { it.axolotlTargetingSquidImmunityFor } },
            { value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.axolotlTargetingSquidImmunityFor = value } }
        )
		val witchTargetingWitchImmunityFor = PerformanceSetting(
            "witch targeting witch inactivity immune time",
            { PaperConfigUtils.getFromSpigotMainWorldConfig { it.witchTargetingWitchImmunityFor } },
            { value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.witchTargetingWitchImmunityFor = value } }
        )

		val ticksPerMonsterSpawns = PerformanceSetting(
			"ticks per spawns (monsters)",
			{ mainWorld.getTicksPerSpawns(SpawnCategory.MONSTER) },
			{ value -> Bukkit.getWorlds().forEach { it.setTicksPerSpawns(SpawnCategory.MONSTER, value.toInt()) } }
		)
		val ticksPerAnimalSpawns = PerformanceSetting(
			"ticks per spawns (animals)",
			{ mainWorld.getTicksPerSpawns(SpawnCategory.ANIMAL) },
			{ value -> Bukkit.getWorlds().forEach { it.setTicksPerSpawns(SpawnCategory.ANIMAL, value.toInt()) } }
		)
		val ticksPerWaterAnimalSpawns = PerformanceSetting(
			"ticks per spawns (water animals)",
			{ mainWorld.getTicksPerSpawns(SpawnCategory.WATER_ANIMAL) },
			{ value -> Bukkit.getWorlds().forEach { it.setTicksPerSpawns(SpawnCategory.WATER_ANIMAL, value.toInt()) } }
		)
		val ticksPerWaterAmbientSpawns = PerformanceSetting(
			"ticks per spawns (water ambient)",
			{ mainWorld.getTicksPerSpawns(SpawnCategory.WATER_AMBIENT) },
			{ value -> Bukkit.getWorlds().forEach { it.setTicksPerSpawns(SpawnCategory.WATER_AMBIENT, value.toInt()) } }
		)
		val ticksPerWaterUndergroundCreatureSpawns = PerformanceSetting(
			"ticks per spawns (water underground creatures)",
			{ mainWorld.getTicksPerSpawns(SpawnCategory.WATER_UNDERGROUND_CREATURE) },
			{ value -> Bukkit.getWorlds().forEach { it.setTicksPerSpawns(SpawnCategory.WATER_UNDERGROUND_CREATURE, value.toInt()) } }
		)
		val ticksPerAmbientSpawns = PerformanceSetting(
			"ticks per spawns (ambient)",
			{ mainWorld.getTicksPerSpawns(SpawnCategory.AMBIENT) },
			{ value -> Bukkit.getWorlds().forEach { it.setTicksPerSpawns(SpawnCategory.AMBIENT, value.toInt()) } }
		)

		val mobSpawnChunkRange = PerformanceSetting(
			"mob spawn chunk range",
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.mobSpawnRange } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.mobSpawnRange = value } }
		)

		val mobSpawnerTickRate = PerformanceSetting(
			"mob spawner tick rate",
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.mobSpawnerTickRate } },
			{ value -> PaperConfigUtils.modifyInPaperWorldsConfig { it.mobSpawnerTickRate = value } }
		)

		val globalMaxConcurrentChunkLoads = PerformanceSetting(
			"max concurrent chunk loads",
			{ PaperConfig.globalMaxConcurrentChunkLoads },
			{ PaperConfig.globalMaxConcurrentChunkLoads = it }
		)
		val maxAutoSaveChunksPerTick = PerformanceSetting(
			"max auto-save chunks per tick",
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.maxAutoSaveChunksPerTick } },
			{ value -> PaperConfigUtils.modifyInPaperWorldsConfig { it.maxAutoSaveChunksPerTick = value } }
		)

		val disableIceAndSnow = PerformanceSetting(
			"disable ice and snow",
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.disableIceAndSnow } },
			{ value -> PaperConfigUtils.modifyInPaperWorldsConfig { it.disableIceAndSnow = value } }
		)
		val tickArmorStands = PerformanceSetting(
			"tick armor stands",
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.armorStandTick } },
			{ value ->
				PaperConfigUtils.modifyInPaperWorldsConfig { it.armorStandTick = value }
				Bukkit.getWorlds().asSequence().flatMap { it.getEntitiesByClass(ArmorStand::class.java) }.forEach { it.setCanTick(value) }
			}
		)
		val containerUpdateTickRate = PerformanceSetting(
			"container update tick rate",
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.containerUpdateTickRate } },
			{ value -> PaperConfigUtils.modifyInPaperWorldsConfig { it.containerUpdateTickRate = value } }
		)
		val grassSpreadTickRate = PerformanceSetting(
			"grass spread tick rate",
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.grassUpdateRate } },
			{ value -> PaperConfigUtils.modifyInPaperWorldsConfig { it.grassUpdateRate = value } }
		)
		val updatePathfindingOnBlockUpdate = PerformanceSetting(
			"update pathfinding on block update",
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.updatePathfindingOnBlockUpdate } },
			{ value -> PaperConfigUtils.modifyInPaperWorldsConfig { it.updatePathfindingOnBlockUpdate = value } }
		)
		val disablePillagerPatrols = PerformanceSetting(
			"disable pillager patrols",
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.disablePillagerPatrols } },
			{ value -> PaperConfigUtils.modifyInPaperWorldsConfig { it.disablePillagerPatrols = value } }
		)
		val zombifiedPiglinPortalSpawns = PerformanceSetting(
			"zombified piglin portal spawns",
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.enableZombiePigmenPortalSpawns } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.enableZombiePigmenPortalSpawns = value } }
		)

		val maxPrimedTNT = PerformanceSetting(
			"max primed TNT",
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.maxTntTicksPerTick } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.maxTntTicksPerTick = value } }
		)

		val maxEntityCollisions = PerformanceSetting(
			"max collisions per entity",
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.maxCollisionsPerEntity } },
			{ value -> PaperConfigUtils.modifyInPaperWorldsConfig { it.maxCollisionsPerEntity = value } }
		)
		val armorStandCollisionLookups = PerformanceSetting(
			"armor stand collision lookups",
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.armorStandEntityLookups } },
			{ value -> PaperConfigUtils.modifyInPaperWorldsConfig { it.armorStandEntityLookups = value } }
		)

		val itemMergeRadius = PerformanceSetting(
			"merge radius (items)",
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.itemMerge } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.itemMerge = value } }
		)
		val experienceMergeRadius = PerformanceSetting(
			"merge radius (experience)",
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.expMerge } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.expMerge = value } }
		)
		val itemDespawnRate = PerformanceSetting(
			"item despawn rate",
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.itemDespawnRate } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.itemDespawnRate = value } }
		)

		val values = arrayOf(

			mobFarmFactor,

			pistonDuplicationEnabled,

			fartherViewDistanceMaxChunksGeneratedPerTick,

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

	}

}