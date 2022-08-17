/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.persistentdata.typedvalueextensions

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import org.json.JSONArray
import org.json.JSONObject
import org.sucraft.common.persistentdata.CustomPersistentDataType
import org.sucraft.common.persistentdata.getOrNull
import java.util.*

// Byte

fun PersistentDataContainer.getByte(key: NamespacedKey) =
	get(key, PersistentDataType.BYTE)

fun PersistentDataContainer.getByteOrNull(key: NamespacedKey) =
	getOrNull(key, PersistentDataType.BYTE)

fun PersistentDataContainer.hasByte(key: NamespacedKey) =
	has(key, PersistentDataType.BYTE)

fun PersistentDataContainer.setByte(key: NamespacedKey, value: Byte) =
	set(key, PersistentDataType.BYTE, value)

// ByteArray

fun PersistentDataContainer.getByteArray(key: NamespacedKey) =
	get(key, PersistentDataType.BYTE_ARRAY)

fun PersistentDataContainer.getByteArrayOrNull(key: NamespacedKey) =
	getOrNull(key, PersistentDataType.BYTE_ARRAY)

fun PersistentDataContainer.hasByteArray(key: NamespacedKey) =
	has(key, PersistentDataType.BYTE_ARRAY)

fun PersistentDataContainer.setByteArray(key: NamespacedKey, value: ByteArray) =
	set(key, PersistentDataType.BYTE_ARRAY, value)

// Double

fun PersistentDataContainer.getDouble(key: NamespacedKey) =
	get(key, PersistentDataType.DOUBLE)

fun PersistentDataContainer.getDoubleOrNull(key: NamespacedKey) =
	getOrNull(key, PersistentDataType.DOUBLE)

fun PersistentDataContainer.hasDouble(key: NamespacedKey) =
	has(key, PersistentDataType.DOUBLE)

fun PersistentDataContainer.setDouble(key: NamespacedKey, value: Double) =
	set(key, PersistentDataType.DOUBLE, value)

// Float

fun PersistentDataContainer.getFloat(key: NamespacedKey) =
	get(key, PersistentDataType.FLOAT)

fun PersistentDataContainer.getFloatOrNull(key: NamespacedKey) =
	getOrNull(key, PersistentDataType.FLOAT)

fun PersistentDataContainer.hasFloat(key: NamespacedKey) =
	has(key, PersistentDataType.FLOAT)

fun PersistentDataContainer.setFloat(key: NamespacedKey, value: Float) =
	set(key, PersistentDataType.FLOAT, value)

// Int

fun PersistentDataContainer.getInt(key: NamespacedKey) =
	get(key, PersistentDataType.INTEGER)

fun PersistentDataContainer.getIntOrNull(key: NamespacedKey) =
	getOrNull(key, PersistentDataType.INTEGER)

fun PersistentDataContainer.hasInt(key: NamespacedKey) =
	has(key, PersistentDataType.INTEGER)

fun PersistentDataContainer.setInt(key: NamespacedKey, value: Int) =
	set(key, PersistentDataType.INTEGER, value)

// IntArray

fun PersistentDataContainer.getIntArray(key: NamespacedKey) =
	get(key, PersistentDataType.INTEGER_ARRAY)

fun PersistentDataContainer.getIntArrayOrNull(key: NamespacedKey) =
	getOrNull(key, PersistentDataType.INTEGER_ARRAY)

fun PersistentDataContainer.hasIntArray(key: NamespacedKey) =
	has(key, PersistentDataType.INTEGER_ARRAY)

fun PersistentDataContainer.setIntArray(key: NamespacedKey, value: IntArray) =
	set(key, PersistentDataType.INTEGER_ARRAY, value)

// Long

fun PersistentDataContainer.getLong(key: NamespacedKey) =
	get(key, PersistentDataType.LONG)

fun PersistentDataContainer.getLongOrNull(key: NamespacedKey) =
	getOrNull(key, PersistentDataType.LONG)

fun PersistentDataContainer.hasLong(key: NamespacedKey) =
	has(key, PersistentDataType.LONG)

fun PersistentDataContainer.setLong(key: NamespacedKey, value: Long) =
	set(key, PersistentDataType.LONG, value)

// LongArray

fun PersistentDataContainer.getLongArray(key: NamespacedKey) =
	get(key, PersistentDataType.LONG_ARRAY)

fun PersistentDataContainer.getLongArrayOrNull(key: NamespacedKey) =
	getOrNull(key, PersistentDataType.LONG_ARRAY)

fun PersistentDataContainer.hasLongArray(key: NamespacedKey) =
	has(key, PersistentDataType.LONG_ARRAY)

fun PersistentDataContainer.setLongArray(key: NamespacedKey, value: LongArray) =
	set(key, PersistentDataType.LONG_ARRAY, value)

// Short

fun PersistentDataContainer.getShort(key: NamespacedKey) =
	get(key, PersistentDataType.SHORT)

fun PersistentDataContainer.getShortOrNull(key: NamespacedKey) =
	getOrNull(key, PersistentDataType.SHORT)

fun PersistentDataContainer.hasShort(key: NamespacedKey) =
	has(key, PersistentDataType.SHORT)

fun PersistentDataContainer.setShort(key: NamespacedKey, value: Short) =
	set(key, PersistentDataType.SHORT, value)

// String

fun PersistentDataContainer.getString(key: NamespacedKey) =
	get(key, PersistentDataType.STRING)

fun PersistentDataContainer.getStringOrNull(key: NamespacedKey) =
	getOrNull(key, PersistentDataType.STRING)

