/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.shorttermbackups

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.sucraft.common.concurrent.ioScope
import org.sucraft.common.io.inside
import org.sucraft.common.module.SuCraftComponent
import org.sucraft.common.time.TimeInMillis
import org.sucraft.common.time.TimeInSeconds
import org.sucraft.common.time.TimeLength
import org.sucraft.modules.shorttermbackups.ShortTermBackups.airlockFolder
import org.sucraft.modules.shorttermbackups.ShortTermBackups.backupFolder
import org.sucraft.modules.shorttermbackups.ShortTermBackups.keepBackupFileInterval
import org.sucraft.modules.shorttermbackups.ShortTermBackups.keepLongBackupFileInterval
import org.sucraft.modules.shorttermbackups.ShortTermBackups.longBackupFolder
import org.sucraft.modules.shorttermbackups.ShortTermBackups.maxFileSizeInBytes
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.StandardCopyOption.REPLACE_EXISTING
import java.nio.file.StandardOpenOption
import java.util.*

/**
 * Makes sure the backup tasks (backing up a file and removing old backups) are performed correctly.
 */
object BackupTaskExecutor : SuCraftComponent<ShortTermBackups>() {

	// Settings

	private val intervalBetweenTaskSteps = TimeInMillis(100)

	// Note that at the moment, if the server is stopping, waiting for all tasks will wait for the delays caused be
	// retries to finish, so stopping server may take this much extra time
	private val retryBackupAttemptAfter = TimeInSeconds(10)
	private const val maxBackupTaskAttempts = 5

	// Using bukkitScope here seems like a bad idea because the task executions may clash with
	// the runBlocking block on the main thread in waitForAllTasksToComplete
	private val scope = ioScope

	// Data

	private val backupTasksInProgressMutex = Mutex()
	private val backupTasksInProgress: MutableSet<String> = HashSet(0)
	private val oldFileRemovalTasksInProgressMutex = Mutex()
	private val oldFileRemovalTasksInProgress: MutableSet<String> = HashSet(0)

	private val taskInProgressJobsMutex = Mutex()
	private val taskInProgressJobs: MutableSet<Job> = Collections.newSetFromMap(IdentityHashMap(0))

	/**
	 * Can be marked true when the server is stopping.
	 */
	private var retriesShouldBeCancelled = false

	// Provided functionality

	/**
	 * Starts a backup of the given file, if necessary.
	 */
	fun startFileBackupTask(originalFile: File, delay: TimeLength? = null) {
		BackupTask(originalFile).start(delay)
	}

	/**
	 * Starts a removal of the given file, if necessary.
	 */
	fun startOldFileRemovalTask(file: File, minimumAgeToDelete: TimeLength, delay: TimeLength? = null) {
		OldFileRemovalTask(file, minimumAgeToDelete).start(delay)
	}

	/**
	 * Waits for all tasks to complete: blocks the thread.
	 */
	fun waitForAllTasksToComplete() {
		runBlocking {
			// Wait for completion of every type of task
			var printedToConsole = false
			while (true) {
				val jobToWaitFor: Job
				val numberOfJobsLeft: Int
				taskInProgressJobsMutex.withLock {
					if (taskInProgressJobs.isEmpty()) return@runBlocking
					jobToWaitFor = taskInProgressJobs.first()
					numberOfJobsLeft = taskInProgressJobs.size
				}
				retriesShouldBeCancelled = true
				if (!printedToConsole) {
					info(
						"Waiting for tasks ($numberOfJobsLeft) in progress to complete" +
								" (this may take around ${retryBackupAttemptAfter.seconds} seconds)..."
					)
					printedToConsole = true
				}
				jobToWaitFor.join()
			}
		}
	}

	// Implementation

	private val File.airlockFileForOriginalFile
		get() = path inside airlockFolder

	private val File.backupFileForOriginalFile
		get() = path inside backupFolder

	private val File.longBackupFileForOriginalFile
		get() = path inside longBackupFolder

	/**
	 * An ongoing task.
	 */
	private abstract class Task {

		// Data

		protected abstract val id: String
		private lateinit var job: Job

		// Functionality

		/**
		 * Starts this task.
		 */
		fun start(delay: TimeLength? = null) {
			scope.launch {
				// Initial delay
				if (delay != null) delay(delay.millis)
				// Add this task to the tasks in progress
				tasksInProgressMutex.withLock {
					if (!tasksInProgress.add(id)) {
						return@launch
					}
				}
				job = coroutineContext.job
				taskInProgressJobsMutex.withLock {
					taskInProgressJobs.add(job)
				}
				// Start the first step
				startFirstStep()
			}
		}

		// Implementation

		protected abstract suspend fun startFirstStep()

		protected abstract val tasksInProgressMutex: Mutex
		protected abstract val tasksInProgress: MutableSet<String>

