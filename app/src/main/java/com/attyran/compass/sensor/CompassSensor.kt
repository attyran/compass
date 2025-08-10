package com.attyran.compass.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.Surface
import android.view.WindowManager
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

    @Suppress("DEPRECATION")
    private val windowManager by lazy {
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    private val rotationVectorSensor by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    }

    fun getCompassReadings(): Flow<CompassData> = callbackFlow {
        val rotationMatrix = FloatArray(9)
        val adjustedRotationMatrix = FloatArray(9)
        val orientationAngles = FloatArray(3)

        // Simple smoothing variables
        var lastAzimuth = 0.0
        val smoothingFactor = 0.3f // Higher for more responsive movement

        val sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
                    SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)

                    val rotation = windowManager.defaultDisplay.rotation
                    val axisX = when (rotation) {
                        Surface.ROTATION_90 -> SensorManager.AXIS_Z
                        Surface.ROTATION_180 -> SensorManager.AXIS_MINUS_X
                        Surface.ROTATION_270 -> SensorManager.AXIS_MINUS_Z
                        else -> SensorManager.AXIS_X
                    }
                    val axisY = when (rotation) {
                        Surface.ROTATION_90 -> SensorManager.AXIS_MINUS_X
                        Surface.ROTATION_180 -> SensorManager.AXIS_MINUS_Z
                        Surface.ROTATION_270 -> SensorManager.AXIS_X
                        else -> SensorManager.AXIS_Z
                    }

                    SensorManager.remapCoordinateSystem(rotationMatrix, axisX, axisY, adjustedRotationMatrix)
                    SensorManager.getOrientation(adjustedRotationMatrix, orientationAngles)

                    val azimuthInRadians = orientationAngles[0]
                    val azimuthInDegrees = Math.toDegrees(azimuthInRadians.toDouble())
                    val normalizedAzimuth = (azimuthInDegrees + 360) % 360

                    // Simple smoothing
                    lastAzimuth += smoothingFactor * (normalizedAzimuth - lastAzimuth)
                    lastAzimuth = (lastAzimuth + 360) % 360

                    val direction = when (lastAzimuth.roundToInt()) {
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
                            degrees = lastAzimuth.roundToInt(),
                            direction = direction
                        )
                    )
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(
            sensorEventListener,
            rotationVectorSensor,
            SensorManager.SENSOR_DELAY_UI
        )

        awaitClose {
            sensorManager.unregisterListener(sensorEventListener)
        }
    }
}

data class CompassData(
    val degrees: Int,
    val direction: String
) 