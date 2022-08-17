/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.tenyearselytra

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material.ELYTRA
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.sucraft.common.itemstack.meta.customModelData
import org.sucraft.common.persistentdata.removePersistent
import org.sucraft.common.persistentdata.typedvalueextensions.getPersistentUUID
import org.sucraft.common.persistentdata.typedvalueextensions.setPersistentUUID

private val tenYearsElytraEffectAdderKey by lazy {
	@Suppress("DEPRECATION")
	NamespacedKey("martijnsextrafeatures", "elytra_ten_years")
}
private const val tenYearsElytraCustomModelData = 1

/**
 * Provides some [ItemStack] extensions related to 10-years elytras to any class that implements this interface.
 */
interface TenYearsElytraItemStackExtensions {

	// Extensions

	val ItemStack.isTenYearsElytra get() = tenYearsElytraEffectAdder != null

	val ItemStack.tenYearsElytraEffectAdder get() = getPersistentUUID(tenYearsElytraEffectAdderKey)

	fun ItemStack.getWithTenYearsEffect(adder: Player): ItemStack {
		require(type == ELYTRA) { "this item stack is not an elytra" }
		require(!isTenYearsElytra) { "this item stack already has the 10-years effect" }
		return clone().apply {
			// Add the player that added the effect
			setPersistentUUID(tenYearsElytraEffectAdderKey, adder.uniqueId)
			// Insert the lore lines one by one
			val lore = lore() ?: ArrayList(loreLines.size)
			for (i in loreLines.size - 1 downTo 0)
				if (!lore.contains(loreLines[i])) lore.add(0, loreLines[i])
			lore(lore)
			// Set the custom model data
			customModelData = tenYearsElytraCustomModelData
		}
	}

	fun ItemStack.getWithoutTenYearsEffect(): ItemStack {
		require(isTenYearsElytra) { "this item stack does not have the 10-years effect" }
		return clone().apply {
			// Remove the player that added the effect
			removePersistent(tenYearsElytraEffectAdderKey)
			// Remove the specific lore
			lore()?.let(::ArrayList)?.let { lore ->
				var removedAnyLoreLines = false
				keepRemovingLore@ while (true) {
					for (loreLine in loreLines) {
						if (lore.remove(loreLine)) {
							removedAnyLoreLines = true
							continue@keepRemovingLore
						}
					}
					break
				}
				if (removedAnyLoreLines) {
					lore(lore.takeIf { it.isNotEmpty() })
				}
			}
			// Remove the custom model data
			customModelData = null
		}
	}

	fun ItemStack.getWithFixedLoreAndCustomModelDataIfNecessary(): ItemStack? {
		require(isTenYearsElytra) { "this item stack does not have the 10-years effect" }
		val withFixedLore = getWithFixedLoreIfNecessary()
		val withFixedCustomModelData = (withFixedLore ?: this).getWithFixedCustomModelDataIfNecessary()
		return withFixedCustomModelData ?: withFixedLore
	}

	private fun ItemStack.getWithFixedLoreIfNecessary(): ItemStack? {
		var lore: MutableList<Component?> = lore() ?: ArrayList(0)
		var containsAllLore = true
		for (loreLine in loreLines) {
			if (!lore.contains(loreLine)) {
				containsAllLore = false
				break
			}
		}
		if (containsAllLore) return null
		lore = java.util.ArrayList(lore)
		lore.removeAll(loreLines.toSet())
		lore.removeAll(oldLoreLines.toSet())
		for (i in loreLines.size - 1 downTo 0) {
			if (!lore.contains(loreLines[i])) {
				lore.add(0, loreLines[i])
			}
		}
		return clone().apply {
			lore(lore)
		}
	}

	private fun ItemStack.getWithFixedCustomModelDataIfNecessary(): ItemStack? {
		if (customModelData == tenYearsElytraCustomModelData) return null
		return clone().apply {
			customModelData = tenYearsElytraCustomModelData
		}
	}

	// Settings

	companion object {

		private val loreLines: Array<Component> = arrayOf(
			MiniMessage.builder().build().deserialize("<rainbow:2>Thank you for 10 years of SuCraft! ❤</rainbow>"),
			Component.text("This elytra will leave fireworks").color(NamedTextColor.GRAY)
				.decoration(TextDecoration.ITALIC, false),
			Component.text("when you boost with rockets!").color(NamedTextColor.GRAY)
				.decoration(TextDecoration.ITALIC, false)
		)

		// To fix elytras with an old version of the lore
		private val oldLoreLines: Array<Component> = arrayOf(
			Component.text("when you boost with rockets!").color(NamedTextColor.GRAY)
		)

	}

}