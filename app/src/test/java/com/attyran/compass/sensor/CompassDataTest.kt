package com.attyran.compass.sensor

import org.junit.Assert.assertEquals
import org.junit.Test

class CompassDataTest {

    @Test
    fun `test CompassData properties`() {
        val compassData = CompassData(degrees = 90, direction = "E")
        
        assertEquals(90, compassData.degrees)
        assertEquals("E", compassData.direction)
    }

    @Test
    fun `test CompassData with zero degrees`() {
        val compassData = CompassData(degrees = 0, direction = "N")
        
        assertEquals(0, compassData.degrees)
        assertEquals("N", compassData.direction)
    }

    @Test
    fun `test CompassData with negative degrees`() {
        val compassData = CompassData(degrees = -90, direction = "W")
        
        assertEquals(-90, compassData.degrees)
        assertEquals("W", compassData.direction)
    }

    @Test
    fun `test CompassData with large degrees`() {
        val compassData = CompassData(degrees = 359, direction = "N")
        
        assertEquals(359, compassData.degrees)
        assertEquals("N", compassData.direction)
    }
}
