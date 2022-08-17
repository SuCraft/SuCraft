/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.persistentdata

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.sucraft.common.itemstack.meta.runMeta
import org.sucraft.common.itemstack.meta.withMeta

fun <T : Any, Z : Any> ItemStack.getPersistent(key: NamespacedKey, type: PersistentDataType<T, Z>) =
	runMeta { getPersistent(key, type) }

val ItemStack.persistentAdapterContext
	get() = runMeta { persistentAdapterContext }

val ItemStack.persistentKeys
	get() = runMeta { persistentKeys }

fun <T : Any, Z : Any> ItemStack.getPersistentOrDefault(
	key: NamespacedKey,
	type: PersistentDataType<T, Z>,
	defaultValue: Z
) =
	runMeta { getPersistentOrDefault(key, type, defaultValue) }

fun <T : Any, Z : Any> ItemStack.hasPersistent(key: NamespacedKey, type: PersistentDataType<T, Z>) =
	runMeta { hasPersistent(key, type) }

fun ItemStack.hasPersistent(key: NamespacedKey) =
	runMeta { hasPersistent(key) }

val ItemStack.isPersistentDataEmpty
	get() = runMeta { isPersistentDataEmpty }

fun <T : Any, Z : Any> ItemStack.getPersistentOrNull(key: NamespacedKey, type: PersistentDataType<T, Z>) =
	runMeta { getPersistentOrNull(key, type) }

fun ItemStack.removePersistent(key: NamespacedKey) =
	withMeta { removePersistent(key) }

fun <T : Any, Z : Any> ItemStack.setPersistent(key: NamespacedKey, type: PersistentDataType<T, Z>, value: Z) =
	withMeta { setPersistent(key, type, value) }