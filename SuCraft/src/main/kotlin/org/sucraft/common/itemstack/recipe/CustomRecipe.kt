/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack.recipe

import org.bukkit.*
import org.bukkit.advancement.Advancement
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.sucraft.common.advancement.hasCompletedAdvancement
import org.sucraft.common.function.forEachNotNull
import org.sucraft.common.function.runEach
import org.sucraft.common.namespacedkey.toSuCraftNamespacedKey
import org.sucraft.common.permission.SuCraftPermission
import org.sucraft.main.SuCraftPlugin

/**
 * A utility class for easily registering custom recipes.
 */
open class CustomRecipe<R : Recipe>(
	private val constructRecipe: (NamespacedKey, ItemStack) -> R,
	getNamespacedKey: () -> NamespacedKey,
	private val isSkippableForPlayersThatCannotAcceptLargePackets: Boolean,
	getResult: () -> ItemStack,
	private val setIngredients: R.() -> Unit
) {

	constructor(
		constructRecipe: (NamespacedKey, ItemStack) -> R,
		key: String,
		isSkippableForPlayersThatCannotAcceptLargePackets: Boolean,
		getResult: () -> ItemStack,
		setIngredients: R.() -> Unit
	) : this(
		constructRecipe,
		key::toSuCraftNamespacedKey,
		isSkippableForPlayersThatCannotAcceptLargePackets,
		getResult,
		setIngredients
	)

	constructor(
		constructRecipe: (NamespacedKey, ItemStack) -> R,
		getNamespacedKey: () -> NamespacedKey,
		isSkippableForPlayersThatCannotAcceptLargePackets: Boolean,
		result: ItemStack,
		setIngredients: R.() -> Unit
	) : this(
		constructRecipe,
		getNamespacedKey,
		isSkippableForPlayersThatCannotAcceptLargePackets,
		{ result },
		setIngredients
	)

	constructor(
		constructRecipe: (NamespacedKey, ItemStack) -> R,
		key: String,
		isSkippableForPlayersThatCannotAcceptLargePackets: Boolean,
		result: ItemStack,
		setIngredients: R.() -> Unit
	) : this(
		constructRecipe,
		key::toSuCraftNamespacedKey,
		isSkippableForPlayersThatCannotAcceptLargePackets,
		{ result },
		setIngredients
	)

	private var isRegistered = false
	private lateinit var bukkitRecipe: Recipe

	val namespacedKey by lazy(getNamespacedKey)
	val result by lazy(getResult)

	fun register() {
		if (isRegistered) return
		bukkitRecipe = constructRecipe(namespacedKey, result).apply(setIngredients)
		Bukkit.addRecipe(bukkitRecipe, isSkippableForPlayersThatCannotAcceptLargePackets)
		// If Permissions are needed for recipe, register an event listener to check for them
		if (permissions.isNotEmpty())
			Bukkit.getPluginManager().registerEvents(
				object : Listener {

					// Listen for crafting events to cancel crafting by players
					// who don't have permission to use this recipe
					@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
					fun onPrepareItemCraft(event: PrepareItemCraftEvent) {
						val player = event.view.player as? Player ?: return
						// Make sure the player is crafting this recipe
						if (event.recipe?.equalsRecipe(bukkitRecipe) != true) return
						// If the player lacks a permission,
						// set the inventory result to null, which effectively cancels the event
						if (!player.hasPermissionToCraftRecipe(this@CustomRecipe))
							event.inventory.result = null
					}

					// Listen for players joining to remove this recipe from their recipe book
					// if they don't have permission to use it
					@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
					fun onPlayerJoin(event: PlayerJoinEvent) {
						with(event.player) {
							if (!hasPermissionToCraftRecipe(this@CustomRecipe))
								if (hasDiscoveredRecipe(namespacedKey))
									undiscoverRecipe(namespacedKey)
						}
					}

				},
				SuCraftPlugin.instance
			)
		// If there exist conditions to discover, register an event listener to check for them
		if (conditionsToDiscover.isNotEmpty())
			Bukkit.getPluginManager().registerEvents(
				object : Listener {

					// Check the condition when the player joins
					@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
					fun onPlayerJoin(event: PlayerJoinEvent) {
						with(event.player) {
							if (hasPermissionToCraftRecipe(this@CustomRecipe))
								if (!hasDiscoveredRecipe(namespacedKey))
									if (conditionsToDiscover.any { it() })
										discoverRecipe(namespacedKey)
						}
					}

				},
				SuCraftPlugin.instance
			)
		// If there exist item stack conditions or materials to discover, register an event listener to check for them
		if (
			(itemStackConditionsToDiscover.isNotEmpty()) ||
			(materialsToDiscover.isNotEmpty())
		)
			Bukkit.getPluginManager().registerEvents(
				object : Listener {

					private fun Player.discoverIfItemStackFulfillsCondition(itemStack: ItemStack) {
						if (
							itemStackConditionsToDiscover.any { it(itemStack) } ||
							itemStack.type in materialsToDiscover
						)
							discoverRecipe(namespacedKey)
					}

					// Check when the player joins
					@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
					fun onPlayerJoin(event: PlayerJoinEvent) {
						with(event.player) {
							if (hasPermissionToCraftRecipe(this@CustomRecipe))
								if (!hasDiscoveredRecipe(namespacedKey))
									inventory.contents.forEachNotNull { discoverIfItemStackFulfillsCondition(it) }
						}
					}

					// Check when the player picks up an item
					@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
					fun onEntityPickupItem(event: EntityPickupItemEvent) {
						(event.entity as? Player)?.run {
							if (hasPermissionToCraftRecipe(this@CustomRecipe))
								if (!hasDiscoveredRecipe(namespacedKey))
									discoverIfItemStackFulfillsCondition(event.item.itemStack)
						}
					}

				},
				SuCraftPlugin.instance
			)
		// If there exist advancements to discover, register an event listener to check for them
		if (advancementsToDiscover.isNotEmpty())
			Bukkit.getPluginManager().registerEvents(
				object : Listener {

					// Check the condition when the player joins
					@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
					fun onPlayerJoin(event: PlayerJoinEvent) {
						with(event.player) {
							if (hasPermissionToCraftRecipe(this@CustomRecipe))
								if (!hasDiscoveredRecipe(namespacedKey))
									if (advancementsToDiscover.any(::hasCompletedAdvancement))
										discoverRecipe(namespacedKey)
						}
					}

					// Check the condition when the player completes an advancement
					@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
					fun onPlayerAdvancementDone(event: PlayerAdvancementDoneEvent) {
						with(event.player) {
							if (hasPermissionToCraftRecipe(this@CustomRecipe))
								if (!hasDiscoveredRecipe(namespacedKey))
									if (event.advancement in advancementsToDiscover)
										discoverRecipe(namespacedKey)
						}
					}

				},
				SuCraftPlugin.instance
			)
		isRegistered = true
	}

	val permissions: MutableList<String> = ArrayList(0)

	/**
	 * @param permission A [permission][SuCraftPermission] that will be needed to use this recipe.
	 */
	fun addPermission(permission: SuCraftPermission) =
		addPermission(permission.key)

	/**
	 * @param permission A [permission][SuCraftPermission] that will be needed to use this recipe.
	 */
	fun addPermission(permission: String) {
		permissions.add(permission)
	}

	val conditionsToDiscover: MutableList<Player.() -> Boolean> = ArrayList(0)
	val itemStackConditionsToDiscover: MutableList<ItemStack.() -> Boolean> = ArrayList(0)
	val materialsToDiscover: MutableList<Material> = ArrayList(0)
	val advancementsToDiscover: MutableList<Advancement> = ArrayList(0)

	/**
	 * @param condition A condition on a [Player], that if they meet,
	 * this recipe will be [added to their recipe book][Player.discoverRecipe].
	 *
	 * This condition is checked at least when they [join][PlayerJoinEvent].
	 */
	fun addConditionToDiscover(condition: Player.() -> Boolean) {
		conditionsToDiscover.add(condition)
	}

	/**
	 * @param condition A condition on an [ItemStack], so that when a [player][Player] has it,
	 * this recipe will be [added to their recipe book][Player.discoverRecipe].
	 *
	 * This condition is checked at least when they [join][PlayerJoinEvent] or
	 * [pick up an item][EntityPickupItemEvent].
	 */
	fun addItemStackToDiscover(condition: ItemStack.() -> Boolean) {
		itemStackConditionsToDiscover.add(condition)
	}

	/**
	 * @param materials [Item types][Material], so that when a [player][Player] has an item with any of those types,
	 * this recipe will be [added to their recipe book][Player.discoverRecipe].
	 *
	 * This is checked at least when they [join][PlayerJoinEvent] or
	 * [pick up an item][EntityPickupItemEvent].
	 */
	fun addItemMaterialsToDiscover(vararg materials: Material) {
		materialsToDiscover.addAll(materials)
	}

	/**
	 * @see [addItemMaterialsToDiscover]
	 */
	fun addItemMaterialsToDiscover(materials: Iterable<Material>) {
		materialsToDiscover.addAll(materials)
	}

	/**
	 * @param tags Tags for [item types][Material], so that when a [player][Player] has an item with
	 * any of those types,
	 * this recipe will be [added to their recipe book][Player.discoverRecipe].
	 *
	 * @see [addItemMaterialsToDiscover]
	 */
	fun addItemMaterialsToDiscover(vararg tags: Tag<Material>) =
		tags.runEach { addItemMaterialsToDiscover(values) }

	/**
	 * @param advancement An [Advancement], so that when a [player][Player] has it,
	 * this recipe will be [added to their recipe book][Player.discoverRecipe].
	 *
	 * This condition is checked at least when they [join][PlayerJoinEvent] or
	 * [complete an advancement][PlayerAdvancementDoneEvent].
	 */
	fun addAdvancementToDiscover(advancement: Advancement) {
		advancementsToDiscover.add(advancement)
	}

}