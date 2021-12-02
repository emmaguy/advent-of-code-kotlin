package `2015`

import junit.framework.Assert.assertEquals
import org.junit.Test

class Day4Test {

    @Test fun `calculate secret key hash correctly`() {
        assertEquals("000001dbbfa3a5c83a2d506429c7b00e", calculateSecretKeyHash("abcdef", 609043))
    }

}