package aoc2015

import junit.framework.Assert.assertEquals
import org.junit.Test

class Day1Test {

    @Test fun example1() {
        assertEquals(0, calculateFloor("(())"))
    }

    @Test fun example2() {
        assertEquals(0, calculateFloor("()()"))
    }
}