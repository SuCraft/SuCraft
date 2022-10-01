/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.collection

fun <T1, T2> T1.then(second: T2) = this to second

fun <T1, T2, T3> Pair<T1, T2>.then(third: T3) = Triple(first, second, third)