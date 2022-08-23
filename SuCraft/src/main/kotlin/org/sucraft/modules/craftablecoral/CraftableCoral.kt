/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.craftablecoral

import org.bukkit.Material.*
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.RecipeChoice
import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.itemstack.recipe.ShapedCustomRecipe
import org.sucraft.common.module.SuCraftModule

/**
 * Makes coral blocks craftable from coral and coral fans.
 */
object CraftableCoral : SuCraftModule<CraftableCoral>() {

	// Permissions

	object Permissions {

		val craftCoralBlocks = permission(
			"craft.coralblocks",
			"Craft coral blocks",
			PermissionDefault.TRUE
		)

	}

	// Recipes

	override val customRecipes by lazy {
		sequenceOf(
			(BRAIN_CORAL to BRAIN_CORAL_FAN) to BRAIN_CORAL_BLOCK,
			(BUBBLE_CORAL to BUBBLE_CORAL_FAN) to BUBBLE_CORAL_BLOCK,
			(FIRE_CORAL to FIRE_CORAL_FAN) to FIRE_CORAL_BLOCK,
			(HORN_CORAL to HORN_CORAL_FAN) to HORN_CORAL_BLOCK,
			(TUBE_CORAL to TUBE_CORAL_FAN) to TUBE_CORAL_BLOCK,
			(DEAD_BRAIN_CORAL to DEAD_BRAIN_CORAL_FAN) to DEAD_BRAIN_CORAL_BLOCK,
			(DEAD_BUBBLE_CORAL to DEAD_BUBBLE_CORAL_FAN) to DEAD_BUBBLE_CORAL_BLOCK,
			(DEAD_FIRE_CORAL to DEAD_FIRE_CORAL_FAN) to DEAD_FIRE_CORAL_BLOCK,
			(DEAD_HORN_CORAL to DEAD_HORN_CORAL_FAN) to DEAD_HORN_CORAL_BLOCK,
			(DEAD_TUBE_CORAL to DEAD_TUBE_CORAL_FAN) to DEAD_TUBE_CORAL_BLOCK
		).map { (corals, coralBlock) ->
			val (coral, coralFan) = corals
			ShapedCustomRecipe(
				"${coralBlock.key.key}_from_coral",
				false,
				ItemStack(coralBlock)
			) {
				shape("$$", "$$")
				setIngredient('$', RecipeChoice.MaterialChoice(coral, coralFan))
			}.apply {
				addPermission(Permissions.craftCoralBlocks)
				addItemMaterialsToDiscover(coral, coralFan)
			}
		}.toList()
	}

}