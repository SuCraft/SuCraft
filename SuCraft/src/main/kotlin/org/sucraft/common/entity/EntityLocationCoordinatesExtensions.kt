/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.entity

import org.bukkit.entity.Entity

val Entity.x
	get() = location.x

val Entity.y
	get() = location.y

val Entity.z
	get() = location.z