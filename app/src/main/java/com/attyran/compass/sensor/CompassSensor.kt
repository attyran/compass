package com.attyran.compass.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.math.roundToInt

class CompassSensor @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sensorManager by lazy {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private val accelerometer by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    private val magnetometer by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    fun getCompassReadings(): Flow<CompassData> = callbackFlow {
        val rotationMatrix = FloatArray(9)
        val orientationAngles = FloatArray(3)
        var lastAccelerometer = FloatArray(3)
        var lastMagnetometer = FloatArray(3)
        var lastAccelerometerSet = false
        var lastMagnetometerSet = false

        val sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                when (event.sensor.type) {
                    Sensor.TYPE_ACCELEROMETER -> {
                        lastAccelerometer = event.values.clone()
                        lastAccelerometerSet = true
                    }
                    Sensor.TYPE_MAGNETIC_FIELD -> {
                        lastMagnetometer = event.values.clone()
                        lastMagnetometerSet = true
                    }
                }

                if (lastAccelerometerSet && lastMagnetometerSet) {
                    SensorManager.getRotationMatrix(
                        rotationMatrix,
                        null,
                        lastAccelerometer,
                        lastMagnetometer
                    )
                    SensorManager.getOrientation(rotationMatrix, orientationAngles)

                    val azimuthInRadians = orientationAngles[0]
                    val azimuthInDegrees = Math.toDegrees(azimuthInRadians.toDouble())
                    val normalizedAzimuth = ((azimuthInDegrees + 360) % 360).roundToInt()
                    
                    val direction = when (normalizedAzimuth) {
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

                    trySend(
                        CompassData(
                            degrees = normalizedAzimuth,
                            direction = direction,
                            strength = lastMagnetometer.magnitude()
                        )
                    )
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(
            sensorEventListener,
            accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager.registerListener(
            sensorEventListener,
            magnetometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        awaitClose {
            sensorManager.unregisterListener(sensorEventListener)
        }
    }

    private fun FloatArray.magnitude(): Float {
        return kotlin.math.sqrt(this[0] * this[0] + this[1] * this[1] + this[2] * this[2])
    }
}

data class CompassData(
    val degrees: Int,
    val direction: String,
    val strength: Float
) 