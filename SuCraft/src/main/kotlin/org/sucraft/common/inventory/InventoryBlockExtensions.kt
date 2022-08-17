/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.inventory

import org.bukkit.block.DoubleChest
import org.bukkit.inventory.BlockInventoryHolder
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.sucraft.common.block.BlockCoordinates.Companion.coordinates

/**
 * @return The single [Block] associated with this [InventoryHolder],
 * or null if the [InventoryHolder] is not associated with a single block.
 *
 * Currently, the only type of [InventoryHolder] which has a single block associated with it
 * is [BlockInventoryHolder].
 */
val InventoryHolder.block
	get() = (this as? BlockInventoryHolder)?.block

/**
 * @return A sequence of the (potentially non-distinct) [blocks][Block] associated with this [InventoryHolder].
 * This is:
 * - an empty sequence if the [InventoryHolder] is not associated with any blocks,
 * - a singleton sequence if the [InventoryHolder] is [associated with a single block][InventoryHolder.block],
 * - a sequence of up to 2 blocks if the inventory is a valid physical [double chest][DoubleChest].
 */
val InventoryHolder.blocks
	get() = when (this) {
		is DoubleChest -> sequenceOf(leftSide?.block, rightSide?.block).filterNotNull()
		is BlockInventoryHolder -> sequenceOf(block)
		else -> emptySequence()
	}

/**
 * @return The single [Block] associated with this [Inventory],
 * or null if the [Inventory] is not associated with a single block.
 *
 * Currently, there are two ways for an [Inventory] to have a single block associated with it
 * (listed in order of being returned if applicable):
 * - its [holder][Inventory.getHolder] is a [BlockInventoryHolder], or
 * - it has a [location][Inventory.getLocation].
 */
val Inventory.block
	get() = holder?.block ?: location?.block

/**
 * @return A sequence of the (potentially non-distinct) [blocks][Block] associated with this [Inventory].
 * This consists of:
 * - any blocks [associated with the inventory holder][InventoryHolder.blocks], and
 * - the block at the inventory's [location][Inventory.getLocation], if any.
 */
val Inventory.blocks
	get() = sequence {
		holder?.blocks?.forEach { yield(it) }
		location?.block?.let { yield(it) }
	}

/**
 * @see InventoryHolder.block
 */
val InventoryHolder.blockCoordinates
	get() = block?.coordinates

/**
 * @see InventoryHolder.blocks
 */
val InventoryHolder.blocksCoordinates
	get() = blocks.map { it.coordinates }

/**
 * @see InventoryHolder.blockCoordinates
 */
val Inventory.blockCoordinates
	get() = block?.coordinates

/**
 * @see InventoryHolder.blocksCoordinates
 */
val Inventory.blocksCoordinates
	get() = blocks.map { it.coordinates }