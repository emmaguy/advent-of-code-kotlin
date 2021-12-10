package aoc2015

import java.io.File

fun main() {
    val directions = File("input/2015/day3.txt").readLines().first()

    println("part1: ${housesThatReceiveAtLeastOnePresent(directions)}")
    println("part2: ${part2(directions)}")
}

internal fun String.santaDirections(): String {
    return filterIndexed { index, _ -> index % 2 == 0 }
}

internal fun String.roboSantaDirections(): String {
    return filterIndexed { index, _ -> index % 2 == 1 }
}

internal fun part2(directions: String): Int {
    val santasHouses = housesThatReceivePresents(directions.santaDirections(), who = "santa")
    val roboSantaHouses = housesThatReceivePresents(directions.roboSantaDirections(), who = "robosanta")

    val overallHouses = mutableMapOf<String, Int>()
    overallHouses.putAll(santasHouses)
    overallHouses.putAll(roboSantaHouses)

    return overallHouses.size
}

internal fun housesThatReceiveAtLeastOnePresent(directionsString: String, who: String = "santa"): Int {
    return housesThatReceivePresents(directionsString, who).size
}

internal fun housesThatReceivePresents(directionsString: String, who: String): MutableMap<String, Int> {
    val directions = directionsString.toDirections()

    val coordinatesVisited = mutableMapOf<String, Int>()
    coordinatesVisited.append("(0,0)") // Starting point

    var xPos = 0
    var yPos = 0

    directions.forEach {
        when (it) {
            Direction.UP -> ++yPos
            Direction.DOWN -> --yPos
            Direction.LEFT -> ++xPos
            Direction.RIGHT -> --xPos
        }
        println("$who visits ($xPos,$yPos)")
        coordinatesVisited.append("($xPos,$yPos)")
    }

    return coordinatesVisited
}

private fun MutableMap<String, Int>.append(key: String) {
    val existingValue = getOrDefault(key, defaultValue = 0)
    this[key] = existingValue + 1
}

private fun String.toDirections(): List<Direction> {
    return map {
        when (it) {
            '^' -> Direction.UP
            '>' -> Direction.RIGHT
            '<' -> Direction.LEFT
            'v' -> Direction.DOWN
            else -> throw IllegalArgumentException("Unexpected direction: $it")
        }
    }.toList()
}

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}



