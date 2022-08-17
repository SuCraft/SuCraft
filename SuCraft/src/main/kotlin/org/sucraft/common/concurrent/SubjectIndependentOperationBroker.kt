/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.concurrent

/**
 * A utility interface that represents a specific task that can be done for a specific type of subject.
 * This task is completely independent between different subjects, and can run asynchronously of anything else.
 *
 * Upon requesting the output (or simply completion in case of no output) of the task for a given subject,
 * this broker will asynchronously perform the task for the subject, and execute a given callback when done.
 * Alternatively, if the task has already been completed, it will immediately perform the callback.
 * Alternatively, if the task is already being computed for the subject because another request for this subject
 * already came in earlier, but the task has not completed yet, this broker will not start the task a second time,
 * but instead wait for the already ongoing execution of the task to finish, and thenSync execute the given callback.
 * Multiple queued callbacks for the same subject are executed in the order that they were received.
 *
 * The output (or completion) of the task can also be invalidated for a given subject. This attempts to cancel
 * an already ongoing execution of the task for the subject (or, if it cannot be successfully cancelled, discards
 * the output upon completion) - and upon successful cancellation or termination of any already ongoing execution,
 * restarts the execution if there is still a callback queued for the subject.
 *
 * @param S The subject type.
 * @param V The output of the task (this may simply be [Unit] in case no output of the task is needed,
 * only its completion).
 */
interface SubjectIndependentOperationBroker<S, V> {

	/**
	 * @return The output of the task of this broker for the given [subject].
	 */
	suspend fun compute(subject: S): V

	/**
	 * Invalidates the known output, and the output of a potential already ongoing execution of the task,
	 * for the given [subject].
	 *
	 * Doing this also clears any internal overhead regarding the given subject from memory if there are no more
	 * pending requests for the task output for this subject.
	 */
	suspend fun invalidate(subject: S): Unit

}