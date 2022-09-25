/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.performanceadaptation

import it.unimi.dsi.fastutil.objects.Object2IntMap
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import org.apache.commons.csv.CSVFormat
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerJoinEvent
import org.sucraft.common.event.on
import org.sucraft.common.function.runEach
import org.sucraft.common.io.inside
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.scheduler.runTimer
import org.sucraft.common.time.TimeInSeconds
import org.sucraft.common.time.TimeInTicks
import org.sucraft.modules.shorttermbackups.ShortTermBackups
import org.sucraft.modules.teleportfollowingmobsbeforeunload.TeleportFollowingMobsBeforeUnload
import java.io.FileInputStream
import java.util.*
import kotlin.math.pow

/**
 * Automatically changes the server settings (particularly those that may influence the
 * performance of the server, notably the quality-TPS tradeoff) based on the historical TPS.
 */
object PerformanceAdaptation : SuCraftModule<PerformanceAdaptation>() {

	// Dependencies

	override val dependencies = listOf(
		ShortTermBackups,
		TeleportFollowingMobsBeforeUnload
	)

	// Settings

	private const val modesFilename = "performance_adaptation.csv"
	private const val modesFileKeyColumns = 3

	private val minimumDelayBeforeModeDecrease = TimeInSeconds(6)
	private val minimumDelayBeforeModeIncrease = TimeInSeconds(16)

	private val runningMSPTDecayHalftime = TimeInSeconds(4) + TimeInTicks(10)
	private val runningMSPTMomentum = 0.5.pow(1.0 / runningMSPTDecayHalftime.ticksExact)
	private val useMaxRunningMSPTOverTime = TimeInSeconds(2) + TimeInTicks(10)

	/**
	 * If this is 1.5, and 5 players were previously online, and a new player joins, then
	 * the [running MSPT][runningMSPT] will be multiplied by the factor (5+1.5)/5 to estimate
	 * what the MSPT would have been historically like if there had been another 1.5 players online.
	 * This is so that we don't have to wait for the running MSPT to actually drop from a new player joining,
	 * potentially leading to temporary lag.
	 *
	 * The best estimate would be to have this value be 1, but it seems good to take a slightly higher value
	 * so that we account for the fact a new player joining will cost more processing than existing players
	 * (due to world loading, plugins loading data, and so on).
	 */
	private const val numberOfExtraPeopleRetroactivelyAssumedForMSPTOnJoin = 1.5

	private const val printSettingsAtModeChange = false

	// Data

	/**
	 * Will be initialized in [loadPerformanceModes].
	 */
	lateinit var modes: Array<PerformanceMode>

	/**
	 * Will be initialized to [PerformanceMode.highest] after the modes have been [loaded][loadPerformanceModes].
	 */
	private lateinit var currentMode: PerformanceMode

	/**
	 * In milliseconds as given by [System.currentTimeMillis].
	 */
	private var lastModeChangeTimeMillis = 0L

	private var runningMSPT = 1.0 // Arbitrary low starting value

	class MSPTMeasurement(
		/**
		 * In milliseconds as given by [System.currentTimeMillis].
		 */
		val time: Long,
		val mspt: Double
	)

	/**
	 * A deque of measured MSPTs, in order of their [time][MSPTMeasurement.time]
	 * (with the newest, i.e. those with the highest [time][MSPTMeasurement.time], at the end of the deque).
	 *
	 * The following variants are maintained:
	 * - For any two measurements A and B stored, if A appears before B,
	 * then A's [MSPT][MSPTMeasurement.mspt] is higher than B's [MSPT][MSPTMeasurement.mspt].
	 * - For any measurement stored, its [time][MSPTMeasurement.time] is less than
	 * [useMaxRunningMSPTOverTime] ago.
	 */
	private val runningMSPTMeasurements: Deque<MSPTMeasurement> = ArrayDeque()

	// Initialization

	override fun onInitialize() {
		super.onInitialize()
		// Load the performance modes from the configuration
		loadPerformanceModes()
		forceApplyCurrentModeSettingValues()
		// Schedule to update the measured MSPT and update the performance mode every tick
		runTimer(TimeInTicks.ONE) {
			updateMeasuredMSPT()
			if (shouldGoDownAMode()) goDownAMode() else if (shouldGoUpAMode()) goUpAMode()
		}
	}

	// Events

	init {
		// Listen for player joins to multiply the running MSPT to 'imagine' what the
		// MSPT would have been like for the new number of players
		on(PlayerJoinEvent::class) {
			val numberOfPlayersOnline = Bukkit.getOnlinePlayers().size
			runningMSPT *=
				(numberOfPlayersOnline + numberOfExtraPeopleRetroactivelyAssumedForMSPTOnJoin) / numberOfPlayersOnline
		}
	}

	// Implementation

	/**
	 * The value used for MSPT is the maximum of any MSPT [measured][Bukkit.getAverageTickTime] over
	 * a [period of time][useMaxRunningMSPTOverTime].
	 * If no measurements have been made yet, a default value of 1.0 is returned.
	 */
	private fun getMSPTToUse() =
		if (runningMSPTMeasurements.isEmpty()) 1.0 else runningMSPTMeasurements.peekFirst().mspt

