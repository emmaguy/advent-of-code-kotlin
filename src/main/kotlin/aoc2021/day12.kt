package aoc2021

import java.io.File

internal val caveMapInput = File("src/main/resources/2021/day12.txt").readLines()
internal val caveMapSample = File("src/main/resources/2021/day12-sample.txt").readLines()

fun main() {
    part1(caveMapSample)
}

private fun part1(input: List<String>) {
    // A-b
    val paths = input.map { it.split("-")[0] to it.split("-")[1] }
        .groupBy({ it.first }, { it.second })

    // b-A
    val reversePaths = input.map { it.split("-")[0] to it.split("-")[1] }
        .filter {
            it.second != "end" && it.second != "start" &&
                    it.first != "end" && it.first != "start"
        }
        .groupBy({ it.second }, { it.first })

    val allPaths = paths + reversePaths

    val smallCavesVisited = mutableSetOf<String>()
    visit(currentCave = "start", allPaths, smallCavesVisited)
}

private fun visit(currentCave: String, paths: Map<String, List<String>>, smallCavesVisited: MutableSet<String>) {
    if (currentCave == "end") {
        return
    }

    if (currentCave.isSmallCave()) {
        smallCavesVisited.add(currentCave)
    }

    val possibles = paths[currentCave] ?: emptyList()
    possibles.forEach {
        if (!smallCavesVisited.contains(it)) {
            if (!currentCave.isSmallCave() || (currentCave.isSmallCave() && !it.isSmallCave())) {
                println("$currentCave-$it")
                visit(currentCave = it, paths = paths, smallCavesVisited = smallCavesVisited)
            }
        }
    }
}

private fun String.isSmallCave(): Boolean {
    return length == 1 && first().isLowerCase()
}
