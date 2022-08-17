/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack.material

import org.bukkit.Material
import org.bukkit.NamespacedKey

val Material.keyKey
	get() = key.key

private val materialByNamespacedKey: Map<NamespacedKey, Material> = Material.values().associateBy { it.key }

// This will throw a NullPointerException during initialization
// if there are any 2 Material values that have the same key
private val materialByNamespacedKeyKey: Map<String, Material> = Material.values().associateBy { it.key.key }
	.takeIf { it.size == Material.values().size }!!

fun NamespacedKey.getMaterial() = materialByNamespacedKey[this]

fun getMaterialByKeyKey(keyKey: String) = materialByNamespacedKeyKey[keyKey]