/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.axolotlcolorinlore.listener

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.entity.Axolotl.Variant.*
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.inventory.meta.AxolotlBucketMeta
import org.sucraft.axolotlcolorinlore.main.SuCraftAxolotlColorInLorePlugin
import org.sucraft.bukkitapiextension.event.playerscoopentity.PlayerScoopEntityEvent
import org.sucraft.core.common.bukkit.item.GuaranteedItemMetaGetter
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent


object ScoopAxolotlListener : SuCraftComponent<SuCraftAxolotlColorInLorePlugin>(SuCraftAxolotlColorInLorePlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	fun onCustomPlayerScoopEntity(event: PlayerScoopEntityEvent) {
		if (event.entity.type != EntityType.AXOLOTL) return
		if (event.resultingItem.type != Material.AXOLOTL_BUCKET) return
		for ((slot, itemStack) in event.player.inventory.withIndex()) {
			if (!event.resultingItem.isSimilar(itemStack)) continue
			val itemMeta = GuaranteedItemMetaGetter.get(itemStack) as? AxolotlBucketMeta ?: continue
			val (variantName, color, customModelData) = when (if (itemMeta.hasVariant()) itemMeta.variant else null) {
				BLUE -> Triple("Blue", TextColor.color(179, 183, 252), 4)
				CYAN -> Triple("Cyan", TextColor.color(239, 247, 255), 3)
				GOLD -> Triple("Gold", TextColor.color(254, 214, 29), 2)
				WILD -> Triple("Brown", TextColor.color(139, 105, 77), 1)
				LUCY -> Triple("Pink", TextColor.color(255, 199, 234), null)
				else -> Triple("Unknown variant", NamedTextColor.GRAY, null)
			}
			val lore = itemMeta.lore() ?: ArrayList(1)
			lore.add(0, Component.text(variantName).color(color).decoration(TextDecoration.ITALIC, false))
			itemMeta.lore(lore)
			if (customModelData != null) itemMeta.setCustomModelData(customModelData)
			itemStack.itemMeta = itemMeta
			event.player.inventory.setItem(slot, itemStack)
			return
		}
	}

}