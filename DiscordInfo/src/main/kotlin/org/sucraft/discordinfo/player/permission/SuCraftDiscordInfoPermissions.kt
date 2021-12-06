/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.discordinfo.player.permission

import org.sucraft.core.common.sucraft.player.permission.SuCraftPermissions


object SuCraftDiscordInfoPermissions : SuCraftPermissions("discordinfo") {

	val SEE_DISCORD_URL = createPermission("discord")

}