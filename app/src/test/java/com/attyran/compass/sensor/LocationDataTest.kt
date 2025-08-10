package com.attyran.compass.sensor

import org.junit.Assert.assertEquals
import org.junit.Test

class LocationDataTest {

    @Test
    fun `test LocationData with all properties`() {
        val locationData = LocationData(
            latitude = 37.7749,
            longitude = -122.4194,
            elevation = 100.0,
            address = "San Francisco, CA"
        )
        
        assertEquals(37.7749, locationData.latitude, 0.001)
        assertEquals(-122.4194, locationData.longitude, 0.001)
        assertEquals(100.0, locationData.elevation, 0.001)
        assertEquals("San Francisco, CA", locationData.address)
    }

    @Test
    fun `test LocationData default values`() {
        val locationData = LocationData()
        
        assertEquals(0.0, locationData.latitude, 0.001)
        assertEquals(0.0, locationData.longitude, 0.001)
        assertEquals(0.0, locationData.elevation, 0.001)
        assertEquals("Location unavailable", locationData.address)
    }

    @Test
    fun `test LocationData with zero coordinates`() {
        val locationData = LocationData(
            latitude = 0.0,
            longitude = 0.0,
            elevation = 0.0,
            address = "Null Island"
        )
        
        assertEquals(0.0, locationData.latitude, 0.001)
        assertEquals(0.0, locationData.longitude, 0.001)
        assertEquals(0.0, locationData.elevation, 0.001)
        assertEquals("Null Island", locationData.address)
    }

    @Test
    fun `test LocationData with negative coordinates`() {
        val locationData = LocationData(
            latitude = -33.8688,
            longitude = 151.2093,
            elevation = -10.0,
            address = "Sydney, Australia"
        )
        
        assertEquals(-33.8688, locationData.latitude, 0.001)
        assertEquals(151.2093, locationData.longitude, 0.001)
        assertEquals(-10.0, locationData.elevation, 0.001)
        assertEquals("Sydney, Australia", locationData.address)
    }
}
