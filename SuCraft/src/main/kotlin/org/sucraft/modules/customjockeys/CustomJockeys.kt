/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.customjockeys

import org.bukkit.entity.EntityType
import org.sucraft.common.module.SuCraftModule
import org.sucraft.modules.includedcustomjockeys.IncludedCustomJockeys
import org.sucraft.modules.zombiehorsejockeys.ZombieHorseJockeys
import java.util.*

/**
 * Utility module that makes it possible to spawn custom combinations of jockeys.
 *
 * This module does not actually add any jockeys yet. Jockeys are added by at least the following modules:
 * - [IncludedCustomJockeys]
 * - [ZombieHorseJockeys]
 */
object CustomJockeys : SuCraftModule<CustomJockeys>() {

	// Data

	/**
	 * The custom jockey types that have been registered.
	 */
	private val registeredTypesByDetectedEntityType: MutableMap<EntityType, Array<CustomJockey>> =
		EnumMap(EntityType::class.java)

	// Provided functionality

	/**
	 * Register a new type of custom jockey.
	 */
	fun registerJockey(jockey: CustomJockey) =
		jockey.detectedEntityTypes.forEach {
			registeredTypesByDetectedEntityType.compute(it) { _, existingArray ->
				existingArray?.let { existingArray + arrayOf(jockey) } ?: arrayOf(jockey)
			}
		}

	/**
	 * @return The custom jockey types that have been registered for the given [entity type][EntityType].
	 */
	fun getRegisteredJockeys(entityType: EntityType) =
		registeredTypesByDetectedEntityType[entityType] ?: emptyArray()

	// Components

	override val components = listOf(
		SpawnCustomJockeys
	)

}