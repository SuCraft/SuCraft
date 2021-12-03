/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.general.string


object StringSplit {

    fun cutStringIntoParts(text: String, partPrefix: String?, partSuffix: String?, maxPartLength: Int): List<String> {

        fun createPartFromInnerText(innerText: String?): String {
            val partBuilder = StringBuilder("")
            if (partPrefix != null) {
                partBuilder.append(partPrefix)
            }
            partBuilder.append(innerText)
            if (partSuffix != null) {
                partBuilder.append(partSuffix)
            }
            return partBuilder.toString()
        }

        val result: MutableList<String> = ArrayList()
        var index = 0
        var textLeft = text
        while (true) {
            if (index == textLeft.length) {
                result.add(createPartFromInnerText(textLeft))
                break
            }
            var nextIndex = index + 1
            while (true) {
                if (nextIndex == textLeft.length || textLeft[nextIndex] == ' ') {
                    break
                }
                nextIndex++
            }
            if (nextIndex > maxPartLength) {
                result.add(createPartFromInnerText(textLeft.substring(0, index)))
                textLeft = textLeft.substring(index + 1)
                index = 0
            } else {
                index = nextIndex
            }
        }

        return result

    }

    /**
     * The max line length is as excluding the line prefix
     *
     * This method will remove any repeated spaces from the string, as well as any leading or trailing spaces
     */
    fun splitIntoLines(string: String, maxLineLength: Int, linePrefix: String): List<String> {

        var whitespaceNormalizedString = string
        while (true) {
            val newString = whitespaceNormalizedString.replace("  ", " ")
            if (newString == whitespaceNormalizedString) {
                break
            }
            whitespaceNormalizedString = newString
        }
        whitespaceNormalizedString = whitespaceNormalizedString.trim { it <= ' ' }
        val lines: MutableList<String> = ArrayList()
        var currentLine = ""
        var index = 0
        while (true) {
            var nextSpaceIndex = whitespaceNormalizedString.indexOf(' ', index)
            if (nextSpaceIndex == -1) {
                nextSpaceIndex = whitespaceNormalizedString.length
            }
            var newLineLength = nextSpaceIndex - index + currentLine.length
            if (currentLine.isNotEmpty()) {
                newLineLength++
            }
            if (newLineLength > maxLineLength) {
                if (currentLine.isEmpty()) {
                    lines.add(whitespaceNormalizedString.substring(index, index + maxLineLength - 1) + "-")
                    index += maxLineLength - 1
                } else {
                    lines.add(currentLine)
                    currentLine = ""
                }
            } else {
                if (currentLine.isNotEmpty()) {
                    currentLine += " "
                }
                currentLine += whitespaceNormalizedString.substring(index, nextSpaceIndex)
                if (nextSpaceIndex == whitespaceNormalizedString.length) {
                    lines.add(currentLine)
                    break
                }
                index = nextSpaceIndex + 1
            }
        }
        for (i in lines.indices) {
            lines[i] = linePrefix + lines[i]
        }

        return lines

    }

}