package aoc2021

import java.io.File

internal val xInput = File("src/main/resources/2021/day10-sample.txt")
    .readLines()
internal val xSample = File("src/main/resources/2021/day10.txt")
    .readLines()

fun main() {

}

internal fun List<String>.toWhatever(): List<Int> {
    return first().split(",").map { it.toInt() }
}
