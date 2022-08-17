/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack

fun ItemStack.serializeToYamlConfiguration() =
	YamlConfiguration().also {
		it.set("_", this)
	}

fun ItemStack.serializeToString() =
	serializeToYamlConfiguration().saveToString()

fun YamlConfiguration.deserializeToItemStack() =
	getItemStack("_")!!

fun String.deserializeToItemStack() =
	YamlConfiguration().let {
		it.loadFromString(this)
		it.deserializeToItemStack()
	}
