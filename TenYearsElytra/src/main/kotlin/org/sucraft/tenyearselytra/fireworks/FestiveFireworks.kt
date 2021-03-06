/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.tenyearselytra.fireworks

import org.bukkit.Color
import org.bukkit.DyeColor
import org.bukkit.FireworkEffect
import org.bukkit.Location
import org.bukkit.entity.Firework
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.FireworkMeta
import org.bukkit.scheduler.BukkitTask
import org.sucraft.core.common.bukkit.scheduler.RunInFuture
import org.sucraft.core.common.bukkit.scheduler.WhilePlayersAreOnlineTimerTask
import org.sucraft.core.common.sucraft.delegate.HarmlessEntities
import org.sucraft.core.common.sucraft.log.SuCraftLogTexts
import org.sucraft.core.common.sucraft.player.PlayerUUID
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.tenyearselytra.main.SuCraftTenYearsElytraPlugin

object FestiveFireworks : SuCraftComponent<SuCraftTenYearsElytraPlugin>(SuCraftTenYearsElytraPlugin.getInstance()) {

	// Settings

	private val fireworkColors: Array<Color> = arrayOf(
		DyeColor.WHITE.color,
		DyeColor.WHITE.color,
		DyeColor.WHITE.color,
		DyeColor.WHITE.color,
		DyeColor.RED.color,
		DyeColor.GREEN.color,
		DyeColor.YELLOW.color,
		DyeColor.BLUE.color,
		DyeColor.CYAN.color,
		DyeColor.MAGENTA.color,
		DyeColor.ORANGE.color,
		DyeColor.LIME.color
	)

	private const val minFireworkDelay = 1L // In ticks, so: immediately after (but we wait 1 tick so the velocity has been updated according to the firework boost)
	private const val fireworkInterval = 4L // In ticks
	private const val numberOfFireworks = 10
	private const val maxFireworkDelay = minFireworkDelay + fireworkInterval * numberOfFireworks - 1 // In ticks, so: causes exactly [numberOfFireworks] fireworks

	private const val cleanSpawnTasksMapInterval = 20L * 60

	// Data

	private val spawnTasks: MutableMap<PlayerUUID, MutableList<BukkitTask>> = HashMap(20)

	// Schedule cleaning task

	init {
		logger.info(SuCraftLogTexts.schedulingTasks)
		WhilePlayersAreOnlineTimerTask(plugin, {
			with (spawnTasks.iterator()) {
				forEach { if (it.value.isEmpty()) remove() }
			}
		}, cleanSpawnTasksMapInterval)
	}

	// Schedule fireworks

	fun scheduleFireworks(player: Player) {
		val uuid: PlayerUUID = PlayerUUID.get(player)
		// Clear the current tasks
		spawnTasks[uuid]?.forEach {
			try {
				it.cancel()
			} catch (_: Exception) {
			}
		}
		val newSpawnFireworksTasksForPlayer: MutableList<BukkitTask> = ArrayList<BukkitTask>(numberOfFireworks).also { spawnTasks[uuid] = it }
		var delay: Long = minFireworkDelay
		while (delay <= maxFireworkDelay) {
			newSpawnFireworksTasksForPlayer.add(RunInFuture.forPlayerIfOnline(plugin, player, ::spawnFireworks, delay))
			delay += fireworkInterval
		}
	}

	// Spawn fireworks

	private fun spawnFireworks(player: Player) {
		if (player.isGliding)
			spawnFireworks(player.location.clone().subtract(player.velocity.clone().normalize().multiply(0.8)))
	}

	private fun spawnFireworks(location: Location) {
		location.world.spawn(location, Firework::class.java) { firework ->
			HarmlessEntities.get().setHarmless(firework, true)
			val meta: FireworkMeta = firework.fireworkMeta
			val color1: Color = fireworkColors.random()
			var color2: Color
			while (true) {
				color2 = fireworkColors.random()
				if (color1 != color2) break
			}
			val effect = FireworkEffect.builder()
				.withColor(color1, color2)
				.with(FireworkEffect.Type.STAR)
				.withFlicker()
				.withTrail()
				.build()
			meta.addEffects(effect)
			meta.power = 1
			firework.fireworkMeta = meta
		}.detonate()
	}

}