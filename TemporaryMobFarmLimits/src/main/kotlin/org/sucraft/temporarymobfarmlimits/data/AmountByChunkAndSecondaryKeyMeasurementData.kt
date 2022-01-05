/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.temporarymobfarmlimits.data

import org.bukkit.Bukkit
import org.bukkit.Location
import org.sucraft.core.common.bukkit.chunk.ChunkCoordinates
import org.sucraft.core.common.bukkit.chunk.SecondaryKeyChunkRadiusMap
import org.sucraft.core.common.bukkit.player.ClosestPlayerFinder
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.temporarymobfarmlimits.main.SuCraftTemporaryMobFarmLimitsPlugin


abstract class AmountByChunkAndSecondaryKeyMeasurementData<K>(
	protected val measurementName: String,
	// Settings
	protected val chunkAmountToClassifyAsFarm: Long,
	protected val markChunkDistance: Int,
	protected val decreaseChunkAmountsIntervalInTicks: Long,
	protected val decreaseChunkAmountsAmount: Long,
) : SuCraftComponent<SuCraftTemporaryMobFarmLimitsPlugin>(SuCraftTemporaryMobFarmLimitsPlugin.getInstance()) {

	// Settings

	protected open fun constructAmountsMap(): SecondaryKeyChunkRadiusMap<K, Long> = SecondaryKeyChunkRadiusMap()

	// Data

	private var amounts: SecondaryKeyChunkRadiusMap<K, Long>? = null

	private fun getAmounts(): SecondaryKeyChunkRadiusMap<K, Long> {
		if (amounts == null) amounts = constructAmountsMap()
		return amounts!!
	}

	// Initialization

	init {
		// Scheduling decreasing measured recent creature spawns
		Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
			val iterator: MutableIterator<Map.Entry<Long, MutableMap<K, Long>>> = getAmounts().iterator()
			while (iterator.hasNext()) {
				val mapForChunk = iterator.next().value
				val iteratorForChunk = mapForChunk.iterator()
				while (iteratorForChunk.hasNext()) {
					val amount = iteratorForChunk.next().value
					if (amount <= decreaseChunkAmountsAmount)
						iteratorForChunk.remove()
				}
				if (mapForChunk.isEmpty())
					iterator.remove()
				else
					mapForChunk.replaceAll { _, amount -> amount - decreaseChunkAmountsAmount }
			}
		}, decreaseChunkAmountsIntervalInTicks, decreaseChunkAmountsIntervalInTicks)
	}

	// Implementation

	fun incrementAmount(secondaryKey: K, location: Location) {

		val chunk = ChunkCoordinates.get(location)

		getAmounts().updateWithinSquareRadius(chunk, secondaryKey, { existingAmount, relativeChunk ->

			// Calculate the new amount
			val newAmount = (existingAmount ?: 0) + 1

			// Log to console if this chunk has become marked as a farm
			if (
				(existingAmount?.let { it < chunkAmountToClassifyAsFarm} != false)
				&& newAmount >= chunkAmountToClassifyAsFarm
			) {
				val closestPlayerName = ClosestPlayerFinder.getClosestPlayer(location)?.name ?: "<none>"
				logger.info("The chunk $relativeChunk has been marked as a farm of type $measurementName by key $secondaryKey, with recent amount being $newAmount and the closest player being $closestPlayerName")
			}

			// Return the value to place in the map
			newAmount

		}, markChunkDistance)

	}

	fun isProbablyFarm(secondaryKey: K, chunk: ChunkCoordinates) =
		(getAmounts()[chunk, secondaryKey] ?: 0) >= chunkAmountToClassifyAsFarm

}