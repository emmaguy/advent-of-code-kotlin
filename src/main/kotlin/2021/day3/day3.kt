package `2021`.day3

import java.io.File

internal val sampleInput =
    listOf("00100", "11110", "10110", "10111", "10101", "01111", "00111", "11100", "10000", "11001", "00010", "01010")
internal val diagnosticReport = File("src/main/resources/2021/day3.txt").readLines()

fun day3() {
    part1(diagnosticReport)
}

internal fun part2() {

}

internal fun part1(diagnosticReport: List<String>) {
    var gammaRate = ""
    var epsilonRate = ""

    for (index in 0 until diagnosticReport[0].length) {
        val numZeros = diagnosticReport.count { it[index] == '0' }
        val numOnes = diagnosticReport.count { it[index] == '1' }

        if (numZeros > numOnes) {
            gammaRate += "0"
            epsilonRate += "1"
        } else {
            gammaRate += "1"
            epsilonRate += "0"
        }
        println("0s: $numZeros, 1s: $numOnes, gam: $gammaRate, ep: $epsilonRate")
    }
    val gamma = Integer.parseInt(gammaRate, 2)
    val epsilon = Integer.parseInt(epsilonRate, 2)
    println("gamma: $gamma, epsilon: $epsilon, product: ${gamma * epsilon}")
}