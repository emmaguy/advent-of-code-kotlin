package `2021`

import org.junit.Assert.*
import org.junit.Test

class Day82021Test {

    @Test fun `from7SegmentDisplay 9`() {
        assertEquals(9, "cefabd".from7SegmentDisplay())
    }

    @Test fun `from7SegmentDisplay 8`() {
        assertEquals(8, "acedgfb".from7SegmentDisplay())
        assertEquals(8, "acdegfb".from7SegmentDisplay()) // Any order works too
    }

    @Test fun `from7SegmentDisplay 7`() {
        assertEquals(7, "dab".from7SegmentDisplay())
    }

    @Test fun `from7SegmentDisplay 6`() {
        assertEquals(6, "cdfgeb".from7SegmentDisplay())
    }

    @Test fun `from7SegmentDisplay 5`() {
        assertEquals(5, "cdfbe".from7SegmentDisplay())
    }

    @Test fun `from7SegmentDisplay 4`() {
        assertEquals(4, "eafb".from7SegmentDisplay())
    }

    @Test fun `from7SegmentDisplay 3`() {
        assertEquals(3, "fbcad".from7SegmentDisplay())
    }

    @Test fun `from7SegmentDisplay 2`() {
        assertEquals(2, "gcdfa".from7SegmentDisplay())
    }

    @Test fun `from7SegmentDisplay 1`() {
        assertEquals(1, "ab".from7SegmentDisplay())
    }

    @Test fun `from7SegmentDisplay 0`() {
        assertEquals(0, "cagedb".from7SegmentDisplay())
    }
}