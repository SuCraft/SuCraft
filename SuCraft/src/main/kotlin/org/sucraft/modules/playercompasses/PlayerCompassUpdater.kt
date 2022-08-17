/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.playercompasses

import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.CompassMeta
import org.sucraft.common.itemstack.meta.runMeta
import org.sucraft.common.module.SuCraftComponent
import org.sucraft.common.player.forEachPlayer
import org.sucraft.common.player.getOnlinePlayer
import org.sucraft.common.scheduler.WhilePlayersAreOnlineTimerTask
import org.sucraft.common.time.TimeInSeconds
import org.sucraft.common.time.TimeInTicks
import java.util.*
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.min

/**
 * Continuously updates player compasses to track their intended target.
 */
object PlayerCompassUpdater : SuCraftComponent<PlayerCompasses>(), PlayerCompassItemStackManipulation {

	// Settings

	private val checkForCompassesInInventoriesInterval = TimeInSeconds(1)
	private val updateCompassesInInventoriesInterval = TimeInTicks(1)

	// There are 29 different visual angles, so taking that with a little extra margin
	private const val minAngularDifferenceForCompassLocationUpdate = 2 * Math.PI / 32

	// Data

	/**
	 * A map that, for each player UUID that is tracked by some player compass of someone who is (or was recently)
	 * online, a list of pairs of the UUIDs of players that hold (or recently held) in their inventory a player compass
	 * that tracks the tracked player, along with the slot in their inventory that the compass is (or was recently) in.
	 */
	private val trackedBy: MutableMap<UUID, MutableList<Pair<UUID, Int>>> = HashMap(20)

	// Initialization

	override fun onInitialize() {
		super.onInitialize()
		// Schedule checking for compasses in inventories to update trackedBy
		WhilePlayersAreOnlineTimerTask(
			::checkForCompassesInInventories,
			checkForCompassesInInventoriesInterval,
			minimumDelayAfterTurningOn = TimeInTicks(1)
		)
		// Schedule updating the direction of known player compasses in inventories
		WhilePlayersAreOnlineTimerTask(
			::updateCompassesInInventories,
			updateCompassesInInventoriesInterval,
			minimumDelayAfterTurningOn = TimeInTicks(1)
		)
	}

	// Implementation

	/**
	 * Checks for compasses in inventories, and updates [trackedBy], so that we can run
	 * [updateCompassesInInventories] efficiently.
	 */
	private fun checkForCompassesInInventories() {
		trackedBy.clear()
		forEachPlayer { trackingPlayer ->
			trackingPlayer.inventory.forEachIndexed { slot, itemStack ->
				itemStack.playerCompassTrackedPlayer?.takeIf { it != trackingPlayer }?.let { trackedPlayer ->
					trackedBy.computeIfAbsent(trackedPlayer.uniqueId) { ArrayList(1) } +=
						trackingPlayer.uniqueId to slot
				}
			}
		}
	}

	/**
	 * Updates the direction of the known player compasses in inventories stored in [trackedBy].
	 */
	private fun updateCompassesInInventories() {
		forEachPlayer { trackedPlayer ->
			trackedBy[trackedPlayer.uniqueId]?.forEach { (trackingPlayerUUID, trackingCompassInventorySlot) ->
				trackingPlayerUUID.getOnlinePlayer()
					?.run {
						possiblyUpdateCompass(
							location,
							inventory.contents[trackingCompassInventorySlot],
							trackedPlayer
						)
					}
			}
		}
	}

	/**
	 * Updates the given [compass item stack][compassItemStack] to point to the given [player][trackedPlayer],
	 * assuming it is held in an inventory by a player at the given [tracker location][trackerLocation].
	 *
	 * @return Whether the compass was updated.
	 */
	fun possiblyUpdateCompass(
		trackerLocation: Location,
		compassItemStack: ItemStack?,
		trackedPlayer: Player
	): Boolean {

		// Make sure the item stack in the inventory is not null
		if (compassItemStack == null) return false
		// Check if the target is in the same world as the tracker
		if (trackedPlayer.world != trackerLocation.world) return false
		// Make sure the target is the player tracked by this compass
		if (trackedPlayer != compassItemStack.playerCompassTrackedPlayer) return false

		return compassItemStack.runMeta(CompassMeta::class) {

			// Make sure rotation of compass needs to be changed
			val isRotationallyDifferent = lodestone?.let { lodestone ->
				val currentAngle = atan2(lodestone.z - trackerLocation.z, lodestone.x - trackerLocation.x)
				val newAngle =
					atan2(
						trackedPlayer.location.z - trackerLocation.z,
						trackedPlayer.location.x - trackerLocation.x
					)
				var angularDifference = abs(currentAngle - newAngle)
				angularDifference = min(angularDifference, 2 * Math.PI - angularDifference)
				angularDifference >= minAngularDifferenceForCompassLocationUpdate
			} ?: true
			if (!isRotationallyDifferent) return@runMeta false

			// Update the compass
			isLodestoneTracked = false
			lodestone = trackedPlayer.location
			return@runMeta true

		}

	}

}