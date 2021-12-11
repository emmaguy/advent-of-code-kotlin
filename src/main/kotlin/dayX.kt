import java.io.File

internal val xInput = File("src/main/resources/2021/dayX.txt").readLines()
internal val xSample = File("src/main/resources/2021/dayX-sample.txt").readLines()

fun main() {

}

private fun part1(input: List<String>) {

}

internal fun List<String>.toWhatever(): List<Int> {
    return first().split(",").map { it.toInt() }
}
