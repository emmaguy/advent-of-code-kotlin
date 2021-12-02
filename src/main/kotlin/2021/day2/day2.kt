package `2021`.day2

import java.io.File

internal val submarineSampleInput = listOf(
    "forward 5",
    "down 5",
    "forward 8",
    "up 3",
    "down 8",
    "forward 2"
)
internal val submarineInput = File("src/main/resources/2021/day2.txt").readLines()

fun day2() {
    part2(submarineInput)
}

internal fun part2(input: List<String>) {
    var horizontalPosition = 0
    var depth = 0
    var aim = 0

    for (instruction in input) {
        val (direction, distanceStr) = instruction.split(' ')
        val distance = distanceStr.toInt()
        when (direction) {
            "forward" -> {
                horizontalPosition += distance
                depth += aim * distance
            }
            "down" -> aim += distance
            "up" -> aim -= distance
        }
    }

    println("horizontal position * depth: ${horizontalPosition * depth}")
}

internal fun part1(input: List<String>) {
    var horizontalPosition = 0
    var depth = 0

    for (instruction in input) {
        val (direction, distanceStr) = instruction.split(' ')
        val distance = distanceStr.toInt()
        when (direction) {
            "forward" -> horizontalPosition += distance
            "down" -> depth += distance
            "up" -> depth -= distance
        }
    }

    println("horizontal position * depth: ${horizontalPosition * depth}")
}