/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.plugin

open class SuCraftDelegateHolder<T> {

	private var implementation: T? = null

	fun registerImplementation(implementation: T) {
		if (isImplementationRegistered())
			throw java.lang.IllegalStateException("Implementation has already been registered")
		this.implementation = implementation
	}

	fun isImplementationRegistered() = implementation != null

	fun get() = implementation ?: throw IllegalStateException("No implementation has been registered yet")

}