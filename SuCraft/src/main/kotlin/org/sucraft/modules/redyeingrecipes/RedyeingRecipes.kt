/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.redyeingrecipes

import org.bukkit.Material
import org.bukkit.Material.*
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.RecipeChoice
import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.itemstack.material.*
import org.sucraft.common.itemstack.recipe.ShapelessCustomRecipe
import org.sucraft.common.module.SuCraftModule

/**
 * Adds recipes to redye already colored items and blocks, and undye some as well.
 */
object RedyeingRecipes : SuCraftModule<RedyeingRecipes>() {

	// Settings

	/**
	 * If true, if a recipe allows redyeing 8 items at a time,
	 * it will also be allowed to redye 1 to 7 items at a time.
	 *
	 * However, this may be confusing because someone may believe the recipe only allows
	 * redyeing 1 at a time if that is all they tried.
	 */
	private const val allowRedyeAmountsSmallerThanMaxRecipeAmount = false

	/**
	 * If true, if a recipe allows undyeing 8 items at a time,
	 * it will also be allowed to undye 1 to 7 items at a time.
	 */
	private const val allowUndyeAmountsSmallerThanMaxRecipeAmount = false

	// Permissions

	object Permissions {

		val redyeItems = permission(
			"redye",
			"Redye blocks and items",
			PermissionDefault.TRUE
		)

		val undyeItems = permission(
			"undye",
			"Undye blocks and items",
			PermissionDefault.TRUE
		)

	}

	// Recipes

	override val customRecipes by lazy {

		fun createRedyeRecipes(
			materialCategoryName: String,
			maxRecipeAmount: Int,
			newMaterials: Iterable<Material>,
			oldMaterials: Iterable<Material>
		) =
			(if (allowRedyeAmountsSmallerThanMaxRecipeAmount) (1..maxRecipeAmount)
			else listOf(maxRecipeAmount)).flatMap { recipeAmount ->
				newMaterials.map { new ->
					val newDyeColor = new.dyeColor!!
					ShapelessCustomRecipe(
						"redye_${recipeAmount}_${materialCategoryName}_to_${newDyeColor.name.lowercase()}",
						true,
						ItemStack(new, recipeAmount)
					) {
						repeat(recipeAmount) {
							addIngredient(RecipeChoice.MaterialChoice(oldMaterials.toList()))
						}
						addIngredient(dyes.firstWithDyeColor(newDyeColor))
					}.apply {
						addPermission(Permissions.redyeItems)
						addItemMaterialsToDiscover(oldMaterials)
					}
				}
			}

		fun createUndyeRecipes(
			newMaterial: Material,
			oldMaterials: Iterable<Material>,
			maxRecipeAmount: Int = 8
		) =
			(if (allowUndyeAmountsSmallerThanMaxRecipeAmount) (1..maxRecipeAmount)
			else listOf(maxRecipeAmount)).map { recipeAmount ->
				ShapelessCustomRecipe(
					"undye_${recipeAmount}_${newMaterial.keyKey}",
					true,
					ItemStack(newMaterial, recipeAmount)
				) {
					repeat(recipeAmount) {
						addIngredient(RecipeChoice.MaterialChoice(oldMaterials.toList()))
					}
					addIngredient(RecipeChoice.MaterialChoice(WATER_BUCKET, ICE, PACKED_ICE, BLUE_ICE))
				}.apply {
					addPermission(Permissions.undyeItems)
					addItemMaterialsToDiscover(oldMaterials)
				}
			}

		createRedyeRecipes(
			"glass_pane",
			8,
			stainedGlassPanes,
			unstainedOrStainedGlassPanes
		) + createRedyeRecipes(
			"glass",
			8,
			stainedGlasses,
			unstainedOrStainedGlasses
		) + createRedyeRecipes(
			"terracotta",
			8,
			unglazedColoredTerracottaFullBlocks,
			unglazedUncoloredOrColoredTerracottaFullBlocks
		) + createRedyeRecipes(
			"terracotta_slab",
			8,
			unglazedColoredTerracottaSlabs,
			unglazedUncoloredOrColoredTerracottaSlabs
		) + createRedyeRecipes(
			"terracotta_stairs",
			8,
			unglazedColoredTerracottaStairs,
			unglazedUncoloredOrColoredTerracottaStairs
		) + createRedyeRecipes(
			"glazed_terracotta",
			8,
			glazedTerracottaFullBlocks,
			glazedTerracottaFullBlocks
		) + createRedyeRecipes(
			"wool",
			1,
			woolFullBlocks,
			woolFullBlocks
		) + createRedyeRecipes(
			"wool_slab",
			2,
			woolSlabs,
			woolSlabs
		) + createRedyeRecipes(
			"wool_stairs",
			1,
			woolStairs,
			woolStairs
		) + createRedyeRecipes(
			"carpet",
			8,
			woolCarpets,
			woolCarpets
		) + createRedyeRecipes(
			"bed",
			1,
			beds,
			beds
		) + createRedyeRecipes(
			"concrete",
			8,
			concreteFullBlocks,
			concreteFullBlocks
		) + createRedyeRecipes(
			"concrete_slab",
			8,
			concreteSlabs,
			concreteSlabs
		) + createRedyeRecipes(
			"concrete_stairs",
			8,
			concreteStairs,
			concreteStairs
		) + createRedyeRecipes(
			"concrete_powder",
			8,
			concretePowderFullBlocks,
			concretePowderFullBlocks
		) + createRedyeRecipes(
			"concrete_powder_slab",
			8,
			concretePowderSlabs,
			concretePowderSlabs
		) + createRedyeRecipes(
			"concrete_powder_stairs",
			8,
			concretePowderStairs,
			concretePowderStairs
		) + createRedyeRecipes(
			"candle",
			1,
			coloredCandles,
			uncoloredOrColoredCandles
		) + createUndyeRecipes(
			GLASS_PANE,
			stainedGlassPanes
		) + createUndyeRecipes(
			GLASS,
			stainedGlasses
		) + createUndyeRecipes(
			TERRACOTTA,
			unglazedColoredTerracottaFullBlocks
		) + createUndyeRecipes(
			WHITE_WOOL,
			woolFullBlocks
		) + createUndyeRecipes(
			SUCRAFT_WHITE_WOOL_SLAB,
			woolSlabs
		) + createUndyeRecipes(
			SUCRAFT_WHITE_WOOL_STAIRS,
			woolStairs
		) + createUndyeRecipes(
			WHITE_CARPET,
			woolCarpets
		) + createUndyeRecipes(
			WHITE_BED,
			beds
		) + createUndyeRecipes(
			CANDLE,
			coloredCandles
		) + createUndyeRecipes(
			SHULKER_BOX,
			coloredShulkerBoxes
		)

	}

}