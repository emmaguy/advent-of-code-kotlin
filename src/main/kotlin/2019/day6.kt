package `2019`
import java.io.File

var total = 0

fun main() {
    val input = File("input/2019/day6.txt").readLines()

    val map = mutableMapOf<String, Object>()

    input.forEach {
        // COM)B = B is in Orbit around COM
        val objects = it.split(")")
        val objectBeingOrbited = getOrCreate(map, objects.first())
        val objectDirectlyOrbiting = getOrCreate(map, objects.last())
        objectBeingOrbited.orbitedBy.add(objectDirectlyOrbiting)
    }

    map.forEach { (_, parent) ->
        parent.orbitedBy.forEach { it.parent = parent }
    }

    // find all parents of both
    // find the first parent which is common
    val parentsOfYou = allParentsOf(map["YOU"]!!.parent!!)
    val parentsOfSanta = allParentsOf(map["SAN"]!!.parent!!)

    var total = 0
    for (parentOfYou in parentsOfYou) {
        val find = parentsOfSanta.find { it.first == parentOfYou.first }
        if (find != null) {
            total = parentOfYou.second + find.second
            break
        }
    }

    println("$total")

}

fun allParentsOf(objectSantaIsOrbiting: Object): List<Pair<Object, Int>> {
    val list = mutableListOf<Pair<Object, Int>>()
    var currentObject = objectSantaIsOrbiting
    var index = 0
    while (true) {
        list.add(Pair(currentObject, index++))
        if (currentObject.parent != null) {
            currentObject = currentObject.parent!!
        } else {
            break
        }
    }
    return list
}

private fun part1(map: MutableMap<String, Object>) {
    val root = map["COM"]!!
    visit(root, 0)
    print("total: $total\n")
}

fun getOrCreate(map: MutableMap<String, Object>, objectName: String): Object {
    if (!map.containsKey(objectName)) {
        map[objectName] = Object(objectName)
    }
    return map[objectName]!!
}

fun visit(obj: Object, level: Int): Int {
    total += level
    return obj.orbitedBy.map { visit(it, level + 1) }.sum()
}

data class Object(val name: String) {
    var parent: Object? = null
    val orbitedBy = mutableListOf<Object>()
}
