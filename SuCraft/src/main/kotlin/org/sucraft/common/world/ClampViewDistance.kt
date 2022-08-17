/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.world

import kotlin.math.max
import kotlin.math.min

fun clampViewDistanceToAllowedPaperValues(viewDistance: Int) =
	min(32, max(2, viewDistance))