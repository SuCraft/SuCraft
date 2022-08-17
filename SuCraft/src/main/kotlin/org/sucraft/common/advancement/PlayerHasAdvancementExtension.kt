/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.advancement

import org.bukkit.advancement.Advancement
import org.bukkit.entity.Player

fun Player.hasCompletedAdvancement(advancement: Advancement) =
	getAdvancementProgress(advancement).isDone