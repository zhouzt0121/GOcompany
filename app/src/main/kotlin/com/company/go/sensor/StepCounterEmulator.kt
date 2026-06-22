package com.company.go.sensor

import com.company.go.data.model.SensorData

class StepCounterEmulator {
    private var stepCount: Long = 0L
    private var lastUpdateTime: Long = 0L
    
    fun generateStepData(stepsPerMinute: Int = 120, timestamp: Long = System.currentTimeMillis()): SensorData {
        if (lastUpdateTime == 0L) lastUpdateTime = timestamp
        val timeDeltaMs = timestamp - lastUpdateTime
        val newSteps = (timeDeltaMs * stepsPerMinute / 60000).toLong()
        stepCount += newSteps
        lastUpdateTime = timestamp
        return SensorData(sensorType = "STEP_COUNTER", timestamp = timestamp, values = listOf(stepCount.toFloat()), accuracy = 3)
    }
    
    fun getCurrentStepCount(): Long = stepCount
    fun resetStepCount() { stepCount = 0L; lastUpdateTime = System.currentTimeMillis() }
    fun setStepCount(count: Long) { stepCount = count; lastUpdateTime = System.currentTimeMillis() }
}
