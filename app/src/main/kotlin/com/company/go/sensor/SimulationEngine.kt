package com.company.go.sensor

import com.company.go.data.model.SensorData
import com.company.go.data.model.SensorType
import com.company.go.data.model.SimulationConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI

/**
 * Core Sensor Simulation Engine
 * Manages all sensor simulations with configurable parameters
 */
class SimulationEngine(private val scope: CoroutineScope) {

    // Sensor data flows
    private val _accelerometerFlow = MutableStateFlow<SensorData?>(null)
    val accelerometerFlow: StateFlow<SensorData?> = _accelerometerFlow.asStateFlow()

    private val _gyroscopeFlow = MutableStateFlow<SensorData?>(null)
    val gyroscopeFlow: StateFlow<SensorData?> = _gyroscopeFlow.asStateFlow()

    private val _stepCounterFlow = MutableStateFlow<SensorData?>(null)
    val stepCounterFlow: StateFlow<SensorData?> = _stepCounterFlow.asStateFlow()

    private val _magnetometerFlow = MutableStateFlow<SensorData?>(null)
    val magnetometerFlow: StateFlow<SensorData?> = _magnetometerFlow.asStateFlow()

    private val _lightSensorFlow = MutableStateFlow<SensorData?>(null)
    val lightSensorFlow: StateFlow<SensorData?> = _lightSensorFlow.asStateFlow()

    private val _cellInfoFlow = MutableStateFlow<SensorData?>(null)
    val cellInfoFlow: StateFlow<SensorData?> = _cellInfoFlow.asStateFlow()

    // Simulation states
    private val _simulationStates = MutableStateFlow<Map<SensorType, Boolean>>(
        SensorType.values().associateWith { false }
    )
    val simulationStates: StateFlow<Map<SensorType, Boolean>> = _simulationStates.asStateFlow()

    // Active simulation jobs
    private val simulationJobs = mutableMapOf<SensorType, Job>()

    // Configuration storage
    private val configs = mutableMapOf<SensorType, SimulationConfig>()

    // Emulator instances
    private val accelerometerEmulator = AccelerometerEmulator()
    private val gyroscopeEmulator = GyroscopeEmulator()
    private val stepCounterEmulator = StepCounterEmulator()
    private val magnetometerEmulator = MagnetometerEmulator()
    private val lightSensorEmulator = LightSensorEmulator()
    private val cellInfoEmulator = CellInfoEmulator()

    /**
     * Start sensor simulation
     */
    fun startSimulation(sensorType: SensorType, config: SimulationConfig = SimulationConfig(sensorType.name)) {
        // Cancel existing job if running
        simulationJobs[sensorType]?.cancel()

        // Store config
        configs[sensorType] = config

        // Start new simulation
        simulationJobs[sensorType] = scope.launch {
            simulateSelectedSensor(sensorType, config)
        }

        // Update state
        updateSimulationState(sensorType, true)
    }

    /**
     * Stop sensor simulation
     */
    fun stopSimulation(sensorType: SensorType) {
        simulationJobs[sensorType]?.cancel()
        simulationJobs.remove(sensorType)
        updateSimulationState(sensorType, false)
    }

    /**
     * Stop all simulations
     */
    fun stopAllSimulations() {
        SensorType.values().forEach { stopSimulation(it) }
    }

    /**
     * Main simulation loop
     */
    private suspend fun simulateSelectedSensor(sensorType: SensorType, config: SimulationConfig) {
        val intervalMs = 1000L / (config.frequency.coerceIn(1, 200))
        var elapsedTime = 0L

        while (true) {
            if (config.duration > 0 && elapsedTime >= config.duration) {
                stopSimulation(sensorType)
                break
            }

            val data = when (sensorType) {
                SensorType.ACCELEROMETER -> accelerometerEmulator.generateAccelerometerData(config.intensity)
                SensorType.GYROSCOPE -> gyroscopeEmulator.generateGyroscopeData(config.intensity)
                SensorType.STEP_COUNTER -> stepCounterEmulator.generateStepData(stepsPerMinute = (config.intensity * 200).toInt())
                SensorType.STEP_DETECTOR -> generateStepDetectorData(config.intensity)
                SensorType.MAGNETOMETER -> magnetometerEmulator.generateMagnetometerData(config.intensity)
                SensorType.LINEAR_ACCELEROMETER -> accelerometerEmulator.generateAccelerometerData(config.intensity).copy(
                    sensorType = "LINEAR_ACCELEROMETER"
                )
                SensorType.LIGHT_SENSOR -> lightSensorEmulator.generateLightData(config.intensity * 500)
                SensorType.CELL_INFO -> cellInfoEmulator.generateVariableSignal()
            }

            when (sensorType) {
                SensorType.ACCELEROMETER -> _accelerometerFlow.value = data
                SensorType.GYROSCOPE -> _gyroscopeFlow.value = data
                SensorType.STEP_COUNTER -> _stepCounterFlow.value = data
                SensorType.STEP_DETECTOR -> _stepCounterFlow.value = data
                SensorType.MAGNETOMETER -> _magnetometerFlow.value = data
                SensorType.LINEAR_ACCELEROMETER -> _accelerometerFlow.value = data
                SensorType.LIGHT_SENSOR -> _lightSensorFlow.value = data
                SensorType.CELL_INFO -> _cellInfoFlow.value = data
            }

            delay(intervalMs)
            elapsedTime += intervalMs
        }
    }

    /**
     * Generate step detector events
     */
    private fun generateStepDetectorData(intensity: Float): SensorData {
        return SensorData(
            sensorType = "STEP_DETECTOR",
            timestamp = System.currentTimeMillis(),
            values = listOf(1.0f),  // 1 = step detected
            accuracy = 3
        )
    }

    /**
     * Update simulation state
     */
    private fun updateSimulationState(sensorType: SensorType, isRunning: Boolean) {
        val currentStates = _simulationStates.value.toMutableMap()
        currentStates[sensorType] = isRunning
        _simulationStates.value = currentStates
    }

    /**
     * Get current state of all sensors
     */
    fun getAllSensorStatus(): Map<SensorType, Boolean> {
        return _simulationStates.value
    }

    /**
     * Check if sensor is running
     */
    fun isSensorRunning(sensorType: SensorType): Boolean {
        return _simulationStates.value[sensorType] ?: false
    }

    /**
     * Update sensor configuration
     */
    fun updateConfig(sensorType: SensorType, config: SimulationConfig) {
        configs[sensorType] = config
        if (isSensorRunning(sensorType)) {
            stopSimulation(sensorType)
            startSimulation(sensorType, config)
        }
    }

    /**
     * Get sensor configuration
     */
    fun getConfig(sensorType: SensorType): SimulationConfig {
        return configs[sensorType] ?: SimulationConfig(sensorType.name)
    }
}
