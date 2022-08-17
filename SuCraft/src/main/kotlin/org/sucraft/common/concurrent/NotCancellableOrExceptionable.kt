/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.concurrent

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import org.sucraft.common.log.AbstractLogger
import org.sucraft.main.SuCraftPlugin

suspend fun ignoreCancelOrExceptions(
	exceptionLogger: AbstractLogger? = SuCraftPlugin.instance.logger,
	block: suspend CoroutineScope.() -> Unit
) =
	withContext(NonCancellable) {
		try {
			block()
		} catch (e: Throwable) {
			// We don't want the exception to propagate
			// Log the exception if needed
			exceptionLogger?.warning("An exception occurred in a coroutine context that ignores exceptions: $e")
		}
	}