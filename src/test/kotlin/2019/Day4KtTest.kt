package `2019`

import org.junit.Assert.*
import org.junit.Test

class Day4KtTest {

    @Test
    fun `double recurring digit, valid`() {
        assertTrue(isValid(number = 122))
    }

    @Test
    fun `no double recurring digit, not valid`() {
        assertFalse(isValid(number = 123))
    }

    @Test
    fun `double digits only part of bigger group, not valid`() {
        assertFalse(isValid(number = 1112))
    }

    @Test
    fun `double digits part of bigger group with another double, valid`() {
        assertTrue(isValid(number = 11122))
    }

    @Test
    fun `digits that decrease left to right, valid`() {
        assertTrue(isValid(number = 11223))
    }

    @Test
    fun `digits that increase, not valid`() {
        assertFalse(isValid(number = 6557))
    }
}