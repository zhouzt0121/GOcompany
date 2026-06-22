package com.company.go.data.repository

import com.company.go.data.model.SensorData
import com.company.go.data.model.SensorType
import com.company.go.data.model.SimulationConfig
import com.company.go.sensor.SimulationEngine
import kotlinx.coroutines.flow.StateFlow

/**
 * Repository pattern for sensor data management
 * Acts as a single source of truth for sensor simulations
 */
class SensorRepository(private val simulationEngine: SimulationEngine) {

    // Expose sensor data flows
    val accelerometerData: StateFlow<SensorData?> = simulationEngine.accelerometerFlow
    val gyroscopeData: StateFlow<SensorData?> = simulationEngine.gyroscopeFlow
    val stepCounterData: StateFlow<SensorData?> = simulationEngine.stepCounterFlow
    val magnetometerData: StateFlow<SensorData?> = simulationEngine.magnetometerFlow
    val lightSensorData: StateFlow<SensorData?> = simulationEngine.lightSensorFlow
    val cellInfoData: StateFlow<SensorData?> = simulationEngine.cellInfoFlow
    val simulationStates: StateFlow<Map<SensorType, Boolean>> = simulationEngine.simulationStates

    /**
     * Start sensor simulation with default config
     */
    fun startSensor(sensorType: SensorType) {
        simulationEngine.startSimulation(sensorType)
    }

    /**
     * Start sensor simulation with custom config
     */
    fun startSensorWithConfig(sensorType: SensorType, config: SimulationConfig) {
        simulationEngine.startSimulation(sensorType, config)
    }

    /**
     * Stop sensor simulation
     */
    fun stopSensor(sensorType: SensorType) {
        simulationEngine.stopSimulation(sensorType)
    }

    /**
     * Stop all simulations
     */
    fun stopAllSensors() {
        simulationEngine.stopAllSimulations()
    }

    /**
     * Get all sensor statuses
     */
    fun getAllSensorStatus(): Map<SensorType, Boolean> {
        return simulationEngine.getAllSensorStatus()
    }

    /**
     * Check if specific sensor is running
     */
    fun isSensorRunning(sensorType: SensorType): Boolean {
        return simulationEngine.isSensorRunning(sensorType)
    }

    /**
     * Update sensor configuration
     */
    fun updateSensorConfig(sensorType: SensorType, config: SimulationConfig) {
        simulationEngine.updateConfig(sensorType, config)
    }

    /**
     * Get sensor configuration
     */
    fun getSensorConfig(sensorType: SensorType): SimulationConfig {
        return simulationEngine.getConfig(sensorType)
    }

    /**
     * Toggle sensor on/off
     */
    fun toggleSensor(sensorType: SensorType) {
        if (isSensorRunning(sensorType)) {
            stopSensor(sensorType)
        } else {
            startSensor(sensorType)
        }
    }
}
