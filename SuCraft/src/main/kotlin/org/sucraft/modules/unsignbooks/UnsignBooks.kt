/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.unsignbooks

import net.kyori.adventure.text.Component.text
import org.bukkit.Material.*
import org.bukkit.inventory.ItemStack
import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.itemstack.meta.book.bookTitle
import org.sucraft.common.itemstack.meta.bookMeta
import org.sucraft.common.itemstack.typeAndNBT
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.permission.hasPermission
import org.sucraft.common.text.*
import org.sucraft.modules.unsignbooks.UnsignBooks.Permissions.unsignOwn

/**
 * Adds a command to unsign signed books.
 */
object UnsignBooks : SuCraftModule<UnsignBooks>() {

	// Settings

	val ItemStack.notAWrittenBookMessage
		get() = when (type) {
			WRITABLE_BOOK ->
				"This book is not signed."
			BOOK ->
				"This book has no text."
			ENCHANTED_BOOK ->
				"A magical force rests on the words in this book."
			KNOWLEDGE_BOOK ->
				"Removing information from this book would violate the no-hiding theorem in quantum physics."
			else ->
				"You are not holding a written book."
		}

	// Permissions

	object Permissions {

		val unsignOwn = permission(
			"unsign.own",
			"Unsign own books authored by yourself",
			PermissionDefault.TRUE
		)
		val unsignOther = permission(
			"unsign.other",
			"Unsign books authored by other players",
			PermissionDefault.OP
		)

	}

	// Commands

	object Commands {

		val unsign = command(
			"unsign",
			"Unsign a book",
			unsignOwn,
		) {
			executesPlayerSync executes@{

				// Make sure the player is holding a written book
				val itemStack = inventory.itemInMainHand
				if (itemStack.type != WRITTEN_BOOK) {
					sendMessage(
						text(itemStack.notAWrittenBookMessage, ERROR_NOTHING_HAPPENED)
					)
					return@executes
				}

				// Make sure the player is holding a valid written book
				if (!itemStack.hasItemMeta()) {
					sendMessage(446372879678119483L, ERROR_NOTHING_HAPPENED) {
						"This book is missing data."
					}
					return@executes
				}
				if (itemStack.bookMeta?.hasAuthor() != true) {
					sendMessage(889675987334235940L, ERROR_NOTHING_HAPPENED) {
						"This book is missing data."
					}
					return@executes
				}

				// Make sure the player has permission to unsign the book
				val hasPermissionToUnsign =
					itemStack.bookMeta!!.author.equals(name, ignoreCase = true) ||
							hasPermission(Permissions.unsignOther)
				if (!hasPermissionToUnsign) {
					sendMessage(978688766334950591L, ERROR_NOTHING_HAPPENED) {
						"You can only unsign books signed by yourself."
					}
					return@executes
				}

				// Unsign the book
				val newItemStack = ItemStack(WRITABLE_BOOK).apply {
					itemMeta = itemStack.bookMeta!!.clone()
				}
				info("$name unsigned a book ${
					itemStack.bookTitle?.let { "titled '$it'" } ?: "without a title"
				}: ${itemStack.typeAndNBT}")
				inventory.setItemInMainHand(newItemStack)
				itemStack.bookTitle?.let {
					sendMessage(
						948394009283748999L, BORING_SUCCESS,
						it
					) {
						+"Unsigned the book" + BORING_SUCCESS_FOCUS * variable - "."
					}
				} ?: sendMessage(493004890583771181L, BORING_SUCCESS) {
					"Unsigned the untitled book."
				}

			}
		}

	}

}