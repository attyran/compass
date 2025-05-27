package com.attyran.compass.sensor

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class LocationSensor @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    private val geocoder: Geocoder by lazy {
        Geocoder(context)
    }

    fun getLocationUpdates(): Flow<LocationData> = callbackFlow {
        val locationRequest = LocationRequest.Builder(2000L)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setMinUpdateIntervalMillis(1000L)
            .setMaxUpdateDelayMillis(3000L)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { location ->
                    val address = try {
                        geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            ?.firstOrNull()
                            ?.getAddressLine(0) ?: "Address unavailable"
                    } catch (e: Exception) {
                        "Address unavailable"
                    }

                    trySend(
                        LocationData(
                            latitude = location.latitude,
                            longitude = location.longitude,
                            elevation = metersToFeet(location.altitude),
                            address = address
                        )
                    )
                }
            }
        }

        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            // Handle permission not granted
            trySend(LocationData())
        }

        awaitClose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    private fun metersToFeet(meters: Double): Double {
        return meters * 3.28084
    }
}

data class LocationData(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val elevation: Double = 0.0,
    val address: String = "Location unavailable"
) 