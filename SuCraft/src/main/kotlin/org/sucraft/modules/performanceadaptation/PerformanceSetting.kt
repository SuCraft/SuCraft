/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.performanceadaptation

import net.minecraft.world.entity.EntityTypes
import org.sucraft.common.serverconfig.paperConfig
import org.sucraft.common.world.mainWorld
import org.sucraft.common.world.runEachWorld
import kotlin.reflect.KClass

/**
 * A setting that can be changed dynamically to calibrate the server's performance.
 */
open class PerformanceSetting<T>(
	val parseValue: (String) -> T,
	val key: String,
	private val getter: () -> T,
	private val setter: (T) -> Unit
) {

	fun get() = getter()

	open fun set(value: T) = setter(value)

	@Suppress("JoinDeclarationAndAssignment")
	val index: Int

	init {
		index = nextPerformanceSettingIndex
		nextPerformanceSettingIndex++
	}

	override fun hashCode() = index

	override fun equals(other: Any?) = (other as? PerformanceSetting<*>)?.let { it.index == index } ?: false

	companion object {

		/**
		 * Used to assign each setting a unique index.
		 */
		var nextPerformanceSettingIndex: Int = 0

		fun byte(
			key: String,
			getter: () -> Byte,
			setter: (Byte) -> Unit
		) = PerformanceSetting({ it.trim().toByte() }, key, getter, setter)

		fun int(
			key: String,
			getter: () -> Int,
			setter: (Int) -> Unit
		) = PerformanceSetting({ it.trim().toInt() }, key, getter, setter)

		fun long(
			key: String,
			getter: () -> Long,
			setter: (Long) -> Unit
		) = PerformanceSetting({ it.trim().toLong() }, key, getter, setter)

		fun double(
			key: String,
			getter: () -> Double,
			setter: (Double) -> Unit
		) = PerformanceSetting({ it.trim().toDouble() }, key, getter, setter)

		fun boolean(
			key: String,
			getter: () -> Boolean,
			setter: (Boolean) -> Unit
		) = PerformanceSetting({ it.trim().lowercase().toBooleanStrict() }, key, getter, setter)

		fun fraction(
			key: String,
			getter: () -> Pair<Int, Int>,
			setter: (Pair<Int, Int>) -> Unit
		) = PerformanceSetting(
			{
				it.split('/').let { split ->
					split[0].trim().toInt() to if (split.size > 1) split[1].trim().toInt() else 1
				}
			},
			key,
			getter,
			setter
		)

		fun tickRate(typeKey: String, entityKey: String, entityType: EntityTypes<*>, columnKey: String) = int(
			"tick-rate/$typeKey/$entityKey/$columnKey",
			{
				mainWorld.paperConfig.tickRates.behavior
					.get(entityType, columnKey)
					?: -1
			},
			{
				runEachWorld {
					paperConfig.tickRates.behavior
						.put(entityType, columnKey, it)
				}
			}
		)

		fun tickRateBehaviorVillager(columnKey: String) =
			tickRate("behavior", "villager", EntityTypes._VILLAGER(), columnKey)

		fun tickRateSensorVillager(columnKey: String) =
			tickRate("sensor", "villager", EntityTypes._VILLAGER(), columnKey)

	}

	fun withValue(value: T) =
		SettingValue(this, value)

	fun withParsedValue(valueString: String) =
		withValue(parseValue(valueString))

	fun withValueUnsafe(value: Any?) =
		@Suppress("UNCHECKED_CAST")
		withValue(value as T)

}