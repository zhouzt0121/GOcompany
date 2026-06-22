package com.company.go.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SensorData(
    val sensorType: String,
    val timestamp: Long = System.currentTimeMillis(),
    val values: List<Float> = emptyList(),
    val accuracy: Int = 0,
    val metadata: Map<String, String> = emptyMap()
)

@Serializable
data class SimulationConfig(
    val sensorType: String,
    val intensity: Float = 1.0f,
    val frequency: Int = 50,
    val duration: Long = 0L,
    val isActive: Boolean = false
)

@Serializable
data class SensorState(
    val type: SensorType,
    val isEnabled: Boolean = false,
    val config: SimulationConfig = SimulationConfig(type.name),
    val currentData: SensorData = SensorData(type.name)
)
