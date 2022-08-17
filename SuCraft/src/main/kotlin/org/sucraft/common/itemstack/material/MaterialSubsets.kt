/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack.material

import org.bukkit.Material

val itemMaterials = Material.values().filter { it.isItem }.toTypedArray()

val blockMaterials = Material.values().filter { it.isBlock }.toTypedArray()