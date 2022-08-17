/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.function

inline fun <T : T2, T2> T.letIf(condition: Boolean, function: (T) -> T2) =
	if (condition) function(this) else this

inline fun <T : T2, T2> T.letIfNotNull(value: Any?, function: (T) -> T2) =
	letIf(value != null, function)

inline fun <T, O> T.letEach(secondOperands: Array<O>, function: (T, O) -> T): T {
	var value = this
	secondOperands.forEach { secondOperand ->
		value = function(value, secondOperand)
	}
	return value
}

inline fun <T, O> T.letEach(secondOperands: Iterable<O>, function: (T, O) -> T): T {
	var value = this
	secondOperands.forEach { secondOperand ->
		value = function(value, secondOperand)
	}
	return value
}

inline fun <T, O> T.letEach(secondOperands: Iterator<O>, function: (T, O) -> T): T {
	var value = this
	secondOperands.forEach { secondOperand ->
		value = function(value, secondOperand)
	}
	return value
}

inline fun <T, O> T.letEach(secondOperands: Sequence<O>, function: (T, O) -> T): T {
	var value = this
	secondOperands.forEach { secondOperand ->
		value = function(value, secondOperand)
	}
	return value
}

inline fun <T : T2, T2> T.runIf(condition: Boolean, function: T.() -> T2) =
	if (condition) function() else this

inline fun <T : T2, T2> T.runIfNotNull(value: Any?, function: T.() -> T2) =
	runIf(value != null, function)

inline fun <T, O> T.runEach(secondOperands: Array<O>, function: T.(O) -> T): T {
	var value = this
	secondOperands.forEach { secondOperand ->
		value = value.function(secondOperand)
	}
	return value
}

inline fun <T, O> T.runEach(secondOperands: Iterable<O>, function: T.(O) -> T): T {
	var value = this
	secondOperands.forEach { secondOperand ->
		value = value.function(secondOperand)
	}
	return value
}

inline fun <T, O> T.runEach(secondOperands: Iterator<O>, function: T.(O) -> T): T {
	var value = this
	secondOperands.forEach { secondOperand ->
		value = value.function(secondOperand)
	}
	return value
}

inline fun <T, O> T.runEach(secondOperands: Sequence<O>, function: T.(O) -> T): T {
	var value = this
	secondOperands.forEach { secondOperand ->
		value = value.function(secondOperand)
	}
	return value
}

inline fun <T, T2, T3> ((T) -> T2).andThen(crossinline function: (T2) -> T3): (T) -> T3 =
	{ function(this(it)) }

inline fun <T, T2> (() -> T).andThen(crossinline function: (T) -> T2): () -> T2 =
	{ function(this()) }

inline fun <T : Any> Array<T?>.forEachNotNull(action: (T) -> Unit) =
	forEach { it?.let(action) }

inline fun <T : Any> Iterable<T?>.forEachNotNull(action: (T) -> Unit) =
	forEach { it?.let(action) }

inline fun <T : Any> Iterator<T?>.forEachNotNull(action: (T) -> Unit) =
	forEach { it?.let(action) }

inline fun <T : Any> Sequence<T?>.forEachNotNull(action: (T) -> Unit) =
	forEach { it?.let(action) }

inline fun <T> Array<T>.runEach(function: T.() -> Any?) {
	forEach { it.function() }
}

inline fun <T> Iterable<T>.runEach(function: T.() -> Any?) {
	forEach { it.function() }
}

inline fun <T> Iterator<T>.runEach(function: T.() -> Any?) {
	forEach { it.function() }
}

inline fun <T> Sequence<T>.runEach(function: T.() -> Any?) {
	forEach { it.function() }
}

inline fun <T : Any> Array<T?>.runEachNotNull(action: T.() -> Unit) =
	forEach { it?.run(action) }

inline fun <T : Any> Iterable<T?>.runEachNotNull(action: T.() -> Unit) =
	forEach { it?.run(action) }

inline fun <T : Any> Iterator<T?>.runEachNotNull(action: T.() -> Unit) =
	forEach { it?.run(action) }

inline fun <T : Any> Sequence<T?>.runEachNotNull(action: T.() -> Unit) =
	forEach { it?.run(action) }

inline fun <T : Any> T.takeThisIf(predicate: T.() -> Boolean) =
	takeIf { it.predicate() }