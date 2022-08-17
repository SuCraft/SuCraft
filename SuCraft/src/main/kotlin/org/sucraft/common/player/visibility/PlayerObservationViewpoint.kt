/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.player.visibility

/**
 * A viewpoint from which players can be observed.
 *
 * This includes the [observer][PlayerObserver] and the [observation method][PlayerObservationMethod].
 */
data class PlayerObservationViewpoint(
	val observer: PlayerObserver,
	val method: PlayerObservationMethod
)