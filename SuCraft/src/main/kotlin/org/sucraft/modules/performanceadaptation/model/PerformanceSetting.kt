/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.performanceadaptation.model

/**
 * A setting that can be changed dynamically to calibrate the server's performance.
 */
open class PerformanceSetting<T>(
	val displayName: String,
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

	}

}