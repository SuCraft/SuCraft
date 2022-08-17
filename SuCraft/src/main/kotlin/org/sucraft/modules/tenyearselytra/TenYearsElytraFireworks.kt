/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.tenyearselytra

import org.bukkit.Color
import org.bukkit.DyeColor.*
import org.bukkit.FireworkEffect
import org.bukkit.Location
import org.bukkit.entity.Firework
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import org.sucraft.common.function.runEach
import org.sucraft.common.location.spawn
import org.sucraft.common.module.SuCraftComponent
import org.sucraft.common.scheduler.WhilePlayersAreOnlineTimerTask
import org.sucraft.common.scheduler.runLater
import org.sucraft.common.time.TimeInMinutes
import org.sucraft.common.time.TimeInTicks
import org.sucraft.modules.harmlessentities.isHarmless
import java.util.*

/**
 * Provides functionality for spawning the fireworks for the 10-years elytra effect.
 */
object TenYearsElytraFireworks : SuCraftComponent<TenYearsElytra>() {

	// Settings

	private val fireworkColors: Array<Color> = arrayOf(
		WHITE.color,
		WHITE.color,
		WHITE.color,
		WHITE.color,
		RED.color,
		GREEN.color,
		YELLOW.color,
		BLUE.color,
		CYAN.color,
		MAGENTA.color,
		ORANGE.color,
		LIME.color
	)

	// Immediately after (but we wait 1 tick so the velocity has been updated according to the firework boost)
	private val minFireworkDelay = TimeInTicks(1)
	private val fireworkInterval = TimeInTicks(4)
	private const val numberOfFireworks = 10

	// Causes exactly [numberOfFireworks] fireworks
	private val maxFireworkDelay = TimeInTicks(
		minFireworkDelay.ticks + fireworkInterval.ticks * numberOfFireworks - 1
	)

	private val cleanSpawnTasksMapInterval = TimeInMinutes(1)

	// Data

	private val spawnTasks: MutableMap<UUID, MutableList<BukkitTask>> = HashMap(20)

	// Schedule cleaning task

	override fun onInitialize() {
		super.onInitialize()
		// Schedule cleaning scheduled firework spawning tasks left behind for whatever reason
		WhilePlayersAreOnlineTimerTask({
			spawnTasks.iterator().run {
				runEach { if (value.isEmpty()) remove() }
			}
		}, cleanSpawnTasksMapInterval)
	}

	// Schedule fireworks

	fun Player.scheduleTenYearsFireworks() {
		// Clear the current tasks
		spawnTasks[uniqueId]?.runEach {
			try {
				cancel()
			} catch (_: Exception) {
			}
		}
		val newSpawnFireworksTasksForPlayer: MutableList<BukkitTask> =
			ArrayList<BukkitTask>(numberOfFireworks).also { spawnTasks[uniqueId] = it }
		var delay = minFireworkDelay
		while (delay <= maxFireworkDelay) {
			newSpawnFireworksTasksForPlayer.add(runLater(delay) { spawnTenYearsFireworks() })
			delay = TimeInTicks(delay.ticks + fireworkInterval.ticks)
		}
	}

	// Spawn fireworks

	private fun Player.spawnTenYearsFireworks() {
		if (isGliding)
			location.clone().subtract(velocity.clone().normalize().multiply(0.8)).spawnTenYearsFireworks()
	}

	private fun Location.spawnTenYearsFireworks() =
		spawn(Firework::class.java) {
			// Make these fireworks not do any damage
			isHarmless = true
			// Set the colors and effects
			fireworkMeta = fireworkMeta.apply {
				val color1: Color = fireworkColors.random()
				var color2: Color
				while (true) {
					color2 = fireworkColors.random()
					if (color1 != color2) break
				}
				addEffects(
					FireworkEffect.builder()
						.withColor(color1, color2)
						.with(FireworkEffect.Type.STAR)
						.withFlicker()
						.withTrail()
						.build()
				)
				power = 1
			}
		}.apply { detonate() }

}