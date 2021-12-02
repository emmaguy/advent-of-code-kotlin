package `2015`

import java.io.File
import kotlin.math.min

fun main() {
    val dimensions = File("input/2015/day2.txt").readLines()

    println("part1: ${dimensions.map { wrappingPaperRequired(it) }.sum()}")
    println("part2: ${dimensions.map { ribbonRequired(it) }.sum()}")
}

internal fun wrappingPaperRequired(presentDimensions: String): Int {
    val (l, w, h) = presentDimensions.toDimensions()
    val side1 = l * w
    val side2 = w * h
    val side3 = h * l
    return 2 * side1 + 2 * side2 + 2 * side3 + min(side1, min(side2, side3))
}

private fun String.toDimensions(): List<Int> {
    return split("x").map { it.toInt() }
}

internal fun ribbonRequired(presentDimensions: String): Int {
    val dimensions = presentDimensions.toDimensions()
    val (l, w, h) = dimensions
    val shortestSides = dimensions.sorted().subList(fromIndex = 0, toIndex = 2)
    val bow = l * w * h
    return shortestSides.first() * 2 + shortestSides.last() * 2 + bow
}


