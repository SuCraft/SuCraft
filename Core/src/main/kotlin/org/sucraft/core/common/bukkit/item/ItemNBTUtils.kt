/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.item

import org.bukkit.inventory.ItemStack


object ItemNBTUtils {

	// TODO uncomment and fix when added
	/*
	fun getNBTTagCompound(itemStack: ItemStack): NBTTagCompound? {
		try {
			val field: Field = ItemStack::class.java.getDeclaredField("tag")
			field.setAccessible(true)
			return field.get((itemStack as CraftItemStack).handle) as NBTTagCompound
		} catch (e: Exception) {
		}
		return null
	}

	fun getFullNBTString(itemStack: ItemStack): String? {
		return Optional.ofNullable(getNBTTagCompound(itemStack)).map(NBTTagCompound::asString).orElse(null)
	}

	fun getTypeAndFullNBTString(itemStack: ItemStack): String {
		return itemStack.type.key.asString() + Optional.ofNullable(getFullNBTString(itemStack)).orElse("")
	}

	fun getAmountAndTypeAndFullNBTString(itemStack: ItemStack): String {
		return itemStack.amount.toString() + " " + getTypeAndFullNBTString(itemStack)
	}
	 */

	// TODO remove all the below when the above are added

	fun getNBTTagCompound(itemStack: ItemStack): Nothing {
		throw NotImplementedError("Not implemented yet")
	}

	fun getFullNBTString(itemStack: ItemStack): String =
		"<NBT strings not implemented>"

	fun getTypeAndFullNBTString(itemStack: ItemStack) =
		"${itemStack.type.key.asString()}${getFullNBTString(itemStack)}"

	fun getAmountAndTypeAndFullNBTString(itemStack: ItemStack) =
		"${itemStack.amount} ${getTypeAndFullNBTString(itemStack)}"

}