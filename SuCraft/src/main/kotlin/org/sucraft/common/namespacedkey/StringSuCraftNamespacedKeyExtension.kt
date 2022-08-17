/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.namespacedkey

import org.bukkit.NamespacedKey

const val suCraftNamespace = "sucraft"

fun String.toSuCraftNamespacedKey() = NamespacedKey(suCraftNamespace, this)