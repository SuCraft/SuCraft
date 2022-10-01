/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.dispenserscanlightportals

import org.bukkit.event.EventPriority
import org.bukkit.Material.*
import org.bukkit.event.block.BlockIgniteEvent
import org.sucraft.common.block.BlockCoordinates
import org.sucraft.common.block.BlockCoordinates.Companion.coordinates
import org.sucraft.common.block.orthogonalLoadedNeighbors
import org.sucraft.common.event.on
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.scheduler.runNextTick

/**
 * WorldGuard does not allow dispensers to light portals when fire spreading is disabled.
 * This module re-adds that behavior.
 */
object DispensersCanLightPortals : SuCraftModule<DispensersCanLightPortals>() {

	// Data

	private val ignitionsNotCancelledBeforeWorldGuard: MutableSet<BlockCoordinates> = HashSet(1)

	// Implementation

	private val BlockIgniteEvent.isDispenserPortalIgnition
		get(): Boolean {

			// Make sure the block ignited is air
			if (!block.type.isAir) return false

			// Make sure the block is ignited by a dispenser
			if (ignitingBlock?.type != DISPENSER) return false

			// Make sure the cause was flint and steel
			if (cause != BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL) return false

			// Make sure the block is touching obsidian
			if (block.orthogonalLoadedNeighbors.none {
					when (it.type) {
						OBSIDIAN, SUCRAFT_OBSIDIAN_STAIRS, SUCRAFT_OBSIDIAN_SLAB -> true
						else -> false
					}
				}) return false

			return true

		}

	// Events

	init {

		// Listen for block ignitions with priority LOW, which is called before
		// WorldGuard's event listener, which uses priority HIGH, to determine dispenser portal ignitions that
		// were not cancelled before
		on(BlockIgniteEvent::class, priority = EventPriority.LOW, ignoreCancelled = false) {

			if (isCancelled) return@on

			// Make sure the event is a dispenser portal ignition
			if (!isDispenserPortalIgnition) return@on

			// Store the coordinates as a non-cancelled dispenser portal ignition
			val coordinates = block.coordinates
			ignitionsNotCancelledBeforeWorldGuard.add(coordinates)
			runNextTick {
				ignitionsNotCancelledBeforeWorldGuard.remove(coordinates)
			}

		}

		// Listen for block ignitions with priority HIGHEST, which is called after
		// WorldGuard's event listener, which uses priority HIGH, to find out if WorldGuard cancelled it
		on(BlockIgniteEvent::class, priority = EventPriority.HIGHEST, ignoreCancelled = false) {
			if (ignitionsNotCancelledBeforeWorldGuard.remove(block.coordinates) && isCancelled) {
				isCancelled = false
			}
		}

	}

}