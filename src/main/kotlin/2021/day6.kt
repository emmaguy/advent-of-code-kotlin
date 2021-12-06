package `2021`

import java.io.File

internal val fishSample = File("src/main/resources/2021/day6-sample.txt")
    .readLines()
    .toFish()
internal val fishInput = File("src/main/resources/2021/day6.txt")
    .readLines()
    .toFish()

fun main() {
    calculateSchoolSize(fishInput, days = 256)
}

internal fun calculateSchoolSize(input: List<Laternfish>, days: Int) {
    val fish = mutableListOf(*input.toTypedArray())
    println("initial: ${fish.joinToString(",")}")

    var daysElapsed = 1
    while (daysElapsed <= days) {
        val newFish = mutableListOf<Laternfish>()
        for (f in fish) {
            f.daysTilNewFish--
            if (f.daysTilNewFish < 0) {
                f.daysTilNewFish = 6
                newFish.add(Laternfish(daysTilNewFish = 8))
            }
        }

        fish.addAll(newFish)
        println("after $daysElapsed: ${fish.joinToString(",")}")
        daysElapsed++
    }

    println("${fish.size}")
}

internal fun List<String>.toFish(): List<Laternfish> {
    return first().split(",").map { Laternfish(it.toInt()) }
}

data class Laternfish(var daysTilNewFish: Int) {
    override fun toString(): String {
        return "$daysTilNewFish"
    }
}