/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.uuidcommand.player.permission

import org.sucraft.core.common.sucraft.player.permission.SuCraftPermissions


object SuCraftUUIDCommandPermissions : SuCraftPermissions("uuidcommand") {

	val CHECK_UUID = createPermission("uuid")

}