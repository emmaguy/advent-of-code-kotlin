package aoc2019

import java.io.File
import kotlin.math.absoluteValue

private val memory = File("input/2019/day13.txt")
    .readLines()
    .first()
    .split(",")
    .map { it.toLong() }
    .toTypedArray()

fun main() {
    val computer = IntcodeComputer(memory = memory, inputList = mutableListOf()).apply { compute() }
    computer.output().forEach { println(it) }
}
