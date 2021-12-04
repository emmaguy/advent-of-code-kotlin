package `2021`.day4

import java.io.File

internal val bingoSample = File("src/main/resources/2021/day4-sample.txt")
    .readLines()
    .toBingoInput()
internal val bingoBoards = File("src/main/resources/2021/day4.txt")
    .readLines()
    .toBingoInput()
internal const val bingoBoardSize = 5

fun main() {
    val (numbers, boards) = bingoBoards

    part2(numbers, boards)
}

internal fun part2(numbers: List<Int>, boards: List<BingoBoard>) {
    val (winningBoard, calledNumber) = playBingo(numbers, boards)

    // Calculate score
    val unmarkedNumbers = winningBoard.rows.flatten().filter { !it.isMarked }.sumOf { it.number }
    println("unmarkedNumbers: $unmarkedNumbers, total: ${calledNumber * unmarkedNumbers}")
}

internal fun playBingo(numbers: List<Int>, boards: List<BingoBoard>): Pair<BingoBoard, Int> {
    val wonBoards = mutableSetOf<BingoBoard>()
    numbers.forEach { calledNumber ->
        for (board in boards) {
            if (!board.hasWon()) {
                for ((rowIndex, row) in board.rows.withIndex()) {
                    for ((columnIndex, item) in row.withIndex()) {
                        if (item.number == calledNumber) {
                            item.isMarked = true
                            board.markedRows[rowIndex] = (board.markedRows[rowIndex] ?: 0) + 1
                            board.markedColumns[columnIndex] = (board.markedColumns[columnIndex] ?: 0) + 1
                        }
                    }
                }
            }
            if (board.hasWon()) {
                println("bingo! (on number $calledNumber)")
                wonBoards.add(board)
                if (wonBoards.size == boards.size) {
                    return Pair(board, calledNumber)
                }
            }
        }
    }
    throw RuntimeException("No winners :(")
}

data class BingoBoard(
    val rows: List<List<BingoItem>>,
    val markedRows: MutableMap<Int, Int>,
    val markedColumns: MutableMap<Int, Int>,
) {
    fun hasWon(): Boolean {
        return markedColumns.any { it.value == bingoBoardSize } || markedRows.any { it.value == bingoBoardSize }
    }
}

data class BingoItem(val number: Int, var isMarked: Boolean)

private fun List<String>.toBingoInput(): Pair<List<Int>, List<BingoBoard>> {
    val numbers = mutableListOf<Int>()
    val boards = mutableListOf<BingoBoard>()
    val rows = mutableListOf<List<BingoItem>>()

    forEachIndexed { index, line ->
        if (index == 0) {
            numbers.addAll(line.split(",").map { it.toInt() })
        } else {
            if (line.isEmpty()) {
                // do nothing
            } else {
                val currentRow = line.split(" ")
                    .filter { it.isNotBlank() }
                    .map { BingoItem(it.toInt(), isMarked = false) }

                rows.add(currentRow)
                if (rows.size >= bingoBoardSize) {
                    boards.add(
                        BingoBoard(
                            rows = rows.toList(),
                            markedRows = mutableMapOf(),
                            markedColumns = mutableMapOf(),
                        )
                    )
                    rows.clear()
                }
            }
        }
    }

    return Pair(numbers, boards)
}