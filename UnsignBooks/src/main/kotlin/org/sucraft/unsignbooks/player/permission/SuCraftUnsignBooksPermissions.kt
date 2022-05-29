/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.unsignbooks.player.permission

import org.sucraft.core.common.sucraft.player.permission.SuCraftPermissions

object SuCraftUnsignBooksPermissions : SuCraftPermissions("unsignbooks") {

	val UNSIGN_OWN_BOOKS = createPermission("unsign.own")
	val UNSIGN_OTHER_PLAYERS_BOOKS = createPermission("unsign.other")

}