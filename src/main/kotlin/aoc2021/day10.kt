package aoc2021

import java.io.File

internal val subsystemInput = File("src/main/resources/2021/day10.txt").readLines()
internal val subsystemSample = File("src/main/resources/2021/day10-sample.txt").readLines()

fun main() {
    part1(listOf("[({(<(())[]>"))
    part1(listOf("{([(<{}[<>[]}>{[]{[(<()>"))
    part1(subsystemSample)
    part1(subsystemInput)

    part2(subsystemInput)
}

private fun part2(input: List<String>) {
    val rows = input.mapNotNull { row ->
        println(row)

        val root = Node(row[0])
        var currentNode = root
        for (i in 1 until row.length) {
            val char = row[i]
            val node = Node(char)
            if (char == '(' || char == '{' || char == '[' || char == '<') {
                currentNode.addChild(node)
                currentNode = node
            } else if (char == ')' || char == '}' || char == ']' || char == '>') {
                val parent = currentNode.parent ?: return@mapNotNull null
                parent.addChild(node)
                currentNode = parent
            }
        }
        root.printTree()
        println()
        print("missing closes: ")
        val missingCloses = root.missingCloses() ?: return@mapNotNull null
        val total = missingCloses
            .map {
                print("$it")
                when (it) {
                    ')' -> 1L
                    ']' -> 2L
                    '}' -> 3L
                    '>' -> 4L
                    else -> throw RuntimeException("invalid char: $it")
                }
            }
            .reduce { acc, i -> acc * 5L + i }

        println()
        println("total: $total")
        total
    }.sorted().toList()

    println("$rows")
    println("half: ${rows[rows.size / 2]}")
}

private fun Node<Char>.isValid(): Char? {
    closeCharOrNull(openChar = '(', closeChar = ')', children)?.let { return it }
    closeCharOrNull(openChar = '[', closeChar = ']', children)?.let { return it }
    closeCharOrNull(openChar = '{', closeChar = '}', children)?.let { return it }
    closeCharOrNull(openChar = '<', closeChar = '>', children)?.let { return it }

    for (c in children) {
        // any close brackets without an open?
        c.isValid()?.let { return it }
    }
    return null
}

private fun part1(input: List<String>) {
    val groups = input.mapNotNull { row ->
        println(row)

        val root = Node(row[0])
        var currentNode = root
        for (i in 1 until row.length) {
            val char = row[i]
            val node = Node(char)
            if (char == '(' || char == '{' || char == '[' || char == '<') {
                currentNode.addChild(node)
                currentNode = node
            } else if (char == ')' || char == '}' || char == ']' || char == '>') {
                val parent = currentNode.parent ?: break
                parent.addChild(node)
                currentNode = parent
            }
        }
        root.printTree()

        val firstInvalidChar = root.isValid()
        println("\n\nfirstInvalidChar: $firstInvalidChar")
        firstInvalidChar
    }
        .map {
            if (it == ')') 3
            else if (it == ']') 57
            else if (it == '}') 1197
            else if (it == '>') 25137
            else throw RuntimeException("invalid char: $it")
        }.sum()
    println("$groups")
}

private fun Node<Char>.missingCloses(): List<Char>? {
    if (children.isEmpty()) return emptyList()
    val chars = mutableListOf<Char>()
    children.forEach { c ->
        val list = c.missingCloses() ?: return null
        chars.addAll(list)
    }

    // exclude corrupt lines
    closeCharOrNull(openChar = '(', closeChar = ')', children)?.let { return null }
    closeCharOrNull(openChar = '[', closeChar = ']', children)?.let { return null }
    closeCharOrNull(openChar = '{', closeChar = '}', children)?.let { return null }
    closeCharOrNull(openChar = '<', closeChar = '>', children)?.let { return null }

    openCharOrNull(openChar = '[', closeChar = ']', children)?.let { chars.add(it) }
    openCharOrNull(openChar = '(', closeChar = ')', children)?.let { chars.add(it) }
    openCharOrNull(openChar = '{', closeChar = '}', children)?.let { chars.add(it) }
    openCharOrNull(openChar = '<', closeChar = '>', children)?.let { chars.add(it) }

    if (parent == null) {
        if (value == '(') chars.add(')')
        if (value == '[') chars.add(']')
        if (value == '{') chars.add('}')
        if (value == '<') chars.add('>')
    }

    return chars
}

private fun openCharOrNull(openChar: Char, closeChar: Char, children: MutableList<Node<Char>>): Char? {
    val opens = children.filter { it.value == openChar }
    val closes = children.filter { it.value == closeChar }
    if (opens.size > closes.size) {
        return closeChar
    }
    return null
}

private fun closeCharOrNull(openChar: Char, closeChar: Char, children: MutableList<Node<Char>>): Char? {
    val opens = children.filter { it.value == openChar }
    val closes = children.filter { it.value == closeChar }
    if (closes.size > opens.size) {
        return closeChar
    }
    return null
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
private val openToClose = mapOf(
    '(' to ')',
    '[' to ']',
    '{' to '}',
    '<' to '>',
)

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
                    println("invalid row $it, peek: ${stack.peek()}")
                    firstIncorrectClosingChar.add(it)
                    break
                } else {
                    stack.pop()
                }
            }
        }
    }

    val errorScore = firstIncorrectClosingChar
        .map {
            when (it) {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> throw RuntimeException("invalid char: $it")
            }
        }.sum()
    println("$errorScore")
}

fun <T> MutableList<T>.push(item: T) = this.add(this.count(), item)
fun <T> MutableList<T>.pop(): T? = if (this.isNotEmpty()) this.removeAt(this.count() - 1) else null
fun <T> MutableList<T>.peek(): T? = if (this.isNotEmpty()) this[this.count() - 1] else null