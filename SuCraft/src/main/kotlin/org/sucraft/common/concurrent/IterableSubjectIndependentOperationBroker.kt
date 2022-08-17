/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.concurrent

/**
 * A [SubjectIndependentOperationBroker] that additionally provides the ability to perform the task
 * for all subjects: the subjects must be able to be iterated over in some way.
 *
 * The implementation of performing the task for all subjects may be extra efficient.
 */
interface IterableSubjectIndependentOperationBroker<S, V> : SubjectIndependentOperationBroker<S, V> {

	/**
	 * Pre-computes the outputs of all subjects in the provided iterable.
	 *
	 * This method starts the task for all subjects in parallel, and suspends until all tasks are complete.
	 */
	suspend fun precomputeAll()

}