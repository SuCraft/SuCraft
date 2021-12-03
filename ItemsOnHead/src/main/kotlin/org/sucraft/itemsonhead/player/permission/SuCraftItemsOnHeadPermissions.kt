/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.itemsonhead.player.permission

import org.sucraft.core.common.sucraft.player.permission.SuCraftPermissions


object SuCraftItemsOnHeadPermissions : SuCraftPermissions("itemsonhead") {

	val ALL_ITEMS = createPermission("all")
	val HELMETS = createPermission("helmet")
	val BANNERS = createPermission("banner")

}