package aoc2019

import java.io.File
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val inputAsteroidField = File("input/2019/day10-1.txt").readLines()
        .mapIndexed { rowIndex, row ->
            row.mapIndexed { columnIndex, character ->
                if (character == '#') {
                    Asteroid(columnIndex.toDouble(), rowIndex.toDouble())
                } else {
                    null
                }
            }.filterNotNull()
        }.flatten()
        .toSet()

    val asteroidForStation = part1(inputAsteroidField)
    val exploded200th = part2(asteroidForStation, inputAsteroidField)

    println(exploded200th)
}

private fun part2(
    asteroidForStation: Asteroid,
    inputAsteroidField: Set<Asteroid>
): Asteroid {
    val asteroidsAroundStation = anglesAndDistances(
        asteroidForStation, inputAsteroidField.filter { it != asteroidForStation }
    )
        .sortedBy { it.angle }
        .groupByTo(mutableMapOf()) { it.angle }

    asteroidsAroundStation.forEach { entry ->
        // Sort list per angle, by distance
        entry.value.sortBy { it.distance }
    }

    var count = 1
    while (true) {
        asteroidsAroundStation.entries.forEach { (angle, asteroids) ->
            val asteroidInfo = asteroids.removeAt(0)
            println("exploded asteroid: $asteroidInfo at angle: $angle number $count")
            if (count == 200) {
                return asteroidInfo.asteroid
            }
            count++
        }
    }
}

private fun part1(inputAsteroidField: Set<Asteroid>): Asteroid {
    val distances = inputAsteroidField
        .map { asteroid -> Pair(asteroid, inputAsteroidField.filter { it != asteroid }) }
        .map { (asteroid, asteroids) ->
            val map = anglesAndDistances(asteroid, asteroids)
                .groupBy { (slope, _) -> slope } // Group by the angle as only one can be visible on line of sight
                .toMap()
            Pair(asteroid, map.size)
        }
        .sortedBy { it.second } // Sort by the number of asteroids that can be seen

    val pair = distances.last()
    println(pair)
    return pair.first
}

private fun anglesAndDistances(
    asteroid: Asteroid,
    asteroidField: List<Asteroid>
): List<AsteroidInfo> {
    return asteroidField.map { otherAsteroid ->
        // Calculate the angle to each asteroid and the distance to it
        val angle = atan2(otherAsteroid.y - asteroid.y, otherAsteroid.x - asteroid.x).toDegrees()
        val distance = sqrt((otherAsteroid.x - asteroid.x).pow(2.0) + (otherAsteroid.y - asteroid.y).pow(2.0))
        AsteroidInfo(angle, distance, otherAsteroid)
    }
}

private fun Double.toDegrees(): Double {
    var toDegrees = Math.toDegrees(this) + 90 // rotate by 90 degrees for part 2 so we start from the top
    if (toDegrees < 0) {
        toDegrees += 360
    }
    return toDegrees
}

data class AsteroidInfo(val angle: Double, val distance: Double, val asteroid: Asteroid)
data class Asteroid(val x: Double, val y: Double)
