/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack.recipe

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.sucraft.common.namespacedkey.toSuCraftNamespacedKey

open class ShapedCustomRecipe(
	getNamespacedKey: () -> NamespacedKey,
	getResult: () -> ItemStack,
	setIngredients: ShapedRecipe.() -> Unit
) : CustomRecipe<ShapedRecipe>(
	::ShapedRecipe,
	getNamespacedKey,
	getResult,
	setIngredients
) {

	constructor(
		key: String,
		getResult: () -> ItemStack,
		setIngredients: ShapedRecipe.() -> Unit
	) : this(
		key::toSuCraftNamespacedKey,
		getResult,
		setIngredients
	)

	constructor(
		getNamespacedKey: () -> NamespacedKey,
		result: ItemStack,
		setIngredients: ShapedRecipe.() -> Unit
	) : this(
		getNamespacedKey,
		{ result },
		setIngredients
	)

	constructor(
		key: String,
		result: ItemStack,
		setIngredients: ShapedRecipe.() -> Unit
	) : this(
		key::toSuCraftNamespacedKey,
		{ result },
		setIngredients
	)

}