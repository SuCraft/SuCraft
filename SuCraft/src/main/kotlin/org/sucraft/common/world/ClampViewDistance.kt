/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.world

import kotlin.math.max
import kotlin.math.min

fun clampViewDistanceToAllowedPaperValues(viewDistance: Int) =
	viewDistance.coerceIn(2, 32)