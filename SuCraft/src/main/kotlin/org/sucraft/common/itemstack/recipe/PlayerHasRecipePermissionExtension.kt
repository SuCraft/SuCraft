/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack.recipe

import org.bukkit.entity.Player

fun Player.hasPermissionToCraftRecipe(recipe: CustomRecipe<*>) =
	recipe.permissions.all(::hasPermission)