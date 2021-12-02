package `2015`

import junit.framework.Assert.assertEquals
import org.junit.Test

class Day2Test {

    @Test fun example1() {
        assertEquals(58, wrappingPaperRequired("2x3x4"))
    }

    @Test fun example2() {
        assertEquals(43, wrappingPaperRequired("1x1x10"))
    }

    @Test fun `example1 part2`() {
        assertEquals(34, ribbonRequired("2x3x4"))
    }

    @Test fun `example2 part2`() {
        assertEquals(14, ribbonRequired("1x1x10"))
    }
}