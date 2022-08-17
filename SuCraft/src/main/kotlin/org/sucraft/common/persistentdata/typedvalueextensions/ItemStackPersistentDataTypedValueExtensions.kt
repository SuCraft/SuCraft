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
import org.sucraft.common.itemstack.meta.runMeta
import org.sucraft.common.itemstack.meta.withMeta
import org.sucraft.common.persistentdata.*
import java.util.*

// Byte

fun ItemStack.getPersistentByte(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.BYTE)

fun ItemStack.getPersistentByteOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.BYTE)

fun ItemStack.hasPersistentByte(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.BYTE)

fun ItemStack.setPersistentByte(key: NamespacedKey, value: Byte) =
	setPersistent(key, PersistentDataType.BYTE, value)

// ByteArray

fun ItemStack.getPersistentByteArray(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.BYTE_ARRAY)

fun ItemStack.getPersistentByteArrayOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.BYTE_ARRAY)

fun ItemStack.hasPersistentByteArray(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.BYTE_ARRAY)

fun ItemStack.setPersistentByteArray(key: NamespacedKey, value: ByteArray) =
	setPersistent(key, PersistentDataType.BYTE_ARRAY, value)

// Double

fun ItemStack.getPersistentDouble(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.DOUBLE)

fun ItemStack.getPersistentDoubleOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.DOUBLE)

fun ItemStack.hasPersistentDouble(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.DOUBLE)

fun ItemStack.setPersistentDouble(key: NamespacedKey, value: Double) =
	setPersistent(key, PersistentDataType.DOUBLE, value)

// Float

fun ItemStack.getPersistentFloat(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.FLOAT)

fun ItemStack.getPersistentFloatOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.FLOAT)

fun ItemStack.hasPersistentFloat(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.FLOAT)

fun ItemStack.setPersistentFloat(key: NamespacedKey, value: Float) =
	setPersistent(key, PersistentDataType.FLOAT, value)

// Int

fun ItemStack.getPersistentInt(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.INTEGER)

fun ItemStack.getPersistentIntOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.INTEGER)

fun ItemStack.hasPersistentInt(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.INTEGER)

fun ItemStack.setPersistentInt(key: NamespacedKey, value: Int) =
	setPersistent(key, PersistentDataType.INTEGER, value)

// IntArray

fun ItemStack.getPersistentIntArray(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.INTEGER_ARRAY)

fun ItemStack.getPersistentIntArrayOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.INTEGER_ARRAY)

fun ItemStack.hasPersistentIntArray(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.INTEGER_ARRAY)

fun ItemStack.setPersistentIntArray(key: NamespacedKey, value: IntArray) =
	setPersistent(key, PersistentDataType.INTEGER_ARRAY, value)

// Long

fun ItemStack.getPersistentLong(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.LONG)

fun ItemStack.getPersistentLongOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.LONG)

fun ItemStack.hasPersistentLong(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.LONG)

fun ItemStack.setPersistentLong(key: NamespacedKey, value: Long) =
	setPersistent(key, PersistentDataType.LONG, value)

// LongArray

fun ItemStack.getPersistentLongArray(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.LONG_ARRAY)

fun ItemStack.getPersistentLongArrayOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.LONG_ARRAY)

fun ItemStack.hasPersistentLongArray(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.LONG_ARRAY)

fun ItemStack.setPersistentLongArray(key: NamespacedKey, value: LongArray) =
	setPersistent(key, PersistentDataType.LONG_ARRAY, value)

// Short

fun ItemStack.getPersistentShort(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.SHORT)

fun ItemStack.getPersistentShortOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.SHORT)

fun ItemStack.hasPersistentShort(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.SHORT)

fun ItemStack.setPersistentShort(key: NamespacedKey, value: Short) =
	setPersistent(key, PersistentDataType.SHORT, value)

// String

fun ItemStack.getPersistentString(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.STRING)

fun ItemStack.getPersistentStringOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.STRING)

fun ItemStack.hasPersistentString(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.STRING)

fun ItemStack.setPersistentString(key: NamespacedKey, value: String) =
	setPersistent(key, PersistentDataType.STRING, value)

// PersistentDataContainer

fun ItemStack.getPersistentTagContainer(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.TAG_CONTAINER)

fun ItemStack.getPersistentTagContainerOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.TAG_CONTAINER)

fun ItemStack.hasPersistentTagContainer(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.TAG_CONTAINER)

fun ItemStack.setPersistentTagContainer(key: NamespacedKey, value: PersistentDataContainer) =
	setPersistent(key, PersistentDataType.TAG_CONTAINER, value)

