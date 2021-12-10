package aoc2021

import java.io.File

internal val heightmapInput = File("src/main/resources/2021/day9.txt").readLines()
internal val heightmapSample = File("src/main/resources/2021/day9-sample.txt").readLines()

fun main() {
    solve(heightmapSample)
    solve(heightmapInput)
}

fun solve(heightmap: List<String>) {
    val points = heightmap.toHeightmap()
    val lowPoints = points.filter { point -> point.adjacentPoints.all { point.value < it } }
    val sumRiskLevels = lowPoints.sumOf { it.value + 1 }

    println("sumRiskLevels: $sumRiskLevels")
    println("low points: \n${lowPoints.joinToString("\n")}")
    val basins = mutableListOf<MutableList<Int>>()

    for (point in lowPoints) {
        val visited = mutableSetOf<Pair<Int, Int>>()
        val basinValues = mutableListOf<Int>()

        // Move if we can in all directions
        Direction.values().forEach { direction ->
            move(point.columnIndex, point.rowIndex, direction, heightmap, basinValues, visited)
        }

        println("basin points: ${basinValues.joinToString(",")}")
        basins.add(basinValues)
    }

    basins.sortByDescending { it.size }
    val product = basins.take(3).map { it.size }.reduce { acc, i -> acc * i }
    println("$product")
}

internal fun move(
    columnIndex: Int,
    rowIndex: Int,
    direction: Direction,
    heightmap: List<String>,
    basinValues: MutableList<Int>,
    visited: MutableSet<Pair<Int, Int>>
) {
    val (r, c) = moveToIndex(columnIndex, rowIndex, direction)
    if (visited.contains(r to c)) return

    val valid = ((direction == Direction.UP || direction == Direction.DOWN) &&
            r >= 0 && r < heightmap.size)
            || ((direction == Direction.LEFT || direction == Direction.RIGHT) &&
            c >= 0 && c < heightmap[rowIndex].length)

    if (valid) {
        visited.add(r to c)
        val element = heightmap[r][c].digitToInt()
        if (element != 9) {
            basinValues.add(element)
            move(c, r, Direction.DOWN, heightmap, basinValues, visited)
            move(c, r, Direction.UP, heightmap, basinValues, visited)
            move(c, r, Direction.LEFT, heightmap, basinValues, visited)
            move(c, r, Direction.RIGHT, heightmap, basinValues, visited)
        }
    }
}

internal fun List<String>.toHeightmap(): List<Point> {
    val points = mutableListOf<Point>()
    for (rowIndex in 0 until this.size) {
        for ((columnIndex, digit) in this[rowIndex].withIndex()) {
            val adjacentValues = mutableListOf<Int>()

            // Move if we can in all directions
            Direction.values().forEach { direction ->
                val (r, c) = moveToIndex(columnIndex, rowIndex, direction)
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

private fun moveToIndex(columnIndex: Int, rowIndex: Int, direction: Direction): Pair<Int, Int> {
    return when (direction) {
        Direction.UP -> rowIndex - 1 to columnIndex
        Direction.DOWN -> rowIndex + 1 to columnIndex
        Direction.LEFT -> rowIndex to columnIndex - 1
        Direction.RIGHT -> rowIndex to columnIndex + 1
    }
}

enum class Direction { UP, DOWN, LEFT, RIGHT }
data class Point(val value: Int, val adjacentPoints: List<Int>, val columnIndex: Int, val rowIndex: Int)