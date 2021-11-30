/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.general.pattern

open class SingletonContainer<T> {

	private var instance: T? = null

	fun setInstance(instance: T) {
		if (isInitialized())
			throw java.lang.IllegalStateException("Singleton has already been initialized")
		this.instance = instance
	}

	fun setInstanceUntyped(instance: Any) {
		@Suppress("UNCHECKED_CAST")
		setInstance( instance as T? ?: throw IllegalArgumentException())
	}

	fun isInitialized() = instance != null

	fun getInstance() = instance ?: throw IllegalStateException("Singleton has not been initialized yet")

}