package aoc2021

import org.junit.Test
import kotlin.test.assertEquals

class Day5Test {

    @Test fun `x changes positive`() {
        val input = listOf("1,1 -> 3,1").toInput(includeDiagonals = false)
        assertEquals(input, listOf(1 to 1, 2 to 1, 3 to 1))
    }

    @Test fun `y changes positive`() {
        val input = listOf("1,1 -> 1,3").toInput(includeDiagonals = false)
        assertEquals(input, listOf(1 to 1, 1 to 2, 1 to 3))
    }

    @Test fun `x changes negative`() {
        val input = listOf("9,7 -> 7,7").toInput(includeDiagonals = false)
        assertEquals(input, listOf(7 to 7, 8 to 7, 9 to 7))
    }

    @Test fun `y changes negative`() {
        val input = listOf("7,9 -> 7,7").toInput(includeDiagonals = false)
        assertEquals(input, listOf(7 to 7, 7 to 8, 7 to 9))
    }

    @Test fun `ignore equal`() {
        val input = listOf("8,0 -> 0,8").toInput(includeDiagonals = false)
        assertEquals(input, emptyList())
    }

    @Test fun `vertical increasing`() {
        val input = listOf("1,1 -> 3,3").toInput(includeDiagonals = true)
        assertEquals(input, listOf(1 to 1, 2 to 2, 3 to 3))
    }

    @Test fun `vertical decreasing`() {
        val input = listOf("9,7 -> 7,9").toInput(includeDiagonals = true)
        assertEquals(input, listOf(9 to 7, 8 to 8, 7 to 9))
    }
}