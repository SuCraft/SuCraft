/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.plugin

@Suppress("MemberVisibilityCanBePrivate")
open class SuCraftDelegateHolder<T: SuCraftDelegate<*>> {

	private var implementation: T? = null

	fun registerImplementation(implementation: T) {
		if (isImplementationRegistered())
			throw java.lang.IllegalStateException("Implementation has already been registered")
		implementation.getDelegateLogger().info("Registered delegate implementation ${implementation.getDelegateImplementationName()} for ${implementation.getDelegateInterfaceName()}")
		this.implementation = implementation
	}

	fun isImplementationRegistered() = implementation != null

	fun get() = implementation ?: throw IllegalStateException("No implementation has been registered yet")

}