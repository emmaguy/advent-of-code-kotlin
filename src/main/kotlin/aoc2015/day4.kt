package aoc2015

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking
import java.math.BigInteger
import java.security.MessageDigest

fun main() {
    val key = "yzbqklnj"

    println("part1: ${findLowestNumberWhichStartsWithAtLeastFiveZeros(key)}")

    runBlocking {
        println("part2: ")

        val numbers = produceNumbers()
        val hashes = calculate(key, numbers)
        repeat(1) {
            println(hashes.receive())
        }

        coroutineContext.cancelChildren()
    }
}

internal fun findLowestNumberWhichStartsWithAtLeastFiveZeros(key: String): Int {
    var number = 0
    while (true) {
        val hash = calculateSecretKeyHash(key, number)
        if (hash.substring(0, 5) == "00000") {
            return number
        }
        number++
    }
}

/**
 * Product an infinite stream of integers, starting from 0
 */
fun CoroutineScope.produceNumbers() = produce {
    var x = 0
    while (true) send(x++)
}

fun CoroutineScope.calculate(key: String, numbers: ReceiveChannel<Int>): ReceiveChannel<Int> {
    return produce {
        for (i in numbers) {
            val hash = calculateSecretKeyHash(key, i)
            if (hash.substring(0, 6) == "000000") {
                send(i)
            }
        }
    }
}

internal fun calculateSecretKeyHash(key: String, number: Int): String {
    return "$key$number".md5()
}

internal fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}