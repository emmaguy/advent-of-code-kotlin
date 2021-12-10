package aoc2019

fun main() {
    val result = calculateSteps(
        wire1 = listOf("R8", "U5", "L5", "D3"),
        wire2 = listOf("U7", "R6", "D4", "L4")
    )
    print("result: ${result}\n")
}

fun calculateSteps(wire1: List<String>, wire2: List<String>): Int {
    val coordWire1 = CoordinateXY(0, 0)
    val coordWire2 = CoordinateXY(0, 0)

    wire1.forEach { coordWire1.move(it) }
    wire2.forEach { coordWire2.move(it) }

    val intersection = coordWire1.pointsVisited
        .map { it.first }
        .intersect(coordWire2.pointsVisited.map { it.first })
        .toMutableSet()

    intersection.remove(CoordinateXY(0, 0))
    intersection.forEach { print("$it\n") }

    val pointsFrom1 = coordWire1.pointsVisited.filter { intersection.contains(it.first) }
    val pointsFrom2 = coordWire2.pointsVisited.filter { intersection.contains(it.first) }

    print("1:\n")
    pointsFrom1.forEach { print("${it.first}, ${it.second}\n") }
    print("2:\n")
    pointsFrom2.forEach { print("${it.first}, ${it.second}\n") }

    val steps = mutableListOf<Int>()
    for (x in pointsFrom1) {
        val y = pointsFrom2.find { it.first == x.first }!!
        steps.add(x.second + y.second)
    }
    steps.sort()

    return steps.first()
}

internal data class CoordinateXY(var x: Int, var y: Int) {
    val pointsVisited = mutableSetOf<Pair<CoordinateXY, Int>>()

    private var stepsTaken = 1

    fun move(input: String) {
        var isFirstMove = true
        val directionString = input.first()
        val distance = input.drop(1).toInt()
        when (directionString) {
            'U' -> {
                for (i in 0..distance) {
                    pointsVisited.add(Pair(CoordinateXY(x + i, y), stepsTaken))
                    if (isFirstMove) {
                        isFirstMove = false
                    } else {
                        stepsTaken++
                    }
                }
                x += distance
            }
            'D' -> {
                for (i in 0..distance) {
                    pointsVisited.add(Pair(CoordinateXY(x - i, y), stepsTaken))
                    if (isFirstMove) {
                        isFirstMove = false
                    } else {
                        stepsTaken++
                    }
                }
                x -= distance
            }
            'L' -> {
                for (i in 0..distance) {
                    pointsVisited.add(Pair(CoordinateXY(x, y - i), stepsTaken))
                    if (isFirstMove) {
                        isFirstMove = false
                    } else {
                        stepsTaken++
                    }
                }
                y -= distance
            }
            'R' -> {
                for (i in 0..distance) {
                    pointsVisited.add(Pair(CoordinateXY(x, y + i), stepsTaken))
                    if (isFirstMove) {
                        isFirstMove = false
                    } else {
                        stepsTaken++
                    }
                }
                y += distance
            }
            else -> throw IllegalArgumentException("Unexpected direction: $directionString")
        }
        print("now at $this, stepsTaken: $stepsTaken\n")
        isFirstMove = true
    }

    override fun toString(): String {
        return "($x, $y)"
    }
}
