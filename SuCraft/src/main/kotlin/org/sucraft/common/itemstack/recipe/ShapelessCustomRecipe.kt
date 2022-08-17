/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack.recipe

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapelessRecipe
import org.sucraft.common.namespacedkey.toSuCraftNamespacedKey

open class ShapelessCustomRecipe(
	getNamespacedKey: () -> NamespacedKey,
	getResult: () -> ItemStack,
	setIngredients: ShapelessRecipe.() -> Unit
) : CustomRecipe<ShapelessRecipe>(
	::ShapelessRecipe,
	getNamespacedKey,
	getResult,
	setIngredients
) {

	constructor(
		key: String,
		getResult: () -> ItemStack,
		setIngredients: ShapelessRecipe.() -> Unit
	) : this(
		key::toSuCraftNamespacedKey,
		getResult,
		setIngredients
	)

	constructor(
		getNamespacedKey: () -> NamespacedKey,
		result: ItemStack,
		setIngredients: ShapelessRecipe.() -> Unit
	) : this(
		getNamespacedKey,
		{ result },
		setIngredients
	)

	constructor(
		key: String,
		result: ItemStack,
		setIngredients: ShapelessRecipe.() -> Unit
	) : this(
		key::toSuCraftNamespacedKey,
		{ result },
		setIngredients
	)

}