		/**
		 * Cancels this task.
		 */
		protected suspend fun cancel() {
			tasksInProgressMutex.withLock {
				tasksInProgress.remove(id)
			}
			taskInProgressJobsMutex.withLock {
				taskInProgressJobs.remove(job)
			}
		}

	}

	/**
	 * An ongoing backup task.
	 */
	private class BackupTask(private val originalFile: File) : Task() {

		// Derived values

		private val originalFileCanonicalPath: String = originalFile.canonicalPath
		private val airlockFile = originalFile.airlockFileForOriginalFile
		private val backupFile = originalFile.backupFileForOriginalFile
		private val longBackupFile = originalFile.longBackupFileForOriginalFile

		override val id get() = originalFileCanonicalPath

		// Data

		private var attempt = 0

		// Subfunctions for the backup task steps

		/**
		 * The first step, which does the following in order:
		 * - Checks if there is a too recent existing backup file,
		 * and if so, will cancel this task altogether.
		 * - Checks if the original file exists,
		 * and if not, will retry this task [later][retryBackupAttemptAfter],
		 * or if [too many][maxBackupTaskAttempts] retries have been attempted, will cancel this task altogether.
		 * - Checks if the original file is too large,
		 * and if so, will cancel this task altogether.
		 * - Checks if the original file is empty,
		 * and if so, will retry this task [later][retryBackupAttemptAfter],
		 * or if [too many][maxBackupTaskAttempts] retries have been attempted, will cancel this task altogether.
		 * - Continues with the [second step][copyOriginalFileToAirlockFileAndVerifyUnchangedContent].
		 */
		private suspend fun cancelBackupTaskIfShouldNotExecute() {
			// Check if there is a too recent existing backup file
			if (backupFile.exists() &&
				backupFile.lastModified() >= System.currentTimeMillis() - keepBackupFileInterval.millis
			) {
				// Cancel this task
				cancel()
				return
			}
			// Check if the original file exists
			if (!originalFile.exists()) {
				// Retry this task later
				retryLaterOrCancel()
				return
			}
			// Check if the original file is too large
			val originalFileSize = originalFile.length()
			if (originalFileSize > maxFileSizeInBytes) {
				// Cancel this task
				cancel()
				return
			}
			// Check if the original file is empty
			if (originalFileSize == 0L) {
				// Retry this task later
				retryLaterOrCancel()
				return
			}
			// Log to console
			info("Ready to back up original file ${originalFile.path}")
			// Continue with the second step
			delay(intervalBetweenTaskSteps.millis)
			copyOriginalFileToAirlockFileAndVerifyUnchangedContent()
		}

		/**
		 * The second step, does the following in order:
		 * - Copies the original file to the airlock file.
		 * - Reads the original file again and checks whether the content is still the same,
		 * and if not, will retry this task [later][retryBackupAttemptAfter],
		 * or if [too many][maxBackupTaskAttempts] retries have been attempted, will cancel this task altogether.
		 * - Continues with the [third step][moveAirlockFileToBackupFile].
		 */
		private suspend fun copyOriginalFileToAirlockFileAndVerifyUnchangedContent() {
			// Read the original file
			val originalFileContents = withContext(Dispatchers.IO) {
				Files.newInputStream(originalFile.toPath(), StandardOpenOption.READ)
			}.use { it.readAllBytes() }
			// Create the airlock file destination folder
			airlockFile.parentFile.mkdirs()
			// Write the original file content to the airlock file
			withContext(Dispatchers.IO) {
				FileOutputStream(airlockFile).use { it.write(originalFileContents) }
			}
			// Read the new (potentially changed) contents of the source file
			val updatedOriginalFileContents = withContext(Dispatchers.IO) {
				Files.newInputStream(originalFile.toPath(), StandardOpenOption.READ)
			}.use { it.readAllBytes() }
			// Check if the original file changed
			if (!Arrays.equals(originalFileContents, updatedOriginalFileContents)) {
				// Log to console
				info(
					"After copying original file ${originalFile.path} " +
							"to airlock file ${airlockFile.path}, " +
							"the original file changed: retrying in a moment..."
				)
				// Retry this task later
				retryLaterOrCancel()
				return
			}
			// Log to console
			info("Copied original file ${originalFile.path} to airlock file ${airlockFile.path}")
			// Continue with the third step
			delay(intervalBetweenTaskSteps.millis)
			moveAirlockFileToBackupFile()
		}

