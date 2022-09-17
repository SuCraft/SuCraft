/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.performanceadaptation

import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import org.sucraft.modules.performanceadaptation.PerformanceAdaptation.modes

/**
 * A performance mode.
 */
class PerformanceMode(
	val index: Int,
	val internalName: String,
	val displayName: String,
	val msptToDecreaseMode: Double,
	vararg settingValues: SettingValue<*>
) : Comparable<PerformanceMode> {

	private val valuesBySetting: Int2ObjectMap<*> =
		Int2ObjectOpenHashMap(settingValues.asSequence().map { it.setting.index to it.value }.toMap())

	fun <T> getSettingValue(setting: PerformanceSetting<T>): T =
		if (setting.index in valuesBySetting)
			@Suppress("UNCHECKED_CAST")
			valuesBySetting[setting.index] as T
		else
			getLowerMode().getSettingValue(setting)

	private fun <T> applySettingValue(setting: PerformanceSetting<T>) =
		setting.set(getSettingValue(setting))

	private fun <T> applySettingValueIfDiffering(setting: PerformanceSetting<T>, otherValue: T) =
		getSettingValue(setting).let { if (otherValue != it) setting.set(it) }

	private fun <T> applySettingValueIfDifferingWithMode(setting: PerformanceSetting<T>, otherMode: PerformanceMode) =
		applySettingValueIfDiffering(setting, otherMode.getSettingValue(setting))

	fun applyAllSettingValues() =
		performanceSettings.forEach { applySettingValue(it) }

	fun applyDifferingSettingValues(otherMode: PerformanceMode) =
		performanceSettings.forEach { applySettingValueIfDifferingWithMode(it, otherMode) }

	fun getLowerMode() = modes[index + 1]

	fun getHigherMode() = modes[index - 1]

	override fun compareTo(other: PerformanceMode) = index.compareTo(other.index)

	companion object {

		val highest by lazy { modes[0] }

		val lowest by lazy { modes[modes.size - 1] }

	}

}