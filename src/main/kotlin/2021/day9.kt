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
        val row = this[rowIndex]
        for ((index, digit) in row.withIndex()) {
            // Look up down left right for adjacent friends
            val adjacentValues = mutableListOf<Int>()
            index(index, rowIndex, Direction.LEFT).takeIf { it >= 0 }?.let {
                adjacentValues.add(row[it].digitToInt())
            }
            index(index, rowIndex, Direction.RIGHT).takeIf { it < row.length }?.let {
                adjacentValues.add(row[it].digitToInt())
            }
            index(index, rowIndex, Direction.UP).takeIf { it >= 0 }?.let {
                adjacentValues.add(this[it][index].digitToInt())
            }
            index(index, rowIndex, Direction.DOWN).takeIf { it < size }?.let {
                adjacentValues.add(this[it][index].digitToInt())
            }

            println("$digit has adjacent values ${adjacentValues.joinToString(",")}")
            points.add(Point(digit.digitToInt(), adjacentValues))
        }
    }
    return points
}

private fun index(columnIndex: Int, rowIndex: Int, direction: Direction): Int {
    return when (direction) {
        Direction.UP -> rowIndex - 1
        Direction.DOWN -> rowIndex + 1
        Direction.LEFT -> columnIndex - 1
        Direction.RIGHT -> columnIndex + 1
    }
}

enum class Direction { UP, DOWN, LEFT, RIGHT }
data class Point(val value: Int, val adjacentPoints: List<Int>)