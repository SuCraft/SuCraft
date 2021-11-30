/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.player.permission

abstract class SuCraftPermissions(val pluginNamespace: String) {

	protected fun createPermission(key: String) = "sucraft.${pluginNamespace}.${key}"

}