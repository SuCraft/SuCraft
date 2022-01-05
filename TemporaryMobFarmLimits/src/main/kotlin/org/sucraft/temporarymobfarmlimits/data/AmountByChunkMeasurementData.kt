/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.temporarymobfarmlimits.data

import org.bukkit.Bukkit
import org.bukkit.Location
import org.sucraft.core.common.bukkit.chunk.ChunkCoordinates
import org.sucraft.core.common.bukkit.chunk.ChunkRadiusMap
import org.sucraft.core.common.bukkit.player.ClosestPlayerFinder
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.temporarymobfarmlimits.main.SuCraftTemporaryMobFarmLimitsPlugin


abstract class AmountByChunkMeasurementData(
	protected val measurementName: String,
	// Settings
	protected val chunkAmountToClassifyAsFarm: Long,
	protected val markChunkDistance: Int,
	protected val decreaseChunkAmountsIntervalInTicks: Long,
	protected val decreaseChunkAmountsAmount: Long,
) : SuCraftComponent<SuCraftTemporaryMobFarmLimitsPlugin>(SuCraftTemporaryMobFarmLimitsPlugin.getInstance()) {

	// Data

	protected val amounts: ChunkRadiusMap<Long> = ChunkRadiusMap()

	// Initialization

	init {
		// Scheduling decreasing measured recent creature spawns
		Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
			val iterator: MutableIterator<Map.Entry<Long, Long>> = amounts.iterator()
			while (iterator.hasNext()) {
				val amount = iterator.next().value
				if (amount <= decreaseChunkAmountsAmount)
					iterator.remove()
			}
			amounts.getMutableInternalMap().replaceAll { _, amount -> amount - decreaseChunkAmountsAmount }
		}, decreaseChunkAmountsIntervalInTicks, decreaseChunkAmountsIntervalInTicks)
	}

	// Implementation

	fun incrementAmount(location: Location) {

		val chunk = ChunkCoordinates.get(location)

		amounts.updateWithinSquareRadius(chunk, { existingAmount, relativeChunk ->

			// Calculate the new amount
			val newAmount = (existingAmount ?: 0) + 1

			// Log to console if this chunk has become marked as a farm
			if (newAmount >= chunkAmountToClassifyAsFarm) {
				val closestPlayerName = ClosestPlayerFinder.getClosestPlayer(location)?.name ?: "<none>"
				logger.info("The chunk $relativeChunk has been marked as a farm of type $measurementName, with recent amount being $newAmount and the closest player being $closestPlayerName")
			}

			// Return the value to place in the map
			newAmount

		}, markChunkDistance)

	}

	fun isProbablyFarm(chunk: ChunkCoordinates) =
		(amounts[chunk] ?: 0) >= chunkAmountToClassifyAsFarm

}