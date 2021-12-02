/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.raiddifficulty.raid

import org.bukkit.Bukkit
import org.bukkit.Difficulty
import org.bukkit.World
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.raiddifficulty.main.SuCraftRaidDifficultyPlugin


object TemporaryWorldDifficulty : SuCraftComponent<SuCraftRaidDifficultyPlugin>(SuCraftRaidDifficultyPlugin.getInstance()) {

	// Settings

	const val resetOriginalDifficultyTimeoutInTicks = 2L

	// Functionality

	fun setToHardTemporarily(world: World) {
		val originalDifficulty = world.difficulty
		if (originalDifficulty == Difficulty.HARD) return
		Bukkit.getScheduler().runTaskLater(plugin, Runnable { world.difficulty = originalDifficulty }, resetOriginalDifficultyTimeoutInTicks)
		world.difficulty = Difficulty.HARD
	}

}