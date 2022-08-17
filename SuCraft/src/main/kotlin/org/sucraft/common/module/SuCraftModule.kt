/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.module

import org.sucraft.common.function.runEach
import org.sucraft.common.io.inside
import org.sucraft.main.SuCraftPlugin

/**
 * A module is similar to a plugin: it is a well-bounded functionality provided to the server as a whole,
 * such as invisible item frames or making barriers and lights drop.
 *
 * Modules are also [components][AbstractSuCraftComponent] in their own right.
 */
abstract class SuCraftModule<M : SuCraftModule<M>> :
	AbstractSuCraftComponent<M>() {

	@Suppress("UNCHECKED_CAST")
	final override val module
		get() = this as M

	override val parentLogger
		get() = SuCraftPlugin.instance.logger

	protected open val components: List<SuCraftComponent<M>> = emptyList()

	override fun onInitialize() {
		@Suppress("UNCHECKED_CAST")
		components.forEach { it.initialize(this as M) }
	}

	override fun onTerminate() {
		@Suppress("UNCHECKED_CAST")
		components.runEach { terminate() }
	}

	/**
	 * A folder to store data and other files related to this module.
	 */
	val folder get() = name inside SuCraftPlugin.instance.dataFolder

}