/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.log

interface AbstractLogger {

	fun info(message: Any?)

	fun warning(message: Any?)

	fun severe(message: Any?)

}