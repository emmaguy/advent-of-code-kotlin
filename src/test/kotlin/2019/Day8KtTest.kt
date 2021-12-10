package aoc2019

import org.junit.Assert.*
import org.junit.Test

class Day8KtTest {

    @Test fun `find pixels on layers at first index`() {
        val list = listOf(
            listOf("0", "1", "2"),
            listOf("0", "1", "2"),
            listOf("0", "1", "2")
        ).findPixelsOnAllLayersAtIndex(0)
        assertEquals(listOf("0", "0", "0"), list)
    }

    @Test fun `find pixels on layers at last index`() {
        val list = listOf(
            listOf("0", "1", "2"),
            listOf("0", "1", "2"),
            listOf("0", "1", "2")
        ).findPixelsOnAllLayersAtIndex(2)
        assertEquals(listOf("2", "2", "2"), list)
    }

    @Test fun `all layers black, return black`() {
        val pixel = listOf("0", "0", "0").findVisibleColourForPixel()
        assertEquals("0", pixel)
    }

    @Test fun `top layer black, rest white, return black`() {
        val pixel = listOf("0", "1", "1").findVisibleColourForPixel()
        assertEquals("0", pixel)
    }

    @Test fun `top layer transparent, rest white, return white`() {
        val pixel = listOf("2", "1", "1").findVisibleColourForPixel()
        assertEquals("1", pixel)
    }

    @Test fun `layers transparent, white then black, return white`() {
        val pixel = listOf("2", "1", "0").findVisibleColourForPixel()
        assertEquals("1", pixel)
    }
}