		/**
		 * The third step, which moves the airlock file to the backup file, overwriting the backup file.
		 * Afterwards, will continue with the [fourth step][copyBackupFileToLongBackupFile].
		 */
		private suspend fun moveAirlockFileToBackupFile() {
			// If the airlock file doesn't exist or is empty: maybe there is some sort of IO desynchronization,
			// we will try this step again later
			if (airlockFile.length() == 0L) {
				// Log to console
				warning(
					"When attempting to move airlock file ${airlockFile.path} " +
							"to backup file ${backupFile.path}, " +
							"the airlock file was empty: retrying in a moment..."
				)
				retryLaterOrCancel(3)
				return
			}
			// Create the backup file destination folder
			backupFile.parentFile.mkdirs()
			// Move the airlock file to the backup file
			withContext(Dispatchers.IO) {
				Files.move(airlockFile.toPath(), backupFile.toPath(), REPLACE_EXISTING)
			}
			// Log to console
			info("Moved airlock file ${airlockFile.path} to backup file ${backupFile.path}")
			// Continue with the fourth step
			delay(intervalBetweenTaskSteps.millis)
			copyBackupFileToLongBackupFile()
		}

		/**
		 * The fourth step, which copies the backup file to the long backup file, overwriting the long backup file.
		 * This step completes the backup task.
		 */
		private suspend fun copyBackupFileToLongBackupFile() {
			// If the long backup file already exists and is too recent, we do not need to do anything:
			// in that case we cancel the task from here
			if (longBackupFile.exists()) {
				val lastModified = longBackupFile.lastModified()
				if (lastModified >= System.currentTimeMillis() - keepLongBackupFileInterval.millis) {
					// Log to console
					info(
						"There is no need to copy backup file ${backupFile.path} " +
								"to long backup file ${longBackupFile.path}, " +
								"because a recent enough version (${Date(lastModified)}) " +
								"already exists"
					)
					cancel()
					return
				}
			}
			// If the backup file doesn't exist or is empty: maybe there is some sort of IO desynchronization,
			// we will try this step again later
			if (backupFile.length() == 0L) {
				// Log to console
				warning(
					"When attempting to copy backup file ${backupFile.path} " +
							"to long backup file ${longBackupFile.path}, " +
							"the backup file was empty: retrying in a moment..."
				)
				// Retry this step later
				retryLaterOrCancel(4)
				return
			}
			// Create the long backup file destination folder
			longBackupFile.parentFile.mkdirs()
			// Move the backup file to the long backup file
			withContext(Dispatchers.IO) {
				Files.copy(backupFile.toPath(), longBackupFile.toPath(), REPLACE_EXISTING)
			}
			// Log to console
			info("Copied backup file ${backupFile.path} to long backup file ${longBackupFile.path}")
			// Remove this task from the ongoing tasks
			cancel()
		}

		// Implementation

		override suspend fun startFirstStep() = cancelBackupTaskIfShouldNotExecute()

		override val tasksInProgressMutex get() = backupTasksInProgressMutex
		override val tasksInProgress get() = backupTasksInProgress

		/**
		 * Retries this backup task [later][retryBackupAttemptAfter],
		 * or cancels if the [maximum number of attempts][maxBackupTaskAttempts] is exceeded.
		 */
		private suspend fun retryLaterOrCancel(stepToResume: Int = 1) {
			attempt++
			// Cancel if the maximum number of attempts has been reached, or if retries are no longer desired
			if (attempt >= maxBackupTaskAttempts || retriesShouldBeCancelled) {
				cancel()
				return
			}
			// Wait
			delay(retryBackupAttemptAfter.millis)
			// Cancel if retries are no longer desired
			if (retriesShouldBeCancelled) {
				cancel()
				return
			}
			// Resume the step
			when (stepToResume) {
				1 -> cancelBackupTaskIfShouldNotExecute()
				2 -> copyOriginalFileToAirlockFileAndVerifyUnchangedContent()
				3 -> moveAirlockFileToBackupFile()
				4 -> copyBackupFileToLongBackupFile()
			}
		}

	}

	/**
	 * An ongoing old file removal task.
	 */
	private class OldFileRemovalTask(private val file: File, private val minimumAgeToDelete: TimeLength) : Task() {

		// Derived values

		private val fileCanonicalPath: String = file.canonicalPath

		// Data

		override val id get() = fileCanonicalPath

		// Implementation of the old file removal task

		/**
		 * Performs the removal of the old file if necessary, which does the following in order:
		 * - Checks if the file exists,
		 * and if not, cancels this task.
		 * - Checks if the file is [too recent][minimumAgeToDelete] to be deleted,
		 * and if so, cancels this task.
		 * - Deletes the file.
		 */
		private suspend fun performRemoval() {
			// Check if the file exists
			if (!file.exists()) {
				// Cancel this task, because the file doesn't exist
				cancel()
				return
			}
			// Check if the file is too recent
			if (file.lastModified() >= System.currentTimeMillis() - minimumAgeToDelete.millis) {
				// Cancel this task, because the file is too recent
				cancel()
				return
			}
			// Delete the file
			file.delete()
			// Log to console
			info("Removed old file ${file.path}")
			// Remove this task from the ongoing tasks
			cancel()
		}

		// Implementation

		override suspend fun startFirstStep() = performRemoval()

		override val tasksInProgressMutex get() = oldFileRemovalTasksInProgressMutex
		override val tasksInProgress get() = oldFileRemovalTasksInProgress

	}

}