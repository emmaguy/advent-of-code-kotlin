package aoc2021

import java.io.File
import java.math.BigInteger

internal val fishSample = File("src/main/resources/2021/day6-sample.txt")
    .readLines()
    .toFish()
internal val fishInput = File("src/main/resources/2021/day6.txt")
    .readLines()
    .toFish()

fun main() {
    calculateSchoolSize(fishInput, days = 256)
}

internal fun calculateSchoolSize(input: List<Long>, days: Int) {
    val fishDaysRemaining = LongArray(9) { 0 }
    input.forEach { fishDaysRemaining[it.toInt()] += 1L }
    println("initial: ${fishDaysRemaining.joinToString(",")}")

    var daysElapsed = 1
    while (daysElapsed <= days) {
        val newFish = fishDaysRemaining[0]
        for (i in fishDaysRemaining.indices) {
            if (i > 0) {
                fishDaysRemaining[i - 1] = fishDaysRemaining[i]
            }
        }

        fishDaysRemaining[6] += newFish // Old fish
        fishDaysRemaining[8] = newFish // Baby fish
        println("after $daysElapsed days: ${fishDaysRemaining.sum()} fish, ${fishDaysRemaining.joinToString(",")}")
        daysElapsed++
    }

    println("${fishDaysRemaining.sum()}")
}

internal fun List<String>.toFish(): List<Long> {
    return first().split(",").map { it.toLong() }
}
