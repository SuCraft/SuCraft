/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.concurrent

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.yield

/**
 * Creates a channel that computes a single value
 * (with the computation starting as soon as the channel is instantiated)
 * and thenSync forever keeps sending that value.
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun <T> CoroutineScope.computeValueOnceUsingChannel(computation: suspend ProducerScope<T>.() -> T) =
	produce {
		// First we must compute the value
		val output = computation()
		// Keep sending the output as long as is needed
		while (true) {
			// Stop (do not even start sending) if this channel ahs been cancelled
			yield()
			// Send the output
			send(output)
		}
	}

/**
 * Creates a channel that performs a specific computation once
 * (starting as soon as the channel is instantiated)
 * and simply suspends all receive calls until the computation is complete.
 *
 * This is essentially just an alias for [computeValueOnceUsingChannel], but with the clear purpose of not using the return value
 * of the computation.
 */
fun CoroutineScope.awaitComputationOnceUsingChannel(computation: suspend ProducerScope<Unit>.() -> Unit) =
	computeValueOnceUsingChannel {
		computation()
	}
