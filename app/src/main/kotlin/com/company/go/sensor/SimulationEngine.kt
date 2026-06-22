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

class SimulationEngine(private val scope: CoroutineScope) {

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

    private val _simulationStates = MutableStateFlow<Map<SensorType, Boolean>>(
        SensorType.values().associateWith { false }
    )
    val simulationStates: StateFlow<Map<SensorType, Boolean>> = _simulationStates.asStateFlow()

    private val simulationJobs = mutableMapOf<SensorType, Job>()
    private val configs = mutableMapOf<SensorType, SimulationConfig>()

    private val accelerometerEmulator = AccelerometerEmulator()
    private val gyroscopeEmulator = GyroscopeEmulator()
    private val stepCounterEmulator = StepCounterEmulator()
    private val magnetometerEmulator = MagnetometerEmulator()
    private val lightSensorEmulator = LightSensorEmulator()
    private val cellInfoEmulator = CellInfoEmulator()

    fun startSimulation(sensorType: SensorType, config: SimulationConfig = SimulationConfig(sensorType.name)) {
        simulationJobs[sensorType]?.cancel()
        configs[sensorType] = config
        simulationJobs[sensorType] = scope.launch { simulateSelectedSensor(sensorType, config) }
        updateSimulationState(sensorType, true)
    }

    fun stopSimulation(sensorType: SensorType) {
        simulationJobs[sensorType]?.cancel()
        simulationJobs.remove(sensorType)
        updateSimulationState(sensorType, false)
    }

    fun stopAllSimulations() { SensorType.values().forEach { stopSimulation(it) } }

    private suspend fun simulateSelectedSensor(sensorType: SensorType, config: SimulationConfig) {
        val intervalMs = 1000L / (config.frequency.coerceIn(1, 200))
        var elapsedTime = 0L
        while (true) {
            if (config.duration > 0 && elapsedTime >= config.duration) { stopSimulation(sensorType); break }
            val data = when (sensorType) {
                SensorType.ACCELEROMETER -> accelerometerEmulator.generateAccelerometerData(config.intensity)
                SensorType.GYROSCOPE -> gyroscopeEmulator.generateGyroscopeData(config.intensity)
                SensorType.STEP_COUNTER -> stepCounterEmulator.generateStepData(stepsPerMinute = (config.intensity * 200).toInt())
                SensorType.STEP_DETECTOR -> SensorData(sensorType = "STEP_DETECTOR", timestamp = System.currentTimeMillis(), values = listOf(1.0f), accuracy = 3)
                SensorType.MAGNETOMETER -> magnetometerEmulator.generateMagnetometerData(config.intensity)
                SensorType.LINEAR_ACCELEROMETER -> accelerometerEmulator.generateAccelerometerData(config.intensity).copy(sensorType = "LINEAR_ACCELEROMETER")
                SensorType.LIGHT_SENSOR -> lightSensorEmulator.generateLightData(config.intensity * 500)
                SensorType.CELL_INFO -> cellInfoEmulator.generateVariableSignal()
            }
            when (sensorType) {
                SensorType.ACCELEROMETER, SensorType.LINEAR_ACCELEROMETER -> _accelerometerFlow.value = data
                SensorType.GYROSCOPE -> _gyroscopeFlow.value = data
                SensorType.STEP_COUNTER, SensorType.STEP_DETECTOR -> _stepCounterFlow.value = data
                SensorType.MAGNETOMETER -> _magnetometerFlow.value = data
                SensorType.LIGHT_SENSOR -> _lightSensorFlow.value = data
                SensorType.CELL_INFO -> _cellInfoFlow.value = data
            }
            delay(intervalMs)
            elapsedTime += intervalMs
        }
    }

    private fun updateSimulationState(sensorType: SensorType, isRunning: Boolean) {
        val currentStates = _simulationStates.value.toMutableMap()
        currentStates[sensorType] = isRunning
        _simulationStates.value = currentStates
    }

    fun getAllSensorStatus(): Map<SensorType, Boolean> = _simulationStates.value
    fun isSensorRunning(sensorType: SensorType): Boolean = _simulationStates.value[sensorType] ?: false
    fun updateConfig(sensorType: SensorType, config: SimulationConfig) {
        configs[sensorType] = config
        if (isSensorRunning(sensorType)) { stopSimulation(sensorType); startSimulation(sensorType, config) }
    }
    fun getConfig(sensorType: SensorType): SimulationConfig = configs[sensorType] ?: SimulationConfig(sensorType.name)
}
