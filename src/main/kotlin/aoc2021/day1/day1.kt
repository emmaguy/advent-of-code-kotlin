package aoc2021.day1

import java.io.File

internal val sampleInput = listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)
internal val submarineReport = File("src/main/resources/2021/day1.txt")
    .readLines()
    .map { it.toInt() }

fun day1() {
    part1(submarineReport)
    part2(submarineReport)
}

internal fun part2(list: List<Int>) {
    var increases = 0
    var currentValue = 0
    for ((index, value) in list.withIndex()) {
        val newValue = slidingWindow(index, list)
        if (newValue > currentValue && index != 0) {
            increases++
        }
        currentValue = newValue
    }
    println("part2 increases: $increases")
}

private fun slidingWindow(index: Int, list: List<Int>): Int {
    if (list.size <= index + 2) return 0
    return list[index] + list[index + 1] + list[index + 2]
}

internal fun part1(list: List<Int>) {
    var increases = 0

    for ((index, value) in list.withIndex()) {
        if (index > 0) {
            if (value > list[index - 1])
                increases++
        }
    }

    println("part1 increases: $increases")
}
