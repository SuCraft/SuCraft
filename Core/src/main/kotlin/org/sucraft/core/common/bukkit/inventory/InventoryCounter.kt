/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.inventory

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.sucraft.core.common.bukkit.item.EmptyItemStack
import java.util.*


/**
 * Reasonably optimized inventory counting operations
 */
object InventoryCounter {

	fun getOrderedSnapshot(inventory: Inventory): Array<ItemStack?> =
		Array(inventory.contents.size) { inventory.contents[it]?.clone() }

	fun getOrderedSnapshot(inventory: Inventory, preservePredicate: (ItemStack) -> Boolean): Array<ItemStack?> =
		Array(inventory.contents.size) { inventory.contents[it]?.takeIf(preservePredicate)?.clone() }

	// Implementation switched because keeping empty spaces was the point of it being ordered
//	fun getOrderedSnapshot(inventory: Inventory, preservePredicate: (ItemStack) -> Boolean): List<ItemStack> =
//		inventory.contents.asSequence().filter { !EmptyItemStack.isEmpty(it) && preservePredicate(it) }.map(ItemStack::clone).toList()

	/**
	 * @return A map where all keys are itemstacks with amount 1
	 */
	fun getUnorderedSnapshot(inventory: Inventory, preservePredicate: ((ItemStack) -> Boolean)? = null): Map<ItemStack, Int> {
		var interestingInventoryContents = inventory.contents.asSequence().filter { EmptyItemStack.isNotEmpty(it) }
		if (preservePredicate != null)
			interestingInventoryContents = interestingInventoryContents.filter(preservePredicate)
		val map: MutableMap<ItemStack, Int> = HashMap(interestingInventoryContents.count())
		@Suppress("NestedLambdaShadowedImplicitParameter")
		interestingInventoryContents.map { it.clone() }.map{ it.also { it.amount = 1 } }.forEach { map[it] = map.getOrDefault(it, 0) + it.amount }
		return map
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun getDifferenceMap(before: Array<ItemStack?>, after: Array<ItemStack?>) =
		getDifferenceMap(
			before.asSequence(),
			after.asSequence()
		)

	inline fun getDifferenceMap(before: Array<ItemStack?>, after: Array<ItemStack?>, crossinline preservePredicate: (ItemStack) -> Boolean) =
		getDifferenceMap(
			before.asSequence().filter { it == null || preservePredicate(it) },
			after.asSequence().filter { it == null || preservePredicate(it) }
		)

	/**
	 * [before] and [after] must (and are assumed to) have the same size
	 * @return A map where all keys are itemstacks with amount 1
	 */
	@Throws(IllegalArgumentException::class)
	fun getDifferenceMap(before: Sequence<ItemStack?>, after: Sequence<ItemStack?>): Map<ItemStack, Int> {
		val map: MutableMap<ItemStack, Int> = HashMap(3)
		for ((beforeItemStack, afterItemStack) in before.zip(after)) {
			if (beforeItemStack == null) {
				if (afterItemStack != null) {
					map[afterItemStack] = map.getOrDefault(afterItemStack, 0) + afterItemStack.amount
				}
			} else {
				if (afterItemStack == null) {
					map[beforeItemStack] = map.getOrDefault(beforeItemStack, 0) - beforeItemStack.amount
				} else {
					if (beforeItemStack.isSimilar(afterItemStack)) {
						val change = afterItemStack.amount - beforeItemStack.amount
						if (change != 0) map[beforeItemStack] = map.getOrDefault(beforeItemStack, 0) + change
					} else {
						map[beforeItemStack] = map.getOrDefault(beforeItemStack, 0) - beforeItemStack.amount
						map[afterItemStack] = map.getOrDefault(afterItemStack, 0) + afterItemStack.amount
					}
				}
			}
		}
		return map.filterValues { it != 0 }
	}

	/**
	 * All keys in [before] and [after] must be itemstacks with amount 1
	 * @return A map where all keys are itemstacks with amount 1
	 */
	fun getDifferenceMap(before: Map<ItemStack, Int>, after: Map<ItemStack, Int>): Map<ItemStack, Int> {
		val map: MutableMap<ItemStack, Int> = HashMap(3)
		for ((itemStack, beforeAmount) in before) {
			val change = (after[itemStack] ?: 0) - beforeAmount
			if (change != 0) map[itemStack] = change
		}
		for ((itemStack, afterAmount) in after) {
			if (itemStack !in before) map[itemStack] = afterAmount
		}
		return map
	}

	/**
	 * All keys in [before] and [after] must be itemstacks with amount 1
	 * @return A map where all keys are itemstacks with amount 1
	 */
	fun getDifferenceMap(before: Map<ItemStack, Int>, after: Map<ItemStack, Int>, preservePredicate: (ItemStack) -> Boolean): Map<ItemStack, Int> {
		val map: MutableMap<ItemStack, Int> = HashMap(3)
		for ((itemStack, beforeAmount) in before.asSequence().filter { (itemStack, _) -> preservePredicate(itemStack) }) {
			val change = (after[itemStack] ?: 0) - beforeAmount
			if (change != 0) map[itemStack] = change
		}
		for ((itemStack, afterAmount) in after.asSequence().filter { (itemStack, _) -> preservePredicate(itemStack) }) {
			if (itemStack !in before) map[itemStack] = afterAmount
		}
		return map
	}

}