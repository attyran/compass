package com.attyran.compass.sensor

import org.junit.Assert.assertEquals
import org.junit.Test

class UnitConversionTest {

    @Test
    fun `test metersToFeet conversion with zero meters`() {
        val meters = 0.0
        val expectedFeet = 0.0
        val actualFeet = metersToFeet(meters)
        
        assertEquals(expectedFeet, actualFeet, 0.001)
    }

    @Test
    fun `test metersToFeet conversion with positive meters`() {
        val meters = 100.0
        val expectedFeet = 328.084
        val actualFeet = metersToFeet(meters)
        
        assertEquals(expectedFeet, actualFeet, 0.001)
    }

    @Test
    fun `test metersToFeet conversion with negative meters`() {
        val meters = -50.0
        val expectedFeet = -164.042
        val actualFeet = metersToFeet(meters)
        
        assertEquals(expectedFeet, actualFeet, 0.001)
    }

    @Test
    fun `test metersToFeet conversion with decimal meters`() {
        val meters = 1.5
        val expectedFeet = 4.92126
        val actualFeet = metersToFeet(meters)
        
        assertEquals(expectedFeet, actualFeet, 0.001)
    }

    @Test
    fun `test metersToFeet conversion with large value`() {
        val meters = 10000.0
        val expectedFeet = 32808.4
        val actualFeet = metersToFeet(meters)
        
        assertEquals(expectedFeet, actualFeet, 0.1)
    }

    @Test
    fun `test metersToFeet conversion with small value`() {
        val meters = 0.1
        val expectedFeet = 0.328084
        val actualFeet = metersToFeet(meters)
        
        assertEquals(expectedFeet, actualFeet, 0.0001)
    }

    private fun metersToFeet(meters: Double): Double {
        return meters * 3.28084
    }
}
