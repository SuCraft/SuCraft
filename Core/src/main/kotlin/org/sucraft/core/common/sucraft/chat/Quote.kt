/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.core.common.sucraft.chat

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.format.NamedTextColor


/**
 * A class to contain the quotes used by SuCraft-HomeQuotes and SuCraft-DeathQuotes
 */
data class Quote(
	val text: String,
	val author: String? = null
) {

	fun getComponent(): Component =
		if (author == null)
			Component.text(text).color(NamedTextColor.GRAY)
		else
			Component.join(
				JoinConfiguration.noSeparators(),
				Component.text("\"$text\"").color(NamedTextColor.GRAY),
				Component.text(" ~").color(NamedTextColor.DARK_GRAY),
				Component.text(author).color(NamedTextColor.WHITE)
			)

}