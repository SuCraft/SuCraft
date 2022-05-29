/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.tenyearselytra.data

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.sucraft.core.common.bukkit.persistentdata.PersistentDataShortcuts
import org.sucraft.core.common.sucraft.player.PlayerUUID
import java.util.*
import kotlin.collections.ArrayList

@Suppress("MemberVisibilityCanBePrivate")
object ElytraData {

	@Suppress("DEPRECATION")
	private val festiveElytraCreatorPersistentDataNamespacedKey: NamespacedKey = NamespacedKey("martijnsextrafeatures", "elytra_ten_years")

	private val loreLines: Array<Component> = arrayOf(
		MiniMessage.builder().build().parse("<rainbow:2>Thank you for 10 years of SuCraft! ❤</rainbow>"),
		Component.text("This elytra will leave fireworks").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
		Component.text("when you boost with rockets!").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
	)
	// To fix elytras with an old version of the lore
	private val oldLoreLines: Array<Component> = arrayOf(
		Component.text("when you boost with rockets!").color(NamedTextColor.GRAY)
	)

	fun isFestiveOrFestivableElytra(itemStack: ItemStack?) = itemStack?.type == Material.ELYTRA

	fun getFestiveElytraCreator(itemStack: ItemStack?): UUID? = PersistentDataShortcuts.UUID[itemStack, festiveElytraCreatorPersistentDataNamespacedKey]

	fun isFestiveElytra(itemStack: ItemStack?) = getFestiveElytraCreator(itemStack) != null

	fun festivizeElytra(creator: Player, itemStack: ItemStack): ItemStack {
		require(isFestiveOrFestivableElytra(itemStack)) { "itemStack cannot be made festive" }
		require(!isFestiveElytra(itemStack)) { "itemStack is already festive" }
		val festiveItemStack = itemStack.clone()
		PersistentDataShortcuts.PlayerUUID[festiveItemStack, festiveElytraCreatorPersistentDataNamespacedKey] = PlayerUUID.get(creator)
		val lore = festiveItemStack.lore() ?: ArrayList(loreLines.size)
		for (i in loreLines.size - 1 downTo 0)
			if (!lore.contains(loreLines[i])) lore.add(0, loreLines[i])
		festiveItemStack.lore(lore)
		return festiveItemStack
	}

	fun regularizeElytra(itemStack: ItemStack): ItemStack {
		require(isFestiveElytra(itemStack)) { "itemStack is not festive" }
		val regularItemStack = itemStack.clone()
		PersistentDataShortcuts.remove(regularItemStack, festiveElytraCreatorPersistentDataNamespacedKey)
		var lore = regularItemStack.lore()
		if (lore != null) {
			lore = ArrayList<Component>(lore)
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
				regularItemStack.lore(lore)
			}
		}
		return regularItemStack
	}

	/**
	 * Fixes the old lore that is no longer used: returns null if nothing needs to be changed to the given item, or otherwise a fixed item
	 */
	fun fixOldLore(itemStack: ItemStack): ItemStack? {
		if (!isFestiveElytra(itemStack)) return null
		var lore: MutableList<Component?> = itemStack.lore()!!
		var containsAllLore = true
		for (loreLine in loreLines) {
			if (!lore.contains(loreLine)) {
				containsAllLore = false
				break
			}
		}
		if (containsAllLore) return null
		lore = ArrayList(lore)
		lore.removeAll(loreLines.toSet())
		lore.removeAll(oldLoreLines.toSet())
		for (i in loreLines.size - 1 downTo 0) {
			if (!lore.contains(loreLines[i])) {
				lore.add(0, loreLines[i])
			}
		}
		val newItemStack = itemStack.clone()
		newItemStack.lore(lore)
		return newItemStack
	}

}