package `2021`

import org.junit.Assert.*
import org.junit.Test

class day8test {

    @Test fun `from7SegmentDisplay 9`() {
        assertEquals(9, "abcdfg".from7SegmentDisplay())
    }

    @Test fun `from7SegmentDisplay 8`() {
        assertEquals(8, "abcdefg".from7SegmentDisplay())
        assertEquals(8, "acedgfb".from7SegmentDisplay()) // Any order works too
    }

    @Test fun `from7SegmentDisplay 7`() {
        assertEquals(7, "acf".from7SegmentDisplay())
    }

    @Test fun `from7SegmentDisplay 6`() {
        assertEquals(6, "abdefg".from7SegmentDisplay())
    }

    @Test fun `from7SegmentDisplay 5`() {
        assertEquals(5, "abdfg".from7SegmentDisplay())
    }

    @Test fun `from7SegmentDisplay 4`() {
        assertEquals(4, "bcdf".from7SegmentDisplay())
    }

    @Test fun `from7SegmentDisplay 3`() {
        assertEquals(3, "acdfg".from7SegmentDisplay())
    }

    @Test fun `from7SegmentDisplay 2`() {
        assertEquals(2, "acdeg".from7SegmentDisplay())
    }

    @Test fun `from7SegmentDisplay 1`() {
        assertEquals(1, "cf".from7SegmentDisplay())
    }

    @Test fun `from7SegmentDisplay 0`() {
        assertEquals(0, "abcefg".from7SegmentDisplay())
    }
}