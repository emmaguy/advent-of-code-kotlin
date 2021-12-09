package `2021`

import java.io.File

internal val heightmapInput = File("src/main/resources/2021/day9.txt").readLines()
internal val heightmapSample = File("src/main/resources/2021/day9-sample.txt").readLines()

fun main() {
    part1(heightmapSample)
    part1(heightmapInput)
}

fun part1(heightmap: List<String>) {
    val points = heightmap.toHeightmap()
//    println("all points: ${points.joinToString("\n")}")

    val lowPoints = points.filter { point -> point.adjacentPoints.all { point.value < it } }
    val sumRiskLevels = lowPoints.sumOf { it.value + 1 }

    println("sumRiskLevels: $sumRiskLevels")
    println("low points: ${lowPoints.joinToString("\n")}")
}

internal fun List<String>.toHeightmap(): List<Point> {
    val points = mutableListOf<Point>()
    for (rowIndex in 0 until this.size) {
        for ((columnIndex, digit) in this[rowIndex].withIndex()) {
            val adjacentValues = mutableListOf<Int>()

            // Move if we can in all directions
            Direction.values().forEach { direction ->
                val (r, c) = getIndex(columnIndex, rowIndex, direction)
                if (direction == Direction.UP || direction == Direction.DOWN) {
                    if (r in indices) {
                        adjacentValues.add(this[r][c].digitToInt())
                    }
                } else {
                    if (c >= 0 && c < this[rowIndex].length) {
                        adjacentValues.add(this[r][c].digitToInt())
                    }
                }
            }

//            println("$digit has adjacent values ${adjacentValues.joinToString(",")}")
            points.add(
                Point(
                    value = digit.digitToInt(),
                    adjacentPoints = adjacentValues,
                    columnIndex = columnIndex,
                    rowIndex = rowIndex
                )
            )
        }
    }
    return points
}

private fun getIndex(columnIndex: Int, rowIndex: Int, direction: Direction): Pair<Int, Int> {
    return when (direction) {
        Direction.UP -> rowIndex - 1 to columnIndex
        Direction.DOWN -> rowIndex + 1 to columnIndex
        Direction.LEFT -> rowIndex to columnIndex - 1
        Direction.RIGHT -> rowIndex to columnIndex + 1
    }
}

enum class Direction { UP, DOWN, LEFT, RIGHT }
data class Point(val value: Int, val adjacentPoints: List<Int>, val columnIndex: Int, val rowIndex: Int)