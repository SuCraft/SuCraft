/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.persistentdata

import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataHolder
import org.bukkit.persistence.PersistentDataType

fun <T : Any, Z : Any> PersistentDataHolder.getPersistent(key: NamespacedKey, type: PersistentDataType<T, Z>) =
	persistentDataContainer.get(key, type)

val PersistentDataHolder.persistentAdapterContext
	get() = persistentDataContainer.adapterContext

val PersistentDataHolder.persistentKeys
	get() = persistentDataContainer.keys

fun <T : Any, Z : Any> PersistentDataHolder.getPersistentOrDefault(
	key: NamespacedKey,
	type: PersistentDataType<T, Z>,
	defaultValue: Z
) =
	persistentDataContainer.getOrDefault(key, type, defaultValue)

fun <T : Any, Z : Any> PersistentDataHolder.hasPersistent(key: NamespacedKey, type: PersistentDataType<T, Z>) =
	persistentDataContainer.has(key, type)

fun PersistentDataHolder.hasPersistent(key: NamespacedKey) =
	persistentDataContainer.has(key)

val PersistentDataHolder.isPersistentDataEmpty
	get() = persistentDataContainer.isEmpty

fun <T : Any, Z : Any> PersistentDataHolder.getPersistentOrNull(key: NamespacedKey, type: PersistentDataType<T, Z>) =
	persistentDataContainer.getOrNull(key, type)

fun PersistentDataHolder.removePersistent(key: NamespacedKey) =
	persistentDataContainer.remove(key)

fun <T : Any, Z : Any> PersistentDataHolder.setPersistent(key: NamespacedKey, type: PersistentDataType<T, Z>, value: Z) =
	persistentDataContainer.set(key, type, value)