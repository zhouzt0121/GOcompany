package com.company.go.sensor

import com.company.go.data.model.SensorData
import kotlin.math.sin
import kotlin.math.cos
import kotlin.math.PI

class AccelerometerEmulator {
    
    fun generateAccelerometerData(intensity: Float = 1.0f, timestamp: Long = System.currentTimeMillis()): SensorData {
        val time = timestamp / 1000.0
        val frequency = 2 * PI / 4.0
        val x = sin(frequency * time) * intensity * 9.81f
        val y = cos(frequency * time) * intensity * 9.81f
        val z = 9.81f + sin(frequency * time * 2) * intensity * 0.5f
        return SensorData(sensorType = "ACCELEROMETER", timestamp = timestamp, values = listOf(x, y, z), accuracy = 3)
    }
    
    fun generateRandomAcceleration(intensity: Float = 1.0f): SensorData {
        val x = (Math.random() - 0.5f) * 2 * intensity * 9.81f
        val y = (Math.random() - 0.5f) * 2 * intensity * 9.81f
        val z = 9.81f + (Math.random() - 0.5f) * intensity * 2
        return SensorData(sensorType = "ACCELEROMETER", timestamp = System.currentTimeMillis(), values = listOf(x, y, z), accuracy = 3)
    }
}
