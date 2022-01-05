/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.antilag.performanceadapter

import com.destroystokyo.paper.PaperConfig
import org.bukkit.Bukkit
import org.bukkit.entity.ArmorStand
import org.sucraft.core.common.bukkit.config.PaperConfigUtils
import org.sucraft.core.common.bukkit.world.ViewDistanceUtils
import org.sucraft.core.common.bukkit.world.mainWorld
import org.sucraft.core.common.sucraft.delegate.MobFarmWeight
import xuan.cat.fartherviewdistance.code.Index


class PerformanceSetting<T>(
	private val getter: () -> T,
	private val setter: (T) -> Unit
) {

	fun get() = getter()

	fun set(value: T) = setter(value)

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
			MobFarmWeight.get()::getWeight,
			MobFarmWeight.get()::setWeight
		)
		val pistonDuplicationEnabled = PerformanceSetting(
			{ PaperConfig.allowPistonDuplication },
			{ PaperConfig.allowPistonDuplication = it }
		)

		val fartherViewDistanceMaxChunksGeneratedPerTick = PerformanceSetting(
			{ Index.getConfigData().serverTickMaxGenerateAmount },
			{ Index.getConfigData().serverTickMaxGenerateAmount = it }
		)

		val simulationDistance = PerformanceSetting(
			{ mainWorld.simulationDistance },
			{ value ->
				//Bukkit.getWorlds().forEach { it.viewDistance = value }
				Bukkit.getWorlds().forEach { ViewDistanceUtils.setSimulationDistance(it, value) }
			}
		)

		val monsterEntityActivationRange = PerformanceSetting(
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.monsterActivationRange } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.monsterActivationRange = value } }
		)
		val animalEntityActivationRange = PerformanceSetting(
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.animalActivationRange } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.animalActivationRange = value } }
		)
		val villagerEntityActivationRange = PerformanceSetting(
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.villagerActivationRange } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.villagerActivationRange = value } }
		)
		val raiderEntityActivationRange = PerformanceSetting(
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.raiderActivationRange } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.raiderActivationRange = value } }
		)
		val waterEntityActivationRange = PerformanceSetting(
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.waterActivationRange } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.waterActivationRange = value } }
		)
		val flyingMonsterEntityActivationRange = PerformanceSetting(
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.flyingMonsterActivationRange } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.flyingMonsterActivationRange = value } }
		)
		val miscEntityActivationRange = PerformanceSetting(
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.miscActivationRange } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.miscActivationRange = value } }
		)

		val wakeUpInactiveMonstersPerTick = PerformanceSetting(
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.wakeUpInactiveMonsters } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.wakeUpInactiveMonsters = value } }
		)
		val wakeUpInactiveAnimalsPerTick = PerformanceSetting(
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.wakeUpInactiveAnimals } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.wakeUpInactiveAnimals = value } }
		)
		val wakeUpInactiveVillagersPerTick = PerformanceSetting(
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.wakeUpInactiveVillagers } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.wakeUpInactiveVillagers = value } }
		)
		val wakeUpInactiveFlyingMonstersPerTick = PerformanceSetting(
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.wakeUpInactiveFlying } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.wakeUpInactiveFlying = value } }
		)

		val tickInactiveVillagers = PerformanceSetting(
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.tickInactiveVillagers } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.tickInactiveVillagers = value } }
		)

		val ticksPerMonsterSpawns = PerformanceSetting(
			{ mainWorld.ticksPerMonsterSpawns },
			{ value -> Bukkit.getWorlds().forEach { it.setTicksPerMonsterSpawns(value.toInt()) } }
		)
		val ticksPerAnimalSpawns = PerformanceSetting(
			{ mainWorld.ticksPerAnimalSpawns },
			{ value -> Bukkit.getWorlds().forEach { it.setTicksPerAnimalSpawns(value.toInt()) } }
		)
		val ticksPerWaterAnimalSpawns = PerformanceSetting(
			{ mainWorld.ticksPerWaterSpawns },
			{ value -> Bukkit.getWorlds().forEach { it.setTicksPerWaterSpawns(value.toInt()) } }
		)
		val ticksPerWaterAmbientSpawns = PerformanceSetting(
			{ mainWorld.ticksPerWaterAmbientSpawns },
			{ value -> Bukkit.getWorlds().forEach { it.setTicksPerWaterAmbientSpawns(value.toInt()) } }
		)
		val ticksPerWaterUndergroundCreatureSpawns = PerformanceSetting(
			{ mainWorld.ticksPerWaterUndergroundCreatureSpawns },
			{ value -> Bukkit.getWorlds().forEach { it.setTicksPerWaterUndergroundCreatureSpawns(value.toInt()) } }
		)
		val ticksPerAmbientSpawns = PerformanceSetting(
			{ mainWorld.ticksPerAmbientSpawns },
			{ value -> Bukkit.getWorlds().forEach { it.setTicksPerAmbientSpawns(value.toInt()) } }
		)

		val mobSpawnChunkRange = PerformanceSetting(
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.mobSpawnRange } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.mobSpawnRange = value } }
		)

		val mobSpawnerTickRate = PerformanceSetting(
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.mobSpawnerTickRate } },
			{ value -> PaperConfigUtils.modifyInPaperWorldsConfig { it.mobSpawnerTickRate = value } }
		)

		/*val globalMaxConcurrentChunkLoads = PerformanceSetting(
			{ PaperConfig.globalMaxConcurrentChunkLoads },
			{ PaperConfig.globalMaxConcurrentChunkLoads = it }
		)*/
		val maxAutoSaveChunksPerTick = PerformanceSetting(
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.maxAutoSaveChunksPerTick } },
			{ value -> PaperConfigUtils.modifyInPaperWorldsConfig { it.maxAutoSaveChunksPerTick = value } }
		)

		val disableIceAndSnow = PerformanceSetting(
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.disableIceAndSnow } },
			{ value -> PaperConfigUtils.modifyInPaperWorldsConfig { it.disableIceAndSnow = value } }
		)
		val tickArmorStands = PerformanceSetting(
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.armorStandTick } },
			{ value ->
				PaperConfigUtils.modifyInPaperWorldsConfig { it.armorStandTick = value }
				Bukkit.getWorlds().asSequence().flatMap { it.getEntitiesByClass(ArmorStand::class.java) }.forEach { it.setCanTick(value) }
			}
		)
		val containerUpdateTickRate = PerformanceSetting(
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.containerUpdateTickRate } },
			{ value -> PaperConfigUtils.modifyInPaperWorldsConfig { it.containerUpdateTickRate = value } }
		)
		val grassSpreadTickRate = PerformanceSetting(
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.grassUpdateRate } },
			{ value -> PaperConfigUtils.modifyInPaperWorldsConfig { it.grassUpdateRate = value } }
		)
		val updatePathfindingOnBlockUpdate = PerformanceSetting(
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.updatePathfindingOnBlockUpdate } },
			{ value -> PaperConfigUtils.modifyInPaperWorldsConfig { it.updatePathfindingOnBlockUpdate = value } }
		)
		val disablePillagerPatrols = PerformanceSetting(
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.disablePillagerPatrols } },
			{ value -> PaperConfigUtils.modifyInPaperWorldsConfig { it.disablePillagerPatrols = value } }
		)
		val zombifiedPiglinPortalSpawns = PerformanceSetting(
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.enableZombiePigmenPortalSpawns } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.enableZombiePigmenPortalSpawns = value } }
		)

		val maxPrimedTNT = PerformanceSetting(
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.maxTntTicksPerTick } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.maxTntTicksPerTick = value } }
		)

		val maxEntityCollisions = PerformanceSetting(
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.maxCollisionsPerEntity } },
			{ value -> PaperConfigUtils.modifyInPaperWorldsConfig { it.maxCollisionsPerEntity = value } }
		)
		val armorStandCollisionLookups = PerformanceSetting(
			{ PaperConfigUtils.getFromPaperMainWorldConfig { it.armorStandEntityLookups } },
			{ value -> PaperConfigUtils.modifyInPaperWorldsConfig { it.armorStandEntityLookups = value } }
		)

		val itemMergeRadius = PerformanceSetting(
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.itemMerge } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.itemMerge = value } }
		)
		val experienceMergeRadius = PerformanceSetting(
			{ PaperConfigUtils.getFromSpigotMainWorldConfig { it.expMerge } },
			{ value -> PaperConfigUtils.modifyInSpigotWorldsConfig { it.expMerge = value } }
		)
		val itemDespawnRate = PerformanceSetting(
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