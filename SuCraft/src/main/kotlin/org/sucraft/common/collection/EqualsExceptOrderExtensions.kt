/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.collection

private fun <T> checkIfListAndListCopyElementsAreEqualExceptOrder(list1: List<T>, list2: MutableList<T>) =
	list1.all(list2::remove) && list2.isEmpty()

fun <T> List<T>.equalsExceptOrder(other: List<T>) =
	size == other.size && checkIfListAndListCopyElementsAreEqualExceptOrder(this, other.toMutableList())

fun <T> Array<T>.equalsExceptOrder(other: List<T>) =
	size == other.size && checkIfListAndListCopyElementsAreEqualExceptOrder(toList(), other.toMutableList())

fun <T> List<T>.equalsExceptOrder(other: Array<T>) =
	size == other.size && checkIfListAndListCopyElementsAreEqualExceptOrder(this, other.toMutableList())

fun <T> Array<T>.equalsExceptOrder(other: Array<T>) =
	size == other.size && checkIfListAndListCopyElementsAreEqualExceptOrder(toList(), other.toMutableList())