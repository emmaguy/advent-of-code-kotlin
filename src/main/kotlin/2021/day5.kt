package `2021`

import java.io.File

internal val ventLinesSample = File("src/main/resources/2021/day5-sample.txt")
    .readLines()
internal val ventLines = File("src/main/resources/2021/day5.txt")
    .readLines()

fun main() {
    calculatePoints(ventLines.toInput(includeDiagonals = false))
    calculatePoints(ventLines.toInput(includeDiagonals = true))
}

internal fun calculatePoints(input: List<Pair<Int, Int>>) {
    val frequencyMap = mutableMapOf<Pair<Int, Int>, Int>()

    input.forEach { frequencyMap[it] = (frequencyMap[it] ?: 0) + 1 }

    // Count all points with the same max
    println("points: ${frequencyMap.count { it.value >= 2 }}")
}

// Parse the points and calculate the ones in between
internal fun List<String>.toInput(includeDiagonals: Boolean): List<Pair<Int, Int>> {
    return flatMap { line ->
        val start = line.split(" ")[0].split(",")
        val end = line.split(" ")[2].split(",")

        val x1 = start[0].toInt()
        val y1 = start[1].toInt()

        val x2 = end[0].toInt()
        val y2 = end[1].toInt()

        if (x1 == x2) {
            rangeFromSmallest(y1, y2).map { Pair(x1, it) }
        } else if (y1 == y2) {
            rangeFromSmallest(x1, x2).map { Pair(it, y1) }
        } else {
            if (includeDiagonals) {
                val diff = distanceToMove(x1, x2)
                (0..diff).map {
                    val x = if (x2 > x1) {
                        x1 + it
                    } else {
                        x1 - it
                    }
                    val y = if (y2 > y1) {
                        y1 + it
                    } else {
                        y1 - it
                    }
                    Pair(x, y)
                }
            } else {
                emptyList()
            }
        }
    }
}

fun distanceToMove(x1: Int, x2: Int): Int {
    return if (x2 > x1) x2 - x1 else x1 - x2
}

private fun rangeFromSmallest(y1: Int, y2: Int): IntRange {
    return (if (y2 > y1) (y1..y2) else (y2..y1))
}