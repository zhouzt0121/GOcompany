package com.company.go.sensor

import com.company.go.data.model.SensorData
import kotlin.math.sin
import kotlin.math.cos
import kotlin.math.PI

/**
 * Simulates magnetometer (magnetic field) sensor data
 * Provides X, Y, Z magnetic field strength in μT
 */
class MagnetometerEmulator {
    
    fun generateMagnetometerData(
        intensity: Float = 1.0f,
        timestamp: Long = System.currentTimeMillis()
    ): SensorData {
        val time = timestamp / 1000.0
        val frequency = 2 * PI / 8.0  // 8-second period
        
        // Earth's magnetic field approximately 45-60 μT
        val baseMagnitude = 50.0f * intensity
        
        val x = sin(frequency * time) * baseMagnitude
        val y = cos(frequency * time) * baseMagnitude * 0.8f
        val z = baseMagnitude * 0.6f + sin(frequency * time * 2) * baseMagnitude * 0.2f
        
        return SensorData(
            sensorType = "MAGNETOMETER",
            timestamp = timestamp,
            values = listOf(x, y, z),
            accuracy = 3,
            metadata = mapOf(
                "magnitude" to "%.2f".format(
                    kotlin.math.sqrt(x * x + y * y + z * z)
                )
            )
        )
    }
    
    fun generateCompassHeading(
        heading: Float = 0.0f,  // 0-360 degrees
        variation: Float = 0.1f,
        timestamp: Long = System.currentTimeMillis()
    ): SensorData {
        val time = timestamp / 1000.0
        val frequency = 2 * PI / 10.0
        
        val actualHeading = (heading + sin(frequency * time) * variation * 360) % 360
        val radiansHeading = actualHeading * PI / 180.0
        
        val magnitude = 50.0f
        val x = (magnitude * sin(radiansHeading)).toFloat()
        val y = (magnitude * cos(radiansHeading)).toFloat()
        val z = 30.0f
        
        return SensorData(
            sensorType = "MAGNETOMETER",
            timestamp = timestamp,
            values = listOf(x, y, z),
            accuracy = 2,
            metadata = mapOf("heading" to "%.1f".format(actualHeading))
        )
    }
}
