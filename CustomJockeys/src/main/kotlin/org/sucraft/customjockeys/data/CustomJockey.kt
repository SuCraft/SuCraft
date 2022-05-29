/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.customjockeys.data

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.event.entity.CreatureSpawnEvent
import org.sucraft.core.common.bukkit.chunk.ChunkCoordinates
import org.sucraft.core.common.bukkit.entity.EntityAge
import org.sucraft.core.common.bukkit.entity.PrintableEntityDescription

// Settings

val defaultEntityPredicate: (Any?) -> Boolean = { true }
val defaultSpawnReasonPredicate: (Any?) -> Boolean = { true }
val defaultLocationPredicate: (Any?) -> Boolean = { true }
val defaultWorldPredicate: (Any?) -> Boolean = { true }
val defaultEnvironmentPredicate: (Any?) -> Boolean = { true }

fun defaultVehicleSpawnMethod(passenger: LivingEntity, location: Location, vehicleType: EntityType): Boolean {
	var vehicleTypeToUse = vehicleType
	if (vehicleType == EntityType.CAVE_SPIDER && (EntityAge.isAdult(passenger) == true)) {
		vehicleTypeToUse = EntityType.SPIDER
	} else if  (vehicleType == EntityType.SPIDER && (EntityAge.isBaby(passenger) == true)) {
		vehicleTypeToUse = EntityType.CAVE_SPIDER
	}
	val vehicle = location.world.spawnEntity(location, vehicleTypeToUse, CreatureSpawnEvent.SpawnReason.JOCKEY) {
		when (EntityAge.isAdult(passenger)) {
			true -> EntityAge.setAdult(it as LivingEntity)
			false -> EntityAge.setBaby(it as LivingEntity)
			else -> {}
		}
	}
	if (vehicle.isDead) {
		CustomJockeyData.logger.warning("Custom spawned jockey vehicle ${PrintableEntityDescription.get(vehicle)} for passenger ${PrintableEntityDescription.get(passenger)} was instantly dead")
		return false
	}
	if (!vehicle.addPassenger(passenger)) {
		CustomJockeyData.logger.warning("Failed to add jockey passenger ${PrintableEntityDescription.get(passenger)} to newly spawned vehicle ${PrintableEntityDescription.get(vehicle)}")
		vehicle.remove()
		return false
	}
	CustomJockeyData.incrementChunkJockeySpawns(ChunkCoordinates.get(location))
	return true
}

fun defaultPassengerSpawnMethod(vehicle: LivingEntity, location: Location, passengerType: EntityType): Boolean {
	val passenger = location.world.spawnEntity(location, passengerType, CreatureSpawnEvent.SpawnReason.MOUNT) {
		when (EntityAge.isAdult(vehicle) ?: when (vehicle.type) {
			EntityType.SPIDER -> true
			EntityType.CAVE_SPIDER -> false
			else -> null
		}) {
			true -> EntityAge.setAdult(it as LivingEntity)
			false -> EntityAge.setBaby(it as LivingEntity)
			else -> {}
		}
	}
	if (passenger.isDead) {
		CustomJockeyData.logger.warning("Custom spawned jockey passenger ${PrintableEntityDescription.get(passenger)} for vehicle ${PrintableEntityDescription.get(vehicle)} was instantly dead")
		return false
	}
	if (!vehicle.addPassenger(passenger)) {
		CustomJockeyData.logger.warning("Failed to add newly spawned jockey passenger ${PrintableEntityDescription.get(passenger)} to vehicle ${PrintableEntityDescription.get(vehicle)}")
		passenger.remove()
		return false
	}
	CustomJockeyData.incrementChunkJockeySpawns(ChunkCoordinates.get(location))
	return true
}

// Class

abstract class CustomJockey(
	val chance: Double,
	val detectedEntityTypes: Array<EntityType>,
	val spawnedEntityTypes: Array<EntityType>,
	val spawnJockeyEntity: (existingEntity: LivingEntity, location: Location, spawnedEntityType: EntityType) -> Boolean,
	val entityPredicate: (LivingEntity) -> Boolean = defaultEntityPredicate,
	val spawnReasonPredicate: (CreatureSpawnEvent.SpawnReason) -> Boolean = defaultSpawnReasonPredicate,
	val locationPredicate: (Location) -> Boolean = defaultLocationPredicate,
	val worldPredicate: (World) -> Boolean = defaultWorldPredicate,
	val environmentPredicate: (World.Environment) -> Boolean = defaultEnvironmentPredicate
	) {

	fun checkCanSpawnJockeyFrom(entity: LivingEntity, location: Location, spawnReason: CreatureSpawnEvent.SpawnReason): Boolean {
		if (!entityPredicate(entity)) return false
		if (!spawnReasonPredicate(spawnReason)) return false
		if (!locationPredicate(location)) return false
		if (!worldPredicate(location.world)) return false
		if (!environmentPredicate(location.world.environment)) return false
		if (entity.type !in detectedEntityTypes) return false
		return true
	}

	fun getRandomSpawnedEntityType() = spawnedEntityTypes.random()

	fun evaluateChance() = Math.random() < chance

	class CustomJockeyByPassenger(
		chance: Double,
		passengerTypes: Array<EntityType>,
		vehicleTypes: Array<EntityType>,
		vehicleSpawnMethod: (LivingEntity, Location, EntityType) -> Boolean = ::defaultVehicleSpawnMethod,
		entityPredicate: (LivingEntity) -> Boolean = defaultEntityPredicate,
		spawnReasonPredicate: (CreatureSpawnEvent.SpawnReason) -> Boolean = defaultSpawnReasonPredicate,
		locationPredicate: (Location) -> Boolean = defaultLocationPredicate,
		worldPredicate: (World) -> Boolean = defaultWorldPredicate,
		environmentPredicate: (World.Environment) -> Boolean = defaultEnvironmentPredicate
	) : CustomJockey(
		chance,
		passengerTypes,
		vehicleTypes,
		vehicleSpawnMethod,
		entityPredicate,
		spawnReasonPredicate,
		locationPredicate,
		worldPredicate,
		environmentPredicate
	)

	class CustomJockeyByVehicle(
		chance: Double,
		vehicleTypes: Array<EntityType>,
		passengerTypes: Array<EntityType>,
		passengerSpawnMethod: (LivingEntity, Location, EntityType) -> Boolean = ::defaultPassengerSpawnMethod,
		entityPredicate: (LivingEntity) -> Boolean = defaultEntityPredicate,
		spawnReasonPredicate: (CreatureSpawnEvent.SpawnReason) -> Boolean = defaultSpawnReasonPredicate,
		locationPredicate: (Location) -> Boolean = defaultLocationPredicate,
		worldPredicate: (World) -> Boolean = defaultWorldPredicate,
		environmentPredicate: (World.Environment) -> Boolean = defaultEnvironmentPredicate
	) : CustomJockey(
		chance,
		vehicleTypes,
		passengerTypes,
		passengerSpawnMethod,
		entityPredicate,
		spawnReasonPredicate,
		locationPredicate,
		worldPredicate,
		environmentPredicate
	)

}