/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.text

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent.openUrl
import java.net.URL

fun Component.clickOpenURL(url: String) =
	clickEvent(openUrl(url))

fun Component.clickOpenURL(url: URL) =
	clickEvent(openUrl(url))