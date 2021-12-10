package aoc2021

import java.io.File

internal val subsystemInput = File("src/main/resources/2021/day10.txt").readLines()
internal val subsystemSample = File("src/main/resources/2021/day10-sample.txt").readLines()

private val openToClose = mapOf(
    '(' to ')',
    '[' to ']',
    '{' to '}',
    '<' to '>',
)

private val illegalCharScores = mapOf(
    ')' to 3,
    ']' to 57,
    '}' to 1197,
    '>' to 25137,
)

private val charScores = mapOf(
    ')' to 1L,
    ']' to 2L,
    '}' to 3L,
    '>' to 4L,
)

fun main() {
    part1(listOf("[({(<(())[]>"))
    part1(listOf("{([(<{}[<>[]}>{[]{[(<()>"))
    part1(subsystemSample)
    part1Alt(subsystemSample)
    part1(subsystemInput)

    println("\n\n")

    part2(listOf("[({(<(())[]>"))
    part2(listOf("<{([{{}}[<[[[<>{}]]]>[]]"))
    part2(subsystemSample)
    part2(subsystemInput)
}

private fun part2(input: List<String>) {
    val rows = input.mapNotNull { row ->
        val root = Node(row[0])
        var currentNode = root
        for (i in 1 until row.length) {
            val char = row[i]
            val node = Node(char)
            if (openToClose.containsKey(char)) { // Open char e.g. '('
                currentNode.addChild(node)
                currentNode = node
            } else {
                val parent = currentNode.parent ?: return@mapNotNull null
                parent.addChild(node)
                currentNode = parent
            }
        }

        // Exclude invalid rows
        val missingCloseChars = root.missingCloseChars() ?: return@mapNotNull null

        missingCloseChars.map { charScores[it]!! }
            .reduce { acc, i -> acc * 5L + i }
    }.sorted().toList()

    println("part 2 middle score: ${rows[rows.size / 2]}")
}

// Checks the node and all children below are valid, returns first non-valid char
private fun Node<Char>.findFirstNonValidChar(): Char? {
    openToClose.forEach { (openChar, closeChar) ->
        val opens = children.filter { it.value == openChar }
        val closes = children.filter { it.value == closeChar }
        if (closes.size > opens.size) {
            return closeChar
        }
    }

    for (c in children) {
        // any close brackets without an open?
        c.findFirstNonValidChar()?.let { return it }
    }
    return null
}

private fun part1(input: List<String>) {
    val score = input.mapNotNull { row ->
        val root = Node(row[0])
        var currentNode = root
        for (i in 1 until row.length) {
            val char = row[i]
            val node = Node(char)
            if (openToClose.containsKey(char)) { // Open char e.g. '('
                currentNode.addChild(node)
                currentNode = node
            } else {
                val parent = currentNode.parent ?: break
                parent.addChild(node)
                currentNode = parent
            }
        }
        root.findFirstNonValidChar()
    }.sumOf { illegalCharScores[it]!! }

    println("part1 score: $score")
}

private fun Node<Char>.missingCloseChars(): List<Char>? {
    if (children.isEmpty()) return emptyList()

    val chars = mutableListOf<Char>()
    children.forEach { c ->
        val list = c.missingCloseChars() ?: return null
        chars.addAll(list)
    }
    openToClose.forEach { (openChar, closeChar) ->
        val opens = children.filter { it.value == openChar }
        val closes = children.filter { it.value == closeChar }
        if (closes.size > opens.size) {
            // Invalid row
            return null
        }
        if (opens.size > closes.size) {
            chars.add(closeChar)
        }
    }

    if (parent == null) {
        chars.add(openToClose[value]!!)
    }
    return chars
}

private fun <T> Node<T>.printTree() {
    for (c in children) {
        c.printTree()
    }
    print("$this")
}

class Node<T>(var value: T) {
    var parent: Node<T>? = null
    var children: MutableList<Node<T>> = mutableListOf()

    fun addChild(node: Node<T>) {
        children.add(node)
        node.parent = this
    }

    override fun toString(): String {
        return "$value"
    }
}

/// ALT

private fun part1Alt(input: List<String>) {
    val firstIncorrectClosingChar = mutableListOf<Char>()

    for (row in input) {
        val stack = mutableListOf<Char>()
        for (it in row) {
            // If char is a close, it should be of same type as top of stack
            val isOpen = it == '(' || it == '[' || it == '{' || it == '<'
            if (isOpen) {
                stack.push(it)
            } else {
                if (it != openToClose[stack.peek()]) {
                    firstIncorrectClosingChar.add(it)
                    break
                } else {
                    stack.pop()
                }
            }
        }
    }

    val errorScore = firstIncorrectClosingChar.sumOf { illegalCharScores[it]!! }
    println("part1 alt score: $errorScore")
}

fun <T> MutableList<T>.push(item: T) = this.add(this.count(), item)
fun <T> MutableList<T>.pop(): T? = if (this.isNotEmpty()) this.removeAt(this.count() - 1) else null
fun <T> MutableList<T>.peek(): T? = if (this.isNotEmpty()) this[this.count() - 1] else null