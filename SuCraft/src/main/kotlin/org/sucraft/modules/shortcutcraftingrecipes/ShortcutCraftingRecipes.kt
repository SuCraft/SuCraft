/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.shortcutcraftingrecipes

import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.module.SuCraftModule

/**
 * Adds a number of crafting recipes that simply combine two or more sequential crafting recipes into one.
 */
object ShortcutCraftingRecipes : SuCraftModule<ShortcutCraftingRecipes>() {

	// Permissions

	object Permissions {

		val chestsFromLogs = permission(
			"chests_from_logs",
			"Craft chests from logs",
			PermissionDefault.TRUE
		)
		val dispenserFromDropperAndBow = permission(
			"dispenser_from_dropper_and_bow",
			"Craft a dispenser from a dropper and a bow",
			// TODO Currently false because of potential problem: See DispenserFromDopperAndBowCraftingRecipe
			PermissionDefault.FALSE
		)
		val dispenserFromDropperAndUncraftedBow = permission(
			"dispenser_from_dropper_and_uncrafted_bow",
			"Craft a dispenser from a dropper and an uncrafted bow",
			PermissionDefault.TRUE
		)
		val repeaterFromUncraftedTorches = permission(
			"repeater_from_uncrafted_torches",
			"Craft a redstone repeater with uncrafted redstone torches",
			PermissionDefault.TRUE
		)
		val chestMinecartFromChestAndIron = permission(
			"chest_minecart_from_chest_and_iron",
			"Craft a minecart with chest from a chest and an uncrafted minecart",
			PermissionDefault.TRUE
		)
		val furnaceMinecartFromFurnaceAndIron = permission(
			"furnace_minecart_from_furnace_and_iron",
			"Craft a minecart with furnace from a furnace and an uncrafted minecart",
			PermissionDefault.TRUE
		)
		val hopperMinecartFromHopperAndIron = permission(
			"hopper_minecart_from_hopper_and_iron",
			"Craft a minecart with hopper from a hopper and an uncrafted minecart",
			PermissionDefault.TRUE
		)
		val tntMinecartFromTNTAndIron = permission(
			"tnt_minecart_from_tnt_and_iron",
			"Craft a minecart with TNT from TNT and an uncrafted minecart",
			PermissionDefault.OP
		)

	}

	// Recipes

	override val customRecipes by lazy {
		listOf(
			chestsFromLogsCraftingRecipe,
			dispenserFromDropperAndBowCraftingRecipe,
			dispenserFromDropperAndUncraftedBowCraftingRecipe,
			repeaterFromUncraftedTorchesCraftingRecipe,
			chestMinecartFromChestAndIronCraftingRecipe,
			furnaceMinecartFromFurnaceAndIronCraftingRecipe,
			hopperMinecartFromHopperAndIronCraftingRecipe,
			tntMinecartFromTNTAndIronCraftingRecipe
		)
	}

}