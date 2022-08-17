/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.harmlessentities

import org.bukkit.NamespacedKey
import org.bukkit.entity.Entity
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.sucraft.common.event.on
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.namespacedkey.toSuCraftNamespacedKey

/**
 * Allows for [entities][Entity] to marked as harmless, meaning they cannot harm players or other entities.
 */
object HarmlessEntities : SuCraftModule<HarmlessEntities>() {

	// Settings

	val harmlessEntityKey = "harmless".toSuCraftNamespacedKey()

	// Checked for backwards compatability with old versions
	@Suppress("DEPRECATION")
	val oldHarmlessEntityKey = NamespacedKey("harmlessentities", "harmless")

	// Events

	init {
		// Cancel damage to entities inflicted by harmless entities,
		// including by entities that are transitively harmless (such as arrows fired by a harmless skeleton)
		on(EntityDamageByEntityEvent::class, EventPriority.LOWEST) {
			if (damager.isTransitivelyHarmless)
				isCancelled = true
		}
	}

}