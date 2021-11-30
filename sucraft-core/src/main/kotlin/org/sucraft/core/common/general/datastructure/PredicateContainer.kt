/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.general.datastructure

import java.util.function.Predicate

class PredicateContainer<T>(private val predicate: Predicate<T>) {

	operator fun contains(t: T) = predicate.test(t)

}
