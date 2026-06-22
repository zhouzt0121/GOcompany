package com.company.go.sensor

import com.company.go.data.model.SensorData
import kotlin.math.sin
import kotlin.math.cos
import kotlin.math.PI

class GyroscopeEmulator {
    
    fun generateGyroscopeData(intensity: Float = 1.0f, timestamp: Long = System.currentTimeMillis()): SensorData {
        val time = timestamp / 1000.0
        val frequency = 2 * PI / 6.0
        val roll = sin(frequency * time) * intensity * 2.0f
        val pitch = cos(frequency * time) * intensity * 2.0f
        val yaw = sin(frequency * time * 1.5f) * intensity * 1.5f
        return SensorData(sensorType = "GYROSCOPE", timestamp = timestamp, values = listOf(roll, pitch, yaw), accuracy = 3)
    }
    
    fun generateRotation(rotationRate: Float = 1.0f, timestamp: Long = System.currentTimeMillis()): SensorData {
        val time = timestamp / 1000.0
        val angularVelocity = rotationRate * 0.1f
        val roll = sin(time * angularVelocity) * 2.0f
        val pitch = cos(time * angularVelocity) * 2.0f
        val yaw = time * angularVelocity
        return SensorData(sensorType = "GYROSCOPE", timestamp = timestamp, values = listOf(roll, pitch, yaw), accuracy = 3)
    }
}
