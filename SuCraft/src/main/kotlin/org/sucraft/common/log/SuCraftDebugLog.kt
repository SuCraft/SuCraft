/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.log

import org.sucraft.main.SuCraftPlugin

fun debugInfo(message: Any?) =
	SuCraftPlugin.instance.logger.info("TEMP DEBUG - $message")