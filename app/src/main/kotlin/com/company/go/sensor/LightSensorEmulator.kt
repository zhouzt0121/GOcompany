package com.company.go.sensor

import com.company.go.data.model.SensorData
import kotlin.math.sin
import kotlin.math.PI

class LightSensorEmulator {
    
    fun generateLightData(baseIntensity: Float = 400.0f, variation: Float = 0.2f, timestamp: Long = System.currentTimeMillis()): SensorData {
        val time = timestamp / 1000.0
        val frequency = 2 * PI / 30.0
        val fluctuation = sin(frequency * time) * variation
        val lux = maxOf(0.1f, baseIntensity * (1.0f + fluctuation))
        return SensorData(sensorType = "LIGHT_SENSOR", timestamp = timestamp, values = listOf(lux), accuracy = 3)
    }
    
    fun generateDayNightCycle(hourOfDay: Int = 12, timestamp: Long = System.currentTimeMillis()): SensorData {
        val normalizedHour = (hourOfDay % 24) / 24.0
        val cycle = sin(PI * normalizedHour - PI / 2.0)
        val lux = when {
            cycle < 0 -> 0.1f
            cycle < 0.3f -> 10.0f + cycle * 300.0f
            cycle < 0.7f -> 100.0f + cycle * 1000.0f
            else -> 50.0f + (1.0f - cycle) * 500.0f
        }
        return SensorData(sensorType = "LIGHT_SENSOR", timestamp = timestamp, values = listOf(lux.coerceIn(0.1f, 120000.0f)), accuracy = 2)
    }
}
