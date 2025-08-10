package com.attyran.compass.sensor

import org.junit.Assert.assertEquals
import org.junit.Test

class DirectionCalculatorTest {

    @Test
    fun `test direction calculation for North`() {
        val directions = listOf(0, 22, 338, 360)
        directions.forEach { degrees ->
            val expectedDirection = "N"
            assertEquals("Direction for $degrees degrees should be $expectedDirection", 
                expectedDirection, calculateDirection(degrees))
        }
    }

    @Test
    fun `test direction calculation for Northeast`() {
        val directions = listOf(23, 45, 67)
        directions.forEach { degrees ->
            val expectedDirection = "NE"
            assertEquals("Direction for $degrees degrees should be $expectedDirection", 
                expectedDirection, calculateDirection(degrees))
        }
    }

    @Test
    fun `test direction calculation for East`() {
        val directions = listOf(68, 90, 112)
        directions.forEach { degrees ->
            val expectedDirection = "E"
            assertEquals("Direction for $degrees degrees should be $expectedDirection", 
                expectedDirection, calculateDirection(degrees))
        }
    }

    @Test
    fun `test direction calculation for Southeast`() {
        val directions = listOf(113, 135, 157)
        directions.forEach { degrees ->
            val expectedDirection = "SE"
            assertEquals("Direction for $degrees degrees should be $expectedDirection", 
                expectedDirection, calculateDirection(degrees))
        }
    }

    @Test
    fun `test direction calculation for South`() {
        val directions = listOf(158, 180, 202)
        directions.forEach { degrees ->
            val expectedDirection = "S"
            assertEquals("Direction for $degrees degrees should be $expectedDirection", 
                expectedDirection, calculateDirection(degrees))
        }
    }

    @Test
    fun `test direction calculation for Southwest`() {
        val directions = listOf(203, 225, 247)
        directions.forEach { degrees ->
            val expectedDirection = "SW"
            assertEquals("Direction for $degrees degrees should be $expectedDirection", 
                expectedDirection, calculateDirection(degrees))
        }
    }

    @Test
    fun `test direction calculation for West`() {
        val directions = listOf(248, 270, 292)
        directions.forEach { degrees ->
            val expectedDirection = "W"
            assertEquals("Direction for $degrees degrees should be $expectedDirection", 
                expectedDirection, calculateDirection(degrees))
        }
    }

    @Test
    fun `test direction calculation for Northwest`() {
        val directions = listOf(293, 315, 337)
        directions.forEach { degrees ->
            val expectedDirection = "NW"
            assertEquals("Direction for $degrees degrees should be $expectedDirection", 
                expectedDirection, calculateDirection(degrees))
        }
    }

    @Test
    fun `test direction calculation edge cases`() {
        assertEquals("Direction for -1 degrees should be N", "N", calculateDirection(-1))
        assertEquals("Direction for 361 degrees should be N", "N", calculateDirection(361))
        assertEquals("Direction for 720 degrees should be N", "N", calculateDirection(720))
    }

    private fun calculateDirection(degrees: Int): String {
        return when (degrees) {
            in 0..22, in 338..360 -> "N"
            in 23..67 -> "NE"
            in 68..112 -> "E"
            in 113..157 -> "SE"
            in 158..202 -> "S"
            in 203..247 -> "SW"
            in 248..292 -> "W"
            in 293..337 -> "NW"
            else -> "N"
        }
    }
}
