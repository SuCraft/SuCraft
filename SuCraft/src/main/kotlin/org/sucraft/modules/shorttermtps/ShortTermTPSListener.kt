/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.shorttermtps

/**
 * A listener that can be registered with [ShortTermTPS] to be informed
 * every time the measured TPS is updated.
 */
fun interface ShortTermTPSListener {

	fun measuredTPSWasUpdated()

}