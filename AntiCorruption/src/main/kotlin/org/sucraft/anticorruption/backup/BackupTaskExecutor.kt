/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.anticorruption.backup

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask
import org.sucraft.anticorruption.main.SuCraftAntiCorruptionPlugin
import org.sucraft.core.common.bukkit.time.TickTime
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.math.ceil


object BackupTaskExecutor : SuCraftComponent<SuCraftAntiCorruptionPlugin>(SuCraftAntiCorruptionPlugin.getInstance()) {

	// Settings

	private const val maxFileSizeInBytes = 1024L * 1024 * 50 // 50 MB

	private const val intervalBetweenTaskStepsInMillis = 100L
	private const val retryBackupAttemptAfterMillis = 1000L * 10 // 10 seconds
	private const val maxBackupTaskAttempts = 40

	private const val keepBackupFileIntervalInMillis = 1000L * 3600 * 24 * 2 // 2 days
	private const val keepLongBackupFileIntervalInMillis = 1000L * 3600 * 24 * 7 // 7 days

	// Data

	private val queueLock = ReentrantLock()

	private val queue: Queue<RunnableAfterMinimumSystemTime> = PriorityQueue()

	/**
	 * The key of this map is the attempt (the first attempt being 1)
	 */
	private val backupTasksInProgress: MutableMap<BackupTask, Int> = HashMap()

	private var queueExecutionBukkitTask: BukkitTask? = null
	private var queueExecutionBukkitTaskPlannedTimeInMillis: Long = 0

	// Steps in backing up

	/**
	 * Step 1: checks whether the file needs to be backed up, if so, copies the file to airlock and verifies the airlock file
	 */
	private fun copyToAirlock(task: BackupTask) {
		try {
			// If the backup file already exists and is too recent, cancel
			val backupFile = task.backupFile
			if (backupFile.exists()) {
				if (backupFile.lastModified() >= System.currentTimeMillis() - keepBackupFileIntervalInMillis) {
					unscheduleTask(task)
					return
				}
			}
			// If the source file doesn't exist, try again later
			val sourceFile = task.sourceFile
			if (!sourceFile.exists()) {
				rescheduleTask(task) { copyToAirlock(task) }
				return
			}
			// If the source file is too large, cancel
			val sourceFileLength = sourceFile.length()
			if (sourceFileLength > maxFileSizeInBytes) {
				unscheduleTask(task)
				return
			}
			// If the source file is empty, try again later
			if (sourceFileLength == 0L) {
				rescheduleTask(task) { copyToAirlock(task) }
				return
			}
			// Read the contents of the source file
			val sourceFileContents = FileInputStream(sourceFile).use { it.readAllBytes() }
			// Write the contents of the source file to the airlock file
			val airlockFile = task.airlockFile
			airlockFile.parentFile.mkdirs()
			FileOutputStream(airlockFile).use { it.write(sourceFileContents) }
			// Read the new contents of the source file
			val updatedSourceFileContents = FileInputStream(sourceFile).readAllBytes()
			// If the source file has changed, try again later
			if (!Arrays.equals(sourceFileContents, updatedSourceFileContents)) {
				rescheduleTask(task) { copyToAirlock(task) }
				return
			}
			// The airlock file is a consistent copy, so we can move it to the backup location: schedule that task
			queueLock.withLock {
				backupTasksInProgress[task] = 1
				queue.add(RunnableAfterMinimumSystemTime.after(intervalBetweenTaskStepsInMillis) { copyToBackup(task) })
			}
			// Log success of step
			logger.info("Copied source file '${sourceFile.path}' to airlock file")
		} catch (e: Exception) {
			// If an exception happens, try again later
			rescheduleTask(task) { copyToAirlock(task) }
		}
	}

