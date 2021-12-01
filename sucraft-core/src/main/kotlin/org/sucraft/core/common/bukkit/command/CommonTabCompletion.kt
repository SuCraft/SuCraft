/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.command

import org.sucraft.core.common.sucraft.command.TabCompleter
import java.util.*

object CommonTabCompletion {

	val EMPTY: TabCompleter = { _, _, _, _ -> Collections.emptyList() }

}