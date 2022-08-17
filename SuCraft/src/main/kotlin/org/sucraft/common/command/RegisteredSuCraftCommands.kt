/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.command

val lowercaseRegisteredSuCraftCommandLabels: MutableSet<String> = HashSet(256)

fun isRegisteredSuCraftCommandLabel(label: String) =
	label.lowercase() in lowercaseRegisteredSuCraftCommandLabels