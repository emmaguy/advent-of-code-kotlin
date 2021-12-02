package `2019`
import java.io.File

val inputMemory = File("input/2019/day9.txt")
    .readLines()
    .first()
    .split(",")
    .map { it.toLong() }
    .toTypedArray()

fun main() {
    val memory = LongArray(100000000).toTypedArray()
    inputMemory.copyInto(destination = memory)

    IntcodeComputer(memory, mutableListOf(1)).compute()
    IntcodeComputer(memory, mutableListOf(2)).compute()
}
