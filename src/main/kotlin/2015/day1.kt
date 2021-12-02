package `2015`

import java.io.File
import java.lang.RuntimeException

const val floorBasement = -1

fun main() {
    val liftInstructions = File("input/2015/day1.txt").readLines().first()

    println("part1: ${calculateFloor(liftInstructions)}")
    println("part2: ${findFirstBasement(liftInstructions)}")
}

internal fun calculateFloor(input: String): Int {
    return input.map { if (it == '(') 1 else -1 }.sum()
}

internal fun findFirstBasement(input: String): Int {
    var total = 0
    input.forEachIndexed { index, it ->
        total += if (it == '(') 1 else -1
        if (total == floorBasement) return index + 1
    }

    throw RuntimeException("Never reached the basement")
}