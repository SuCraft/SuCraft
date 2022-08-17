/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.persistentdata

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataHolder
import org.sucraft.common.itemstack.meta.runMeta
import org.sucraft.common.itemstack.meta.withMeta
import org.sucraft.common.persistentdata.typedvalueextensions.setBoolean

fun PersistentDataContainer.setPersistentFlag(key: NamespacedKey) =
	setBoolean(key, true)

fun PersistentDataContainer.getPersistentFlag(key: NamespacedKey) =
	has(key)

fun PersistentDataHolder.setPersistentFlag(key: NamespacedKey) =
	persistentDataContainer.setPersistentFlag(key)

fun PersistentDataHolder.getPersistentFlag(key: NamespacedKey) =
	persistentDataContainer.getPersistentFlag(key)

fun ItemStack.setPersistentFlag(key: NamespacedKey) =
	withMeta { setPersistentFlag(key) }

fun ItemStack.getPersistentFlag(key: NamespacedKey) =
	runMeta { getPersistentFlag(key) }