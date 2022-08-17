/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.modules.customjockeys

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.EntityType
import org.bukkit.entity.EntityType.CAVE_SPIDER
import org.bukkit.entity.EntityType.SPIDER
import org.bukkit.entity.LivingEntity
import org.bukkit.event.entity.CreatureSpawnEvent
import org.sucraft.common.entity.*

// Settings

val defaultEntityPredicate: (Any?) -> Boolean = { true }
val defaultSpawnReasonPredicate: (Any?) -> Boolean = { true }
val defaultLocationPredicate: (Any?) -> Boolean = { true }
val defaultWorldPredicate: (Any?) -> Boolean = { true }
val defaultEnvironmentPredicate: (Any?) -> Boolean = { true }

/**
 * This function provides a [CustomJockey.spawnJockeyEntity] implementation for spawning a vehicle
 * for a given passenger.
 */
fun defaultVehicleSpawnMethod(passenger: LivingEntity, location: Location, vehicleType: EntityType): Boolean {

	var vehicleTypeToUse = vehicleType
	// If the vehicle is a spider, adapt its size to the size of the passenger
	if (vehicleType == CAVE_SPIDER && passenger.isAdultAgeable)
		vehicleTypeToUse = SPIDER
	else if (vehicleType == SPIDER && passenger.isBabyAgeable)
		vehicleTypeToUse = CAVE_SPIDER

	// Spawn the vehicle
	val vehicle = location.world.spawnEntity(location, vehicleTypeToUse, CreatureSpawnEvent.SpawnReason.JOCKEY) {
		when (passenger.isAdultIfAgeableElseNull) {
			true -> it.setAdultIfAgeable()
			false -> it.setBabyIfAgeable()
			else -> {}
		}
	}
	// Check for the case the vehicle was not spawned successfully
	if (vehicle.isDead) {
		CustomJockeys.logger.warning(
			"Custom spawned jockey vehicle ${
				vehicle.longDescription
			} for passenger ${
				passenger.longDescription
			} was instantly dead"
		)
		return false
	}

	// Add the passenger to the vehicle, and check for the case it was unsuccessful
	if (!vehicle.addPassenger(passenger)) {
		CustomJockeys.logger.warning(
			"Failed to add jockey passenger ${
				passenger.longDescription
			} to newly spawned vehicle ${
				vehicle.longDescription
			}"
		)
		vehicle.remove()
		return false
	}

	return true

}

/**
 * This function provides a [CustomJockey.spawnJockeyEntity] implementation for spawning a passenger
 * for a given vehicle.
 */
fun defaultPassengerSpawnMethod(vehicle: LivingEntity, location: Location, passengerType: EntityType): Boolean {

	// Spawn the passenger
	val passenger = location.world.spawnEntity(location, passengerType, CreatureSpawnEvent.SpawnReason.MOUNT) {
		// Base the vehicle age on the vehicle size (assumed based on age or spider type)
		when (vehicle.isAdultIfAgeableElseNull ?: when (vehicle.type) {
			SPIDER -> true
			CAVE_SPIDER -> false
			else -> null
		}) {
			true -> it.setAdultIfAgeable()
			false -> it.setBabyIfAgeable()
			else -> {}
		}
	}

	// Check for the case the passenger was not spawned successfully
	if (passenger.isDead) {
		CustomJockeys.logger.warning(
			"Custom spawned jockey passenger ${
				passenger.longDescription
			} for vehicle ${
				vehicle.longDescription
			} was instantly dead"
		)
		return false
	}

	// Add the passenger to the vehicle, and check for the case it was unsuccessful
	if (!vehicle.addPassenger(passenger)) {
		CustomJockeys.logger.warning(
			"Failed to add newly spawned jockey passenger ${
				passenger.longDescription
			} to vehicle ${
				vehicle.longDescription
			}"
		)
		passenger.remove()
		return false
	}

	return true

}

// Class

/**
 * This class represents a custom jockey that can potentially spawn.
 */
abstract class CustomJockey(
	/**
	 * The chance of this jockey spawning for any spawn event of one of the [detectedEntityTypes].
	 */
	val chance: Double,
	/**
	 * The type of entities originally detected that can cause this jockey to spawn.
	 */
	val detectedEntityTypes: Array<EntityType>,
	/**
	 * The types of entity that may be spawned to form the jockey.
	 */
	val spawnedEntityTypes: Array<EntityType>,
	/**
	 * A function that is called when the jockey is desired to be spawned at the given location.
	 *
	 * This must return whether the spawn was successful
	 * (because if not, another jockey spawn may be attempted instead).
	 */
	val spawnJockeyEntity: (existingEntity: LivingEntity, location: Location, spawnedEntityType: EntityType) -> Boolean,
	/**
	 * This jockey can only spawn if this predicate holds true for the existing entity.
	 */
	val entityPredicate: (LivingEntity) -> Boolean = defaultEntityPredicate,
	/**
	 * This jockey can only spawn if this predicate holds true for this [CreatureSpawnEvent.SpawnReason]
	 * of the [original entity spawn event][CreatureSpawnEvent].
	 */
	val spawnReasonPredicate: (CreatureSpawnEvent.SpawnReason) -> Boolean = defaultSpawnReasonPredicate,
	/**
	 * This jockey can only spawn if this predicate holds true for the original spawn [Location].
	 */
	val locationPredicate: (Location) -> Boolean = defaultLocationPredicate,
	/**
	 * This jockey can only spawn if this predicate holds true for the original spawn [world][Location.getWorld].
	 */
	val worldPredicate: (World) -> Boolean = defaultWorldPredicate,
	/**
	 * This jockey can only spawn if this predicate holds true
	 * for the original spawn [world environment][World.getEnvironment].
	 */
	val environmentPredicate: (World.Environment) -> Boolean = defaultEnvironmentPredicate
) {

	/**
	 * @return Whether this jockey can spawn given the circumstances of a
	 * [spawn event][CreatureSpawnEvent].
	 */
	fun matchesSpawnEventCircumstances(
		entity: LivingEntity,
		location: Location,
		spawnReason: CreatureSpawnEvent.SpawnReason
	) = entityPredicate(entity) &&
			spawnReasonPredicate(spawnReason) &&
			locationPredicate(location) &&
			worldPredicate(location.world) &&
			environmentPredicate(location.world.environment) &&
			entity.type in detectedEntityTypes

	/**
	 * @return A random pick of entity that can be spawned by this jockey.
	 */
	fun getRandomSpawnedEntityType() = spawnedEntityTypes.random()

	fun evaluateChance() = Math.random() < chance

	/**
	 * This class represents a custom jockey that can potentially spawn,
	 * where the additionally spawned entity is the vehicle and the detected spawning entity is the passenger.
	 */
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

	/**
	 * This class represents a custom jockey that can potentially spawn,
	 * where the additionally spawned entity is the passenger and the detected spawning entity is the vehicle.
	 */
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