/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.nojumpingmobfalldamage

import com.sk89q.worldedit.world.entity.EntityTypes.MOOSHROOM
import org.bukkit.entity.EntityType.*
import org.bukkit.event.EventPriority.NORMAL
import org.bukkit.event.entity.EntityDamageEvent
import org.sucraft.common.event.on
import org.sucraft.common.module.SuCraftModule

/**
 * Disables or decreases fall damages for mobs that frequently jump, and thereby sometimes unintentionally
 * jump to their death.
 */
object NoJumpingMobFallDamage : SuCraftModule<NoJumpingMobFallDamage>() {

	// Settings

	/**
	 * Fall damage is disabled for these types of entities.
	 */
	private val entityTypesThatDoNotTakeFallDamage = setOf(
		AXOLOTL, // Not a jumping mob, but it's still lame if they take fall damage
		BEE, // Not a jumping mob, but it's still lame if they take fall damage
		CAT,
		FOX,
		FROG,
		GOAT,
		MAGMA_CUBE,
		OCELOT,
		PANDA, // Not a jumping mob, but it's still lame if they take fall damage
		PARROT, // Not a jumping mob, but it's still lame if they take fall damage
		RABBIT,
		SLIME,
		TADPOLE, // Not a jumping mob, but it's still lame if they take fall damage
		TURTLE // Not a jumping mob, but it's still lame if they take fall damage
	)

	private const val rideableMobFallDamageFactor = 0.25
	private const val villagerFallDamageFactor = 0.25
	private const val farmAnimalsFallDamageFactor = 0.4
	private const val petFallDamageFactor = 0.2

	/**
	 * Fall damage is scaled for these types of entities.
	 */
	private val entityTypeFallDamageFactors = mapOf(
		// Rideable mobs
		DONKEY to rideableMobFallDamageFactor,
		HORSE to rideableMobFallDamageFactor,
		LLAMA to rideableMobFallDamageFactor,
		MULE to rideableMobFallDamageFactor,
		SKELETON_HORSE to rideableMobFallDamageFactor,
		STRIDER to rideableMobFallDamageFactor,
		TRADER_LLAMA to rideableMobFallDamageFactor,
		ZOMBIE_HORSE to rideableMobFallDamageFactor,
		// Villagers
		VILLAGER to villagerFallDamageFactor,
		WANDERING_TRADER to villagerFallDamageFactor,
		ZOMBIE_VILLAGER to villagerFallDamageFactor, // Count as villagers in case they were meant to be cured later
		// Farm animals
		CHICKEN to farmAnimalsFallDamageFactor,
		COW to farmAnimalsFallDamageFactor,
		MOOSHROOM to farmAnimalsFallDamageFactor,
		PIG to farmAnimalsFallDamageFactor,
		SHEEP to farmAnimalsFallDamageFactor,
		// Pets
		DOLPHIN to petFallDamageFactor,
		WOLF to petFallDamageFactor
	)

	// Events

	init {
		on(EntityDamageEvent::class, priority = NORMAL) {
			// Cancel fall damage
			if (entityType in entityTypesThatDoNotTakeFallDamage) {
				damage = 0.0
				isCancelled = true
				return@on
			}
			// Scale fall damage
			entityTypeFallDamageFactors[entityType]?.let {
				damage *= it
			}
		}
	}

}