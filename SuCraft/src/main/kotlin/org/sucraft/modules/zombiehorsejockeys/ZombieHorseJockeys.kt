/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.zombiehorsejockeys

import org.sucraft.common.module.SuCraftModule
import org.sucraft.modules.customjockeys.CustomJockeys

/**
 * Zombie horses can spawn as part of a jockey.
 */
object ZombieHorseJockeys : SuCraftModule<ZombieHorseJockeys>() {

	// Dependencies

	override val dependencies = listOf(
		CustomJockeys
	)

	// Components

	override val components = listOf(
		SpawnZombieHorseJockeys,
		JoinZombieHorseAndRiderDeath
	)

}