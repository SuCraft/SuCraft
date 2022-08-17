/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.persistentdata

import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType
import org.json.JSONArray
import org.json.JSONObject
import org.sucraft.common.function.runEach
import org.sucraft.common.itemstack.deserializeToItemStack
import org.sucraft.common.itemstack.serializeToString
import org.sucraft.common.persistentdata.CustomPersistentDataType.UUID
import java.nio.ByteBuffer
import java.util.*

object CustomPersistentDataType {

	/**
	 * An abstract base for a [PersistentDataType] for lists,
	 * that uses a [JSONArray] of strings to store the elements.
	 */
	abstract class ListViaJSONArrayPersistentDataType<T : Any> : PersistentDataType<String, List<T>> {

		override fun getPrimitiveType() = String::class.java

		@Suppress("UNCHECKED_CAST")
		override fun getComplexType() = List::class.java as Class<List<T>>

		override fun toPrimitive(complex: List<T>, context: PersistentDataAdapterContext) =
			JSON_ARRAY.toPrimitive(
				JSONArray()
					.runEach(complex) {
						put(elementToPrimitive(it, context))
					},
				context
			)

		override fun fromPrimitive(primitive: String, context: PersistentDataAdapterContext) =
			JSON_ARRAY.fromPrimitive(primitive, context)
				.mapNotNull { it as? String }
				.map { elementFromPrimitive(it, context) }
				.toList()

		abstract fun elementToPrimitive(complex: T, context: PersistentDataAdapterContext): String

		abstract fun elementFromPrimitive(primitive: String, context: PersistentDataAdapterContext): T

	}

	/**
	 * An abstract base for a [PersistentDataType] for lists,
	 * that uses a [JSONArray] of strings to store the elements,
	 * where the underlying element already has a defined [PersistentDataType] with primitive type
	 * [PersistentDataType.STRING].
	 */
	abstract class ExistingDataTypeListViaJSONArrayPersistentDataType<T : Any>(
		protected val elementType: PersistentDataType<String, T>
	) : ListViaJSONArrayPersistentDataType<T>() {

		override fun elementToPrimitive(complex: T, context: PersistentDataAdapterContext) =
			elementType.toPrimitive(complex, context)

		override fun elementFromPrimitive(primitive: String, context: PersistentDataAdapterContext) =
			elementType.fromPrimitive(primitive, context)

	}

	/**
	 * A custom [PersistentDataType] for boolean values.
	 */
	val BOOLEAN = object : PersistentDataType<Byte, Boolean> {

		override fun getPrimitiveType() = Byte::class.java

		override fun getComplexType() = Boolean::class.java

		override fun toPrimitive(complex: Boolean, context: PersistentDataAdapterContext) =
			(if (complex) 1 else 0).toByte()

		override fun fromPrimitive(primitive: Byte, context: PersistentDataAdapterContext) =
			primitive != 0.toByte()

	}

	/**
	 * A custom [PersistentDataType] for [UUID][java.util.UUID]s that are human-readable.
	 */
	val UUID = object : PersistentDataType<String, UUID> {

		override fun getPrimitiveType() = String::class.java

		override fun getComplexType() = java.util.UUID::class.java

		override fun toPrimitive(complex: UUID, context: PersistentDataAdapterContext) =
			"$complex"

		override fun fromPrimitive(primitive: String, context: PersistentDataAdapterContext) =
			java.util.UUID.fromString(primitive)

	}

	/**
	 * A custom [PersistentDataType] for [UUID][java.util.UUID]s that has a compact representation
	 * (and saves storage in comparison to [CustomPersistentDataType.UUID]).
	 */
	val COMPACT_UUID = object : PersistentDataType<ByteArray, UUID> {

		override fun getPrimitiveType() = ByteArray::class.java

		override fun getComplexType() = java.util.UUID::class.java

		override fun toPrimitive(complex: UUID, context: PersistentDataAdapterContext) =
			ByteBuffer.wrap(ByteArray(16)).apply {
				putLong(complex.mostSignificantBits)
				putLong(complex.leastSignificantBits)
			}.array()

		override fun fromPrimitive(primitive: ByteArray, context: PersistentDataAdapterContext) =
			ByteBuffer.wrap(primitive).run { UUID(long, long) }

	}

	/**
	 * A custom [PersistentDataType] for [item stacks][ItemStack].
	 */
	val ITEM_STACK = object : PersistentDataType<String, ItemStack> {

		override fun getPrimitiveType() = String::class.java

		override fun getComplexType() = ItemStack::class.java

		override fun toPrimitive(complex: ItemStack, context: PersistentDataAdapterContext) =
			complex.serializeToString()

		override fun fromPrimitive(primitive: String, context: PersistentDataAdapterContext) =
			primitive.deserializeToItemStack()

	}

	/**
	 * A custom [PersistentDataType] for [JSON objects][JSONObject].
	 */
	val JSON_OBJECT = object : PersistentDataType<String, JSONObject> {

		override fun getPrimitiveType() = String::class.java

		override fun getComplexType() = JSONObject::class.java

		override fun toPrimitive(complex: JSONObject, context: PersistentDataAdapterContext) =
			"$complex"

		override fun fromPrimitive(primitive: String, context: PersistentDataAdapterContext) =
			JSONObject(primitive)

	}

	/**
	 * A custom [PersistentDataType] for [JSON arrays][JSONArray].
	 */
	val JSON_ARRAY = object : PersistentDataType<String, JSONArray> {

		override fun getPrimitiveType() = String::class.java

		override fun getComplexType() = JSONArray::class.java

		override fun toPrimitive(complex: JSONArray, context: PersistentDataAdapterContext) =
			"$complex"

		override fun fromPrimitive(primitive: String, context: PersistentDataAdapterContext) =
			JSONArray(primitive)

	}

	/**
	 * A custom [PersistentDataType] for lists of [UUID]s.
	 */
	val UUID_LIST = object : ExistingDataTypeListViaJSONArrayPersistentDataType<UUID>(UUID) {}

}