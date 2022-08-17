/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.concurrent

import com.github.shynixn.mccoroutine.bukkit.asyncDispatcher
import com.github.shynixn.mccoroutine.bukkit.scope
import kotlinx.coroutines.*
import org.sucraft.main.SuCraftPlugin

val bukkitScope by lazy {
	SuCraftPlugin.instance.scope
}

val defaultScope by lazy {
	SuCraftPlugin.instance.defaultScope
}

val ioScope by lazy {
	SuCraftPlugin.instance.ioScope
}

fun bukkitLaunch(block: suspend CoroutineScope.() -> Unit) =
	bukkitScope.launch(block = block)

fun defaultLaunch(block: suspend CoroutineScope.() -> Unit) =
	defaultScope.launch(block = block)

fun ioLaunch(block: suspend CoroutineScope.() -> Unit) =
	ioScope.launch(block = block)

fun <T> bukkitAsync(block: suspend CoroutineScope.() -> T) =
	bukkitScope.async(block = block)

fun <T> defaultAsync(block: suspend CoroutineScope.() -> T) =
	defaultScope.async(block = block)

fun <T> ioAsync(block: suspend CoroutineScope.() -> T) =
	ioScope.async(block = block)

fun <T> bukkitRunBlocking(block: suspend CoroutineScope.() -> T) =
	runBlocking(SuCraftPlugin.instance.asyncDispatcher, block = block)

fun <T> defaultRunBlocking(block: suspend CoroutineScope.() -> T) =
	runBlocking(Dispatchers.Default, block = block)

fun <T> ioRunBlocking(block: suspend CoroutineScope.() -> T) =
	runBlocking(Dispatchers.IO, block = block)