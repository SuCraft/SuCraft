/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.module

/**
 * A component takes care of a single basic task, typically a Bukkit-related task,
 * such as handling a type of event or managing a task to be scheduled.
 *
 * A component belongs to a [module][SuCraftModule].
 */
abstract class SuCraftComponent<M : SuCraftModule<M>> :
	AbstractSuCraftComponent<M>() {

	final override lateinit var module: M
		private set

	override val parentLogger
		get() = module.logger

	fun initialize(module: M) {
		this.module = module
		super.initialize()
	}

}