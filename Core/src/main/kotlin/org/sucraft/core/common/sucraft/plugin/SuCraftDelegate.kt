/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.plugin

import org.sucraft.core.common.bukkit.log.NestedLogger
import org.sucraft.core.common.general.log.AbstractLogger


interface SuCraftDelegate<P : SuCraftPlugin> {

	fun getPlugin(): P

	fun getLogger(): AbstractLogger

	fun getDelegateInterfaceName(): String

	fun getDelegateImplementationName(): String = this::class.simpleName ?: "<anonymous implementation class>"

}