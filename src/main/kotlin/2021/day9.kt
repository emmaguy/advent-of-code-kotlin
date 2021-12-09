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
    for (rowIndex in 0 until this.size) {
        for ((columnIndex, digit) in this[rowIndex].withIndex()) {
            // Look up down left right for adjacent friends
            val adjacentValues = mutableListOf<Int>()
            index(size, columnIndex, rowIndex, Direction.LEFT)?.let {
                adjacentValues.add(this[it.first][it.second].digitToInt())
            }
            index(size, columnIndex, rowIndex, Direction.RIGHT)?.let {
                adjacentValues.add(this[it.first][it.second].digitToInt())
            }
            index(size, columnIndex, rowIndex, Direction.UP)?.let {
                adjacentValues.add(this[it.first][it.second].digitToInt())
            }
            index(size, columnIndex, rowIndex, Direction.DOWN)?.let {
                adjacentValues.add(this[it.first][it.second].digitToInt())
            }

            println("$digit has adjacent values ${adjacentValues.joinToString(",")}")
            points.add(Point(digit.digitToInt(), adjacentValues))
        }
    }
    return points
}

private fun index(size: Int, columnIndex: Int, rowIndex: Int, direction: Direction): Pair<Int, Int>? {
    val index = when (direction) {
        Direction.UP -> rowIndex - 1
        Direction.DOWN -> rowIndex + 1
        Direction.LEFT -> columnIndex - 1
        Direction.RIGHT -> columnIndex + 1
    }
    return if (direction == Direction.UP || direction == Direction.DOWN) {
        if (index in 0 until size) index to columnIndex else null
    } else {
        if (index in 0 until size) rowIndex to index else null
    }
}

enum class Direction { UP, DOWN, LEFT, RIGHT }
data class Point(val value: Int, val adjacentPoints: List<Int>)