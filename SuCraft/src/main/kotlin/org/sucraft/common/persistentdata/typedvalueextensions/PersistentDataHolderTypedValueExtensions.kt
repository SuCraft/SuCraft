/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.persistentdata.typedvalueextensions

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataHolder
import org.bukkit.persistence.PersistentDataType
import org.json.JSONArray
import org.json.JSONObject
import org.sucraft.common.persistentdata.*
import java.util.*

// Byte

fun PersistentDataHolder.getPersistentByte(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.BYTE)

fun PersistentDataHolder.getPersistentByteOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.BYTE)

fun PersistentDataHolder.hasPersistentByte(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.BYTE)

fun PersistentDataHolder.setPersistentByte(key: NamespacedKey, value: Byte) =
	setPersistent(key, PersistentDataType.BYTE, value)

// ByteArray

fun PersistentDataHolder.getPersistentByteArray(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.BYTE_ARRAY)

fun PersistentDataHolder.getPersistentByteArrayOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.BYTE_ARRAY)

fun PersistentDataHolder.hasPersistentByteArray(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.BYTE_ARRAY)

fun PersistentDataHolder.setPersistentByteArray(key: NamespacedKey, value: ByteArray) =
	setPersistent(key, PersistentDataType.BYTE_ARRAY, value)

// Double

fun PersistentDataHolder.getPersistentDouble(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.DOUBLE)

fun PersistentDataHolder.getPersistentDoubleOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.DOUBLE)

fun PersistentDataHolder.hasPersistentDouble(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.DOUBLE)

fun PersistentDataHolder.setPersistentDouble(key: NamespacedKey, value: Double) =
	setPersistent(key, PersistentDataType.DOUBLE, value)

// Float

fun PersistentDataHolder.getPersistentFloat(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.FLOAT)

fun PersistentDataHolder.getPersistentFloatOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.FLOAT)

fun PersistentDataHolder.hasPersistentFloat(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.FLOAT)

fun PersistentDataHolder.setPersistentFloat(key: NamespacedKey, value: Float) =
	setPersistent(key, PersistentDataType.FLOAT, value)

// Int

fun PersistentDataHolder.getPersistentInt(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.INTEGER)

fun PersistentDataHolder.getPersistentIntOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.INTEGER)

fun PersistentDataHolder.hasPersistentInt(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.INTEGER)

fun PersistentDataHolder.setPersistentInt(key: NamespacedKey, value: Int) =
	setPersistent(key, PersistentDataType.INTEGER, value)

// IntArray

fun PersistentDataHolder.getPersistentIntArray(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.INTEGER_ARRAY)

fun PersistentDataHolder.getPersistentIntArrayOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.INTEGER_ARRAY)

fun PersistentDataHolder.hasPersistentIntArray(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.INTEGER_ARRAY)

fun PersistentDataHolder.setPersistentIntArray(key: NamespacedKey, value: IntArray) =
	setPersistent(key, PersistentDataType.INTEGER_ARRAY, value)

// Long

fun PersistentDataHolder.getPersistentLong(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.LONG)

fun PersistentDataHolder.getPersistentLongOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.LONG)

fun PersistentDataHolder.hasPersistentLong(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.LONG)

fun PersistentDataHolder.setPersistentLong(key: NamespacedKey, value: Long) =
	setPersistent(key, PersistentDataType.LONG, value)

// LongArray

fun PersistentDataHolder.getPersistentLongArray(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.LONG_ARRAY)

fun PersistentDataHolder.getPersistentLongArrayOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.LONG_ARRAY)

fun PersistentDataHolder.hasPersistentLongArray(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.LONG_ARRAY)

fun PersistentDataHolder.setPersistentLongArray(key: NamespacedKey, value: LongArray) =
	setPersistent(key, PersistentDataType.LONG_ARRAY, value)

// Short

fun PersistentDataHolder.getPersistentShort(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.SHORT)

fun PersistentDataHolder.getPersistentShortOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.SHORT)

fun PersistentDataHolder.hasPersistentShort(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.SHORT)

fun PersistentDataHolder.setPersistentShort(key: NamespacedKey, value: Short) =
	setPersistent(key, PersistentDataType.SHORT, value)

// String

fun PersistentDataHolder.getPersistentString(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.STRING)

fun PersistentDataHolder.getPersistentStringOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.STRING)

fun PersistentDataHolder.hasPersistentString(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.STRING)

fun PersistentDataHolder.setPersistentString(key: NamespacedKey, value: String) =
	setPersistent(key, PersistentDataType.STRING, value)

// PersistentDataContainer

fun PersistentDataHolder.getPersistentTagContainer(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.TAG_CONTAINER)

fun PersistentDataHolder.getPersistentTagContainerOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.TAG_CONTAINER)

