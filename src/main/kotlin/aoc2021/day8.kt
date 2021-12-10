package aoc2021

import java.io.File
import kotlin.math.pow

internal val displaySample = File("src/main/resources/2021/day8-sample.txt").readLines()

internal val displayInput = File("src/main/resources/2021/day8.txt").readLines()

fun main() {
    println("part2 sample: ${displaySample.parse().sum()}")
    println("part2 actual: ${displayInput.parse().sum()}")
}

internal fun List<String>.parse(): List<Int> {
    val entries = mutableListOf<Int>()
    forEach { entry ->
        println("it is: $entry")
        val split = entry.split("|")
        val signalPatterns = split[0].trim().split(" ")
        val output = split[1].trim().split(" ")

        val one = signalPatterns.first { it.length == 2 }
        val four = signalPatterns.first { it.length == 4 }
        val seven = signalPatterns.first { it.length == 3 }
        val eight = signalPatterns.first { it.length == 7 }

        val fiveTwoOrThree = signalPatterns.filter { it.length == 5 }
        val zeroNineOrSix = signalPatterns.filter { it.length == 6 }

        // fiveTwoOrThree only 3 contains c and f (7)
        val three = findDigit(fiveTwoOrThree, seven.toSet())
        println("three is $three")

        val bAndE = (eight.toSet() subtract three.toSet()).toSortedSet()
        println("bAndE are $bAndE")

        val (b, e) = if (four.contains(bAndE.first())) {
            bAndE.first() to bAndE.last()
        } else {
            bAndE.last() to bAndE.first()
        }
        println("b is $b")
        println("e is $e")

        val five = findDigit(fiveTwoOrThree, setOf(b))
        println("five is $five")

        println("fiveTwoOrThree is $fiveTwoOrThree")
        val two = mutableListOf(*fiveTwoOrThree.toTypedArray())
            .first { it.toSet() != three.toSet() && it.toSet() != five.toSet() }
        println("two is $two")

        // nine has no e
        val nine = findMissing(zeroNineOrSix, e)
        println("nine is $nine")

        val zeroOrSix = mutableListOf(*zeroNineOrSix.toTypedArray()).filter { it.toSet() != nine.toSet() }
        val zero = findDigit(zeroOrSix, one.toSet())
        println("zero is $zero")

        // 6 is left
        val six = mutableListOf(*zeroNineOrSix.toTypedArray())
            .first { it.toSet() != nine.toSet() && it.toSet() != zero.toSet() }
        println("six is $six")

        val digits = mutableListOf<Int>()
        for (o in output) {
            digits += when (o.toSet()) {
                zero.toSet() -> 0
                one.toSet() -> 1
                two.toSet() -> 2
                three.toSet() -> 3
                four.toSet() -> 4
                five.toSet() -> 5
                six.toSet() -> 6
                seven.toSet() -> 7
                eight.toSet() -> 8
                nine.toSet() -> 9
                else -> throw RuntimeException("I hate everything: $o")
            }
        }
        entries.add(digits.fromDigits())
        println("...")
    }
    return entries
}

fun findMissing(possibles: List<String>, required: Char): String {
    for (str in possibles) {
        if (!str.toSet().contains(required)) {
            return str
        }
    }
    throw RuntimeException("Couldn't find three")
}

fun findDigit(possibles: List<String>, excluded: Set<Char>): String {
    for (str in possibles) {
        if (str.toSet().containsAll(excluded)) {
            return str
        }
    }
    throw RuntimeException("Couldn't find three")
}

private fun List<Int>.fromDigits(): Int {
    var total = 0.0
    for (i in indices) {
        total += this[i] * 10.0.pow(size - i - 1)
    }
    return total.toInt()
}