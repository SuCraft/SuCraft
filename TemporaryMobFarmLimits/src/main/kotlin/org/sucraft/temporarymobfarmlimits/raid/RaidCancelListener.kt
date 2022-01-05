/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.temporarymobfarmlimits.raid

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityPotionEffectEvent
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause.*
import org.bukkit.event.raid.RaidTriggerEvent
import org.bukkit.potion.PotionEffectType
import org.sucraft.core.common.bukkit.chunk.ChunkCoordinates
import org.sucraft.core.common.sucraft.delegate.MobFarmWeight
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.temporarymobfarmlimits.main.SuCraftTemporaryMobFarmLimitsPlugin


object RaidCancelListener : SuCraftComponent<SuCraftTemporaryMobFarmLimitsPlugin>(SuCraftTemporaryMobFarmLimitsPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	fun onEntityPotionEffect(event: EntityPotionEffectEvent) {

		if (MobFarmWeight.get().getWeight() >= 1e-6) return

		if (event.action != EntityPotionEffectEvent.Action.REMOVED) return
		if (event.modifiedType != PotionEffectType.BAD_OMEN) return
		when (event.cause) {
			COMMAND, DEATH, EXPIRATION, MILK, PLUGIN, TOTEM -> return
			else -> {}
		}

		if (RaidMeasurementData.isProbablyFarm(ChunkCoordinates.get(event.entity.location)))
			event.isCancelled = true

	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	fun onRaidTrigger(event: RaidTriggerEvent) {

		if (MobFarmWeight.get().getWeight() >= 1e-6) return

		if (sequenceOf(event.raid.location, event.player.location)
				.any { RaidMeasurementData.isProbablyFarm(ChunkCoordinates.get(it)) }
		)
			event.isCancelled = true

	}

}