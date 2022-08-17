/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack.meta.book

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BookMeta
import org.sucraft.common.itemstack.meta.runMeta
import org.sucraft.common.itemstack.meta.withMeta

val ItemStack.hasBookAuthor
	/**
	 * @throws IllegalArgumentException If the meta is not an instance of [BookMeta].
	 */
	@Throws(IllegalArgumentException::class)
	get() = runMeta(BookMeta::class) { hasAuthor() }

var ItemStack.bookAuthor
	/**
	 * @throws IllegalArgumentException If the meta is not an instance of [BookMeta].
	 */
	@Throws(IllegalArgumentException::class)
	get() = runMeta(BookMeta::class) { if (hasAuthor()) author() else null }
	/**
	 * @throws IllegalArgumentException If the meta is not an instance of [BookMeta].
	 */
	@Throws(IllegalArgumentException::class)
	set(value) {
		withMeta(BookMeta::class) { author(value) }
	}

val ItemStack.hasBookTitle
	/**
	 * @throws IllegalArgumentException If the meta is not an instance of [BookMeta].
	 */
	@Throws(IllegalArgumentException::class)
	get() = runMeta(BookMeta::class) { hasTitle() }

var ItemStack.bookTitle
	/**
	 * @throws IllegalArgumentException If the meta is not an instance of [BookMeta].
	 */
	@Throws(IllegalArgumentException::class)
	get() = runMeta(BookMeta::class) { if (hasTitle()) title() else null }
	/**
	 * @throws IllegalArgumentException If the meta is not an instance of [BookMeta].
	 */
	@Throws(IllegalArgumentException::class)
	set(value) {
		withMeta(BookMeta::class) { title(value) }
	}