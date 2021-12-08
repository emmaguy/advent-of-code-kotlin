package `2021`

import java.io.File

internal val displaySample = File("src/main/resources/2021/day8-sample.txt")
    .readLines()
    .parse()

internal val displayInput = File("src/main/resources/2021/day8.txt")
    .readLines()
    .parse()

fun main() {
    val total1478s = displayInput.flatMap { it.output }.count { it == 1 || it == 4 || it == 7 || it == 8 }
    println("part1: $total1478s")
}

internal fun List<String>.parse(): List<Entry> {
    val entries = mutableListOf<Entry>()
    forEach {
        val split = it.split("|")
        val signalPatterns = split[0].trim()
        val output = split[1].trim()

        entries.add(
            Entry(
                input = signalPatterns.parse(),
                output = output.parse()
            )
        )
        println("${entries[entries.size - 1]}")
    }
    return entries
}

internal fun String.parse(): List<Int> {
    return split(" ").map { it.from7SegmentDisplay() }
}

internal fun String.from7SegmentDisplay(): Int {
    return when {
        length == 2 -> 1
        length == 4 -> 4
        length == 3 -> 7
        length == 7 -> 8
        else -> 0
    }
}

data class Entry(val input: List<Int>, val output: List<Int>)