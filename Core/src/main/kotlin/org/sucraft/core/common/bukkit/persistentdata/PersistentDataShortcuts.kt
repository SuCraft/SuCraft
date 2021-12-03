/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.persistentdata

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataHolder
import org.bukkit.persistence.PersistentDataType
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.sucraft.core.common.sucraft.player.PlayerUUID
import org.sucraft.core.common.bukkit.item.GuaranteedItemMetaGetter
import java.util.UUID
import kotlin.reflect.KClass


typealias _Byte = Byte
typealias _Short = Short
typealias _Int = Int
typealias _Long = Long
typealias _Float = Float
typealias _Double = Double
typealias _String = String
typealias _IntArray = IntArray
typealias _LongArray = LongArray
typealias _Boolean = Boolean
typealias _UUID = UUID
typealias _PlayerUUID = PlayerUUID
typealias _JSONObject = JSONObject
typealias _JSONArray = JSONArray
typealias _Enum<E> = Enum<E>

@Suppress("MemberVisibilityCanBePrivate")
object PersistentDataShortcuts {

	// Remove persistent data

	fun remove(container: PersistentDataContainer, key: NamespacedKey) =
		container.remove(key)

	fun remove(holder: PersistentDataHolder, key: NamespacedKey) =
		remove(holder.persistentDataContainer, key)

	fun remove(itemStack: ItemStack?, key: NamespacedKey) {
		if (itemStack == null) return
		if (!itemStack.hasItemMeta()) return
		remove(itemStack.itemMeta, key)
	}

	interface PersistentDataShortcut<T> {

		operator fun get(container: PersistentDataContainer, key: NamespacedKey): T?

		operator fun get(holder: PersistentDataHolder, key: NamespacedKey) =
			get(holder.persistentDataContainer, key)

		operator fun get(itemStack: ItemStack?, key: NamespacedKey): T? {
			if (itemStack == null) return null
			if (!itemStack.hasItemMeta()) return null
			return get(itemStack.itemMeta, key)
		}

		operator fun set(container: PersistentDataContainer, key: NamespacedKey, value: T)

		operator fun set(holder: PersistentDataHolder, key: NamespacedKey, value: T) =
			set(holder.persistentDataContainer, key, value)

		operator fun set(itemStack: ItemStack, key: NamespacedKey, value: T) =
			set(GuaranteedItemMetaGetter.get(itemStack), key, value)

	}

	private class ExistingTypePersistentDataShortcut<T: Any>(val type: PersistentDataType<T, T>) : PersistentDataShortcut<T> {

		override operator fun get(container: PersistentDataContainer, key: NamespacedKey) =
			container.get(key, type)

		override operator fun set(container: PersistentDataContainer, key: NamespacedKey, value: T) =
			container.set(key, type, value)

	}

	val Byte: PersistentDataShortcut<_Byte> = ExistingTypePersistentDataShortcut(PersistentDataType.BYTE)
	val Short: PersistentDataShortcut<_Short> = ExistingTypePersistentDataShortcut(PersistentDataType.SHORT)
	val Int: PersistentDataShortcut<_Int> = ExistingTypePersistentDataShortcut(PersistentDataType.INTEGER)
	val Long: PersistentDataShortcut<_Long> = ExistingTypePersistentDataShortcut(PersistentDataType.LONG)
	val Float: PersistentDataShortcut<_Float> = ExistingTypePersistentDataShortcut(PersistentDataType.FLOAT)
	val Double: PersistentDataShortcut<_Double> = ExistingTypePersistentDataShortcut(PersistentDataType.DOUBLE)
	val String: PersistentDataShortcut<_String> = ExistingTypePersistentDataShortcut(PersistentDataType.STRING)
	val IntArray: PersistentDataShortcut<_IntArray> = ExistingTypePersistentDataShortcut(PersistentDataType.INTEGER_ARRAY)
	val LongArray: PersistentDataShortcut<_LongArray> = ExistingTypePersistentDataShortcut(PersistentDataType.LONG_ARRAY)

	private class SerializedPersistentDataShortcut<T, ST>(val serialize: (T) -> ST, val deserialize: (ST) -> T?, val serialShortcut: PersistentDataShortcut<ST>) : PersistentDataShortcut<T> {

		override operator fun get(container: PersistentDataContainer, key: NamespacedKey) =
			serialShortcut[container, key]?.let(deserialize)

		override operator fun set(container: PersistentDataContainer, key: NamespacedKey, value: T) {
			serialShortcut[container, key] = serialize(value)
		}

	}

	val Boolean: PersistentDataShortcut<_Boolean> = SerializedPersistentDataShortcut(
		{ (if (it) 1 else 0).toByte() },
		{ it != (0).toByte() },
		Byte
	)
	val UUID: PersistentDataShortcut<_UUID> = SerializedPersistentDataShortcut(
		_UUID::toString,
		{
			try {
				_UUID.fromString(it)
			} catch (e: IllegalArgumentException) {
				null
			}
		},
		String
	)
	val PlayerUUID: PersistentDataShortcut<_PlayerUUID> = SerializedPersistentDataShortcut(
		_PlayerUUID::toString,
		{
			try {
				_PlayerUUID.fromString(it)
			} catch (e: IllegalArgumentException) {
				null
			}
		},
		String
	)
	val JSONObject: PersistentDataShortcut<_JSONObject> = SerializedPersistentDataShortcut(
		_JSONObject::toString,
		{
			try {
				_JSONObject(it)
			} catch (e: JSONException) {
				null
			}
		},
		String
	)
	val JSONArray: PersistentDataShortcut<_JSONArray> = SerializedPersistentDataShortcut(
		_JSONArray::toString,
		{
			try {
				_JSONArray(it)
			} catch (e: JSONException) {
				null
			}
		},
		String
	)
	@Suppress("NestedLambdaShadowedImplicitParameter")
	val UUIDList: PersistentDataShortcut<MutableList<_UUID>> = SerializedPersistentDataShortcut(
		{
			val json = JSONArray()
			it.forEach { json.put(it) }
			json
		},
		{
			it.mapNotNull { it as? String }.mapNotNull {
				try {
					_UUID.fromString(it)
				} catch (e: IllegalArgumentException) {
					null
				}
			}.toMutableList()
		},
		JSONArray
	)

	fun <E : _Enum<E>> Enum(type: KClass<E>): PersistentDataShortcut<E> = SerializedPersistentDataShortcut(
		{ it.name },
		{
			try {
				java.lang.Enum.valueOf(type.java, it)
			} catch (e: IllegalArgumentException) {
				null
			}
		},
		String
	)

	object Tag {

		operator fun get(container: PersistentDataContainer, key: NamespacedKey) =
			Boolean[container, key] ?: false

		operator fun get(holder: PersistentDataHolder, key: NamespacedKey) =
			get(holder.persistentDataContainer, key)

		operator fun get(itemStack: ItemStack?, key: NamespacedKey): Boolean {
			if (itemStack == null) return false
			if (!itemStack.hasItemMeta()) return false
			return get(itemStack.itemMeta, key)
		}

		fun set(container: PersistentDataContainer, key: NamespacedKey) {
			Boolean[container, key] = true
		}

		fun set(holder: PersistentDataHolder, key: NamespacedKey) =
			set(holder.persistentDataContainer, key)

		fun set(itemStack: ItemStack, key: NamespacedKey) =
			set(itemStack.itemMeta, key)

	}

}