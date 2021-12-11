package aoc2021

import java.io.File

internal val octopusInput = File("src/main/resources/2021/day11.txt").readLines()
internal val octopusSample = File("src/main/resources/2021/day11-sample.txt").readLines()

fun main() {
    part1(octopusSample, days = 10)
    part1(octopusInput, days = 100)
    part2(octopusSample, days = 200)
    part2(octopusInput, days = 1000)
}

private fun part2(input: List<String>, days: Int) {
    val octopuses = findAdjacents(input).flatten()

    for (day in 0 until days) {
        println("Day $day")
        octopuses.forEach { octopus -> octopus.value++ }
        octopuses.forEach { octopus -> flash(octopus) }

        var flashes = 0
        octopuses.forEach { octopus ->
            if (octopus.flashed) {
                octopus.flashed = false
                octopus.value = 0
                flashes++
            }
        }
        if (flashes == octopuses.size) {
            println("done! All ${octopuses.size} octupuses flashed together on day ${day + 1}")
            return
        }
    }
}

private fun part1(input: List<String>, days: Int) {
    val octopuses = findAdjacents(input).flatten()

    var totalFlashes = 0
    for (day in 0 until days) {
        println("Day $day")

        octopuses.forEach { octopus -> octopus.value++ }
        octopuses.forEach { octopus -> flash(octopus) }

        octopuses.forEach { octopus ->
            if (octopus.flashed) {
                octopus.flashed = false
                octopus.value = 0
                totalFlashes++
            }
        }
        println("Flashes $totalFlashes")
    }
}

private fun findAdjacents(input: List<String>): List<MutableList<Octopus>> {
    val octopusPositions = input.map { it.map { Octopus(value = it.digitToInt()) }.toMutableList() }

    for ((rowIndex, row) in octopusPositions.withIndex()) {
        for ((columnIndex, octopus) in row.withIndex()) {
            val adjacentOctopuses = mutableListOf<Octopus>()
            AdjacentDirection.values().forEach { direction ->
                val (rOffset, cOffset) = direction.offsetForDirection()
                val r = rowIndex + rOffset
                val c = columnIndex + cOffset

                if (c >= 0 && c < octopusPositions[rowIndex].size &&
                    r >= 0 && r < octopusPositions.size
                ) {
                    adjacentOctopuses.add(octopusPositions[r][c])
                }
            }
            octopus.adjacents = adjacentOctopuses
        }
    }
    return octopusPositions
}

private fun flash(octopus: Octopus) {
    if (octopus.value > 9 && !octopus.flashed) {
        octopus.flashed = true
        octopus.adjacents.forEach {
            it.value++
            flash(it)
        }
    }
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
    var adjacents: List<Octopus> = emptyList()
}