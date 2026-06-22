package com.company.go.data.repository

import com.company.go.data.model.SensorData
import com.company.go.data.model.SensorType
import com.company.go.data.model.SimulationConfig
import com.company.go.sensor.SimulationEngine
import kotlinx.coroutines.flow.StateFlow

class SensorRepository(private val simulationEngine: SimulationEngine) {

    val accelerometerData: StateFlow<SensorData?> = simulationEngine.accelerometerFlow
    val gyroscopeData: StateFlow<SensorData?> = simulationEngine.gyroscopeFlow
    val stepCounterData: StateFlow<SensorData?> = simulationEngine.stepCounterFlow
    val magnetometerData: StateFlow<SensorData?> = simulationEngine.magnetometerFlow
    val lightSensorData: StateFlow<SensorData?> = simulationEngine.lightSensorFlow
    val cellInfoData: StateFlow<SensorData?> = simulationEngine.cellInfoFlow
    val simulationStates: StateFlow<Map<SensorType, Boolean>> = simulationEngine.simulationStates

    fun startSensor(sensorType: SensorType) = simulationEngine.startSimulation(sensorType)
    fun startSensorWithConfig(sensorType: SensorType, config: SimulationConfig) = simulationEngine.startSimulation(sensorType, config)
    fun stopSensor(sensorType: SensorType) = simulationEngine.stopSimulation(sensorType)
    fun stopAllSensors() = simulationEngine.stopAllSimulations()
    fun getAllSensorStatus(): Map<SensorType, Boolean> = simulationEngine.getAllSensorStatus()
    fun isSensorRunning(sensorType: SensorType): Boolean = simulationEngine.isSensorRunning(sensorType)
    fun updateSensorConfig(sensorType: SensorType, config: SimulationConfig) = simulationEngine.updateConfig(sensorType, config)
    fun getSensorConfig(sensorType: SensorType): SimulationConfig = simulationEngine.getConfig(sensorType)
    fun toggleSensor(sensorType: SensorType) {
        if (isSensorRunning(sensorType)) stopSensor(sensorType) else startSensor(sensorType)
    }
}
