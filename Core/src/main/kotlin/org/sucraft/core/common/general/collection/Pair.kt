package org.sucraft.core.common.general.collection

import org.json.JSONObject
import java.util.AbstractMap.SimpleEntry
import java.util.Comparator
import java.util.stream.Collector
import java.util.stream.Collectors


@Suppress("MemberVisibilityCanBePrivate")
class Pair<A, B>(val first: A, val second: B) {

	constructor(entry: Map.Entry<A, B>) : this(entry.key, entry.value)

	fun <A2> applyFirst(function: (A) -> A2): Pair<A2, B> {
		return Pair(function(first), second)
	}

	fun <B2> applySecond(function: (B) -> B2): Pair<A, B2> {
		return Pair(first, function(second))
	}

	fun <A2, B2> apply(aFunction: (A) -> A2, bFunction: (B) -> B2): Pair<A2, B2> {
		return applyFirst(aFunction).applySecond(bFunction)
	}

	fun toEntry(): Map.Entry<A, B> {
		return SimpleEntry(first, second)
	}

	fun toJSON(aKey: String, bKey: String): JSONObject {
		val json = JSONObject()
		json.put(aKey, first)
		json.put(bKey, second)
		return json
	}

	companion object {

		fun <A, B> putAll(pairs: List<Pair<A, B>>, map: MutableMap<A, B>) = pairs.forEach { map[it.first] = it.second }

		fun <A, B> pairSet(map: Map<A, B>): Set<Pair<A, B>> = map.asSequence().map(::Pair).toSet()

		fun <A, B, A2> mapFirst(function: (A) -> A2): (Pair<A, B>) -> Pair<A2, B> = { it.applyFirst(function) }

		fun <A, B, B2> mapSecond(function: (B) -> B2): (Pair<A, B>) -> Pair<A, B2> = { it.applySecond(function) }

		fun <A, B, A2, B2> map(aFunction: (A) -> A2, bFunction: (B) -> B2): (Pair<A, B>) -> Pair<A2, B2> = { it.apply(aFunction, bFunction) }

		fun <A, B> toMapCollector(): Collector<Pair<A, B>, *, Map<A, B>> = Collectors.toMap({ obj: Pair<A, B> -> obj.first }, { obj: Pair<A, B> -> obj.second })

		fun <A, B> comparator(firstComparator: Comparator<A>, secondComparator: Comparator<B>, firstDominant: Boolean): Comparator<Pair<A, B>> =
			Comparator { pair1, pair2 ->
				val dominantCompare = if (firstDominant)
					firstComparator.compare(pair1.first, pair2.first)
				else
					secondComparator.compare(pair1.second, pair2.second)
				if (dominantCompare != 0) dominantCompare else {
					if (firstDominant)
						secondComparator.compare(pair1.second, pair2.second)
					else
						firstComparator.compare(pair1.first, pair2.first)
				}
			}

		fun <A : Comparable<A>, B : Comparable<B>> naturalOrderComparator(firstDominant: Boolean): Comparator<Pair<A, B>> {
			return comparator({ a1: A, a2: A -> a1.compareTo(a2) }, { b1: B, b2: B -> b1.compareTo(b2) }, firstDominant)
		}

	}

}