fun PersistentDataHolder.hasPersistentTagContainer(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.TAG_CONTAINER)

fun PersistentDataHolder.setPersistentTagContainer(key: NamespacedKey, value: PersistentDataContainer) =
	setPersistent(key, PersistentDataType.TAG_CONTAINER, value)

// Array<PersistentDataContainer>

fun PersistentDataHolder.getPersistentTagContainerArray(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.TAG_CONTAINER_ARRAY)

fun PersistentDataHolder.getPersistentTagContainerArrayOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.TAG_CONTAINER_ARRAY)

fun PersistentDataHolder.hasPersistentTagContainerArray(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.TAG_CONTAINER_ARRAY)

fun PersistentDataHolder.setPersistentTagContainerArray(key: NamespacedKey, value: Array<PersistentDataContainer>) =
	setPersistent(key, PersistentDataType.TAG_CONTAINER_ARRAY, value)

// Boolean

fun PersistentDataHolder.getPersistentBoolean(key: NamespacedKey) =
	getPersistent(key, CustomPersistentDataType.BOOLEAN)

fun PersistentDataHolder.getPersistentBooleanOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, CustomPersistentDataType.BOOLEAN)

fun PersistentDataHolder.setPersistentBoolean(key: NamespacedKey, value: Boolean) =
	setPersistent(key, CustomPersistentDataType.BOOLEAN, value)

// UUID

fun PersistentDataHolder.getPersistentUUID(key: NamespacedKey) =
	getPersistent(key, CustomPersistentDataType.UUID)

fun PersistentDataHolder.getPersistentUUIDOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, CustomPersistentDataType.UUID)

fun PersistentDataHolder.setPersistentUUID(key: NamespacedKey, value: UUID) =
	setPersistent(key, CustomPersistentDataType.UUID, value)

// UUID (compact)

fun PersistentDataHolder.getPersistentCompactUUID(key: NamespacedKey) =
	getPersistent(key, CustomPersistentDataType.COMPACT_UUID)

fun PersistentDataHolder.getPersistentCompactUUIDOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, CustomPersistentDataType.COMPACT_UUID)

fun PersistentDataHolder.setPersistentCompactUUID(key: NamespacedKey, value: UUID) =
	setPersistent(key, CustomPersistentDataType.COMPACT_UUID, value)

// ItemStack

fun PersistentDataHolder.getPersistentItemStack(key: NamespacedKey) =
	getPersistent(key, CustomPersistentDataType.ITEM_STACK)

fun PersistentDataHolder.getPersistentItemStackOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, CustomPersistentDataType.ITEM_STACK)

fun PersistentDataHolder.setPersistentItemStack(key: NamespacedKey, value: ItemStack) =
	setPersistent(key, CustomPersistentDataType.ITEM_STACK, value)

// JSONObject

fun PersistentDataHolder.getPersistentJSONObject(key: NamespacedKey) =
	getPersistent(key, CustomPersistentDataType.JSON_OBJECT)

fun PersistentDataHolder.getPersistentJSONObjectOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, CustomPersistentDataType.JSON_OBJECT)

fun PersistentDataHolder.setPersistentJSONObject(key: NamespacedKey, value: JSONObject) =
	setPersistent(key, CustomPersistentDataType.JSON_OBJECT, value)

// JSONArray

fun PersistentDataHolder.getPersistentJSONArray(key: NamespacedKey) =
	getPersistent(key, CustomPersistentDataType.JSON_ARRAY)

fun PersistentDataHolder.getPersistentJSONArrayOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, CustomPersistentDataType.JSON_ARRAY)

fun PersistentDataHolder.setPersistentJSONArray(key: NamespacedKey, value: JSONArray) =
	setPersistent(key, CustomPersistentDataType.JSON_ARRAY, value)

// List<UUID>

fun PersistentDataHolder.getPersistentUUIDList(key: NamespacedKey) =
	getPersistent(key, CustomPersistentDataType.UUID_LIST)

fun PersistentDataHolder.getPersistentUUIDListOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, CustomPersistentDataType.UUID_LIST)

fun PersistentDataHolder.setPersistentUUIDList(key: NamespacedKey, value: List<UUID>) =
	setPersistent(key, CustomPersistentDataType.UUID_LIST, value)

// Enum values

inline fun <reified E : Enum<E>> PersistentDataHolder.getPersistentEnum(key: NamespacedKey) =
	persistentDataContainer.getEnum<E>(key)

inline fun <reified E : Enum<E>> PersistentDataHolder.getPersistentEnumOrNull(key: NamespacedKey) =
	persistentDataContainer.getEnumOrNull<E>(key)

inline fun <reified E : Enum<E>> PersistentDataHolder.setPersistentEnum(key: NamespacedKey, value: E) =
	persistentDataContainer.setEnum(key, value)