import java.io.File

internal val xInput = File("src/main/resources/2021/dayX-sample.txt")
    .readLines()
internal val xSample = File("src/main/resources/2021/dayX.txt")
    .readLines()

fun main() {

}

internal fun List<String>.toWhatever(): List<Int> {
    return first().split(",").map { it.toInt() }
}
