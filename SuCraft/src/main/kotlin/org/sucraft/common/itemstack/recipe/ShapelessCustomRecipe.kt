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
	isSkippableForPlayersThatCannotAcceptLargePackets: Boolean,
	getResult: () -> ItemStack,
	setIngredients: ShapelessRecipe.() -> Unit
) : CustomRecipe<ShapelessRecipe>(
	::ShapelessRecipe,
	getNamespacedKey,
	isSkippableForPlayersThatCannotAcceptLargePackets,
	getResult,
	setIngredients
) {

	constructor(
		key: String,
		isSkippableForPlayersThatCannotAcceptLargePackets: Boolean,
		getResult: () -> ItemStack,
		setIngredients: ShapelessRecipe.() -> Unit
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
		setIngredients: ShapelessRecipe.() -> Unit
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
		setIngredients: ShapelessRecipe.() -> Unit
	) : this(
		key::toSuCraftNamespacedKey,
		isSkippableForPlayersThatCannotAcceptLargePackets,
		{ result },
		setIngredients
	)

}