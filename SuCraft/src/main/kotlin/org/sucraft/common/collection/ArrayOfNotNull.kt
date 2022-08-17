/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.collection

inline fun <reified T> arrayOfNotNull(vararg elements: T) =
	elements.filterNotNull().toTypedArray()