/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.general.string

import java.util.*


@Suppress("MemberVisibilityCanBePrivate")
object StringReplacement {

    /**
     * Original must have the same length as replacement (otherwise will just return the string as given)
     */
    fun replaceKeepCase(string: String, original: String, replacement: String): String {
        if (original.length != replacement.length) {
            return string
        }
        val builder = StringBuilder("")
        val lowerCaseString = string.lowercase(Locale.getDefault())
        val lowerCaseOriginal = original.lowercase(Locale.getDefault())
        val lowerCaseReplacement = replacement.lowercase(Locale.getDefault())
        val upperCaseReplacement = replacement.uppercase(Locale.getDefault())
        var startSearchIndex = 0
        while (startSearchIndex < string.length) {
            val index = lowerCaseString.indexOf(lowerCaseOriginal, startSearchIndex)
            if (index == -1) {
                break
            }
            if (index > startSearchIndex) {
                builder.append(string.substring(startSearchIndex, index))
            }
            for (i in original.indices) {
                if (Character.isUpperCase(string[index + i])) {
                    builder.append(upperCaseReplacement[i])
                } else {
                    builder.append(lowerCaseReplacement[i])
                }
            }
            startSearchIndex = index + original.length
        }
        if (string.length > startSearchIndex) {
            builder.append(string.substring(startSearchIndex))
        }
        return builder.toString()
    }

    fun prefixAll(strings: Array<String>, prefix: String, condition: (String) -> Boolean = { true }): List<String> {
        return prefixAll(listOf(*strings), prefix, condition)
    }

    fun prefixAll(strings: Iterable<String>, prefix: String, condition: (String) -> Boolean = { true }): List<String> {
        val prefixed: MutableList<String> = ArrayList()
        for (string in strings) {
            if (condition(string)) {
                prefixed.add(prefix + string)
            } else {
                prefixed.add(string)
            }
        }
        return prefixed
    }

}