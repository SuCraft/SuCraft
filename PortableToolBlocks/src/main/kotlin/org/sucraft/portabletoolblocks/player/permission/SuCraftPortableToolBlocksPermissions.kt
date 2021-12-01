/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.portabletoolblocks.player.permission

import org.sucraft.core.common.sucraft.player.permission.SuCraftPermissions


object SuCraftPortableToolBlocksPermissions : SuCraftPermissions("portabletoolblocks") {

	val CRAFTING_TABLE = createPermission("craftingtable")
	val ENDER_CHEST = createPermission("enderchest")
	val ENDER_CHEST_OF_OTHER_PLAYERS = createPermission("enderchest.other")

}