/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance

/**
 * This value is larger than any distance between objects can be,
 * since the world is 60,000,000 blocks in each planar dimension, so
 * any distance (if we assume the same distance in the y-axis) should be less than
 * 60,000,000 * sqrt(3). On top of this limit, some extra margin has been added.
 */
const val distanceLargerThanWorldSize = 150000000.0