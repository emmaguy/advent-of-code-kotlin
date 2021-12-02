package `2019`

import java.io.File

fun main() {
    val input = File("input/2019/day8.txt").readLines().first()
    val layers = parseImage(input, 25, 6)
//    val layers = parseImage("0222112222120000", 2, 2)

    val finalImage = mutableListOf<String>()

    for (pixel in layers.first().indices) {
        val currentPixels = layers.findPixelsOnAllLayersAtIndex(pixel)
        val finalPixel = currentPixels.findVisibleColourForPixel()

        finalImage.add(finalPixel)
    }

    var index = 0
    for (i in 0 until 6) {
        for (j in 0 until 25) {
            print(if (finalImage[index++] == "1") "1" else " ")
        }
        print("\n")
    }
//    println("final image: ${finalImage.joinToString("")}")
}

fun List<List<String>>.findPixelsOnAllLayersAtIndex(index: Int): List<String> {
    return map { it[index] }
}

fun List<String>.findVisibleColourForPixel(): String {
    return find { it == "0" || it == "1" }!!
}

// 0 = black
// 1 = white
// 2 = transparent

private fun part1(layers: List<List<String>>) {
    val (count, index, result) = layers.mapIndexed { index, list ->
        val count = list.count { it == "0" }
        Triple(count, index, list.count { it == "1" } * list.count { it == "2" })
    }.sortedBy { it.first }.first()

    println("with most 0, index: $index with $count, result: $result")
}

fun parseImage(imageData: String, imageWidth: Int, imageHeight: Int): List<List<String>> {
    val numberOfLayers = imageData.length / (imageWidth * imageHeight)
    val layers = mutableListOf<List<String>>()
    var index = 0
    for (layer in 0 until numberOfLayers) {
        val list = mutableListOf<String>()
        for (width in 0 until imageWidth) {
            for (height in 0 until imageHeight) {
                list.add(imageData[index].toString())
                index++
            }
        }
        layers.add(list)
    }

    return layers
}
