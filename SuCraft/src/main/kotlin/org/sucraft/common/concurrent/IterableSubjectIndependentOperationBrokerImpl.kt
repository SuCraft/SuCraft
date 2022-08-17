/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.concurrent

import kotlinx.coroutines.*

/**
 * An implementation of [IterableSubjectIndependentOperationBroker] built upon
 * [SubjectIndependentOperationBrokerImpl].
 */
open class IterableSubjectIndependentOperationBrokerImpl<S, K : Any, V>(
	getKey: S.() -> K,
	expectedSize: Int = 0,
	scope: CoroutineScope = defaultScope,
	private val getAllSubjects: suspend () -> Iterable<S>,
	private val onlyComputeAllOnce: Boolean = true,
	task: suspend InvalidationCheckable.(S) -> V
) : SubjectIndependentOperationBrokerImpl<S, K, V>(
	getKey,
	false,
	expectedSize,
	scope,
	task
), IterableSubjectIndependentOperationBroker<S, V> {

	private lateinit var precomputeAllCompletionChannel: Job

	private suspend fun precomputeAllInner() {
		getAllSubjects().run {
			zip(map { scope.async { compute(it) } }.awaitAll())
		}
	}

	override suspend fun precomputeAll() {
		if (onlyComputeAllOnce) {
			// Create pre-computation completion channel if it does not exist
			synchronized(this) {
				if (!::precomputeAllCompletionChannel.isInitialized) {
					precomputeAllCompletionChannel = scope.launch {
						precomputeAllInner()
					}
				}
			}
			// Wait for the completion
			precomputeAllCompletionChannel.join()
		} else {
			// If we don't compute only once, we just have to run the actual computation
			precomputeAllInner()
		}
	}

}