package `2019`

import org.junit.Assert.*
import org.junit.Test

class IntcodeComputerTest {

    @Test fun `can add in memory`() {
        val memory = arrayOf(1L, 0, 0, 0, 99)
        IntcodeComputer(memory = memory, inputList = mutableListOf()).apply { compute() }

        assertArrayEquals(arrayOf(2L, 0, 0, 0, 99), memory)
    }

    @Test fun `can multiply in memory`() {
        val memory = arrayOf(2L, 3, 0, 3, 99)
        IntcodeComputer(memory = memory, inputList = mutableListOf()).apply { compute() }

        assertArrayEquals(arrayOf(2L, 3, 0, 6, 99), memory)
    }

    @Test fun `can do basic operations in memory`() {
        val memory = arrayOf(1L, 1, 1, 4, 99, 5, 6, 0, 99)
        IntcodeComputer(memory = memory, inputList = mutableListOf()).apply { compute() }

        assertArrayEquals(arrayOf(30L, 1, 1, 4, 2, 5, 6, 0, 99), memory)
    }

    @Test fun `can input and output a value`() {
        val memory = arrayOf(3L, 0, 4, 0, 99)
        val input = 2L
        val computer = IntcodeComputer(memory = memory, inputList = mutableListOf(input)).apply { compute() }

        assertEquals(computer.output(), listOf(input))
    }

    @Test fun `can handle position mode`() {
        val memory = arrayOf(1002L, 4, 3, 4, 33)
        IntcodeComputer(memory = memory, inputList = mutableListOf()).apply { compute() }

        assertArrayEquals(arrayOf(1002L, 4, 3, 4, 99), memory)
    }

    @Test fun `position mode input equal to 8`() {
        val memory = arrayOf(3L, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8)
        val computer = IntcodeComputer(memory = memory, inputList = mutableListOf(8)).apply { compute() }

        assertEquals(computer.output(), listOf(1L))
    }

    @Test fun `position mode input not equal to 8`() {
        val memory = arrayOf(3L, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8)
        val computer = IntcodeComputer(memory = memory, inputList = mutableListOf(10)).apply { compute() }

        assertEquals(computer.output(), listOf(0L))
    }

    @Test fun `position mode input less than 8`() {
        val memory = arrayOf(3L, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8)
        val computer = IntcodeComputer(memory = memory, inputList = mutableListOf(6)).apply { compute() }

        assertEquals(computer.output(), listOf(1L))
    }

    @Test fun `position mode input not less than 8`() {
        val memory = arrayOf(3L, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8)
        val computer = IntcodeComputer(memory = memory, inputList = mutableListOf(10)).apply { compute() }

        assertEquals(computer.output(), listOf(0L))
    }

    @Test fun `immediate mode input equal to 8`() {
        val memory = arrayOf(3L, 3, 1108, -1, 8, 3, 4, 3, 99)
        val computer = IntcodeComputer(memory = memory, inputList = mutableListOf(8)).apply { compute() }

        assertEquals(computer.output(), listOf(1L))
    }

    @Test fun `immediate mode input not equal to 8`() {
        val memory = arrayOf(3L, 3, 1108, -1, 8, 3, 4, 3, 99)
        val computer = IntcodeComputer(memory = memory, inputList = mutableListOf(10)).apply { compute() }

        assertEquals(computer.output(), listOf(0L))
    }

    @Test fun `immediate mode input less than 8`() {
        val memory = arrayOf(3L, 3, 1107, -1, 8, 3, 4, 3, 99)
        val computer = IntcodeComputer(memory = memory, inputList = mutableListOf(6)).apply { compute() }

        assertEquals(computer.output(), listOf(1L))
    }

    @Test fun `immediate mode input not less than 8`() {
        val memory = arrayOf(3L, 3, 1107, -1, 8, 3, 4, 3, 99)
        val computer = IntcodeComputer(memory = memory, inputList = mutableListOf(10)).apply { compute() }

        assertEquals(computer.output(), listOf(0L))
    }

    @Test fun `position mode input was zero`() {
        val memory = arrayOf(3L, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9)
        val computer = IntcodeComputer(memory = memory, inputList = mutableListOf(0)).apply { compute() }

        assertEquals(computer.output(), listOf(0L))
    }

    @Test fun `position mode input was not zero`() {
        val memory = arrayOf(3L, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9)
        val computer = IntcodeComputer(memory = memory, inputList = mutableListOf(6)).apply { compute() }

        assertEquals(computer.output(), listOf(1L))
    }

    @Test fun `immediate mode input was zero`() {
        val memory = arrayOf(3L, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1)
        val computer = IntcodeComputer(memory = memory, inputList = mutableListOf(0)).apply { compute() }

        assertEquals(computer.output(), listOf(0L))
    }

    @Test fun `immediate mode input was not zero`() {
        val memory = arrayOf(3L, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1)
        val computer = IntcodeComputer(memory = memory, inputList = mutableListOf(3)).apply { compute() }

        assertEquals(computer.output(), listOf(1L))
    }
}