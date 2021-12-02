package `2019`

import java.io.File
import kotlin.math.absoluteValue

fun main() {
    val names = listOf("Io", "Europa", "Ganymede", "Callisto")
    val moons = File("input/2019/day12-2.txt").readLines()
        .mapIndexed { index, it ->
            val positions = it.trim('<', '>').split(",")
            val x = positions[0].replace("x=", "").trim()
            val y = positions[1].replace("y=", "").trim()
            val z = positions[2].replace("z=", "").trim()
            Moon(
                position = Coordinate(x = x.toInt(), y = y.toInt(), z = z.toInt()),
                velocity = Coordinate(x = 0, y = 0, z = 0),
                name = names[index]
            )
        }.toSet()

    part1(moons)
}

private fun part1(initialMoons: Set<Moon>) {
    val numberOfSteps = 1000
    var moons = initialMoons.map { it.copy() }.toSet()
    for (i in 0 until numberOfSteps) {
        moons = stepTimeForwards(moons)
        moons.forEach { println(it) }
        val totalEnergy = moons.map {
            (it.position.x.absoluteValue + it.position.y.absoluteValue + it.position.z.absoluteValue) *
                    (it.velocity.x.absoluteValue + it.velocity.y.absoluteValue + it.velocity.z.absoluteValue)
        }.sum()
        println("total energy: $totalEnergy")
        println()
    }
}

private fun stepTimeForwards(moons: Set<Moon>): Set<Moon> {
    val moonPairs = moons.allPairs()
    moonPairs.forEach { (moon1, moon2) ->
        applyVelocityX(moon1, moon2)
        applyVelocityY(moon1, moon2)
        applyVelocityZ(moon1, moon2)
    }

    val moonsAfter = moonPairs.flatMap { listOf(it.first, it.second) }.toSet()
    moonsAfter.forEach {
        it.position.x += it.velocity.x
        it.position.y += it.velocity.y
        it.position.z += it.velocity.z
    }
    return moonsAfter
}

private fun applyVelocityX(moon1: Moon, moon2: Moon) {
    val xDiff = when {
        moon1.position.x > moon2.position.x -> 1
        moon1.position.x < moon2.position.x -> -1
        else -> 0
    }
    moon1.velocity.x -= xDiff
    moon2.velocity.x += xDiff
}

private fun applyVelocityY(moon1: Moon, moon2: Moon) {
    val yDiff = when {
        moon1.position.y > moon2.position.y -> 1
        moon1.position.y < moon2.position.y -> -1
        else -> 0
    }
    moon1.velocity.y -= yDiff
    moon2.velocity.y += yDiff
}

private fun applyVelocityZ(moon1: Moon, moon2: Moon) {
    val zDiff = when {
        moon1.position.z > moon2.position.z -> 1
        moon1.position.z < moon2.position.z -> -1
        else -> 0
    }
    moon1.velocity.z -= zDiff
    moon2.velocity.z += zDiff
}

data class Moon(val name: String, val position: Coordinate, val velocity: Coordinate)

data class Coordinate(var x: Int, var y: Int, var z: Int)

private fun <E> Set<E>.allPairs(): List<Pair<E, E>> {
    val pairs = mutableListOf<Pair<E, E>>()
    val copyOfSet = this.toMutableSet()
    while (copyOfSet.isNotEmpty()) {
        val first = copyOfSet.first()
        copyOfSet.remove(first)
        for (otherItems in copyOfSet) {
            pairs.add(Pair(first, otherItems))
        }
    }
    return pairs
}
