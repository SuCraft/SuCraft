/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.playercompass.tracker

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.CompassMeta
import org.sucraft.core.common.bukkit.persistentdata.PersistentDataShortcuts
import org.sucraft.core.common.bukkit.scheduler.WhilePlayersAreOnlineTimerTask
import org.sucraft.core.common.sucraft.log.SuCraftLogTexts
import org.sucraft.core.common.sucraft.persistentdata.DefaultSuCraftNamespace
import org.sucraft.core.common.sucraft.player.PlayerByUUID
import org.sucraft.core.common.sucraft.player.PlayerUUID
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.playercompass.main.SuCraftPlayerCompassPlugin
import java.util.*
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.min


object PlayerCompassTracker : SuCraftComponent<SuCraftPlayerCompassPlugin>(SuCraftPlayerCompassPlugin.getInstance()) {

	// Settings

	private const val checkForCompassesInInventoriesIntervalInTicks = 20L
	private const val updateCompassesInInventoriesIntervalInTicks = 1L
	private const val minAngularDifferenceForCompassLocationUpdate = 2 * Math.PI / 32 // There are 29 different visual angles, so taking that with a little extra margin

	val playerCompassTrackedPlayerPersistentDataNamespacedKey = DefaultSuCraftNamespace.getNamespacedKey("player_compass_tracked_player")
	// Checked for backwards compatability
	@Suppress("DEPRECATION")
	private val oldPlayerCompassTrackedPlayerPersistentDataNamespacedKey = NamespacedKey("martijnsextrafeatures", "compass_tracked_player")

	// Data

	private val trackedBy: MutableMap<PlayerUUID, MutableList<Pair<PlayerUUID, Int>>> = HashMap(20)

	// Initialization

	init {
		logger.info(SuCraftLogTexts.schedulingTasks)
		WhilePlayersAreOnlineTimerTask(plugin, ::checkForCompassesInInventories, interval = checkForCompassesInInventoriesIntervalInTicks, minimumDelayAfterTurningOn = 1)
		WhilePlayersAreOnlineTimerTask(plugin, ::updateCompassesInInventories, interval = updateCompassesInInventoriesIntervalInTicks, minimumDelayAfterTurningOn = 1)
	}

	// Implementation

	private fun checkForCompassesInInventories() {
		trackedBy.clear()
		for (player in Bukkit.getOnlinePlayers()) {
			val trackingPlayerIdentifier: PlayerUUID = PlayerUUID.get(player)
			player.inventory.contents!!.forEachIndexed forInventoryContents@{ i, itemStack ->
				val trackedPlayer = getTrackedPlayer(itemStack) ?: return@forInventoryContents
				if (trackedPlayer == player) return@forInventoryContents
				val trackedPlayerIdentifier = PlayerUUID.get(trackedPlayer)
				val trackersForTrackedPlayer: MutableList<Pair<PlayerUUID, Int>> = trackedBy[trackedPlayerIdentifier] ?:
					ArrayList<Pair<PlayerUUID, Int>>().also { trackedBy[trackedPlayerIdentifier] = it }
				trackersForTrackedPlayer.add(Pair(trackingPlayerIdentifier, i))
			}
		}
	}

	private fun updateCompassesInInventories() {
		for (player in Bukkit.getOnlinePlayers()) {
			trackedBy[PlayerUUID.get(player)]?.forEach { (trackerUUID, trackerItemSlot) ->
				trackerUUID.getOnlinePlayer()?.let { possiblyUpdateCompass(it.location, it.inventory.contents!![trackerItemSlot], player) }
			}
		}
	}

	fun getTrackedUUID(itemStack: ItemStack?): UUID? =
		itemStack
			?.takeIf { itemStack.type == Material.COMPASS }
			?.takeIf { itemStack.hasItemMeta() }
			?.let { itemStack.itemMeta as? CompassMeta }
			?.let { PersistentDataShortcuts.UUID[it, playerCompassTrackedPlayerPersistentDataNamespacedKey] ?: PersistentDataShortcuts.UUID[it, oldPlayerCompassTrackedPlayerPersistentDataNamespacedKey] }

	fun getTrackedPlayerUUID(itemStack: ItemStack?): PlayerUUID? = getTrackedUUID(itemStack)?.let(PlayerUUID::get)

	fun getTrackedPlayer(itemStack: ItemStack?): Player? = getTrackedUUID(itemStack)?.let(PlayerByUUID::getOnline)

	fun possiblyUpdateCompass(trackerLocation: Location, compassItemStack: ItemStack?, targetedPlayer: Player): Boolean {

		// Check if the target is in the same world as the tracker
		if (targetedPlayer.world != trackerLocation.world) return false
		// Make sure the player tracked by this compass is online
		val trackedPlayer = getTrackedPlayer(compassItemStack) ?: return false
		// Make sure the target is the player tracked by this compass
		if (trackedPlayer != targetedPlayer) return false

		// Make sure rotation of compass needs to be changed
		val compassMeta = compassItemStack!!.itemMeta as CompassMeta
		val isRotationallyDifferent = compassMeta.lodestone?.let { lodestone ->
			val currentAngle = atan2(lodestone.z - trackerLocation.z, lodestone.x - trackerLocation.x)
			val newAngle = atan2(trackedPlayer.location.z - trackerLocation.z, trackedPlayer.location.x - trackerLocation.x)
			var angularDifference = abs(currentAngle - newAngle)
			angularDifference = min(angularDifference, 2 * Math.PI - angularDifference)
			angularDifference >= minAngularDifferenceForCompassLocationUpdate
		} ?: true
		if (!isRotationallyDifferent) return false

		// Update the compass
		compassMeta.isLodestoneTracked = false
		compassMeta.lodestone = trackedPlayer.location
		compassItemStack.itemMeta = compassMeta
		return true

	}

}