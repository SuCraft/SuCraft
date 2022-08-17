/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack.meta

import com.destroystokyo.paper.inventory.meta.ArmorStandMeta
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.*
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

/**
 * @return The existing or newly created [ItemMeta].
 */
fun ItemStack.getOrAddItemMeta(setNewItemMetaBeforeReturn: Boolean = true) =
	// Return the existing meta if present
	if (hasItemMeta() && itemMeta != null)
		itemMeta!!
	// Otherwise, make a new meta
	else
		Bukkit.getItemFactory().getItemMeta(type)!!
			.also { if (setNewItemMetaBeforeReturn) itemMeta = it }

/**
 * @return This [ItemStack].
 */
fun ItemStack.withMeta(operation: ItemMeta.() -> Unit): ItemStack {
	getOrAddItemMeta()
		.also(operation)
		.also { itemMeta = it }
	return this
}

/**
 * @return This [ItemStack].
 */
@Suppress("UNCHECKED_CAST")
fun <M : ItemMeta> ItemStack.withMeta(
	`class`: KClass<M>,
	ignoreTypeMismatch: Boolean = false,
	operation: M.() -> Unit
) =
	withMeta subWithMeta@{
		if (ignoreTypeMismatch && !`class`.isInstance(this)) return@subWithMeta
		operation(this as M)
	} as? M

/**
 * @return The result of the [function] applied to the meta.
 */
fun <T> ItemStack.runMeta(function: ItemMeta.() -> T) =
	run {
		val meta = getOrAddItemMeta()
		val returnValue = meta.run(function)
		itemMeta = meta
		returnValue
	}

/**
 * @return The result of the [function] applied to the meta.
 * @throws IllegalArgumentException If the meta is not of the given type.
 */
@Throws(IllegalArgumentException::class)
@Suppress("UNCHECKED_CAST")
fun <M : ItemMeta, T> ItemStack.runMeta(
	`class`: KClass<M>,
	function: M.() -> T
) = runMeta {
	if (!`class`.isInstance(this))
		throw IllegalArgumentException(
			"Called ItemStack.runMeta with an invalid meta type: ${`class`.jvmName} (actual meta type was: ${this.javaClass.name})"
		)
	(this as M).function()
}

/**
 * @return The result of the [function] applied to the meta,
 * or null if the meta is not of the given type.
 */
@Suppress("UNCHECKED_CAST")
fun <M : ItemMeta, T> ItemStack.runMetaOrNull(
	`class`: KClass<M>,
	function: M.() -> T
) = runMeta {
	if (!`class`.isInstance(this))
		null
	else
		(this as M).function()
}

/**
 * @return The [ItemMeta] of this [ItemStack],
 * or null if it [has none][ItemStack.hasItemMeta].
 */
val ItemStack.itemMetaOrNull
	get() = if (hasItemMeta()) itemMeta else null

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [ArmorStandMeta],
 * or null otherwise.
 */
val ItemStack.armorStandMeta
	get() = itemMetaOrNull as? ArmorStandMeta

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [AxolotlBucketMeta],
 * or null otherwise.
 */
val ItemStack.axolotlBucketMeta
	get() = itemMetaOrNull as? AxolotlBucketMeta

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [BannerMeta],
 * or null otherwise.
 */
val ItemStack.bannerMeta
	get() = itemMetaOrNull as? BannerMeta

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [BlockDataMeta],
 * or null otherwise.
 */
val ItemStack.blockDataMeta
	get() = itemMetaOrNull as? BlockDataMeta

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [BlockStateMeta],
 * or null otherwise.
 */
val ItemStack.blockStateMeta
	get() = itemMetaOrNull as? BlockStateMeta

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [BookMeta],
 * or null otherwise.
 */
val ItemStack.bookMeta
	get() = itemMetaOrNull as? BookMeta

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [BundleMeta],
 * or null otherwise.
 */
val ItemStack.bundleMeta
	get() = itemMetaOrNull as? BundleMeta

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [CompassMeta],
 * or null otherwise.
 */
val ItemStack.compassMeta
	get() = itemMetaOrNull as? CompassMeta

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [CrossbowMeta],
 * or null otherwise.
 */
val ItemStack.crossbowMeta
	get() = itemMetaOrNull as? CrossbowMeta

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [Damageable],
 * or null otherwise.
 */
val ItemStack.damageableMeta
	get() = itemMetaOrNull as? Damageable

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [EnchantmentStorageMeta],
 * or null otherwise.
 */
val ItemStack.enchantmentStorageMeta
	get() = itemMetaOrNull as? EnchantmentStorageMeta

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [FireworkEffectMeta],
 * or null otherwise.
 */
val ItemStack.fireworkEffectMeta
	get() = itemMetaOrNull as? FireworkEffectMeta

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [FireworkMeta],
 * or null otherwise.
 */
val ItemStack.fireworkMeta
	get() = itemMetaOrNull as? FireworkMeta

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [KnowledgeBookMeta],
 * or null otherwise.
 */
val ItemStack.knowledgeBookMeta
	get() = itemMetaOrNull as? KnowledgeBookMeta

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [LeatherArmorMeta],
 * or null otherwise.
 */
val ItemStack.leatherArmorMeta
	get() = itemMetaOrNull as? LeatherArmorMeta

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [MapMeta],
 * or null otherwise.
 */
val ItemStack.mapMeta
	get() = itemMetaOrNull as? MapMeta

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [PotionMeta],
 * or null otherwise.
 */
val ItemStack.potionMeta
	get() = itemMetaOrNull as? PotionMeta

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [Repairable],
 * or null otherwise.
 */
val ItemStack.repairableMeta
	get() = itemMetaOrNull as? Repairable

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [SkullMeta],
 * or null otherwise.
 */
val ItemStack.skullMeta
	get() = itemMetaOrNull as? SkullMeta

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [SpawnEggMeta],
 * or null otherwise.
 */
val ItemStack.spawnEggMeta
	get() = itemMetaOrNull as? SpawnEggMeta

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [SuspiciousStewMeta],
 * or null otherwise.
 */
val ItemStack.suspiciousStewMeta
	get() = itemMetaOrNull as? SuspiciousStewMeta

/**
 * @return The [ItemMeta] of this [ItemStack] if it is present and an instance of [TropicalFishBucketMeta],
 * or null otherwise.
 */
val ItemStack.tropicalFishBucketMeta
	get() = itemMetaOrNull as? TropicalFishBucketMeta