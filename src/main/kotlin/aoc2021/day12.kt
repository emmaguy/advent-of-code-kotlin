package aoc2021

import java.io.File

internal val caveMapInput = File("src/main/resources/2021/day12.txt").readLines()
internal val caveMapSample = File("src/main/resources/2021/day12-sample.txt").readLines()

fun main() {
    part1(parseGraph(caveMapSample))
//    part1(parseGraph(caveMapInput))

    part2(parseGraph(caveMapSample))
//    part2(parseGraph(caveMapInput))
}

private fun part1(graph: Map<String, List<String>>) {
    val allPaths = dfs(graph, cave = "start", emptyList())
    allPaths.forEach { println("path: ${it.joinToString(",")}") }

    val count = allPaths.count { it.any { cave -> cave.isSmallCave() } }
    println("small caves visited: $count\n")
}

private fun part2(graph: Map<String, List<String>>) {
    val allPaths = dfsPart2(graph, cave = "start", emptyList())
    allPaths.forEach { println("path: ${it.joinToString(",")}") }

    println("count paths: ${allPaths.count()}\n")
}

private fun parseGraph(input: List<String>): Map<String, List<String>> {
    // A-b & b-A
    val forwardPaths = input.map { it.split("-")[0] to it.split("-")[1] }
    val reversePaths = input.map { it.split("-")[1] to it.split("-")[0] }

    return (forwardPaths + reversePaths)
        .groupBy({ it.first }, { it.second })
}

private fun dfsPart2(
    graph: Map<String, List<String>>,
    cave: String,
    visited: List<String>,
): List<List<String>> {
    if (cave == "end") {
        return listOf(visited + cave)
    }

    return graph[cave]!!
        .filter { nextCave -> nextCave !in visited + cave || nextCave.isBigCave() }
        .flatMap { nextCave -> dfs(graph, cave = nextCave, visited = visited + cave) }
}

private fun dfs(
    graph: Map<String, List<String>>,
    cave: String,
    visited: List<String>,
): List<List<String>> {
    if (cave == "end") {
        return listOf(visited + cave)
    }

    return graph[cave]!!
        .filter { nextCave -> nextCave !in visited + cave || nextCave.isBigCave() }
        .flatMap { nextCave -> dfs(graph, cave = nextCave, visited = visited + cave) }
}

private fun String.isSmallCave(): Boolean {
    return first().isLowerCase()
}

private fun String.isBigCave(): Boolean {
    return first().isUpperCase()
}