fun PersistentDataContainer.hasString(key: NamespacedKey) =
	has(key, PersistentDataType.STRING)

fun PersistentDataContainer.setString(key: NamespacedKey, value: String) =
	set(key, PersistentDataType.STRING, value)

// PersistentDataContainer

fun PersistentDataContainer.getTagContainer(key: NamespacedKey) =
	get(key, PersistentDataType.TAG_CONTAINER)

fun PersistentDataContainer.getTagContainerOrNull(key: NamespacedKey) =
	getOrNull(key, PersistentDataType.TAG_CONTAINER)

fun PersistentDataContainer.hasTagContainer(key: NamespacedKey) =
	has(key, PersistentDataType.TAG_CONTAINER)

fun PersistentDataContainer.setTagContainer(key: NamespacedKey, value: PersistentDataContainer) =
	set(key, PersistentDataType.TAG_CONTAINER, value)

// Array<PersistentDataContainer>

fun PersistentDataContainer.getTagContainerArray(key: NamespacedKey) =
	get(key, PersistentDataType.TAG_CONTAINER_ARRAY)

fun PersistentDataContainer.getTagContainerArrayOrNull(key: NamespacedKey) =
	getOrNull(key, PersistentDataType.TAG_CONTAINER_ARRAY)

fun PersistentDataContainer.hasTagContainerArray(key: NamespacedKey) =
	has(key, PersistentDataType.TAG_CONTAINER_ARRAY)

fun PersistentDataContainer.setTagContainerArray(key: NamespacedKey, value: Array<PersistentDataContainer>) =
	set(key, PersistentDataType.TAG_CONTAINER_ARRAY, value)

// Boolean

fun PersistentDataContainer.getBoolean(key: NamespacedKey) =
	get(key, CustomPersistentDataType.BOOLEAN)

fun PersistentDataContainer.getBooleanOrNull(key: NamespacedKey) =
	getOrNull(key, CustomPersistentDataType.BOOLEAN)

fun PersistentDataContainer.setBoolean(key: NamespacedKey, value: Boolean) =
	set(key, CustomPersistentDataType.BOOLEAN, value)

// UUID

fun PersistentDataContainer.getUUID(key: NamespacedKey) =
	get(key, CustomPersistentDataType.UUID)

fun PersistentDataContainer.getUUIDOrNull(key: NamespacedKey) =
	getOrNull(key, CustomPersistentDataType.UUID)

fun PersistentDataContainer.setUUID(key: NamespacedKey, value: UUID) =
	set(key, CustomPersistentDataType.UUID, value)

// UUID (compact)

fun PersistentDataContainer.getCompactUUID(key: NamespacedKey) =
	get(key, CustomPersistentDataType.COMPACT_UUID)

fun PersistentDataContainer.getCompactUUIDOrNull(key: NamespacedKey) =
	getOrNull(key, CustomPersistentDataType.COMPACT_UUID)

fun PersistentDataContainer.setCompactUUID(key: NamespacedKey, value: UUID) =
	set(key, CustomPersistentDataType.COMPACT_UUID, value)

// ItemStack

fun PersistentDataContainer.getItemStack(key: NamespacedKey) =
	get(key, CustomPersistentDataType.ITEM_STACK)

fun PersistentDataContainer.getItemStackOrNull(key: NamespacedKey) =
	getOrNull(key, CustomPersistentDataType.ITEM_STACK)

fun PersistentDataContainer.setItemStack(key: NamespacedKey, value: ItemStack) =
	set(key, CustomPersistentDataType.ITEM_STACK, value)

// JSONObject

fun PersistentDataContainer.getJSONObject(key: NamespacedKey) =
	get(key, CustomPersistentDataType.JSON_OBJECT)

fun PersistentDataContainer.getJSONObjectOrNull(key: NamespacedKey) =
	getOrNull(key, CustomPersistentDataType.JSON_OBJECT)

fun PersistentDataContainer.setJSONObject(key: NamespacedKey, value: JSONObject) =
	set(key, CustomPersistentDataType.JSON_OBJECT, value)

// JSONArray

fun PersistentDataContainer.getJSONArray(key: NamespacedKey) =
	get(key, CustomPersistentDataType.JSON_ARRAY)

fun PersistentDataContainer.getJSONArrayOrNull(key: NamespacedKey) =
	getOrNull(key, CustomPersistentDataType.JSON_ARRAY)

fun PersistentDataContainer.setJSONArray(key: NamespacedKey, value: JSONArray) =
	set(key, CustomPersistentDataType.JSON_ARRAY, value)

// List<UUID>

fun PersistentDataContainer.getUUIDList(key: NamespacedKey) =
	get(key, CustomPersistentDataType.UUID_LIST)

fun PersistentDataContainer.getUUIDListOrNull(key: NamespacedKey) =
	getOrNull(key, CustomPersistentDataType.UUID_LIST)

fun PersistentDataContainer.setUUIDList(key: NamespacedKey, value: List<UUID>) =
	set(key, CustomPersistentDataType.UUID_LIST, value)

// Enum values

inline fun <reified E : Enum<E>> PersistentDataContainer.getEnum(key: NamespacedKey) =
	getString(key)?.let { java.lang.Enum.valueOf(E::class.java, it) }

inline fun <reified E : Enum<E>> PersistentDataContainer.getEnumOrNull(key: NamespacedKey) =
	getStringOrNull(key)?.let { java.lang.Enum.valueOf(E::class.java, it) }

inline fun <reified E : Enum<E>> PersistentDataContainer.setEnum(key: NamespacedKey, value: E) =
	setString(key, value.name)