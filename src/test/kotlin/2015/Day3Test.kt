package aoc2015

import junit.framework.Assert.assertEquals
import org.junit.Test

class Day3Test {

    @Test fun example1() {
        assertEquals(2, housesThatReceiveAtLeastOnePresent(">"))
    }

    @Test fun example2() {
        assertEquals(4, housesThatReceiveAtLeastOnePresent("^>v<"))
    }

    @Test fun example3() {
        assertEquals(2, housesThatReceiveAtLeastOnePresent("^v^v^v^v^v"))
    }

    @Test fun `part2 example1`() {
        assertEquals(3, part2("^v"))
    }

    @Test fun `part2 example2`() {
        assertEquals(3, part2("^>v<"))
    }

    @Test fun `part2 example3`() {
        assertEquals(11, part2("^v^v^v^v^v"))
    }
}