// Array<PersistentDataContainer>

fun ItemStack.getPersistentTagContainerArray(key: NamespacedKey) =
	getPersistent(key, PersistentDataType.TAG_CONTAINER_ARRAY)

fun ItemStack.getPersistentTagContainerArrayOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, PersistentDataType.TAG_CONTAINER_ARRAY)

fun ItemStack.hasPersistentTagContainerArray(key: NamespacedKey) =
	hasPersistent(key, PersistentDataType.TAG_CONTAINER_ARRAY)

fun ItemStack.setPersistentTagContainerArray(key: NamespacedKey, value: Array<PersistentDataContainer>) =
	setPersistent(key, PersistentDataType.TAG_CONTAINER_ARRAY, value)

// Boolean

fun ItemStack.getPersistentBoolean(key: NamespacedKey) =
	getPersistent(key, CustomPersistentDataType.BOOLEAN)

fun ItemStack.getPersistentBooleanOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, CustomPersistentDataType.BOOLEAN)

fun ItemStack.setPersistentBoolean(key: NamespacedKey, value: Boolean) =
	setPersistent(key, CustomPersistentDataType.BOOLEAN, value)

// UUID

fun ItemStack.getPersistentUUID(key: NamespacedKey) =
	getPersistent(key, CustomPersistentDataType.UUID)

fun ItemStack.getPersistentUUIDOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, CustomPersistentDataType.UUID)

fun ItemStack.setPersistentUUID(key: NamespacedKey, value: UUID) =
	setPersistent(key, CustomPersistentDataType.UUID, value)

// UUID (compact)

fun ItemStack.getPersistentCompactUUID(key: NamespacedKey) =
	getPersistent(key, CustomPersistentDataType.COMPACT_UUID)

fun ItemStack.getPersistentCompactUUIDOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, CustomPersistentDataType.COMPACT_UUID)

fun ItemStack.setPersistentCompactUUID(key: NamespacedKey, value: UUID) =
	setPersistent(key, CustomPersistentDataType.COMPACT_UUID, value)

// ItemStack

fun ItemStack.getPersistentItemStack(key: NamespacedKey) =
	getPersistent(key, CustomPersistentDataType.ITEM_STACK)

fun ItemStack.getPersistentItemStackOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, CustomPersistentDataType.ITEM_STACK)

fun ItemStack.setPersistentItemStack(key: NamespacedKey, value: ItemStack) =
	setPersistent(key, CustomPersistentDataType.ITEM_STACK, value)

// JSONObject

fun ItemStack.getPersistentJSONObject(key: NamespacedKey) =
	getPersistent(key, CustomPersistentDataType.JSON_OBJECT)

fun ItemStack.getPersistentJSONObjectOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, CustomPersistentDataType.JSON_OBJECT)

fun ItemStack.setPersistentJSONObject(key: NamespacedKey, value: JSONObject) =
	setPersistent(key, CustomPersistentDataType.JSON_OBJECT, value)

// JSONArray

fun ItemStack.getPersistentJSONArray(key: NamespacedKey) =
	getPersistent(key, CustomPersistentDataType.JSON_ARRAY)

fun ItemStack.getPersistentJSONArrayOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, CustomPersistentDataType.JSON_ARRAY)

fun ItemStack.setPersistentJSONArray(key: NamespacedKey, value: JSONArray) =
	setPersistent(key, CustomPersistentDataType.JSON_ARRAY, value)

// List<UUID>

fun ItemStack.getPersistentUUIDList(key: NamespacedKey) =
	getPersistent(key, CustomPersistentDataType.UUID_LIST)

fun ItemStack.getPersistentUUIDListOrNull(key: NamespacedKey) =
	getPersistentOrNull(key, CustomPersistentDataType.UUID_LIST)

fun ItemStack.setPersistentUUIDList(key: NamespacedKey, value: List<UUID>) =
	setPersistent(key, CustomPersistentDataType.UUID_LIST, value)

// Enum values

inline fun <reified E : Enum<E>> ItemStack.getPersistentEnum(key: NamespacedKey) =
	runMeta { getPersistentEnum<E>(key) }

inline fun <reified E : Enum<E>> ItemStack.getPersistentEnumOrNull(key: NamespacedKey) =
	runMeta { getPersistentEnumOrNull<E>(key) }

inline fun <reified E : Enum<E>> ItemStack.setPersistentEnum(key: NamespacedKey, value: E) =
	withMeta { setPersistentEnum(key, value) }