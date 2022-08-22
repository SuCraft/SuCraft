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
	isSkippableForPlayersThatCannotAcceptLargePackets: Boolean,
	getResult: () -> ItemStack,
	setIngredients: ShapedRecipe.() -> Unit
) : CustomRecipe<ShapedRecipe>(
	::ShapedRecipe,
	getNamespacedKey,
	isSkippableForPlayersThatCannotAcceptLargePackets,
	getResult,
	setIngredients
) {

	constructor(
		key: String,
		isSkippableForPlayersThatCannotAcceptLargePackets: Boolean,
		getResult: () -> ItemStack,
		setIngredients: ShapedRecipe.() -> Unit
	) : this(
		key::toSuCraftNamespacedKey,
		isSkippableForPlayersThatCannotAcceptLargePackets,
		getResult,
		setIngredients
	)

	constructor(
		getNamespacedKey: () -> NamespacedKey,
		isSkippableForPlayersThatCannotAcceptLargePackets: Boolean,
		result: ItemStack,
		setIngredients: ShapedRecipe.() -> Unit
	) : this(
		getNamespacedKey,
		isSkippableForPlayersThatCannotAcceptLargePackets,
		{ result },
		setIngredients
	)

	constructor(
		key: String,
		isSkippableForPlayersThatCannotAcceptLargePackets: Boolean,
		result: ItemStack,
		setIngredients: ShapedRecipe.() -> Unit
	) : this(
		key::toSuCraftNamespacedKey,
		isSkippableForPlayersThatCannotAcceptLargePackets,
		{ result },
		setIngredients
	)

}