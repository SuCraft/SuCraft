/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.pingcommand.player.permission

import org.sucraft.core.common.sucraft.player.permission.SuCraftPermissions

object SuCraftPingCommandPermissions : SuCraftPermissions("pingcommand") {

	val PING = createPermission("ping")
	val PONG = createPermission("pong")

}