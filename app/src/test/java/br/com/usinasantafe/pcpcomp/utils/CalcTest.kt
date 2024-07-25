package br.com.usinasantafe.pcpcomp.utils

import org.junit.Assert.*

import org.junit.Test

class CalcTest {

    @Test
    fun `Test calc`() {
        val result = porc(1f, 3f)
        assertEquals(result, 0.33333334f)
    }
}