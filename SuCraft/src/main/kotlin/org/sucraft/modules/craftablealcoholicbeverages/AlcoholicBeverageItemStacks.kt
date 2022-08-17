/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.craftablealcoholicbeverages

import net.kyori.adventure.text.format.NamedTextColor.GRAY
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.sucraft.common.itemstack.meta.addLore
import org.sucraft.common.persistentdata.getPersistentFlag
import org.sucraft.common.persistentdata.hasPersistent
import org.sucraft.common.persistentdata.setPersistentFlag
import org.sucraft.common.persistentdata.typedvalueextensions.setPersistentUUID
import org.sucraft.common.text.unstyledText

private val alcoholicBeverageKey by lazy {
	@Suppress("DEPRECATION")
	NamespacedKey("martijnsrecipes", "alcoholicbeverage")
}

private val alcoholicBeverageBrewerKey by lazy {
	@Suppress("DEPRECATION")
	NamespacedKey("martijnsrecipes", "alcoholicbeveragebrewer")
}

fun ItemStack.setBrewerLoreAndPersistentDataAfterBrewing(brewer: Player): ItemStack {
	// Do nothing if this item already appears to have brewer lore
	if (hasPersistent(alcoholicBeverageBrewerKey)) return this
	addLore(unstyledText("Brewed by ${brewer.name}", GRAY))
	setPersistentUUID(alcoholicBeverageBrewerKey, brewer.uniqueId)
	return this
}

fun ItemStack.setAlcoholicBeveragePersistentFlag() =
	setPersistentFlag(alcoholicBeverageKey)

val ItemStack.isAlcoholicBeverage
	get() = getPersistentFlag(alcoholicBeverageKey)

val ItemStack.isSimilarToAlcoholicBeverageWithoutBrewer
	get() = isSimilar(beerItemStackWithoutBrewer) ||
			isSimilar(vodkaItemStackWithoutBrewer) ||
			isSimilar(kvassItemStackWithoutBrewer)