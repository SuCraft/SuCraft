/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.raiddifficulty.raid

import org.bukkit.Difficulty
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityPotionEffectEvent
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause.*
import org.bukkit.event.raid.RaidTriggerEvent
import org.bukkit.potion.PotionEffectType
import org.sucraft.core.common.bukkit.scheduler.RunInFuture
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.raiddifficulty.main.SuCraftRaidDifficultyPlugin
import kotlin.math.max

object RaidStartListener : SuCraftComponent<SuCraftRaidDifficultyPlugin>(SuCraftRaidDifficultyPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onEntityPotionEffect(event: EntityPotionEffectEvent) {

		if (event.action != EntityPotionEffectEvent.Action.REMOVED) return
		if (event.modifiedType != PotionEffectType.BAD_OMEN) return
		when (event.cause) {
			COMMAND, DEATH, EXPIRATION, MILK, PLUGIN, TOTEM -> return
			else -> {}
		}

		// Cancel removing the bad omen effect to make sure that if a raid was already triggered, it will be triggered again
		event.isCancelled = true
		TemporaryWorldDifficulty.setToHardTemporarily(event.entity.world)

	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onRaidTrigger(event: RaidTriggerEvent) {

		// Cancel raids that do not conform to the requirements (another raid with new properties will be triggered immediately since we cancelled removing the bad omen effect)
		if (event.world.difficulty != Difficulty.HARD) {
			event.isCancelled = true
			TemporaryWorldDifficulty.setToHardTemporarily(event.world)
			return
		}
		if (event.raid.totalWaves < 7) {
			event.isCancelled = true
			return
		}

		// The raid conforms to the requirements, let this raid happen and remove the bad omen now
		RunInFuture.forPlayerIfOnline(plugin, event.player, { it.removePotionEffect(PotionEffectType.BAD_OMEN) } )
		// Log to console
		logger.info("A raid was triggered by ${event.player.name} at ${event.raid.location} with bad omen level ${max(event.raid.badOmenLevel, event.player.getPotionEffect(PotionEffectType.BAD_OMEN)?.amplifier ?: 0) + 1}, ${event.raid.totalWaves} total waves and ${event.raid.totalGroups} total groups")

	}

}