	/**
	 * Step 2: copies the airlock file to backup
	 */
	private fun copyToBackup(task: BackupTask) {
		try {
			// If the airlock file doesn't exist, cancel
			val airlockFile = task.airlockFile
			if (!airlockFile.exists()) {
				unscheduleTask(task)
				return
			}
			// If the airlock file is too large, cancel
			val airlockFileLength = airlockFile.length()
			if (airlockFileLength > maxFileSizeInBytes) {
				unscheduleTask(task)
				return
			}
			// If the airlock file is empty, try again later
			if (airlockFileLength == 0L) {
				rescheduleTask(task) { copyToBackup(task) }
				return
			}
			// Move the airlock file to the backup file
			val backupFile = task.backupFile
			backupFile.parentFile.mkdirs()
			Files.move(airlockFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
			// Schedule copying the backup file to a long backup file
			queueLock.withLock {
				queue.add(RunnableAfterMinimumSystemTime.after(intervalBetweenTaskStepsInMillis) { copyToLongBackup(task) })
			}
			// Log success of step
			logger.info("Copied airlock file for source file '${task.sourceFile.path}' to backup file")
		} catch (e: Exception) {
			// If an exception happens, try again later
			rescheduleTask(task) { copyToAirlock(task) }
		}
	}

	/**
	 * Step 3: copies the backup file to long backup
	 */
	private fun copyToLongBackup(task: BackupTask) {
		try {
			// If the long backup file already exists and is too recent, cancel
			val longBackupFile = task.longBackupFile
			if (longBackupFile.exists()) {
				if (longBackupFile.lastModified() >= System.currentTimeMillis() - keepLongBackupFileIntervalInMillis) {
					unscheduleTask(task)
					return
				}
			}
			// If the backup file doesn't exist, cancel
			val backupFile = task.backupFile
			if (!backupFile.exists()) {
				unscheduleTask(task)
				return
			}
			// If the backup file is too large, cancel
			val backupFileLength = backupFile.length()
			if (backupFileLength > maxFileSizeInBytes) {
				unscheduleTask(task)
				return
			}
			// If the backup file is empty, try again later
			if (backupFileLength == 0L) {
				rescheduleTask(task) { copyToLongBackup(task) }
				return
			}
			// Copy the backup file to the long backup file
			longBackupFile.parentFile.mkdirs()
			Files.copy(backupFile.toPath(), longBackupFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
			// Unschedule this task, since it has been completed
			unscheduleTask(task)
			// Log success of step
			logger.info("Copied backup file for source file '${task.sourceFile.path}' to long backup file")
		} catch (e: Exception) {
			// If an exception happens, try again later
			rescheduleTask(task) { copyToAirlock(task) }
		}
	}

	// Old file removal

	private fun removeOldFile(file: File, minimumAgeInMillis: Long) {
		try {
			// If the file doesn't exist, then no need
			if (!file.exists()) return
			// If the file is too recent, then no need
			if (file.lastModified() >= System.currentTimeMillis() - minimumAgeInMillis) return
			// Delete the file
			file.delete()
			// Log success of removal
			logger.info("Removed old file '${file.path}'")
		} catch (e: Exception) {
			// If an exception happens, do not try again
		}
	}

	// Schedule tasks

	private fun unscheduleTask(task: BackupTask) {
		queueLock.withLock {
			backupTasksInProgress.remove(task)
		}
	}

	private fun rescheduleTask(task: BackupTask, action: () -> Unit) {
		queueLock.withLock {
			if (SuCraftAntiCorruptionPlugin.getInstance().isDisabledOrDisabling || backupTasksInProgress[task]!! >= maxBackupTaskAttempts) {
				unscheduleTask(task)
			} else {
				backupTasksInProgress[task] = backupTasksInProgress[task]!! + 1
				// Add the step again
				queue.add(RunnableAfterMinimumSystemTime.after(retryBackupAttemptAfterMillis, action))
				// Start the queue task if it doesn't exist
				startExecuteQueueItemsTask()
			}
		}
	}

	fun scheduleRunnable(runnable: Runnable, delayInMillis: Long? = null) {
		queueLock.withLock {
			// Add the runnable to the queue
			queue.add(
				delayInMillis
					?.let { RunnableAfterMinimumSystemTime.after(it, runnable) }
					?: RunnableAfterMinimumSystemTime.anytime(runnable)
			)
			// Start the queue task if it doesn't exist
			startExecuteQueueItemsTask()
		}
	}

	fun scheduleBackup(file: FileToBackup, delayInMillis: Long? = null) {
		queueLock.withLock {
			val task = BackupTask(file)
			// Only if this task wasn't in progress yet
			if (task !in backupTasksInProgress) {
				backupTasksInProgress[task] = 1
				// Add the first step
				scheduleRunnable({ copyToAirlock(task) }, delayInMillis)
			}
		}
	}

	fun scheduleOldFileRemoval(file: File, minimumAgeInMillis: Long, delayInMillis: Long? = null) {
		queueLock.withLock {
			scheduleRunnable({ removeOldFile(file, minimumAgeInMillis) }, delayInMillis)
		}
	}

	// Executing tasks scheduled in the queue

	private fun startExecuteQueueItemsTask() {
		queueLock.withLock {
			// Don't schedule anything with Bukkit if the plugin is already disabling
			if (SuCraftAntiCorruptionPlugin.getInstance().isDisabledOrDisabling) return
			// No need if the queue is empty
			if (queue.isEmpty()) return
			val nextTask = queue.peek()
			val timeNextTaskWouldBeScheduledFor = if (nextTask.isDue()) System.currentTimeMillis() else nextTask.minimumSystemTime
			if (queueExecutionBukkitTask != null) {
				// If the task already started is scheduled in time, we don't need to do anything
				if (queueExecutionBukkitTaskPlannedTimeInMillis <= timeNextTaskWouldBeScheduledFor) return
				// Otherwise, we have to cancel the already started task and schedule a new one
				queueExecutionBukkitTask!!.cancel()
				queueExecutionBukkitTask = null
			}
			// Schedule the task with Bukkit
			if (nextTask.isDue())
				Bukkit.getScheduler().runTaskAsynchronously(SuCraftAntiCorruptionPlugin.getInstance(), ::executeQueueItem)
			else
				Bukkit.getScheduler().runTaskLaterAsynchronously(SuCraftAntiCorruptionPlugin.getInstance(), Runnable { executeQueueItem() }, ceil(TickTime.millisToTicks(nextTask.minimumSystemTime - System.currentTimeMillis())).toLong())
			queueExecutionBukkitTaskPlannedTimeInMillis = timeNextTaskWouldBeScheduledFor
		}
	}

	private fun executeQueueItem() {
		queueLock.withLock {
			// No need if the queue is empty
			if (queue.isEmpty()) return
			// Only if the task is actually due, otherwise do nothing (except after this, schedule with Bukkit again)
			if (queue.peek().isDue()) {
				// Execute the task
				queue.poll().run()
			}
			// Schedule with Bukkit again if needed
			queueExecutionBukkitTask = null
			startExecuteQueueItemsTask()
		}
	}

	fun executeAllInQueueSynchronously() {
		queueExecutionBukkitTask = null
		queueLock.withLock {
			while (queue.isNotEmpty()) {
				// Execute the task
				queue.poll().run()
			}
		}
	}

}