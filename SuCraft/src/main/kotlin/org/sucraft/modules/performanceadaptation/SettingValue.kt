/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.performanceadaptation

// Class

/**
 * A pair of a setting and a value for that setting, that can be used to indicate the desired value of a setting
 * for a specific [PerformanceMode].
 */
data class SettingValue<T>(val setting: PerformanceSetting<T>, val value: T)

// Convenient infix notation

infix fun <T> PerformanceSetting<T>.at(value: T) = SettingValue(this, value)