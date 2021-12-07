package `2021`

import java.io.File
import java.math.BigInteger
import kotlin.math.max

internal val crabPositionsSample = File("src/main/resources/2021/day7-sample.txt")
    .readLines()
    .toCrabPositions()
internal val crabPositionsInput = File("src/main/resources/2021/day7.txt")
    .readLines()
    .toCrabPositions()

fun main() {
    part1(crabPositionsInput)
}

internal fun part1(positions: List<Int>) {
    println("input ${positions.joinToString(",")}")

    val minPosition = positions.minOrNull()!!
    val maxPosition = positions.maxOrNull()!!

    val fuelCosts = mutableListOf<Map<Int, Int>>()
    for (position in positions) {
        val map = mutableMapOf<Int, Int>()
        for (p in minPosition until maxPosition) {
            if (position > p) {
                map[p] = position - p
            } else {
                map[p] = p - position
            }
        }
        fuelCosts.add(map)
    }

    var min = Int.MAX_VALUE
    for (pos in 0 until fuelCosts[0].size) {
        var sum = 0
        for (i in fuelCosts) {
            i[pos]?.let { sum += it }
        }
        if (sum < min) {
            min = sum
        }
        println("position $pos costs: $sum fuel")
    }
    println("min $min")
}

internal fun List<String>.toCrabPositions(): List<Int> {
    return first().split(",").map { it.toInt() }
}
