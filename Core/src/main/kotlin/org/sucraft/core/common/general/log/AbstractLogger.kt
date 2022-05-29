/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.general.log

interface AbstractLogger {

	fun info(message: Any?)

	fun warning(message: Any?)

	fun severe(message: Any?)

}