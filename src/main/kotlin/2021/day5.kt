package `2021`

import java.io.File

internal val ventLinesSample = File("src/main/resources/2021/day5-sample.txt")
    .readLines()
internal val ventLines = File("src/main/resources/2021/day5.txt")
    .readLines()

fun main() {
    calculatePoints(ventLinesSample.toInputPart2())
}

internal fun calculatePoints(input: List<Pair<Int, Int>>) {
    val frequencyMap = mutableMapOf<Pair<Int, Int>, Int>()

    input.forEach { frequencyMap[it] = (frequencyMap[it] ?: 0) + 1 }

    // Count all points with the same max
    println("points: ${frequencyMap.count { it.value >= 2 }}")
}

// Horizontal and diagonal only
internal fun List<String>.toInputPart1(): List<Pair<Int, Int>> {
    return flatMap { line ->
        val split = line.split(" ")
        val start = split[0].split(",")
        val end = split[2].split(",")

        val x1 = start[0].toInt()
        val y1 = start[1].toInt()

        val x2 = end[0].toInt()
        val y2 = end[1].toInt()

        if (x1 == x2) {
            (if (y2 > y1) (y1..y2) else (y2..y1)).map { Pair(x1, it) }
        } else if (y1 == y2) {
            (if (x2 > x1) (x1..x2) else (x2..x1)).map { Pair(it, y1) }
        } else {
            emptyList()
        }
    }
}

internal fun List<String>.toInputPart2(): List<Pair<Int, Int>> {
    return flatMap { line ->
        val split = line.split(" ")
        val start = split[0].split(",")
        val end = split[2].split(",")

        val x1 = start[0].toInt()
        val y1 = start[1].toInt()

        val x2 = end[0].toInt()
        val y2 = end[1].toInt()

        if (x1 == x2) {
            (if (y2 > y1) (y1..y2) else (y2..y1)).map { Pair(x1, it) }
        } else if (y1 == y2) {
            (if (x2 > x1) (x1..x2) else (x2..x1)).map { Pair(it, y1) }
        } else {
            emptyList()
        }
    }
}