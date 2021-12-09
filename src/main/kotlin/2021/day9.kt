package `2021`

import java.io.File

internal val heightmapInput = File("src/main/resources/2021/day9.txt").readLines()
internal val heightmapSample = File("src/main/resources/2021/day9-sample.txt").readLines()

fun main() {
    part1(heightmapSample)
    part1(heightmapInput)
}

fun part1(heightmap: List<String>) {
    val lowPoints = heightmap.toHeightmap().filter { point -> point.adjacentPoints.all { point.value < it } }
    val sumRiskLevels = lowPoints.sumOf { it.value + 1 }

    println("sumRiskLevels: $sumRiskLevels, lowPoints: $lowPoints")
}

internal fun List<String>.toHeightmap(): List<Point> {
    val points = mutableListOf<Point>()
    forEachIndexed { rowIndex, s ->
        // parse each number and get the adjacent values
        for ((index, digit) in s.withIndex()) {

            // Look up down left right for adjacent friends
            val adjacentValues = mutableListOf<Int>()
            if (index - 1 >= 0) { // Left
                adjacentValues.add(s[index - 1].digitToInt())
            }
            if (index + 1 < s.length) { // Right
                adjacentValues.add(s[index + 1].digitToInt())
            }
            if (rowIndex - 1 >= 0) { // Up
                adjacentValues.add(this[rowIndex - 1][index].digitToInt())
            }
            if (rowIndex + 1 < size) { // Down
                adjacentValues.add(this[rowIndex + 1][index].digitToInt())
            }

            println("$digit has adjacent values ${adjacentValues.joinToString(",")}")
            points.add(Point(digit.digitToInt(), adjacentValues))
        }
    }
    return points
}

data class Point(val value: Int, val adjacentPoints: List<Int>)