package `2021`.day3

import java.io.File

internal val sampleInput =
    listOf("00100", "11110", "10110", "10111", "10101", "01111", "00111", "11100", "10000", "11001", "00010", "01010")
internal val diagnosticReport = File("src/main/resources/2021/day3.txt").readLines()

fun day3() {
    part2(diagnosticReport)
}

internal fun part2(diagnosticReport: List<String>) {
    val oxygenRating = Integer.parseInt(oxygenRating(bitIndex = 0, diagnosticReport).first(), 2)
    val co2ScrubberRating = Integer.parseInt(co2ScrubberRating(bitIndex = 0, diagnosticReport).first(), 2)

    println("oxygenRating: $oxygenRating, co2ScrubberRating: $co2ScrubberRating, product: ${oxygenRating * co2ScrubberRating}")
}

private fun oxygenRating(bitIndex: Int, list: List<String>): List<String> {
    if (list.size == 1) {
        return list
    }

    val numZeros = list.count { it[bitIndex] == '0' }
    val numOnes = list.count { it[bitIndex] == '1' }
    val valuesWithAZero = list.filter { it[bitIndex] == '0' }
    val valuesWithAOne = list.filter { it[bitIndex] == '1' }

    return if (numZeros > numOnes) {
        oxygenRating(bitIndex = bitIndex + 1, valuesWithAZero)
    } else {
        oxygenRating(bitIndex = bitIndex + 1, valuesWithAOne)
    }
}

private fun co2ScrubberRating(bitIndex: Int, list: List<String>): List<String> {
    if (list.size == 1) {
        return list
    }

    val numZeros = list.count { it[bitIndex] == '0' }
    val numOnes = list.count { it[bitIndex] == '1' }

    val valuesWithAZero = list.filter { it[bitIndex] == '0' }
    val valuesWithAOne = list.filter { it[bitIndex] == '1' }

    println(
        "0s: $numZeros, 1s: $numOnes, val0s: ${valuesWithAZero.joinToString(",")}, val1s: ${
            valuesWithAOne.joinToString(
                ","
            )
        }"
    )
    return if (numZeros > numOnes) {
        co2ScrubberRating(bitIndex = bitIndex + 1, valuesWithAOne)
    } else {
        co2ScrubberRating(bitIndex = bitIndex + 1, valuesWithAZero)
    }
}

internal fun part1(diagnosticReport: List<String>) {
    var gammaRate = ""
    var epsilonRate = ""

    for (bitIndex in 0 until diagnosticReport[0].length) {
        val numZeros = diagnosticReport.count { it[bitIndex] == '0' }
        val numOnes = diagnosticReport.count { it[bitIndex] == '1' }

        if (numZeros > numOnes) {
            gammaRate += "0"
            epsilonRate += "1"
        } else {
            gammaRate += "1"
            epsilonRate += "0"
        }
    }
    val gamma = Integer.parseInt(gammaRate, 2)
    val epsilon = Integer.parseInt(epsilonRate, 2)
    println("gamma: $gamma, epsilon: $epsilon, product: ${gamma * epsilon}")
}