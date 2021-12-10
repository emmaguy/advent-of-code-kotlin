package aoc2021

import java.io.File

internal val subsystemInput = File("src/main/resources/2021/day10.txt").readLines()
internal val subsystemSample = File("src/main/resources/2021/day10-sample.txt").readLines()

fun main() {
    part1(listOf("[({(<(())[]>"))
    part1(listOf("{([(<{}[<>[]}>{[]{[(<()>"))
    part1(subsystemSample)
    part1(subsystemInput)
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
