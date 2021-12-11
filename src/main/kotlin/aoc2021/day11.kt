package aoc2021

import java.io.File

internal val xInput = File("src/main/resources/2021/day11.txt").readLines()
internal val xSample = File("src/main/resources/2021/day11-sample.txt").readLines()

fun main() {
    part1(xSample, days = 2)
}

private fun part1(input: List<String>, days: Int) {
    val octopusPositions = input.map { it.map { Octopus(value = it.digitToInt()) }.toMutableList() }

    for (day in 0 until days) {
        println("Day $day")
        octopusPositions.forEach { println(it) }

        for (row in octopusPositions) {
            for (octopus in row) {
                octopus.value++
            }
        }

        for ((rowIndex, row) in octopusPositions.withIndex()) {
            for ((columnIndex, octopus) in row.withIndex()) {
                if (octopus.value > 9) {
                    octopus.flashed = true
                    AdjacentDirection.values().forEach { direction ->
                        val (rOffset, cOffset) = direction.offsetForDirection()
                        val r = rowIndex + rOffset
                        val c = columnIndex + cOffset

                        if (c >= 0 && c < octopusPositions[rowIndex].size &&
                            r >= 0 && r < octopusPositions.size
                        ) {
                            val adjacentOctopus = octopusPositions[r][c]
                            adjacentOctopus.value++
                            if (!adjacentOctopus.flashed && adjacentOctopus.value > 9) {
                                adjacentOctopus.flashed = true
                            }
                        }
                    }
                }
            }
        }

        var count = 0
        for (row in octopusPositions) {
            for (octopus in row) {
                if (octopus.flashed) {
                    count++
                    octopus.flashed = false
                    octopus.value = 0
                }
            }
        }
        println("flash count $count\n")
    }

    println("Day ${days}:")
    octopusPositions.forEach { println(it) }
}

private fun AdjacentDirection.offsetForDirection(): Pair<Int, Int> {
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
data class Octopus(var value: Int, var flashed: Boolean = false) {
    override fun toString(): String {
        return "$value"
    }
}