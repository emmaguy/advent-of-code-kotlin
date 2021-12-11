package aoc2021

import java.io.File

internal val xInput = File("src/main/resources/2021/day11.txt").readLines()
internal val xSample = File("src/main/resources/2021/day11-sample.txt").readLines()

fun main() {
    part1(xSample)
}

private fun part1(input: List<String>) {
    val octopuses = input.toOctupuses()

    println("$octopuses")
}

internal fun List<String>.toOctupuses(): List<Octopus> {
    val points = mutableListOf<Octopus>()
    for (rowIndex in 0 until this.size) {
        for ((columnIndex, digit) in this[rowIndex].withIndex()) {
            val adjacentOctopuses = mutableListOf<Int>()

            AdjacentDirection.values().forEach { direction ->
                val (rOffset, cOffset) = direction.move()
                val r = rowIndex + rOffset
                val c = columnIndex + cOffset

                if (c >= 0 && c < this[rowIndex].length && r in indices) {
                    adjacentOctopuses.add(this[r][c].digitToInt())
                }
            }
            points.add(Octopus(value = digit.digitToInt(), adjacentPoints = adjacentOctopuses))
        }
    }
    return points
}

private fun AdjacentDirection.move(): Pair<Int, Int> {
    return when (this) {
        AdjacentDirection.N -> -1 to 0
        AdjacentDirection.NE -> -1 to 1
        AdjacentDirection.E -> 0 to 1
        AdjacentDirection.SE -> 1 to 1
        AdjacentDirection.S -> 1 to 0
        AdjacentDirection.SW -> 1 to -1
        AdjacentDirection.W -> 0 to -1
        AdjacentDirection.NW -> -1 to -1
    }
}

enum class AdjacentDirection { N, NE, E, SE, S, SW, W, NW }
data class Octopus(val value: Int, val adjacentPoints: List<Int>)