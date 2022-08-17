/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

@file:Suppress("unused", "UNUSED_PARAMETER")

package org.sucraft.common.concurrent

/**
 * A utility class that contains a [computation function][computationFunction] to compute a value,
 * but for which the result may be marked as outdated later (after which, to get the newest up-to-date value,
 * the computation function must be called again).
 *
 * Depending on the needs of the requester of the value, they can choose to:
 * - [Use the correct up-to-date value][getUpToDate].
 * Note that at any time during any computation that uses the result of the [Future], the value may
 * become no longer up-to-date due to changes in the input to the [computation function][computationFunction]
 * from another thread.
 *
 * TODO unfinished
 */
class SometimesOutdatedValue<T>(
	computationFunction: () -> T
) {

}