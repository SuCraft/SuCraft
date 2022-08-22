/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack

import net.minecraft.nbt.NBTTagCompound
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack
import org.joor.Reflect.on

/**
 * The [NBT][NBTTagCompound] of this [ItemStack], or null if there is no NBT present, or if it could not be retrieved.
 */
val ItemStack.nbt
	get() = (this as? CraftItemStack)?.handle?._tag()

/**
 * The [NBT][NBTTagCompound] of this [ItemStack] in string form,
 * identical to how it would be presented in vanilla,
 * or null if there is no NBT present, or if it could not be retrieved.
 */
val ItemStack.nbtString
	get() = nbt?._getAsString()

/**
 * The [NBT][NBTTagCompound] of this [ItemStack] in [string form][nbtString],
 * identical to how it would be presented in vanilla, prefixed with the [namespaced key][NamespacedKey]
 * of the [type][ItemStack.getType] of this [ItemStack], identical to how it would be presented in vanilla.
 * If there is no NBT present, or if it could not be retrieved, this will
 * be only the [type][ItemStack.getType]'s [namespaced key][NamespacedKey],
 * identical to how it would be presented in vanilla.
 *
 * Examples of potential output (whether the `minecraft:` namespace is included and whether strings are always
 * surrounded by quotes is not guaranteed - it is simply taken as given by the vanilla server code):
 * - `minecraft:torch`
 * - `diamond_axe{Enchantments:[{id:fortune,lvl:2}]}`
 */
val ItemStack.typeAndNBT
	get() = "${type.key.asString()}${nbtString ?: ""}"

/**
 * The [type and NBT][typeAndNBT] of this [ItemStack] as it would be presented in vanilla,
 * prefixed with the [amount][ItemStack.getAmount] and a space character.
 */
val ItemStack.amountAndTypeAndNBT
	get() = "$amount $typeAndNBT"