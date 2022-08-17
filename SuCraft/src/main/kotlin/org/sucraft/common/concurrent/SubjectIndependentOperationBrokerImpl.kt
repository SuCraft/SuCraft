/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.concurrent

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import org.sucraft.common.concurrent.SubjectIndependentOperationBrokerImpl.InvalidationCheckable
import org.sucraft.common.concurrent.SubjectIndependentOperationBrokerImpl.WrappedSubjectChannel
import org.sucraft.main.SuCraftPlugin
import java.util.concurrent.ConcurrentHashMap

/**
 * An implementation of [SubjectIndependentOperationBroker].
 *
 * @param K A key that each subject is uniquely identified by.
 *
 * @constructor
 *
 * @param getKey A method to get the key for a subject.
 * @param forgetOutputAfterNoMoreCallbacks Whether to forget output for a subject as soon as there are no more callbacks
 * that use it (default false, i.e. by default the last computed output for each subject will be stored indefinitely).
 * @param expectedSize The expected highest number of subjects for which the output is actively computed or stored.
 * @param scope The [CoroutineScope] to launch coroutines by (by default uses the
 * [plugin's CoroutineScope][SuCraftPlugin.scope]).
 * @param task The task this broker performs. Notably, the task uses a [CoroutineScope] as context:
 * if the overarching computation is invalidated, it can be helpful if the implementation of the task contains
 * calls to [InvalidationCheckable.restartIfInvalidated] (which is implemented by
 * [WrappedSubjectChannel.restartIfInvalidated]) so it can stop as promptly as possible.
 */
open class SubjectIndependentOperationBrokerImpl<S, K : Any, V>(
	protected val getKey: S.() -> K,
	protected val forgetOutputAfterNoMoreCallbacks: Boolean = false,
	protected val expectedSize: Int = 0,
	protected val scope: CoroutineScope = defaultScope,
	protected val task: suspend InvalidationCheckable.(S) -> V
) : SubjectIndependentOperationBroker<S, V> {

	// Managing each individual subject channel

	/**
	 * A throwable used to interrupt the computation of the task in case the value is invalidated.
	 */
	private class ComputationInvalidatedThrowable : Throwable()

	interface InvalidationCheckable {

		suspend fun restartIfInvalidated()

	}

	/**
	 * This class represents the data on task execution and task output we have for a specific subject.
	 * It is a wrapper for a [channel][ReceiveChannel] via which the computed value is passed to any requesting party.
	 * It also keeps track of whether any party is waiting for the value, so that all state for the subject can
	 * be forgotten about if there are no more waiting requests for its value and the existing value has been
	 * invalidated.
	 *
	 * It provides the properties and methods to support the functionality that [SubjectIndependentOperationBrokerImpl]
	 * should provide for each subject.
	 * Different instances of [WrappedSubjectChannel] are independent of each other and operations on them
	 * run asynchronously with respect to each other and the overall [SubjectIndependentOperationBrokerImpl].
	 */
	private inner class WrappedSubjectChannel : InvalidationCheckable {

		/**
		 * The inner [ReceiveChannel] that is initialized in [createSubjectChannel].
		 */
		lateinit var innerChannel: ReceiveChannel<V>

		var waitingReceivers = 0

		/**
		 * Whether the computed value or any already started ongoing computations have been invalidated.
		 */
		var invalidated = false

		/**
		 * This stops the computation, if occurring,
		 * if the value has been [invalidated][invalidate] after the computation started.
		 */
		override suspend fun restartIfInvalidated() {
			// Yield in case of cancellation of the channel
			yield()
			// If invalidated, throw an exception that will be caught in the producer scope
			if (invalidated) throw ComputationInvalidatedThrowable()
		}

	}

	/**
	 * @return A new [channel][WrappedSubjectChannel] for the given [subject], which is a channel that infinitely sends
	 * the computed value.
	 * It starts computing upon calling this method. It must be destroyed when the computed value is invalidated.
	 */
	@OptIn(ExperimentalCoroutinesApi::class)
	private fun createSubjectChannel(subject: S) =
		// Create the WrappedSubjectChannel instance first so that it can be referenced from within the producer scope
		WrappedSubjectChannel()
			// Set the inner channel
			.apply {
				// The below producer is a modified version of computeValueOnce
				innerChannel = scope.produce {
					// Repeat this loop every time the value is invalidated
					// (only channel cancellation breaks out of this loop)
					while (true) {
						try {
							// First we must compute the output
							val output = task(subject)
							// Keep sending the output as long as is needed
							while (true) {
								// Restart if this computed output is no longer needed
								restartIfInvalidated()
								// Send the output
								send(output)
							}
						} catch (_: ComputationInvalidatedThrowable) {
							// Reset the invalidated flag
							invalidated = false
							// We don't need to do anything else, only continue the outer loop
						}
					}
				}
			}

	// Managing the channels

	/**
	 * The map of [channels][WrappedSubjectChannel] for subjects.
	 *
	 * For all requests involving a subject, we first retrieve the channel from this map, or create it
	 * if it does not exist yet.
	 */
	private val channelMap: MutableMap<K, WrappedSubjectChannel> = ConcurrentHashMap(expectedSize * 2)

	/**
	 * Applies the given [operation] to the [channel][WrappedSubjectChannel] for the given [subject], atomically
	 * (with respect to the subject).
	 *
	 * The channel is created if it does not exist, before passing it to the operation.
	 *
	 * @param subject The subject.
	 * @param operation The operation to perform on the channel.
	 *
	 * Use of [ConcurrentHashMap.compute] within this method
	 * both ensures that only one caller can be modifying the state of the
	 * channel or its existence within this broker (this ensures the channel is not deleted from
	 * this broker concurrently with it performing some callback).
	 *
	 * @return The return value of the given operation.
	 */
	private fun launchForSubjectChannel(
		subject: S,
		operation: suspend WrappedSubjectChannel.() -> Unit,
	): Unit {
		// Get the channel and run the operation
		channelMap.compute(subject.getKey()) { _, existingChannel ->
			// Create the channel if it did not exist, but we need it
			val channel = existingChannel ?: createSubjectChannel(subject)
			// Perform the operation and store the value to return from applyToSubjectChannel
			scope.launch { channel.operation() }
			// Return the channel to be stored in channelMap
			channel
		}
	}

	// Functionality

	/**
	 * @return The output of this broker's task for the given [subject].
	 */
	override suspend fun compute(subject: S): V {
		val returnedValueChannel = Channel<V>()
		launchForSubjectChannel(subject) {
			waitingReceivers++
			val value = innerChannel.receive()
			returnedValueChannel.send(value)
		}
		return returnedValueChannel.receive()
	}

	/**
	 * Invalidates any already computed value or the output of any already started computation
	 * for the given [subject].
	 */
	override suspend fun invalidate(subject: S) {
		channelMap.computeIfPresent(subject.getKey()) { _, existingChannel ->
			// Mark the channel as invalidated
			existingChannel.invalidated = true
			// Decide if we need to destroy the channel
			if (forgetOutputAfterNoMoreCallbacks && existingChannel.waitingReceivers == 0) {
				// Cancel the ReceiveChannel
				existingChannel.innerChannel.cancel()
				// Return null to cause removal from channelMap
				null
			} else existingChannel // Otherwise, we keep the channel
		}
	}

}