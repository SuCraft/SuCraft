/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.player.profile

import com.destroystokyo.paper.profile.PlayerProfile
import org.bukkit.Bukkit
import java.util.*

fun createPlayerProfileWithRandomUUID(): PlayerProfile {
	var uuid: UUID
	do {
		uuid = UUID.randomUUID()
	} while (Bukkit.getOfflinePlayer(uuid).hasPlayedBefore())
	return Bukkit.createProfile(uuid)
}