	private fun updateMeasuredMSPT() {

		// Measure the MSPT
		val measuredMSPT = Bukkit.getAverageTickTime()
			// Numerical stability, just in case
			.coerceIn(0.1, 1000.0)

		// Update the running MSPT
		runningMSPT = runningMSPTMomentum * runningMSPT + (1 - runningMSPTMomentum) * measuredMSPT

		// Create the new running MSPT measurement to be added
		val currentTime = System.currentTimeMillis()
		val newRunningMeasurement = MSPTMeasurement(currentTime, runningMSPT)

		// Remove any leading measurements that are too long ago
		while (runningMSPTMeasurements.isNotEmpty() &&
			runningMSPTMeasurements.peekFirst().time <= currentTime - useMaxRunningMSPTOverTime.millis
		) runningMSPTMeasurements.removeFirst()

		// Remove any trailing measurements that have a lower running MSPT than the newly measured one
		while (runningMSPTMeasurements.isNotEmpty() &&
			runningMSPTMeasurements.peekLast().mspt <= newRunningMeasurement.mspt
		) runningMSPTMeasurements.removeLast()

		// Add the new measurement
		runningMSPTMeasurements.add(newRunningMeasurement)

	}

	private fun loadPerformanceModes() {

		val csvRecords = CSVFormat.Builder.create(CSVFormat.DEFAULT).apply {
			setIgnoreSurroundingSpaces(true)
		}.build().parse(FileInputStream(modesFilename inside folder).reader())
			.toList()
		var lastParts: List<String>? = null
		val keyToRow: Object2IntMap<String> = Object2IntOpenHashMap<String>(csvRecords.size).apply {
			csvRecords.forEachIndexed { csvRecordIndex, csvRecord ->
				val parts = (0 until modesFileKeyColumns)
					.map {
						csvRecord[it].trim().filter { c ->
							Character.isAlphabetic(c.code) || Character.isDigit(c) || c == '-' || c == '_' || c == '/'
						}
					}.toMutableList()
				(modesFileKeyColumns - 1 downTo 1).forEach {
					if (parts[it].isNotBlank() && parts[it - 1].isBlank()) {
						parts[it - 1] = lastParts!![it - 1]
					}
				}
				lastParts = parts
				var key = parts[0]
				for (i in 1 until modesFileKeyColumns)
					parts[i].takeIf { it.isNotBlank() }?.let { key += "/$it" }
				put(key, csvRecordIndex)
			}
		}.apply {
			defaultReturnValue(-1)
		}
		modes = Array<PerformanceMode?>(csvRecords[0].size() - modesFileKeyColumns) { null }.apply {
			(csvRecords[0].size() - modesFileKeyColumns - 1 downTo 0).forEach { modeIndex ->
				val columnIndex = modeIndex + modesFileKeyColumns
				this[modeIndex] = PerformanceMode(
					modeIndex,
					csvRecords[keyToRow.getInt("name/internal")][columnIndex].trim(),
					csvRecords[keyToRow.getInt("name/display")][columnIndex].trim(),
					csvRecords[keyToRow.getInt("mspt-to-decrease-modes/1")][columnIndex].trim().let {
						if (it.isBlank()) this[modeIndex + 1]!!.msptToDecreaseMode else it.toDouble()
					},
					*performanceSettings.map { setting ->
						val row = keyToRow.getInt(setting.key)
						if (row == -1) severe("No row found for setting key '${setting.key}'")
						csvRecords[row][columnIndex].let { valueString ->
							if (valueString.isBlank())
								setting.withValueUnsafe(this[modeIndex + 1]!!.getSettingValue(setting))
							else
								setting.withParsedValue(valueString.trim())
						}
					}.toTypedArray()
				)
			}
		}.filterNotNull().toTypedArray()

		// Initialize currentMode
		currentMode = PerformanceMode.highest

	}

	private fun shouldGoDownAMode(): Boolean {

		// Can't go down if we are at the lowest mode
		if (currentMode == PerformanceMode.lowest) return false

		// Don't go down if the last time we went up or down is too recent
		if (lastModeChangeTimeMillis > System.currentTimeMillis() - minimumDelayBeforeModeDecrease.millis)
			return false

		// Go down if the MSPT is too high
		return getMSPTToUse() >= currentMode.msptToDecreaseMode

	}

	private fun shouldGoUpAMode(): Boolean {

		// Can't go up if we are at the highest mode
		if (currentMode == PerformanceMode.highest) return false

		// Don't go up if the last time we went up or down is too recent
		if (lastModeChangeTimeMillis > System.currentTimeMillis() - minimumDelayBeforeModeIncrease.millis)
			return false

		// Go up if the MSPT is low enough
		return getMSPTToUse() < currentMode.getHigherMode().msptToDecreaseMode

	}

	fun forceApplyCurrentModeSettingValues() = currentMode.applyAllSettingValues()

	private fun setMode(newMode: PerformanceMode) {
		if (newMode == currentMode) return
		lastModeChangeTimeMillis = System.currentTimeMillis()
		val oldMode = currentMode
		currentMode = newMode
		currentMode.applyDifferingSettingValues(oldMode)
		// Log to console
		info(
			"The performance mode has been ${if (currentMode > oldMode) "decreased" else "increased"} " +
					"to '${currentMode.internalName}' with current MSPT ${getMSPTToUse()}"
		)
		if (printSettingsAtModeChange) {
			info("The settings are now:")
			performanceSettings.runEach { info("* $key = ${get()}") }
		}
	}

	private fun goDownAMode() = setMode(currentMode.getLowerMode())

	private fun goUpAMode() = setMode(currentMode.getHigherMode())

}