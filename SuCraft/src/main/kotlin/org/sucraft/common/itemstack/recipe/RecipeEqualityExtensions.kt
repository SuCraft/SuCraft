/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack.recipe

import org.bukkit.Bukkit
import org.bukkit.Keyed
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.*
import org.sucraft.common.collection.equalsExceptOrder

/**
 * @return Whether this recipe equals the [given recipe][other].
 *
 * Because recipe instances may be re-constructed by the server and those re-constructed recipes do not equal their
 * original counterparts (for instance the [recipe present in a crafting event][PrepareItemCraftEvent.getRecipe]
 * may be different from the one originally [registered][Bukkit.addRecipe]),
 * we must implement our own recipe equality function.
 */
fun Recipe.equalsRecipe(other: Recipe) =
	// If they are referentially equal, they are equal
	if (this === other)
		true
	// If they have the same NamespacedKey, we assume them to be equal
	else if (this is Keyed && other is Keyed && key == other.key)
		true
	// If their result is not equal, they are not equal
	else if (result != other.result)
		false
	// Check their ingredients
	else hasSameIngredientsAsRecipe(other)

/**
 * The same as [equalsRecipe], but does not check for the amount of the result.
 */
fun Recipe.isSimilarToRecipe(other: Recipe) =
	// If they are referentially equal, they are equal and thus similar
	if (this === other)
		true
	// If they have the same NamespacedKey, we assume them to be equal and thus similar
	else if (this is Keyed && other is Keyed && key == other.key)
		true
	// If their result is not similar, they are not similar
	else if (!result.isSimilar(other.result))
		false
	// Check their ingredients
	else hasSameIngredientsAsRecipe(other)

/**
 * An auxiliary function for [equalsRecipe] that only checks the ingredients.
 */
fun Recipe.hasSameIngredientsAsRecipe(other: Recipe) =
	when (this) {
		is ShapedRecipe ->
			if (other is ShapedRecipe) {
				val thisIngredientMatrix = getIngredientMatrix()
				val otherIngredientMatrix = other.getIngredientMatrix()
				thisIngredientMatrix contentEquals otherIngredientMatrix ||
						thisIngredientMatrix.mirrorIngredientMatrix() contentEquals otherIngredientMatrix
			} else false
		is ShapelessRecipe ->
			if (other is ShapelessRecipe)
				choiceList.equalsExceptOrder(other.choiceList)
			else false
		is FurnaceRecipe ->
			if (other is FurnaceRecipe)
				inputChoice == other.inputChoice
			else false
		is SmokingRecipe ->
			if (other is SmokingRecipe)
				inputChoice == other.inputChoice
			else false
		is CampfireRecipe ->
			if (other is CampfireRecipe)
				inputChoice == other.inputChoice
			else false
		is BlastingRecipe ->
			if (other is BlastingRecipe)
				inputChoice == other.inputChoice
			else false
		is SmithingRecipe ->
			if (other is SmithingRecipe)
				base == other.base && addition == other.addition && willCopyNbt() == other.willCopyNbt()
			else false
		is StonecuttingRecipe ->
			if (other is StonecuttingRecipe)
				inputChoice == other.inputChoice
			else false
		is MerchantRecipe ->
			if (other is MerchantRecipe)
				ingredients.equalsExceptOrder(other.ingredients) // Could check for more but that seems overkill
			else false
		// This recipe is of an unknown type, so we cannot confidently declare the ingredients equal: we assume false
		else -> false
	}

private fun ShapedRecipe.getIngredientMatrix() =
	arrayOfNulls<RecipeChoice>(9).apply {
		val choiceMap = choiceMap
		var slot = 0
		shape.forEach { row ->
			row.forEach { value ->
				this[slot] = choiceMap[value]
				slot++
			}
		}
	}

private fun Array<RecipeChoice?>.mirrorIngredientMatrix() =
	Array(size) { index ->
		when (index % 3) {
			0 -> this[index / 3 + 2]
			2 -> this[index / 3]
			else -> this[index / 3 + 1]
		}
	}
